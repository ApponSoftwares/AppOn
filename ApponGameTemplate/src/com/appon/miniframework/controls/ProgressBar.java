/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;

import java.io.ByteArrayInputStream;

//#ifdef DEKSTOP_TOOL
//# import com.appon.gtantra.GraphicsUtil;
//# import com.appon.miniframework.*;
//# import java.awt.Graphics;
//# import java.awt.Image;
//# import java.awt.Rectangle;
//# import java.io.ByteArrayOutputStream;
//#elifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.miniframework.Control;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.Settings;
import com.appon.miniframework.Util;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#endif
/**
 *
 * @author dell
 */
public class ProgressBar extends Control{

    private int progressBarColor = -1;
    private int maxProgress = 100;
    private int currentProgress =50 ;
    //#ifdef ANDROID
    private Bitmap progressImage;
    //#else
    //# private Image progressImage;
    //#endif
    private int infiniteProgressBarWidthPercent = 4;
    private int progressBarType;
    private static final int MIN_WIDTH_PERCENT = 3;
    private int imageResourceId = -1;
    private int currentX;
    public static final int PROGRESS_TYPE_PROGRESSIVE = 0;
    public static final int PROGRESS_TYPE_INFINITE = 1;
    private int updateSpeed = 1;
    public ProgressBar(int id) {
        super(id);
    }
   //#ifdef ANDROID
    public void paint(Canvas g,Paint paintObject) 
   //#else 
    //# public void paint(Graphics g) 
    //#endif
    {
       int clipX , clipY , clipWidth , clipHeight;
        //#ifdef DEKSTOP_TOOL
        //# Rectangle r = g.getClipBounds();
        //# clipX = (int)r.getX();
        //# clipY = (int)r.getY();
        //# clipWidth = (int)r.getWidth();
        //# clipHeight = (int)r.getHeight();
        //#elifdef J2ME
       //# clipX = g.getClipX();
       //# clipY = g.getClipY();
       //# clipWidth = g.getClipWidth();
       //# clipHeight = g.getClipHeight();
         //#else
        g.save();
        //#endif
       if(progressBarType == PROGRESS_TYPE_INFINITE)
       {
           //#ifdef ANDROID
            g.clipRect(0, 0,  getBoundWidth(), getBoundHeight());
           //#else 
           //# g.clipRect(0, 0, getBoundWidth(), getBoundHeight());
           //#endif
           int w = (getBoundWidth() * infiniteProgressBarWidthPercent) / 100; 
           if(w < MIN_WIDTH_PERCENT)
           {
               w = MIN_WIDTH_PERCENT;
           }
           //#ifdef ANDROID
           g.clipRect(currentX, 0, currentX + w, getBoundHeight());
           //#else 
           //# g.clipRect(currentX, 0, w, getBoundHeight());
           //#endif
           currentX+= getUpdateSpeed();
           if(currentX > getBoundWidth())
           {
               currentX = 0;
           }
         
           if(progressBarColor != -1)
           {
               //#ifdef ANDROID
               paintObject.setColor(Util.getColor(getProgressBarColor()));
               com.appon.gtantra.GraphicsUtil.fillRect(currentX, 0, w, getBoundHeight(),g,paintObject);
               //#else
               //# g.setColor(Util.getColor(getProgressBarColor()));
               //# g.fillRect(currentX, 0, w, getBoundHeight());
               //#endif
           }
           if(progressImage != null)
           {
               //#ifdef ANDROID
               fillImageRect(g, currentX, 0, w, getBoundHeight(), progressImage,paintObject);
               //#else
               //# fillImageRect(g, currentX, 0, w, getBoundHeight(), progressImage);
               //#endif
           }
           //#ifdef ANDROID
            g.restore();
           //#else 
           //# g.setClip(clipX,clipY,clipWidth,clipHeight);
           //#endif
           
       }else{
           
           int per = (currentProgress * 100) / maxProgress;
           currentX = (getBoundWidth() * per) / 100; 
           //#ifdef ANDROID
            g.clipRect(0, 0, currentX , getBoundHeight());
           //#else 
           //# g.clipRect(0, 0, currentX, getBoundHeight());
           //#endif
           if(progressBarColor != -1)
           {
               //#ifdef ANDROID
               paintObject.setColor(Util.getColor(getProgressBarColor()));
               com.appon.gtantra.GraphicsUtil.fillRect(0, 0,currentX , getBoundHeight(),g,paintObject);
               //#else
               //# g.setColor(Util.getColor(getProgressBarColor()));
               //# g.fillRect(0, 0,currentX , getBoundHeight());
               //#endif
           }
           if(progressImage != null)
           {
               //#ifdef ANDROID
               fillImageRect(g, 0,0,currentX, getBoundHeight(), progressImage,paintObject);
               //#else
               //# fillImageRect(g, 0,0,currentX, getBoundHeight(), progressImage);
               //#endif
           }
           //#ifdef ANDROID
            g.restore();
           //#else 
           //# g.setClip(clipX,clipY,clipWidth,clipHeight);
           //#endif
       }
    }
 //#ifdef ANDROID
    private void fillImageRect(Canvas g,int x,int y,int width,int height,Bitmap image, Paint paintObject)
    //#else
    //# private void fillImageRect(Graphics g,int x,int y,int width,int height,Image image)
    //#endif
    {
        int _maxW = x + width + Util.getImageWidth(image);
        int w = Util.getImageWidth(image);
        int _maxH = y + height + Util.getImageHeight(image);
        int h = Util.getImageHeight(image);
        for (int i = x; i <= _maxW; i+= w) {
            for (int j = y; j <= _maxH; j+= h) {
            	Util.drawImage(g, image, i, j,paintObject);
            }
        }
    }
    
    public void showNotify() {
        super.showNotify();
        currentX = 0;
    }

    public void setUpdateSpeed(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }

    public int getUpdateSpeed() {
        return updateSpeed;
    }

    
    public int getClassCode() {
        return MenuSerilize.CONTROL_PROGRESS_BAR;
    }

    public int getProgressBarColor() {
        return progressBarColor;
    }

    public void setProgressBarColor(int scrollBarColor) {
        this.progressBarColor = scrollBarColor;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    

    public int getInfiniteProgressBarWidthPercent() {
        return infiniteProgressBarWidthPercent;
    }

    public void setInfiniteProgressBarWidthPercent(int infiniteProgressBarWidthPercent) {
        this.infiniteProgressBarWidthPercent = infiniteProgressBarWidthPercent;
    }

    public int getProgressBarType() {
        return progressBarType;
    }

    public void setProgressBarType(int progressBarType) {
        this.progressBarType = progressBarType;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    //#ifdef ANDROID
    public Bitmap getProgressImage() {
        return progressImage;
    }

    public void setProgressImage(Bitmap progressImage) {
        this.progressImage = progressImage;
    }
    //#else
    //# public Image getProgressImage() {
        //# return progressImage;
    //# }
//# 
    //# public void setProgressImage(Image progressImage) {
        //# this.progressImage = progressImage;
    //# }
    //#endif
     public byte[] serialize() throws Exception {
         //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# bos.write(super.serialize());
         //# Util.writeInt(bos, getProgressBarType(), 1);
         //# Util.writeColor(bos, getProgressBarColor());
         //# Util.writeSignedInt(bos, getImageResourceId(),2);
         //# Util.writeInt(bos, getMaxProgress(), 2);
         //# Util.writeInt(bos, getInfiniteProgressBarWidthPercent(), 1);
         //# Util.writeInt(bos, getUpdateSpeed(), 1);
         //# if(Settings.VERSION_NUMBER_FOUND >= 3)
         //# {
           //# Util.writeInt(bos, getCurrentProgress(), 1);
         //# }
         //# bos.flush();
         //# byte data[] = bos.toByteArray();
         //# bos.close();
         //# bos = null;
         //# return data;
          //#else
	return null;
        //#endif
    }


    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
        super.deserialize(bis);
        setProgressBarType(Util.readInt(bis, 1));
        setProgressBarColor(Util.readColor(bis));
        setImageResourceId(Util.readSignedInt(bis, 2));
        setProgressImage(ResourceManager.getInstance().getImageResource(getImageResourceId()));
        setMaxProgress(Util.readInt(bis, 2));
        setInfiniteProgressBarWidthPercent(Util.readInt(bis, 1));
        setUpdateSpeed(Util.readInt(bis, 1));
        if(Settings.VERSION_NUMBER_FOUND >= 3)
        {
            setCurrentProgress(Util.readInt(bis, 1));
        }
        bis.close();
        return null;
    }
}
