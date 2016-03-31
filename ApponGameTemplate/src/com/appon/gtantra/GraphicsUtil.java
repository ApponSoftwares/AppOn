package com.appon.gtantra;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;

public class GraphicsUtil {
	private static Matrix matrix = new Matrix();

	public final static int ORIGINAL = 0;
	public final static int ROTATE_90 = 1;
	public final static int ROTATE_180 = 2;
	public final static int ROTATE_270 = 4;
	public final static int MIRROR = 8;

	public final static int LEFT = 1;
	public final static int RIGHT = 2;
	public final static int TOP = 4;
	public final static int BOTTOM = 8;
	public final static int HCENTER = 16;
	public final static int VCENTER = 64;
	public final static int BASELINE = 256;

	private static Paint paint = new Paint();
	private static Path path2=new Path();
	public static void drawReverseRectangles(Canvas g,int x1, int y1,int width,int height,int alpha,int colorToFill,int startX,int startY,int totalW,int totalH,Paint p)
	{
		paint.setStrokeWidth(2);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		path2.reset();
		path2.addRect(x1, y1, x1 + width, y1 + height, Path.Direction.CCW);
		g.save();
		g.clipPath(path2,Region.Op.DIFFERENCE);
		p.setColor(colorToFill);
		p.setAlpha(alpha);
		p.setStyle(Paint.Style.FILL);
		g.drawRect(startX, startY, totalW, totalH, p);
		g.restore();
	}

	public static void drawBorder(Canvas g,int x, int y,int width,int height,int alpha,int colorToFill,int thickness,Paint p)
	{
		float strokeBackup = p.getStrokeWidth(); 
		p.setStrokeWidth(thickness);
		p.setStyle(Paint.Style.STROKE);
		p.setColor(colorToFill);
		p.setAlpha(alpha);
		g.drawRect((new RectF(x, y, x + width, y + height)), p);
		p.setStrokeWidth(strokeBackup);
	}

	public static void drawRoundBorder(Canvas g,int x, int y,int width,int height,int alpha,int colorToFill,int thickness,Paint p)
	{
		float strokeBackup = p.getStrokeWidth(); 
		p.setStrokeWidth(thickness);
		p.setStyle(Paint.Style.STROKE);
		p.setColor(colorToFill);
		p.setAlpha(alpha);
		g.drawRoundRect(new RectF(x, y, x + width, y + height) , ((int)height>>3), ((int)height>>3),p);
		p.setStrokeWidth(strokeBackup);
	}
	public static void fillRect(float x, float y,float width, float height,int color ,Canvas c, Paint p)
	{
		c.save();
		p.setColor(color);
		p.setStyle(Paint.Style.FILL);
		c.drawRect(x, y, x + width, y + height, p);
		c.restore();
	}
	public static void fillGradientRect(float x, float y, float width, float height, Canvas c, Paint p, int colors[]) {
		p.setShader(new LinearGradient(x, y, x, y + height, colors, null, Shader.TileMode.MIRROR));
		c.save();
		p.setStyle(Paint.Style.FILL);
		c.drawRect(x, y, x + width, y + height, p);
		c.restore();
		p.reset();
	}

	public static void fillGradientRoundRect(float x, float y, float width, float height, Canvas c, Paint p, int colors[]) {
		p.setShader(new LinearGradient(x, y, x, y + height, colors, null, Shader.TileMode.MIRROR));
		c.save();
		p.setStyle(Paint.Style.FILL);
		c.drawRoundRect(new RectF(x, y, x + width, y + height) , ((int)height>>3), ((int)height>>3),p);
		c.restore();
		p.reset();
	}

	public static void fillArc(Canvas g,int x,int y,int width,int height,int startAngle,int endAngle,Paint p)
	{
		g.save();
		p.setStyle(Paint.Style.FILL);
		g.drawArc(new RectF(x, y, x + width, y + height), startAngle, endAngle, true, p);
		g.restore();
	}
	private static void drawArcUtil(Canvas g,int x,int y,int r,int startAngle,int endAngle,Paint p)
	{
		g.drawArc(new RectF(x - (r >> 1), y - (r >> 1), x +  (r >> 1), y +   (r >> 1)), startAngle, endAngle, false, p);
	}
	public static void drawCircleEffect(Canvas g,int cx,int cy,int r,int startAngle,int endAngle)
	{
		float outerStroke = 20f;
		Paint _paintSimple = new Paint();
		_paintSimple.setAntiAlias(true);
		_paintSimple.setDither(true);
		_paintSimple.setColor(Color.argb(150, 0x07, 0xe0,0xb0));
		_paintSimple.setStrokeWidth(3f);
		_paintSimple.setStyle(Paint.Style.STROKE);
		_paintSimple.setStrokeJoin(Paint.Join.ROUND);
		_paintSimple.setStrokeCap(Paint.Cap.ROUND);

		Paint _paintBlur = new Paint();
		_paintBlur.set(_paintSimple);
		_paintBlur.setColor(Color.argb(150, 0x1c, 0x81, 0xb1));
		_paintBlur.setStrokeWidth(outerStroke);
		_paintBlur.setMaskFilter(new BlurMaskFilter(12, BlurMaskFilter.Blur.NORMAL));

		Paint paintBubble = new Paint();
		paintBubble.setStyle(Paint.Style.FILL);
		paintBubble.setColor(Color.argb(40, 0x1c, 0x81, 0xb1));
		drawArcUtil(g, cx, cy,r, startAngle, endAngle, paintBubble);
		drawArcUtil(g, cx, cy,(int)(r - (outerStroke - 4)), startAngle, endAngle, _paintBlur);
		drawArcUtil(g, cx, cy,r, startAngle, endAngle, _paintSimple);   
	}
	public static void drawArc(Canvas g,int x,int y,int width,int height,int startAngle,int endAngle,Paint p)
	{
		g.drawArc(new RectF(x, y, x + width, y + height), startAngle, endAngle, false, p);
	}
	public static void drawRect(float x, float y,float width, float height,Canvas c, Paint p)
	{
		c.save();
		p.setStyle(Paint.Style.STROKE);
		c.drawRect(x, y, x + width, y + height, p);
		c.restore();
	}
	public static void fillRect(float x, float y,float width, float height,Canvas c, Paint p)
	{
		c.save();
		p.setStyle(Paint.Style.FILL);
		c.drawRect(x, y, x + width, y + height, p);
		c.restore();
	}

	public static void drawRGB(int[] rgbData, int offset, int scanLength, int x, int y, int width, int height, boolean processAlpha,Canvas c) {
		c.drawBitmap(rgbData, offset, width, x , y , width, height, processAlpha, null);
	}
	public static void drawLine(float x, float y,float endx, float endy,Canvas c, Paint p)
	{
		c.save();
		p.setStyle(Paint.Style.STROKE);
		c.drawLine(x, y,endx ,endy,p);
		c.restore();
	}

	public static void paintRescaleImage(Canvas g,Bitmap bitmap,long x,int y,float percentage,Paint p)
	{
		matrix.reset();
		matrix.postScale(percentage/100,percentage/100);
		matrix.postTranslate(x,y);
		g.drawBitmap(bitmap, matrix, p);
	}
	public static void paintRescaleImage(Canvas g,Bitmap bitmap,long x,int y,float widthPercentage,float heightPercentage,Paint p)
	{
		matrix.reset();
		matrix.postScale(widthPercentage/100,heightPercentage/100);
		matrix.postTranslate(x,y);
		g.drawBitmap(bitmap, matrix, p);

	}
	public static void paintRoatateRescaleImage(Canvas g,Bitmap bitmap,long x,int y,long centerX,int centerY,int angle,float percentage,Paint p)
	{
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		float newWidth = (width * percentage)/ 100;
		float newHeight = (height * percentage)/ 100;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		matrix.reset();
		matrix.postScale(scaleWidth,scaleHeight );
		matrix.postTranslate(x,y);
		matrix.postRotate(angle, centerX,centerY);
		g.drawBitmap(bitmap, matrix, p);

	}



	public static void paintRoatateImage(Canvas g,Bitmap bitmap,long x,int y,int angle,Paint p)
	{
		matrix.reset();
		matrix.postTranslate(x,y);
		matrix.postRotate(angle,x+(bitmap.getWidth()>>1),y+(bitmap.getHeight()>>1));
		g.drawBitmap(bitmap, matrix, p);

	}
	public static void paintRoatateImage(Canvas g,Bitmap bitmap,long x,int y,int angle,boolean iscale,int percentage,Paint p)
	{
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		float newWidth = (width * percentage)/ 100;
		float newHeight = (height * percentage)/ 100;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		x+=((width-newWidth)*(0.5f));
		y+=((height-newHeight)*(0.5f));
		matrix.reset();
		matrix.postScale(scaleWidth,scaleHeight );
		matrix.postTranslate(x,y);
		matrix.postRotate(angle,x+(newWidth*(0.5f)),y+(newHeight*(0.5f)));
		g.drawBitmap(bitmap, matrix, p);

	}

	public static void paintRescaleImageAtCenter(Canvas g,Bitmap bitmap,long x,int y,float percentage)
	{
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		float newWidth = (width * percentage)/ 100;
		float newHeight = (height * percentage)/ 100;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		x+=((width-newWidth)*(0.5f));
		y+=((height-newHeight)*(0.5f));

		matrix.reset();
		matrix.postScale(scaleWidth,scaleHeight );
		matrix.postTranslate(x,y);
		g.drawBitmap(bitmap, matrix, null);

	}
	public static void paintRescaleImageAtCenter(Canvas g,Bitmap bitmap,long x,int y,float percentage,Paint p)
	{
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		float newWidth = (width * percentage)/ 100;
		float newHeight = (height * percentage)/ 100;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		x+=((width-newWidth)*(0.5f));
		y+=((height-newHeight)*(0.5f));

		matrix.reset();
		matrix.postScale(scaleWidth,scaleHeight );
		matrix.postTranslate(x,y);
		g.drawBitmap(bitmap, matrix, p);

	}

	public static int getRescaleIamgeHeight(int height,int percentage)
	{
		return ((height * percentage)/ 100);
	}
	public static int getRescaleIamgeWidth(int width,int percentage)
	{
		return ((width * percentage)/ 100);
	}

	public static void drawBitmap(Canvas canvas, Bitmap bitmap, int x, int y,int anchor,Paint p)
	{
		if((anchor & RIGHT) != 0)
		{
			x = x - bitmap.getWidth();
		}
		if((anchor & BOTTOM) != 0)
		{
			y = y - bitmap.getHeight();
		}
		if((anchor & HCENTER) != 0)
		{
			x = x - (bitmap.getWidth() >> 1);
		}
		if((anchor & VCENTER) != 0)
		{
			y = y - (bitmap.getHeight() >> 1);
		}

		canvas.drawBitmap(bitmap,x,y, p);
	}
	public static void drawBitmap(Canvas canvas, Bitmap bitmap, long l, int y,int anchor,Paint paint)
	{

		if((anchor & RIGHT) != 0)
		{
			l = l - bitmap.getWidth();
		}
		if((anchor & BOTTOM) != 0)
		{
			y = y - bitmap.getHeight();
		}
		if((anchor & HCENTER) != 0)
		{
			l = l - (bitmap.getWidth() >> 1);
		}
		if((anchor & VCENTER) != 0)
		{
			y = y - (bitmap.getHeight() >> 1);
		}
		canvas.drawBitmap(bitmap,l,y, paint);
	}

	public static void drawBitmap(Canvas canvas, Bitmap bitmap, long l, int y,int anchor,boolean isScale,float scalePercentage,Paint paintObject)
	{

		if((anchor & RIGHT) != 0)
		{
			l = l - bitmap.getWidth();
		}
		if((anchor & BOTTOM) != 0)
		{
			y = y - bitmap.getHeight();
		}
		if((anchor & HCENTER) != 0)
		{
			l = l - (bitmap.getWidth() >> 1);
		}
		if((anchor & VCENTER) != 0)
		{
			y = y - (bitmap.getHeight() >> 1);
		}
		if(isScale)
			paintRescaleImage(canvas, bitmap,l,y,scalePercentage,paintObject);	
		else	
			canvas.drawBitmap(bitmap,l,y, paintObject);
	}
	public static void drawBitmap(Canvas canvas, Bitmap bitmap, long l, int y,long centerX,int centerY,int anchor,int angle,boolean isScale,float scalePercentage,Paint p)
	{

		if((anchor & RIGHT) != 0)
		{
			l = l - bitmap.getWidth();
		}
		if((anchor & BOTTOM) != 0)
		{
			y = y - bitmap.getHeight();
		}
		if((anchor & HCENTER) != 0)
		{
			l = l - (bitmap.getWidth() >> 1);
		}
		if((anchor & VCENTER) != 0)
		{
			y = y - (bitmap.getHeight() >> 1);
		}
		if(isScale)
		{	if(angle!=0)
		{
			paintRoatateRescaleImage(canvas, bitmap, l, y,centerX,centerY, angle, scalePercentage, p);
		}
		else
			paintRescaleImage(canvas, bitmap,l,y,scalePercentage,p);	
		}
		else	
		{
			if(angle!=0)
				rotateBitmap(canvas, bitmap, angle,(int)l,y, (GraphicsUtil.HCENTER|GraphicsUtil.VCENTER), p);
			else	
				canvas.drawBitmap(bitmap,l,y, p);
		}
	}
	public static void rotateBitmap(Canvas canvas, Bitmap bitmap, int angle, int x, int y,int anchor,Paint p){
		//we conside the angle of rotatedd images is left top
		Bitmap oldBitmap = bitmap;
		int w = oldBitmap.getWidth();
		int h = oldBitmap.getHeight();
		Matrix mtx = new Matrix();
		mtx.postRotate(angle,w>>1,h>>1);
		oldBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, w, h, mtx, true);

		if((anchor & RIGHT) != 0)
		{
			x = x - oldBitmap.getWidth();
		}
		if((anchor & BOTTOM) != 0)
		{
			y = y - oldBitmap.getHeight();
		}
		if((anchor & HCENTER) != 0)
		{
			x = x - (oldBitmap.getWidth() >> 1);
		}
		if((anchor & VCENTER) != 0)
		{
			y = y - (oldBitmap.getHeight() >> 1);
		}
		canvas.drawBitmap(oldBitmap,x,y, p);
	}

	public static void drawRegion(Canvas canvas, Bitmap bitmap, float x, float y, int transform,int anchor,Paint paint){

		canvas.save();
		if((transform & MIRROR)!= 0 )
		{
			canvas.scale(-1, 1);
			canvas.translate(-2*x-bitmap.getWidth(), 0);
		}
		if((transform & ROTATE_90)!= 0 )
		{
			canvas.translate(bitmap.getHeight(), 0);
			canvas.rotate(90, x, y);
		}
		else if((transform & ROTATE_180)!= 0 )
		{
			canvas.translate(bitmap.getWidth(), bitmap.getHeight());
			canvas.rotate(180, x, y);
		}else if((transform & ROTATE_270)!= 0 )
		{
			canvas.translate(0, bitmap.getWidth());
			canvas.rotate(270, x, y);
		}
		drawBitmap(canvas,bitmap,(int)x,(int)y,anchor,paint);
		canvas.restore();
	}

	public static void drawRegion(Canvas canvas, Bitmap bitmap, float x, float y, int transform,int anchor,boolean isScale,float scalePercentage,Paint paintObject){

		canvas.save();
		if((transform & MIRROR)!= 0 )
		{
			canvas.scale(-1, 1);
			canvas.translate(-2*x-bitmap.getWidth(), 0);
		}
		if((transform & ROTATE_90)!= 0 )
		{
			canvas.translate(bitmap.getHeight(), 0);
			canvas.rotate(90, x, y);
		}
		else if((transform & ROTATE_180)!= 0 )
		{
			canvas.translate(bitmap.getWidth(), bitmap.getHeight());
			canvas.rotate(180, x, y);
		}else if((transform & ROTATE_270)!= 0 )
		{
			canvas.translate(0, bitmap.getWidth());
			canvas.rotate(270, x, y);
		}
		drawBitmap(canvas,bitmap,(int)x,(int)y,anchor, isScale, scalePercentage,paintObject);
		canvas.restore();
	}
	public static void drawRegion(Canvas canvas, Bitmap bitmap, float x, float y,float centerX,float centerY, int transform,int anchor,int angle,boolean isScale,float scalePercentage,Paint p){

		canvas.save();
		if((transform & MIRROR)!= 0 )
		{
			canvas.scale(-1, 1);
			canvas.translate(-2*x-bitmap.getWidth(), 0);
		}
		if((transform & ROTATE_90)!= 0 )
		{
			canvas.translate(bitmap.getHeight(), 0);
			canvas.rotate(90, x, y);
		}
		else if((transform & ROTATE_180)!= 0 )
		{
			canvas.translate(bitmap.getWidth(), bitmap.getHeight());
			canvas.rotate(180, x, y);
		}else if((transform & ROTATE_270)!= 0 )
		{
			canvas.translate(0, bitmap.getWidth());
			canvas.rotate(270, x, y);
		}
		drawBitmap(canvas,bitmap,(int)x,(int)y,(int)centerX,(int)centerY,anchor,angle,isScale, scalePercentage,p);
		canvas.restore();
	} 
	public static void drawRegion(Canvas canvas, Bitmap bitmap,int xPos,int yPos,int width,int height,int transform, int x, int y, int anchor,Paint paintObject){
		canvas.save();
		Bitmap newBitMap = Bitmap.createBitmap(bitmap, xPos, yPos, width, height,null, false);
		if((transform & MIRROR)!= 0 )
		{
			canvas.scale(-1, 1);
			canvas.translate(-2*x-newBitMap.getWidth(), 0);
		}
		if((transform & ROTATE_90)!= 0 )
		{
			canvas.translate(newBitMap.getHeight(), 0);
			canvas.rotate(90, x, y);
		}
		else if((transform & ROTATE_180)!= 0 )
		{
			canvas.translate(newBitMap.getWidth(), newBitMap.getHeight());
			canvas.rotate(180, x, y);
		}else if((transform & ROTATE_270)!= 0 )
		{
			canvas.translate(0, newBitMap.getWidth());
			canvas.rotate(270, x, y);
		}
		drawBitmap(canvas,newBitMap,x,y,anchor,paintObject);

		canvas.restore();
	}

	public static void drawRegion(Canvas canvas, Bitmap bitmap,int xPos,int yPos,int width,int height,int transform, int x, int y, int anchor,boolean isScale,float scalePercentage,Paint paintObject){
		canvas.save();
		Bitmap newBitMap = Bitmap.createBitmap(bitmap, xPos, yPos, width, height,null, false);
		if((transform & MIRROR)!= 0 )
		{
			canvas.scale(-1, 1);
			canvas.translate(-2*x-newBitMap.getWidth(), 0);
		}
		if((transform & ROTATE_90)!= 0 )
		{
			canvas.translate(newBitMap.getHeight(), 0);
			canvas.rotate(90, x, y);
		}
		else if((transform & ROTATE_180)!= 0 )
		{
			canvas.translate(newBitMap.getWidth(), newBitMap.getHeight());
			canvas.rotate(180, x, y);
		}else if((transform & ROTATE_270)!= 0 )
		{
			canvas.translate(0, newBitMap.getWidth());
			canvas.rotate(270, x, y);
		}
		drawBitmap(canvas,newBitMap,x,y,anchor,isScale,scalePercentage,paintObject);

		canvas.restore();
	}

	public static void drawRegion(Canvas canvas, Bitmap bitmap,int xPos,int yPos,int centerX,int centerY,int width,int height,int transform, int x, int y, int anchor,int angle,boolean isScale,float scalePercentage,Paint P){
		canvas.save();
		Bitmap newBitMap = Bitmap.createBitmap(bitmap, xPos, yPos, width, height,null, false);
		if((transform & MIRROR)!= 0 )
		{
			canvas.scale(-1, 1);
			canvas.translate(-2*x-newBitMap.getWidth(), 0);
		}
		if((transform & ROTATE_90)!= 0 )
		{
			canvas.translate(newBitMap.getHeight(), 0);
			canvas.rotate(90, x, y);
		}
		else if((transform & ROTATE_180)!= 0 )
		{
			canvas.translate(newBitMap.getWidth(), newBitMap.getHeight());
			canvas.rotate(180, x, y);
		}else if((transform & ROTATE_270)!= 0 )
		{
			canvas.translate(0, newBitMap.getWidth());
			canvas.rotate(270, x, y);
		}
		drawBitmap(canvas,newBitMap,x,y,centerX,centerY,anchor,angle,isScale,scalePercentage,P);

		canvas.restore();
	} 

	public static void fillTriangle(Canvas g,Paint paint,int x1, int y1,int x2,int y2,int x3,int y3)
	{
		paint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		path.moveTo(x1,y1);	  
		path.lineTo(x2, y2);	
		path.lineTo(x3, y3);
		path.close();
		g.save();
		g.drawPath(path, paint);
		g.restore();
	}
	public static void fillPoly(Canvas g,Paint paint,int x1, int y1,int x2,int y2,int x3,int y3,int x4,int y4,int x5,int y5)
	{
		paint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		path.moveTo(x1,y1);	  
		path.lineTo(x2, y2);	
		path.lineTo(x3, y3);
		path.lineTo(x4, y4);
		path.lineTo(x5, y5);
		path.close();
		g.save();
		g.drawPath(path, paint);
		g.restore();
	}
	public static void fillPoly(Canvas g,Paint paint,int x1, int y1,int x2,int y2,int x3,int y3,int x4,int y4)
	{
		paint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		path.moveTo(x1,y1);	  
		path.lineTo(x2, y2);	
		path.lineTo(x3, y3);
		path.lineTo(x4, y4);
		path.close();
		g.save();
		g.drawPath(path, paint);
		g.restore();
	}


	private static int counter = 0;

	private static Paint _paintSimple=new Paint();
	private static Paint _paintBlur=new Paint();
	private static Paint _paintline=new Paint();
	public static void drawRadar(Canvas g,int x,int y,int r)
	{

		r = r << 1;
		float outerStroke = 70f;
		Paint _paintSimple = new Paint();
		_paintSimple.setAntiAlias(true);
		_paintSimple.setDither(true);
		_paintSimple.setColor(Color.argb(150, 0x07, 0xe0,0xb0));
		_paintSimple.setStrokeWidth(3f);
		_paintSimple.setStyle(Paint.Style.STROKE);
		_paintSimple.setStrokeJoin(Paint.Join.ROUND);
		_paintSimple.setStrokeCap(Paint.Cap.ROUND);

		Paint _paintBlur = new Paint();
		_paintBlur.set(_paintSimple);
		_paintBlur.setColor(0x8525900a);
		_paintBlur.setStrokeWidth(outerStroke);
		_paintBlur.setMaskFilter(new BlurMaskFilter(12, BlurMaskFilter.Blur.NORMAL));

		Paint paintBubble = new Paint();
		paintBubble.setStyle(Paint.Style.FILL);
		paintBubble.setColor(Color.argb(40, 0, 0xff, 0));
		drawArcUtil(g, x, y,r, 0, 360, paintBubble);
		drawArcUtil(g, x, y,(int)(r - (outerStroke - 4)), 0, 360, _paintBlur);
		drawArcUtil(g, x, y,r, 0, 360, _paintSimple); 

		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		int startAngle = counter ;
		int endAngle = 20 ;
		counter += 20;
		p.setColor(Color.argb(100, 0x0, 0xff,0x0));
		g.drawArc(new RectF(x - (r >> 1), y - (r >> 1), x +  (r >> 1), y +   (r >> 1)), startAngle, endAngle, true, p);
	}
	public static void drawRoundRect(float x, float y,float width, float height,Canvas c, Paint p)
	{
		c.save();
		p.setStyle(Paint.Style.STROKE);
		c.drawRoundRect(new RectF(x, y, x + width, y + height) ,((int)height>>3), ((int)height>>3),p);
		c.restore();
	}
	public static void fillRoundRect(float x, float y,float width, float height,Canvas c, Paint p)
	{
		p.setStyle(Paint.Style.FILL);
		p.setAntiAlias(true);
		c.drawRoundRect(new RectF(x, y, x + width, y + height) , ((int)height>>3), ((int)height>>3),p);
	}


	public static void fillRoundRectWithType(int type,int color,int x, int y,int width, int height,Canvas c, Paint p)
	{
		p.setStyle(Paint.Style.FILL);
		p.setAntiAlias(true);
		p.setColor(color);
		if(type==0){
			c.drawRoundRect(new RectF(x, y, x + width, y + height) , ((int)height>>3), ((int)height>>3),p);
		}else if(type==1){
			c.drawRoundRect(new RectF(x, y, x + width, y + height) , ((int)height>>3), ((int)height>>3),p);
			c.drawRect( x+(width>>1), y, x+width, y + height, p);		
		}else if(type==2){
			c.drawRoundRect(new RectF(x, y, x + width, y + height) , ((int)height>>3), ((int)height>>3),p);
			c.drawRect(x, y, x+(width>>1), y + height, p);	    	    	
		}else if(type==3){
			c.drawRect(x, y, x+width, y + height, p);	    	    	
		}
	}


	public static void fillRoundRect(int x, int y,
			int width, int height, int roundX, int roundY,Canvas c ,Paint p) {
		c.save();

		p.setStyle(Paint.Style.FILL);
		RectF rect = new RectF(x, y,  x + width, y + height);
		c.drawRoundRect(rect,roundX, roundY, p);
		c.restore();

	}
	public static void drawRoundRect(int x, int y,int width, int height,Canvas c, Paint p)
	{
		c.save();
		p.setStyle(Paint.Style.STROKE);
		c.drawRoundRect(new RectF(x, y, x + width, y + height) , ((int)height>>3), ((int)height>>3),p);
		c.restore();
	}
	public static void drawRoundRect(int x, int y,
			int width, int height, int roundX, int roundY,Canvas c ,Paint p) {
		c.save();

		p.setStyle(Paint.Style.STROKE);
		RectF rect = new RectF(x, y,  x + width, y + height);
		c.drawRoundRect(rect,roundX, roundY, p);
		c.restore();

	}

	public static void CreateGlowLine()
	{
		_paintSimple.reset();
		_paintSimple.setAntiAlias(true);
		_paintSimple.setDither(true);
		_paintSimple.setColor(Color.argb(248, 0, 255, 255));
		_paintSimple.setStrokeWidth(2f);
		_paintSimple.setStyle(Paint.Style.STROKE);
		_paintSimple.setStrokeJoin(Paint.Join.ROUND);
		_paintSimple.setStrokeCap(Paint.Cap.ROUND);

		_paintBlur.reset();
		_paintBlur.set(_paintSimple);
		_paintBlur.setColor(Color.argb(235, 255, 255, 255));
		_paintBlur.setStrokeWidth(4f);
		_paintBlur.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));

		_paintline.reset();
		_paintline.setColor(Color.argb(230, 255, 255, 255));
		_paintline.setStrokeWidth(1);
		_paintline.setStyle(Paint.Style.STROKE);
		_paintline.setStrokeJoin(Paint.Join.ROUND);
		_paintline.setStrokeCap(Paint.Cap.ROUND);
		_paintline.setAntiAlias(true);
		_paintline.setDither(true);
	}

	public static void drawGlowLine(Canvas canvas,int x1,int y1,int x2,int y2)
	{
		canvas.drawLine(x1, y1, x2, y2, _paintBlur);
		canvas.drawLine(x1, y1, x2, y2, _paintSimple);
		canvas.drawLine(x1, y1, x2, y2, _paintline);

	}
	static Matrix  reizsedMatrix=new Matrix();
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) 
	{
		int width = bm.getWidth();
		int height = bm.getHeight();
		reizsedMatrix.reset();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		reizsedMatrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap =com.appon.gamehelper.Util.createScaledBitmap(bm,  newWidth, newHeight);

		return resizedBitmap;
	}
}
