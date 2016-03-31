
package com.appon.miniframework.controls;

import java.io.ByteArrayInputStream;

//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//# import java.io.ByteArrayOutputStream;
//# 
//#elifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//# import javax.microedition.lcdui.Image;
//#elifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
//#endif

import com.appon.miniframework.Control;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.Util;


public class ImageControl extends Control
{
    //#ifdef ANDROID
    private Bitmap icon;
    //#else
    //# private Image icon;
    //#endif
    
    public static final int LEFT_ALLIGN = 0;
    public static final int CENTER_ALLIGN = 1;
    public static final int RIGHT_ALLIGN = 2;
    public static final int TOP_ALLIGN = 0;
    public static final int BOTTOM_ALLIGN = 2;
    private int xAllign = LEFT_ALLIGN;
    private int yAllign = TOP_ALLIGN;
    private int iconResourceId =  -1;
    public ImageControl() {
        super(DEFAULT_ID);
    }
    public ImageControl(int id) {
        super(id);
      
    }
     //#ifdef ANDROID
    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
    public Bitmap getIcon() {
        return icon;
    }
    //#else
     //# public void setIcon(Image icon) {
        //# this.icon = icon;
    //# }
    //# public Image getIcon() {
        //# return icon;
    //# }
    //#endif
    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public void setXAllign(int xAllign) {
        this.xAllign = xAllign;
    }

    public void setYAllign(int yAllign) {
        this.yAllign = yAllign;
    }

    public int getYAllign() {
        return yAllign;
    }

    public int getXAllign() {
        return xAllign;
    }

  
     //#ifdef ANDROID
    public void paint(Canvas g, Paint paintObject) 
    //#else
    //# public void paint(Graphics g)         
    //#endif        
    {
        if(icon == null)
            return;
        int _x = 0, _y = 0;
        if(xAllign == RIGHT_ALLIGN)
        {
           _x = getBoundWidth() - Util.getImageWidth(icon);
        }else if(xAllign == CENTER_ALLIGN){
           _x = (getBoundWidth() - Util.getImageWidth(icon)) >> 1;
        }
        if(yAllign == BOTTOM_ALLIGN)
        {
           _y = getBoundHeight()- Util.getImageHeight(icon);
        }else if(yAllign == CENTER_ALLIGN){
            _y = (getBoundHeight() - Util.getImageHeight(icon)) >> 1;
        }
        Util.drawImage(g, icon, _x, _y,paintObject);
        
    }

    
    public int getPreferredWidth() {
       if(icon == null)
           return 20;
       return Util.getImageWidth(icon) + getLeftInBound() + getRightInBound();
    }
    
    public int getPreferredHeight() {
       if(icon == null)
           return 20;
       return Util.getImageHeight(icon) + getTopInBound() + getBottomInBound();
    }
  
    public String toString() {
        return "ImageControl-"+getId();
    }


    public int getClassCode() {
       return MenuSerilize.CONTROL_IMAGE_TYPE;
    }

  
    public byte[] serialize() throws Exception {
          //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# bos.write(super.serialize());
         //# Util.writeInt(bos, getIconResourceId(),1);
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
        setIconResourceId(Util.readInt(bis,1));
        setIcon(ResourceManager.getInstance().getImageResource(getIconResourceId()));
        setXAllign(Util.readInt(bis,1));
        setYAllign(Util.readInt(bis,1));
        bis.close();
        return null;
    }

    
    public void cleanup() {
        super.cleanup();
        icon = null;
    }
	

   
    
}
