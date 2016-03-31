/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif

import com.appon.util.Serilizable;


/**
 *
 * @author acer
 */
public class TimeFrame implements Serilizable{
    private int timeFrameId;
    private Vector shapes = new Vector();
   
    private boolean visibility = true;
    public TimeFrame clone()
    {
        TimeFrame tf = new TimeFrame();
        tf.setTimeFrameId(getTimeFrameId());
        for (int i = 0; i < shapes.size(); i++) {
            Object object = shapes.elementAt(i);
            tf.getShapes().addElement(((EShape)object).clone());
        }
        tf.setVisibility(isVisibility());
        return tf;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isVisibility() {
        return visibility;
    }
  //#ifdef ANDROID
    public void paint(Canvas g,int x,int y,boolean considerVisability,Effect parent,Paint p,boolean calldedFromETF)
   //#else
     //# public void paint(Graphics g,int x,int y,boolean considerVisability,Effect parent,boolean calldedFromETF)
   //#endif
   
    {
        if(considerVisability && !isVisibility() && !calldedFromETF)
            return;
        for (int i = 0; i < shapes.size(); i++) {
            EShape object = (EShape)shapes.elementAt(i);
            if((considerVisability && object.isVisible()) || !considerVisability)
            {
            
                //#ifdef ANDROID
            	object.paint(g,x,y,0,0,0,0,parent,p);
                //#else
                //# object.paint(g,x,y,0,0,0,0,parent);
                //#endif
            }
                
        }
    }
  //#ifdef ANDROID
    public void paint(Canvas g,int x,int y,boolean considerVisability,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint p,boolean calldedFromETF)
   //#else
    //# public void  paint(Graphics g,int x,int y,boolean considerVisability,int theta,int zoom,int anchorX,int anchorY,Effect parent,boolean calldedFromETF)
   //#endif
    {
        
        if(considerVisability && !isVisibility() && !calldedFromETF)
            return;
        for (int i = 0; i < shapes.size(); i++) {
            EShape object = (EShape)shapes.elementAt(i);
          
            if((considerVisability && object.isVisible()) || !considerVisability /*|| calldedFromETF*/)
            {
            
                //#ifdef ANDROID
            	object.paint(g,x,y,theta,zoom,anchorX,anchorY,parent,p);
                //#else
                //# object.paint(g,x,y,theta,zoom,anchorX,anchorY,parent);
                //#endif
            	
            }
                
        }
    }
    public void setBgColorCustom(int color) {
    	 for (int i = 0; i < shapes.size(); i++) {
             EShape object = (EShape)shapes.elementAt(i);
             object.setBgColor(color);
         }
		
	}
    public void setBgColorCustom(int color,int sahpeId) {
   	 for (int i = 0; i < shapes.size(); i++) {
            EShape object = (EShape)shapes.elementAt(i);
            if(object.getId()==sahpeId)
            	object.setBgColor(color);
        }
		
	}
    public void setAngle(int _angle) {
   	 for (int i = 0; i < shapes.size(); i++) {
            EShape object = (EShape)shapes.elementAt(i);
            object.setRotate(_angle);
        }
		
	}
    public int getTimeFrameId() {
        return timeFrameId;
    }

    public void setTimeFrameId(int timeFrameId) {
        this.timeFrameId = timeFrameId;
    }

    public Vector getShapes() {
        return shapes;
    }

   
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        com.appon.miniframework.Util.writeSignedInt(bos,timeFrameId ,2);
        EffectsSerilize.serialize(shapes, bos);
        EffectsSerilize.serialize(new Boolean(isVisibility()), bos);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       timeFrameId =  com.appon.miniframework.Util.readInt(bis, 2);
       shapes = (Vector)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
       if(Util.LOADING_VERSION > 0)
       {
           setVisibility(((Boolean)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance())).booleanValue());
       }else{
           setVisibility(true);
       }
       bis.close();
       return null;
    }

    
    public int getClassCode() {
       return EffectsSerilize.TIME_FRAME_TYPE;
    }
    public void port(int xper,int yper)
    {
        for (int i = 0; i < shapes.size(); i++) {
              EShape shape = (EShape)shapes.elementAt(i);
              shape.port(xper, yper);
        }
    }

	
}
