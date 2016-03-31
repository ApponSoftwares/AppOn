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
//#elifdef ANDROID
import android.graphics.Paint;

import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Settings;
import com.appon.miniframework.Util;
//#ifdef DEKSTOP_TOOL
//# import java.io.ByteArrayOutputStream;
//#endif
/**
 *
 * @author acer
 */
public class EndAnimationLinearMoveOut extends EndAnimation{

    private int direction;
    private int movementType;
    private int speed = 5;
    private int actualSpeed;
    public static final int MOVE_TO_RIGHT = 0;
    public static final int MOVE_TO_LEFT = 1;
    public static final int MOVE_TO_BOTTOM = 2;
    public static final int MOVE_TO_TOP = 3;
    public static final int MOVEMENT_TYPES_STEPS = 0;
    public static final int MOVEMENT_TYPES_PIXELS = 1;
    
    private int refrenceFrom;
    public static final int REFRENCE_FROM_SCREEN = 0;
    public static final int REFRENCE_FROM_PARENT = 1;
    private int refrenceWidth , refrenceHeight;
    private int startDelay,tmpStartDelay;
    private boolean isRotating;
    private int roationSpeed;
    public int getDirection() {
        return direction;
    }

    public void setIsRotating(boolean isRotating) {
        this.isRotating = isRotating;
    }

    public boolean isIsRotating() {
        return isRotating;
    }

    public void setRoationSpeed(int roationSpeed) {
        this.roationSpeed = roationSpeed;
    }

    public int getRoationSpeed() {
        return roationSpeed;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(int startDelay) {
        this.startDelay = startDelay;
    }

    public int getRefrenceFrom() {
        return refrenceFrom;
    }

    public void setRefrenceFrom(int refrenceFrom) {
        this.refrenceFrom = refrenceFrom;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getMovementType() {
        return movementType;
    }

    public void setMovementType(int movementType) {
        this.movementType = movementType;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    
    public void reset() {
        super.reset();
        tmpStartDelay = startDelay;
        if(refrenceFrom == REFRENCE_FROM_PARENT && control.getParent() != null)
        {
            refrenceWidth = control.getParent().getWidth();
            refrenceHeight = control.getParent().getWidth();
        }else{
            refrenceWidth = Settings.SCREEN_WIDTH;
            refrenceHeight = Settings.SCREEN_HEIGHT;
        }
        if(movementType == MOVEMENT_TYPES_STEPS)
        {
            switch(getDirection())
            {
                case MOVE_TO_RIGHT:
                    
                        actualSpeed = (refrenceWidth - getActualX()) / speed;
                  
                break;
                case MOVE_TO_LEFT:
                    actualSpeed = (getActualX() + control.getWidth()) / speed;
                  
                break;
                case MOVE_TO_TOP:
                    actualSpeed = (getActualY() + control.getHeight()) / speed;
                  
                break;    
                case MOVE_TO_BOTTOM:
                    actualSpeed = (refrenceHeight - getActualY()) / speed;
                  
                break;        
            }
        }else{
            actualSpeed = speed;
        }
       if(isRotating)
       {
           control.setRotation(0);
       }
    }
    

    //#ifdef ANDROID
     public Object applyAnimation(Canvas g,Paint paintObject)
    //#else
    //# public Object applyAnimation(Graphics g)
    //#endif
    {
        if(tmpStartDelay > 0)
        {
            tmpStartDelay--;
            return null;
        }
        if(isRotating)
        {
            control.setRotation(control.getRotation() + getRoationSpeed());
        }
        switch(getDirection())
            {
                case MOVE_TO_RIGHT:
                                        
                    int newX = control.getX() + actualSpeed;
                    if(refrenceFrom == REFRENCE_FROM_PARENT)
                    {
                        if(newX < refrenceWidth)
                        {
                            control.setX(newX);
                        }else{
                             control.setX(newX);
                            setIsAnimationOver(true);
                        }
                    }else{
                        if(Util.getActualX(control) + actualSpeed < refrenceWidth)
                        {
                            control.setX(newX);
                        }else{
                             control.setX(newX);
                            setIsAnimationOver(true);
                        }
                    }
                break;
                case MOVE_TO_LEFT:
                    newX = control.getX() - actualSpeed;
                    if(refrenceFrom == REFRENCE_FROM_PARENT)
                    {
                        if(newX > -control.getWidth())
                        {
                            control.setX(newX);
                        }else{
                             control.setX(newX);
                            setIsAnimationOver(true);
                        }
                    }else{
                        if(Util.getActualX(control) - actualSpeed > -control.getWidth())
                        {
                            control.setX(newX);
                        }else{
                             control.setX(newX);
                            setIsAnimationOver(true);
                        }
                    }
                   
                break;
                case MOVE_TO_TOP:
                    int newY = control.getY() - actualSpeed;
                    if(refrenceFrom == REFRENCE_FROM_PARENT)
                    {
                        if(newY > -control.getHeight())
                        {
                            control.setY(newY);
                        }else{
                             control.setY(newY);
                            setIsAnimationOver(true);
                        }
                    }else{
                        if(Util.getActualY(control) - actualSpeed > -control.getHeight())
                        {
                            control.setY(newY);
                        }else{
                            control.setY(newY);
                            setIsAnimationOver(true);
                        }
                    }
                break;    
                case MOVE_TO_BOTTOM:
                    newY = control.getY() + actualSpeed;
                    if(newY < refrenceWidth)
                    {
                        control.setY(newY);
                    }else{
                          control.setY(newY);
                        setIsAnimationOver(true);
                    }
                break;        
            }
        return null;
    }


    public byte[] serialize() throws Exception {
          //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# Util.writeInt(bos,getDirection(),1);
         //# Util.writeInt(bos,getMovementType(),1);
         //# Util.writeInt(bos, getSpeed(),1);
         //# Util.writeInt(bos, getRefrenceFrom(),1);
         //# Util.writeInt(bos, getStartDelay(),1);
         //# Util.writeBoolean(bos, isIsRotating());
         //# Util.writeSignedInt(bos, getRoationSpeed(),2);
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
         setDirection(Util.readInt(bis,1));
         setMovementType(Util.readInt(bis,1));
         setSpeed(Util.readInt(bis,1));
         setRefrenceFrom(Util.readInt(bis,1));
         setStartDelay(Util.readInt(bis,1));
         setIsRotating(Util.readBoolean(bis));
         setRoationSpeed(Util.readSignedInt(bis,2));
         bis.close();
         return null;
    }


    public int getClassCode() {
        return MenuSerilize.ANIMATION_LINEAR_MOVE_OUT;
    }
    
}
