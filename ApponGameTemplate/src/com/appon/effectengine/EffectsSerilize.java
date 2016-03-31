/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

import com.appon.util.Serilizable;
import com.appon.util.Serilize;



/**
 *
 * @author acer
 */
public class EffectsSerilize extends Serilize {

    //LATEST:  public static final int LAYOUT_GRID_ARRANGER = 1018;
    private static EffectsSerilize instance;
    public static final int EFFECT_TYPE = 2000;
    public static final int EFFECT_LAYER_TYPE = 2001;
    public static final int TIME_FRAME_TYPE = 2002;
    public static final int EFFECT_GROUP_TYPE = 2003;
    public static final int SHAPE_TYPE_EARC = 2010;
    public static final int SHAPE_TYPE_EGANIMATIONFRAME = 2011;
    public static final int SHAPE_TYPE_EGFONT = 2012;
    public static final int SHAPE_TYPE_EGFRAME = 2013;
    public static final int SHAPE_TYPE_EGMODULE = 2014;
    public static final int SHAPE_TYPE_IMAGE = 2015;
    public static final int SHAPE_TYPE_LINE = 2016;
    public static final int SHAPE_TYPE_POLYGON = 2017;
    public static final int SHAPE_TYPE_TRAINGLE = 2018;
    public static final int SHAPE_TYPE_RECT = 2019;
    public static final int SHAPE_TYPE_TIMEFRAME = 2020;

    private EffectsSerilize() {
    }

    public static EffectsSerilize getInstance() {
        if(instance == null)
        {
            instance = new EffectsSerilize();
        }
        return instance;
    }
    
    public static boolean isShapeType(int classcode)
    {
        return classcode >= SHAPE_TYPE_EARC && classcode <= SHAPE_TYPE_TIMEFRAME;
    }
    public Serilizable getClassObject(int classcode,int id,int additionalData) {
        switch(classcode)
        {
            case EFFECT_TYPE:
                return new Effect();
            case EFFECT_LAYER_TYPE:
                return new EffectLayer();
            case TIME_FRAME_TYPE:
                return new TimeFrame();   
            case EFFECT_GROUP_TYPE:
                return new EffectGroup();
            case SHAPE_TYPE_EARC:
                return new EArc(id);   
            case SHAPE_TYPE_EGANIMATIONFRAME:
                return new EGAnimationFrame(id);
            case SHAPE_TYPE_EGFONT:
                return new EGFont(id);
            case SHAPE_TYPE_EGFRAME:
                return new EGFrame(id);   
            case SHAPE_TYPE_EGMODULE:
                return new EGModule(id);   
            case SHAPE_TYPE_IMAGE:
                 return new EImage(id);   
            case SHAPE_TYPE_LINE:
                return new ELine(id);   
            case SHAPE_TYPE_POLYGON:
                return new EPolygon(id);   
            case SHAPE_TYPE_TRAINGLE:
                return new ETraingle(id);   
            case SHAPE_TYPE_RECT:
                return new ERect(id);   
            case SHAPE_TYPE_TIMEFRAME:
                return new ETimeFrameShape(id);   
                
        }
        return null;
    }
    
   
}
