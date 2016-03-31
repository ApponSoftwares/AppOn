/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.layout;

import java.io.ByteArrayInputStream;

import com.appon.miniframework.Container;
import com.appon.miniframework.Control;
import com.appon.miniframework.Layout;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Util;
//#ifdef DEKSTOP_TOOL 
//# import java.io.ByteArrayOutputStream;
//#endif
/**
 *
 * @author acer
 */
public class LinearLayout implements Layout{

    public static final int ORIANTATION_VERTICAL = 0;
    public static final int ORIANTATION_HORIZONTAL = 1;
    public static final int PADDING_EQUAL = 1;
    public static final int PADDING_CUSTOM = 0;
    public static final int ALLIGNMENT_TOP_OF_PARENT = 0;
    public static final int ALLIGNMENT_BOTTOM_OF_PARENT = 1;
    public static final int ALLIGNMENT_CENTER_OF_PARENT = 2;
    public static final int ALLIGNMENT_LEFT_OF_PARENT = 0;
    public static final int ALLIGNMENT_RIGHT_OF_PARENT = 1;
    
    private int allignmentX;
    private int allignmentY;
    private int paddingType;
    private int padding;
    private int oriantation;
    private int offset;
    private boolean portOffset;
    private boolean portPadding;
    private int oppositeAdditionalPadding;
    private boolean portOppsiteAdditionalPadding;
    public int getAllignmentX() {
        return allignmentX;
    }

    public void setAllignmentX(int allignmentX) {
        this.allignmentX = allignmentX;
    }

    public int getOppositeAdditionalPadding() {
        return oppositeAdditionalPadding;
    }

    public void setOppositeAdditionalPadding(int oppositeAdditionalPadding) {
        this.oppositeAdditionalPadding = oppositeAdditionalPadding;
    }

    public boolean isPortOppsiteAdditionalPadding() {
        return portOppsiteAdditionalPadding;
    }

    public void setPortOppsiteAdditionalPadding(boolean portOppsiteAdditionalPadding) {
        this.portOppsiteAdditionalPadding = portOppsiteAdditionalPadding;
    }
    public int getTotalChildsWidth(Container c)
    {
        int width = 0;
          for (int i = 0; i < c.getSize(); i++) {
                Control control = c.getChild(i);
                if(control.isVisible())
                {
                    width += control.getWidth();
                }
                    
          }
          return width;
    }
    public int getTotalChildsHeight(Container c)
    {
        int width = 0;
          for (int i = 0; i < c.getSize(); i++) {
                Control control = c.getChild(i);
                if(control.isVisible())
                    width += control.getHeight();
          }
          return width;
    }

    public int getAllignmentY() {
        return allignmentY;
    }

    public void setAllignmentY(int allignmentY) {
        this.allignmentY = allignmentY;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOriantation() {
        return oriantation;
    }

    public void setOriantation(int oriantation) {
        this.oriantation = oriantation;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPaddingType() {
        return paddingType;
    }

    public void setPaddingType(int paddingType) {
        this.paddingType = paddingType;
    }

    public boolean isPortOffset() {
        return portOffset;
    }

    public void setPortOffset(boolean portOffset) {
        this.portOffset = portOffset;
    }

    public boolean isPortPadding() {
        return portPadding;
    }

    public void setPortPadding(boolean portPadding) {
        this.portPadding = portPadding;
    }


    public void applyLayout(Container parent, Control currentContainer) {
      if(!(currentContainer instanceof Container) ) 
      {
          throw new RuntimeException("Linear layout can be applied only on Container");
      }
//      if(currentContainer.getSizeSetting() == Control.WRAP_CONTENT)
//      {
//          currentContainer.setWidth(getPreferedWidth((Container)currentContainer));
//          currentContainer.setHeight(getPreferedHeight((Container)currentContainer));
//      }
      Container container = (Container) currentContainer;
      if(oriantation == ORIANTATION_VERTICAL)
      {
          if(paddingType == PADDING_EQUAL)
          {
             padding = ((container.getBoundHeight() - getTotalChildsHeight(container) - (offset *2)) / (container.getSize() -1 ));
             
             if(padding < 0)
                 padding = 0;
          }
          int startY = offset;
          int startX = 0;
           if(allignmentY == ALLIGNMENT_CENTER_OF_PARENT)
           {
              int totalHeight = ((container.getSize() -1 )*padding) + getTotalChildsHeight(container);
              startY = (container.getBoundHeight() - totalHeight) >> 1;
           }else if(allignmentY == ALLIGNMENT_BOTTOM_OF_PARENT)
           {
              startY = container.getBoundHeight() - offset;
           }
           for (int i = 0; i < container.getSize(); i++) {
            Control c = container.getChild(i);
             if((c.getRelativeLocation() != null))
                 continue;
            if(allignmentY == ALLIGNMENT_BOTTOM_OF_PARENT)
            {
               startY -= c.getHeight();
            }
            if(allignmentX == ALLIGNMENT_LEFT_OF_PARENT)
            {
                startX = oppositeAdditionalPadding;
            }else if(allignmentX == ALLIGNMENT_RIGHT_OF_PARENT)
            {
                startX = container.getBoundWidth() - c.getWidth() - oppositeAdditionalPadding;
            }else{
                startX = (container.getBoundWidth() - c.getWidth() ) >> 1;
                startX += oppositeAdditionalPadding;
            }
            if(startY < 0)
                startY = 0;
           
                c.setPoistion(startX, startY);
            if(allignmentY == ALLIGNMENT_BOTTOM_OF_PARENT)
            {
                startY -= padding;
            }else{
                startY += (padding + c.getHeight());
            }
        }
      }else{
          if(paddingType == PADDING_EQUAL)
          {
             padding = ((container.getBoundWidth() - getTotalChildsWidth(container) - (offset *2)) / (container.getSize() -1 ));
             if(padding < 0)
                 padding = 0;
          }
          int startX = offset;
          int startY = 0;
          if(allignmentX == ALLIGNMENT_CENTER_OF_PARENT)
           {
              int totalWidth = ((container.getSize() -1 )*padding) + getTotalChildsWidth(container);
              startX = (container.getBoundWidth() - totalWidth) >> 1;
           }else if(allignmentX == ALLIGNMENT_RIGHT_OF_PARENT)
           {
              int totalWidth = ((container.getSize() -1 )*padding) + getTotalChildsWidth(container) + offset;
              if(totalWidth < container.getBoundWidth())
              {
                  totalWidth =  container.getBoundWidth();
              }
              startX = totalWidth - offset;
              
           }
          
           for (int i = 0; i < container.getSize(); i++) {
            Control c = container.getChild(i);
            if(allignmentX == ALLIGNMENT_RIGHT_OF_PARENT)
            {
               startX -= c.getWidth();
            }
            if(allignmentY == ALLIGNMENT_TOP_OF_PARENT)
            {
                startY = oppositeAdditionalPadding;
            }else if(allignmentY == ALLIGNMENT_BOTTOM_OF_PARENT)
            {
                startY = container.getBoundHeight() - c.getHeight() - oppositeAdditionalPadding;
            }else{
                startY = ((container.getBoundHeight())  - c.getHeight() ) >> 1;
//                startY += oppositeAdditionalPadding;
            }
            if(startX < 0)
                startX = 0;
            if(!(c.getLayout() instanceof RelativeLayout))
                c.setPoistion(startX, startY);
            if(allignmentX == ALLIGNMENT_RIGHT_OF_PARENT)
            {
                startX -= padding;
            }else{
                startX += (padding + c.getWidth());
            }
        }
      }
    }
    
    public byte[] serialize() throws Exception {
          //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# Util.writeInt(bos,getOriantation(),1);
         //# Util.writeInt(bos,getAllignmentX(),1);
         //# Util.writeInt(bos,getAllignmentY(),1);
         //# Util.writeSignedInt(bos,getOffset(),2);
         //# Util.writeInt(bos,getPaddingType(),1);
         //# Util.writeSignedInt(bos,getPadding(),2);
         //# Util.writeSignedInt(bos,getOppositeAdditionalPadding(),2);
         //# Util.writeBoolean(bos, isPortOppsiteAdditionalPadding());
         //# Util.writeBoolean(bos, isPortOffset());
         //# Util.writeBoolean(bos, isPortPadding());
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
         
         setOriantation(Util.readInt(bis,1));
         setAllignmentX(Util.readInt(bis,1));
         setAllignmentY(Util.readInt(bis,1));
         setOffset(Util.readSignedInt(bis,2));
         setPaddingType(Util.readInt(bis,1));
         setPadding(Util.readSignedInt(bis,2));
         setOppositeAdditionalPadding(Util.readSignedInt(bis,2));
         setPortOppsiteAdditionalPadding(Util.readBoolean(bis));
         setPortOffset(Util.readBoolean(bis));
         setPortPadding(Util.readBoolean(bis));
         bis.close();
        return null;
    }
 
    public int getClassCode() {
        return MenuSerilize.LAYOUT_LINEAR_TYPE;
    }

 
    public void port() {
       int scaleX = Util.getScaleX();
       int scaleY = Util.getScaleY();
       if(isPortOffset())
       {
           if(getOriantation() == ORIANTATION_VERTICAL)
           {
               setOffset(Util.getScaleValue(getOffset(), scaleY));
           }else{
               setOffset(Util.getScaleValue(getOffset(), scaleX));
           }
           
       }
       if(isPortOppsiteAdditionalPadding())
       {
            if(getOriantation() == ORIANTATION_VERTICAL)
           {
               setOppositeAdditionalPadding(Util.getScaleValue(getOppositeAdditionalPadding(), scaleX));
           }else{
               setOppositeAdditionalPadding(Util.getScaleValue(getOppositeAdditionalPadding(), scaleY));
           }
       }
       if(isPortPadding())
       {
           if(getOriantation() == ORIANTATION_VERTICAL)
           {
               setPadding(Util.getScaleValue(getPadding(), scaleY));
           }else{
               setPadding(Util.getScaleValue(getPadding(), scaleX));
           }
           
       }
    }

  

    public int getPreferedWidth(Container control) {
       if(oriantation == ORIANTATION_HORIZONTAL)
       {
           int h = getOffset() << 1;
           h += (getPadding() * (control.getSize() - 1));
           for (int i = 0; i < control.getSize(); i++) {
               h += Util.getChildernLayoutWrapWidth(control.getChild(i));
           }
           return h;
       }else{
           int max = getOppositeAdditionalPadding() ;
           for (int i = 0; i < control.getSize(); i++) {
               int val = getOppositeAdditionalPadding() +  Util.getChildernLayoutWrapWidth(control.getChild(i));
               if(val > max)
               {
                   max = val;
               }
           }
           return max;
       }
    }

    public int getPreferedHeight(Container control) {
        
       if(oriantation == ORIANTATION_VERTICAL)
       {
           int h = getOffset() << 1;
           h += (getPadding() * (control.getSize() - 1));
           for (int i = 0; i < control.getSize(); i++) {
               h += Util.getChildernLayoutWrapHeight(control.getChild(i));
           }
           return h;
       }else{
           int max = 0 ;
           for (int i = 0; i < control.getSize(); i++) {
               int val = getOppositeAdditionalPadding() + Util.getChildernLayoutWrapHeight(control.getChild(i));
               if(val > max)
               {
                   max = val;
               }
           }
           return max ;
       }
    }
   
}
