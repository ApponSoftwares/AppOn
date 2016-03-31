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
public class APArcShape extends APShapeElement {
	private int fillType;
	private double width = 50, height = 50;
	int startAngle = 0;
	int endAngle = 360;
	public APArcShape(int id) {
		super(id);
		setBorderColor(0);
	}
	public APShapeElement clone() {
		APArcShape arc = new APArcShape(-1);
		copyProperties(arc);
		arc.setFillType(getFillType());
		arc.setStartAngle(getStartAngle());
		arc.setEndAngle(getEndAngle());
		arc.setWidth(getWidth());
		arc.setHeight(getHeight());
		return arc;
	}

	public int getFillType() {
		return fillType;
	}

	public void setFillType(int fillType) {
		this.fillType = fillType;
	}

	@Override
	public void setBorderColor(int borderColor) {
		super.setBorderColor(borderColor);
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
	public void paint(Canvas g, int _x, int _y, int theta, int zoom, int anchorX, int anchorY, AnimationGroupSupport parent, Paint paintObject) {
		// #else
		// # public void paint(Graphics g, int _x, int _y,int theta,int zoom,int
		// anchorX,int anchorY,AnimationGroupSupport parent) {
		// #endif

		Point p = Util.pointToRotate;
		p.setPoints(getX(), getY());
		Util.rotatePoint(p, anchorX, anchorY, theta, zoom, this);
		int _w = Util.roundTheValues(Util.getScaleValue(getWidth(), zoom));
		int _h = Util.roundTheValues(Util.getScaleValue(getHeight(), zoom));

		if (getFillType() == FILL_TYPE && getBgColor() != -1) {
			// #ifdef ANDROID
			paintObject.setColor(Util.getColor(getBgColor()));
			Util.fillArc(g, _x + p.getX(), _y + p.getY(), _w, _h, startAngle, endAngle, paintObject);
			// #else
			// # g.setColor(Util.getColor(getBgColor()));
			// # g.fillArc( Util.roundTheValues(_x + p.getX()) ,
			// Util.roundTheValues(_y + p.getY()) , _w ,
			// _h,startAngle,endAngle);
			// #endif

		}
		if (getBorderColor() != -1) {
			// #ifdef ANDROID
			paintObject.setColor(Util.getColor(getBorderColor()));
			Util.drawArc(g, _x + p.getX(), _y + p.getY(), _w, _h, startAngle, endAngle, getBorderThickness(), paintObject);
			// #else
			// # g.setColor(Util.getColor(getBorderColor()));
			// # Util.drawArc(g, Util.roundTheValues(_x + p.getX()) ,
			// Util.roundTheValues(_y + p.getY()) , _w
			// ,_h,startAngle,endAngle,getBorderThickness());
			// #endif

		}
	}

	public void setEndAngle(int endAngle) {

		this.endAngle = endAngle;
	}

	public void setStartAngle(int startAngle) {
		this.startAngle = startAngle;
	}

	public int getEndAngle() {
		return endAngle;
	}

	public int getStartAngle() {
		return startAngle;
	}

	public void port(int xper, int yper) {
		setX(Util.getScaleValue(getX(), xper));
		setY(Util.getScaleValue(getY(), yper));
		setWidth(Util.getScaleValue(getWidth(), xper));
		setHeight(Util.getScaleValue(getHeight(), yper));
	}

	public String toString() {
		return "Arc: " + getId();
	}

	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(super.serialize());
		SerializeUtil.writeInt(bos, fillType, 1);
		SerializeUtil.writeDouble(bos, width);
		SerializeUtil.writeDouble(bos, height);
		SerializeUtil.writeSignedInt(bos, startAngle, 2);
		SerializeUtil.writeSignedInt(bos, endAngle, 2);
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
		startAngle = SerializeUtil.readSignedInt(bis, 2);
		endAngle = SerializeUtil.readSignedInt(bis, 2);
		bis.close();
		return null;
	}

	public int getClassCode() {
		return APSerilize.SHAPE_TYPE_APARC;
	}
}
