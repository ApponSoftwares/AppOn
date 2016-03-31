/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.basicelements;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Stroke;
//#elifdef ANDROID
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Canvas;
import android.graphics.Paint;

//#endif



import com.appon.animlib.AnimationGroupSupport;
import com.appon.animlib.util.SerializeUtil;
import com.appon.animlib.util.Serilizable;
import com.appon.animlib.util.Util;

/**
 *
 * @author acer
 */
public abstract class APShapeElement implements Serilizable {
	private double x, y;
	private int id;
	public static final int STROKE_TYPE = 0;
	public static final int FILL_TYPE = 1;
	private int borderColor;
	private int bgColor;
	private int borderThickness = 1;
	// #ifdef DEKSTOP_TOOL
	// # Stroke backupStroke;
	// #endif

	private boolean visible = true;
	public APShapeElement(int id) {
		this.id = id;
	}
	public abstract APShapeElement clone();
	public abstract void port(int xper, int yper);

	public void copyProperties(APShapeElement shape) {
		shape.setId(getId());
		shape.setX(getX());
		shape.setY(getY());
		shape.setBorderColor(getBorderColor());
		shape.setBgColor(getBgColor());
		shape.setVisible(isVisible());
		shape.setBorderThickness(getBorderThickness());
	}
	public double getWidth() {
		return 0;
	}
	public double getHeight() {
		return 0;
	}
	public abstract double getMinX();
	public abstract double getMinY();

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return Util.roundTheValues(x);
	}

	public int getY() {
		return Util.roundTheValues(y);
	}

	public double getXDouble() {
		return x;
	}
	public double getYDouble() {
		return y;
	}
	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	public int getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
	}

	public int getBorderThickness() {
		return borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
	}

	// #ifdef ANDROID
	public abstract void paint(Canvas g, int _x, int _y, int theta, int zoom, int anchorX, int anchorY, AnimationGroupSupport parent, Paint paintObj);
	// #else
	// # public abstract void paint(Graphics g,int _x,int _y,int theta,int
	// zoom,int anchorX,int anchorY,AnimationGroupSupport parent);
	// #endif

	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		SerializeUtil.writeInt(bos, id, 2);
		SerializeUtil.writeDouble(bos, x);
		SerializeUtil.writeDouble(bos, y);
		SerializeUtil.writeColor(bos, borderColor);
		SerializeUtil.writeColor(bos, bgColor);
		SerializeUtil.writeInt(bos, borderThickness, 1);
		SerializeUtil.writeBoolean(bos, visible);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		id = SerializeUtil.readInt(bis, 2);
		x = SerializeUtil.readDouble(bis);
		y = SerializeUtil.readDouble(bis);
		borderColor = SerializeUtil.readColor(bis);
		bgColor = SerializeUtil.readColor(bis);
		borderThickness = SerializeUtil.readInt(bis, 1);
		visible = SerializeUtil.readBoolean(bis);
		bis.close();
		// #ifdef ANDROID
		borderColor = Util.getColorAlphaRemover(borderColor);
		bgColor = Util.getColorAlphaRemover(bgColor);
		// #endif
		return null;
	}
}
