/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

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

import com.appon.gtantra.GraphicsUtil;

/**
 *
 * @author acer
 */
public class ERect extends EShape{
    private int fillType;
    private int width = 50, height = 50;

    public ERect(int id) {
        super(id);
    }
    public EShape clone() {
        ERect arc = new ERect(-1);
        copyProperties(arc);
        arc.setFillType(getFillType());
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    
    //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
    //#else
   //# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
    //#endif
   
        Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
        Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
        int _w = Util.getScaleValue(getWidth(), zoom);
        int _h = Util.getScaleValue(getHeight(), zoom);
        if(getFillType() == FILL_TYPE && getBgColor() != -1 )
        {
        	  //#ifdef ANDROID
        	  paintObj.setColor(Util.getColor(getBgColor()));
              GraphicsUtil.fillRect(p.getX() + _x, p.getY() + _y, _w , _h,g,paintObj);
        	   //#else
              //# g.setColor(Util.getColor(getBgColor()));
              //# g.fillRect(p.getX() + _x, p.getY() + _y, _w , _h);
        	   //#endif
           
        }
        if(getBorderColor() != -1 )
        {
        	  //#ifdef ANDROID
        	 paintObj.setColor(Util.getColor(getBorderColor()));
             Util.drawRectangle(g, p.getX() + _x, p.getY() + _y,_w, _h, getBorderThickness(),paintObj);
        	   //#else
            //# g.setColor(Util.getColor(getBorderColor()));
             //# Util.drawRectangle(g, p.getX() + _x, p.getY() + _y,_w, _h, getBorderThickness());
        	   //#endif

           
        }
    }
    public void port(int xper, int yper) {
        setX(Util.getScaleValue(getX(), xper));
        setY(Util.getScaleValue(getY(), yper));
        setWidth(Util.getScaleValue(getWidth(), xper));
        setHeight(Util.getScaleValue(getHeight(), yper));
    }
    
    public String toString() {
        return "Rect: "+getId();
    }
     
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeInt(bos,fillType  ,1);
        com.appon.miniframework.Util.writeInt(bos,width  ,2);
        com.appon.miniframework.Util.writeInt(bos,height  ,2);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       fillType =  com.appon.miniframework.Util.readInt(bis, 1);
       width =  com.appon.miniframework.Util.readInt(bis, 2);
       height =  com.appon.miniframework.Util.readInt(bis, 2);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_RECT;
    }
    public int getScaledX(int zoom,int _x){
    	 int scaledX=0;
    	 Point p = Util.pointToRotate;
         p.setPoints( getX(), getY());
         Util.rotatePoint(p, 0, 0, 0,zoom,this);
         int _w = Util.getScaleValue(getWidth(), zoom);
         int _h = Util.getScaleValue(getHeight(), zoom);
         scaledX = p.getX()+_x;
       return scaledX;
    }
    public int getScaledY(int zoom, int  _y){
    	int scaledY=0;
    	Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
        Util.rotatePoint(p, 0, 0, 0,zoom,this);
        int _w = Util.getScaleValue(getWidth(), zoom);
        int _h = Util.getScaleValue(getHeight(), zoom);
        scaledY = p.getY()+_y;		
		return scaledY;
    }
}
