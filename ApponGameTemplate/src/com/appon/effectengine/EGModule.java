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
public class EGModule extends EShape{
 
    private int gTrantraId = -1;
    private GTantra gtantra;
    private int moduleId = -1;
    private int flipX;
    private int flipY;
    public EGModule(int id) {
        super(id);
    }
    public EShape clone() {
        EGModule arc = new EGModule(-1);
        copyProperties(arc);
        arc.setgTrantraId(getgTrantraId());
        arc.setGtantra(getGtantra());
        arc.setModuleId(getModuleId());
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

    public void setFlipX(int flipX) {
        this.flipX = flipX;
    }

    public int getgTrantraId() {
        return gTrantraId;
    }
    public void port(int xper, int yper) {
        setX(Util.getScaleValue(getX(), xper));
        setY(Util.getScaleValue(getY(), yper));
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

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

   
    public int getWidth()
    {
        if(gtantra == null)
            return 50;
        return gtantra._width[moduleId];
    }
    public int getHeight()
    {
       if(gtantra == null)
            return 50;
        return gtantra._height[moduleId];
    }
  
    //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
   //#else
      //# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
   //#endif
   
        if(gtantra == null)
            return;
        Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
        Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
        gtantra.DrawModule(g, moduleId, _x + p.getX(), _y + p.getY(), flipX | flipY);
    }
    
    public String toString() {
        return "GModule: "+getId();
    }
      
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeSignedInt(bos,gTrantraId  ,1);
        com.appon.miniframework.Util.writeInt(bos,moduleId  ,1);
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
       moduleId  =  com.appon.miniframework.Util.readInt(bis, 1);
       flipX  =  com.appon.miniframework.Util.readInt(bis, 1);
       flipY  =  com.appon.miniframework.Util.readInt(bis, 1);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_EGMODULE;
    }
}
