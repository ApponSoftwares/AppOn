/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;



import java.io.ByteArrayInputStream;
import java.util.Stack;
import java.util.Vector;

import com.appon.game.MyGameConstants;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Color;
//#endif





/**
 *
 * @author acer
 */
public class Util {
    public static int LOADING_VERSION = 0;//com.appon.effectcreater.Settings.VERSION_NO;
    //#ifdef DEKSTOP_TOOL
 //# public static Color getColor(int color)
    //# {
//# //        byte[] b = Util.intToBytes(color, 4);
//# //         byte[] b = Util.intToBytes(0x8fff0000 & 0xffffffff, 4);
        //# long val = color & 0xffffffff;
        //# int b = (int)(val & 0x00FF);
        //# int g = (int) ((val >> 8) & 0x000000FF);
        //# int r = (int) ((val >> 16) & 0x000000FF);
        //# int a = (int) ((val >> 24) & 0x000000FF);
        //# if(a == 0)
        //# {
            //# a = 255;
        //# }
        //# return new Color(r,g,b ,a);
    //# }
//#elifdef J2ME
//# public static int getColor(int color)
//# {
//# return color & 0xffffff;
//# }
//#elifdef ANDROID
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
//#endif
 
//#ifdef ANDROID
public static void drawPolygon(Canvas g, int xPoints[], int yPoints[],int thickness,Paint paintObj)
//#else
 //# public static void drawPolygon(Graphics g, int xPoints[], int yPoints[],int thickness)
//#endif
 
    {
	
	 //#ifdef ANDROID
    float strokeBackup = paintObj.getStrokeWidth(); 
    paintObj.setStrokeWidth(thickness);
    paintObj.setStyle(Paint.Style.STROKE);
    Path p = new Path();
    p.moveTo(xPoints[0], yPoints[0]);
    for (int i = 1; i < yPoints.length; i++) {
    	 p.lineTo(xPoints[i], yPoints[i]);
	}
    p.lineTo(xPoints[0], yPoints[0]);
    g.drawPath(p, paintObj);
    paintObj.setStrokeWidth(strokeBackup);
   //#else
  //# int max = xPoints.length - 1;
   //# for(int i = 0; i < max; i++)
  //# {
      //# drawThickLine(g, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1], thickness);
   //# }
   //# drawThickLine(g, xPoints[max], yPoints[max], xPoints[0], yPoints[0], thickness);
    //#endif
       
    }
   
	//#ifdef ANDROID
    public static void fillPolygon(Canvas g, int xPoints[], int yPoints[],Paint paintObj)
	//#else
	 //# public static void fillPolygon(Graphics g, int xPoints[], int yPoints[])
	//#endif
    
    {
    	 //#ifdef ANDROID
        paintObj.setStyle(Paint.Style.FILL);
        Path p = new Path();
        p.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < yPoints.length; i++) {
        	 p.lineTo(xPoints[i], yPoints[i]);
    	}
        p.lineTo(xPoints[0], yPoints[0]);
        g.drawPath(p, paintObj);
       //#else
        //# Stack stack = new Stack();
      //# fillPolygon(g, xPoints, yPoints, stack);
      //# for(; !stack.isEmpty(); fillPolygon(g, (int[])stack.pop(), (int[])stack.pop(), stack)) { }
        //#endif
     
    }
    private static int xTrianglePoints[] = new int[3];
    private static int yTrianglePoints[] = new int[3];
    
  //#ifdef ANDROID
    public static void fillTriangle(Canvas g,int x1,int y1,int x2,int y2,int x3,int y3,Paint p)
	//#else
	 //# public static void fillTriangle(Graphics g,int x1,int y1,int x2,int y2,int x3,int y3)
	//#endif
    
    {
	   xTrianglePoints[0] = x1;
       yTrianglePoints[0] = y1;
       xTrianglePoints[1] = x2;
       yTrianglePoints[1] = y2;
       xTrianglePoints[2] = x3;
       yTrianglePoints[2] = y3;
      //#ifdef ANDROID
      fillPolygon(g, xTrianglePoints, yTrianglePoints, p);
       //#elifdef J2ME
       //# g.fillTriangle(xTrianglePoints[0], yTrianglePoints[0], xTrianglePoints[1],  yTrianglePoints[1],  xTrianglePoints[2],  yTrianglePoints[2]);
       //#elifdef DEKSTOP_TOOL
       //# g.fillPolygon(xTrianglePoints, yTrianglePoints, 3);
        //#endif
     
              
        
        
    }
   

    //#ifdef ANDROID
    private static void fillPolygon(Canvas g, int xPoints[], int yPoints[], Stack stack,Paint p)
	//#else
	 //# private static void fillPolygon(Graphics g, int xPoints[], int yPoints[], Stack stack)
	//#endif
    
    {
        while(xPoints.length > 2) 
        {
            int a = indexOfLeast(xPoints);
            int b = (a + 1) % xPoints.length;
            int c = a <= 0 ? xPoints.length - 1 : a - 1;
            int leastInternalIndex = -1;
            boolean leastInternalSet = false;
            if(xPoints.length > 3)
            {
                for(int i = 0; i < xPoints.length; i++)
                {
                    if(i != a && i != b && i != c && withinBounds(xPoints[i], yPoints[i], xPoints[a], yPoints[a], xPoints[b], yPoints[b], xPoints[c], yPoints[c]) && (!leastInternalSet || xPoints[i] < xPoints[leastInternalIndex]))
                    {
                        leastInternalIndex = i;
                        leastInternalSet = true;
                    }
                }

            }
            if(!leastInternalSet)
            {
            	  //#ifdef ANDROID
            	 fillTriangle(g,xPoints[a], yPoints[a], xPoints[b], yPoints[b], xPoints[c], yPoints[c],p);
            	//#else
            	 //# fillTriangle(g,xPoints[a], yPoints[a], xPoints[b], yPoints[b], xPoints[c], yPoints[c]);
            	//#endif
                
//                g.fillTriangle(xPoints[a], yPoints[a], xPoints[b], yPoints[b], xPoints[c], yPoints[c]);
                int trimmed[][] = trimEar(xPoints, yPoints, a);
                xPoints = trimmed[0];
                yPoints = trimmed[1];
                continue;
            }
            int split[][][] = split(xPoints, yPoints, a, leastInternalIndex);
            int poly1[][] = split[0];
            int poly2[][] = split[1];
            stack.push(poly2[1]);
            stack.push(poly2[0]);
            stack.push(poly1[1]);
            stack.push(poly1[0]);
            break;
        }
    }
    static boolean withinBounds(int px, int py, int ax, int ay, int bx, int by, int cx, int cy)
    {
        if(px < min(ax, bx, cx) || px > max(ax, bx, cx) || py < min(ay, by, cy) || py > max(ay, by, cy))
        {
            return false;
        }
        boolean sameabc = sameSide(px, py, ax, ay, bx, by, cx, cy);
        boolean samebac = sameSide(px, py, bx, by, ax, ay, cx, cy);
        boolean samecab = sameSide(px, py, cx, cy, ax, ay, bx, by);
        return sameabc && samebac && samecab;
    }

    static int[][][] split(int xPoints[], int yPoints[], int aIndex, int bIndex)
    {
        int firstLen;
        if(bIndex < aIndex)
        {
            firstLen = (xPoints.length - aIndex) + bIndex + 1;
        } else
        {
            firstLen = (bIndex - aIndex) + 1;
        }
        int secondLen = (xPoints.length - firstLen) + 2;
        int first[][] = new int[2][firstLen];
        int second[][] = new int[2][secondLen];
        for(int i = 0; i < firstLen; i++)
        {
            int index = (aIndex + i) % xPoints.length;
            first[0][i] = xPoints[index];
            first[1][i] = yPoints[index];
        }

        for(int i = 0; i < secondLen; i++)
        {
            int index = (bIndex + i) % xPoints.length;
            second[0][i] = xPoints[index];
            second[1][i] = yPoints[index];
        }

        int result[][][] = new int[2][][];
        result[0] = first;
        result[1] = second;
        return result;
    }

    static int[][] trimEar(int xPoints[], int yPoints[], int earIndex)
    {
        int newXPoints[] = new int[xPoints.length - 1];
        int newYPoints[] = new int[yPoints.length - 1];
        int newPoly[][] = new int[2][];
        newPoly[0] = newXPoints;
        newPoly[1] = newYPoints;
        int p = 0;
        for(int i = 0; i < xPoints.length; i++)
        {
            if(i != earIndex)
            {
                newXPoints[p] = xPoints[i];
                newYPoints[p] = yPoints[i];
                p++;
            }
        }

        return newPoly;
    }

    static int indexOfLeast(int elements[])
    {
        int index = 0;
        int least = elements[0];
        for(int i = 1; i < elements.length; i++)
        {
            if(elements[i] < least)
            {
                index = i;
                least = elements[i];
            }
        }

        return index;
    }

    private static boolean sameSide(int p1x, int p1y, int p2x, int p2y, int l1x, int l1y, int l2x, int l2y)
    {
        long lhs = (p1x - l1x) * (l2y - l1y) - (l2x - l1x) * (p1y - l1y);
        long rhs = (p2x - l1x) * (l2y - l1y) - (l2x - l1x) * (p2y - l1y);
        long product = lhs * rhs;
        boolean result = product >= 0L;
        return result;
    }

    private static int min(int a, int b, int c)
    {
        return Math.min(Math.min(a, b), c);
    }

    private static int max(int a, int b, int c)
    {
        return Math.max(Math.max(a, b), c);
    }
    public static EShape findShape(EffectGroup group , int id)
    {
        for (int k = 0; k < group.getSize(); k++) {
            Effect effect = group.getEffect(k);
            Vector effectLayers = effect.getEffectLayers();
            int maxTimeFrames = getMaxTimeFrame(group, k);
             for (int j = 0; j <= (int)maxTimeFrames; j++) {
                for (int i = 0; i < effectLayers.size(); i++) {
                    EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
                    TimeFrame tf = object.getTimeFrame(j);
                    if(tf == null)
                        continue;
                    Vector shapes = tf.getShapes();
                    for (int l = 0; l < shapes.size(); l++) {
                        EShape shapeobject = (EShape)shapes.elementAt(l);
                        if(shapeobject.getId() == id)
                            return shapeobject;
                    }
                }
            }
            
        }
       return null;
    }
    public static int getMaxTimeFrame(EffectGroup group,int effectIndex)
    {
        Effect effect = group.getEffect(effectIndex);
        Vector vector  = effect.getEffectLayers();
        int maxTimeFrame = 0;
        for (int i = 0; i < vector.size(); i++) {
            EffectLayer effectLayer = (EffectLayer)vector.elementAt(i);
            Vector timeFrames = effectLayer.getTimeFrames();
            for (int j = 0; j < timeFrames.size(); j++) {
                TimeFrame object = (TimeFrame)timeFrames.elementAt(j);
                if(object.getTimeFrameId() > maxTimeFrame)
                {
                   maxTimeFrame =  object.getTimeFrameId();
                }
            }
        }
        return maxTimeFrame;
    }
    private static int resizePercentX = 0;
    private static int resizePercentY = 0;

    public static void setResizePercentX(int resizePercentValue) {
    	resizePercentX = resizePercentValue;
    }
    public static void setResizePercentY(int resizePercentValue) {
    	resizePercentY = resizePercentValue;
    }
//    public static EffectGroup loadEffect(byte[] data) throws Exception
//    {
//    	 ByteArrayInputStream bis = new ByteArrayInputStream(data);
//         LOADING_VERSION = com.appon.miniframework.Util.readInt(bis, 1);
//         EffectGroup container = (EffectGroup)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
//         if(resizePercentX != 0||resizePercentY != 0)
//         {
////        	 System.out.println("EFffect rescled by-----------   "+resizePercent);
//             rescale(container, resizePercentX,resizePercentY);
//         }
//         return container;
//    }
    
    public static EffectGroup loadEffect(byte[] data,boolean xScale,boolean yScale) throws Exception
    {
    	 ByteArrayInputStream bis = new ByteArrayInputStream(data);
         LOADING_VERSION = com.appon.miniframework.Util.readInt(bis, 1);
         EffectGroup container = (EffectGroup)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
         if(resizePercentX != 0||resizePercentY != 0)
         {
//        	 System.out.println("EFffect rescled by-----------   "+resizePercent);
        	 
        	 if(xScale && !yScale)
        		 rescale(container, resizePercentX,resizePercentX);
        	 else if(yScale && !xScale)
        		 rescale(container, resizePercentY,resizePercentY);
        	 else  
        		 rescale(container, resizePercentX,resizePercentY);
         }
         return container;
    }    
    
    
    public static void rescale(EffectGroup group,int percentX,int percentY)
    {
      
        for (int k = 0; k < group.getSize(); k++) {
           Effect effect = group.getEffect(k);
           Vector effectLayers = effect.getEffectLayers();
           int maxTimeFrames = getMaxTimeFrame(group, k);
            for (int j = 0; j <= (int)maxTimeFrames; j++) {
               for (int i = 0; i < effectLayers.size(); i++) {
                   EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
                   TimeFrame tf = object.getTimeFrame(j);
                   if(tf == null)
                       continue;
                   Vector shapes = tf.getShapes();
                   for (int l = 0; l < shapes.size(); l++) {
                      EShape shapeobject = (EShape)shapes.elementAt(l);
                      portShape(percentX,percentY, shapeobject);
                   }
               }
           }
       }
    }   
   public static void portShape(int perX,int perY,EShape selShape)
   {
       if(selShape != null)
       {
           if(selShape instanceof ELine)
           {
               ELine line = (ELine)selShape;
               line.setX(getScaleValue(line.getX() , perX));
               line.setY(getScaleValue(line.getY() , perY));
               line.setX2(getScaleValue(line.getX2() , perX));
               line.setY2(getScaleValue(line.getY2() , perY));
           }else if(selShape instanceof EPolygon)
           {
               EPolygon poly = (EPolygon)selShape;
               int x[] = poly.getXPoints();
               int y[] = poly.getYPoints();
               for (int i = 0; i < x.length; i++) {
                   x[i] = getScaleValue( x[i] , perX);
                   y[i] =  getScaleValue( y[i] , perY);
               }
           }else if(selShape instanceof ETraingle)
           {
               ETraingle trai = (ETraingle)selShape;
               int x[] = trai.getXPoints();
               int y[] = trai.getYPoints();
               for (int i = 0; i < x.length; i++) {
                   x[i] = getScaleValue( x[i] , perX);
                   y[i] = getScaleValue( y[i] , perY);
               }
           }else if(selShape instanceof ERect)
           {
               ERect line = (ERect)selShape;
               line.setX(getScaleValue(line.getX() , perX));
               line.setY(getScaleValue(line.getY() , perY));
               line.setWidth(getScaleValue(line.getWidth() , perX));
               line.setHeight(getScaleValue(line.getHeight() , perY));
           }else if(selShape instanceof EArc)
           {
               EArc line = (EArc)selShape;
               line.setX(getScaleValue(line.getX() , perX));
               line.setY(getScaleValue(line.getY() , perY));
               line.setWidth(getScaleValue(line.getWidth() , perX));
               line.setHeight(getScaleValue(line.getHeight() , perY));
           }else
           {
               selShape.setX(getScaleValue(selShape.getX() , perX));
               selShape.setY(getScaleValue(selShape.getY() , perY));
           }
       }      
    }

   
     public static int  approx_distance( int dx, int dy )
    {
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
    private static int xPoints[] = new int[4];
    private static int yPoints[] = new int[4];
    
    //#ifdef ANDROID
    public static void drawThickLine(Canvas g, int x1, int y1, int x2, int y2, int thickness,Paint paintObj) {
	//#else
	 //# public static void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness) {
	//#endif
  
    	//#ifdef ANDROID
    	 float strokeBackup = paintObj.getStrokeWidth(); 
         paintObj.setStrokeWidth(thickness);
         paintObj.setStyle(Paint.Style.STROKE);
         g.drawLine(x1, y1, x2, y2, paintObj);
         paintObj.setStrokeWidth(strokeBackup);
    	//#else
    	//# // The thick line is in fact a filled polygon
        //# 
        //# if(thickness <= 1)
    	 //# {
    	//# g.drawLine(x1, y1, x2, y2);
    	//# return;
    	//# }
    	//# int dX = x2 - x1;
    	//# int dY = y2 - y1;
    	    //# // line length
    	//# 
    	//# int lineLength = approx_distance(dX, dY);
    	//# 
    	//# if(lineLength == 0)
    	//# lineLength = 1;
    	//# int scale = (thickness << 14) / (2 * lineLength);
    	//# 
    	 //# // The x,y increments from an endpoint needed to create a rectangle...
    	//# int ddx = -scale * dY;
    	//# int ddy = scale * dX;
    	//# ddx += ((ddx >> 14) > 0) ? 8192 : -8192;
    	//# ddy += ((ddy >> 14) > 0) ? 8192 : -8192;
    	//# int dx = (int)ddx >> 14;
    	//# int dy = (int)ddy >> 14;
    	//# 
    	//# // Now we can compute the corner points...
    	//# 
    	//# 
    	//# xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
    	//# xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
    	//# xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
    	//# xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;
    	//# fillPolygon(g, xPoints, yPoints);
    	//# 
    //#endif
  }
   //#ifdef ANDROID
   public static void drawArc(Canvas g,int x,int y,int width,int height,int startangle, int endAngle,int thickness,Paint paintObj)
   //#else
   //# public static void drawArc(Graphics g,int x,int y,int width,int height,int startangle, int endAngle,int thickness)
   //#endif
   {
	   
	  //#ifdef ANDROID
	   float strokeBackup = paintObj.getStrokeWidth(); 
       paintObj.setStrokeWidth(thickness);
       paintObj.setStyle(Paint.Style.STROKE);
       g.drawArc(new RectF(x, y, x + width, y + height), startangle, endAngle, true, paintObj);
       paintObj.setStrokeWidth(strokeBackup);
     //#else
	  //# if(thickness <= 1)
	    //# {
	              //# g.drawArc(x, y, width, height, startangle, endAngle);
	              //# return;
	   //# }
	   //# for (int i = 0; i < thickness; i++) {
	               //# g.drawArc(x + i, y + i, width - (i << 1), height- (i << 1), startangle, endAngle);
	   //# }
     //#endif
       
   }
   //#ifdef ANDROID
   public static void drawRectangle(Canvas g,int x,int y,int width,int height,int thickness,Paint paintObj)
   //#else
   //# public static void drawRectangle(Graphics g,int x,int y,int width,int height,int thickness)
   //#endif

   {
	 //#ifdef ANDROID
	   float strokeBackup = paintObj.getStrokeWidth(); 
       paintObj.setStrokeWidth(thickness);
       paintObj.setStyle(Paint.Style.STROKE);
       g.drawRect((new RectF(x, y, x + width, y + height)), paintObj);
       paintObj.setStrokeWidth(strokeBackup);
	   //#else
	  //# if(thickness <= 1)
	 //# {
	   //# g.drawRect(x, y, width, height);
	 //# return;
	 //# }
	 //# for (int i = 0; i < thickness; i++) {
	 //# g.drawRect(x + i, y + i, width - (i << 1), height- (i << 1));
	 //# }
	   //#endif
      
   }
    public static Point pointToRotate = new Point();
    
    public static Point rotatePoint(Point pt, int anchorX,int anchorY,int theta,int zoom,EShape shape)
    {
        if(zoom != 0)
        {
            pt.x = getScaleValue(pt.x, zoom);
            pt.y = getScaleValue(pt.y, zoom);
        }
        if(theta == 0)
        {
            return pt;
        }
        pt.x += (shape.getWidth() >> 1);
        pt.y += (shape.getHeight() >> 1);
        int d = theta;
        int tmpX1 = pt.x;
        int tmpY1 = pt.y;
        int centerX = anchorX;
        int centerY = anchorY;
        tmpX1 -= centerX;
        tmpY1 -= centerY;
        int tmpValue = 0;
        tmpValue = ((tmpX1*ProjectileMotion.cos(d)) - (tmpY1*ProjectileMotion.sin(d))) >> 14;
        tmpY1 = ((tmpX1*ProjectileMotion.sin(d)) + (tmpY1*ProjectileMotion.cos(d))) >> 14;
        tmpX1 = tmpValue;
        tmpX1 += centerX;
        tmpY1 += centerY;
        pt.x = (int)tmpX1 ;
        pt.y = (int)tmpY1 ;
        pt.x -= (shape.getWidth() >> 1);
        pt.y -= (shape.getHeight() >> 1);
        return pt;
    }
    public static int getScaleValue(int value, int scale)
    {
       if(scale == 0)
           return value;
        value =  (value + ((value * scale) / 100));
        return (int)(value);
    }
     public static int getMaxTimeFrame(Effect effect)
    {
       
        Vector vector  = effect.getEffectLayers();
        int maxTimeFrame = 0;
        for (int i = 0; i < vector.size(); i++) {
            EffectLayer effectLayer = (EffectLayer)vector.elementAt(i);
            Vector timeFrames = effectLayer.getTimeFrames();
            for (int j = 0; j < timeFrames.size(); j++) {
                TimeFrame object = (TimeFrame)timeFrames.elementAt(j);
                if(object.getTimeFrameId() > maxTimeFrame)
                {
                   maxTimeFrame =  object.getTimeFrameId();
                }
            }
        }
        return maxTimeFrame;
    }
      public static int findLayerIndex(Effect effect , int id)
    {
            Vector effectLayers = effect.getEffectLayers();
            int maxTimeFrames = getMaxTimeFrame(effect);
             for (int j = 0; j <= (int)maxTimeFrames; j++) {
                for (int i = 0; i < effectLayers.size(); i++) {
                    EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
                    TimeFrame tf = object.getTimeFrame(j);
                    if(tf == null)
                        continue;
                    Vector shapes = tf.getShapes();
                    for (int l = 0; l < shapes.size(); l++) {
                        EShape shapeobject = (EShape)shapes.elementAt(l);
                        if(shapeobject.getId() == id)
                            return i;
                    }
                }
            }
            
       
       return -1;
    }
    public static int findTimeFrameIndex(Effect effect , int id)
    {
            Vector effectLayers = effect.getEffectLayers();
            int maxTimeFrames = getMaxTimeFrame(effect);
             for (int j = 0; j <= (int)maxTimeFrames; j++) {
                for (int i = 0; i < effectLayers.size(); i++) {
                    EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
                    TimeFrame tf = object.getTimeFrame(j);
                    if(tf == null)
                        continue;
                    Vector shapes = tf.getShapes();
                    for (int l = 0; l < shapes.size(); l++) {
                        EShape shapeobject = (EShape)shapes.elementAt(l);
                        if(shapeobject.getId() == id)
                            return j;
                    }
                }
            }
            
       
       return -1;
    }

	public static Path getPath(EPolygon ePolygon) {
		Path path=new Path();
		int x[]=ePolygon.getXPoints();
		int y[]=ePolygon.getYPoints();
		float fx[] = new float[x.length];
		float fy[] = new float[y.length];
		
		float temp1=(MyGameConstants.SCREEN_WIDTH>>1);
		float temp2=(MyGameConstants.SCREEN_HEIGHT>>1);
		for (int i = 0; i < x.length; i++) {
			fx[i]=temp1+x[i];
			fy[i]=temp2+y[i];
		}
		path.moveTo(fx[0],fy[0]);
		for (int i = 1; i < fx.length; i++) {
			path.lineTo(fx[i],fy[i]);
		}
		path.lineTo(fx[0],fy[0]);
		
		return path;
	}
	
	public static Path getPath(EPolygon ePolygon,int pX,int pY,int start1X,int start1Y,int start2X,int start2Y) {
		Path path=new Path();
		int x[]=ePolygon.getXPoints();
		int y[]=ePolygon.getYPoints();
		float fx[] = new float[x.length];
		float fy[] = new float[y.length];
		
		for (int i = 0; i < x.length; i++) {
			fx[i]=pX+x[i];
			fy[i]=pY+y[i];
		}
		
		
		fx[0]=start1X;
		fy[0]=start1Y;
		
		fx[x.length-1]=start2X;
		fy[x.length-1]=start2Y;
		
		
		path.moveTo(fx[0],fy[0]);
		for (int i = 1; i < fx.length; i++) {
			path.lineTo(fx[i],fy[i]);
		}
		path.lineTo(fx[0],fy[0]);
		
		return path;
	}
	
	
	
}
