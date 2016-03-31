/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.animation;

import java.io.ByteArrayInputStream;

import android.graphics.Canvas;
//#ifdef DEKSTOP_TOOL
//# import java.io.ByteArrayOutputStream;
//# import java.awt.Graphics;
//#elifdef ANDROID
import android.graphics.Paint;

import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Settings;
import com.appon.miniframework.Util;
//#endif
/**
 *
 * @author acer
 */
public class StartAnimationLinearMoveIn extends StartAnimation{

    private int direction;
    private int movementType;
    private int speed = 5;
    private int actualSpeed;
    public static final int MOVE_FROM_RIGHT = 0;
    public static final int MOVE_FROM_LEFT = 1;
    public static final int MOVE_FROM_BOTTOM = 2;
    public static final int MOVE_FROM_TOP = 3;
    public static final int MOVEMENT_TYPES_STEPS = 0;
    public static final int MOVEMENT_TYPES_PIXELS = 1;
    
    private int refrenceFrom;
    public static final int REFRENCE_FROM_SCREEN = 0;
    public static final int REFRENCE_FROM_PARENT = 1;
    private int refrenceWidth , refrenceHeight;
    private int startDelay;
    private int tmpDelay;
    private boolean bringInRoatating;
    private int rotateSpeed;
    public int getDirection() {
        return direction;
    }

    public int getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(int rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public boolean isBringInRoatating() {
        return bringInRoatating;
    }

    public void setBringInRoatating(boolean bringInRoatating) {
        this.bringInRoatating = bringInRoatating;
    }

    public int getRefrenceFrom() {
        return refrenceFrom;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(int startDelay) {
        this.startDelay = startDelay;
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
        int _x , _y;
        
        if(refrenceFrom == REFRENCE_FROM_PARENT && control.getParent() != null)
        {
            refrenceWidth = control.getParent().getWidth();
            refrenceHeight = control.getParent().getHeight();
//            _x = control.getX();
//            _y = control.getY();
            _x = Util.getActualX(control);
            _y = Util.getActualY(control);
        }else{
            refrenceWidth = Settings.SCREEN_WIDTH;
            refrenceHeight = Settings.SCREEN_HEIGHT;
            _x = Util.getActualX(control);
            _y = Util.getActualY(control);
        }
        if(movementType == MOVEMENT_TYPES_STEPS)
        {
            switch(getDirection())
            {
                case MOVE_FROM_RIGHT:
                    actualSpeed = (refrenceWidth - _x) / speed;
                break;
                case MOVE_FROM_LEFT:
                    actualSpeed = (_x + control.getWidth()) / speed;
                
                break;
                case MOVE_FROM_TOP:
                    actualSpeed = (_y + control.getHeight()) / speed;
                
                break;    
                case MOVE_FROM_BOTTOM:
                    actualSpeed = (refrenceHeight - _y) / speed;
                
                break;        
            }
        }else{
            actualSpeed = speed;
        }
         switch(getDirection())
            {
                case MOVE_FROM_RIGHT:
                    control.setX(refrenceWidth - Util.getAddonParentsX(control));
                break;
                case MOVE_FROM_LEFT:
                    control.setX(-control.getWidth()- Util.getAddonParentsX(control));
                break;
                case MOVE_FROM_TOP:
                    control.setY(-control.getHeight()- Util.getAddonParentsY(control));
                break;    
                case MOVE_FROM_BOTTOM:
                    control.setY(refrenceHeight- Util.getAddonParentsY(control));
                break;        
            }
         tmpDelay = startDelay;
         if(isBringInRoatating())
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
        if(tmpDelay > 0)
        {
            tmpDelay--;
            return null;
        }
        if(isBringInRoatating())
        {
            control.setRotation(control.getRotation() + getRotateSpeed());
        }
        switch(getDirection())
        {
            case MOVE_FROM_RIGHT:
                int newX = control.getX() - actualSpeed;
                if(refrenceFrom == REFRENCE_FROM_PARENT)
                {
                    if(newX > getOrigionalX())
                    {
                        control.setX(newX);
                    }else{
                        setIsAnimationOver(true);
                    }
                }else{
                    int checkX = Util.getActualX(control) - actualSpeed;
                    if(checkX > getActualX())
                    {
                        control.setX(newX);
                    }else{
                        setIsAnimationOver(true);
                    }
                }
            break;
            case MOVE_FROM_LEFT:
                newX = control.getX() + actualSpeed;
                if(refrenceFrom == REFRENCE_FROM_PARENT)
                {
                    if(newX < getOrigionalX())
                    {
                        control.setX(newX);
                    }else{
                        setIsAnimationOver(true);
                    }
                }else{
                    int checkX = Util.getActualX(control) + actualSpeed;
                    if(checkX < getActualX())
                    {
                        control.setX(newX);
                    }else{
                        setIsAnimationOver(true);
                    }
                }
            break;
            case MOVE_FROM_TOP:
                int newY = control.getY() + actualSpeed;
                if(refrenceFrom == REFRENCE_FROM_PARENT)
                {
                    if(newY < getOrigionalY())
                    {
                        control.setY(newY);
                    }else{
                        setIsAnimationOver(true);
                    }
                }else{
                    int checkY = Util.getActualY(control) + actualSpeed;
                    if(checkY < getActualY())
                    {
                        control.setY(newY);
                    }else{
                        setIsAnimationOver(true);
                    }
                }
            break;    
            case MOVE_FROM_BOTTOM:
                newY = control.getY() - actualSpeed;
                if(refrenceFrom == REFRENCE_FROM_PARENT)
                {
                    if(newY > getOrigionalY())
                    {
                        control.setY(newY);
                    }else{
                        setIsAnimationOver(true);
                    }
                }else{
                    int checkY = Util.getActualY(control) - actualSpeed;
                    if(checkY > getActualY())
                    {
                        control.setY(newY);
                    }else{
                        setIsAnimationOver(true);
                    }
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
         //# Util.writeBoolean(bos, isBringInRoatating());
         //# Util.writeSignedInt(bos, getRotateSpeed(),2);
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
         setBringInRoatating(Util.readBoolean(bis));
         setRotateSpeed(Util.readSignedInt(bis,2));
         bis.close();
         return null;
    }


    public int getClassCode() {
        return MenuSerilize.ANIMATION_LINEAR_MOVE_IN;
    }
    
}
