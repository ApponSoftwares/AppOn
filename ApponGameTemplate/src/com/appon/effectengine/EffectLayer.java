/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.util.Serilizable;

/**
 *
 * @author acer
 */
public class EffectLayer implements Serilizable{
    private Vector timeFrames = new Vector();
    private boolean visibility = true;
    public Vector getTimeFrames() {
        return timeFrames;
    }
    public EffectLayer clone()
    {
        EffectLayer tf = new EffectLayer();
        for (int i = 0; i < timeFrames.size(); i++) {
            Object object = timeFrames.elementAt(i);
            tf.getTimeFrames().addElement(((TimeFrame)object).clone());
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
    public void paint(Canvas g,int x,int y,int timeFrame,boolean considerVisability,Effect parent,Paint p)
   //#else
     //# public void paint(Graphics g,int x,int y,int timeFrame,boolean considerVisability,Effect parent)
   //#endif
   
    {
  		if(considerVisability && !isVisibility())
        {
            return;
        }
        for (int i = 0; i < timeFrames.size(); i++) {
            TimeFrame object = (TimeFrame)timeFrames.elementAt(i);
            if(object.getTimeFrameId() == timeFrame)
            {
                //#ifdef ANDROID
                   object.paint(g, x, y,considerVisability,parent,p,false);
                //#else
                   //# object.paint(g, x, y,considerVisability,parent,false);
                //#endif
                
                break;
            }
        }
    }
    //#ifdef ANDROID
    public void paint(Canvas g,int x,int y,int timeFrame,boolean considerVisability,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint p)
   //#else
        //# public void paint(Graphics g,int x,int y,int timeFrame,boolean considerVisability,int theta,int zoom,int anchorX,int anchorY,Effect parent)
   //#endif
 
    {
  		if(considerVisability && !isVisibility())
        {
            return;
        }
        for (int i = 0; i < timeFrames.size(); i++) {
            TimeFrame object = (TimeFrame)timeFrames.elementAt(i);
            if(object.getTimeFrameId() == timeFrame)
            {
                //#ifdef ANDROID
                object.paint(g, x, y,considerVisability,theta,zoom,anchorX,anchorY,parent,p,false);
                //#else
                    //# object.paint(g, x, y,considerVisability,theta,zoom,anchorX,anchorY,parent,false);
                //#endif
               
                break;
            }
        }
    }
    public void setBgColorCustom(int color) {
    	for (int i = 0; i < timeFrames.size(); i++) {
            TimeFrame object = (TimeFrame)timeFrames.elementAt(i);
            object.setBgColorCustom(color);
        }
	}
    public void setBgColorCustom(int color,int sahpeId) {
    	for (int i = 0; i < timeFrames.size(); i++) {
            TimeFrame object = (TimeFrame)timeFrames.elementAt(i);
            object.setBgColorCustom(color,sahpeId);
        }
	}
    public void setAngle(int _angle) {
    	for (int i = 0; i < timeFrames.size(); i++) {
            TimeFrame object = (TimeFrame)timeFrames.elementAt(i);
            object.setAngle(_angle);
        }
	}
    public TimeFrame getTimeFrame(int frameId)
    {
        for (int i = 0; i < timeFrames.size(); i++) {
            TimeFrame object = (TimeFrame)timeFrames.elementAt(i);
            if(object.getTimeFrameId() == frameId)
            {
                return object;
            }
        }
        return null;
    }

    
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        EffectsSerilize.serialize(timeFrames, bos);
        EffectsSerilize.serialize(new Boolean(isVisibility()), bos);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       timeFrames = (Vector)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
       if(Util.LOADING_VERSION > 0)
       {
           setVisibility(((Boolean)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance())).booleanValue());
       }else{
           setVisibility(true);
       }
       bis.close();
       return null;
    }

    public void port(int xper,int yper)
    {
         for (int i = 0; i < timeFrames.size(); i++) {
            TimeFrame object = (TimeFrame)timeFrames.elementAt(i);
            object.port(xper, yper);
        }
    }
    public int getClassCode() {
       return EffectsSerilize.EFFECT_LAYER_TYPE;
    }
	
}
