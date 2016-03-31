/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.gamehelper;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;



/**
 *
 * @author Swaroop
 */
public class Util {
	
	public static boolean isInRect(int xPos,int yPos, int width,int height , int x,int y )
	{
		if(x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height)
		{
			return true;
		}
		return false;
	}

	public static int getAngle(int x1,int y1,int x2, int y2) 
	{
		int x = x2 - x1;
		int y = y2 - y1;
		if(x == 0)
		{
			if (y<=0)
			{
				return (270);
			}
			return (90);
		}
		int coeff_1 = 12873;
		int coeff_2 = 38619;
		int abs_y = Math.abs(y);
		int angle;
		if (x >= 0){
			angle = (coeff_1*(x - abs_y))/ (x + abs_y);
			angle = coeff_1 - angle;
		}
		else{
			angle = (coeff_1 * (x + abs_y))/ (abs_y - x);
			angle = coeff_2 - angle;
		}


		int result =  y < 0 ? -angle : angle;
		result =  ((57*result) >> 14);
		if(result < 0)
		{
			result = 360 + result; 
		}
		return result;
	}

	public static boolean isRectCollision(int xPos1,int ypos1,int width1,int height1,int xPos2,int ypos2,int width2,int height2)
	{

		if (xPos1>(xPos2+width2) || (xPos1+width1)<xPos2)
			return false;
		if (ypos1>(ypos2+height2) || (ypos1+height1)<ypos2)
			return false;

		return true;       
	}


	
	public static Bitmap resizeImageWithTransperency(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		if(scaleWidth == 1.0 && scaleHeight == 1.0)
		{
			return bm;
		}
		Bitmap scaledBitmap =createScaledBitmap(bm, newWidth, newHeight);
		bm = null;
		return scaledBitmap;
	}



	public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight) {
		Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight);
		Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight);
		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
		return scaledBitmap;
	}
	public static int convertValue(float value) {
		return Math.round(value);
	}
	public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {

		return new Rect(0, 0, srcWidth, srcHeight);

	}
	public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
		return new Rect(0, 0, dstWidth, dstHeight);
	}

	public static void portArray(int[] array,int percent)
	{
		for (int s = 0; s < array.length; s++) {
			array[s] = getScaleValue(array[s], percent);
		}
	}
	public static int getScaleValue(int value, int scale)
	{
		if(scale < 0)
		{
			scale = Math.abs(scale);
			value = (value -  (( Math.abs(value) * scale) / 100));
		}else{
			value =  (value + ((value * scale) / 100));

		}
		return (int)value;
	}
	public static int getScaleFloatValue(float value, float scale)
	{
		if(scale < 0)
		{
			scale = Math.abs(scale);
			value = (value -  (( Math.abs(value) * scale) / 100));
		}else{
			value =  (value + ((value * scale) / 100));

		}
		return (int)Math.ceil(value);
	}


	public static boolean isCircleToCircleCollision(int cx1,int cy1,int r1,int cx2,int cy2,int r2)
	{
		int d = approx_distance(cx1 - cx2, cy1 - cy2);
		return (d < (r1 + r2));
	}
	public static boolean isPointInCircle(int cx,int cy,int r,int x,int y)
	{
		return (cx - x)*(cx - x) + (cy - y)*(cy - y) < (r*r);
	}
	public static int  approx_distance( int dx, int dy ) {
		int min, max;
		if ( dx < 0 ) dx = -dx;
		if ( dy < 0 ) dy = -dy;
		if ( dx < dy )  {
			min = dx;
			max = dy;
		} else {
			min = dy;
			max = dx;
		}
		// coefficients equivalent to ( 123/128 * max ) and ( 51/128 * min )
		return ((( max << 8 ) + ( max << 3 ) - ( max << 4 ) - ( max << 1 ) +
				( min << 7 ) - ( min << 5 ) + ( min << 3 ) - ( min << 1 )) >> 8 );
	}



}

