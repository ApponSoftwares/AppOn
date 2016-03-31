/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;



//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#elifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;
//#endif

import com.appon.miniframework.Control;
import com.appon.util.Serilizable;


/**
 *
 * @author acer
 */
public abstract class CustomControl extends Control implements Serilizable{
    private int identifier;

    public CustomControl() {
        super(DEFAULT_ID);
    }
    public CustomControl(int id) {
        super(id);
    }
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }
    //#ifdef ANDROID
    public abstract void paint(Canvas g, Paint paintObject) ;
    //#else
    //# public abstract void paint(Graphics g) ;
    //#endif
    
    
    public abstract int getPreferredHeight();
    public abstract int getPreferredWidth();
    public abstract void callpointerPressed(int x, int y);
    public abstract void callpointerRealease(int x, int y);
  
    public String toString() {
         return "CustomControl- "+getId();
    }
	public void onpointerPressed(int x, int y) {
		callpointerPressed(x, y);
	}
	public void onpointerRealease(int x, int y) {
		callpointerRealease(x, y);
	}
    
}
