/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.basicelements;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.animlib.AnimationGroupSupport;
import com.appon.animlib.util.APSerilize;
import com.appon.animlib.util.Point;
import com.appon.animlib.util.SerializeUtil;
import com.appon.animlib.util.Util;

/**
 *
 * @author acer
 */
public class APLineShape extends APShapeElement {

	double x2 = 50, y2 = 25;
	public APLineShape(int id) {
		super(id);

	}
	public APShapeElement clone() {
		APLineShape arc = new APLineShape(-1);
		copyProperties(arc);
		arc.setX2(getX2());
		arc.setY2(getY2());
		return arc;
	}
	public void port(int xper, int yper) {
		setX(Util.getScaleValue(getX(), xper));
		setY(Util.getScaleValue(getY(), yper));
		setX2(Util.getScaleValue(getX2(), xper));
		setY2(Util.getScaleValue(getY2(), yper));
	}
	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}
	@Override
	public double getMinX() {
		return Math.min(getX(), getX2());
	}
	public double getMinY() {
		return Math.min(getY(), getY2());
	}

	@Override
	public double getWidth() {
		return Math.abs(getX2() - getX());
	}
	@Override
	public double getHeight() {
		return Math.abs(getY2() - getY());
	}
	// #ifdef ANDROID
	public void paint(Canvas g, int _x, int _y, int theta, int zoom, int anchorX, int anchorY, AnimationGroupSupport parent, Paint paintObj) {
		// #else
		// # public void paint(Graphics g,int _x,int _y,int theta,int zoom,int
		// anchorX,int anchorY,AnimationGroupSupport parent) {
		// #endif
		if (getBorderColor() != -1) {
			// #ifdef ANDROID
			paintObj.setColor(Util.getColor(getBorderColor()));
			// #else
			// # g.setColor(Util.getColor(getBorderColor()));
			// #endif

			Point p = Util.pointToRotate;
			p.setPoints(getX(), getY());
			Util.rotatePoint(p, anchorX, anchorY, theta, zoom, this);
			double newX1 = p.getX();
			double newY1 = p.getY();
			p.setPoints(getX2(), getY2());
			Util.rotatePoint(p, anchorX, anchorY, theta, zoom, this);
			// #ifdef ANDROID
			Util.drawThickLine(g, newX1 + _x, newY1 + _y, p.getX() + _x, p.getY() + _y, getBorderThickness(), paintObj);
			// #else
			// # Util.drawThickLine(g, Util.roundTheValues(newX1 + _x),
			// Util.roundTheValues(newY1 + _y),Util.roundTheValues( p.getX()+
			// _x),Util.roundTheValues( p.getY() + _y), getBorderThickness());
			// #endif
		}
	}

	public String toString() {
		return "Line: " + getId();
	}

	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(super.serialize());
		SerializeUtil.writeDouble(bos, x2);
		SerializeUtil.writeDouble(bos, y2);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		super.deserialize(bis);
		x2 = SerializeUtil.readDouble(bis);
		y2 = SerializeUtil.readDouble(bis);
		bis.close();
		return null;
	}

	public int getClassCode() {
		return APSerilize.SHAPE_TYPE_APLINE;
	}
}
