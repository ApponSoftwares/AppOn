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

import com.appon.gtantra.GFont;
import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.ResourceManager;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif
/**
 *
 * @author acer
 */
public class EGFont extends EShape{
    private int fillType;
 
    private int fontId = -1;
    private String text;
    private GFont font;
    private int pallet = 0;
    private int additionPadding;
    public EGFont(int id) {
        super(id);
    }
     public EShape clone() {
        EGFont arc = new EGFont(-1);
        copyProperties(arc);
        arc.setFillType(getFillType());
        arc.setFontId(getFontId());
        arc.setText(getText());
        arc.setFont(getFont());
        arc.setPallet(getPallet());
        arc.setAdditionPadding(getAdditionPadding());
        return arc;
    }
    public int getFillType() {
        return fillType;
    }

    public void setFillType(int fillType) {
        this.fillType = fillType;
    }

    public int getHeight() {
        if(font != null && text != null)
        {
           return font.getStringHeight(text) + (additionPadding << 1);
        }
        return 50;
    }

    public GFont getFont() {
        return font;
    }

    public void setFont(GFont font) {
        this.font = font;
    }

    public int getFontId() {
        return fontId;
    }

    public void setFontId(int fontId) {
        this.fontId = fontId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

   

    public int getWidth() {
        if(font != null && text != null)
        {
            return font.getStringWidth(text)+ (additionPadding << 1);
        }
        return 50;
    }

    public void setAdditionPadding(int additionPadding) {
        this.additionPadding = additionPadding;
    }

    public int getAdditionPadding() {
        return additionPadding;
    }

    public int getPallet() {
        return pallet;
    }

   

    
  //#ifdef ANDROID
    public void paint(Canvas g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent,Paint paintObj) {
   //#else
     //# public void paint(Graphics g,int _x,int _y,int theta,int zoom,int anchorX,int anchorY,Effect parent) {
   //#endif
    
        Point p = Util.pointToRotate;
        p.setPoints( getX(), getY());
        Util.rotatePoint(p, anchorX, anchorY, theta,zoom,this);
        if(getFillType() == FILL_TYPE && getBgColor() != -1 )
        {
        	 //#ifdef ANDROID
        	paintObj.setColor(Util.getColor(getBgColor()));
            GraphicsUtil.fillRect(p.getX() + _x, p.getY() + _y, getWidth() , getHeight(),g,paintObj);
        	//#else
          //# g.setColor(Util.getColor(getBgColor()));
          //# g.fillRect(p.getX() + _x, p.getY() + _y, getWidth() , getHeight());
        	//#endif
            
        }
        if(getBorderColor() != -1 )
        {
        	 //#ifdef ANDROID
        	paintObj.setColor(Util.getColor(getBorderColor()));
            Util.drawRectangle(g,p.getX() + _x, p.getY() + _y, getWidth(), getHeight(), getBorderThickness(),paintObj);
        	//#else
        	//# g.setColor(Util.getColor(getBorderColor()));
            //# Util.drawRectangle(g,p.getX() + _x, p.getY() + _y, getWidth(), getHeight(), getBorderThickness());
        	//#endif
            
        }
        if(font != null && text != null)
        {
            font.setColor(pallet);
            //#ifdef ANDROID
            font.drawString(g, text, p.getX() + _x + additionPadding, p.getY() + _y + additionPadding, 0,paintObj);
            //#else
            //# font.drawString(g, text, p.getX() + _x + additionPadding, p.getY() + _y + additionPadding, 0);
            //#endif
        }
    }

    
    public String toString() {
        return "EGFont: "+getId();
    }
     
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        com.appon.miniframework.Util.writeSignedInt(bos,fontId ,1);
        com.appon.miniframework.Util.writeInt(bos,fillType ,1);
        com.appon.miniframework.Util.writeString(bos,text);
        com.appon.miniframework.Util.writeInt(bos,pallet ,1);
        com.appon.miniframework.Util.writeInt(bos,additionPadding,1);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }

    public void port(int xper, int yper) {
        setX(Util.getScaleValue(getX(), xper));
        setY(Util.getScaleValue(getY(), yper));
    }
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       super.deserialize(bis);
       fontId =  com.appon.miniframework.Util.readSignedInt(bis, 1);
       setFont(ResourceManager.getInstance().getFontResource(fontId));
       fillType =  com.appon.miniframework.Util.readInt(bis, 1);
       text = com.appon.miniframework.Util.readString(bis);
       pallet =  com.appon.miniframework.Util.readInt(bis, 1);
       additionPadding =  com.appon.miniframework.Util.readInt(bis, 1);
       bis.close();
       return null;
    }


    
    public int getClassCode() {
       return EffectsSerilize.SHAPE_TYPE_EGFONT;
    }
}
