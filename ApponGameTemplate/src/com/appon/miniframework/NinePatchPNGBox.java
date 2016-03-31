/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;

//#ifdef DEKSTOP_TOOL
//# import com.appon.gtantra.GraphicsUtil;
//# import java.awt.Graphics;
//# import java.awt.Image;
//# import java.awt.Rectangle;
//#elifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.gtantra.GraphicsUtil;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#endif




/**
 *
 * @author user
 */
public class NinePatchPNGBox 
{
    //#ifdef ANDROID
    private Bitmap leftTopImage;
    private Bitmap topImage;
    private Bitmap leftImage;
    private Bitmap baseImage;
    private Bitmap bottomImage;
   private Bitmap rightImage;
   private Bitmap rightTopImage;
   private Bitmap rightBottomImage;
   private Bitmap leftBottomImage;

    //#else
    //# private Image leftTopImage;
    //# 
    //# private Image topImage;
    //# private Image leftImage;
    //# private Image baseImage;
    //# private Image bottomImage;
    //# private Image rightImage;
    //# private Image rightTopImage;
    //# private Image rightBottomImage;
    //# private Image leftBottomImage;
    //# 
    //# 
   //# 
//# 
    //#endif
    
    
    private int color;
    
    

    //#ifdef ANDROID
      public NinePatchPNGBox(Bitmap leftTopImage, Bitmap topImage, Bitmap leftImage,int color,Bitmap baseBitmap,Bitmap bottomImage,Bitmap rightImage,Bitmap rightTopImage,Bitmap rightBottomImage,Bitmap leftBottomImage) {
    //#else
    //# public NinePatchPNGBox(Image leftTopImage, Image topImage, Image leftImage,int color,Image baseBitmap,Image bottomImage,Image rightImage,Image rightTopImage,Image rightBottomImage,Image leftBottomImage) {
    //#endif
  
        this.leftTopImage = leftTopImage;
        this.topImage = topImage;
        this.leftImage = leftImage;
        this.color = color;
        this.baseImage = baseBitmap;
        
        this.bottomImage = bottomImage;
        this.rightImage = rightImage;
        this.rightTopImage = rightTopImage;
        this.rightBottomImage = rightBottomImage;
        this.leftBottomImage = leftBottomImage;
    }

    public NinePatchPNGBox() {
    }

  

    
    
     //#ifdef ANDROID
    public void paint(Canvas g,int x,int y,int width,int height, Paint paintObject)
     //#else
    //# public void paint(Graphics g,int x,int y,int width,int height)
    //#endif
    {
//        int clipX = g.getClipX();
//        int clipY = g.getClipY();
//        int clipWidth = g.getClipWidth();
//        int clipHeight = g.getClipHeight();
        if(leftImage == null || topImage == null || leftTopImage == null)
            return;
         int clipX , clipY , clipWidth , clipHeight;
        //#ifdef DEKSTOP_TOOL
        //# Rectangle r = g.getClipBounds();
        //# clipX = (int)r.getX();
        //# clipY = (int)r.getY();
        //# clipWidth = (int)r.getWidth();
        //# clipHeight = (int)r.getHeight();
        //#elifdef J2ME
       //# clipX = g.getClipX();
       //# clipY = g.getClipY();
       //# clipWidth = g.getClipWidth();
       //# clipHeight = g.getClipHeight();
         //#else
        g.save();
        //#endif
       
        //
        // drawing top Strip
        int startX = x + Util.getImageWidth(leftTopImage);
        int endX = x + width  - Util.getImageWidth(leftTopImage);
        //#ifdef ANDROID
        g.clipRect(startX, y, startX + width - (Util.getImageWidth(leftTopImage) * 2), y + height);
        //#else
        //# g.setClip(startX, y, width - (Util.getImageWidth(leftTopImage) * 2), height);
        //#endif
        
        for (int i = startX ; i < endX ; i+= Util.getImageWidth(topImage)) {
        	Util.drawImage(g, topImage, i, y,paintObject);
        }
        // drawing bottom Strip
        startX = x + Util.getImageWidth(leftTopImage);
        endX = x + width - Util.getImageWidth(leftTopImage);
       
        for (int i = startX ; i < endX ; i+= Util.getImageWidth(topImage)) {
            Util.drawImage(g, bottomImage, i, y + height - Util.getImageHeight(topImage),paintObject);
        }
//        
       
        // drawing left Strip
        int startY = y + Util.getImageHeight(leftTopImage);
        int endY = y + height  - Util.getImageHeight(leftTopImage) ;
        //#ifdef ANDROID
        g.restore();
        g.save();
        g.clipRect(x, startY,x+ width , startY + height - (Util.getImageHeight(leftTopImage) * 2));
        //#else
         //# g.setClip(x, startY, width , height - (Util.getImageHeight(leftTopImage) * 2));
        //#endif
       
        for (int i = startY ; i < endY ; i+= Util.getImageHeight(leftImage)) {
        	Util.drawImage(g, leftImage, x, i,paintObject);
        }
//        
        // drawing right Strip
        startY = y + Util.getImageHeight(leftTopImage);
        endY = y + height  - Util.getImageHeight(leftTopImage) ;
        for (int i = startY ; i < endY ; i+= Util.getImageHeight(leftImage)) {
            Util.drawImage(g, rightImage, x + width - Util.getImageWidth(leftImage), i,paintObject);
        }
       //#ifdef ANDROID
        g.restore();
        g.save();
        g.clipRect( x + Util.getImageWidth(leftImage) , y + Util.getImageHeight(topImage),x + width -  Util.getImageWidth(leftImage) ,y+ height - Util.getImageHeight(topImage));
        //#else
        //# g.setClip( x + Util.getImageWidth(leftImage), y + Util.getImageHeight(topImage), width -  Util.getImageWidth(leftImage) -  Util.getImageWidth(leftImage), height - Util.getImageHeight(topImage) - Util.getImageHeight(topImage));
        //#endif
        
        if(color != -1)
        {
            //#ifdef ANDROID
            paintObject.setColor(Util.getColor(color));
            GraphicsUtil.fillRect(x + Util.getImageWidth(leftImage), y + Util.getImageHeight(topImage), width -  Util.getImageWidth(leftImage) -  Util.getImageWidth(leftImage), height - Util.getImageHeight(topImage) - Util.getImageHeight(topImage), g, paintObject);
            //#else
            //# g.setColor(Util.getColor(color));
            //# g.fillRect(x + Util.getImageWidth(leftImage), y + Util.getImageHeight(topImage), width -  Util.getImageWidth(leftImage) -  Util.getImageWidth(leftImage), height - Util.getImageHeight(topImage) - Util.getImageHeight(topImage));
            //#endif
           

        }
        if(baseImage != null)
        {
             //#ifdef ANDROID
            fillImageRect(g, x + Util.getImageWidth(leftImage), y + Util.getImageHeight(topImage), width -  Util.getImageWidth(leftImage) -  Util.getImageWidth(leftImage), height - Util.getImageHeight(topImage) - Util.getImageHeight(topImage), baseImage,paintObject);
            //#else
            //# fillImageRect(g, x + Util.getImageWidth(leftImage), y + Util.getImageHeight(topImage), width -  Util.getImageWidth(leftImage) -  Util.getImageWidth(leftImage), height - Util.getImageHeight(topImage) - Util.getImageHeight(topImage), baseImage);
            //#endif
        }
        //#ifdef ANDROID
        g.restore();
        //#else
        //# g.setClip(clipX, clipY, clipWidth, clipHeight);
        //#endif
        
         // drawing corners
        Util.drawImage(g, leftTopImage, x, y,paintObject);
        Util.drawImage(g, rightTopImage, x + width - Util.getImageWidth(leftTopImage), y,paintObject);
        Util.drawImage(g, leftBottomImage,x , y + height - Util.getImageHeight(leftTopImage),paintObject);
        Util.drawImage(g, rightBottomImage, x + width- Util.getImageWidth(leftTopImage), y + height - Util.getImageHeight(leftTopImage),paintObject);
       
        
    }
      //#ifdef ANDROID
    private void fillImageRect(Canvas g,int x,int y,int width,int height,Bitmap image, Paint paintObject)
    //#else
    //# private void fillImageRect(Graphics g,int x,int y,int width,int height,Image image)
    //#endif
    {
        int _maxW = x + width + Util.getImageWidth(image);
        int w = Util.getImageWidth(image);
        int _maxH = y + height + Util.getImageHeight(image);
        int h = Util.getImageHeight(image);
        for (int i = x; i <= _maxW; i+= w) {
            for (int j = y; j <= _maxH; j+= h) {
            	Util.drawImage(g, image, i, j,paintObject);
            }
        }
    }
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

  

   

//    @Override
//    public boolean equals(Object obj) {
//       if(obj == null)
//           return false;
//       CornersPNGBox box = (CornersPNGBox)obj;
//       return (getLeftTopResourceIndex() == box.getLeftTopResourceIndex() && getLeftResourceIndex() == box.getLeftResourceIndex() && getTopResourceIndex() == box.getTopResourceIndex() && color == box.getColor());
//    }
//
//    @Override
//    public byte[] serialize() throws Exception {
//       ByteArrayOutputStream bos = new ByteArrayOutputStream();
//       Util.writeInt(bos, getLeftTopResourceIndex(), 2);
//       Util.writeInt(bos, getTopResourceIndex(), 2);
//       Util.writeInt(bos, getLeftResourceIndex(), 2);
//       Util.writeColor(bos, color);
//       return bos.toByteArray();
//    }
//
//    @Override
//    public ByteArrayInputStream deserialize(byte[] data) throws Exception {
//       ByteArrayInputStream bis = new ByteArrayInputStream(data);
//       setLeftTopResourceIndex(Util.readInt(bis, 2));
//       setTopResourceIndex(Util.readInt(bis, 2));
//       setLeftResourceIndex(Util.readInt(bis, 2));
//       setColor(Util.readColor(bis));
//       setLeftTopImage(ResourceManager.getInstance().getImageResource(getLeftTopResourceIndex()));
//       setLeftImage(ResourceManager.getInstance().getImageResource(getLeftResourceIndex()));
//       setTopImage(ResourceManager.getInstance().getImageResource(getTopResourceIndex()));
//       return bis;
//    }
//
//    @Override
//    public int getClassCode() {
//        return MenuSerilize.CORNER_PNG_BOX;
//    }
//   
    public void cleanup()
    {
         leftTopImage =  topImage = leftImage = baseImage = null;
    }
}
