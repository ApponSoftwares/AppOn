package com.appon.animlib.core;

//#ifdef DEKSTOP_TOOL
//# import com.appon.tool.ui.customeditors.CustomCanvas;
//# import java.awt.*;
//# import java.awt.geom.AffineTransform;
//# import java.awt.image.AffineTransformOp;
//# import java.awt.image.BufferedImage;
//#else
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
//#endif










import com.appon.animlib.AnimationGroupSupport;
import com.appon.animlib.ENAnimationGroup;
import com.appon.animlib.ImagePack;
import com.appon.animlib.util.Point;
import com.appon.animlib.util.Util;

public class GraphicsUtil {

	// #ifdef ANDROID
	private static Paint paintObj = new Paint();
	public static void drawImage(Canvas g,int moduleId, AnimationGroupSupport parent, double x, double y, double newWidth, double newHeight, boolean flipX, boolean flipY, double thetaRotate, int tintColor, int alpha) {
		g.save();
		
		ColorFilter filter = paintObj.getColorFilter();
		int backupalpha=paintObj.getAlpha();
		if(tintColor != -1)
			paintObj.setColorFilter(new LightingColorFilter(getColor(tintColor),0));
		paintObj.setAlpha(alpha);
		
		ImagePack pack = ((ENAnimationGroup) parent).getImagePack();
		Bitmap b = pack.getImage(moduleId);

		
		float scaleX = (float) (newWidth / b.getWidth());
		float scaleY = (float) (newHeight / b.getHeight());
		float px = (float) (x + (b.getWidth() / 2));
		float py = (float) (y + (b.getHeight() / 2));
		
		float px1 = (float)(x + (newWidth /2));
     	float py1 = (float)(y + (newHeight /2));
		//g.scale(-1, 1, (float) px1, (float) py1);
		
		matrix.reset();
		matrix.postTranslate((float)x,(float)y);
		matrix.postScale(flipX ? -1 : 1, flipY ? -1 : 1,(float)px,(float)py);
        matrix.postScale((float)(scaleX),(float)scaleY,(float)x,(float)y);
        matrix.postRotate((float)thetaRotate,px1,py1);
        g.drawBitmap(b, matrix, paintObj);
	
		paintObj.setColorFilter(filter);
		paintObj.setAlpha(backupalpha);
		g.restore();
	}
	// #endif
	 public static void drawRegion(Canvas canvas, Bitmap bitmap,float x,float y,float angle, boolean flipX,boolean flipY,Paint paintObject){
	  	com.appon.gtantra.GraphicsUtil.drawBitmap(canvas,bitmap,(int)x,(int)y,0,paintObject);	   
	 }
	 public static int getColor(int color)
	   {
			long val = color & 0xffffffff;
			int b = (int)(val & 0x00FF);
		    int g = (int) ((val >> 8) & 0x000000FF);
		    int r = (int) ((val >> 16) & 0x000000FF);
		    int a = (int) ((val >> 24) & 0x000000FF);
		    if(a == 0)
		    {
		        a = 255;
		    }
		    return (a << 24) + (r << 16) + (g << 8) + (b);
		
	   }
	private static Matrix matrix = new Matrix();
	private static void paintRoatateImage(Canvas g, Bitmap b, float x, float y,float thetaRotate, Paint paintObj) {
		matrix.reset();
        matrix.postTranslate(x,y);
        matrix.postRotate(thetaRotate,x+( b.getWidth()>>1),y+( b.getHeight()>>1));
        g.drawBitmap(b, matrix, paintObj);
	}
//	
	
////if(flipX)
////	scaleX=-scaleX;
////if(flipY)
////	scaleY=-scaleY;
//
////float px1 = (float)(x + (newWidth /2));
////float py1 = (float)(y + (newHeight /2));
//float px = (float) (x + (b.getWidth() / 2));
//float py = (float) (y + (b.getHeight() / 2));
////
////if (scaleX != 1f || scaleY != 1f)
////	g.scale(scaleX, scaleY, (float) x, (float) y);
////
////if (flipX || flipY){
////	g.scale(flipX ? -1 : 1, flipY ? -1 : 1, px, py);
////	g.translate(flipX ? -b.getWidth() : (float)x, flipY ? -b.getHeight() : (float)y);
////}
////
////if(thetaRotate!=0)
////   g.rotate((float)thetaRotate, px, py);
////
////g.drawBitmap(b, (float) x, (float) y, paintObj);
//
//matrix.reset();
//matrix.postTranslate((float)x,(float)y);
//matrix.postScale((float)(scaleX),(float)scaleY,(float)x,(float)y);
//matrix.postRotate((float)thetaRotate,px,py);
//
//
//g.drawBitmap(b, matrix, paintObj);
//
//
	// #ifdef DEKSTOP_TOOL
	// # public static void drawImage(Graphics g, Image bitmap, double x, double
	// y,double newWidth,double newHeight, boolean flipX,boolean flipY,double
	// thetaRotate,int tintColor,int alpha)
	// # {
	// #
	// # int transX = 0;
	// # int transY = 0;
	// # int scaleX = 1;
	// # int scaleY = 1;
	// # if( flipX )
	// # {
	// # scaleX = -1;
	// # transX = -bitmap.getWidth(null);
	// # }
	// # if( flipY)
	// # {
	// # scaleY = -1;
	// # transY = -bitmap.getHeight(null);
	// # }
	// #
	// # BufferedImage bufferedImage = new BufferedImage(bitmap.getWidth(null),
	// bitmap.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	// # Graphics2D g2d = (Graphics2D) g;
	// # Graphics gb = bufferedImage.getGraphics();
	// # gb.drawImage(bitmap, 0, 0, null);
	// # gb.dispose();
	// #
	// # AffineTransform tx = AffineTransform.getScaleInstance( (newWidth /
	// bufferedImage.getWidth())*scaleX,(newHeight /
	// bufferedImage.getHeight())*scaleY);
	// # tx.translate(transX, transY);
	// # AffineTransformOp op = new AffineTransformOp(tx,
	// AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	// # bufferedImage = op.filter(bufferedImage, null);
	// # if(tintColor != -1)
	// # {
	// # bufferedImage = getTintedImage(bufferedImage,tintColor);
	// # }
	// # g2d = (Graphics2D)g2d.create();
	// #
	// # g2d.rotate(Math.toRadians(thetaRotate),x +
	// CustomCanvas.roundTheValues(newWidth / 2),y +
	// CustomCanvas.roundTheValues(newHeight / 2));
	// # if(alpha < 255 && alpha > 0)
	// # {
	// # AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	// ((float)alpha / 255f));
	// # g2d.setComposite(ac);
	// # }
	// # g2d.drawImage(bufferedImage, null, (int)x, (int)y);
	// # g2d.dispose();
	// # }
	// #
	// #
	// # public static BufferedImage getTintedImage(BufferedImage master,int
	// color)
	// # {
	// # long l = (long)color & 0xFFFFFFFFL;
	// # //arbg
	// # int g = (int)(l & 0xff);
	// # int b = (int)((l >> 8) & 0xff);
	// # int r = (int)((l >> 16) & 0xff);
	// # int a = (int)((l >> 24) & 0xff);
	// # if(a == 0)
	// # {
	// # a = 255;
	// # }
	// # // System.out.println(" color.getAlpha(): "+ color.getAlpha());
	// # BufferedImage mask = generateMask(master, new Color(color),(float)
	// ((float)a/ 255f));
	// # return tint(master, mask);
	// # }
	// # public static GraphicsConfiguration getGraphicsConfiguration() {
	// # return
	// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	// # }
	// # public static BufferedImage createCompatibleImage(int width, int
	// height, int transparency) {
	// # BufferedImage image =
	// getGraphicsConfiguration().createCompatibleImage(width, height,
	// transparency);
	// # image.coerceData(true);
	// # return image;
	// # }
	// # public static BufferedImage tint(BufferedImage master, BufferedImage
	// tint) {
	// # int imgWidth = master.getWidth();
	// # int imgHeight = master.getHeight();
	// #
	// # BufferedImage tinted = createCompatibleImage(imgWidth, imgHeight,
	// Transparency.TRANSLUCENT);
	// # Graphics2D g2 = tinted.createGraphics();
	// # applyQualityRenderingHints(g2);
	// # g2.drawImage(master, 0, 0, null);
	// # g2.drawImage(tint, 0, 0, null);
	// # g2.dispose();
	// #
	// # return tinted;
	// # }
	// # public static void applyQualityRenderingHints(Graphics2D g2d) {
	// # g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
	// RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	// # g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);
	// # g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
	// RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	// # g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
	// RenderingHints.VALUE_DITHER_ENABLE);
	// # g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
	// RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	// # g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	// # g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
	// RenderingHints.VALUE_RENDER_QUALITY);
	// # g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
	// RenderingHints.VALUE_STROKE_PURE);
	// # }
	// # public static BufferedImage generateMask(BufferedImage imgSource, Color
	// color, float alpha) {
	// # int imgWidth = imgSource.getWidth();
	// # int imgHeight = imgSource.getHeight();
	// #
	// # BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight,
	// Transparency.TRANSLUCENT);
	// # Graphics2D g2 = imgMask.createGraphics();
	// # applyQualityRenderingHints(g2);
	// #
	// # g2.drawImage(imgSource, 0, 0, null);
	// # g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,
	// alpha));
	// # g2.setColor(color);
	// #
	// # g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
	// # g2.dispose();
	// #
	// # return imgMask;
	// # }
	// #endif

}
