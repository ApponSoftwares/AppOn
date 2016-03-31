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

import com.appon.gtantra.GraphicsUtil;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif
/**
 *
 * @author acer
 */
public class EArc extends EShape{
    
    private int startAngle = 0, endAngle = 360;
    private int fillType;
    private int width =50 , height = 50;
    public EArc(int id) {
        super(id);
    }

    
    public EShape clone() {
        EArc arc = new EArc(-1);
        copyProperties(arc);
        arc.setStartAngle(getStartAngle());
        arc.setEndAngle(getEndAngle());
        arc.setFillType(getFillType());
        arc.setWidth(getWidth());
        arc.setHeight(getHeight());
        return arc;
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

    public int getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

    public int getFillType() {
        return fillType;
    }

    public void setFillType(int fillType) {
        this.fillType = fillType;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }
   
  //#ifdef ANDROID
    public void paint(Canvas g, int _x, int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObject) {
   //#else
     //# public void paint(Graphics g, int _x, int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
   //#endif
    	
        Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
        Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
        int _w = Util.getScaleValue(getWidth(), zoom);
        int _h = Util.getScaleValue(getHeight(), zoom);
        if(getFillType() == FILL_TYPE && getBgColor() != -1 )
        {
        	//#ifdef ANDROID
        	 paintObject.setColor(Util.getColor(getBgColor()));
        	 GraphicsUtil.fillArc(g, _x + p.getX() ,_y + p.getY() , _w , _h,startAngle,endAngle,paintObject);
           //#else
            //# g.setColor(Util.getColor(getBgColor()));
            //# g.fillArc(_x + p.getX() ,_y + p.getY() , _w , _h,startAngle,endAngle);
           //#endif
           
        }
        if(getBorderColor() != -1 )
        {
          //#ifdef ANDROID
        	paintObject.setColor(Util.getColor(getBorderColor()));
            Util.drawArc(g, _x + p.getX() ,_y + p.getY() , _w ,_h,startAngle,endAngle,getBorderThickness(),paintObject);
          //#else
           //# g.setColor(Util.getColor(getBorderColor()));
           //# Util.drawArc(g, _x + p.getX() ,_y + p.getY() , _w ,_h,startAngle,endAngle,getBorderThickness());
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
        return "Arc: "+getId();
    }

   
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeSignedInt(bos,startAngle,2);
        com.appon.miniframework.Util.writeSignedInt(bos,endAngle,2);
        com.appon.miniframework.Util.writeInt(bos,fillType,1);
        com.appon.miniframework.Util.writeInt(bos,width,2);
        com.appon.miniframework.Util.writeInt(bos,height,2);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       startAngle =  com.appon.miniframework.Util.readSignedInt(bis, 2);
       endAngle =  com.appon.miniframework.Util.readSignedInt(bis, 2);
       fillType =  com.appon.miniframework.Util.readInt(bis, 1);
       width =  com.appon.miniframework.Util.readInt(bis, 2);
       height =  com.appon.miniframework.Util.readInt(bis, 2);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_EARC;
    }
}
