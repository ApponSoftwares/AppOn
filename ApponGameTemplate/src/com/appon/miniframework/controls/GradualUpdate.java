/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;

import java.io.ByteArrayInputStream;

//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//#elifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//# import javax.microedition.lcdui.Image;
//#elifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
//#endif

import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.Control;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.Util;

/**
 *
 * @author acer
 */
public class GradualUpdate extends Control{

    //#ifdef ANDROID
   private Bitmap leftFilled;
   private Bitmap leftEmpty;
   private Bitmap centerFilled;
   private Bitmap centerEmpty;
   //#else
    //# private Image leftFilled;
    //# private Image leftEmpty;
    //# private Image centerFilled;
    //# private Image centerEmpty;
    //#endif
   
    
    private int currentUpgrade;
    private int maxUpgrade;

    private int leftFilledImageId;
    private int leftEmptyImageId;
    private int centerFilledImageId;
    private int centerEmptyImageId;
    public GradualUpdate(int id) {
        super(id);
    }
    
    
       //#ifdef ANDROID
    public void paint(Canvas g, Paint paintObject) 
    //#else
    //# public void paint(Graphics g) 
    //#endif
    {
        if(leftFilled == null || leftEmpty == null || centerFilled == null || centerEmpty == null)
            return;
        int _x = getLeftInBound();
        int _y = getTopInBound();
        if(currentUpgrade > 0)
            Util.drawImage(g, leftFilled, _x, _y,paintObject);
        else
            Util.drawImage(g, leftEmpty, _x, _y,paintObject);
        int count = (maxUpgrade - 2);
        _x += Util.getImageWidth(leftFilled);
        int value = 1;
        for (int i = 0; i < count && (count > 0); i++) {
            if(value >= currentUpgrade)
                Util.drawImage(g, centerEmpty, _x, _y,paintObject);
            else
                Util.drawImage(g, centerFilled, _x, _y,paintObject);
            value ++;
            _x +=  Util.getImageWidth(centerFilled);
        }
         if(maxUpgrade > currentUpgrade)
         {
              GraphicsUtil.drawRegion(g, leftEmpty, _x, _y, GraphicsUtil.MIRROR, 0,paintObject);
         }else{
             GraphicsUtil.drawRegion(g, leftFilled, _x, _y, GraphicsUtil.MIRROR, 0,paintObject);
         }
    }

    
    public int getPreferredWidth() {
        int _w = getLeftInBound() + getRightInBound();
        if(leftEmpty != null)
        {
            _w +=  (Util.getImageWidth(leftEmpty) * 2);
        }
        if(centerEmpty != null && maxUpgrade > 2)
        {
              _w +=  (Util.getImageWidth(centerEmpty) * (maxUpgrade - 2));
        }
        return _w;
    }
    
    public int getPreferredHeight() {
        int _w = getTopInBound() + getBottomInBound();
        if(leftEmpty != null)
        {
            _w +=  Util.getImageHeight(leftEmpty);
        }
        return _w;
    }
  
    
    public int getClassCode() {
        return MenuSerilize.CONTROL_GRADUAL_UPDATE_TYPE;
    }

    
    public byte[] serialize() throws Exception {
         //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# bos.write(super.serialize());
         //# Util.writeSignedInt(bos, getLeftFilledImageId(),2);
         //# Util.writeSignedInt(bos, getLeftEmptyImageId(),2);
         //# Util.writeSignedInt(bos, getCenterEmptyImageId(),2);
         //# Util.writeSignedInt(bos, getCenterFilledImageId(),2);
         //# Util.writeInt(bos, getMaxUpgrade(), 1);
         //# Util.writeInt(bos, getCurrentUpgrade(), 1);
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
        setLeftFilledImageId(Util.readSignedInt(bis,2));
        
        setLeftFilled(ResourceManager.getInstance().getImageResource(getLeftFilledImageId()));
        
        setLeftEmptyImageId(Util.readSignedInt(bis,2));
        
         setLeftEmpty(ResourceManager.getInstance().getImageResource(getLeftEmptyImageId()));
         
        setCenterEmptyImageId(Util.readSignedInt(bis,2));
        
        setCenterEmpty(ResourceManager.getInstance().getImageResource(getCenterEmptyImageId()));
        
        setCenterFilledImageId(Util.readSignedInt(bis,2));
        setCenterFilled(ResourceManager.getInstance().getImageResource(getCenterFilledImageId()));
        setMaxUpgrade(Util.readInt(bis, 1));
        setCurrentUpgrade(Util.readInt(bis, 1));
        bis.close();
        return null;
    }
   

  

    public int getCurrentUpgrade() {
        return currentUpgrade;
    }

    public void setCurrentUpgrade(int currentUpgrade) {
        this.currentUpgrade = currentUpgrade;
    }


   

    public int getMaxUpgrade() {
        return maxUpgrade;
    }

    public void setMaxUpgrade(int maxUpgrade) {
        this.maxUpgrade = maxUpgrade;
    }

    public int getCenterEmptyImageId() {
        return centerEmptyImageId;
    }

    public void setCenterEmptyImageId(int centerEmptyImageId) {
        this.centerEmptyImageId = centerEmptyImageId;
    }

    public int getCenterFilledImageId() {
        return centerFilledImageId;
    }

    public void setCenterFilledImageId(int centerFilledImageId) {
        this.centerFilledImageId = centerFilledImageId;
    }

    public int getLeftEmptyImageId() {
        return leftEmptyImageId;
    }

    public void setLeftEmptyImageId(int leftEmptyImageId) {
        this.leftEmptyImageId = leftEmptyImageId;
    }

    public int getLeftFilledImageId() {
        return leftFilledImageId;
    }

    public void setLeftFilledImageId(int leftFilledImageId) {
        this.leftFilledImageId = leftFilledImageId;
    }

    
    public String toString() {
        return "GradualUpdate-"+getId();
    }
    
      //#ifdef ANDROID
    public Bitmap getLeftFilled() {
        return leftFilled;
    }
    public Bitmap getCenterEmpty() {
        return centerEmpty;
    }
    public Bitmap getCenterFilled() {
        return centerFilled;
    }
    public Bitmap getLeftEmpty() {
        return leftEmpty;
    }
     public void setLeftEmpty(Bitmap leftEmpty) {
        this.leftEmpty = leftEmpty;
    }

     public void setCenterEmpty(Bitmap centerEmpty) {
        this.centerEmpty = centerEmpty;
    }


    public void setCenterFilled(Bitmap centerFilled) {
        this.centerFilled = centerFilled;
    }

    public void setLeftFilled(Bitmap leftFilled) {
        this.leftFilled = leftFilled;
    }
    //#else
    //# public Image getLeftFilled() {
        //# return leftFilled;
    //# }
    //# public Image getCenterEmpty() {
        //# return centerEmpty;
    //# }
    //# public Image getCenterFilled() {
        //# return centerFilled;
    //# }
    //# public Image getLeftEmpty() {
        //# return leftEmpty;
    //# }
     //# public void setLeftEmpty(Image leftEmpty) {
        //# this.leftEmpty = leftEmpty;
    //# }
//# 
     //# public void setCenterEmpty(Image centerEmpty) {
        //# this.centerEmpty = centerEmpty;
    //# }
//# 
//# 
    //# public void setCenterFilled(Image centerFilled) {
        //# this.centerFilled = centerFilled;
    //# }
//# 
    //# public void setLeftFilled(Image leftFilled) {
        //# this.leftFilled = leftFilled;
    //# }
    //#endif
}
