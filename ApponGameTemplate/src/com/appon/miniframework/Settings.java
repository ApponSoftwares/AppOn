/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;
//#ifdef J2ME
//# import javax.microedition.lcdui.Canvas;
//#endif

/**
 *
 * @author acer
 */
public class Settings {
     public static int VERSION_NUMBER_FOUND = 4;
     
    //#ifdef J2ME
	//# public static final int UP = Canvas.UP;
	//# public static final int DOWN = Canvas.DOWN;
	//# public static final int LEFT = Canvas.LEFT;
	//# public static final int RIGHT = Canvas.RIGHT;
	//# public static final int KEY_SOFT_CENTER = Canvas.FIRE;
	//# public static final int FIRE = KEY_SOFT_CENTER;
    //#else
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int KEY_SOFT_CENTER = 4;
    public static final int FIRE = KEY_SOFT_CENTER;
   
    //#endif
   
    public static int SCREEN_WIDTH = 360;
    public static int SCREEN_HEIGHT = 640;
    public static int MASTER_SCREEN_WIDTH = 360;
    public static int MASTER_SCREEN_HEIGHT = 640;
    public static int TRANSLATED_Y = 0;
    public static int TRANSLATED_X = 0;
    public static boolean TOUCH_DEVICE = true;
    public static boolean enableAnimations = false;
        
}
