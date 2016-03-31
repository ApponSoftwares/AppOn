/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//#endif
//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//# import javax.microedition.lcdui.Image;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.ResourceManager;

/**
 *
 * @author acer
 */
public class EImage extends EShape{
 
    
    private int imageId;
    private int fillType;
    public EImage(int id) {
        super(id);
    }
    public EShape clone() {
        EImage arc = new EImage(-1);
        copyProperties(arc);
        arc.setImage(getImage());
        arc.setImageId(getImageId());
        arc.setFillType(getFillType());
        return arc;
    }

    public int getImageId() {
        return imageId;
    }
   
    public int getWidth()
    {
        if(image == null)
            return 50;
        return com.appon.miniframework.Util.getImageWidth(image) ;
    }
    public int getHeight()
    {
        if(image == null)
            return 50;
        return com.appon.miniframework.Util.getImageHeight(image) ;
    }
     public void port(int xper, int yper) {
        setX(Util.getScaleValue(getX(), xper));
        setY(Util.getScaleValue(getY(), yper));
    }
    //#ifndef ANDROID
    //# private Image image;
  //# public void setImage(Image image) {
  //# this.image = image;
  //# }
  //# 
  //# public Image getImage() {
  //# return image;
  //# }
    //#else
    private Bitmap image;
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
    //#endif
   

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getFillType() {
        return fillType;
    }

    public void setFillType(int fillType) {
        this.fillType = fillType;
    }
    //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
   //#else
     //# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
   //#endif
    
    
        if(image == null)
            return;
        Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
//        Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
//        com.appon.miniframework.Util.drawImage(g, image, p.getX() + _x, p.getY() + _y,paintObj);
        if(zoom==0)
        	GraphicsUtil.paintRoatateImage(g, image, p.getX() + _x, p.getY() + _y, theta, paintObj);
        else
        	GraphicsUtil.paintRoatateImage(g, image, p.getX() + _x, p.getY() + _y, theta,true,zoom,paintObj);
        
        if(getFillType() == FILL_TYPE && getBgColor() != -1 )
        {
        	  //#ifdef ANDROID
        	paintObj.setColor(Util.getColor(getBgColor()));
            GraphicsUtil.fillRect(p.getX() + _x, p.getY() + _y, com.appon.miniframework.Util.getImageWidth(image)  , com.appon.miniframework.Util.getImageHeight(image),g,paintObj);
        	   //#else
        	  //# g.setColor(Util.getColor(getBgColor()));
              //# g.fillRect(p.getX() + _x, p.getY() + _y, com.appon.miniframework.Util.getImageWidth(image)  , com.appon.miniframework.Util.getImageHeight(image));
        	   //#endif
          
        }
        if(getBorderColor() != -1)
        {
        	  //#ifdef ANDROID
        	paintObj.setColor(Util.getColor(getBgColor()));
        	GraphicsUtil.drawRect(p.getX() + _x, p.getY() + _y, com.appon.miniframework.Util.getImageWidth(image)  , com.appon.miniframework.Util.getImageHeight(image), g, paintObj);
        	   //#else
        	 //# g.setColor(Util.getColor(getBorderColor()));
             //# Util.drawRectangle(g,p.getX() + _x, p.getY() + _y,com.appon.miniframework.Util.getImageWidth(image), com.appon.miniframework.Util.getImageHeight(image), getBorderThickness());
        	   //#endif
           
        }
    }
    
    public String toString() {
        return "Image: "+getId();
    }
      
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeSignedInt(bos,imageId  ,1);
        com.appon.miniframework.Util.writeInt(bos,fillType  ,1);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       imageId =  com.appon.miniframework.Util.readSignedInt(bis, 1);
       setImage(ResourceManager.getInstance().getImageResource(imageId));
       fillType  =  com.appon.miniframework.Util.readInt(bis, 1);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_IMAGE;
    }
}
