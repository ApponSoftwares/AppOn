/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#endif

/**
 *
 * @author acer
 */
public abstract class EffectShape {
	  //#ifdef ANDROID
	public abstract void paint(Canvas g,Paint p);
   //#else
     //# public abstract void paint(Graphics g);
   //#endif
    
    
}
