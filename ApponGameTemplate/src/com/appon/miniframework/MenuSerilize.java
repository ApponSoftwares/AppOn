/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;


import com.appon.miniframework.animation.EndAnimationLinearMoveOut;
import com.appon.miniframework.animation.StartAnimZoomOut;
import com.appon.miniframework.animation.StartAnimationLinearMoveIn;
import com.appon.miniframework.animation.SteadyFloatAnimation;
import com.appon.miniframework.controls.CustomControl;
import com.appon.miniframework.controls.DummyControl;
import com.appon.miniframework.controls.GradualUpdate;
import com.appon.miniframework.controls.ImageControl;
import com.appon.miniframework.controls.MultilineTextControl;
import com.appon.miniframework.controls.ProgressBar;
import com.appon.miniframework.controls.TabControl;
import com.appon.miniframework.controls.TabPane;
import com.appon.miniframework.controls.TextControl;
import com.appon.miniframework.controls.ToggleIconControl;
import com.appon.miniframework.layout.GridArrangerLayout;
import com.appon.miniframework.layout.GridLayout;
import com.appon.miniframework.layout.LinearLayout;
import com.appon.miniframework.layout.PropotionLayout;
import com.appon.miniframework.layout.RelativeLayout;
import com.appon.util.Serilizable;
import com.appon.util.Serilize;
//#ifdef DEKSTOP_TOOL
//# import com.appon.ui.model.CustomControlImpl;
//#endif


/**
 *
 * @author acer
 */
public class MenuSerilize extends Serilize {

    //LATEST:  public static final int LAYOUT_GRID_ARRANGER = 1018;
    private static MenuSerilize instance;
    public static final int CONTROL_TEXT_TYPE = 1000;
    public static final int CONTROL_IMAGE_TYPE = 1001;
    public static final int CONTROL_MULTILINE_TYPE = 1002;
    public static final int CONTROL_DUMMY_TYPE = 1003;
    public static final int CONTROL_CUSTOM_TYPE = 1004;
    public static final int CONTROL_SCROLLABLE_CONTAINER_TYPE = 1005;
    public static final int CONTROL_GRADUAL_UPDATE_TYPE = 1015;
    public static final int CONTROL_TAB_CONTROL_TYPE = 1016;
    public static final int CONTROL_TAB_PANE_CONTROL_TYPE = 1017;
    
    
    public static final int LAYOUT_GRID_TYPE = 1006;
    public static final int LAYOUT_LINEAR_TYPE = 1007;
    public static final int LAYOUT_PROPOTION_TYPE = 1008;
    public static final int LAYOUT_RELATIVE_TYPE = 1009;
    public static final int LAYOUT_GRID_ARRANGER = 1018;
    
    
    public static final int ANIMATION_STEADY_FLOAT = 1010;
    public static final int ANIMATION_LINEAR_MOVE_IN = 1011;
    public static final int ANIMATION_LINEAR_MOVE_OUT = 1012;
    public static final int CONTROL_TOGGLE_ICON = 1013;
    public static final int CONTROL_PROGRESS_BAR = 1014;
    public static final int ANIMATION_ZOOM_OUT = 1019;

    private MenuSerilize() {
    }

    public static MenuSerilize getInstance() {
        if(instance == null)
        {
            instance = new MenuSerilize();
        }
        return instance;
    }
    
    public static boolean isControlType(int classId)
    {
        if((classId >= CONTROL_TEXT_TYPE &&  classId <= CONTROL_SCROLLABLE_CONTAINER_TYPE) || (classId == CONTROL_TOGGLE_ICON || classId == CONTROL_PROGRESS_BAR || classId == CONTROL_GRADUAL_UPDATE_TYPE || classId == CONTROL_TAB_PANE_CONTROL_TYPE|| classId == CONTROL_TAB_CONTROL_TYPE))
          return true;
        return false;
    }
    public Serilizable getClassObject(int classcode,int id,int additionalData) {
        switch(classcode)
        {
            case CONTROL_GRADUAL_UPDATE_TYPE:
                return new GradualUpdate(id);
            case CONTROL_TEXT_TYPE:
                return new TextControl(id);
            case CONTROL_IMAGE_TYPE:
                return new ImageControl(id);
            case CONTROL_MULTILINE_TYPE:
                return new MultilineTextControl(id);   
            case CONTROL_DUMMY_TYPE:
                return new DummyControl(id);   
            case CONTROL_SCROLLABLE_CONTAINER_TYPE:
                return new ScrollableContainer(id);
            case CONTROL_CUSTOM_TYPE:
                CustomControl c =  getCustomControl(id,additionalData);
                if(c == null)
                {
                    throw new RuntimeException("You forgot to add custom object declreations in MenuSerilize");
                }
                c.setId(id);
                return c;
            case LAYOUT_GRID_TYPE:  
                return new GridLayout();
            case LAYOUT_LINEAR_TYPE:  
                return new LinearLayout();
            case LAYOUT_PROPOTION_TYPE:  
                return new PropotionLayout();
            case LAYOUT_RELATIVE_TYPE:  
                return new RelativeLayout();
              
            case ANIMATION_STEADY_FLOAT:  
                return new SteadyFloatAnimation();
            
            case ANIMATION_LINEAR_MOVE_IN:  
                return new StartAnimationLinearMoveIn();
                
            case ANIMATION_ZOOM_OUT:  
                return new StartAnimZoomOut();
           
            case ANIMATION_LINEAR_MOVE_OUT:  
                return new EndAnimationLinearMoveOut();
           
            case CONTROL_TOGGLE_ICON:  
                return new ToggleIconControl(id);
                
            case CONTROL_PROGRESS_BAR:  
                return new ProgressBar(id);
                
            case CONTROL_TAB_PANE_CONTROL_TYPE:  
                return new TabPane(id);
                
            case CONTROL_TAB_CONTROL_TYPE:  
                return new TabControl(id);
                
            case LAYOUT_GRID_ARRANGER:  
                return new GridArrangerLayout();   
        }
       return null;
    }
    
    public CustomControl getCustomControl(int id,int identifier)
    {
    	//#ifdef DEKSTOP_TOOL
        //# return new CustomControlImpl(id);
        //#else
        return null;
        //#endif

}

}
