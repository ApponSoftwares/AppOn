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
// x and y not used for this class
public class APPolygonShape extends APShapeElement {
	private int fillType;
	private double xPoints[];
	private double yPoints[];
	private double tmpXPoints[];
	private double tmpYPoints[];
	public APPolygonShape(int id) {
		super(id);
	}

	@Override
	public double getMinX() {

		return Util.getMinimumNumber(xPoints);
	}
	public double getMinY() {
		return Util.getMinimumNumber(yPoints);
	}

	@Override
	public double getWidth() {
		return Math.abs(Util.getMaximumNumber(xPoints) - Util.getMinimumNumber(xPoints));
	}
	@Override
	public double getHeight() {
		return Math.abs(Util.getMaximumNumber(yPoints) - Util.getMinimumNumber(yPoints));
	}
	public APShapeElement clone() {
		APPolygonShape arc = new APPolygonShape(-1);
		copyProperties(arc);
		arc.setFillType(getFillType());
		arc.setXPoints(getXPoints());
		arc.setYPoints(getYPoints());
		return arc;
	}
	public void setXPoints(int[] xPoints) {
		this.xPoints = new double[xPoints.length];
		for (int i = 0; i < xPoints.length; i++) {
			this.xPoints[i] = xPoints[i];
		}

	}

	public void setYPoints(int[] yPoints) {
		this.yPoints = new double[yPoints.length];
		for (int i = 0; i < yPoints.length; i++) {
			this.yPoints[i] = yPoints[i];
		}
	}
	public void setXPoints(double[] xPoints) {
		this.xPoints = xPoints;
	}

	public void setYPoints(double[] yPoints) {
		this.yPoints = yPoints;
	}

	public double[] getYPoints() {
		return yPoints;
	}

	public double[] getXPoints() {
		return xPoints;
	}

	public int getFillType() {
		return fillType;
	}

	public void setFillType(int fillType) {
		this.fillType = fillType;
	}
	public void port(int xper, int yper) {
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] = Util.getScaleValue(xPoints[i], xper);
			yPoints[i] = Util.getScaleValue(yPoints[i], yper);
		}
	}
	public double[] getTmpXPoints() {
		return tmpXPoints;
	}

	public double[] getTmpYPoints() {
		return tmpYPoints;
	}
	// #ifdef ANDROID
	public void paint(Canvas g, int _x, int _y, int theta, int zoom, int anchorX, int anchorY, AnimationGroupSupport parent, Paint paintObj) {
		// #else
		// # public void paint(Graphics g,int _x,int _y,int theta,int zoom,int
		// anchorX,int anchorY,AnimationGroupSupport parent) {
		// #endif

		if (tmpXPoints == null || tmpXPoints.length != xPoints.length) {
			tmpXPoints = new double[xPoints.length];
		}
		if (tmpYPoints == null || tmpYPoints.length != yPoints.length) {
			tmpYPoints = new double[yPoints.length];

		}
		for (int i = 0; i < yPoints.length; i++) {
			Point p = Util.pointToRotate;
			p.setPoints(xPoints[i], yPoints[i]);
			Util.rotatePoint(p, anchorX, anchorY, theta, zoom, this);
			tmpXPoints[i] = p.getX() + _x;
			tmpYPoints[i] = p.getY() + _y;
		}
		if (getFillType() == FILL_TYPE && getBgColor() != -1) {
			// #ifdef ANDROID
			paintObj.setColor(Util.getColor(getBgColor()));
			Util.fillPolygon(g, tmpXPoints, tmpYPoints, paintObj);
			// #else
			// # g.setColor(Util.getColor(getBgColor()));
			// # Util.fillPolygon(g, tmpXPoints, tmpYPoints);
			// #endif

		}
		if (getBorderColor() != -1) {
			// #ifdef ANDROID
			paintObj.setColor(Util.getColor(getBorderColor()));
			Util.drawPolygon(g, tmpXPoints, tmpYPoints, getBorderThickness(), paintObj);
			// #else
			// # g.setColor(Util.getColor(getBorderColor()));
			// # Util.drawPolygon(g, tmpXPoints,
			// tmpYPoints,getBorderThickness());
			// #endif

		}
	}

	public String toString() {
		return "Poly: " + getId();
	}

	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		super.deserialize(bis);
		fillType = SerializeUtil.readInt(bis, 1);
		xPoints = (double[]) APSerilize.getInstance().deserialize(bis, null);
		yPoints = (double[]) APSerilize.getInstance().deserialize(bis, null);
		bis.close();
		return null;
	}

	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(super.serialize());
		SerializeUtil.writeInt(bos, fillType, 1);
		APSerilize.getInstance().serialize(xPoints, bos);
		APSerilize.getInstance().serialize(yPoints, bos);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	public int getClassCode() {
		return APSerilize.SHAPE_TYPE_APPOLYGON;
	}
}
