/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;

import java.io.ByteArrayInputStream;



//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.io.ByteArrayOutputStream;
//#elifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;
//#endif



import com.appon.gtantra.GFont;
import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.Control;
import com.appon.miniframework.EventQueue;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.Settings;
import com.appon.miniframework.Util;

/**
 *
 * @author user
 */
public class TextControl extends Control{
    private GFont font;
    private GFont selectionFont;
    private String text="";
    public static final int LEFT_ALLIGN = 0;
    public static final int CENTER_ALLIGN = 1;
    public static final int RIGHT_ALLIGN = 2;
    public static final int TOP_ALLIGN = 0;
    public static final int BOTTOM_ALLIGN = 2;
    private int xAllign = LEFT_ALLIGN;
    private int yAllign = TOP_ALLIGN;
    private int pallate = 0;
    private int selectionPallate =0;
    private int fontResourceId = -1;
    private int selectionFontResourceId = -1;
    private int localTextId = -1;
    private int tintColor = -1;
    public TextControl() {
        super(DEFAULT_ID);
    }
    public TextControl(int id) {
         super(id);
    }

   public void setLocalTextId(int localTextId) {
        this.localTextId = localTextId;
    }
    public int getPreferredWidth() {
        if(getCurrentFont() == null)
        {
            return 50;
        }
        return getCurrentFont().getStringWidth(text) + getLeftInBound() + getRightInBound();
    }

    public void setSelectionFontResourceId(int selectionFontResourceId) {
        this.selectionFontResourceId = selectionFontResourceId;
    }

    public void setFontResourceId(int fontResourceId) {
        this.fontResourceId = fontResourceId;
    }

    public int getTintColor() {
        return tintColor;
    }

    public void setTintColor(int tintColor) {
        this.tintColor = tintColor;
    }

    public int getFontResourceId() {
        return fontResourceId;
    }
    public int getSelectionFontResourceId() {
        return selectionFontResourceId;
    }
    public GFont getSelectionFont() {
        return selectionFont;
    }

   

    public int getXAllign() {
        return xAllign;
    }

    public int getYAllign() {
        return yAllign;
    }

    public void setSelectionFont(GFont selectionFont) {
        if(selectionFont != null)
            this.selectionFont = selectionFont;
    }
    
    public int getSelectionPallate() {
        return selectionPallate;
    }

    public int getPallate() {
        return pallate;
    }

    public void setSelectionPallate(int selectionPallate) {
        this.selectionPallate = selectionPallate;
    }

    public void setPallate(int pallate) {
        this.pallate = pallate;
        if(getSelectionPallate() == -1)
        {
            setSelectionPallate(pallate);
        }
    }


    public int getPreferredHeight() {
        if(getCurrentFont() == null)
        {
            return 20;
        }
        return getCurrentFont()._iCharCommanHeight + getTopInBound() + getBottomInBound();
    }
 
    public void setXAllign(int xAllign) {
        this.xAllign = xAllign;
    }

    public void setYAllign(int yAllign) {
        this.yAllign = yAllign;
    }

    public void setFont(GFont font) {
        this.font = font;
        if(getSelectionFont() == null)
            setSelectionFont(font);
    }

    public GFont getFont() {
        return font;
    }

    public String getText() {
        return text;
    }
    private GFont getCurrentFont()
    {
        GFont font = null;
        if(!isSelected())
        {
            font = this.font;
            if(pallate == -1)
            {
                pallate = 0;
            }
            if(font != null)
                font.setColor(pallate);
        }else{
            font = this.selectionFont;
            if(selectionPallate == -1)
            {
                selectionPallate = 0;
            }
            if(font != null)
                font.setColor(selectionPallate);
        }
        return font;
    }
    //#ifdef ANDROID
    public void paint(Canvas g, Paint paintObject) 
    //#else
    //# public void paint(Graphics g) 
    //#endif
    {
        
        GFont font = getCurrentFont();
        if(font == null)
           return;
        int _x = 0;
        int _y = 0;
        if(xAllign == RIGHT_ALLIGN)
        {
            _x = getBoundWidth() - getPreferredWidth();
        }else if(xAllign == CENTER_ALLIGN){
            _x = (getBoundWidth() - getPreferredWidth()) >> 1;
        }
        if(yAllign == BOTTOM_ALLIGN)
        {
            _y = getBoundHeight() - getPreferredHeight();
        }else if(yAllign == CENTER_ALLIGN){
            _y = (getBoundHeight() - getPreferredHeight()) >> 1;
        }
        

    //#ifdef DEKSTOP_TOOL
    //# GraphicsUtil.setTintColor(tintColor);
    //#endif
    //#ifdef ANDROID
        font.drawString(g, text, _x, _y, GraphicsUtil.LEFT | GraphicsUtil.TOP,paintObject);
    //#else
     //# font.drawString(g, text, _x, _y, GraphicsUtil.LEFT | GraphicsUtil.TOP);
    //#endif
     
       //#ifdef DEKSTOP_TOOL
        //# GraphicsUtil.setTintColor(-1);
        //#endif
       
    }

    public void setText(String text) {
        this.text = text;
    }

    
    public String toString() {
        return "TextControl- "+getId();
    }
    
    public int getClassCode() {
       return MenuSerilize.CONTROL_TEXT_TYPE;
    }
    
    public byte[] serialize() throws Exception {
          //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# bos.write(super.serialize());
         //# Util.writeString(bos, getText());
         //# Util.writeInt(bos, getFontResourceId(),1);
         //# Util.writeInt(bos, getSelectionFontResourceId(),1);
         //# Util.writeInt(bos, getPallate(),1);
         //# Util.writeInt(bos, getSelectionPallate(),1);
         //# Util.writeInt(bos, getXAllign(),1);
         //# Util.writeInt(bos, getYAllign(),1);
         //# Util.writeColor(bos, getTintColor());
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
        super.deserialize(bis);
        setText(Util.readString(bis));
        setFontResourceId(Util.readInt(bis,1));
        setFont(ResourceManager.getInstance().getFontResource(getFontResourceId()));
        setSelectionFontResourceId(Util.readInt(bis,1));
        setSelectionFont(ResourceManager.getInstance().getFontResource(getSelectionFontResourceId()));
        setPallate(Util.readInt(bis,1));
        setSelectionPallate(Util.readInt(bis,1));
        setXAllign(Util.readInt(bis,1));
        setYAllign(Util.readInt(bis,1));
        if(Settings.VERSION_NUMBER_FOUND >= 4)
        {
            setTintColor(Util.readColor(bis));
        }
        bis.close();
        return null;
    }
      public void cleanup() {
        super.cleanup();
        font = selectionFont = null;
        text= null;
       
    }
       
    public void showNotify() {
        if(EventQueue.getInstance().getLocalText(localTextId) != null)
        {
            setText(EventQueue.getInstance().getLocalText(localTextId));
        }
        super.showNotify();
    }
}
