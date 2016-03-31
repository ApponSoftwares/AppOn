/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;





//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.animlib.basicelements.APShapeElement;
import com.appon.animlib.util.APSerilize;
import com.appon.animlib.util.SerializeUtil;
import com.appon.animlib.util.Serilizable;
//#endif
/**
 *
 * @author Swaroop1
 */
public class ENTimeFrame implements Serilizable {

	private int timeFrameId;
	private Vector shapes = new Vector();
	private boolean visibility = true;
	private int length = 0;
	private int offset = 0;
	public ENTimeFrame clone() {
		ENTimeFrame tf = new ENTimeFrame();
		tf.setTimeFrameId(getTimeFrameId());
		tf.length = length;
		tf.offset = offset;
		if (shapes != null) {
			for (int i = 0; i < shapes.size(); i++) {
				Object object = shapes.elementAt(i);
				tf.getShapes().addElement(((APShapeElement) object).clone());
			}
		}
		tf.setVisibility(isVisibility());
		return tf;
	}
	public void port(int xper, int yper) {
		for (int i = 0; i < shapes.size(); i++) {
			APShapeElement shape = (APShapeElement) shapes.elementAt(i);
			shape.port(xper, yper);
		}
	}
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {

		this.length = length;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public boolean isVisibility() {
		return visibility;
	}
	// #ifdef ANDROID
	public void paint(Canvas g, int x, int y, boolean considerVisability, AnimationSupport animation, AnimationGroupSupport parent, boolean calldedFromETF, Paint p)
	// #else
	// # public void paint(Graphics g,int x,int y,boolean
	// considerVisability,AnimationSupport animation,AnimationGroupSupport
	// parent,boolean calldedFromETF)
	// #endif

	{
		if (considerVisability && !isVisibility() && !calldedFromETF)
			return;
		for (int i = 0; i < shapes.size(); i++) {
			APShapeElement object = (APShapeElement) shapes.elementAt(i);
			if ((considerVisability && object.isVisible()) || !considerVisability) {

				// #ifdef ANDROID
				object.paint(g, x, y, 0, 0, 0, 0, parent, p);
				// #else
				// # object.paint(g,x,y,0,0,0,0,parent);
				// #endif
			}

		}
	}
	// #ifdef ANDROID
	public void paint(Canvas g, int x, int y, boolean considerVisability, int theta, int zoom, int anchorX, int anchorY, AnimationGroupSupport parent, AnimationSupport animation, boolean calldedFromETF, Paint p)
	// #else
	// # public void paint(Graphics g,int x,int y,boolean considerVisability,int
	// theta,int zoom,int anchorX,int anchorY,AnimationGroupSupport
	// parent,AnimationSupport animation,boolean calldedFromETF)
	// #endif
	{

		if (considerVisability && !isVisibility() && !calldedFromETF)
			return;
		for (int i = 0; i < shapes.size(); i++) {
			APShapeElement object = (APShapeElement) shapes.elementAt(i);

			if ((considerVisability && object.isVisible()) || !considerVisability /*
																				 * ||
																				 * calldedFromETF
																				 */) {

				// #ifdef ANDROID
				object.paint(g, x, y, theta, zoom, anchorX, anchorY, parent, p);
				// #else
				// # object.paint(g,x,y,theta,zoom,anchorX,anchorY,parent);
				// #endif

			}

		}
	}
	public int getTimeFrameId() {
		return timeFrameId;
	}

	public void setTimeFrameId(int timeFrameId) {
		this.timeFrameId = timeFrameId;
	}

	public Vector getShapes() {
		return shapes;
	}

	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		SerializeUtil.writeSignedInt(bos, timeFrameId, 2);
		APSerilize.serialize(shapes, bos);
		APSerilize.serialize(new Boolean(isVisibility()), bos);
		SerializeUtil.writeInt(bos, offset, 2);
		SerializeUtil.writeInt(bos, length, 1);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		timeFrameId = SerializeUtil.readInt(bis, 2);
		shapes = (Vector) APSerilize.deserialize(bis, APSerilize.getInstance());
		setVisibility(((Boolean) APSerilize.deserialize(bis, APSerilize.getInstance())).booleanValue());
		offset = SerializeUtil.readInt(bis, 2);
		length = SerializeUtil.readInt(bis, 1);
		bis.close();
		return null;
	}

	public int getClassCode() {
		return APSerilize.ENTIMEFRAME_TYPE;
	}

}
