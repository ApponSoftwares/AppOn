/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.gtantra.GTantra;
import com.appon.miniframework.ResourceManager;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif
/**
 *
 * @author acer
 */
public class EGFrame extends EShape{
 
    private int gTrantraId = -1;
    private GTantra gtantra;
    private int frameId = -1;
    private int flipX;
    private int flipY;
    public EGFrame(int id) {
        super(id);
    }
    public EShape clone() {
        EGFrame arc = new EGFrame(-1);
        copyProperties(arc);
        arc.setgTrantraId(getgTrantraId());
        arc.setGtantra(getGtantra());
        arc.setFrameId(getFrameId());
        arc.setFlipX(getFlipX());
        arc.setFlipY(getFlipY());
        return arc;
    }
    public int getFlipY() {
        return flipY;
    }

    public int getFlipX() {
        return flipX;
    }

    public void setFlipY(int flipY) {
        this.flipY = flipY;
    }
    public void port(int xper, int yper) {
        setX(Util.getScaleValue(getX(), xper));
        setY(Util.getScaleValue(getY(), yper));
    }
    public void setFlipX(int flipX) {
        this.flipX = flipX;
    }

    public int getgTrantraId() {
        return gTrantraId;
    }

    public void setgTrantraId(int gTrantraId) {
        this.gTrantraId = gTrantraId;
    }

    public GTantra getGtantra() {
        return gtantra;
    }

    public void setGtantra(GTantra gtantra) {
        this.gtantra = gtantra;
    }

    public int getFrameId() {
        return frameId;
    }

    public void setFrameId(int moduleId) {
        this.frameId = moduleId;
    }
    public int getOnlyMinX()
    {
         if(gtantra == null)
            return 0;
        return gtantra.getFrameMinimumX(frameId);
    }
    public int getOnlyMinY()
    {
         if(gtantra == null)
            return 0;
        return  gtantra.getFrameMinimumY(frameId);
    }
    public int getMinX()
    {
         if(gtantra == null)
            return getX();
        return getX() + gtantra.getFrameMinimumX(frameId);
    }
    public int getMinY()
    {
         if(gtantra == null)
            return getY();
        return getY() + gtantra.getFrameMinimumY(frameId);
    }
    public int getWidth()
    {
        if(gtantra == null)
            return 50;
        return gtantra.getFrameWidth(frameId);
    }
    public int getHeight()
    {
       if(gtantra == null)
            return 50;
         return gtantra.getFrameHeight(frameId);
    }
  
  //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
   //#else
      //# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
   //#endif
   
        if(gtantra == null)
            return;
        if(zoom!=0)
        	zoom+=100;
        Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
        Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
        if(zoom!=0)
        	  gtantra.DrawFrame(g, frameId, _x + p.getX(), _y + p.getY(), flipX | flipY,true,zoom, paintObj);
        else
        	  gtantra.DrawFrame(g, frameId, _x + p.getX(), _y + p.getY(), flipX | flipY);

        
    }
    
    public String toString() {
        return "GFrame: "+getId();
    }
     
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeSignedInt(bos,gTrantraId  ,1);
        com.appon.miniframework.Util.writeInt(bos,frameId  ,1);
        com.appon.miniframework.Util.writeInt(bos,flipX ,1);
        com.appon.miniframework.Util.writeInt(bos,flipY,1);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       gTrantraId =  com.appon.miniframework.Util.readSignedInt(bis, 1);
       setGtantra(ResourceManager.getInstance().getGTantraResource(gTrantraId));
       frameId  =  com.appon.miniframework.Util.readInt(bis, 1);
       flipX  =  com.appon.miniframework.Util.readInt(bis, 1);
       flipY  =  com.appon.miniframework.Util.readInt(bis, 1);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_EGFRAME;
    }
}
