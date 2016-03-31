/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.animation;

import java.io.ByteArrayInputStream;

import android.graphics.Canvas;
//#ifdef DEKSTOP_TOOL
//# import java.io.ByteArrayOutputStream;
//#endif
//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#elifdef ANDROID
import android.graphics.Paint;

import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Util;
//#endif
/**
 *
 * @author acer
 */
public class SteadyFloatAnimation extends SteadyAnimation{

    private int amplitudeX = 5;
    private int amplitudeY = 5;
    private int speed = 5;
    private int currentAngle;
    public static final int FLOAT_DIRECTION_Y = 0;
    public static final int FLOAT_DIRECTION_X = 1;
    public static final int FLOAT_DIRECTION_BOTH = 2;
    private int animationDirection = FLOAT_DIRECTION_Y;
    private boolean reducable = false;
    private int tmpAmplitudeX,tmpAmplitudeY;
    private int reducableSpeed = 1;
    private boolean portAmlitudes;
    private boolean portReduceSpeed;
    public int getAmplitudeX() {
        return amplitudeX;
    }

    public boolean isPortReduceSpeed() {
        return portReduceSpeed;
    }

    public boolean isPortAmlitudes() {
        return portAmlitudes;
    }

    public void setPortReduceSpeed(boolean portReduceSpeed) {
        this.portReduceSpeed = portReduceSpeed;
    }

    public void setPortAmlitudes(boolean portAmlitudes) {
        this.portAmlitudes = portAmlitudes;
    }

    public int getAnimationDirection() {
        return animationDirection;
    }

    public int getReducableSpeed() {
        return reducableSpeed;
    }

    public void setReducableSpeed(int reducableSpeed) {
        this.reducableSpeed = reducableSpeed;
    }

    public void setAnimationDirection(int animationDirection) {
        this.animationDirection = animationDirection;
    }

    public void setAmplitudeX(int amplitude) {
        this.amplitudeX = amplitude;
    }

    public boolean isReducable() {
        return reducable;
    }

    public void setReducable(boolean reducable) {
        this.reducable = reducable;
    }

    public int getAmplitudeY() {
        return amplitudeY;
    }

    public void setAmplitudeY(int amplitudeY) {
        this.amplitudeY = amplitudeY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void reset() {
        super.reset();
        tmpAmplitudeX = amplitudeX;
        tmpAmplitudeY = amplitudeY;
    }
    

    //#ifdef ANDROID
     public Object applyAnimation(Canvas g,Paint paintObject)
    //#else
    //# public Object applyAnimation(Graphics g)
    //#endif
    {
        int _x = getOrigionalX() + ((tmpAmplitudeX * Util.sin(currentAngle)) >> 14);
        int _y = getOrigionalY() + ((tmpAmplitudeY * Util.sin(currentAngle)) >> 14);
        if(animationDirection == FLOAT_DIRECTION_X || animationDirection == FLOAT_DIRECTION_BOTH)
        {
            control.setX(_x);
        }
        
        if(animationDirection == FLOAT_DIRECTION_Y || animationDirection == FLOAT_DIRECTION_BOTH)
        {
            control.setY(_y);
        }
        currentAngle += speed;
        if(currentAngle > 360)
            currentAngle = currentAngle - 360;
        if(reducable)
        {
            boolean Over = false;
            if(animationDirection == FLOAT_DIRECTION_X || animationDirection == FLOAT_DIRECTION_BOTH)
            {
               tmpAmplitudeX-= reducableSpeed;
               if(tmpAmplitudeX <= 0)
                   Over = true;
            }
             if(animationDirection == FLOAT_DIRECTION_Y || animationDirection == FLOAT_DIRECTION_BOTH)
            {
               tmpAmplitudeY-= reducableSpeed;
               if(tmpAmplitudeY <= 0)
                   Over = true;
               else
                   Over = false;
            }
             if(Over)
             {
                 setIsAnimationOver(true);
             }
        }
        return null;
    }

  
    public byte[] serialize() throws Exception {
         //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# Util.writeInt(bos,getAmplitudeX(),2);
         //# Util.writeInt(bos,getAmplitudeY(),2);
         //# 
         //# Util.writeInt(bos, getAnimationDirection(),1);
         //# Util.writeInt(bos, getSpeed(),2);
         //# Util.writeBoolean(bos, isReducable());
         //# Util.writeSignedInt(bos, getReducableSpeed(),2);
         //# Util.writeBoolean(bos, isPortAmlitudes());
         //# Util.writeBoolean(bos, isPortReduceSpeed());
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
         
         setAmplitudeX(Util.readInt(bis,2));
         setAmplitudeY(Util.readInt(bis,2));
         setAnimationDirection(Util.readInt(bis,1));
         setSpeed(Util.readInt(bis,2));
         setReducable(Util.readBoolean(bis));
         setReducableSpeed(Util.readSignedInt(bis,2));
         setPortAmlitudes(Util.readBoolean(bis));
         setPortReduceSpeed(Util.readBoolean(bis));
         bis.close();
         return null;
    }


    public int getClassCode() {
        return MenuSerilize.ANIMATION_STEADY_FLOAT;
    }


    public void port() {
        int scaleX = Util.getScaleX();
        int scaleY = Util.getScaleY();
        if(isPortAmlitudes())
        {
            setAmplitudeX(Util.getScaleValue(getAmplitudeX(), scaleX));
            setAmplitudeY(Util.getScaleValue(getAmplitudeY(), scaleY));
        }
        if(isPortReduceSpeed())
        {
            if(getAnimationDirection() == FLOAT_DIRECTION_X)
            {
                setReducableSpeed(Util.getScaleValue(getReducableSpeed(), scaleX));
            }else{
                setReducableSpeed(Util.getScaleValue(getReducableSpeed(), scaleY));
            }
        }
    }
    
}
