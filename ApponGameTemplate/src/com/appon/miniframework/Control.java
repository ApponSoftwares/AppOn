/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;

import java.io.ByteArrayInputStream;


//#ifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.appon.gtantra.GraphicsUtil;
import com.appon.game.MyGameConstants;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Image;
//# import java.awt.Graphics;
//# import java.awt.Graphics2D;
//# import java.awt.Rectangle;
//# import java.awt.geom.AffineTransform;
//# import java.io.ByteArrayOutputStream;
//# import com.appon.ui.model.CommanProperties;
//#endif
import com.appon.miniframework.animation.EndAnimation;
import com.appon.miniframework.animation.StartAnimation;
import com.appon.miniframework.animation.SteadyAnimation;
import com.appon.miniframework.controls.CustomControl;
import com.appon.miniframework.layout.RelativeLayout;
import com.appon.util.Serilizable;


/**
 *
 * @author user
 */
public abstract class Control implements Serilizable
{
   //#ifdef ANDROID 
    private Bitmap selectionBorderImage;
    //#else
    //# private Image selectionBorderImage;
    //#endif
    public static final int DEFAULT_ID= -1;
    private int id;
    protected int width, height,x,y;
    
    boolean visible = true;
    boolean selected;
    boolean selectable = true;    
    private Container parent;
    private EventManager eventManager;
    public static final int RECT_TYPE = 0;
    public static final int ROUND_RECT_TYPE = 1;
    private int bgType = RECT_TYPE;
    private static final int ARC_RADIOUS_WIDTH = 22;
    private static final int ARC_RADIOUS_HEIGHT = 22;
    private int borderThickness = 1;
    int rotation;
    private int sizeSettingX,sizeSettingY;
    public static final int WRAP_CONTENT = 0;
    public static final int FILL_PARENT = 1;
    public static final int CUSTOM_SIZE = 2;
    public static final int MAX_CHILDRENS_SIZE = 3;
    public static final int CONTROL_RELATIVE_SIZE = 4;
    public static final int IMAGE_RELATIVE_SIZE = 5;
    private int additionalWidth , additionalHeight;
    private boolean portWidth , portHeight;
    private boolean stretchBg , stretchSelBg;
    private Layout layout;
    SteadyAnimation steadyAnimation;
    StartAnimation startAnimation;
    EndAnimation endAnimation;
    private boolean clickable = false;
    private boolean clipData = false;
    private int origionalX ,origionalY;
    private int widthWeight,heightWeight;
    private boolean adjustDimentionsFromBgImage = false;
    // selection properties
    private boolean endAnimOnSelf = false;
    private int selectionAdditionalX;
    private int selectionAdditionalY;
    private int zoomPersentOnSelection;
    private int delayInSelection;
    private String userData;
    private int bgImageResourceId = -1;
    private int selectionBgImageResourceId = -1;
    protected int leftChild = -1;
    protected int rightChild = -1;
    protected int downChild = -1;
    protected int upChild = -1;

    private int selectionBorderImageId = -1;
    RelativeLayout relativeLayout;
    private int leftInBound = 0;
    private int rightInBound = 0;
    private int topInBound = 0;
    private int bottomInBound = 0;
    private boolean portBounds = false;
    private int cornerPNGBgResId = -1;
    NinePatchPNGBox ninePatchCornerPngBox;
    
    protected int selectionBorderColor  = -1;
    protected int borderColor = -1;
    protected int bgColor = -1;
    protected int selectionBgColor = -1;
    CornersPNGBox cornerPngBg;
    private int sizeRelationX;
    private int sizeRelationY;
    private boolean skipParentWrapSizeCalc = false;
    private int addShifPerFromSizeX,addShifPerFromSizeY;
    //#ifdef ANDROID
    protected Bitmap bgImage,selectionBgImage;
    //#else
    //# protected Image bgImage,selectionBgImage;
    //#endif
    private int customKeyEventCode = Integer.MAX_VALUE;
    private boolean touchSelectable;
    
    
    public Control(int id) {
       setId(id);
       setSizeSettingX(WRAP_CONTENT);
       setBorderColor(0);
    }
    public void setSoftKeyEvent(int keyCode,int customKeyEvent) {
        ((ScrollableContainer)Util.getRootControl(this)).addSoftKeyEvent(keyCode, this);
        setCustomKeyEventCode(customKeyEvent);
    }

    public void setTouchSelectable(boolean touchSelectable) {
        this.touchSelectable = touchSelectable;
    }

    public boolean isTouchSelectable() {
        return touchSelectable;
    }

    public void setCustomKeyEventCode(int customKeyEventCode) {
        this.customKeyEventCode = customKeyEventCode;
    }

    public int getAddShifPerFromSizeX() {
        return addShifPerFromSizeX;
    }

    public int getAddShifPerFromSizeY() {
        return addShifPerFromSizeY;
    }

    public void setAddShifPerFromSizeX(int addShifPerFromSizeX) {
        this.addShifPerFromSizeX = addShifPerFromSizeX;
    }

    public void setAddShifPerFromSizeY(int addShifPerFromSizeY) {
        this.addShifPerFromSizeY = addShifPerFromSizeY;
    }
    
    public int getLeftInBound() {
        return leftInBound;
    }

    public NinePatchPNGBox getNinePatchCornerPngBox() {
        return ninePatchCornerPngBox;
    }

    public void setNinePatchCornerPngBox(NinePatchPNGBox ninePatchCornerPngBox) {
        this.ninePatchCornerPngBox = ninePatchCornerPngBox;
    }

    public boolean isPortBounds() {
        return portBounds;
    }

    public void setPortBounds(boolean portBounds) {
        this.portBounds = portBounds;
    }

    public int getRightInBound() {
        return rightInBound;
    }

    public int getTopInBound() {
        return topInBound;
    }

    public int getBottomInBound() {
        return bottomInBound;
    }

    public void setLeftInBound(int leftInBound) {
        this.leftInBound = leftInBound;
    }

    public void setRightInBound(int rightInBound) {
        this.rightInBound = rightInBound;
    }

    public void setTopInBound(int topInBound) {
        this.topInBound = topInBound;
    }

    public void setBottomInBound(int bottomInBound) {
        this.bottomInBound = bottomInBound;
    }
    public void setUserData(String userData) {
        this.userData = userData;
    }

    public int getSelectionBorderImageId() {
        return selectionBorderImageId;
    }
    public void setRelativeLocation(RelativeLayout layout)
    {
        this.relativeLayout = layout;
    }

    public RelativeLayout getRelativeLocation() {
        return relativeLayout;
    }

   
    
    public void setSelectionBorderImageId(int selectionBorderImageId) {
        this.selectionBorderImageId = selectionBorderImageId;
    }

    public void setSizeRelationX(int sizeRelationX) {
        this.sizeRelationX = sizeRelationX;
    }

    public int getSizeRelationY() {
        return sizeRelationY;
    }

    public int getSizeRelationX() {
        return sizeRelationX;
    }

    public void setSizeRelationY(int sizeRelationY) {
        this.sizeRelationY = sizeRelationY;
    }

       //#ifdef ANDROID 
    public Bitmap getSelectionBorderImage() {
        return selectionBorderImage;
    }
      public void setSelectionBorderImage(Bitmap selectionBorderBitmap) {
        this.selectionBorderImage = selectionBorderBitmap;
    }
        public void setBgImage(Bitmap image) 
  {
         this.bgImage = image;
        if(this.bgImage!= null && (getWidth() < Util.getImageWidth(this.bgImage) || getHeight() <Util.getImageHeight(getBgImage())))
        {
             setWidth(Util.getImageWidth(this.bgImage));
            setHeight(Util.getImageHeight(this.bgImage));
        }
    }

    public Bitmap getBgImage() {
        return this.bgImage;
    }
        public void setSelectionBgImage(Bitmap selectionBgBitmap) {
        this.selectionBgImage = selectionBgBitmap;
        if(this.selectionBgImage != null && (getWidth() < Util.getImageWidth(this.selectionBgImage) || getHeight() < Util.getImageHeight(this.selectionBgImage)))
        {
            setWidth(Util.getImageWidth(this.selectionBgImage));
            setHeight(Util.getImageHeight(this.selectionBgImage));
        }
    }
    
     public Bitmap getSelectionBgImage() {
        return selectionBgImage;
    }

    //#else
    //# public Image getSelectionBorderImage() {
        //# return selectionBorderImage;
    //# }
      //# public void setSelectionBorderImage(Image selectionBorderBitmap) {
        //# this.selectionBorderImage = selectionBorderBitmap;
    //# }
    //# public Image getSelectionBgImage() {
        //# return selectionBgImage;
    //# }
   //# 
   //# public void setBgImage(Image image) 
   //# {
       //# this.bgImage = image;
        //# if(this.bgImage!= null && (getWidth() < Util.getImageWidth(this.bgImage) || getHeight() <Util.getImageHeight(getBgImage())))
        //# {
            //# setWidth(Util.getImageWidth(this.bgImage));
            //# setHeight(Util.getImageHeight(this.bgImage));
        //# }
    //# }
//# 
    //# public Image getBgImage() {
        //# return this.bgImage;
    //# }
        //# public void setSelectionBgImage(Image selectionBgBitmap) {
        //# this.selectionBgImage = selectionBgBitmap;
        //# if(this.selectionBgImage != null && (getWidth() < Util.getImageWidth(this.selectionBgImage) || getHeight() < Util.getImageHeight(this.selectionBgImage)))
        //# {
            //# setWidth(Util.getImageWidth(this.selectionBgImage));
            //# setHeight(Util.getImageHeight(this.selectionBgImage));
        //# }
    //# }
    //# 
    //#endif
   

  

    public int getDownChild() {
        return downChild;
    }

    public void setDownChild(int downChild) {
        this.downChild = downChild;
    }

    public int getUpChild() {
        return upChild;
    }

    public void setUpChild(int upChild) {
        this.upChild = upChild;
    }

    public int getRightChild() {
        return rightChild;
    }

    public void setRightChild(int rightChild) {
        this.rightChild = rightChild;
    }

    public int getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(int leftChild) {
        this.leftChild = leftChild;
    }

    public int getSelectionBgImageResourceId() {
        return selectionBgImageResourceId;
    }

    public int getBgImageResourceId() {
        return bgImageResourceId;
    }

    public void setSelectionBgImageResourceId(int selectionBgImageResourceId) {
        this.selectionBgImageResourceId = selectionBgImageResourceId;
    }

    public void setBgImageResourceId(int bgImageResourceId) {
        this.bgImageResourceId = bgImageResourceId;
    }

    public String getUserData() {
        return userData;
    }
    
    public int getHeightWeight() {
        return heightWeight;
    }
    public int getZoomPersentOnSelection() {
        return zoomPersentOnSelection;
    }

    public int getDelayInSelection() {
        return delayInSelection;
    }

    public void setDelayInSelection(int delayInSelection) {
        this.delayInSelection = delayInSelection;
    }

    public void setZoomPersentOnSelection(int zoomPersentOnSelection) {
        this.zoomPersentOnSelection = zoomPersentOnSelection;
    }

    public int getSelectionAdditionalY() {
        return selectionAdditionalY;
    }

    public int getSelectionAdditionalX() {
        return selectionAdditionalX;
    }

    public void setSelectionAdditionalY(int selectionAdditionalY) {
        this.selectionAdditionalY = selectionAdditionalY;
    }

    public void setSelectionAdditionalX(int selectionAdditionalX) {
        this.selectionAdditionalX = selectionAdditionalX;
    }

    public boolean isEndAnimOnSelf() {
        return endAnimOnSelf;
    }

    public void setEndAnimOnSelf(boolean endAnimOnSelf) {
        this.endAnimOnSelf = endAnimOnSelf;
    }

    public boolean isAdjustDimentionsFromBgImage() {
        return adjustDimentionsFromBgImage;
    }

    public void setAdjustDimentionsFromBgImage(boolean adjustDimentionsFromBgBitmap) {
        this.adjustDimentionsFromBgImage = adjustDimentionsFromBgBitmap;
    }

    public int getWidthWeight() {
        return widthWeight;
    }

    public void setHeightWeight(int heightWeight) {
        this.heightWeight = heightWeight;
    }

    public void setWidthWeight(int widthWeight) {
        this.widthWeight = widthWeight;
    }

    public int getOrigionalY() {
        return origionalY;
    }

    public int getOrigionalX() {
        return origionalX;
    }
   public int getPreferredWidth()
   {
       return 100;
   }
   public int getPreferredHeight()
   {
       return 100;
   }
    public void setOrigionalY(int origionalY) {
        this.origionalY = origionalY;
    }

    public void setOrigionalX(int origionalX) {
        this.origionalX = origionalX;
    }

    public boolean isClipData() {
        return clipData;
    }

    public void setClipData(boolean clipData) {
        this.clipData = clipData;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setSteadyAnimation(SteadyAnimation steadyAnimation) {
        this.steadyAnimation = steadyAnimation;
    }

    public void setStartAnimation(StartAnimation startAnimation) {
        this.startAnimation = startAnimation;
    }

    public void setEndAnimation(EndAnimation endAnimation) {
        this.endAnimation = endAnimation;
    }

    public SteadyAnimation getSteadyAnimation() {
        return steadyAnimation;
    }

    public StartAnimation getStartAnimation() {
        return startAnimation;
    }

    public EndAnimation getEndAnimation() {
        return endAnimation;
    }
 
    public Layout getLayout() {
        return layout;
    }
    public void resetAnimations()
    {
        if(endAnimation != null)
        {
            endAnimation.registerControl(this);
            endAnimation.reset();
        }if(steadyAnimation != null)
        {
            steadyAnimation.registerControl(this);
            steadyAnimation.reset();
        }
        if(startAnimation != null)
        {
            startAnimation.registerControl(this);
            startAnimation.reset();
        }
    }
    //priority
    //1. fill screen
    //2. wrap content
    //3. layout width
    //4. image stretch
    //5. weight stretch
    public void resize()
    {
        if(sizeSettingX == FILL_PARENT)
        {
            if(getParent() == null)
            {
                setWidth(MyGameConstants.SCREEN_WIDTH);
               
            }else{
                setWidth(getParent().getBoundWidth());
               
            }
        }else if(sizeSettingX == WRAP_CONTENT)
        {
            if(getWidthWeight() <= 0)
                setWidth(getPreferredWidth());
        }else if(sizeSettingX == CONTROL_RELATIVE_SIZE )
        {
            Control c = Util.findControl(Util.getRootControl(this), getSizeRelationX());
            if(c != null)
            {
                setWidth(c.getWidth());
            }
        }else if(sizeSettingX == IMAGE_RELATIVE_SIZE )
        {
        	//#ifdef ANDROID
            Bitmap image = ResourceManager.getInstance().getImageResource(getSizeRelationX());
          //#else
            //# Image image = ResourceManager.getInstance().getImageResource(getSizeRelationX());
            //#endif
            
            if(image != null)
            {
                setWidth(Util.getImageWidth(image));
            }
        }else if(sizeSettingX == MAX_CHILDRENS_SIZE )
        {
            if(parent != null)
            {
                int size = parent.getSize();
                int w = 0;
                for (int i = 0; i < size; i++) {
                   int w1 = ((Control)parent.getChild(i)).getWidth();
                   if(w1 > w)
                   {
                       w = w1;
                   }
                }
                setWidth(w);
            }
        }
        
        
        
        if(sizeSettingY == FILL_PARENT)
        {
            if(getParent() == null)
            {
                setHeight(MyGameConstants.SCREEN_HEIGHT);
            }else{
                setHeight(getParent().getBoundHeight());
            }
        }else if(sizeSettingY == WRAP_CONTENT)
        {
            if(getHeightWeight() <= 0)
                setHeight(getPreferredHeight());
        }else if(sizeSettingY == CONTROL_RELATIVE_SIZE )
        {
            Control c = Util.findControl(Util.getRootControl(this), getSizeRelationY());
            if(c != null)
            {
                setHeight(c.getHeight());
            }
        }else if(sizeSettingY == IMAGE_RELATIVE_SIZE )
        {
        	//#ifdef ANDROID
            Bitmap image = ResourceManager.getInstance().getImageResource(getSizeRelationY());
          //#else
            //# Image image = ResourceManager.getInstance().getImageResource(getSizeRelationY());
            //#endif
            if(image != null)
            {
                setHeight(Util.getImageHeight(image));
            }
        }else if(sizeSettingY == MAX_CHILDRENS_SIZE )
        {
            if(parent != null)
            {
                int size = parent.getSize();
                int w = 0;
                for (int i = 0; i < size; i++) {
                   int w1 = ((Control)parent.getChild(i)).getHeight();
                   if(w1 > w)
                   {
                       w = w1;
                   }
                }
                setHeight(w);
            }
        }
//        if(getSizeSetting() == Control.WRAP_CONTENT && this instanceof Container && (!(getLayout() instanceof RelativeLayout) && !(getLayout() instanceof PropotionLayout) && getLayout() != null))
//        {
//            setWidth(getLayout().getPreferedWidth((Container)this));
//            setHeight(getLayout().getPreferedHeight((Container)this));
//        }
        //#ifdef ANDROID
        Bitmap image = getBgImage();
        //#else
        //# Image image = getBgImage();
        //#endif
        if(image == null)
        {
            image = getSelectionBgImage();
        }
        if(adjustDimentionsFromBgImage && image != null)
        {
            setWidth(Util.getImageWidth(image) + getLeftInBound() + getRightInBound());
            setHeight(Util.getImageHeight(image) + getTopInBound() + getBottomInBound());
        }
    }
    public void setLayout(Layout layout) {
        this.layout = layout;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBgType() {
        return bgType;
    }

    public void setStretchSelBg(boolean stretchSelBg) {
        this.stretchSelBg = stretchSelBg;
    }

    public boolean isStretchSelBg() {
        return stretchSelBg;
    }

    public int getSizeSettingX() {
        return sizeSettingX;
    }

    public void setSizeSettingX(int sizeSetting) {
        this.sizeSettingX = sizeSetting;
    }

     public int getSizeSettingY() {
        return sizeSettingY;
    }

    public void setSizeSettingY(int sizeSetting) {
        this.sizeSettingY = sizeSetting;
    }
    public boolean isStretchBg() {
        return stretchBg;
    }

    public void setStretchBg(boolean stretchBg) {
        this.stretchBg = stretchBg;
    }

    public void setPortHeight(boolean portHeight) {
        this.portHeight = portHeight;
    }

    public void setPortWidth(boolean portWidth) {
        this.portWidth = portWidth;
    }

    public boolean isPortHeight() {
        return portHeight;
    }

    public boolean isPortWidth() {
        return portWidth;
    }
   
    public int getRotation() {
        return rotation;
    }

    public void setAdditionalWidth(int additionalWidth) {
        this.additionalWidth = additionalWidth;
    }

    public void setAdditionalHeight(int additionalHeight) {
        this.additionalHeight = additionalHeight;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getId() {
        return id;
    }
    
    public EventManager getEventListener()
    {
        return eventManager;
    }
    public void setEventManager(EventManager manager)
    {
        eventManager = manager;
    }
    public void setParent(Container parent) 
    {
        this.parent = parent;
    }

    public Container getParent() 
    {
        return parent;
    }
   
  public void setVisible(boolean visible) {
      this.visible = visible;
  }
  public boolean isVisible() {
        return visible;
  }

   public void setCornerPngBg(CornersPNGBox bg)
   {
       this.cornerPngBg = bg;
   }
   public CornersPNGBox getCornerPngBg()
   {
       return  cornerPngBg;
   }
    public boolean isSelected() {
        return selected;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    //#ifdef ANDROID
    public abstract void paint(Canvas g, Paint paintObject);
    //#else
    //# public abstract void paint(Graphics g);
    //#endif
    
  
    public void setPoistion(int x,int y)
    {
        setX(x);setY(y);
    }

    public void setBgType(int bgType) {
        this.bgType = bgType;
    }
    public boolean keyRepeated(int key,int gameKey)
    {
        return keyPressed(key,gameKey);
    }
    float controlScale = 0;

    public void setControlScale(float controlScale) {
        this.controlScale = controlScale;
    }

    public float getControlScale() {
        return controlScale;
    }
    boolean isgreyScale=false;
    boolean isTintToCornerPng=false;
    Paint cornerPngPaintObject;
	private ColorMatrixColorFilter colorfilter=null;
	private boolean blink=false;
	private int mod=12;
	private int blinkCounter=0;
	public void setBlink(boolean blink) {
		this.blink = blink;
	}
    public void setGrayScale(boolean b) {
		// TODO Auto-generated method stub
    	isgreyScale=b;
	}
    public void setTintToCornerPng(boolean isTintToBg,Paint paint) {
		this.isTintToCornerPng = isTintToBg;
		if(isTintToBg)
			cornerPngPaintObject=paint;
	}
    private ColorMatrixColorFilter getGrayColorFilter() {
    	if(colorfilter==null)
    	{
    		ColorMatrix matrix ;
    		matrix = new ColorMatrix();
    		matrix.setSaturation(0);
    		adjustBrightness(matrix,0);
    		colorfilter = new ColorMatrixColorFilter(matrix);
    	}
		return colorfilter;
		
	}
    protected static float cleanValue(float p_val, float p_limit)
    {
        return Math.min(p_limit, Math.max(-p_limit, p_val));
    }
    public static void adjustBrightness(ColorMatrix cm, float value) {
        value = cleanValue(value,100);
        if (value == 0) {
            return;
        }

        float[] mat = new float[]
        { 
            1,0,0,0,value,
            0,1,0,0,value,
            0,0,1,0,value,
            0,0,0,1,0,
            0,0,0,0,1
        };
       
        cm.postConcat(new ColorMatrix(mat));
    }
     //#ifdef ANDROID
     public final void paintUI(Canvas g, Paint paintObject)
    //#else
    //# public final void paintUI(Graphics g)
    //#endif
    {
        
    	int id = getId();
    	 ColorFilter filter=paintObject.getColorFilter();
         int color=paintObject.getColor();
         int alpha=paintObject.getAlpha();
         if(isgreyScale)
        	 paintObject.setColorFilter(getGrayColorFilter());
    	
        if(!isVisible())
            return;
        if(Settings.enableAnimations)
        {
            if(startAnimation != null)
            {
                //#ifdef ANDROID
                 startAnimation.doAnimation(g,paintObject);
                //#else
                //# Object obj = startAnimation.doAnimation(g);
                //# if(obj != null)
                //# {
                   //# g = (Graphics)obj;
                //# }
                    //# 
                //#endif
               
            }if(endAnimation != null && EventQueue.getInstance().isEnding())
            {
                if(EventQueue.getInstance().isIsOnSelfEndAnim())
                {
                    if(getId() == EventQueue.getInstance().getHotControl().getId())
                    {
                         //#ifdef ANDROID
                         endAnimation.doAnimation(g,paintObject);
                        //#else
                         //# Object obj = endAnimation.doAnimation(g);
                         //# if(obj != null)
                            //# g = (Graphics)obj;
                        //#endif
                    }
                      
                }else{
                    //#ifdef ANDROID
                     endAnimation.doAnimation(g,paintObject);
                    //#else
                     //# Object obj = endAnimation.doAnimation(g);
                     //# if(obj != null)
                        //# g = (Graphics)obj;
                    //#endif
                }
                 
            }if(steadyAnimation != null && !EventQueue.getInstance().isEnding())
            {
                if(startAnimation == null || startAnimation.isAnimationOver())
                //#ifdef ANDROID
                 steadyAnimation.doAnimation(g,paintObject);
                //#else
                //# {
                     //# Object obj = steadyAnimation.doAnimation(g);
                     //# if(obj != null)
                        //# g = (Graphics)obj;
                //# }
                //#endif
            }
        }
       
        if(getParent() == null)
        {
            Settings.TRANSLATED_Y = 0;
            Settings.TRANSLATED_X = 0;
        }
        int transX = x;
        int transY = y;
      
        if(EventQueue.getInstance().isProcsseing() && EventQueue.getInstance().getHotControl().getId() == getId())
        {
            transX += getSelectionAdditionalX();
            transY += getSelectionAdditionalY();
//            scaleXValue = getScaleX();
//            scaleYValue = getScaleY();
//            
//            if(getZoomPersentOnSelection() > 0)
//            {
//                ((Graphics2D)g).scale(getScaleX(), getScaleY());
//                transX -= ((getScaledW() - getWidth())  >> 1);
//                transY -= ((getScaledH() - getHeight())  >> 1);
//            }
             
        }
      
        g.translate(transX, transY);
     
        Settings.TRANSLATED_Y += y;
        Settings.TRANSLATED_X += x;
       
        if(controlScale != 0)
        {
            //#ifdef ANDROID
             g.scale(getControlScale(), getControlScale(), getWidth() >> 1, getHeight() >> 1);
             //#else
            //# ((Graphics2D)g).translate(((float)getWidth() - ((float)getWidth()*getControlScale())) / 2, ((float)getWidth() - ((float)getHeight()*getControlScale())) / 2);
            //# ((Graphics2D)g).scale(getControlScale(), getControlScale());
            //#endif
            
        }
        
        
        //#ifdef DEKSTOP_TOOL
        //# int clipX, clipY , clipWidth,clipHeight;
        //# Rectangle r = g.getClipBounds();
            //# clipX = (int)r.getX();
            //# clipY = (int)r.getY();
            //# clipWidth = (int)r.getWidth();
            //# clipHeight = (int)r.getHeight();
        //#elifdef J2ME
        //# int clipX, clipY , clipWidth,clipHeight;
        //# clipX = g.getClipX();
        //# clipY = g.getClipY();
        //# clipWidth = g.getClipWidth();
        //# clipHeight = g.getClipHeight();
        //#endif

        //#ifdef ANDROID
        if(clipData)  
        	g.save(Canvas.CLIP_SAVE_FLAG);

        //#endif
        
        if(clipData)  
        {
            g.clipRect(0, 0,getWidth() , getHeight());
        }
        
        if(getWidth() > 0 && getHeight() > 0)
        {
             //#ifdef DEKSTOP_TOOL
            //# AffineTransform saveTransform = null;
            //# if(getRotation() != 0)
            //# {
                //# saveTransform = ((Graphics2D)g).getTransform();
                //# AffineTransform a = new AffineTransform();
                //# 
                //# a.setToRotation(Math.toRadians(getRotation()), (getWidth() >> 1) + saveTransform.getTranslateX(), (getHeight() >> 1)+saveTransform.getTranslateY());
                //# a.translate(saveTransform.getTranslateX(), saveTransform.getTranslateY());
                //# ((Graphics2D)g).setTransform(a);
            //# }
           //# 
            //#endif
        
            //#ifdef ANDROID
        	if(getRotation() != 0)
        	{
        		g.save();
        		g.rotate(getRotation(),(getWidth() >> 1) , (getHeight() >> 1));
        	}
            //#endif   
                
            
            //#ifdef ANDROID    
                paintBackground(g,paintObject);
                paintObject.setAlpha(alpha);
        	    paintObject.setColorFilter(filter);
        	    paintObject.setColor(color);
            //#else
                //# paintBackground(g);
            //#endif
            
            g.translate(getLeftInBound(), getTopInBound());
            if(clipData)
            {
                g.clipRect(0, 0, getBoundWidth(), getBoundHeight());
            }
            if(isgreyScale)
           	 	paintObject.setColorFilter(getGrayColorFilter());
             //#ifdef ANDROID    
                 paint(g,paintObject);
            //#else
                 //# paint(g);
            //#endif
                
          
            g.translate(-getLeftInBound(), -getTopInBound());
            if(clipData)
            {
            	
            	 //#ifdef ANDROID
            	g.restore();
            	g.save(Canvas.CLIP_SAVE_FLAG);
            	g.clipRect(0, 0,getWidth() , getHeight());
            	//#else
            	//# g.setClip(clipX, clipY, clipWidth, clipHeight);
            	//#endif
                
               
            }
             //#ifdef ANDROID    
                paintForground(g,paintObject);
            //#else
                 //# paintForground(g);
            //#endif
           
             //#ifdef DEKSTOP_TOOL
            //# if(saveTransform != null)
                //# ((Graphics2D)g).setTransform(saveTransform);
            //#endif
            
                 
             //#ifdef ANDROID       
            if(getRotation() != 0)
            {
        	g.restore();
            }
            //#endif
        }
        if(clipData)
        {
        	 //#ifdef ANDROID
//        	g.clipRect(bounds);
        	g.restore();
        	//#else
        	//# g.setClip(clipX, clipY, clipWidth, clipHeight);
        	//#endif
        }
        g.translate(-transX, -transY);
//        if(getZoomPersentOnSelection() > 0)
//        {
//            ((Graphics2D)g).scale(1,1);
//        }
        Settings.TRANSLATED_Y -= y;
        Settings.TRANSLATED_X -= x;

        paintObject.setAlpha(alpha);
        paintObject.setColorFilter(filter);
        paintObject.setColor(color);
        
        if(Settings.enableAnimations)
        {
            if(startAnimation != null)
            {
                //#ifdef ANDROID
                 startAnimation.restoreAnimationEffect(g,paintObject);
                //#else
                //# startAnimation.restoreAnimationEffect();
                //#endif
               
            }if(endAnimation != null && EventQueue.getInstance().isEnding())
            {
                if(EventQueue.getInstance().isIsOnSelfEndAnim())
                {
                    if(getId() == EventQueue.getInstance().getHotControl().getId())
                        
                    //#ifdef ANDROID
                     endAnimation.restoreAnimationEffect(g,paintObject);
                    //#else
                    //# endAnimation.restoreAnimationEffect();
                    //#endif
                     
                }else{
                    
                     //#ifdef ANDROID
                     endAnimation.restoreAnimationEffect(g,paintObject);
                    //#else
                    //# endAnimation.restoreAnimationEffect();
                    //#endif

                }
                 
            }if(steadyAnimation != null && !EventQueue.getInstance().isEnding())
            {
                if(startAnimation == null || startAnimation.isAnimationOver())
                    //#ifdef ANDROID
                     steadyAnimation.restoreAnimationEffect(g,paintObject);
                    //#else
                    //# steadyAnimation.restoreAnimationEffect();
                    //#endif
            }
        }
    }
    
    public int getBorderThickness()
    {
        return this.borderThickness;
    }
     //#ifdef ANDROID
  protected void paintForground(Canvas g, Paint paintObject)
    //#else
     //# protected void paintForground(Graphics g)
    //#endif
  
    {
        if(selected && selectionBorderColor   != -1)
        {
                 //#ifdef ANDROID
        	paintObject.setColor(Util.getColor(selectionBorderColor));
                //#else
                //# g.setColor(Util.getColor(selectionBorderColor));
                //#endif
	            for (int i = 0; i < borderThickness; i++) {
	                if(bgType == ROUND_RECT_TYPE)
	                {
                             //#ifdef ANDROID
	                	GraphicsUtil.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                             //#else
                                //# g.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1), ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
                             //#endif
	               
	                }else{
                             //#ifdef ANDROID
	                	GraphicsUtil.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                             //#else
                                //# g.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1));
                             //#endif
	                	
	                }
                }
            
        }
        else if(borderColor != -1)
        {
              //#ifdef ANDROID
        	 paintObject.setColor(Util.getColor(borderColor));
                //#else
                 //# g.setColor(Util.getColor(borderColor));
                //#endif
           if(blink){ 
        	   if(blinkCounter%mod==0||blinkCounter%mod==1||blinkCounter%mod==2||blinkCounter%mod==3||blinkCounter%mod==4||blinkCounter%mod==5){
        			paintborder(g,paintObject);
        	   }
        	   blinkCounter++;
               if(blinkCounter==mod)
            	   blinkCounter=0;
        	}else{
        		paintborder(g,paintObject);
        	}
            
        }
        if(selected && selectionBorderImage != null)
        {
        	Util.drawImage(g,getSelectionBorderImage(),(getWidth() - Util.getImageWidth(getSelectionBorderImage())) >> 1, (getHeight() - Util.getImageHeight(getSelectionBorderImage()) >> 1),paintObject);
        }
    }
     
     private void paintborder(Canvas g, Paint paintObject) {
    	 for (int i = 0; i < borderThickness; i++) {
             if(bgType == ROUND_RECT_TYPE)
             {
                  //#ifdef ANDROID
                     GraphicsUtil.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                   //#else
                      //# g.drawRoundRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1), ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
                   //#endif
             	
             }else{

                  //#ifdef ANDROID
	              	GraphicsUtil.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1),g ,paintObject);
                  //#else
                    //# g.drawRect(i, i, getWidth() - 1 - (i << 1), getHeight() - 1 - (i << 1));
                   //#endif
                 
             }
         }
}
	//#ifdef ANDROID
    protected final void paintBackground(Canvas g, Paint paintObject)
    //#else
    //# protected final void paintBackground(Graphics g)
    //#endif
  
    {
        
        if(selected && selectionBgColor  != -1)
        {
              //#ifdef ANDROID
                paintObject.setColor(Util.getColor(selectionBgColor));
              //#else
                  //# g.setColor(Util.getColor(selectionBgColor ));
              //#endif
           
            if(bgType == ROUND_RECT_TYPE)
            {
              //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, g,paintObject);
              //#else
                    //# g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
              //#endif
                  
            	
            }
            else
            {
              //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRect(0, 0, getWidth() - 1 , getHeight() - 1,g,paintObject);
              //#else
                //# g.fillRect(0, 0, getWidth() - 1 , getHeight() - 1);
              //#endif
            
            }
        }
        else if(bgColor  != -1)
        {
             //#ifdef ANDROID
                paintObject.setColor(Util.getColor(bgColor));
              //#else
                  //# g.setColor(Util.getColor(bgColor ));
              //#endif
            
            if(bgType == ROUND_RECT_TYPE){
            
                //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, g,paintObject);
                //#else
                //# g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_RADIOUS_WIDTH, ARC_RADIOUS_HEIGHT);
                //#endif
            	
            }
            else
            {
                 //#ifdef ANDROID
                com.appon.gtantra.GraphicsUtil.fillRect(0, 0, getWidth() - 1 , getHeight() - 1,                 g,paintObject);
                //#else
                 //# g.fillRect(0, 0, getWidth() - 1 , getHeight() - 1);
                //#endif
            	
            }
        }
        if(selectionBgImage != null && isSelected())
        {
            if(isStretchSelBg())
            {
                 //#ifdef ANDROID
                 stretchDraw(g, selectionBgImage,paintObject);
              //#else
                   //# stretchDraw(g, selectionBgImage);
              //#endif
               
            }else{
                Util.drawImage(g, selectionBgImage,  (getWidth() - Util.getImageWidth(selectionBgImage)) >> 1, (getHeight() - Util.getImageHeight(selectionBgImage)) >> 1,paintObject);
            }
        }
        else if(bgImage != null)
        {
        	
            if(isStretchBg())
            {
                //#ifdef ANDROID
                 stretchDraw(g, bgImage,paintObject);
              //#else
                   //# stretchDraw(g, bgImage);
              //#endif
              
            }else{
                Util.drawImage(g, bgImage,  (getWidth() - Util.getImageWidth(bgImage)) >> 1, (getHeight() - Util.getImageHeight(bgImage)) >> 1,paintObject);

            }
           
        }
        if(cornerPngBg != null)
        {
        	Paint temp=paintObject;
        	if(isTintToCornerPng)
        		paintObject=cornerPngPaintObject;
              //#ifdef ANDROID
                  cornerPngBg.paint(g, 0, 0, getWidth(), getHeight(),paintObject);
              //#else
                    //# cornerPngBg.paint(g, 0, 0, getWidth(), getHeight());
              //#endif
                  paintObject=temp;  
        }
         if(ninePatchCornerPngBox != null)
        {
        	 Paint temp=paintObject;
         	if(isTintToCornerPng)
         		paintObject=cornerPngPaintObject;
              //#ifdef ANDROID
                 ninePatchCornerPngBox.paint(g, 0, 0, getWidth(), getHeight(),paintObject);
              //#else
                   //# ninePatchCornerPngBox.paint(g, 0, 0, getWidth(), getHeight());
              //#endif
                 paintObject=temp;  
           
        }
    }
      //#ifdef ANDROID
     private void stretchDraw(Canvas g,Bitmap image, Paint paintObject)
     //#else
     //# private void stretchDraw(Graphics g,Image image)
     //#endif
   
    {
        if(getWidth() > 0 && getHeight() > 0)
        {
        	 //#ifdef DEKSTOP_TOOL
        	 //# g.drawImage(image, 0 , 0, getWidth(), getHeight(), null);
                //#elifdef J2ME
                //# Util.drawImage(g, image, (getWidth() - Util.getImageWidth(image)) >> 1 , (getHeight() - Util.getImageHeight(image)) >> 1);
        	 //#elifdef ANDROID
        	Matrix matrix = new Matrix();
        	float sx = (float)getWidth() / (float)image.getWidth();
        	float sy = (float)getHeight() / (float)image.getHeight();
        	matrix.setScale(sx, sy);
        	g.drawBitmap(image, matrix, paintObject);
                //#endif
        	
            
        }
       
    }

    public int getHeight() {
        return height + getAdditionalHeight();
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width + getAdditionalWidth();
    }
    public int getBoundWidth() {
        return getWidth() - getLeftInBound() - getRightInBound();
    }
      public int getBoundHeight() {
        return getHeight() - getTopInBound() - getBottomInBound();
    }
    public void setSelected(boolean selected)
    {
        setSelected(selected,false);
    }
    public void setSelected(boolean selected,boolean isFromTouch)
    {
        this.selected = selected;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void showNotify()
    {
        selected = false;
        
        if(layout != null)
                layout.applyLayout(getParent(),this);
        if(relativeLayout != null)
                relativeLayout.applyLayout(getParent(),this);
        
        setPosition(getX() + ((getWidth() * getAddShifPerFromSizeX()) / 100), getY() + ((getHeight() * getAddShifPerFromSizeY()) / 100));
        
    }
    public void hideNotify()
    {
        selected = false;
        boolean restored = false;
        if(startAnimation != null)
        {
            restored =  startAnimation.restore();
        }if(endAnimation != null && !restored)
        {
            restored = endAnimation.restore();
        }if(steadyAnimation != null && !restored)
        {
            steadyAnimation.restore();
        }
    }
    public void cutomKeyEventFired()
    {
        if(getEventListener() != null && customKeyEventCode != Integer.MAX_VALUE)
        {
            getEventListener().event(new Event(customKeyEventCode, this, null));
        }
    }
    public boolean keyPressed(int keycode,int gameKey)
    {
        if(getEventListener() != null && (keycode == Settings.KEY_SOFT_CENTER || gameKey == Settings.FIRE ))
        {
            if(isClickable() )
            {
                EventQueue.getInstance().addEvent(new Event(EventManager.CONTROL_CLICKED, this,null));
            }   
            else{
                getEventListener().event(new Event(EventManager.FIRE_PRESSED, this, null));
            }
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public boolean keyReleased(int keycode,int gameKey)
    {
        return false;
    }

    public int getBgColor() {
        return bgColor ;
    }

    public void setBgColor(int color) {
       this.bgColor = color;
        if(getBorderColor() == -1)
        {
           this.borderColor = color;
        }
    }

    public int getBorderColor() {
        return borderColor ;
    }

    public void setBorderColor(int color) {
        this.borderColor = color;
    }

    public int getSelectionBgColor() {
        return selectionBgColor ;
    }

    public void setSelectionBgColor(int color) {
        this.selectionBgColor = color;
        if(getSelectionBorderColor() == -1)
        {
            setSelectionBorderColor(color);
        }
    }

   
    public int getSelectionBorderColor() {
        return selectionBorderColor  ;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    public void setSelectionBorderColor(int color) {
        this.selectionBorderColor  = color;
      
    }

 
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getAdditionalHeight() {
        return additionalHeight;
    }

    public int getAdditionalWidth() {
        return additionalWidth;
    }

   
    public String toString() {
       return "Control- ID: "+getId();
    }

   
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(getId() == -1 || ((Control)obj).getId() == -1)
        {
            return super.equals(obj);
        }
        Control newControl = (Control)obj;
        return newControl.getId() == getId();
    }
      public boolean pointerDragged(int x, int y)
      {
          return false;
      }
      public boolean pointerPressed(int x, int y)
      {
         if(this instanceof CustomControl)
        	 ((CustomControl)this).onpointerPressed(x, y);
          return false;
      }
      public boolean pointerReleased(int x, int y){
          if(getEventListener() != null /*&& isSelected()*/ && !ScrollableContainer.fromPointerDrag)
          {
              if(isClickable())
              {
                  EventQueue.getInstance().addEvent(new Event(EventManager.CONTROL_CLICKED, this,null));
              }else{
                  getEventListener().event(new Event(EventManager.FIRE_PRESSED, this, null));
              }
          }
          
          if(this instanceof CustomControl && !ScrollableContainer.fromPointerDrag)
         	 ((CustomControl)this).onpointerRealease(x, y);
          
          return false;
      }
      public void restoreOriogionalXY()
      {
          setX(getOrigionalX());
          setY(getOrigionalY());
      }

    public int getCornerPNGBgResId() {
        return cornerPNGBgResId;
    }

    public void setCornerPNGBgResId(int cornerPNGBgResId) {
        this.cornerPNGBgResId = cornerPNGBgResId;
    }

   
    public byte[] serialize() throws Exception {
          //#ifdef DEKSTOP_TOOL
        //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //# Util.writeInt(bos, getId(),2);
        //# if(this instanceof CustomControl)
        //# {
            //# Util.writeInt(bos,((CustomControl)this).getIdentifier(),2);
        //# }
        //# Util.writeInt(bos, getBgType(),1);
        //# Util.writeBoolean(bos, isSelectable());
        //# Util.writeBoolean(bos, isVisible());
        //# Util.writeColor(bos, getBorderColor());
        //# Util.writeColor(bos, getSelectionBorderColor());
        //# Util.writeSignedInt(bos, getSelectionBorderImageId(),2);
        //# Util.writeInt(bos, getBorderThickness(),1);
        //# Util.writeColor(bos, getBgColor());
        //# Util.writeColor(bos, getSelectionBgColor());
        //# Util.writeSignedInt(bos, getBgImageResourceId(),2);
        //# Util.writeBoolean(bos, isStretchBg());
        //# Util.writeSignedInt(bos, getSelectionBgImageResourceId(),2);
        //# Util.writeBoolean(bos, isStretchSelBg());
        //# Util.writeBoolean(bos, isAdjustDimentionsFromBgImage());
        //# Util.writeSignedInt(bos,getRotation(),2);
        //# Util.writeSignedInt(bos,getAdditionalWidth(),2);
        //# Util.writeSignedInt(bos,getAdditionalHeight(),2);
        //# Util.writeSignedInt(bos,getOrigionalX(),2);
        //# Util.writeSignedInt(bos,getOrigionalY(),2);
        //# Util.writeInt(bos,getSizeSettingX(),1);
        //# Util.writeInt(bos,getSizeSettingY(),1);
        //# if(getSizeSettingX() >= CONTROL_RELATIVE_SIZE)
        //# {
            //# Util.writeSignedInt(bos,getSizeRelationX(),2);
        //# }
        //# if(getSizeSettingY() >= CONTROL_RELATIVE_SIZE)
        //# {
            //# Util.writeSignedInt(bos,getSizeRelationY(),2);
        //# }   
        //# Util.writeSignedInt(bos,width,2);
        //# Util.writeSignedInt(bos,height,2);
        //# Util.writeBoolean(bos, isPortWidth());
        //# Util.writeBoolean(bos, isPortHeight());
        //# 
        //# Util.writeBoolean(bos, isClickable());
        //# Util.writeBoolean(bos, isClipData());
        //# Util.writeSignedInt(bos,getWidthWeight(),2);
        //# Util.writeSignedInt(bos,getHeightWeight(),2);
        //# 
        //# Util.writeString(bos, getUserData());
        //# Util.writeInt(bos, getDelayInSelection(),1);
        //# Util.writeBoolean(bos, isEndAnimOnSelf());
        //# Util.writeSignedInt(bos, getZoomPersentOnSelection(),2);
        //# Util.writeSignedInt(bos, getSelectionAdditionalX(),2);
        //# Util.writeSignedInt(bos, getSelectionAdditionalY(),2);
        //# 
        //# Util.writeSignedInt(bos, getLeftChild(),2);
        //# Util.writeSignedInt(bos, getRightChild(),2);
        //# Util.writeSignedInt(bos, getDownChild(),2);
        //# Util.writeSignedInt(bos, getUpChild(),2);
        //# 
        //# Util.writeInt(bos, getLeftInBound(),2);
        //# Util.writeInt(bos, getRightInBound(),2);
        //# Util.writeInt(bos, getTopInBound(),2);
        //# Util.writeInt(bos, getBottomInBound(),2);
        //# Util.writeBoolean(bos, isPortBounds());
        //# Util.writeBoolean(bos, isSkipParentWrapSizeCalc());
        //# Util.writeSignedInt(bos,getAddShifPerFromSizeX() , 1);
        //# Util.writeSignedInt(bos,getAddShifPerFromSizeY() , 1);
        //# Util.writeSignedInt(bos,getCornerPNGBgResId() , 1);
        //# Util.writeInt(bos, getLayout() == null ? 0 : 1,1);
        //# if(getLayout() != null)
        //# {
            //# MenuSerilize.serialize(getLayout(), bos);
        //# }
        //# 
        //# Util.writeInt(bos, getRelativeLocation() == null ? 0 : 1,1);
        //# if(getRelativeLocation() != null)
        //# {
            //# MenuSerilize.serialize(getRelativeLocation(), bos);
        //# }
        //# 
        //# Util.writeInt(bos,getStartAnimation() == null ? 0 : 1,1);
        //# if(getStartAnimation() != null)
        //# {
            //# MenuSerilize.serialize(getStartAnimation(), bos);
        //# }
        //# 
        //# Util.writeInt(bos,getEndAnimation() == null ? 0 : 1,1);
        //# if(getEndAnimation() != null)
        //# {
            //# MenuSerilize.serialize(getEndAnimation(), bos);
        //# }
        //# 
        //# Util.writeInt(bos,getSteadyAnimation() == null ? 0 : 1,1);
        //# if(getSteadyAnimation() != null)
        //# {
            //# MenuSerilize.serialize(getSteadyAnimation(), bos);
        //# }
        //# bos.flush();
        //# byte data[] = bos.toByteArray();
        //# bos.close();
        //# bos = null;
        //# return data;
        //#else
	return null;
        //#endif
    }
    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
    
//        setId(Util.readInt(bis,2));
//        if(this instanceof CustomControl)
//        {
//            ((CustomControl)this).setIdentifier(Util.readInt(bis,2));
//        }
        setBgType(Util.readInt(bis,1));
        setSelectable(Util.readBoolean(bis));
        setVisible(Util.readBoolean(bis));
        setBorderColor(Util.readColor(bis));
        setSelectionBorderColor(Util.readColor(bis));
        setSelectionBorderImageId(Util.readSignedInt(bis,2));
        setSelectionBorderImage(ResourceManager.getInstance().getImageResource(getSelectionBorderImageId()));
        setBorderThickness(Util.readInt(bis,1));
        setBgColor(Util.readColor(bis));
        setSelectionBgColor(Util.readColor(bis));
        setBgImageResourceId(Util.readSignedInt(bis,2));
        setBgImage(ResourceManager.getInstance().getImageResource(getBgImageResourceId()));
        setStretchBg(Util.readBoolean(bis));
        setSelectionBgImageResourceId(Util.readSignedInt(bis,2));
        if(ResourceManager.getInstance().getImageResource(getSelectionBgImageResourceId()) != null)
            setSelectionBgImage(ResourceManager.getInstance().getImageResource(getSelectionBgImageResourceId()));
        setStretchSelBg(Util.readBoolean(bis));
        setAdjustDimentionsFromBgImage(Util.readBoolean(bis));
        setRotation(Util.readSignedInt(bis,2));
        setAdditionalWidth(Util.readSignedInt(bis,2));
        setAdditionalHeight(Util.readSignedInt(bis,2));
        setOrigionalX(Util.readSignedInt(bis,2));
        setOrigionalY(Util.readSignedInt(bis,2));
      
        
        if(Settings.VERSION_NUMBER_FOUND >= 2)
        {
             setSizeSettingX(Util.readInt(bis,1));
             setSizeSettingY(Util.readInt(bis,1));
             if(getSizeSettingX() >= CONTROL_RELATIVE_SIZE)
            {
                setSizeRelationX(Util.readSignedInt(bis,2));
            }
            if(getSizeSettingY() >= CONTROL_RELATIVE_SIZE)
            {
                setSizeRelationY(Util.readSignedInt(bis,2));
            }
        }else{
            setSizeSettingX(Util.readInt(bis,1));
            setSizeSettingY(getSizeSettingX());
        }
       
      
        width =  Util.readSignedInt(bis,2);
        height = Util.readSignedInt(bis,2);
        setPortWidth(Util.readBoolean(bis));
        setPortHeight(Util.readBoolean(bis));
        setClickable(Util.readBoolean(bis));
        setClipData(Util.readBoolean(bis));
        setWidthWeight( Util.readSignedInt(bis,2));
        setHeightWeight( Util.readSignedInt(bis,2));
        setUserData(Util.readString(bis));
        setDelayInSelection(Util.readInt(bis,1));
        setEndAnimOnSelf(Util.readBoolean(bis));
        setZoomPersentOnSelection(Util.readSignedInt(bis,2));
        setSelectionAdditionalX(Util.readSignedInt(bis,2));
        setSelectionAdditionalY(Util.readSignedInt(bis,2));
        setLeftChild(Util.readSignedInt(bis,2));
        setRightChild(Util.readSignedInt(bis,2));
        setDownChild(Util.readSignedInt(bis,2));
        setUpChild(Util.readSignedInt(bis,2));
        
        setLeftInBound(Util.readInt(bis, 2));
        setRightInBound(Util.readInt(bis, 2));
        setTopInBound(Util.readInt(bis, 2));
        setBottomInBound(Util.readInt(bis, 2));
        setPortBounds(Util.readBoolean(bis));
        if(Settings.VERSION_NUMBER_FOUND >= 2)
        {
            setSkipParentWrapSizeCalc(Util.readBoolean(bis));
            setAddShifPerFromSizeX(Util.readSignedInt(bis,1));
            setAddShifPerFromSizeY(Util.readSignedInt(bis,1));
        }
        setCornerPNGBgResId(Util.readSignedInt(bis, 1));
        if(getCornerPNGBgResId() != -1)
        {
            setNinePatchCornerPngBox(ResourceManager.getInstance().getCornerPNGBox(getCornerPNGBgResId()));
        }
        int value = Util.readInt(bis,1);
        if(value == 1)
        {
            setLayout((Layout)MenuSerilize.deserialize(bis, MenuSerilize.getInstance()));
        }
        
        value = Util.readInt(bis,1);
        if(value == 1)
        {
            setRelativeLocation((RelativeLayout)MenuSerilize.deserialize(bis, MenuSerilize.getInstance()));
        }
        
        value = Util.readInt(bis,1);
        if(value == 1)
        {
            setStartAnimation((StartAnimation)MenuSerilize.deserialize(bis, MenuSerilize.getInstance()));
        }
        value = Util.readInt(bis,1);
        if(value == 1)
        {
            setEndAnimation((EndAnimation)MenuSerilize.deserialize(bis, MenuSerilize.getInstance()));
        }
        value = Util.readInt(bis,1);
        if(value == 1)
        {
            setSteadyAnimation((SteadyAnimation)MenuSerilize.deserialize(bis, MenuSerilize.getInstance()));
        }
        return bis;
    }
    public void port()
    {
        int scaleX = Util.getScaleX();
        int scaleY = Util.getScaleY();
        if(isPortWidth())
        {
            width = Util.getScaleValue(width, scaleX);
        }
        if(isPortHeight())
        {
            height = Util.getScaleValue(height, scaleY);
        }
        if(isPortBounds())
        {
            setLeftInBound(Util.getScaleValue(getLeftInBound(), scaleX));
            setRightInBound(Util.getScaleValue(getRightInBound(), scaleX));
            setTopInBound(Util.getScaleValue(getTopInBound(), scaleY));
            setBottomInBound(Util.getScaleValue(getBottomInBound(), scaleY));
        }
    }
    public void cleanup()
    {
        selectionBorderImage = null;
        layout = null;
        steadyAnimation = null;
        startAnimation = null;
        endAnimation = null;
        relativeLayout = null;
    }
    //#ifdef DEKSTOP_TOOL
    //# public void copyFromControl(CommanProperties comman)
    //# {
        //# comman.width = width;
        //# comman.height = height;
        //# comman.addShiftX = addShifPerFromSizeX;
        //# comman.addShiftY= addShifPerFromSizeY;
        //# comman.visible = visible;
        //# comman.skipSize = skipParentWrapSizeCalc;
        //# comman.selectable = selectable;
        //# comman.bgType = bgType;
        //# comman.borderThickness = borderThickness;
        //# comman.rotation = rotation;
        //# comman.sizeSettingX = sizeSettingX;
        //# comman.sizeSettingY = sizeSettingY;
        //# comman.sizeRelationX = sizeRelationX;
        //# comman.sizeRelationY = sizeRelationY;
        //# comman.additionalWidth = additionalWidth;
        //# comman.additionalHeight = additionalHeight;
        //# comman.portWidth = portWidth;
        //# comman.portHeight = portHeight;
        //# comman.stretchBg = stretchBg;
        //# comman.stretchSelBg = stretchSelBg;
        //# comman.clickable = clickable;
        //# comman.clipData = clipData;
        //# comman.origionalX = origionalX;
        //# comman.origionalY = origionalY;
        //# comman.widthWeight = widthWeight;
        //# comman.heightWeight = heightWeight;
        //# comman.adjustDimentionsFromBgImage = adjustDimentionsFromBgImage;
        //# comman.endAnimOnSelf = endAnimOnSelf;
        //# comman.selectionAdditionalX = selectionAdditionalX;
        //# comman.selectionAdditionalY = selectionAdditionalY;
        //# comman.zoomPersentOnSelection = zoomPersentOnSelection;
        //# comman.delayInSelection = delayInSelection;
        //# comman.bgImageResourceId = bgImageResourceId;
        //# comman.selectionBgImageResourceId = selectionBgImageResourceId;
        //# comman.selectionBorderImageId = selectionBorderImageId;
        //# comman.leftInBound = leftInBound;
        //# comman.rightInBound = rightInBound;
        //# comman.topInBound = topInBound;
        //# comman.bottomInBound = bottomInBound;
        //# comman.portBounds = portBounds;
        //# comman.cornerPNGBgResId = cornerPNGBgResId;
        //# comman.selectionBorderColor = selectionBorderColor;
        //# comman.borderColor = borderColor;
        //# comman.bgColor = bgColor;
        //# comman.selectionBgColor = selectionBgColor;
        //# comman.cornerPngBg = cornerPngBg;
//# 
    //# }
    //# public void copyToControl(CommanProperties comman)
    //# {
         //# width = comman.width ;
         //# 
         //# height =  comman.height;
         //# addShifPerFromSizeX= comman.addShiftX;
         //# addShifPerFromSizeY= comman.addShiftY;
         //# skipParentWrapSizeCalc = comman.skipSize;
         //# visible = comman.visible ;
         //# selectable=comman.selectable;
         //# bgType=comman.bgType;
         //# borderThickness=comman.borderThickness;
        //# rotation=comman.rotation; 
        //# sizeSettingX=comman.sizeSettingX; 
        //# sizeSettingY=comman.sizeSettingY; 
        //# sizeRelationX=comman.sizeRelationX; 
        //# sizeRelationY=comman.sizeRelationY; 
        //# additionalWidth= comman.additionalWidth;
       //# additionalHeight= comman.additionalHeight; 
       //# portWidth= comman.portWidth; 
         //# portHeight=comman.portHeight;
        //# stretchBg= comman.stretchBg;
        //# stretchSelBg=comman.stretchSelBg; 
       //# clickable= comman.clickable; 
       //# clipData= comman.clipData; 
       //# origionalX= comman.origionalX; 
         //# origionalY=comman.origionalY;
       //# widthWeight= comman.widthWeight; 
        //# heightWeight=comman.heightWeight; 
        //# adjustDimentionsFromBgImage= comman.adjustDimentionsFromBgImage;
       //# endAnimOnSelf= comman.endAnimOnSelf; 
        //# selectionAdditionalX= comman.selectionAdditionalX;
        //# selectionAdditionalY= comman.selectionAdditionalY;
       //# zoomPersentOnSelection= comman.zoomPersentOnSelection; 
        //# delayInSelection=comman.delayInSelection; 
        //# bgImageResourceId= comman.bgImageResourceId;
        //# selectionBgImageResourceId= comman.selectionBgImageResourceId;
       //# selectionBorderImageId= comman.selectionBorderImageId; 
        //# leftInBound= comman.leftInBound;
       //# rightInBound= comman.rightInBound; 
        //# topInBound= comman.topInBound;
       //# bottomInBound= comman.bottomInBound; 
       //# portBounds= comman.portBounds; 
         //# cornerPNGBgResId=comman.cornerPNGBgResId;
         //# selectionBorderColor=comman.selectionBorderColor;
         //# borderColor=comman.borderColor;
        //# bgColor=comman.bgColor; 
       //# selectionBgColor= comman.selectionBgColor; 
        //# cornerPngBg= comman.cornerPngBg;
//# 
    //# }
    //#endif

    public boolean isSkipParentWrapSizeCalc() {
        return skipParentWrapSizeCalc;
    }

    public void setSkipParentWrapSizeCalc(boolean skipParentWrapSizeCalc) {
        this.skipParentWrapSizeCalc = skipParentWrapSizeCalc;
    }
   
}
