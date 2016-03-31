/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.basicelements;

//import com.appon.gtantra.GTantra;
//import com.appon.miniframework.ResourceManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.animlib.AnimationGroupSupport;
import com.appon.animlib.core.GraphicsUtil;
import com.appon.animlib.util.APSerilize;
import com.appon.animlib.util.Point;
import com.appon.animlib.util.SerializeUtil;
import com.appon.animlib.util.Util;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//# import com.appon.animlibeditor.APAnimationGroup;
//# import com.appon.tool.ui.AppOnEditor;
//# import com.appon.tool.ui.customeditors.CustomCanvas;
//#endif
/**
 *
 * @author acer
 */
public class APModuleShape extends APShapeElement {

	private int groupId = -1;
	private int moduleId = -1;
	private boolean flipX = false;
	private boolean flipY = false;
	private double width = 0;
	private double height = 0;
	private double theta = 0;
	private int tinitColor = -1;
	private boolean init = false;
	// #ifdef ANDROID
	private Bitmap image;
	// #else
	// # private Image image;
	// #endif

	private int alpha = 255;
	private int origionalWidth;
	private int origionalHeight;
	public APModuleShape(int id) {
		super(id);
	}

	public void setOrigionalHeight(int origionalHeight) {
		this.origionalHeight = origionalHeight;
	}

	public void setOrigionalWidth(int origionalWidth) {
		this.origionalWidth = origionalWidth;
	}

	public int getOrigionalHeight() {
		return origionalHeight;
	}

	public int getOrigionalWidth() {
		return origionalWidth;
	}

	public boolean isInit() {
		return init;
	}

	// #ifdef ANDROID
	public Bitmap getImage() {
		return image;
	}
	// #else
	// # public Image getImage() {
	// # return image;
	// # }
	// #endif

	public void setInit(boolean init) {
		this.init = init;
	}

	public boolean isFlipY() {
		return flipY;
	}

	public boolean isFlipX() {
		return flipX;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getAlpha() {
		return alpha;
	}

	public int getTinitColor() {
		return tinitColor;
	}

	public void setTinitColor(int tinitColor) {
		this.tinitColor = tinitColor;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getTheta() {
		return theta;
	}

	// #ifdef DEKSTOP_TOOL
	// # private boolean init(AnimationGroupSupport group1)
	// # {
	// # APAnimationGroup group = null;
	// # if(group1 instanceof APAnimationGroup)
	// # {
	// # group = (APAnimationGroup)group1;
	// # }
	// # if(group == null || group.getImage(moduleId) == null)
	// # return false;
	// # if(init)
	// # return true;
	// # image= group.getImage(moduleId);
	// # init = true;
	// # return true;
	// # }
	// #endif

	public APModuleShape clone() {
		APModuleShape arc = new APModuleShape(-1);
		copyProperties(arc);
		arc.setGroupId(getGroupId());
		arc.setModuleId(getModuleId());
		arc.setFlipX(getFlipX());
		arc.setFlipY(getFlipY());
		arc.setWidth(getWidth());
		arc.setHeight(getHeight());
		arc.setTheta(getTheta());
		arc.setTinitColor(getTinitColor());
		arc.setAlpha(getAlpha());
		arc.setOrigionalHeight(getOrigionalHeight());
		arc.setOrigionalWidth(getOrigionalWidth());
		return arc;
	}
	public boolean getFlipY() {
		return flipY;
	}

	public int getGroupId() {
		return groupId;
	}

	public boolean getFlipX() {
		return flipX;
	}

	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}

	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}

	public int geGroupId() {
		return groupId;
	}

	public void setWidth(double width) {

		this.width = width;
	}

	public void setHeight(double height) {

		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	// #ifdef ANDROID
	public void paint(Canvas g, int _x, int _y, int theta, int zoom, int anchorX, int anchorY, AnimationGroupSupport parent, Paint paintObj) {
		// #else
		// # public void paint(Graphics g,int _x,int _y,int theta,int zoom,int
		// anchorX,int anchorY,AnimationGroupSupport parent) {
		// #endif

		// #ifdef DEKSTOP_TOOL
		// # if(AppOnEditor.varifyingModules)
		// # return;
		// #endif
		Point p = Util.pointToRotate;
		p.setPoints(getX(), getY());
		Util.rotatePoint(p, anchorX, anchorY, theta, zoom, this);
			// #ifdef ANDROID
		GraphicsUtil.drawImage(g, moduleId, parent, p.getX() + _x, p.getY() + _y, width, height, flipX, flipY, theta + this.theta, tinitColor, alpha);
	
		// #elifdef DEKSTOP_TOOL
		// # if(init(parent) && image != null)
		// # {
		// # GraphicsUtil.drawImage(g, image, _x + p.getX(), _y + p.getY(),
		// width, height, flipX, flipY, theta + this.theta,tinitColor,alpha);
		// # }else{
		// #
		// # g.setColor(Util.getColor(0xff0000));
		// # g.fillRect(CustomCanvas.roundTheValues(p.getX() + _x),
		// CustomCanvas.roundTheValues(p.getY() + _y),
		// CustomCanvas.roundTheValues(width) ,
		// CustomCanvas.roundTheValues(height));
		// #
		// # }
		// #endif

	}

	public String toString() {
		return "GModule: " + getId();
	}

	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(super.serialize());
		SerializeUtil.writeSignedInt(bos, groupId, 1);
		// #ifdef DEKSTOP_TOOL
		// # if(com.appon.tool.ui.AppOnEditor.isExporting)
		// # {
		// #
		// #
		// SerializeUtil.writeInt(bos,com.appon.tool.ui.AppOnEditorManager.getInst().getGroup().getProvider().getBitmapIndex(moduleId)
		// ,1);
		// # }else{
		// #
		// #endif
		SerializeUtil.writeInt(bos, moduleId, 1);
		// #ifdef DEKSTOP_TOOL
		// # }
		// #endif
		SerializeUtil.writeInt(bos, origionalWidth, 2);
		SerializeUtil.writeInt(bos, origionalHeight, 2);
		SerializeUtil.writeBoolean(bos, flipX);
		SerializeUtil.writeBoolean(bos, flipY);
		SerializeUtil.writeDouble(bos, width);
		SerializeUtil.writeDouble(bos, height);
		SerializeUtil.writeDouble(bos, theta);
		SerializeUtil.writeColor(bos, tinitColor);
		SerializeUtil.writeInt(bos, alpha, 1);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		super.deserialize(bis);
		groupId = SerializeUtil.readSignedInt(bis, 1);
		moduleId = SerializeUtil.readInt(bis, 1);
		origionalWidth = SerializeUtil.readInt(bis, 2);
		origionalHeight = SerializeUtil.readInt(bis, 2);
		flipX = SerializeUtil.readBoolean(bis);
		flipY = SerializeUtil.readBoolean(bis);
		width = SerializeUtil.readDouble(bis);
		height = SerializeUtil.readDouble(bis);
		theta = SerializeUtil.readDouble(bis);
		tinitColor = SerializeUtil.readColor(bis);
		alpha = SerializeUtil.readInt(bis, 1);
		// #ifdef ANDROID
		tinitColor = Util.getColorAlphaRemover(tinitColor);
		// #endif
		bis.close();
		return null;
	}

	public int getClassCode() {
		return APSerilize.SHAPE_TYPE_APMODULE;
	}

	@Override
	public double getMinX() {
		return getXDouble();
	}

	@Override
	public double getMinY() {
		return getYDouble();
	}

	@Override
	public void port(int xper, int yper) {
		setX(Util.getScaleValue(getX(), xper));
		setY(Util.getScaleValue(getY(), yper));
		setWidth(Util.getScaleValue(getWidth(), xper));
		setHeight(Util.getScaleValue(getHeight(), yper));
		setOrigionalWidth(Util.getScaleValue(getOrigionalWidth(), xper));
		setOrigionalHeight(Util.getScaleValue(getOrigionalHeight(), yper));
	}

}
