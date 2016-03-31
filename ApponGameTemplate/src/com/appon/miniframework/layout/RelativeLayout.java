/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.layout;

import java.io.ByteArrayInputStream;

import com.appon.miniframework.Container;
import com.appon.miniframework.Control;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Util;
import com.appon.util.Serilizable;
//#ifdef DEKSTOP_TOOL 
//# import java.io.ByteArrayOutputStream;
//#endif
/**
 *
 * @author acer
 */
public class RelativeLayout implements Serilizable{
    public static final int LEFT_TOP_OF_CONTROL = 0;
    public static final int RIGHT_BOTTOM_OF_CONTROL = 1;
    public static final int CENTER_OF_CONTROL = 2;
    public static final int ALLIGN_LEFT_TOP_OF_CONTROL = 3;
    public static final int ALLIGN_RIGHT_BOTTOM_OF_CONTROL = 4;
    public static final int LEFT_TOP_OF_CONTAINER = 5;
    public static final int RIGHT_BOTTOM_OF_CONTAINER = 6;
    public static final int CENTER_OF_CONTAINER = 7;
    
    
    private int relativeControlX = -1;
    private int relativeControlY = -1;
    private int relativeRelationX;
    private int relativeRelationY;
    private int additionalPaddingX;
    private int additionalPaddingY;
    private boolean scalePaddingX;
    private boolean scalePaddingY;
    
    public int getAdditionalPaddingX() {
        return additionalPaddingX;
    }
    public boolean reflectInOrigionalPosition = true;
   

    public void setReflectInOrigionalPosition(boolean reflectInOrigionalPosition) {
        this.reflectInOrigionalPosition = reflectInOrigionalPosition;
    }

    public void setAdditionalPaddingX(int additionalPaddingX) {
        this.additionalPaddingX = additionalPaddingX;
    }

    public int getAdditionalPaddingY() {
        return additionalPaddingY;
    }

    public void setAdditionalPaddingY(int additionalPaddingY) {
        this.additionalPaddingY = additionalPaddingY;
    }

    public int getRelativeControlX() {
        return relativeControlX;
    }

    public void setRelativeControlX(int relativeControl) {
        this.relativeControlX = relativeControl;
    }

    public int getRelativeControlY() {
        return relativeControlY;
    }

    public void setRelativeControlY(int relativeControl) {
        this.relativeControlY = relativeControl;
    }

    public int getRelativeRelationX() {
        return relativeRelationX;
    }

    public void setRelativeRelationX(int relativeRelationX) {
        this.relativeRelationX = relativeRelationX;
    }

    public int getRelativeRelationY() {
        return relativeRelationY;
    }

    public void setRelativeRelationY(int relativeRelationY) {
        this.relativeRelationY = relativeRelationY;
    }

    public boolean isScalePaddingX() {
        return scalePaddingX;
    }

    public void setScalePaddingX(boolean scalePaddingX) {
        this.scalePaddingX = scalePaddingX;
    }

    public boolean isScalePaddingY() {
        return scalePaddingY;
    }

    public void setScalePaddingY(boolean scalePaddingY) {
        this.scalePaddingY = scalePaddingY;
    }

    
    private void setX(Control c,int x)
    {
        c.setX(x);
        if(reflectInOrigionalPosition)
             c.setOrigionalX(x);
    }
    private void setY(Control c,int y)
    {
        c.setY(y);
        if(reflectInOrigionalPosition)
            c.setOrigionalY(y);
    }
  
    public void applyLayout(Container parent, Control currentControl) {
     
        boolean xAlligned = true;
        if(relativeRelationX != -1)
        {
             switch(relativeRelationX)
            {
                case LEFT_TOP_OF_CONTAINER:
                    setX(currentControl, 0 + additionalPaddingX);
                break;
                case RIGHT_BOTTOM_OF_CONTAINER:
                     setX(currentControl, additionalPaddingX + parent.getBoundWidth() - currentControl.getWidth());
                break;
                case CENTER_OF_CONTAINER:
                   setX(currentControl,((parent.getBoundWidth() - currentControl.getWidth())>>1) + additionalPaddingX);
                break;
                default:
                    xAlligned = false;
                break;
            }
        }
       
        if(!xAlligned)
        {
//            System.out.println("getID: "+currentControl.getId() +" relativeControlX: "+relativeControlX);
            Control foundControl = Util.findControl(Util.getRootControl(currentControl), relativeControlX);
            switch(relativeRelationX)
            {
                case LEFT_TOP_OF_CONTROL:
                   setX(currentControl,foundControl.getX() - currentControl.getWidth() + additionalPaddingX);
                break;
                case ALLIGN_LEFT_TOP_OF_CONTROL:
                   setX(currentControl,foundControl.getX() + additionalPaddingX);
                break;
                case ALLIGN_RIGHT_BOTTOM_OF_CONTROL:
                   setX(currentControl,foundControl.getX() +foundControl.getWidth() - currentControl.getWidth() + additionalPaddingX);
                break;
                case RIGHT_BOTTOM_OF_CONTROL:
                   setX(currentControl,foundControl.getX() + foundControl.getWidth() + additionalPaddingX);
                break;
                case CENTER_OF_CONTROL:
                   setX(currentControl,foundControl.getX() + ((foundControl.getWidth() - currentControl.getWidth()) >> 1)+additionalPaddingX);
                break;
                    
            }
        }
       ///////////// alliging Y /////////////
        boolean yAlligned = true;
        
         if(relativeRelationY != -1)
         {
            switch(relativeRelationY)
            {
                case LEFT_TOP_OF_CONTAINER:
                   setY(currentControl,0 + additionalPaddingY);
                break;
                case RIGHT_BOTTOM_OF_CONTAINER:
                   setY(currentControl,parent.getBoundHeight() - currentControl.getHeight() + additionalPaddingY);
                break;
                case CENTER_OF_CONTAINER:
                   setY(currentControl,((parent.getBoundHeight() - currentControl.getHeight())>>1)+additionalPaddingY);
                break;
                default:
                    yAlligned = false;
                break;
            }
        }
        if(!yAlligned)
        {
            Control foundControl = Util.findControl(Util.getRootControl(currentControl), relativeControlY);
            switch(relativeRelationY)
            {
                case LEFT_TOP_OF_CONTROL:
                   setY(currentControl,additionalPaddingY + foundControl.getY() - currentControl.getHeight());
                break;
                case RIGHT_BOTTOM_OF_CONTROL:
                   setY(currentControl,additionalPaddingY + foundControl.getY() + foundControl.getHeight());
                break;
                case CENTER_OF_CONTROL:
                   setY(currentControl,additionalPaddingY + foundControl.getY() + ((foundControl.getHeight() - currentControl.getHeight()) >> 1));
                break;
                case ALLIGN_LEFT_TOP_OF_CONTROL:
                   setY(currentControl,foundControl.getY() + additionalPaddingY);
                break;
                case ALLIGN_RIGHT_BOTTOM_OF_CONTROL:
                   setY(currentControl,foundControl.getY() + foundControl.getHeight() - currentControl.getHeight() + additionalPaddingX);
                break;
            }
        }
     
    }

  
    public byte[] serialize() throws Exception {
         //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# Util.writeSignedInt(bos, getRelativeControlX(),2);
         //# Util.writeSignedInt(bos, getRelativeControlY(),2);
         //# Util.writeInt(bos, getRelativeRelationX(),1);
         //# Util.writeInt(bos, getRelativeRelationY(),1);
         //# Util.writeSignedInt(bos, getAdditionalPaddingX(),2);
         //# Util.writeSignedInt(bos, getAdditionalPaddingY(),2);
         //# Util.writeBoolean(bos, isScalePaddingX());
         //# Util.writeBoolean(bos, isScalePaddingY());
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
        
        setRelativeControlX(Util.readSignedInt(bis,2));
        setRelativeControlY(Util.readSignedInt(bis,2));
        setRelativeRelationX(Util.readInt(bis,1));
        setRelativeRelationY(Util.readInt(bis,1));
        setAdditionalPaddingX(Util.readSignedInt(bis,2));
        setAdditionalPaddingY(Util.readSignedInt(bis,2));
        setScalePaddingX(Util.readBoolean(bis));
        setScalePaddingY(Util.readBoolean(bis));
        bis.close();
        return null;
    }

    public int getClassCode() {
        return MenuSerilize.LAYOUT_RELATIVE_TYPE;
    }

   
    public void port() {
       int scaleX = Util.getScaleX();
       int scaleY = Util.getScaleY();
       if(isScalePaddingX())
       {
           setAdditionalPaddingX(Util.getScaleValue(getAdditionalPaddingX(), scaleX));
       }
       if(isScalePaddingY())
       {
           setAdditionalPaddingY(Util.getScaleValue(getAdditionalPaddingY(), scaleY));
       }
    }

  

  
    
}
