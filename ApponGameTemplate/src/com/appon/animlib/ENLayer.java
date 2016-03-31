/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib;

//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#elifdef ANDROID
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

//#endif


import com.appon.animlib.util.APSerilize;
import com.appon.animlib.util.SerializeUtil;
import com.appon.animlib.util.Serilizable;

/**
 *
 * @author Swaroop1
 */
public class ENLayer implements Serilizable {

	Hashtable<Integer, ENTimeFrame> timeFrames = new Hashtable<Integer, ENTimeFrame>();
	Vector<ENTimeFrame> timeframesVector;
	private boolean isVisible = true;
	private int length;
	public ENLayer() {

	}
	public ENLayer clone() {
		ENLayer layer = new ENLayer();
		layer.isVisible = isVisible;
		layer.timeframesVector = new Vector<ENTimeFrame>();
		layer.length = length;
		if (timeframesVector != null) {
			for (int i = 0; i < timeframesVector.size(); i++) {
				layer.timeframesVector.add(timeframesVector.get(i).clone());
			}

		}
		layer.updateHashTable();
		return layer;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	// #ifdef ANDROID
	public void paint(Canvas g, int x, int y, int timeFrame, boolean considerVisability, AnimationSupport parent, AnimationGroupSupport group, Paint paintObject)
	// #else
	// # public void paint(Graphics g,int x,int y,int timeFrame,boolean
	// considerVisability,AnimationSupport parent,AnimationGroupSupport group)
	// #endif

	{
		if (considerVisability && !isVisible()) {
			return;
		}
		ENTimeFrame item = timeFrames.get(timeFrame);
		if (item != null) {
			// #ifdef ANDROID
			item.paint(g, x, y, considerVisability, parent, group, false, paintObject);
			// #else
			// # item.paint(g, x, y,considerVisability,parent,group,false);
			// #endif

		}

	}
	// #ifdef ANDROID
	public void paint(Canvas g, int x, int y, int timeFrame, boolean considerVisability, int theta, int zoom, int anchorX, int anchorY, AnimationSupport parent, AnimationGroupSupport group, Paint p)
	// #else
	// # public void paint(Graphics g,int x,int y,int timeFrame,boolean
	// considerVisability,int theta,int zoom,int anchorX,int
	// anchorY,AnimationSupport parent,AnimationGroupSupport group)
	// #endif

	{
		if (considerVisability && !isVisible()) {
			return;
		}
		ENTimeFrame item = timeFrames.get(timeFrame);
		if (item != null) {
			// #ifdef ANDROID
			item.paint(g, x, y, considerVisability, theta, zoom, anchorX, anchorY, group, parent, false, p);
			// #else
			// # item.paint(g, x,
			// y,considerVisability,theta,zoom,anchorX,anchorY,group,parent,false);
			// #endif

		}

	}
	@Override
	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		APSerilize.serialize(timeframesVector, bos);
		SerializeUtil.writeBoolean(bos, isVisible);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}
	private void updateHashTable() {
		timeFrames.clear();
		for (int i = 0; i < timeframesVector.size(); i++) {
			ENTimeFrame frame = timeframesVector.get(i);
			for (int j = frame.getOffset(); j < frame.getOffset() + frame.getLength(); j++) {
				timeFrames.put(j, frame);
			}
		}
	}
	public void port(int xper, int yper) {
		for (int i = 0; i < timeframesVector.size(); i++) {
			timeframesVector.get(i).port(xper, yper);
		}
		updateHashTable();
	}
	@Override
	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		// to skip
		SerializeUtil.readString(bis);
		timeframesVector = (Vector<ENTimeFrame>) APSerilize.deserialize(bis, APSerilize.getInstance());
		length = SerializeUtil.readInt(bis, 2);
		// to skip
		SerializeUtil.readBoolean(bis);
		isVisible = SerializeUtil.readBoolean(bis);
		// to skip
		SerializeUtil.readBoolean(bis);
		updateHashTable();
		return bis;
	}
	@Override
	public int getClassCode() {
		return APSerilize.ENLAYER_TYPE;
	}
}
