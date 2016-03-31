/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;

import java.util.Hashtable;

//#ifdef DEKSTOP_TOOL
//# import java.awt.Image;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//#elifdef ANDROID
import android.graphics.Bitmap;
//#endif

import com.appon.gtantra.GFont;
import com.appon.gtantra.GTantra;

/**
 *
 * @author acer
 */
public class ResourceManager {
    private Hashtable fontTable = new Hashtable();
    private Hashtable imageTable = new Hashtable();
    private Hashtable cornerPNGBox = new Hashtable();
    private Hashtable gtantraTable = new Hashtable();
    private static ResourceManager instance;

    private ResourceManager() {
    }

    
    public static ResourceManager getInstance() {
        if(instance == null)
            instance = new ResourceManager();
         return instance;
    }
    
    public void clear()
    {
        fontTable.clear();
        imageTable.clear();
        cornerPNGBox.clear();
        gtantraTable.clear();
    }
    public void setGTantraResource(int id, GTantra tantra)
    {
        if(tantra == null)
            return;
        if(gtantraTable.get(id+"") != null)
        {
            gtantraTable.remove(id+"");
        }
        gtantraTable.put(""+id, tantra);
    }
    public GTantra getGTantraResource(int id)
    {
        return (GTantra)gtantraTable.get(id+"");
    }
    public void setFontResource(int id, GFont font)
    {
        if(font == null)
            return;
        if(fontTable.get(id+"") != null)
        {
            fontTable.remove(id+"");
        }
        fontTable.put(""+id, font);
    }
     public void setPNGBoxResource(int id, NinePatchPNGBox box)
    {
        if(cornerPNGBox.get(id+"") != null)
        {
            cornerPNGBox.remove(id+"");
        }
        cornerPNGBox.put(""+id, box);
    }
    public NinePatchPNGBox getCornerPNGBox(int id)
    {
        return (NinePatchPNGBox)cornerPNGBox.get(id+"");
    }
    public GFont getFontResource(int id)
    {
        return (GFont)fontTable.get(id+"");
    }
    //#ifdef ANDROID
    public void setImageResource(int id, Bitmap image)
    {
        if(image == null)
            return;
        if(imageTable.get(id+"") != null)
        {
            imageTable.remove(id+"");
        }
        imageTable.put(""+id, image);
    }
    public Bitmap getImageResource(int id)
    {
        return (Bitmap)imageTable.get(id+"");
    }
    //#else
    //# public void setImageResource(int id, Image image)
    //# {
        //# if(image == null)
            //# return;
        //# if(imageTable.get(id+"") != null)
        //# {
            //# imageTable.remove(id+"");
        //# }
        //# imageTable.put(""+id, image);
    //# }
    //# public Image getImageResource(int id)
    //# {
        //# return (Image)imageTable.get(id+"");
    //# }
    //#endif
    
}
