/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;

import java.io.ByteArrayInputStream;

//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.gtantra.GFont;
import com.appon.gtantra.GraphicsUtil;
//#endif
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.Util;



/**
 *
 * @author acer
 */
public class TabButton {
    private int borderColor;
    private int selectionBorderColor = -1;
    private int bgColor = -1;
    private int selectionBgColor = -1;
    private int borderThickness = 1;
    private int highlightBorderColor = -1;
    private int highlightBgColor = -1;
   
    public static final int CUSTOM_SIZE = 1;
    public static final int WRAP_SIZE = 0;
    private int sizeType = WRAP_SIZE;
    private int extraWidth;
    private int extraHeight;
    private int width;
    private int height;
    private GFont font;
    private int pallet;
    private int selectionPallet;
    private int highlightPallet;

    private int fontId = -1;
    private int bgImageId = -1;
    private int selectionBgImageId= -1;
    private int highlightBgImageId = -1;
    private int iconId = -1;
    private int selectionIconId = -1;
    private int highlightIconId = -1;
    private String text;
    public static final int RECT_TYPE = 0;
    public static final int ROUND_RECT_TYPE = 1;
    private int bgType = RECT_TYPE;
    private static final int ARC_RADIOUS_WIDTH = 22;
    private static final int ARC_RADIOUS_HEIGHT = 22;
    private int iconAllignment;
    private static final int ICON_LEFT_ALLIGNED = 0;
    private static final int ICON_RIGHT_ALLIGNED = 1;
    private static final int ICON_TOP_ALLIGNED = 2;
    private static final int ICON_BOTTOM_ALLIGNED = 3;
    private int iconPadding;
    int iconX;
    int iconY ;
    int textX;
    int textY;

    private int selectionBorderImageId = -1;
    private int highlightBorderImageId = -1;
    private int x , y;
    
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    //#ifdef ANDROID
    private Bitmap selectionBorderImage;
    private Bitmap highlightBorderImage;
    private Bitmap icon;
    private Bitmap selectionIcon;
    private Bitmap highlightIcon;
    private Bitmap bgImage;
    private Bitmap selectionBgImage;
    private Bitmap highlightBgImage;
    
    public Bitmap getHighlightBorderImage() {
        return highlightBorderImage;
    }

    public void setSelectionBorderImage(Bitmap selectionBorderImage) {
        this.selectionBorderImage = selectionBorderImage;
    }

    public void setHighlightBorderImage(Bitmap highlightBorderImage) {
        this.highlightBorderImage = highlightBorderImage;
    }

  

    public Bitmap getSelectionBorderImage() {
        return selectionBorderImage;
    }
    public Bitmap getBgImage() {
        return bgImage;
    }

    public void setBgImage(Bitmap bgImage) {
        this.bgImage = bgImage;
    }
    public Bitmap getHighlightBgImage() {
        return highlightBgImage;
    }

    public void setHighlightBgImage(Bitmap highlightBgImage) {
        this.highlightBgImage = highlightBgImage;
    }
    public Bitmap getHighlightIcon() {
        return highlightIcon;
    }

    public void setHighlightIcon(Bitmap highlightIcon) {
        this.highlightIcon = highlightIcon;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
    public Bitmap getSelectionBgImage() {
        return selectionBgImage;
    }

    public void setSelectionBgImage(Bitmap selectionBgImage) {
        this.selectionBgImage = selectionBgImage;
    }
    public Bitmap getSelectionIcon() {
        return selectionIcon;
    }

    public void setSelectionIcon(Bitmap selectionIcon) {
        this.selectionIcon = selectionIcon;
    }

    //#else
    //# private Image selectionBorderImage;
  //# private Image highlightBorderImage;
  //# private Image icon;
  //# private Image selectionIcon;
  //# private Image highlightIcon;
    //# private Image bgImage;
    //# private Image selectionBgImage;
   //# private Image highlightBgImage;
  //# public Image getHighlightBorderImage() {
  //# return highlightBorderImage;
  //# }
  //# 
  //# public void setSelectionBorderImage(Image selectionBorderImage) {
  //# this.selectionBorderImage = selectionBorderImage;
  //# }
  //# 
  //# public void setHighlightBorderImage(Image highlightBorderImage) {
  //# this.highlightBorderImage = highlightBorderImage;
  //# }
  //# 
  //# 
  //# 
  //# public Image getSelectionBorderImage() {
  //# return selectionBorderImage;
  //# }
  //# public Image getBgImage() {
  //# return bgImage;
  //# }
  //# 
  //# public void setBgImage(Image bgImage) {
  //# this.bgImage = bgImage;
  //# }
  //# public Image getHighlightBgImage() {
  //# return highlightBgImage;
  //# }
  //# 
  //# public void setHighlightBgImage(Image highlightBgImage) {
  //# this.highlightBgImage = highlightBgImage;
  //# }
  //# public Image getHighlightIcon() {
  //# return highlightIcon;
  //# }
  //# 
  //# public void setHighlightIcon(Image highlightIcon) {
  //# this.highlightIcon = highlightIcon;
  //# }
  //# 
  //# public Image getIcon() {
  //# return icon;
  //# }
  //# 
  //# public void setIcon(Image icon) {
  //# this.icon = icon;
      //# }
  //# public Image getSelectionBgImage() {
  //# return selectionBgImage;
  //# }
  //# 
  //# public void setSelectionBgImage(Image selectionBgImage) {
  //# this.selectionBgImage = selectionBgImage;
  //# }
    //# public Image getSelectionIcon() {
         //# return selectionIcon;
    //# }
  //# 
    //# public void setSelectionIcon(Image selectionIcon) {
        //# this.selectionIcon = selectionIcon;
    //# }
  //#endif
    
    public int getHighlightBorderImageId() {
        return highlightBorderImageId;
    }
    public void setSelectionBorderImageId(int selectionBorderImageId) {
        this.selectionBorderImageId = selectionBorderImageId;
    }

    public int getSelectionBorderImageId() {
        return selectionBorderImageId;
    }
    public void setHighlightBorderImageId(int highlightBorderImageId) {
        this.highlightBorderImageId = highlightBorderImageId;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    
    public void setIconPadding(int iconPadding) {
        this.iconPadding = iconPadding;
    }

    public int getIconPadding() {
        return iconPadding;
    }

    public void setIconAllignment(int iconAllignment) {
        this.iconAllignment = iconAllignment;
    }

    public int getIconAllignment() {
        return iconAllignment;
    }
    
    public void resize()
    {
        iconX = 0;
        iconY = 0;
        textX = 0;
        textY = 0;
        if(sizeType == WRAP_SIZE)
        {
            width = 0;
            height = 0;
            if(bgImage != null)                                                                                                                              
            {
                width = Util.getImageWidth(bgImage);
                height = Util.getImageHeight(bgImage);
                
            }else if(icon != null){
                width = Util.getImageWidth(bgImage);
                height = Util.getImageHeight(bgImage);
                if(font != null && text != null)
                {
                    switch (iconAllignment)
                    {
                        case ICON_LEFT_ALLIGNED:
                        case ICON_RIGHT_ALLIGNED:
                              width += font.getStringWidth(text) + (iconPadding * 2) ;
                              height = Math.max(font.getStringHeight(text),height);
                        break;
                        case ICON_TOP_ALLIGNED:
                        case ICON_BOTTOM_ALLIGNED:
                              width = Math.max(font.getStringWidth(text),width);
                              height += font.getStringHeight(text) + (iconPadding * 2);
                        break;    
                    }
                }
            }else if(font != null && text != null){
                  width = font.getStringWidth(text);
                  height = font.getStringHeight(text);
            }
            if(width == 0) 
                width = 50;
            if(height == 0) 
                height = 20;
        }
        //// calculating icon and text x and y
         if((font == null || text == null) && icon != null)
        {
            iconX += (getWidth() - Util.getImageWidth(icon)) >> 1;
            iconY += (getHeight() - Util.getImageHeight(icon)) >> 1;
        }
        else if(text != null && font != null && icon == null)
        {
            textX += (getWidth() - font.getStringWidth(text)) >> 1;
            textY += (getHeight() - font.getStringHeight(text)) >> 1;
        }else if(text != null && font != null && icon != null)
        {
            switch (iconAllignment)
            {
                case ICON_LEFT_ALLIGNED:
                    iconX = iconPadding;
                    iconY = (getHeight() - Util.getImageHeight(icon)) >> 1;
                    textX = (iconPadding << 1) + Util.getImageWidth(icon);
                    textY = (getHeight() - font.getStringHeight(text)) >> 1;
                break;
                case ICON_RIGHT_ALLIGNED:
                    iconX = font.getStringWidth(text) + iconPadding;
                    iconY = (getHeight() - Util.getImageHeight(icon)) >> 1;
                    textX = 0;
                    textY = (getHeight() - font.getStringHeight(text)) >> 1;
                break;
                case ICON_TOP_ALLIGNED:
                    iconX = (getWidth() - Util.getImageWidth(icon)) >> 1;
                    iconY = iconPadding;
                    textX = (getWidth() - font.getStringWidth(text)) >> 1;;
                    textY = (iconPadding * 2) + Util.getImageHeight(icon);
                break;
                case ICON_BOTTOM_ALLIGNED:
                    iconX = (getWidth() - Util.getImageWidth(icon)) >> 1;
                    iconY = (iconPadding) + font.getStringHeight(text);
                    textX = (getWidth() - font.getStringWidth(text)) >> 1;;
                    textY = 0;
                break;    
            }
        }
    }

    public void setBgType(int bgType) {
        this.bgType = bgType;
    }

    public int getBgType() {
        return bgType;
    }
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }
  //#ifdef ANDROID
    public void paint(Canvas g,boolean highligted, Paint paintObject)
    //#else
    //# public void paint(Graphics g,boolean highligted)
    //#endif
    {
        g.translate(x, y);
        ///////////// paint background /////////////////
        if(highligted && highlightBgColor  != -1)
        {
              //#ifdef ANDROID
                paintObject.setColor(Util.getColor(highlightBgColor));
              //#else
                  //# g.setColor(Util.getColor(highlightBgColor ));
              //#endif
           
            if(bgType == ROUND_RECT_TYPE)
            {
              //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, g,paintObject);
              //#else
                    //# g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
              //#endif
                  
            	
            }
            else
            {
              //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRect(0, 0, getWidth() - 1 , getHeight() - 1,g,paintObject);
              //#else
                //# g.fillRect(0, 0, getWidth() - 1 , getHeight() - 1);
              //#endif
            
            }
        }
        else if(isSelected && selectionBgColor  != -1)
        {
              //#ifdef ANDROID
                paintObject.setColor(Util.getColor(selectionBgColor));
              //#else
                  //# g.setColor(Util.getColor(selectionBgColor ));
              //#endif
           
            if(bgType == ROUND_RECT_TYPE)
            {
              //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, g,paintObject);
              //#else
                    //# g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
              //#endif
                  
            	
            }
            else
            {
              //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRect(0, 0, getWidth() - 1 , getHeight() - 1,g,paintObject);
              //#else
                //# g.fillRect(0, 0, getWidth() - 1 , getHeight() - 1);
              //#endif
            
            }
        }
        else if(bgColor  != -1)
        {
             //#ifdef ANDROID
                paintObject.setColor(Util.getColor(bgColor));
              //#else
                  //# g.setColor(Util.getColor(bgColor ));
              //#endif
            
            if(bgType == ROUND_RECT_TYPE){
            
                //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, g,paintObject);
                //#else
                //# g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
                //#endif
            	
            }
            else
            {
                 //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRect(0, 0, getWidth() - 1 , getHeight() - 1,                 g,paintObject);
                //#else
                 //# g.fillRect(0, 0, getWidth() - 1 , getHeight() - 1);
                //#endif
            	
            }
        }
        if(highligted && highlightBgImage != null)
        {
             Util.drawImage(g, highlightBgImage,  (getWidth() - Util.getImageWidth(highlightBgImage)) >> 1, (getHeight() - Util.getImageHeight(highlightBgImage)) >> 1,paintObject);
        }
        else if(selectionBgImage != null && isSelected())
        {
            Util.drawImage(g, selectionBgImage,  (getWidth() - Util.getImageWidth(selectionBgImage)) >> 1, (getHeight() - Util.getImageHeight(selectionBgImage)) >> 1,paintObject);
        }
        else if(bgImage != null)
        {
            Util.drawImage(g, bgImage,  (getWidth() - Util.getImageWidth(bgImage)) >> 1, (getHeight() - Util.getImageHeight(bgImage)) >> 1,paintObject);
        }
        /////////// paint the tab //////////////
        int relativeIconX = /*x + */iconX;
        int relativeIconY = /*y + */iconY;
        int relativeTextX = /*x + */textX;
        int relativeTextY = /*y +*/ textY;
        if(icon != null)
        {
            if(highligted)
            {
                Util.drawImage(g, highlightIcon,  relativeIconX, relativeIconY,paintObject);
            }else if(isSelected())
            {
                Util.drawImage(g, selectionIcon,  relativeIconX, relativeIconY,paintObject);
            }else{
                Util.drawImage(g, icon,  relativeIconX, relativeIconY,paintObject);
            }
        }
        if(font != null && text != null)
        {
            if(highligted)
            {
                font.setColor(highlightPallet);
                //#ifdef ANDROID
                font.drawString(g, text, relativeTextX, relativeTextY, 0,paintObject);
                //#else
                  //# font.drawString(g, text, relativeTextX, relativeTextY, 0);
                //#endif
              
            }else if(isSelected())
            {
                font.setColor(selectionPallet);
                
                //#ifdef ANDROID
                font.drawString(g, text, relativeTextX, relativeTextY, 0,paintObject);
                //#else
                  //# font.drawString(g, text, relativeTextX, relativeTextY, 0);
                //#endif
               
            }else{
                font.setColor(pallet);
                //#ifdef ANDROID
                font.drawString(g, text, relativeTextX, relativeTextY, 0,paintObject);
                //#else
                  //# font.drawString(g, text, relativeTextX, relativeTextY, 0);
                //#endif
               
            }
        }
        ///////// paint foreground //////
         if(highligted && highlightBorderColor  != -1)
         {
             //#ifdef ANDROID
        	paintObject.setColor(Util.getColor(highlightBorderColor));
                //#else
                //# g.setColor(Util.getColor(highlightBorderColor));
                //#endif
	            for (int i = 0; i < borderThickness; i++) {
	                if(bgType == ROUND_RECT_TYPE)
	                {
                             //#ifdef ANDROID
	                	GraphicsUtil.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                             //#else
                                //# g.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1), ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
                             //#endif
	               
	                }else{
                             //#ifdef ANDROID
	                	GraphicsUtil.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                             //#else
                                //# g.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1));
                             //#endif
	                	
	                }
                }
         }
         else if(isSelected() && selectionBorderColor   != -1)
         {
                //#ifdef ANDROID
        	paintObject.setColor(Util.getColor(selectionBorderColor));
                //#else
                //# g.setColor(Util.getColor(selectionBorderColor));
                //#endif
	            for (int i = 0; i < borderThickness; i++) {
	                if(bgType == ROUND_RECT_TYPE)
	                {
                             //#ifdef ANDROID
	                	GraphicsUtil.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                             //#else
                                //# g.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1), ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
                             //#endif
	               
	                }else{
                             //#ifdef ANDROID
	                	GraphicsUtil.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                             //#else
                                //# g.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1));
                             //#endif
	                	
	                }
                }
            
        }
        else if(borderColor != -1)
        {
              //#ifdef ANDROID
        	 paintObject.setColor(Util.getColor(borderColor));
                //#else
                 //# g.setColor(Util.getColor(borderColor));
                //#endif
           
            for (int i = 0; i < borderThickness; i++) {
                if(bgType == ROUND_RECT_TYPE)
                {
                     //#ifdef ANDROID
                        GraphicsUtil.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                      //#else
                         //# g.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1), ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
                      //#endif
                	
                }else{

                     //#ifdef ANDROID
	              	GraphicsUtil.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                     //#else
                       //# g.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1));
                      //#endif
                    
                }
            }
            
        }
        if(highligted && highlightBorderImage != null)
        {
            Util.drawImage(g,highlightBorderImage,(getWidth() - Util.getImageWidth(highlightBorderImage)) >> 1, (getHeight() - Util.getImageHeight(highlightBorderImage) >> 1),paintObject);
        }
        else if(isSelected() && selectionBorderImage != null)
        {
            Util.drawImage(g,selectionBorderImage,(getWidth() - Util.getImageWidth(selectionBorderImage)) >> 1, (getHeight() - Util.getImageHeight(selectionBorderImage) >> 1),paintObject);
        }
        g.translate(-x, -y);
    }
    public int getBgColor() {
        return bgColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

   

    public int getBgImageId() {
        return bgImageId;
    }

    public void setBgImageId(int bgImageId) {
        this.bgImageId = bgImageId;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    public int getExtraHeight() {
        return extraHeight;
    }

    public void setExtraHeight(int extraHeight) {
        this.extraHeight = extraHeight;
    }

    public int getExtraWidth() {
        return extraWidth;
    }

    public void setExtraWidth(int extraWidth) {
        this.extraWidth = extraWidth;
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

    public int getHeight() {
        return height + getExtraHeight();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHighlightBgColor() {
        return highlightBgColor;
    }

    public void setHighlightBgColor(int highlightBgColor) {
        this.highlightBgColor = highlightBgColor;
    }
 

    public int getHighlightBgImageId() {
        return highlightBgImageId;
    }

    public void setHighlightBgImageId(int highlightBgImageId) {
        this.highlightBgImageId = highlightBgImageId;
    }

    public int getHighlightBorderColor() {
        return highlightBorderColor;
    }

    public void setHighlightBorderColor(int highlightBorderColor) {
        this.highlightBorderColor = highlightBorderColor;
    }
   
    public int getHighlightIconId() {
        return highlightIconId;
    }

    public void setHighlightIconId(int highlightIconId) {
        this.highlightIconId = highlightIconId;
    }

    public int getHighlightPallet() {
        return highlightPallet;
    }

    public void setHighlightPallet(int highlightPallet) {
        this.highlightPallet = highlightPallet;
    }
  

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getPallet() {
        return pallet;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

    public int getSelectionBgColor() {
        return selectionBgColor;
    }

    public void setSelectionBgColor(int selectionBgColor) {
        this.selectionBgColor = selectionBgColor;
    }
   

    public int getSelectionBgImageId() {
        return selectionBgImageId;
    }

    public void setSelectionBgImageId(int selectionBgImageId) {
        this.selectionBgImageId = selectionBgImageId;
    }

    public int getSelectionBorderColor() {
        return selectionBorderColor;
    }

    public void setSelectionBorderColor(int selectionBorderColor) {
        this.selectionBorderColor = selectionBorderColor;
    }

   
    public int getSelectionIconId() {
        return selectionIconId;
    }

    public void setSelectionIconId(int selectionIconId) {
        this.selectionIconId = selectionIconId;
    }

    public int getSelectionPallet() {
        return selectionPallet;
    }

    public void setSelectionPallet(int selectionPallet) {
        this.selectionPallet = selectionPallet;
    }

    public int getSizeType() {
        return sizeType;
    }
    public void setSizeType(int sizeType) {
        this.sizeType = sizeType;
    }
    public int getWidth() {
        return width + getExtraWidth();
    }
    public int getSelBgWidth() {
        return getSelectionBgImage().getWidth();
    }
    public int getSelBgHeight() {
        return getSelectionBgImage().getHeight();
    }
    public void setWidth(int width) {
        this.width = width;
    }
    private boolean portWidth = false;
    private boolean portHeight = false;

    public void setPortHeight(boolean portHeight) {
        this.portHeight = portHeight;
    }

    public void setPortWidth(boolean portWidth) {
        this.portWidth = portWidth;
    }

    public boolean isPortHeight() {
        return portHeight;
    }

    public boolean isPortWidth() {
        return portWidth;
    }
    
     public byte[] serialize() throws Exception {
          //#ifdef DEKSTOP_TOOL
        //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //# Util.writeString(bos, getText());
        //# Util.writeInt(bos, getFontId(),1);
        //# Util.writeInt(bos, getPallet(),1);
        //# Util.writeInt(bos, getSelectionPallet(),1);
        //# Util.writeInt(bos, getHighlightPallet(),1);
        //# Util.writeColor(bos, getBorderColor());
        //# Util.writeColor(bos, getSelectionBorderColor());
        //# Util.writeColor(bos, getBgColor());
        //# Util.writeColor(bos, getSelectionBgColor());
        //# Util.writeColor(bos, getHighlightBorderColor());
        //# Util.writeColor(bos, getHighlightBgColor());
        //# Util.writeSignedInt(bos, getBgImageId(),2);
        //# Util.writeSignedInt(bos, getSelectionBgImageId(),2);
        //# Util.writeSignedInt(bos, getHighlightBgImageId(),2);
        //# Util.writeSignedInt(bos, getSelectionBorderImageId(),2);
        //# Util.writeSignedInt(bos, getHighlightBorderImageId(),2);
        //# Util.writeSignedInt(bos, getIconId(),2);
        //# Util.writeSignedInt(bos, getSelectionIconId(),2);
        //# Util.writeSignedInt(bos, getHighlightIconId(),2);
        //# Util.writeInt(bos, getIconAllignment(),1);
        //# Util.writeSignedInt(bos, getIconPadding(), 2);
        //# Util.writeInt(bos, getBgType(),1);
        //# Util.writeInt(bos, getSizeType(),1);
        //# Util.writeSignedInt(bos, getExtraWidth(),2);
        //# Util.writeSignedInt(bos, getExtraHeight(),2);
        //# Util.writeInt(bos, getWidth(),2);
        //# Util.writeInt(bos, getHeight(),2);
        //# Util.writeBoolean(bos, isPortWidth());
        //# Util.writeBoolean(bos, isPortHeight());
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
        setText(Util.readString(bis));
        setFontId(Util.readInt(bis,1));
        setFont(ResourceManager.getInstance().getFontResource(getFontId()));
        setPallet(Util.readInt(bis,1));
        setSelectionPallet(Util.readInt(bis,1));
        setHighlightPallet(Util.readInt(bis,1));
        setBorderColor(Util.readColor(bis));
        setSelectionBorderColor(Util.readColor(bis));
        setBgColor(Util.readColor(bis));
        setSelectionBgColor(Util.readColor(bis));
        setHighlightBorderColor(Util.readColor(bis));
        setHighlightBgColor(Util.readColor(bis));
        setBgImageId(Util.readSignedInt(bis, 2));
        setBgImage(ResourceManager.getInstance().getImageResource(getBgImageId()));
        setSelectionBgImageId(Util.readSignedInt(bis, 2));
        setSelectionBgImage(ResourceManager.getInstance().getImageResource(getSelectionBgImageId()));
        setHighlightBgImageId(Util.readSignedInt(bis, 2));
        setHighlightBgImage(ResourceManager.getInstance().getImageResource(getHighlightBgImageId()));
        setSelectionBorderImageId(Util.readSignedInt(bis, 2));
        setSelectionBorderImage(ResourceManager.getInstance().getImageResource(getSelectionBorderImageId()));
        setHighlightBorderImageId(Util.readSignedInt(bis, 2));
        setHighlightBorderImage(ResourceManager.getInstance().getImageResource(getHighlightBorderImageId()));
        setIconId(Util.readSignedInt(bis, 2));
        setIcon(ResourceManager.getInstance().getImageResource(getIconId()));
        setSelectionIconId(Util.readSignedInt(bis, 2));
        setSelectionIcon(ResourceManager.getInstance().getImageResource(getSelectionIconId()));
        setHighlightIconId(Util.readSignedInt(bis, 2));
        setHighlightIcon(ResourceManager.getInstance().getImageResource(getHighlightIconId()));
        setIconAllignment(Util.readInt(bis,1));
        setIconPadding(Util.readSignedInt(bis, 2));
        setBgType(Util.readInt(bis,1));
        setSizeType(Util.readInt(bis,1));
        setExtraWidth(Util.readSignedInt(bis, 2));
        setExtraHeight(Util.readSignedInt(bis, 2));
        setWidth(Util.readInt(bis,2));
        setHeight(Util.readInt(bis,2));
        setPortWidth(Util.readBoolean(bis));
        setPortHeight(Util.readBoolean(bis));
        return bis;
    }

    
    
    
//    @Override
//    public int getClassCode() {
//       return MenuSerilize.CONTROL_TAB_CONTROL_TYPE;
//    }
}
