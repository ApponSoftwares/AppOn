/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.basicelements;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#elifdef ANDROID
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
public class APRectShape extends APShapeElement {
	private int fillType;
	private double width = 50, height = 50;

	public APRectShape(int id) {
		super(id);
	}
	public APShapeElement clone() {
		APRectShape arc = new APRectShape(-1);
		copyProperties(arc);
		arc.setFillType(getFillType());
		arc.setWidth(getWidth());
		arc.setHeight(getHeight());
		return arc;
	}

	@Override
	public void setBorderColor(int borderColor) {
		super.setBorderColor(borderColor);
	}

	@Override
	public int getBorderColor() {
		return super.getBorderColor();
	}

	public int getFillType() {
		return fillType;
	}

	public void setFillType(int fillType) {
		this.fillType = fillType;
	}

	public double getHeight() {
		return height;
	}
	@Override
	public double getMinX() {

		return getX();
	}
	public double getMinY() {
		return getY();
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	// #ifdef ANDROID
	public void paint(Canvas g, int _x, int _y, int theta, int zoom, int anchorX, int anchorY, AnimationGroupSupport parent, Paint paintObj) {
		// #else
		// # public void paint(Graphics g,int _x,int _y,int theta,int zoom,int
		// anchorX,int anchorY,AnimationGroupSupport parent) {
		// #endif

		Point p = Util.pointToRotate;
		p.setPoints(getX(), getY());
		Util.rotatePoint(p, anchorX, anchorY, theta, zoom, this);
		int _w = Util.roundTheValues(Util.getScaleValue(getWidth(), zoom));
		int _h = Util.roundTheValues(Util.getScaleValue(getHeight(), zoom));
		if (getFillType() == FILL_TYPE && getBgColor() != -1) {
			// #ifdef ANDROID
			paintObj.setColor(Util.getColor(getBgColor()));
			Util.fillRect(Util.roundTheValues(p.getX() + _x), Util.roundTheValues(p.getY() + _y), _w, _h, g, paintObj);
			// #else
			// # g.setColor(Util.getColor(getBgColor()));
			// # g.fillRect(Util.roundTheValues(p.getX() + _x),
			// Util.roundTheValues(p.getY() + _y), _w , _h);
			// #endif

		}
		if (getBorderColor() != -1) {
			// #ifdef ANDROID
			paintObj.setColor(Util.getColor(getBorderColor()));
			Util.drawRectangle(g, Util.roundTheValues(p.getX() + _x), Util.roundTheValues(p.getY() + _y), _w, _h, getBorderThickness(), paintObj);
			// #else
			// # g.setColor(Util.getColor(getBorderColor()));
			// # Util.drawRectangle(g,Util.roundTheValues( p.getX() + _x),
			// Util.roundTheValues(p.getY() + _y),_w, _h, getBorderThickness());
			// #endif

		}
	}
	public void port(int xper, int yper) {
		setX(Util.getScaleValue(getX(), xper));
		setY(Util.getScaleValue(getY(), yper));
		setWidth(Util.getScaleValue(getWidth(), xper));
		setHeight(Util.getScaleValue(getHeight(), yper));
	}

	public String toString() {
		return "Rect: " + getId();
	}

	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(super.serialize());
		SerializeUtil.writeInt(bos, fillType, 1);
		SerializeUtil.writeDouble(bos, width);
		SerializeUtil.writeDouble(bos, height);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		super.deserialize(bis);
		fillType = SerializeUtil.readInt(bis, 1);
		width = SerializeUtil.readDouble(bis);
		height = SerializeUtil.readDouble(bis);
		bis.close();
		return null;
	}

	public int getClassCode() {
		return APSerilize.SHAPE_TYPE_APRECT;
	}
}
