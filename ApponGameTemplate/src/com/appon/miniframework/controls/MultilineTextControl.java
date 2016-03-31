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
import com.appon.miniframework.Util;

/**
 *
 * @author acer
 */
public class MultilineTextControl extends Control {

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
    private int pallate;
    private int selectionPallate =0;
    private String[] parsed;
    private int calculatedWidth;
    private int calculatedHeight;
    private int fontResourceId = -1;
    private int selectionFontResourceId = -1;
    private int localTextId = -1;
	public MultilineTextControl() {
        super(DEFAULT_ID);
    }
    public MultilineTextControl(int id) {
        super(id);
    }

    public void setLocalTextId(int localTextId) {
        this.localTextId = localTextId;
    }
   
    public void checkResize()
    {
        if(getWidth() > 0 && getHeight() > 0 && getFont() != null && getText() != null)
        {
            parsed = getFont().getBoxString(text, getBoundWidth(), getBoundHeight());
            calculatedWidth = getBoundWidth();
            if(calculatedWidth == 0)
            {
                calculatedWidth = 100;
            }
            calculatedHeight = getFont().getFontHeight() * parsed.length;
        }
    }
    public void setSelectionFontResourceId(int selectionFontResourceId) {
        this.selectionFontResourceId = selectionFontResourceId;
    }

    public void setFontResourceId(int fontResourceId) {
        this.fontResourceId = fontResourceId;
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


    public void setWidth(int width) {
        super.setWidth(width);
        checkResize();
    }

  
    public void setHeight(int height) {
        super.setHeight(height);
        checkResize();
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
       
        return calculatedHeight;
    }

    public int getPreferredWidth() {
        if(getCurrentFont() == null)
        {
            return 50;
        }
        return calculatedWidth;
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
        checkResize();
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
        if(font == null || parsed == null)
           return;
        int allignFlag = GraphicsUtil.LEFT;
        if(xAllign == RIGHT_ALLIGN)
        {
            allignFlag = GraphicsUtil.RIGHT;
        }else if(xAllign == CENTER_ALLIGN){
            allignFlag = GraphicsUtil.HCENTER;
        }
        if(yAllign == BOTTOM_ALLIGN)
        {
           allignFlag = allignFlag | GraphicsUtil.BOTTOM;
        }else if(yAllign == CENTER_ALLIGN){
             allignFlag = allignFlag | GraphicsUtil.BASELINE;
        }else if(yAllign == TOP_ALLIGN){
             allignFlag = allignFlag | GraphicsUtil.TOP;
        }
        //#ifdef ANDROID
        font.drawPage(g, parsed, 0, 0, getBoundWidth(), getBoundHeight(), allignFlag,paintObject);
        //#else
        //# font.drawPage(g, parsed, 0, 0, getBoundWidth(), getBoundHeight(), allignFlag);
        //#endif
        
    }

    public void setText(String text) {
        this.text = text;
        checkResize();
    }

  
    public String toString() {
        return "MultilineTextControl-"+getId();
    }
 
    public int getClassCode() {
       return MenuSerilize.CONTROL_MULTILINE_TYPE;
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
        bis.close();
        return null;
    }

    
    public void cleanup() {
        super.cleanup();
        font = selectionFont = null;
        text= null;
        parsed = null;
    }

    
    public void showNotify() {
        if(EventQueue.getInstance().getLocalText(localTextId) != null)
        {
            setText(EventQueue.getInstance().getLocalText(localTextId));
        }
        super.showNotify();
    }
    
}
