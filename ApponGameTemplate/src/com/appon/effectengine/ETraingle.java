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

/**
 *
 * @author acer
 */
// x and y not used for this class
public class ETraingle extends EShape{
    private int fillType;
    private int xPoints[];
    private int yPoints[];
    private int tmpXPoints[];
    private int tmpYPoints[];
    public ETraingle(int id) {
        super(id);
    }
    public EShape clone() {
        ETraingle arc = new ETraingle(-1);
        copyProperties(arc);
        arc.setFillType(getFillType());
        arc.setXPoints(getXPoints());
        arc.setYPoints(getYPoints());
        return arc;
    }
    public void setXPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public void setYPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }

    public int[] getYPoints() {
        return yPoints;
    }

    public int[] getXPoints() {
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
    //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
    //#else
    //# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
    //#endif
    
        if(tmpXPoints == null || tmpXPoints.length != xPoints.length)
        {
            tmpXPoints = new int[xPoints.length];
        }
        if(tmpYPoints == null || tmpYPoints.length != yPoints.length)
        {
            tmpYPoints = new int[yPoints.length];
           
        }
        for (int i = 0; i < xPoints.length; i++) {
            Point p = Util.pointToRotate;
            p.setPoints( xPoints[i],  yPoints[i]);
            Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
            tmpXPoints[i] = p.getX() + _x;
            tmpYPoints[i] = p.getY() + _y;
        }
        if(getFillType() == FILL_TYPE && getBgColor() != -1 )
        {
        	  //#ifdef ANDROID
               paintObj.setColor(Util.getColor(getBgColor()));
               Util.fillPolygon(g, tmpXPoints, tmpYPoints,paintObj);
        	   //#else
                //# g.setColor(Util.getColor(getBgColor()));
               //# Util.fillPolygon(g, tmpXPoints, tmpYPoints);
        	   //#endif
        }
        if(getBorderColor() != -1 )
        {
        	   //#ifdef ANDROID
        	   paintObj.setColor(Util.getColor(getBorderColor()));
               Util.drawPolygon(g, tmpXPoints, tmpYPoints,getBorderThickness(),paintObj);
        	   //#else
                //# g.setColor(Util.getColor(getBorderColor()));
               //# Util.drawPolygon(g, tmpXPoints, tmpYPoints,getBorderThickness());
        	   //#endif
           
        }
    }

    
    public String toString() {
        return "Traingle: "+getId();
    }
     
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       fillType =  com.appon.miniframework.Util.readInt(bis, 1);
       xPoints =  (int[])EffectsSerilize.getInstance().deserialize(bis, null);
       yPoints =  (int[])EffectsSerilize.getInstance().deserialize(bis, null);
       bis.close();
       return null;
    }
 
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeInt(bos,fillType  ,1);
        EffectsSerilize.getInstance().serialize(xPoints, bos);
        EffectsSerilize.getInstance().serialize(yPoints, bos);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_TRAINGLE;
    }
}
