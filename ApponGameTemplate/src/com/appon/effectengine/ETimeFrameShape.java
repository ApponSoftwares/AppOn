/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 *
 * @author acer
 */
public class ETimeFrameShape extends EShape{
    private int timeFrameIndex;
    private int layerIndex;
    private int theta;
    private int zoom ;
    private int anchorX;
    private int anchorY;
    public ETimeFrameShape(int id) {
        super(id);
    }
    public EShape clone() {
        ETimeFrameShape arc = new ETimeFrameShape(-1);
        copyProperties(arc);
        arc.setTimeFrameIndex(getTimeFrameIndex());
        arc.setLayerIndex(getLayerIndex());
        arc.setTheta(getTheta());
        arc.setZoom(getZoom());
        arc.setAnchorX(getAnchorX());
        arc.setAnchorY(getAnchorY());
        return arc;
    }
    public int getLayerIndex() {
        return layerIndex;
    }

    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }

    public int getTheta() {
        return theta;
    }

    public void setTheta(int theta) {
        this.theta = theta;
    }
    public void port(int xper, int yper) {
        setX(Util.getScaleValue(getX(), xper));
        setY(Util.getScaleValue(getY(), yper));
    }
    public int getTimeFrameIndex() {
        return timeFrameIndex;
    }

    public void setTimeFrameIndex(int timeFrameIndex) {
        this.timeFrameIndex = timeFrameIndex;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
    //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
    //#else
    	//# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
    //#endif  
   
        Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
        Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
        if(parent != null)
        {
            //#ifdef DEKSTOP_TOOL
            //# int foundLayerIndex = Util.findLayerIndex(parent, getId());
            //# int foundTimeFrameIndex = Util.findTimeFrameIndex(parent, getId());
            //# if(foundLayerIndex == getLayerIndex() && foundTimeFrameIndex == getTimeFrameIndex())
            //# {
                //# return;
            //# }    
            //#endif
            if(layerIndex < parent.getEffectLayers().size() && layerIndex >= 0 && timeFrameIndex >= 0)
            {
                EffectLayer layer = (EffectLayer)parent.getEffectLayers().elementAt(layerIndex);
                TimeFrame frame = layer.getTimeFrame(timeFrameIndex);
                if(frame != null)
                {
                	//#ifdef ANDROID
                	frame.paint(g, _x + p.getX(), _y + p.getY(), true, getTheta() + theta, getZoom() + zoom, getAnchorX() + anchorX, getAnchorY() + anchorY, parent,paintObj,true);
                	//#else
                	//# frame.paint(g, _x + p.getX(), _y + p.getY(), true, getTheta() + theta, getZoom() + zoom, getAnchorX() + anchorX, getAnchorY() + anchorY, parent,true);
                	//#endif
                    
                }
            }
        }
        
    }

    public void setAnchorY(int anchorY) {
        this.anchorY = anchorY;
    }

    public void setAnchorX(int anchorX) {
        this.anchorX = anchorX;
    }

    public int getAnchorY() {
        return anchorY;
    }

    public int getAnchorX() {
        return anchorX;
    }

    
    public String toString() {
        return "TimeFrame: "+getId();
    }
     
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeSignedInt(bos,getTimeFrameIndex()  ,1);
        com.appon.miniframework.Util.writeSignedInt(bos,getLayerIndex()  ,1);
        com.appon.miniframework.Util.writeSignedInt(bos,getTheta()  ,2);
        com.appon.miniframework.Util.writeSignedInt(bos,getZoom()  ,2);
        com.appon.miniframework.Util.writeSignedInt(bos,getAnchorX()  ,2);
        com.appon.miniframework.Util.writeSignedInt(bos,getAnchorY()  ,2);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       timeFrameIndex =  com.appon.miniframework.Util.readSignedInt(bis, 1);
       layerIndex =  com.appon.miniframework.Util.readSignedInt(bis, 1);
       theta = com.appon.miniframework.Util.readSignedInt(bis, 2);
       zoom = com.appon.miniframework.Util.readSignedInt(bis, 2);
       anchorX= com.appon.miniframework.Util.readSignedInt(bis, 2);
       anchorY= com.appon.miniframework.Util.readSignedInt(bis, 2);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_TIMEFRAME;
    }
}
