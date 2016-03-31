/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.animation;

import java.io.ByteArrayInputStream;

import android.graphics.Canvas;
//#endif
//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Graphics2D;
//#elifdef ANDROID
import android.graphics.Paint;

import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Util;

/**
 *
 * @author acer
 */
public class StartAnimZoomOut  extends StartAnimation{

    private int steps = 50;
    int currentsteps = 0;
    public void setSteps(int steps) {
        this.steps = steps;
    }
    
    //#ifdef ANDROID
     public Object restoreAnimationEffect(Canvas g,Paint paintObject)
    //#else
    //# public Object restoreAnimationEffect()
    //#endif
    {
        if(isAnimationApplied())
        {
         //#ifdef ANDROID
            g.restore(); 
         //#endif
        }
        return null;
    }
    public int getSteps() {
        return steps;
    }
    
    //#ifdef ANDROID
     public Object applyAnimation(Canvas g,Paint paintObject)
    //#else
    //# public Object applyAnimation(Graphics g)
    //#endif
   
    {
        Object obj = null;
         //#ifdef ANDROID
            g.save();
         //#else
         //# Graphics2D g2d = (Graphics2D)g.create();
         //# obj = g2d;
         //#endif
         if(isReverse()){   
        	 
        	 control.setControlScale(getScalePer());
         
         }else
        	 control.setControlScale(getScaleValue());
         
        	 currentsteps++;
         if(currentsteps > steps)
         {
             setIsAnimationOver(true);
             control.setControlScale(0);
         }
         return obj;
    }

   
    

	private float getScalePer() {
		
		return (((float)(500-(20*currentsteps)))/100f);
	}

	public byte[] serialize() throws Exception {
        //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# Util.writeInt(bos,getSteps(),1);
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
        setSteps(Util.readInt(bis,1));
        return null;
    }

   
    public int getClassCode() {
        return MenuSerilize.ANIMATION_ZOOM_OUT;
    }
    float getScaleValue()
    {
        return (float)(currentsteps + 1)/(float)steps;
    }
   
    public void reset() {
        
        super.reset();
        control.setControlScale(0);
        currentsteps = 0;
    }
    
}
