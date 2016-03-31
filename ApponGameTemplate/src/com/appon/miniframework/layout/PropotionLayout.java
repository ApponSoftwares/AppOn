/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.layout;

import java.io.ByteArrayInputStream;
import java.util.Hashtable;

import com.appon.miniframework.Container;
import com.appon.miniframework.Control;
import com.appon.miniframework.Layout;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Position;
import com.appon.miniframework.Util;
//#ifdef DEKSTOP_TOOL 
//# import java.io.ByteArrayOutputStream;
//#endif
/**
 *
 * @author acer
 */
public class PropotionLayout implements Layout{

    private Hashtable storedVales = new Hashtable();
    private boolean scaleX = true;
    private boolean scaleY = true;
   
    public void applyLayout(Container parent, Control currentContainer) {
      if(!(currentContainer instanceof Container) ) 
      {
          throw new RuntimeException("Linear layout can be applied only on Container");
      }
      
      Container container = (Container) currentContainer;
      
      int xScale = Util.getScaleX();
      int yScale = Util.getScaleY();
       
        for (int i = 0; i < container.getSize(); i++) {
            Control c = container.getChild(i);
            Position pos = (Position)storedVales.get(c.getId()+"");
            if(pos == null)
            {
                pos = new Position();
                pos.setX(c.getX());
                pos.setY(c.getY());
                
                storedVales.put(c.getId()+"", pos);
            }
            
            if(isScaleX())
                c.setX(Util.getScaleValue(pos.getX(), xScale));
            if(isScaleY())
                c.setY(Util.getScaleValue(pos.getY(), yScale));
            
           
        }
    }

    public void setScaleX(boolean scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(boolean scaleY) {
        this.scaleY = scaleY;
    }

    public boolean isScaleX() {
        return scaleX;
    }

    public boolean isScaleY() {
        return scaleY;
    }

  
    public byte[] serialize() throws Exception {
          //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# Util.writeBoolean(bos, isScaleX());
         //# Util.writeBoolean(bos, isScaleY());
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
        
        setScaleX(Util.readBoolean(bis));
        setScaleY(Util.readBoolean(bis));
        bis.close();
        return null;
    }
    
    public int getClassCode() {
        return MenuSerilize.LAYOUT_PROPOTION_TYPE;
    }

   
    public void port() {
     
    }

   

 
    public int getPreferedWidth(Container parent) {
        throw new RuntimeException("Not supported yet.");
    }


    public int getPreferedHeight(Container parent) {
        throw new RuntimeException("Not supported yet.");
    }
     
}
