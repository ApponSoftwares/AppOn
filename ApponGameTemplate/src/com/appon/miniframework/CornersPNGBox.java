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
public class CornersPNGBox 
{
    //#ifdef ANDROID
    private Bitmap leftTopImage;
    private Bitmap topImage;
    private Bitmap leftImage;
    private Bitmap baseImage;
      public Bitmap getBaseImage() {
        return baseImage;
    }
      public void setBaseImage(Bitmap baseBitmap) {
        this.baseImage = baseBitmap;
    }
        public Bitmap getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(Bitmap leftBitmap) {
        this.leftImage = leftBitmap;
    }
      public Bitmap getLeftTopImage() {
        return leftTopImage;
    }

    public void setLeftTopImage(Bitmap leftTopBitmap) {
        this.leftTopImage = leftTopBitmap;
    }
     public Bitmap getTopImage() {
        return topImage;
    }

    public void setTopImage(Bitmap topBitmap) {
        this.topImage = topBitmap;
    }

    //#else
    //# private Image leftTopImage;
    //# private Image topImage;
    //# private Image leftImage;
    //# private Image baseImage;
    //# public Image getBaseImage() {
        //# return baseImage;
    //# }
      //# public void setBaseImage(Image baseBitmap) {
        //# this.baseImage = baseBitmap;
    //# }
    //# public Image getLeftImage() {
        //# return leftImage;
    //# }
//# 
    //# public void setLeftImage(Image leftBitmap) {
        //# this.leftImage = leftBitmap;
    //# }
      //# public Image getLeftTopImage() {
        //# return leftTopImage;
    //# }
//# 
    //# public void setLeftTopImage(Image leftTopBitmap) {
        //# this.leftTopImage = leftTopBitmap;
    //# }
     //# public Image getTopImage() {
        //# return topImage;
    //# }
//# 
    //# public void setTopImage(Image topBitmap) {
        //# this.topImage = topBitmap;
    //# }
//# 
    //#endif
    
    
    private int color;
    
    private int leftTopResourceIndex;
    private int topResourceIndex;
    private int leftResourceIndex;
    private int baseResourceIndex = -1;

    //#ifdef ANDROID
      public CornersPNGBox(Bitmap leftTopImage, Bitmap topImage, Bitmap leftImage,int color,Bitmap baseBitmap) {
    //#else
    //# public CornersPNGBox(Image leftTopImage, Image topImage, Image leftImage,int color,Image baseBitmap) {
    //#endif
  
        this.leftTopImage = leftTopImage;
        this.topImage = topImage;
        this.leftImage = leftImage;
        this.color = color;
        this.baseImage = baseBitmap;
    }

    public CornersPNGBox() {
    }

  

    public void setBaseResourceIndex(int baseResourceIndex) {
        this.baseResourceIndex = baseResourceIndex;
    }

  

    public int getBaseResourceIndex() {
        return baseResourceIndex;
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
            GraphicsUtil.drawRegion(g,topImage, i, y + height - Util.getImageHeight(topImage),GraphicsUtil.ROTATE_180 ,0,paintObject);
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
            GraphicsUtil.drawRegion(g, leftImage, x + width - Util.getImageWidth(leftImage), i, GraphicsUtil.MIRROR, 0,paintObject);
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
        
        //        // drawing corners
        Util.drawImage(g, leftTopImage, x, y,paintObject);
        GraphicsUtil.drawRegion(g, leftTopImage, x + width - Util.getImageWidth(leftTopImage), y, GraphicsUtil.MIRROR, 0,paintObject);
        //#ifdef ANDROID
        GraphicsUtil.drawRegion(g, leftTopImage, x , y + height - Util.getImageHeight(leftTopImage),GraphicsUtil.ROTATE_180 | GraphicsUtil.MIRROR, 0,paintObject);
        GraphicsUtil.drawRegion(g, leftTopImage, x + width- Util.getImageWidth(leftTopImage), y + height - Util.getImageHeight(leftTopImage), GraphicsUtil.ROTATE_180 , 0,paintObject);
        //#elifdef J2ME
        //# GraphicsUtil.drawRegion(g, leftTopImage, x , y + height - Util.getImageHeight(leftTopImage),GraphicsUtil.ROTATE_180| GraphicsUtil.MIRROR  , 0);
        //# GraphicsUtil.drawRegion(g, leftTopImage, x + width- Util.getImageWidth(leftTopImage), y + height - Util.getImageHeight(leftTopImage), GraphicsUtil.ROTATE_180 , 0);
        //#else
        //# GraphicsUtil.drawRegion(g, leftTopImage, x , y + height - Util.getImageHeight(leftTopImage),GraphicsUtil.ROTATE_180  , 0);
        //# GraphicsUtil.drawRegion(g, leftTopImage, x + width- Util.getImageWidth(leftTopImage), y + height - Util.getImageHeight(leftTopImage), GraphicsUtil.ROTATE_180 | GraphicsUtil.MIRROR, 0);
        //#endif
        
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

  

    public int getLeftResourceIndex() {
        return leftResourceIndex;
    }

    public void setLeftResourceIndex(int leftResourceIndex) {
        this.leftResourceIndex = leftResourceIndex;
    }

  

    public int getLeftTopResourceIndex() {
        return leftTopResourceIndex;
    }

    public void setLeftTopResourceIndex(int leftTopResourceIndex) {
        this.leftTopResourceIndex = leftTopResourceIndex;
    }

   
    public int getTopResourceIndex() {
        return topResourceIndex;
    }

    public void setTopResourceIndex(int topResourceIndex) {
        this.topResourceIndex = topResourceIndex;
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
