/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//# import javax.microedition.lcdui.Image;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;
//#elifdef DEKSTOP_TOOL
//# import java.awt.BasicStroke;
//# import java.awt.Graphics;
//# import java.awt.Graphics2D;
//# import java.awt.Stroke;
//#endif

import com.appon.util.Serilizable;

/**
 *
 * @author acer
 */
public abstract class EShape implements Serilizable{
    private int x, y;
    private int id;
    public static final int STROKE_TYPE = 0;
    public static final int FILL_TYPE = 1;
    private int borderColor;
    private int bgColor;
    private int borderThickness = 1;
    //#ifdef DEKSTOP_TOOL
    //# Stroke backupStroke;
    //#endif
    private int rotate;
    private boolean visible = true;
    public EShape(int id) {
        this.id = id;
    }
    public abstract EShape clone();
    public abstract void port(int xper,int yper);
    
    public void copyProperties(EShape shape)
    {
        shape.setId(getId());
        shape.setX(getX());
        shape.setY(getY());
        shape.setBorderColor(getBorderColor());
        shape.setBgColor(getBgColor());
        shape.setRotate(getRotate());
        shape.setVisible(isVisible());
        shape.setBorderThickness(getBorderThickness());
    }
    public int getWidth()
    {
        return 0;
    }
    public int getHeight()
    {
        return 0;
    }
    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public int getX() {
        return x;
    }

    public int getY() {
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
//    public void applyThickness(Graphics g,int thickness)
//    {
//        ((Graphics2D)g).setStroke(new BasicStroke(getBorderThickness()));
//    }
//    public void backupStroke(Graphics g)
//    {
//        backupStroke = ((Graphics2D)g).getStroke();
//    }
//    public void restoreStroke(Graphics g)
//    {
//        ((Graphics2D)g).setStroke(backupStroke);
//    }
    
    //#ifdef ANDROID
    public abstract void  paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint p);
   //#else
     //# public abstract void  paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent);
   //#endif
    
    
    
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        com.appon.miniframework.Util.writeInt(bos,id,2);
        com.appon.miniframework.Util.writeSignedInt(bos,x,2);
        com.appon.miniframework.Util.writeSignedInt(bos,y,2);
        com.appon.miniframework.Util.writeColor(bos, borderColor);
        com.appon.miniframework.Util.writeColor(bos, bgColor);
        com.appon.miniframework.Util.writeInt(bos, borderThickness,1);
        com.appon.miniframework.Util.writeBoolean(bos,visible);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
//       id = com.appon.miniframework.Util.readInt(bis, 2);
       x = com.appon.miniframework.Util.readSignedInt(bis, 2);
       y = com.appon.miniframework.Util.readSignedInt(bis, 2);
       borderColor= com.appon.miniframework.Util.readColor(bis);
       bgColor= com.appon.miniframework.Util.readColor(bis);
       borderThickness = com.appon.miniframework.Util.readInt(bis, 1);
       visible = com.appon.miniframework.Util.readBoolean(bis);
       bis.close();
       return null;
    }
    
    public int getScaledX(int zoom,int _x){
    	int scaledX=0;
    			
		return scaledX;
    }
    public int getScaledY(int zoom, int _y){
    	int scaledY=0;
    			
		return scaledY;
    }
}
