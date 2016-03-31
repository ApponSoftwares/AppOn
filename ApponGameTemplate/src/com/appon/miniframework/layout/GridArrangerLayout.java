/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.layout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.appon.miniframework.Container;
import com.appon.miniframework.Control;
import com.appon.miniframework.Layout;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Util;

/**
 *
 * @author acer
 */
public class GridArrangerLayout implements Layout{

    private int numberOfColumns = -1;
    private int horizontalPadding;
    private int verticalPadding;
    public static final int LEFT_ALLIGNMENT = 0;
    public static final int RIGHT_ALLIGNMENT = 1;
    public static final int CENTER_ALLIGNMENT = 2;
    public static final int CENTER_WITH_EQUAL_PADDING_ALLIGNMENT = 3;
    private int allignment = LEFT_ALLIGNMENT;
    private boolean isPortHorizontalPadding;
    private boolean isPortVerticalPadding;
    private int maxChildrenWidth;
    private int maxChildrenHeight;
    private int actualPadding;
    private int actualColumns;
    
    public void applyLayout(Container parentControl, Control currentControl) {
        if(!(currentControl instanceof Container) ) 
        {
            throw new RuntimeException("GridArrangerLayout can be applied only on Container");
        }
       if(numberOfColumns == 0)
           numberOfColumns = 1;
        Container container = (Container) currentControl;
        doBackgroundCalculations(container);
        int startX = 0;
        int startY = verticalPadding;
        int counter = 0;
        int initX = 0;
        if(actualColumns > container.getSize())
        {
            actualColumns = container.getSize();
        }
        if(allignment == CENTER_WITH_EQUAL_PADDING_ALLIGNMENT)
        {
            actualPadding = (container.getBoundWidth() - (maxChildrenWidth * actualColumns)) / (actualColumns + 1);
            initX = startX = (container.getBoundWidth() - (maxChildrenWidth * actualColumns) - ((actualColumns - 1) * actualPadding)) >> 1;
            
        }else if(allignment == LEFT_ALLIGNMENT)
        {
            initX = startX = actualPadding;
        }else if(allignment == RIGHT_ALLIGNMENT)
        {
           // initX = startX = container.getBoundWidth() - (maxChildrenWidth * actualColumns) - ((actualColumns - 1) * actualPadding);
           initX = startX = container.getBoundWidth() - actualPadding;
        }
        else if(allignment == CENTER_ALLIGNMENT)
        {
           initX = startX = (container.getBoundWidth() - (maxChildrenWidth * actualColumns) - ((actualColumns - 1) * actualPadding)) >> 1;
        }
        for (int i = 0; i < container.getSize(); i++) {
             Control c = container.getChild(i);
             if((c.getRelativeLocation() != null))
                 continue;
             if(allignment == RIGHT_ALLIGNMENT)
             {
            // initX = startX = container.getBoundWidth() - (maxChildrenWidth * actualColumns) - ((actualColumns - 1) * actualPadding);
                startX = startX - maxChildrenWidth;
             }
             c.setPoistion(startX, startY);
           
             if(allignment == RIGHT_ALLIGNMENT)
             {
            // initX = startX = container.getBoundWidth() - (maxChildrenWidth * actualColumns) - ((actualColumns - 1) * actualPadding);
                startX = startX - actualPadding;
             }else{
                 startX += maxChildrenWidth + actualPadding;  
             }
             if(counter == actualColumns - 1)
             {
                  startX = initX;
                  startY += verticalPadding + maxChildrenHeight;
                  counter = 0;
             }else{
                 counter++;
             }
             
        }
    }

    
    public void port() {
       int scaleX = Util.getScaleX();
       int scaleY = Util.getScaleY();
       if(isPortHorizontalPadding())
       {
           setHorizontalPadding(Util.getScaleValue(getHorizontalPadding(), scaleX));
       }
       if(isPortVerticalPadding())
       {
           setVerticalPadding(Util.getScaleValue(getVerticalPadding(), scaleY));
       }
    }

    public int getAllignment() {
        return allignment;
    }

    public void setAllignment(int allignment) {
        this.allignment = allignment;
    }

    public boolean isPortVerticalPadding() {
        return isPortVerticalPadding;
    }

    public boolean isPortHorizontalPadding() {
        return isPortHorizontalPadding;
    }

    public void setPortVerticalPadding(boolean isPortVerticalPadding) {
        this.isPortVerticalPadding = isPortVerticalPadding;
    }

    public void setPortHorizontalPadding(boolean isPortHorizontalPadding) {
        this.isPortHorizontalPadding = isPortHorizontalPadding;
    }

    private void setMaxChildrenSize(Container parent)
    {
         maxChildrenWidth = 0;
         maxChildrenHeight = 0;
         for (int i = 0; i < parent.getSize(); i++) {
               int w = Util.getChildernLayoutWrapWidth(parent.getChild(i));
               int h = Util.getChildernLayoutWrapHeight(parent.getChild(i));
                              
               if(parent.getChild(i).isSkipParentWrapSizeCalc())
                   continue;
               if(w > maxChildrenWidth)
               {
                   maxChildrenWidth = w;
               }
               if(h > maxChildrenHeight)
               {
                   maxChildrenHeight = h;
               }
         }
    }
    
    public int getPreferedWidth(Container parent) {
        return parent.getBoundWidth();
    }
    private void doBackgroundCalculations(Container parent)
    {
        setMaxChildrenSize(parent);
        if(numberOfColumns == -1)
        {
            int div = (maxChildrenWidth + horizontalPadding);
            if(div == 0)
                div = 1;
            actualColumns = (Util.getWrappedWidth(parent) - (horizontalPadding )) / div;
            actualPadding = horizontalPadding;
        }else{
            actualColumns = numberOfColumns;
            actualPadding = horizontalPadding;
        }
        if(actualColumns == 0)
        {
            actualColumns = 1;
        }
    }
    
    public int getPreferedHeight(Container parent) {
        doBackgroundCalculations(parent);
        int numberOfRows = (parent.getSize() / actualColumns) ;
        if(parent.getSize() % actualColumns != 0)
        {
            numberOfRows++;
        }
        return (numberOfRows * maxChildrenHeight) + ((numberOfRows + 1) * verticalPadding);
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getHorizontalPadding() {
        return horizontalPadding;
    }

    public int getVerticalPadding() {
        return verticalPadding;
    }

    
    public byte[] serialize() throws Exception {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         Util.writeSignedInt(bos,getNumberOfColumns(),2);
         Util.writeInt(bos, getAllignment(), 1);
         Util.writeSignedInt(bos,getHorizontalPadding(),2);
         Util.writeSignedInt(bos,getVerticalPadding(),2);
         Util.writeBoolean(bos,isPortHorizontalPadding());
         Util.writeBoolean(bos,isPortVerticalPadding());
         bos.flush();
         byte data[] = bos.toByteArray();
         bos.close();
         bos = null;
         return data;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
        setNumberOfColumns(Util.readSignedInt(bis,2));
        setAllignment(Util.readInt(bis, 1));
        setHorizontalPadding(Util.readSignedInt(bis,2));
        setVerticalPadding(Util.readSignedInt(bis,2));
        setPortHorizontalPadding(Util.readBoolean(bis));
        setPortVerticalPadding(Util.readBoolean(bis));
        bis.close();
        return null;
    }

    
    public int getClassCode() {
        return MenuSerilize.LAYOUT_GRID_ARRANGER;
    }
    
}
