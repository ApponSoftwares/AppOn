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
import java.util.Iterator;
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
public class ENAnimation extends AnimationSupport implements Serilizable {
	private Vector<ENLayer> layers = new Vector<ENLayer>();
	private int maxTimeFrames = 0;
	private int currentTimeFrame = 0;
	ENAnimListener listener;
	private boolean isanimOver=false;
	public ENAnimation() {

	}
	public void setListener(ENAnimListener listener) {
		this.listener = listener;
	}
	public ENAnimation clone() {
		ENAnimation animation = new ENAnimation();

		for (int i = 0; i < layers.size(); i++) {
			animation.getLayers().add(layers.get(i).clone());
		}
		animation.maxTimeFrames = maxTimeFrames;
		animation.currentTimeFrame = 0;
		isanimOver=false;
		return animation;
	}
	public void reset() {
		currentTimeFrame = 0;
		isanimOver=false;
	}
	public int getCurrentTimeFrame() {
		return currentTimeFrame;
	}
	// #ifdef ANDROID
	public void render(Canvas g, int x, int y, AnimationGroupSupport group, Paint paintObject, boolean isLoop) {
		int size = layers.size();
		for (int i = 0; i < size; i++) {
			if (layers.get(i).isVisible()) {
				paintScane(g, x, y, currentTimeFrame, i, group, paintObject);
			}
		}

		currentTimeFrame++;
		if (currentTimeFrame >= maxTimeFrames) {
			if (isLoop) {
				currentTimeFrame = 0;
			} else {
				currentTimeFrame = maxTimeFrames - 1;
			}
			if (listener != null) {
				listener.animationOver(this);
			}
			isanimOver=true;
		}

	}
	// #endif
	// #ifdef ANDROID
	public void paintScane(Canvas g, int x, int y, int timeFrame, int layerIndex, AnimationGroupSupport group, Paint paintObject)
	// #else
	// # public void paintScane(Graphics g,int x,int y,int timeFrame,int
	// layerIndex,AnimationGroupSupport group)
	// #endif

	{
		ENLayer object = (ENLayer) layers.get(layerIndex);
		// #ifdef ANDROID
		object.paint(g, x, y, timeFrame, false, this, group, paintObject);
		// #else
		// # object.paint(g, x, y, timeFrame, false, this, group);
		// #endif
	}
	public int getMaxTimeFrame() {
		int max = -1;
		for (Iterator<ENLayer> it = layers.iterator(); it.hasNext();) {
			ENLayer aPLayer = it.next();
			if (aPLayer.isVisible())
				max = Math.max(max, aPLayer.getLength());
		}
		return max;
	}
	public int getMaxTimeFrame(int layerId) {
		return layers.get(layerId).getLength();
	}
	public Vector<ENLayer> getLayers() {
		return layers;
	}

	public void port(int xper, int yper) {
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).port(xper, yper);
		}
	}

	@Override
	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		APSerilize.serialize(layers, bos);

		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	@Override
	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		layers = (Vector<ENLayer>) APSerilize.deserialize(bis, APSerilize.getInstance());
		// to skip
		SerializeUtil.readInt(bis, 2);
		SerializeUtil.readInt(bis, 2);
		SerializeUtil.readString(bis);
		maxTimeFrames = getMaxTimeFrame();
		currentTimeFrame = 0;
		return bis;
	}

	@Override
	public int getClassCode() {
		return APSerilize.ENANIMATION_TYPE;
	}
	public boolean isAnimOver() {
		return isanimOver;
	}
}
