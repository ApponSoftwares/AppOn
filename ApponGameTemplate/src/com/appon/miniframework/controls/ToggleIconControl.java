/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;

import java.io.ByteArrayInputStream;

//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//# import java.io.ByteArrayOutputStream;
//#elifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//# import javax.microedition.lcdui.Image;
//#elifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
//#endif

import com.appon.gtantra.GFont;
import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.Control;
import com.appon.miniframework.Event;
import com.appon.miniframework.EventManager;
import com.appon.miniframework.EventQueue;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.Settings;
import com.appon.miniframework.Util;

/**
 *
 * @author acer
 */
public class ToggleIconControl extends Control{

   
    private int imageResourceId;
    private int selectionImageResourceId;
    
    private boolean isToggleSelected;
    RadioGroup group;
    private boolean disabled = false;
  
    private String text;
    private String selectionText;
    private String disabledText;
    
    private int localTextId = -1;
    private int localSelectionTextId = -1;
    private int localDisabledTextId = -1;
    
    private int disabledImageResourceId;
    private GFont font;
    private GFont disabledFont;
    private int fontResourceId;
    private int disabledFontResourceId;
    
    private int pallet;
    private int selectedPallet;
    private int disabledPallet;
    //#ifdef ANDROID
     private Bitmap image;
    private Bitmap selectionImage;
      private Bitmap disabledImage;
    //#else
    //# private Image image;
    //# private Image selectionImage;
    //# private Image disabledImage;
    //#endif
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setLocalTextId(int localTextId) {
        this.localTextId = localTextId;
    }

    public void setLocalSelectionTextId(int localSelectionTextId) {
        this.localSelectionTextId = localSelectionTextId;
    }

    public void setLocalDisabledTextId(int localDisabledTextId) {
        this.localDisabledTextId = localDisabledTextId;
    }

    public void setDisabledFont(GFont disabledFont) {
        this.disabledFont = disabledFont;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public GFont getDisabledFont() {
        return disabledFont;
    }

    public int getDisabledFontResourceId() {
        return disabledFontResourceId;
    }

    public void setDisabledFontResourceId(int disabledResourceId) {
        this.disabledFontResourceId = disabledResourceId;
    }

    public int getDisabledPallet() {
        return disabledPallet;
    }

    public void setDisabledPallet(int disabledPallet) {
        this.disabledPallet = disabledPallet;
    }

    public int getPallet() {
        return pallet;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

    public int getSelectedPallet() {
        return selectedPallet;
    }

    public void setSelectedPallet(int selectedPallet) {
        this.selectedPallet = selectedPallet;
    }
    
    public ToggleIconControl(int id) {
        super(id);
    }
    public ToggleIconControl()
    {
        this(DEFAULT_ID);
    }
    protected void setRadioGroup(RadioGroup group)
    {
        this.group = group;
    }
    //#ifdef ANDROID
    public Bitmap getDisabledImage() {
        return disabledImage;
    }

    public void setDisabledImage(Bitmap disabledBitmap) {
        this.disabledImage = disabledBitmap;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setSelectionImage(Bitmap selectionBitmap) {
        this.selectionImage = selectionBitmap;
    }

    public Bitmap getImage() {
        return image;
    }

    public Bitmap getSelectionImage() {
        return selectionImage;
    }
    //#else
    //# public Image getDisabledImage() {
        //# return disabledImage;
    //# }
//# 
    //# public void setDisabledImage(Image disabledBitmap) {
        //# this.disabledImage = disabledBitmap;
    //# }
    //# public void setImage(Image image) {
        //# this.image = image;
    //# }
//# 
    //# public void setSelectionImage(Image selectionBitmap) {
        //# this.selectionImage = selectionBitmap;
    //# }
//# 
    //# public Image getImage() {
        //# return image;
    //# }
//# 
    //# public Image getSelectionImage() {
        //# return selectionImage;
    //# }
    //#endif
    

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setSelectionImageResourceId(int selectionImageResourceId) {
        this.selectionImageResourceId = selectionImageResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getSelectionImageResourceId() {
        return selectionImageResourceId;
    }

    public void setToggleSelected(boolean value)
    {
        this.isToggleSelected = value;
    }

    public boolean isToggleSelected() {
        return isToggleSelected;
    }
    
    //#ifdef ANDROID
    public void paint(Canvas g, Paint paintObject) 
    //#else
    //# public void paint(Graphics g) 
    //#endif
    {
        if(disabled && (disabledText != null || disabledImage != null))
        {
            if(disabledImage != null)
            {
            	Util.drawImage(g, disabledImage, (getBoundWidth() - Util.getImageWidth(disabledImage)) >> 1, (getBoundHeight() - Util.getImageHeight(disabledImage)) >> 1,paintObject);
            }
            if(disabledText != null && disabledFont != null)
            {
                
                disabledFont.setColor(disabledPallet);
                  //#ifdef ANDROID
 disabledFont.drawString(g, disabledText, getWidth() >> 1, getHeight() >> 1, GraphicsUtil.HCENTER | GraphicsUtil.BASELINE,paintObject);
            //#else
             //# disabledFont.drawString(g, disabledText, getWidth() >> 1, getHeight() >> 1, GraphicsUtil.HCENTER | GraphicsUtil.BASELINE);
            //#endif
               
            }
        }else{
             //#ifdef ANDROID
            Bitmap img = image;
            //#else
            //# Image img = image;
            //#endif
           
            if(isToggleSelected())
            {
                img = selectionImage;
            }
            if(img != null)
            {
            	Util.drawImage(g, img, (getBoundWidth() - Util.getImageWidth(img)) >> 1, (getBoundHeight() - Util.getImageHeight(img)) >> 1,paintObject);
            }
            if(font != null && !disabled)
            {
                if(isToggleSelected() && selectionText != null)
                {
                     font.setColor(selectedPallet);
                    //#ifdef ANDROID
                    font.drawString(g, selectionText, getWidth() >> 1, getHeight() >> 1, GraphicsUtil.HCENTER | GraphicsUtil.BASELINE,paintObject);
                    //#else
                    //# font.drawString(g, selectionText, getWidth() >> 1, getHeight() >> 1, GraphicsUtil.HCENTER | GraphicsUtil.BASELINE);
                    //#endif
             
                }if(!isToggleSelected() && text != null)
                {
                     font.setColor(pallet);
                     //#ifdef ANDROID
                     font.drawString(g, text, getWidth() >> 1, getHeight() >> 1, GraphicsUtil.HCENTER | GraphicsUtil.BASELINE,paintObject);
                    //#else
                     //# font.drawString(g, text, getWidth() >> 1, getHeight() >> 1, GraphicsUtil.HCENTER | GraphicsUtil.BASELINE);
                    //#endif
                   
                }
            }
        }
    }
    
    public int getPreferredWidth() {
        int w = 10;
        if(disabledImage != null)
        {
            w = Util.getImageWidth(disabledImage);
        }
        if(image != null)
        {
            w = Util.getImageWidth(image);
        }
        if(selectionImage != null)
        {
            w = Util.getImageWidth(selectionImage);
        }
        if(disabledFont != null && disabledText != null)
        {
            w = Math.max(w, disabledFont.getStringWidth(disabledText));
        }
        if(font != null && text != null)
        {
            w = Math.max(w, font.getStringWidth(text));
        }
        if(font != null && selectionText != null)
        {
            w = Math.max(w, font.getStringWidth(selectionText));
        }
        return w +  getLeftInBound() + getRightInBound();
    }


    public int getPreferredHeight() {
        int w = 10;
        if(disabledImage != null)
        {
            w = Util.getImageHeight(disabledImage);
        }
        if(image != null)
        {
            w = Util.getImageHeight(image);
        }
        if(selectionImage != null)
        {
            w = Util.getImageHeight(selectionImage);
        }
        if(font != null)
        {
            w = Math.max(w, font.getFontHeight());
        }
        if(disabledFont != null)
        {
            w = Math.max(w, disabledFont.getFontHeight());
        }
        return w +  getTopInBound() + getBottomInBound();
        
        
    }
    
    public int getClassCode() {
        return MenuSerilize.CONTROL_TOGGLE_ICON;
    }

   
    public String toString() {
      return "ToggleIconControl-"+getId();
    }

    private void action()
    {
        if(!disabled)
        {
            if(group != null && !isToggleSelected)
            {
                group.selectControl(this);
                if(getEventListener() != null)
                    getEventListener().event(new Event(EventManager.RADIO_GROUP_ITEAM_SELECTED, this, group));
            }else if(group == null){
                isToggleSelected = !isToggleSelected;
                if(getEventListener() != null)
                    getEventListener().event(new Event(EventManager.STATE_CHANGED, this, null));
            }
           
        }
         
    }       
            
    
    public boolean keyPressed(int keycode, int gameKey) {
         super.keyPressed(keycode, gameKey);
        if(gameKey == Settings.FIRE || gameKey == Settings.KEY_SOFT_CENTER)
        {
            action();
            return true;
        }
        return false;
    }

    public boolean pointerReleased(int x, int y) {
        super.pointerReleased(x, y);
        if(isSelected())
            action();
        return true;
    }

    public byte[] serialize() throws Exception {
         //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# bos.write(super.serialize());
         //# Util.writeSignedInt(bos, getImageResourceId(),2);
         //# Util.writeSignedInt(bos, getSelectionImageResourceId(),2);
         //# 
         //# Util.writeSignedInt(bos, getDisabledImageResourceId(),2);
         //# Util.writeString(bos, text);
         //# Util.writeString(bos, selectionText);
         //# Util.writeString(bos, disabledText);
         //# Util.writeSignedInt(bos, getFontResourceId(),1);
         //# Util.writeSignedInt(bos, getDisabledFontResourceId(),1);
         //# 
         //# Util.writeInt(bos, pallet, 1);
         //# Util.writeInt(bos, selectedPallet, 1);
         //# Util.writeInt(bos, disabledPallet, 1);
         //# 
         //# Util.writeBoolean(bos, isDisabled());
         //# Util.writeBoolean(bos, isToggleSelected());
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
        setImageResourceId(Util.readSignedInt(bis,2));
        setImage(ResourceManager.getInstance().getImageResource(getImageResourceId()));
        setSelectionImageResourceId(Util.readSignedInt(bis,2));
        setSelectionImage(ResourceManager.getInstance().getImageResource(getSelectionImageResourceId()));
        
        
        setDisabledImageResourceId(Util.readSignedInt(bis, 2));
        setDisabledImage(ResourceManager.getInstance().getImageResource(getDisabledImageResourceId()));
        setText(Util.readString(bis));
        setSelectionText(Util.readString(bis));
        setDisabledText(Util.readString(bis));
        setFontResourceId(Util.readSignedInt(bis, 1));
        setDisabledFontResourceId(Util.readSignedInt(bis, 1));
        setFont(ResourceManager.getInstance().getFontResource(getFontResourceId()));
        setDisabledFont(ResourceManager.getInstance().getFontResource(getDisabledFontResourceId()));
        if(Settings.VERSION_NUMBER_FOUND >= 2)
        {
            setPallet(Util.readInt(bis, 1));
            setSelectedPallet(Util.readInt(bis, 1));
            setDisabledPallet(Util.readInt(bis, 1));
        }
        setDisabled(Util.readBoolean(bis));
        setToggleSelected(Util.readBoolean(bis));
        bis.close();
        return null;
    }

   
    public void showNotify() {
        if(EventQueue.getInstance().getLocalText(localTextId) != null)
        {
            setText(EventQueue.getInstance().getLocalText(localTextId));
        }
        if(EventQueue.getInstance().getLocalText(localDisabledTextId) != null)
        {
            setDisabledText(EventQueue.getInstance().getLocalText(localDisabledTextId));
        } 
        if(EventQueue.getInstance().getLocalText(localSelectionTextId) != null)
        {
            setSelectionText(EventQueue.getInstance().getLocalText(localSelectionTextId));
        } 
        super.showNotify();
        if(group != null)
        {
            group.reset();
        }
        
    }

    public boolean isIsToggleSelected() {
        return isToggleSelected;
    }

    public void setIsToggleSelected(boolean isToggleSelected) {
        this.isToggleSelected = isToggleSelected;
    }

   

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSelectionText() {
        return selectionText;
    }

    public void setSelectionText(String selectionText) {
        this.selectionText = selectionText;
    }

    public String getDisabledText() {
        return disabledText;
    }

    public void setDisabledText(String disabledText) {
        this.disabledText = disabledText;
    }

    public int getDisabledImageResourceId() {
        return disabledImageResourceId;
    }

    public void setDisabledImageResourceId(int disabledImageResourceId) {
        this.disabledImageResourceId = disabledImageResourceId;
    }

    public GFont getFont() {
        return font;
    }

    public void setFont(GFont font) {
        this.font = font;
    }

    public int getFontResourceId() {
        return fontResourceId;
    }

    public void setFontResourceId(int fontResourceId) {
        this.fontResourceId = fontResourceId;
    }
     public void cleanup() {
        super.cleanup();
        disabledFont = font =  null;
        text= null;
        selectionImage = null;
        disabledText = null;
        disabledImage = null;
        image = null;
    }

    
}
