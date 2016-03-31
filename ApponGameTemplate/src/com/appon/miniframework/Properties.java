/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.miniframework;



//#ifdef DEKSTOP_TOOL
//# import java.awt.Color;
//# import java.awt.Image;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//#elifdef ANDROID
import android.graphics.Bitmap;
//#endif


/**
 *
 * @author Swaroop Kumar
 */
public class Properties 
{
   
    protected int selectionBorderColor  = -1;
    protected int borderColor = -1;
    protected int bgColor = -1;
    protected int selectionBgColor = -1;
    
    CornersPNGBox cornerPngBg;
    
    //#ifdef ANDROID
    protected Bitmap bgImage,selectionBgImage;
    //#else
     //# protected Image bgImage,selectionBgImage;
    //#endif
    public void copyProperites(Properties properties)
    {
        if(properties == null)
            return;
        selectionBgColor = properties.selectionBgColor;
        borderColor = properties.borderColor;
        bgColor = properties.bgColor;
        selectionBgColor = properties.selectionBgColor;
        bgImage = properties.bgImage;
        selectionBgImage = properties.selectionBgImage;
        selectionBorderColor = properties.selectionBorderColor;
        cornerPngBg = properties.cornerPngBg;
    }
    public int getBgColor() {
        return bgColor;
    }

    public void setCornerPngBg(CornersPNGBox cornerPngBg) {
        this.cornerPngBg = cornerPngBg;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

   

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getSelectionBgColor() {
        return selectionBgColor;
    }

    public void setSelectionBgColor(int selectionBgColor) {
        this.selectionBgColor = selectionBgColor;
    }

   

   

    public int getSelectionBorderColor() {
        return selectionBorderColor;
    }
   

    public void setSelectionBorderColor(int selectionBorderColor) {
        this.selectionBorderColor = selectionBorderColor;
    }

    public CornersPNGBox getCornerPngBg() {
        return cornerPngBg;
    }
      //#ifdef ANDROID
    public Bitmap getBgImage() {
        return bgImage;
    }

    public void setBgImage(Bitmap bgBitmap) {
        this.bgImage = bgBitmap;
    }
     public Bitmap getSelectionBgImage() {
        return selectionBgImage;
    }
     public void setSelectionBgImage(Bitmap selectionBgBitmap) {
        this.selectionBgImage = selectionBgBitmap;
    }
    //#else
    //# public Image getBgImage() {
     //# return bgImage;
    //# }
 //# 
    //# public void setBgImage(Image bgBitmap) {
       //# this.bgImage = bgBitmap;
    //# }
     //# public Image getSelectionBgImage() {
        //# return selectionBgImage;
    //# }
     //# public void setSelectionBgImage(Image selectionBgBitmap) {
        //# this.selectionBgImage = selectionBgBitmap;
    //# }
    //#endif
     public void cleanup()
     {
        bgImage = selectionBgImage = null;
        if(cornerPngBg != null)
        {
            cornerPngBg.cleanup();
        }
     }
}
