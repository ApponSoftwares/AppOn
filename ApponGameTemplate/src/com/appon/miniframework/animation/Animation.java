/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.animation;

import android.graphics.Canvas;
//#endif
//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//#elifdef ANDROID
import android.graphics.Paint;

import com.appon.miniframework.Control;
import com.appon.miniframework.Event;
import com.appon.miniframework.EventManager;
import com.appon.miniframework.Util;
import com.appon.util.Serilizable;

/**
 *
 * @author acer
 */
public abstract class Animation implements Serilizable{
    int origionalX , origionalY , origionalRotation;
    int actualX , actualY;
    private boolean isAnimationOver;
    private boolean isReverse=false;
    Control control;
    private boolean initiated = false;
    private boolean goForRestore = false;
    private boolean animationApplied = false;
    public void registerControl(Control control)
    {
        this.control = control;
    }
    public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}
    public boolean isReverse() {
		return isReverse;
	}
    public boolean isAnimationOver()
    {
        return isAnimationOver;
    }

    public int getOrigionalX() {
        return origionalX;
    }

    public int getOrigionalY() {
        return origionalY;
    }

    public int getOrigionalRotation() {
        return origionalRotation;
    }
    public boolean restore()
    {
        if(initiated && control != null && goForRestore)
        {
            control.setX(origionalX);
            control.setY(origionalY);
            control.setRotation(origionalRotation);
            goForRestore = false;
            return true;
        }
        return false;
    }
    public void setIsAnimationOver(boolean isAnimationOver) {
        this.isAnimationOver = isAnimationOver;
        if(isAnimationOver && !(this instanceof EndAnimation))
        {
           restore();
        }
         if(isAnimationOver && control != null && control.getEventListener() != null)
        {
            if((this instanceof EndAnimation))
            {
                control.getEventListener().event(new Event(EventManager.END_ANIMATION_OVER, control, null));
            }else{
                control.getEventListener().event(new Event(EventManager.START_ANIMATION_OVER, control, null));
                
            }
        }
    }
    public void reset()
    {
        animationApplied = false;
        setIsAnimationOver(false);
        origionalX = control.getX();
        origionalY = control.getY();
        actualX = Util.getActualX(control);
        actualY = Util.getActualY(control);
        origionalRotation = control.getRotation();
        initiated = true;
        goForRestore = true;
    }
    //#ifdef ANDROID
     public Object restoreAnimationEffect(Canvas g,Paint paintObject)
    //#else
    //# public Object restoreAnimationEffect()
    //#endif
   
    {
        return null;
    }
    public int getActualX() {
        return actualX;
    }

    public int getActualY() {
        return actualY;
    }
    
    public Control getControl() {
        return control;
    }
     //#ifdef ANDROID
      public final Object doAnimation(Canvas g,Paint paintObject)
    //#else
    //# public final Object doAnimation(Graphics g)
    //#endif
    {
        if(isAnimationOver())
            return null;
          //#ifdef ANDROID
           Object object =applyAnimation(g,paintObject);
          //#else
         //# Object object = applyAnimation(g);
         //#endif
      
        animationApplied = true;
        return object;
    }
    public boolean isAnimationApplied() {
        boolean value = animationApplied;
        animationApplied = false;
        return value;
    }
    //#ifdef ANDROID
     public abstract Object applyAnimation(Canvas g,Paint paintObject);
    //#else
    //# public abstract Object applyAnimation(Graphics g);
    //#endif
    
    public void port()
    {
        //nothing to port
    }
}
