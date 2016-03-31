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
import com.appon.miniframework.MenuSerilize;


/**
 *
 * @author acer
 */
public class DummyControl extends Control{

    public DummyControl() {
        super(DEFAULT_ID);
    }
    public DummyControl(int id) {
        super(id);
    }

   //#ifdef ANDROID
    public void paint(Canvas g, Paint paintObject)
    {
       
    }
   //#else
    //# public void paint(Graphics g)
    //# {
       //# 
    //# }
    //#endif
 
    public String toString() {
        return "DummyControl-"+getId();
    }
    public int getClassCode() {
        return MenuSerilize.CONTROL_DUMMY_TYPE;
    }
}
