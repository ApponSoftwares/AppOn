/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

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

/**
 *
 * @author acer
 */
public class ELine extends EShape{
 
    int x2 = 50 , y2 = 25;
    public ELine(int id) {
        super(id);
    }
    public EShape clone() {
        ELine arc = new ELine(-1);
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
    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
    //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
    //#else
    //# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
    //#endif
        if(getBorderColor() != -1 )
        {
        	 //#ifdef ANDROID
        	paintObj.setColor(Util.getColor(getBorderColor()));
        	 //#else
        	//# g.setColor(Util.getColor(getBorderColor()));
        	 //#endif
             
             Point p = Util.pointToRotate;
             p.setPoints( getX(), getY());
             Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
             int newX1 = p.getX();
             int newY1 = p.getY();
             p.setPoints( getX2(), getY2());
             Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
             //#ifdef ANDROID
             Util.drawThickLine(g, newX1 + _x, newY1 + _y, p.getX()+ _x, p.getY() + _y, getBorderThickness(),paintObj);
             //#else
             //# Util.drawThickLine(g, newX1 + _x, newY1 + _y, p.getX()+ _x, p.getY() + _y, getBorderThickness());
             //#endif
        }
    }
    
    public String toString() {
        return "Line: "+getId();
    }
     
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeSignedInt(bos,x2  ,2);
        com.appon.miniframework.Util.writeSignedInt(bos,y2  ,2);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       x2 =  com.appon.miniframework.Util.readSignedInt(bis, 2);
       y2 =  com.appon.miniframework.Util.readSignedInt(bis, 2);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_LINE;
    }
}
