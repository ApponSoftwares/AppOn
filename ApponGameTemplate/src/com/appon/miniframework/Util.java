/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.miniframework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//#ifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Image;
//# import java.awt.Graphics;
//# import java.awt.Color;
//#endif
import android.graphics.Paint;

import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.controls.TabControl;
import com.appon.miniframework.controls.TabPane;
import com.appon.miniframework.layout.PropotionLayout;
import com.appon.miniframework.layout.RelativeLayout;

/**
 *
 * @author Swaroop Kumar
 */
public class Util {
   
    private static int sin_tabel[] = {0,286,572,857,1143,1428,1713,1997,2280,2563,2845,3126,3406,3686,3964,4240,4516,4790,5063,5334,5604,5872,6138,6402,6664,6924,7182,7438,7692,7943,8193,8438,8682,8923,9162,9397,9630,9860,10087,10311,10531,10749,10963,11174,11381,11585,11786,11982,12176,12365,12551,12733,12911,13085,13255,13421,13583,13741,13894,14044,14189,14330,14466,14598,14726,14849,14968,15082,15191,15296,15396,15491,15582,15668,15749,15826,15897,15964,16026,16083,16135,16182,16225,16262,16294,16322,16344,16362,16374,16382,16384,16382,16374,16362,16344,16322,16294,16262,16225,16182,16135,16083,16026,15964,15897,15826,15749,15668,15582,15491,15396,15296,15191,15082,14968,14849,14726,14598,14466,14330,14189,14044,13894,13741,13583,13421,13255,13085,12911,12733,12551,12365,12176,11982,11786,11585,11381,11174,10963,10749,10531,10311,10087,9860,9630,9397,9162,8923,8682,8438,8193,7943,7692,7438,7182,6924,6664,6402,6138,5872,5604,5334,5063,4790,4516,4240,3964,3686,3406,3126,2845,2563,2280,1997,1713,1428,1143,857,572,286,0,-285,-571,-857,-1142,-1427,-1712,-1996,-2280,-2563,-2845,-3126,-3406,-3685,-3963,-4240,-4516,-4790,-5062,-5334,-5603,-5871,-6137,-6401,-6663,-6924,-7182,-7438,-7691,-7943,-8192,-8438,-8682,-8923,-9161,-9397,-9630,-9860,-10086,-10310,-10531,-10748,-10963,-11173,-11381,-11585,-11785,-11982,-12175,-12365,-12550,-12732,-12910,-13084,-13254,-13420,-13582,-13740,-13894,-14043,-14188,-14329,-14466,-14598,-14725,-14848,-14967,-15081,-15190,-15295,-15395,-15491,-15582,-15668,-15749,-15825,-15897,-15964,-16025,-16082,-16135,-16182,-16224,-16261,-16294,-16321,-16344,-16361,-16374,-16381,-16384,-16381,-16374,-16361,-16344,-16321,-16294,-16261,-16224,-16182,-16135,-16082,-16025,-15964,-15897,-15825,-15749,-15668,-15582,-15491,-15395,-15295,-15190,-15081,-14967,-14848,-14725,-14598,-14466,-14329,-14188,-14043,-13894,-13740,-13582,-13420,-13254,-13084,-12910,-12732,-12550,-12365,-12175,-11982,-11785,-11585,-11381,-11173,-10963,-10748,-10531,-10310,-10086,-9860,-9630,-9397,-9161,-8923,-8682,-8438,-8192,-7943,-7691,-7438,-7182,-6924,-6663,-6401,-6137,-5871,-5603,-5334,-5062,-4790,-4516,-4240,-3963,-3685,-3406,-3126,-2845,-2563,-2280,-1996,-1712,-1427,-1142,-857,-571,-285};
    private static int cos_tabel[] = {16384,16382,16374,16362,16344,16322,16294,16262,16225,16182,16135,16083,16026,15964,15897,15826,15749,15668,15582,15491,15396,15296,15191,15082,14968,14849,14726,14598,14466,14330,14189,14044,13894,13741,13583,13421,13255,13085,12911,12733,12551,12365,12176,11982,11786,11585,11381,11174,10963,10749,10531,10311,10087,9860,9630,9397,9162,8923,8682,8438,8192,7943,7692,7438,7182,6924,6664,6402,6138,5872,5604,5334,5063,4790,4516,4240,3964,3686,3406,3126,2845,2563,2280,1997,1713,1428,1143,857,572,286,0,-285,-571,-857,-1142,-1427,-1712,-1996,-2280,-2563,-2845,-3126,-3406,-3685,-3963,-4240,-4516,-4790,-5062,-5334,-5603,-5871,-6137,-6401,-6663,-6924,-7182,-7438,-7691,-7943,-8191,-8438,-8682,-8923,-9161,-9397,-9630,-9860,-10086,-10310,-10531,-10748,-10963,-11173,-11381,-11585,-11785,-11982,-12175,-12365,-12550,-12732,-12910,-13084,-13254,-13420,-13582,-13740,-13894,-14043,-14188,-14329,-14466,-14598,-14725,-14848,-14967,-15081,-15190,-15295,-15395,-15491,-15582,-15668,-15749,-15825,-15897,-15964,-16025,-16082,-16135,-16182,-16224,-16261,-16294,-16321,-16344,-16361,-16374,-16381,-16384,-16381,-16374,-16361,-16344,-16321,-16294,-16261,-16224,-16182,-16135,-16082,-16025,-15964,-15897,-15825,-15749,-15668,-15582,-15491,-15395,-15295,-15190,-15081,-14967,-14848,-14725,-14598,-14466,-14329,-14188,-14043,-13894,-13740,-13582,-13420,-13254,-13084,-12910,-12732,-12550,-12365,-12175,-11982,-11785,-11585,-11381,-11173,-10963,-10748,-10531,-10310,-10086,-9860,-9630,-9397,-9161,-8923,-8682,-8438,-8192,-7943,-7691,-7438,-7182,-6924,-6663,-6401,-6137,-5871,-5603,-5334,-5062,-4790,-4516,-4240,-3963,-3685,-3406,-3126,-2845,-2563,-2280,-1996,-1712,-1427,-1142,-857,-571,-285,0,286,572,857,1143,1428,1713,1997,2280,2563,2845,3126,3406,3686,3964,4240,4516,4790,5063,5334,5604,5872,6138,6402,6664,6924,7182,7438,7692,7943,8192,8438,8682,8923,9162,9397,9630,9860,10087,10311,10531,10749,10963,11174,11381,11585,11786,11982,12176,12365,12551,12733,12911,13085,13255,13421,13583,13741,13894,14044,14189,14330,14466,14598,14726,14849,14968,15082,15191,15296,15396,15491,15582,15668,15749,15826,15897,15964,16026,16083,16135,16182,16225,16262,16294,16322,16344,16362,16374,16382};
    public static int sin(int theta)
    {
        theta = theta % 360;
        while(theta < 0)
            theta = 360 - theta;
        return sin_tabel[theta];
    }
    public static int cos(int theta)
    {
        theta = theta % 360;
        while(theta < 0)
            theta = 360 - theta;
        return cos_tabel[theta];
    }
//    public static void testRoatation(Control c,Graphics g)
//    {
//        xPoints[0] = c.getX();
//        yPoints[0] = c.getY();
//        xPoints[1] = c.getX() + c.getWidth();
//        yPoints[1] = c.getY();
//        xPoints[2] = c.getX() + c.getWidth();
//        yPoints[2] = c.getY() + c.getHeight();
//        xPoints[3] = c.getX() ;
//        yPoints[3] = c.getY() + c.getHeight();
//        rotateCoordinates(xPoints, yPoints, c.getX() + (c.getWidth() >> 1), c.getY() + (c.getHeight() >> 1), c.getRotation());
//        GeneralPath path = new GeneralPath();
//        path.moveTo(xPoints[0], yPoints[0]);
////        
////     
//        for (int i = 1; i < 4; i++) {
//            path.lineTo(xPoints[i], yPoints[i]);
//        }
//         path.lineTo(xPoints[0], yPoints[0]);
//        
//     
//          g.setColor(Color.red);
//        ((Graphics2D)g).draw(path);
//    }
    
     public static boolean isInRect(int xPos,int yPos, int width,int height , int x,int y )
     {
       if(x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height)
       {
           return true;
       }
       return false;
     }
     public static void selectControl(Control c)
     {
         
         Control child = c;
         Control parent = c.getParent();
         while(parent != null)
         {
             if(parent instanceof ScrollableContainer)
             {
                 ((ScrollableContainer)parent).selectChild(child,false);
             }if(parent instanceof TabControl)
             {
                 ((TabControl)parent).selectTabPane((TabPane)child);
             }
             parent.setSelected(true, false);
             child.setSelected(true, false);
             child = parent;
             parent = parent.getParent();
         }
       
     }
     public static boolean isInRect(Control control , int x,int y )
     {
         if(control.getRotation() == 0)
         {
             return isInRect(control.getX(), control.getY(), control.getWidth(), control.getHeight(), x, y);
         }else{
             return isInPoly(control, x, y,false);
         }
       
     }
      public static boolean isInActualRect(Control control , int x,int y )
     {
         if(control.getRotation() == 0)
         {
             return isInRect(getActualX(control), getActualY(control), control.getWidth(), control.getHeight(), x, y);
         }else{
             return isInPoly(control, x, y,true);
         }
       
     }
      
//     /**
//     * Draws filled triangle
//     * @param startPtX   X coordinate of starting point of the triangle
//     * @param startPtY   Y coordinate of starting point of the triangle
//     * @param arrowDirection   decides the direction of arrow left/right/up/down
//     * @param arrowWidth  size of the arrow
//     * @param horizontal   decides the alignment of arrow horizontal/vertical
//     */
//    public static void drawFilledTriangle(Graphics g, int startPtX, int startPtY, boolean arrowDirection, int arrowWidth, boolean horizontal) {
//        int wdth = arrowWidth;
//        if (horizontal) {
//            /**horizontal arrow is to be drawn*/
//            int i = 0;
//            boolean var = true;
//
//            while (var == true) {
//                /**left arrow is to be drawn*/
//                if (arrowDirection == true) {
//                    g.drawLine(startPtX - i, startPtY - i, startPtX - i, wdth + i);
//                /**right arrow is to be drawn*/
//                } else {
//                    g.drawLine(startPtX + i, startPtY - i, startPtX + i, wdth + i);
//                }
//               if ((startPtY - i) - (wdth + i) <= 1) {
//                    var = false;
//                }
//                i++;
//                
//            }
//        } else {
//            /**vertical arrow is to be drawn*/
//            int i = 0;
//            boolean var = true;
//            while (var == true) {
//                /**up arrow is to be drawn*/
//                if (arrowDirection == true) {
//                    g.drawLine(startPtX + i, startPtY - i, startPtX + wdth - i, startPtY - i);
//                /**down arrow is to be drawn*/
//                } else {
//                    g.drawLine(startPtX + i, startPtY + i, startPtX + wdth - i, startPtY + i);
//                }
//                if (wdth - i == arrowWidth >> 1) {
//                    var = false;
//                }
//                i++;
//            }
//        }
//
//    }
//    
    public static int getApproxDistance( int x1, int y1,int x2, int y2 )
    {
       int dx = Math.abs(x1 - x2);
       int dy = Math.abs(y1 - y2);
       int min, max;
       if ( dx < dy )
       {
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
   
    
    public static int getActualX(Control control)
    {
        int x = control.getX();
        Control parent = control.getParent();
        while(parent != null)
        {
            x+= parent.getX() + parent.getLeftInBound();
            if(parent instanceof Container)
            {
                x -= ((Container)parent).getScrolledX();
            }
            parent = parent.getParent();
        }
        return x;
    }
    public static int getActualY(Control control)
    {
        int y = control.getY();
        Control parent = control.getParent();
        while(parent != null)
        {
            y+= parent.getY() + parent.getTopInBound();
            if(parent instanceof Container)
            {
                y -= ((Container)parent).getScrolledY();
            }
            parent = parent.getParent();
        }
        return y;
    }
     public static int getWrappedHeight(Container c)
     {
         if(c.getSizeSettingY() != Control.WRAP_CONTENT)
         {
             return c.getHeight();
         }
         if((!(c.getLayout() instanceof PropotionLayout) && c.getLayout() != null))
         {
            return c.getLayout().getPreferedHeight(c) + c.getTopInBound() + c.getBottomInBound();
         }
        
          int h = Integer.MIN_VALUE;
          for (int i = 0; i < c.getSize() ; i++) {
            Control control = c.getChild(i);
            if(control.getHeightWeight() == -2 ||control.isSkipParentWrapSizeCalc())
                continue;
            if(control.getRelativeLocation() != null && isHeightParentDepended(control.getRelativeLocation()))
            {
                 int tmp = control.getRelativeLocation().getAdditionalPaddingY();
                 
                 if(control.getSizeSettingY() == Control.WRAP_CONTENT && !control.isAdjustDimentionsFromBgImage())
                 {
                     tmp +=  control.getPreferredHeight();
                 }else{
                     tmp +=  control.getHeight() ;
                 }
                
                 if(h < tmp)
                 {
                     h = tmp;
                 }
            }
            else
            {
                if(control.getSizeSettingY() == Control.WRAP_CONTENT)
                {
                     int tmp = control.getY() + control.getPreferredHeight();
                     if(h < tmp)
                     {
                        h = tmp;
                     }
                }else{
                     int tmp = control.getY()+control.getHeight();
                     if(h < tmp)
                     {
                        h = tmp;
                     }
                }
            }
            
        }
        if(h == Integer.MIN_VALUE)
            return 0;
        return h + c.getTopInBound() + c.getBottomInBound();
     }
     public static boolean isHeightParentDepended(RelativeLayout relativeLocation)
     {
         return relativeLocation.getRelativeRelationY() == RelativeLayout.CENTER_OF_CONTAINER || relativeLocation.getRelativeRelationY() == RelativeLayout.LEFT_TOP_OF_CONTAINER || relativeLocation.getRelativeRelationY() == RelativeLayout.RIGHT_BOTTOM_OF_CONTAINER;
     }
      public static boolean isWidthParentDepended(RelativeLayout relativeLocation)
     {
         return relativeLocation.getRelativeRelationX() == RelativeLayout.CENTER_OF_CONTAINER || relativeLocation.getRelativeRelationX() == RelativeLayout.LEFT_TOP_OF_CONTAINER || relativeLocation.getRelativeRelationX() == RelativeLayout.RIGHT_BOTTOM_OF_CONTAINER;
     }
     public static int getWrappedWidth(Container c)
     {
           if(c.getSizeSettingX() != Control.WRAP_CONTENT)
         {
             return c.getWidth();
         }
          if((!(c.getLayout() instanceof PropotionLayout) && c.getLayout() != null))
         {
            return c.getLayout().getPreferedWidth(c) + c.getLeftInBound() + c.getRightInBound();
         }
       
          int w = Integer.MIN_VALUE;
           for (int i = 0; i < c.getSize() ; i++) {
            Control control = c.getChild(i);
            if(control.getWidthWeight() == -2  ||control.isSkipParentWrapSizeCalc())
                continue;
            if(control.getRelativeLocation() != null && isWidthParentDepended(control.getRelativeLocation()))
            {
                 int tmp = control.getRelativeLocation().getAdditionalPaddingX();
                 
                 if(control.getSizeSettingX() == Control.WRAP_CONTENT  && !control.isAdjustDimentionsFromBgImage())
                 {
                     tmp +=  control.getPreferredWidth();
                 }else{
                     tmp +=  control.getWidth();
                 }
                 if(w < tmp)
                 {
                     w = tmp;
                 }
            }
            else
            {
              
                if(control.getSizeSettingX() == Control.WRAP_CONTENT)
                {
                     int tmp = control.getX() + control.getPreferredWidth();
                     if(w < tmp)
                     {
                        w = tmp;
                     }
                }else{
                     int tmp = control.getX()+control.getWidth();
                     if(w < tmp)
                     {
                        w = tmp;
                     }
                }
            }
            
        }
        if(w == Integer.MIN_VALUE)
            return 0;
        return w + c.getLeftInBound() + c.getRightInBound();
     }
//     public static int getWrappedHeight(Container c)
//     {
//         return getWrappedMaxHeight(c) - getWrappedMinY(c);
//     }
//    public static int getWrappedMinY(Container c)
//    {
//        int w = Integer.MAX_VALUE;
//        for (int i = 0; i < c.getSize() ; i++) {
//            Control control = c.getChild(i);
//            if(w > control.getY() )
//            {
//                w = control.getY();
//            }
//        }
//        if(w == Integer.MAX_VALUE)
//            return 0;
//        return w;
//    }
//    public static int getWrappedMaxHeight(Container c)
//    {
//        int h = Integer.MIN_VALUE;
//        for (int i = 0; i < c.getSize() ; i++) {
//            Control control = c.getChild(i);
//            if(control instanceof Container)
//            {
//                Container calcultionContainer = (Container)control;
//                if(calcultionContainer.getSizeSetting() == Control.WRAP_CONTENT)
//                {
//                     int tmp = getWrappedHeight(calcultionContainer);
//                     if(h < tmp)
//                     {
//                        h = tmp;
//                     }
//                }else{
//                     int tmp = calcultionContainer.getY()+calcultionContainer.getHeight();
//                     if(h < tmp)
//                     {
//                        h = tmp;
//                     }
//                }
//            }else{
//                if(h < control.getY() + control.getHeight())
//                {
//                    h = control.getY() + control.getHeight();
//                }
//            }
//            
//        }
//        if(h == Integer.MIN_VALUE)
//            return 0;
//        return h;
//    }
//     public static int getWrappedWidth(Container c)
//     {
//         return getWrappedMaxWidth(c) - getWrappedMinX(c);
//     }
//    public static int getWrappedMinX(Container c)
//    {
//        int w = Integer.MAX_VALUE;
//        for (int i = 0; i < c.getSize() ; i++) {
//            Control control = c.getChild(i);
//            if(w > control.getX() )
//            {
//                w = control.getX();
//            }
//        }
//        if(w == Integer.MAX_VALUE)
//            return 0;
//        return w;
//    }
//    public static int getWrappedMaxWidth(Container c)
//    {
//        int w = Integer.MIN_VALUE;
//        for (int i = 0; i < c.getSize() ; i++) {
//            Control control = c.getChild(i);
//            if(control instanceof Container)
//            {
//                Container calcultionContainer = (Container)control;
//                if(calcultionContainer.getSizeSetting() == Control.WRAP_CONTENT)
//                {
//                     int tmp = getWrappedWidth(calcultionContainer);
//                     if(w < tmp)
//                     {
//                        w = tmp;
//                     }
//                }else{
//                     int tmp = calcultionContainer.getX()+ calcultionContainer.getWidth();
//                     if(w < tmp)
//                     {
//                        w = tmp;
//                     }
//                }
//               
//            }else{
//                if(w < control.getX() + control.getWidth())
//                {
//                    w = control.getX() + control.getWidth();
//                }
//            }
//            
//        }
//        if(w == Integer.MIN_VALUE)
//            return 0;
//        return w;
//    }
    public static int getScaleValue(int value, int scale)
    {
        
        boolean sign = value >= 0;
        value = Math.abs(value);
        value =  (value + ((value * scale) / 100));
        if(!sign)
        {
            value = -value;
        }
//        if(scale < 0)
//        {
//            scale = Math.abs(scale);
//            value = (value -  (( Math.abs(value) * scale) / 100));
//        }else{
//            value =  (value + ((value * scale) / 100));
//
//        }
        return (int)value;
    }
    public static int getScaleX()
    {
        return ((100 * (Settings.SCREEN_WIDTH - Settings.MASTER_SCREEN_WIDTH) )/ Settings.MASTER_SCREEN_WIDTH) ;
    }
    public static int getScaleY()
    {
        return ((100 * (Settings.SCREEN_HEIGHT - Settings.MASTER_SCREEN_HEIGHT) )/ Settings.MASTER_SCREEN_HEIGHT) ;
    }
    private static int xPoints[] = new int[4];
    private static int yPoints[] = new int[4];
    public static boolean isInPoly(Control c,int x,int y,boolean isActual)
    {
        
        int _x = c.getX();
        int _y = c.getY();
        if(isActual)
        {
            _x = getActualX(c);
            _y = getActualY(c);
        }
        xPoints[0] = _x;
        yPoints[0] = _y;
        xPoints[1] = _x + c.getWidth();
        yPoints[1] = _y;
        xPoints[2] = _x + c.getWidth();
        yPoints[2] = _y + c.getHeight();
        xPoints[3] = _x ;
        yPoints[3] = _y + c.getHeight();
        rotateCoordinates(xPoints, yPoints, _x + (c.getWidth() >> 1), _y + (c.getHeight() >> 1), c.getRotation());
        return pnpoly(4,xPoints,yPoints,x,y);
    }
    //#ifndef J2ME
    public static void rotateCoordinates(int x[],int y[],int centerX,int centerY,int rotation)
    {
         double d = Math.toRadians(rotation);
          for (int i = 0; i < x.length; i++) {
            double tmpX = x[i] - centerX;
            double tmpY = y[i] - centerY;
            double tmpValue = 0;
            tmpValue = Math.ceil((tmpX*Math.cos(d)) - (tmpY*Math.sin(d)));
            tmpY = Math.ceil((tmpX*Math.sin(d)) + (tmpY*Math.cos(d)));
            tmpX = tmpValue;
            x[i]  = (int)tmpX + centerX;
            y[i]  = (int)tmpY + centerY;
        }
    }
    //#else
    //# public static void rotateCoordinates(int x[],int y[],int centerX,int centerY,int rotation)
    //# {
        //# 
    //# }
    //#endif
    public static boolean pnpoly(int npol, int xp[], int yp[], int x, int y)
    {
        int i, j;
        boolean c = false;
        for (i = 0, j = npol-1; i < npol; j = i++) {
            if ((((yp[i] <= y) && (y < yp[j])) ||
                ((yp[j] <= y) && (y < yp[i]))) &&
                (x < (xp[j] - xp[i]) * (y - yp[i]) / (yp[j] - yp[i]) + xp[i]))
            c = !c;
        }
        return c;
    }
//    protected static void stretchWidth(Control c)
//    {
//        c.setWidth(1);
//        int w = c.getWidth();
//        int maxWidth = c.getParent().getWidth() - c.getX();
//        while(w < maxWidth)
//        {
//            Control overlappedControl = isControlOverlapping(c.getId(),c.getX(),c.getY(),w + 1,c.getHeight(),c.getParent());
//            if(overlappedControl != null)
//            {
//                if(!isControlDependent(c, overlappedControl, true))
//                {
//                    break;
//                }
//            }
//            w++;
//        }
//        c.setWidth(w);
//        for (int i = 0; i < c.getParent().getSize(); i++) {
//            Control control = c.getParent().getChild(i);
//            if(control.getId() != c.getId())
//            {
//                if(isControlDependent(c, control, true))
//                {
//                    c.showNotify();
//                }
//            }
//        }
//        
//    }
//    protected static void stretchHeight(Control c)
//    {
//        c.setHeight(1);
//        int w = c.getHeight();
//        while(w < c.getParent().getHeight() - c.getHeight())
//        {
//            Control overlappedControl = isControlOverlapping(c.getId(),c.getX(),c.getY(),c.getWidth(),w,c.getParent());
//            if(overlappedControl != null)
//            {
//                if(!isControlDependent(c, overlappedControl, false))
//                {
//                    break;
//                }
//            }
//            w++;
//        }
//        c.setHeight(w);
//         for (int i = 0; i < c.getParent().getSize(); i++) {
//            Control control = c.getParent().getChild(i);
//            if(control.getId() != c.getId())
//            {
//                if(isControlDependent(c, control, false))
//                {
//                    c.showNotify();
//                }
//            }
//        }
//    }
   public static boolean isRectCollision(int xPos1,int ypos1,int width1,int height1,int xPos2,int ypos2,int width2,int height2)
     {
         if (xPos1>(xPos2+width2) || (xPos1+width1)<xPos2)
             return false;
         if (ypos1>(ypos2+height2) || (ypos1+height1)<ypos2)
             return false;
         
         
         return true;
//         If x0 > (x2 + w2) Or (x0 + w0) < x2 Then Return False
//	If y0 > (y2 + h2) Or (y0 + h0) < y2 Then Return False
//	Return True
         
         
//         return singleRectCollision(xPos1, ypos1, width1, height1, xPos2, ypos2, width2, height2) || singleRectCollision(xPos2, ypos2, width2, height2, xPos1, ypos1, width1, height1);
     }
//    private static Control isControlOverlapping(int id,int x,int y,int width,int height,Container parent)
//    {
//        for (int i = 0; i < parent.getSize(); i++) {
//            Control c = parent.getChild(i);
//            if(c.getId() != id)
//            {
//                if(isRectCollision(x, y, width, height, c.getX(), c.getY(), c.getWidth(), c.getHeight()))
//                {
//                    return c;
//                }
//            }
//        }
//        return null;
//    }
//    private static boolean isControlDependent(Control control,Control checkControl,boolean isFromWidth)
//    {
//        Vector vector = new Vector();
//        try {
//            while(true)
//            {
//                int id = -1;
//                if(isFromWidth)
//                    id = ((RelativeLayout)checkControl.getLayout()).getRelativeRelationX();
//                else
//                    id = ((RelativeLayout)checkControl.getLayout()).getRelativeRelationY();
//                if(id == control.getId())
//                {
//                    return true;
//                }
//                if(id == -1)
//                {
//                    return false;
//                }
//                checkControl = findControl(checkControl.getParent(), id);
//            }
//        } catch (Exception e) {
//            return false;
//        }
//       
//        
//    }
    public static Container getRootControl(Control c)
    {
        if(c == null)
            return null;
        while(c.getParent() != null)
        {
            c = c.getParent();
        }
        if(c instanceof Container)
        {
             return (Container)c;
        }else{
             return null;
        }
       
    }
    public static Control findControl(Container parent,int id)
    {
        if(id == -1)
            throw new RuntimeException("Trying to found invalid control");
        if(parent.getId() == id)
            return parent;
        for (int i = 0; i < parent.getSize(); i++) {
            Control object = (Control)parent.getChild(i);
            if(object.getId() == id)
                return object;
            if(object instanceof Container)
            {
                Control found = findControl((Container)object, id);
                if(found != null)
                {
                    return found;
                }
            }
        }
        return null;
    }
     
    public static int getChildernLayoutWrapHeight(Control control)
    {
         int h = control.getHeight();
//         if(control.getSizeSetting() == Control.WRAP_CONTENT && control instanceof Container && (!(control.getLayout() instanceof RelativeLayout) && !(control.getLayout() instanceof PropotionLayout) && control.getLayout() != null))
//         {
//             h = control.getLayout().getPreferedHeight((Container)control);
//         }
//         return h;
         if(control.isSkipParentWrapSizeCalc())
             return 0;
         if(control instanceof Container)
         {
             h = getWrappedHeight((Container)control);
         }else{
            if(control.getSizeSettingY() == Control.WRAP_CONTENT && control.getHeightWeight() <= 0 && !control.isAdjustDimentionsFromBgImage())
            {
                h = control.getPreferredHeight();
            }
         }
         return h;
    }
    public static int getChildernLayoutWrapWidth(Control control)
    {
         int h = control.getWidth();
//         if(control.getSizeSetting() == Control.WRAP_CONTENT && control instanceof Container && (!(control.getLayout() instanceof RelativeLayout) && !(control.getLayout() instanceof PropotionLayout) && control.getLayout() != null))
//         {
//             h = control.getLayout().getPreferedWidth((Container)control);
//         }
//         return h;
         if(control.isSkipParentWrapSizeCalc())
             return 0;
          if(control instanceof Container)
         {
             h = getWrappedWidth((Container)control);
         }else{
            if(control.getSizeSettingX() == Control.WRAP_CONTENT  && control.getWidthWeight() <= 0 && !control.isAdjustDimentionsFromBgImage())
            {
                h = control.getPreferredWidth();
            }
         }
         return h;
    }
    
    //////////////writting fountion
    
    public static int readSignedInt(ByteArrayInputStream bis,int numOfBytes) throws Exception
    {
//        byte[] data = new byte[numOfBytes];
//        bis.read(data);
        
        int value = read(bis, numOfBytes);
        int signBit = 0;
        int valueWitoutSign = value;
        if(numOfBytes == 1)
        {
            signBit = value & 0x80;
            valueWitoutSign = valueWitoutSign & 0x7f;
        }
        else if(numOfBytes == 2)
        {
            signBit = value & 0x8000;
            valueWitoutSign = valueWitoutSign & 32767;
        }else if(numOfBytes == 3)
        {
            signBit = value & 0x800000;
            valueWitoutSign = valueWitoutSign & (0x800000 - 1);
        }else if(numOfBytes == 4)
        {
            signBit = value & 0x800000;
            valueWitoutSign = valueWitoutSign & (0x800000 - 1);
        }
        
       if(signBit > 0)
       {
           valueWitoutSign = -valueWitoutSign;
       }
       return valueWitoutSign; 
    }
    public static void writeSignedInt(ByteArrayOutputStream bos,int i,int numOfBytes) throws Exception
    {
        int valueWithoutSign = Math.abs(i);
      
        int signValue = 1;
        if(i < 0)
        {
             if(numOfBytes == 1)
             {
                 signValue = 1 << 7;
             }else if(numOfBytes == 2){
                 signValue = 1 << 15;
             }else if(numOfBytes == 3)
             {
                 signValue = 1 << 23;
             }else{
                 signValue = 1 << 23;
             }
             valueWithoutSign = signValue | valueWithoutSign;
        }
        write(bos,valueWithoutSign, numOfBytes);
    }
    public static int read(ByteArrayInputStream fis,int noofBytes) throws Exception
    {
        
        byte[] data = new byte[noofBytes];
        fis.read(data);
        return byteToint(data,noofBytes);
    }
    public static int byteToint(byte[] temp,int noofBytes)
    {
            int _lib_pOffset = 0;
                switch (noofBytes)
                {
                    case 1:
                        _lib_pOffset = (temp[0] & 0xFF);
                        break;
                    case 2:
                        _lib_pOffset = (temp[0] & 0xFF);
                        _lib_pOffset += (temp[1] & 0xFF) << 8;
                        break;
                    case 3:
                        _lib_pOffset = (temp[0] & 0xFF);
                        _lib_pOffset += (temp[1] & 0xFF) << 8;
                        if(temp[2]==(byte)1)
                            _lib_pOffset=_lib_pOffset*(-1);

                        break;
                    case 4:
                        _lib_pOffset = (temp[0] & 0xFF);
                        _lib_pOffset += (temp[1] & 0xFF) << 8;
                        _lib_pOffset += (temp[2] & 0xFF) << 16;
                        _lib_pOffset += (temp[3] & 0xFF) << 24;
                        break;
                }
                return _lib_pOffset;
    }
    public static void write(ByteArrayOutputStream bos,int value,int noofBytes) throws Exception
    {
       bos.write(intToBytes(value, noofBytes));
    }
    private static byte[] bytes1 = new byte[1];
    private static byte[] bytes2 = new byte[2];
    private static byte[] bytes3 = new byte[3];
    private static byte[] bytes4 = new byte[4];
     public static byte[] intToBytes(int i, int noofBytes) {
//        System.out.println("wrote offset="+);
        boolean negative=false;
        if(i<0)
        {
            negative=true;
            i=i*(-1);
        }
		byte[] dword = null;
                switch(noofBytes)
                {
                    case 1:
                        dword = bytes1;
                        dword[0] = (byte) (i & 0x00FF);
                        break;
                    case 2:
                        dword = bytes2;
                        dword[0] = (byte) (i & 0x00FF);
                        dword[1] = (byte) ((i >> 8) & 0x000000FF);
                        break;
                    case 3:
                        dword = bytes3;
                        dword[0] = (byte) (i & 0x00FF);
                        dword[1] = (byte) ((i >> 8) & 0x000000FF);
                        if(negative)
                            dword[2] = (byte)1;
                        else
                            dword[2] = (byte)0;
                        break;
                    case 4:
                        dword = bytes4;
                        dword[0] = (byte) (i & 0x00FF);
                        dword[1] = (byte) ((i >> 8) & 0x000000FF);
                        dword[2] = (byte) ((i >> 16) & 0x000000FF);
                        dword[3] = (byte) ((i >> 24) & 0x000000FF);
                        break;
                }
		return dword;
	}
    
    
    public static void writeInt(ByteArrayOutputStream bos,int value,int numOfBytes) throws Exception
    {
        bos.write(intToBytes(value, numOfBytes));
    }
    public static void writeBoolean(ByteArrayOutputStream bos,boolean value) throws Exception
    {
        byte val = 0;
        if(value)
        {
            val = 1;
        }
        bos.write(val);
    }
    public static boolean readBoolean(ByteArrayInputStream bis) throws Exception
    {
        byte val = (byte)bis.read();
        return val == 1;
    }
    public static void writeColor(ByteArrayOutputStream bos,int value) throws Exception
    {
        if(value == -1)
        {
            bos.write(0);
        }else{
            bos.write(1);
            if(value < 0)
            {
                bos.write(1);
            }else{
                bos.write(0);
            }
            bos.write(intToBytes(Math.abs(value), 4));
        }
       
    }
    public static int readColor(ByteArrayInputStream bis) throws Exception
    {
        int val = bis.read();
        if(val == 0)
        {
            return -1;
        }
        int sign = bis.read();
        
        bis.read(bytes4);
        int finalValue = byteToint(bytes4, 4);
        if(sign == 1)
        {
            finalValue = -finalValue;
        }
        return finalValue;
    }
    public static void writeString(ByteArrayOutputStream bos,String value) throws Exception
    {
        if(value == null)
        {
            bos.write(0);
        }else{
            bos.write(1);
            MenuSerilize.serialize(value,bos);
        }
    }
   public static int readInt(ByteArrayInputStream bis,int numOfBytes) throws Exception
    {
        byte[] b= getByteArray(numOfBytes);
        bis.read(b);
        return byteToint(b, numOfBytes);
    }
    public static String readString(ByteArrayInputStream bis) throws Exception
    {
        int value = bis.read();
        if(value == 0)
            return null;
        return (String)MenuSerilize.deserialize(bis, null);
    }
    private static byte[] getByteArray(int numOfBytes)
    {
        switch(numOfBytes)
        {
            case 1:
                return bytes1;
            case 2:
                return bytes2;    
            case 3:
                return bytes3;      
            default:
                return bytes4;          
        }
    }
//    public static void writeInt(ByteArrayOutputStream bos,int value) throws Exception
//    {
//        MenuSerilize.serialize( new Integer(value),bos);
//    }
//    public static void writeString(ByteArrayOutputStream bos,String value) throws Exception
//    {
//        if(value == null)
//        {
//            bos.write(0);
//        }else{
//            bos.write(1);
//            MenuSerilize.serialize(value,bos);
//        }
//    }
//    public static int readInt(ByteArrayInputStream bis) throws Exception
//    {
//        return ((Integer)MenuSerilize.deserialize(bis, null)).intValue();
//    }
//    public static String readString(ByteArrayInputStream bis) throws Exception
//    {
//        int value = bis.read();
//        if(value == 0)
//            return null;
//        return (String)MenuSerilize.deserialize(bis, null);
//    }
//    public static void writeBoolean(ByteArrayOutputStream bos,boolean value) throws Exception
//    {
//        MenuSerilize.serialize(new Boolean(value),bos);
//    }
//    public static boolean readBoolean(ByteArrayInputStream bis) throws Exception
//    {
//       return ((Boolean)MenuSerilize.deserialize(bis, null)).booleanValue();
//    }
    public static void writeIntArray(ByteArrayOutputStream bos,int value[]) throws Exception
    {
        if(value == null)
        {
            bos.write(0);
        }else{
            bos.write(1);
            MenuSerilize.serialize(value,bos);
        }
    }
    public static int[] readIntArray(ByteArrayInputStream bis) throws Exception
    {
       int value = bis.read();
        if(value == 0)
            return null;
        return (int[])MenuSerilize.deserialize(bis, null);
    }
    public static void write2DIntArray(ByteArrayOutputStream bos,int value[][]) throws Exception
    {
        if(value == null)
        {
            bos.write(0);
        }else{
            bos.write(1);
            MenuSerilize.serialize(value,bos);
        }
    }
    public static int[][] read2DIntArray(ByteArrayInputStream bis) throws Exception
    {
       int value = bis.read();
        if(value == 0)
            return null;
        return (int[][])MenuSerilize.deserialize(bis, null);
    }
    public static void writeBooleanArray(ByteArrayOutputStream bos,boolean value[]) throws Exception
    {
        if(value == null)
        {
            bos.write(0);
        }else{
            bos.write(1);
            MenuSerilize.serialize(value,bos);
        }
    }
    public static boolean[] readBooleanArray(ByteArrayInputStream bis) throws Exception
    {
       int value = bis.read();
        if(value == 0)
            return null;
        return (boolean[])MenuSerilize.deserialize(bis, null);
    }
    public static void prepareDisplay(ScrollableContainer container)
    {
        container.showNotify();
        container.resetAnimations();
        EventQueue.getInstance().reset();
        if(container.isSelectable())
        {
            container.fourceSelect();
        }
    }
    public static void reallignContainer(ScrollableContainer container)
    {
        container.init();
        prepareDisplay(container);
    }
    public static ScrollableContainer loadContainer(byte[] data) throws Exception
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ScrollableContainer container = (ScrollableContainer)MenuSerilize.deserialize(bis, MenuSerilize.getInstance());
        container.port();
        container.init();
        prepareDisplay(container);
        return container;
    }
    public static ScrollableContainer loadContainer(byte[] data,int masterWidth,int masterHeight,int newWidth,int newHeight,boolean isTouch) throws Exception
    {
        Settings.MASTER_SCREEN_WIDTH = masterWidth;
        Settings.MASTER_SCREEN_HEIGHT = masterHeight;
        Settings.SCREEN_WIDTH = newWidth;
        Settings.SCREEN_HEIGHT = newHeight;
        Settings.TOUCH_DEVICE = isTouch;
        Settings.enableAnimations = true;
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Settings.VERSION_NUMBER_FOUND = Util.readInt(bis, 1);
        ScrollableContainer container = (ScrollableContainer)MenuSerilize.deserialize(bis, MenuSerilize.getInstance());
        container.port();
        container.init();
        prepareDisplay(container);
        return container;
    }
   
    public static final int getAddonParentsX(Control control)
    {
        int addonX = 0;
        while(control.getParent() != null)
        {
            addonX += control.getParent().getX() + control.getParent().getLeftInBound();
            control = control.getParent();
        }
        return addonX;
    }
    public static final int getAddonParentsY(Control control)
    {
        int addonY = 0;
        while(control.getParent() != null)
        {
            addonY += control.getParent().getY() + control.getParent().getTopInBound();
            control = control.getParent();
        }
        return addonY;
    }
    //#ifdef ANDROID
    public static int getImageWidth(Bitmap image)
    //#else
    //# public static int getImageWidth(Image image)
    //#endif
    {
        //#ifdef DEKSTOP_TOOL
        //# return image.getWidth(null);
        //#else
        return image.getWidth();
        //#endif
    }
     //#ifdef ANDROID
     public static int getImageHeight(Bitmap image)
    //#else
    //# public static int getImageHeight(Image image)
    //#endif
   
    {
        //#ifdef DEKSTOP_TOOL
        //# return image.getHeight(null);
        //#else
        return image.getHeight();
        //#endif
    }
     
     public static void drawImage(Canvas g,Bitmap image,int x,int y,Paint paintObject)
     //#else
     //# public static void drawImage(Graphics g,Image image,int x,int y)
     //#endif
     {
          //#ifdef DEKSTOP_TOOL
             //# g.drawImage(image, x, y, null);
         //#elifdef J2ME
     	 //# g.drawImage(image, x, y, 0);
     	//#else
     	GraphicsUtil.drawBitmap(g, image, x, y, 0,paintObject);
         //#endif
         
     }
     
//    //#ifdef ANDROID
//    public static void drawImage(Canvas g,Bitmap image,int x,int y)
//    //#else
//    //# public static void drawImage(Graphics g,Image image,int x,int y)
//    //#endif
//    {
//         //#ifdef DEKSTOP_TOOL
//            //# g.drawImage(image, x, y, null);
//        //#elifdef J2ME
//    	 //# g.drawImage(image, x, y, 0);
//    	//#else
//    	GraphicsUtil.drawBitmap(g, image, x, y, 0,null);
//        //#endif
//        
//    }
}
