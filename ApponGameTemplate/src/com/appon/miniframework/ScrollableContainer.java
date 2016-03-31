/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;

import java.io.ByteArrayInputStream;
import java.util.Hashtable;
import java.util.Vector;

//#ifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.miniframework.layout.PropotionLayout;
//#elifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.io.ByteArrayOutputStream;
//#endif




/**
 *
 * @author user
 */
public class ScrollableContainer extends Container{
    private int totalHeight , barWidth;
    private int scrolledY = 0;
    private int totalWidth , scrolledX;
    private Vector childrens = new Vector();
    private static boolean selectChild = true;
    private int selectedChild = 0;
    public static final int SCROLL_BAR_MIN_WIDTH = 2;
    private int upperMostElement = -1;
    private int leftMostElement = -1;
    private CustomSelector selector;
    private static int diffrenceY = 0, oldY = 0;
    private static int diffrenceX = 0, oldX = 0;
    public static final int SMOOTH_SCROLL = 0;
    public static final int SELECT_COMPONENT_SCROLL = 1;
    private int scrollBarColor = 0;
    private int scrollType = SELECT_COMPONENT_SCROLL;
    public static final int SCROLLBAR_ALWAYS_VISABLE = 0;
    public static final int SCROLLBAR_AUTO_HIDE = 1;
    public static final int  SCROLLBAR_DISABLED = 2;
    private int verticalScrollBarVisabilityType = SCROLLBAR_ALWAYS_VISABLE;
    private int widthPersent = 1;
    private int hideInterval = 5;
    private int visabilityTimer = 0;
    private int staticScrolledPos=0;
    
    private int defaultSelection = -1;
    private boolean groupSelect = false;    
    private static boolean unSelectChild = false;
    private static boolean isAnyChildSelected = false;
    private static int selectedChildSelectionDelay = 0;
    private int selectionDelayTimer = 0;
    public static boolean allStartAnimOver = false;
    private long velocityX,velocityY;
    private long lastTouchTime = -1;
    private int lastTouchPressedX,lastTouchPressedY;
    private static boolean didScrolled = false;
    private static final int FPS_CONSIDERED = 15;
    private static final int MAX_SCROOLED_STEPS = 10;
    private int FRICTION = 20;
    private int MINIMUM_VELOCITY = 60;
    public static final int MINIMUM_SHIFT_TO_DRAG = 12;
    private int scrolledBackupY,scrolledBackupX;
    private Hashtable softkeyHashTable;
    public ScrollableContainer(int id) {
        super(id);
    }

    public void setGroupSelect(boolean groupSelect) {
        this.groupSelect = groupSelect;
    }
    public void addSoftKeyEvent(int keyCode,Control control)
    {
        if(softkeyHashTable == null)
        {
            softkeyHashTable = new Hashtable();
        }
        softkeyHashTable.put(""+keyCode, control);
    }
    public boolean isGroupSelect() {
        return groupSelect;
    }

    public int getStaticScrolledPos() {
        return staticScrolledPos;
    }

    public int getDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(int defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    public void setStaticScrolledPos(int staticScrolledPos) {
        this.staticScrolledPos = staticScrolledPos;
    }

    public int getScrollType() {
        return scrollType;
    }

    public void setScrollBarColor(int scrollBarColor) {
        this.scrollBarColor = scrollBarColor;
    }

    public int getScrollBarColor() {
        return scrollBarColor;
    }

    public void setScrollBarVisabilityType(int scrollBarVisabilityType) {
        this.verticalScrollBarVisabilityType = scrollBarVisabilityType;
    }

    public int getScrollBarVisabilityType() {
        return verticalScrollBarVisabilityType;
    }

    public void setWidthPersent(int widthPersent) {
        this.widthPersent = widthPersent;
    }

    public int getWidthPersent() {
        return widthPersent;
    }

    public void setHideInterval(int hideInterval) {
        this.hideInterval = hideInterval;
    }

    public int getHideInterval() {
        return hideInterval;
    }
    public int getTotalWidth() {
		return totalWidth;
	}
    public void setScrollType(int scrollType) {
        this.scrollType = scrollType;
    }

    public void setSelector(CustomSelector selector) {
        this.selector = selector;
    }
    public void resize()
    {
      
        super.resize();
        for (int i = 0; i < childrens.size(); i++) {
            Control object = (Control)childrens.elementAt(i);
            object.resize();
        }
//         for (int i = 0; i < childrens.size(); i++) {
//            Control object = (Control)childrens.elementAt(i);
//            if(object.getWidthWeight() > 0)
//            {
//                object.setWidth((totalEmptyWidth * object.getWidthWeight())/100);
//            }
//           
//            if(object.getHeightWeight() > 0)
//            {
//                object.setHeight((totalEmptyHeight * object.getHeightWeight())/100);
//            }
//        }
        
    }


    public int getPreferredHeight() {
       
        if((!(getLayout() instanceof PropotionLayout) && getLayout() != null))
        {
            return getLayout().getPreferedHeight(this) + getTopInBound() + getBottomInBound();
        }else{
            return Util.getWrappedHeight(this)+ getTopInBound() + getBottomInBound();
        }
    } 
         
   


    public int getPreferredWidth() {
        if(!(getLayout() instanceof PropotionLayout) && getLayout() != null)
        {
            return getLayout().getPreferedWidth(this) + getLeftInBound() + getRightInBound();
        }else{
            return Util.getWrappedWidth(this)+ getLeftInBound() + getRightInBound();
        }
    }
    
    public void setEventManager(EventManager manager)
    {
        super.setEventManager(manager);
        for (int idx = 0; idx < childrens.size(); idx++) {
           Control control = (Control)childrens.elementAt(idx);
           control.setEventManager(manager);
        }
    }


    public void restoreOriogionalXY() {
        super.restoreOriogionalXY();
         for (int idx = 0; idx < childrens.size(); idx++) {
           Control control = (Control)childrens.elementAt(idx);
           control.restoreOriogionalXY();
        }
    }
    
    public void addChildren(Control control)
    {
        control.setParent(this);
        childrens.addElement(control);
        control.setEventManager(getEventListener());
    }
    

    public Vector getChildrens() {
		return childrens;
	}
    public int getTotalHeight() {
        return totalHeight;
    }
   
    public void setTotalHeight(int totalHeight)
    {
        this.totalHeight = totalHeight;
    }

    public int getWidth()
    {
         return super.getWidth();   
    }
    public int getChildrenIndex(Control control){
        return childrens.indexOf(control);
    }
    public Control getChild(int index){
        if(childrens.size()>index)
            return (Control)childrens.elementAt(index);
        else
            return null;
        
    }
    public int getSize()
    {
        return childrens.size();
    }
    public void addChildrenAt(Control control,int index){
        control.setParent(this);
        childrens.insertElementAt(control, index);
    }
    public void removeAll()
    {
        selectedChild = 0;
        childrens.removeAllElements();
        totalHeight = 0;
        scrolledY = 0;
    }
    public void removeChildren(Control children) {
        for (int i = 0; i < childrens.size(); i++) {
            Control control = (Control) childrens.elementAt(i);
            if (control.equals(children)) {
                childrens.removeElementAt(i);
            }
        }
    }

    public boolean isEmpty(){
    	if(childrens.isEmpty())
    		return true;
    	return false;
    }
    public void resetAnimations() {
      
         for (int i = 0; i < childrens.size(); i++) {
            Control control = (Control) childrens.elementAt(i);
            control.resetAnimations();
        }
        super.resetAnimations();
    }
    public void init()
    {
        EventQueue.getInstance().reset();
        hideNotify();
        initShowNotify();
        showNotify();
        resetAnimations();
       
    }
    public int getSelectedChildIndex()
    {
        return selectedChild;
    }
    public Control getSelectedChild()
    {
        if(selectedChild >= 0 && selectedChild < childrens.size())
        {
            Control control =  (Control)childrens.elementAt(selectedChild);
            return control;
        }
        else
            return null;
    }
    private int oldScrollY = -1;
    private int oldScrollX = -1;
	private boolean popup=false;
	private int blinkCounter=0;
	private int mod=12;
	
	
	public void setPopup(boolean popup) {
		this.popup = popup;
	}
	
	
    //#ifdef ANDROID
    public synchronized void paint(Canvas g, Paint paintObject)
    //#else
    //# public synchronized void paint(Graphics g)
    //#endif
    
    {
        updateScrollFromVelocity();
        bringControlToCenter();
        if(oldScrollY != scrolledY && isVerticalScrollBarPresent())
        {
            visabilityTimer = 0;
            oldScrollY = scrolledY;
        }
        if(oldScrollX != scrolledX && isHorizontalScrollBarPresent())
        {
            visabilityTimer = 0;
            oldScrollX = scrolledX;
        }
        if(!allStartAnimOver)
        {
            allStartAnimOver = EventQueue.getInstance().allStartAnimOver(this);
        }
        if(Settings.TOUCH_DEVICE)
        {
            if(unSelectChild && getParent() == null)
            {
                if(selectionDelayTimer >= selectedChildSelectionDelay + 1)
                {
                    unselectChild();

                }else{
                    selectionDelayTimer++;
                }

            }
        }
        visabilityTimer++;
       if(getParent() == null)
       {
            EventQueue.getInstance().process(this);
       }
      
       g.translate(-scrolledX, -scrolledY);
       Settings.TRANSLATED_Y -= scrolledY;
        
       int transY = Settings.TRANSLATED_Y;
      
       for (int idx = 0; idx < childrens.size(); idx++) {
           Control control = (Control)childrens.elementAt(idx);
           //#ifdef J2ME
          //# if( (transY + control.getY() +control.getHeight() < 0 || transY + control.getY() > Settings.SCREEN_HEIGHT))
          //# {
              //# continue;
          //# }
           //# if(isClipData())
           //# {
             //# if( (control.getY() +control.getHeight() < scrolledY || control.getY() - scrolledY > getHeight()))
             //# {
                 //# continue;
             //# }
           //# }
           //#endif
          
               //#ifdef ANDROID
           
//           if(this.popup){ 
//         	   if(blinkCounter%mod==0||blinkCounter%mod==1||blinkCounter%mod==2||blinkCounter%mod==3||blinkCounter%mod==4||blinkCounter%mod==5){
//         		  g.scale(102f/100, 102f/100, getWidth() >> 1, getHeight() >> 1);
//         		 control.paintUI(g,paintObject);
//         		 	g.restore();
//         	   }
//         	   blinkCounter++;
//                if(blinkCounter==mod)
//             	   blinkCounter=0;
//         	}else
         		 control.paintUI(g,paintObject);
           
                
                //#else
                //# control.paintUI(g);
                //#endif
          
        }
        g.translate(scrolledX, scrolledY);
        Settings.TRANSLATED_Y += scrolledY;
    }

 
      //#ifdef ANDROID
  protected void paintForground(Canvas g, Paint paintObject)
    //#else
     //# protected void paintForground(Graphics g)
    //#endif
    {
           //#ifdef ANDROID
        paintScrollBar(g,paintObject);
        super.paintForground(g,paintObject);
        //#else
        //# paintScrollBar(g);
        //# super.paintForground(g);
        //#endif
    }
    
           //#ifdef ANDROID
  protected void paintScrollBar(Canvas g, Paint paintObject)
    //#else
     //# protected void paintScrollBar(Graphics g)
    //#endif
    {
        
        if(totalHeight > getHeight())
        {
          int scrollSliderHeight;
           if((verticalScrollBarVisabilityType != SCROLLBAR_DISABLED) && (verticalScrollBarVisabilityType == SCROLLBAR_ALWAYS_VISABLE ||  (scrollType == SCROLLBAR_AUTO_HIDE && visabilityTimer < hideInterval )))
           {
                int scrollBar_y  =   0;
               
               
                barWidth = (getWidth() * widthPersent)/100;
                if(barWidth < SCROLL_BAR_MIN_WIDTH)
                {
                    barWidth = SCROLL_BAR_MIN_WIDTH;
                }
                int temp = ((((getBoundHeight() - 2) * 100) << 14) / totalHeight);
                scrollSliderHeight = ((temp * (getBoundHeight()  -  2)) / 100) >> 14;
                int percentage = ((((scrolledY + getBoundHeight()) << 6) *100) / totalHeight) >> 6;

                scrollBar_y = (((((getBoundHeight()  - 1) * percentage) << 14) / 100) >> 14 ) - scrollSliderHeight;
                if(scrolledY == 0)
                    scrollBar_y = 0 ;

                
               //#ifdef ANDROID
                 paintObject.setColor(Util.getColor(scrollBarColor));
                com.appon.gtantra.GraphicsUtil.fillRect(getWidth() - barWidth - 2,scrollBar_y, barWidth, scrollSliderHeight,g,paintObject);
                //#else
                 //# g.setColor(Util.getColor(scrollBarColor));
                 //# g.fillRect(getWidth() - barWidth - 2,scrollBar_y, barWidth, scrollSliderHeight);
                //#endif
               
               
           }    
        }
        //////// for horizontal //////
        if(totalWidth > getWidth())
        {
           if((verticalScrollBarVisabilityType != SCROLLBAR_DISABLED) && (verticalScrollBarVisabilityType == SCROLLBAR_ALWAYS_VISABLE ||  (scrollType == SCROLLBAR_AUTO_HIDE && visabilityTimer < hideInterval )))
           {
                    
                    long scrollBar_x  =   0;
                    long scrollSliderWidth;
                    barWidth = (getHeight() * widthPersent)/100;
                    if(barWidth < SCROLL_BAR_MIN_WIDTH)
                    {
                        barWidth = SCROLL_BAR_MIN_WIDTH;
                    }
                    long temp = ((long)((((long)getBoundWidth() - 2l) * 100l) << 14) / (long)totalWidth);
                    scrollSliderWidth = ((temp * (getBoundWidth()  -  2)) / 100) >> 14;
                    int percentage = ((((scrolledX + getBoundWidth()) << 6) *100) / totalWidth) >> 6;

                    scrollBar_x = (((long)((long)(((long)getBoundWidth()  - 1l) * (long)percentage) << 14) / 100) >> 14 ) - scrollSliderWidth;
                    if(scrolledX == 0)
                        scrollBar_x = 0 ;
                    
//                    int scrollBar_x  =   0;
//                    int scrollSliderWidth;
//                    barWidth = (getHeight() * widthPersent)/100;
//                    if(barWidth < SCROLL_BAR_MIN_WIDTH)
//                    {
//                        barWidth = SCROLL_BAR_MIN_WIDTH;
//                    }
//                    int temp = ((((getBoundWidth() - 2) * 100) << 14) / totalWidth);
//                    scrollSliderWidth = ((temp * (getBoundWidth()  -  2)) / 100) >> 14;
//                    int percentage = ((((scrolledX + getBoundWidth()) << 6) *100) / totalWidth) >> 6;
//
//                    scrollBar_x = (((((getBoundWidth()  - 1) * percentage) << 14) / 100) >> 14 ) - scrollSliderWidth;
//                    if(scrolledX == 0)
//                        scrollBar_x = 0 ;

                
               //#ifdef ANDROID
                 paintObject.setColor(Util.getColor(scrollBarColor));
                com.appon.gtantra.GraphicsUtil.fillRect(scrollBar_x,getHeight() - barWidth - 2, scrollSliderWidth, barWidth,g,paintObject);
                //#else
                 //# g.setColor(Util.getColor(scrollBarColor));
                 //# g.fillRect(scrollBar_x,getHeight() - barWidth - 2, scrollSliderWidth, barWidth);
                //#endif
               
               
           }    
        }
    }
     public void fourceSelect()
     {
         this.selected = true;
     }
    public boolean keyRepeated(int key,int gameKey) {
       if(selectedChild >= 0 && selectedChild < childrens.size())
         {
             Control control = (Control)childrens.elementAt(selectedChild);
             if(control.keyRepeated(key,gameKey))
                return true;
            
         }
         return super.keyRepeated(key,gameKey);
    }
    private boolean moveScrollBarHorizontal(int steps)
    {
        if(verticalScrollBarVisabilityType == SCROLLBAR_DISABLED)
        {
            return false;
        }
        int oldPos = scrolledX;
        if(steps > 0)
        {
            if(scrolledX + getBoundWidth() < totalWidth)
            {
                setScrolledX(scrolledX + steps);
            }
            if(scrolledX + getBoundWidth() > totalWidth)
            {
            	setScrolledX(totalWidth - getBoundWidth());
            }
        }else if(steps < 0)
        {
             if(scrolledX > 0)
                 setScrolledX(scrolledX - Math.abs(steps));
             if(scrolledX < 0)
                 setScrolledX(0);
        }
         if(scrolledX != oldPos)
         {
             
             if(Settings.TOUCH_DEVICE)
             {
                if(isAnyChildSelected)
                {
                    unSelectChild = true;
                }
             }
             return true;
         }
         else
             return false;
    }
    private boolean moveScrollBarVertical(int steps)
    {
        if(verticalScrollBarVisabilityType == SCROLLBAR_DISABLED)
        {
            return false;
        }
        int oldPos = scrolledY;
        if(steps > 0)
        {
            if(scrolledY + getBoundHeight() < totalHeight)
            {
                setScrolledY( scrolledY + steps);
            }
            else
                setScrolledY(totalHeight - getBoundHeight());
        }else if(steps < 0)
        {
             if(scrolledY > 0)
                 setScrolledY(scrolledY - Math.abs(steps));
             if(scrolledY < 0)
                 setScrolledY(0);
        }
         if(scrolledY != oldPos)
         {
             
             if(Settings.TOUCH_DEVICE)
             {
                if(isAnyChildSelected)
                {
                    unSelectChild = true;
                }
             }
             return true;
         }
         else
             return false;
    }
     
    public boolean keyPressed(int keycode,int gameKey)
    {
          Settings.TOUCH_DEVICE = false;
          if(EventQueue.getInstance().isProcsseing() || !allStartAnimOver || isBringingToCenter)
             return false;
          if(softkeyHashTable != null && softkeyHashTable.get(keycode+"") != null)
          {
              Control c = (Control)softkeyHashTable.get(keycode+"");
              c.cutomKeyEventFired();
              return true;
          }
           if(singleSelectable)
          {
               switch(gameKey)
               {
                case Settings.LEFT:
                    if(singleSelectableIndex > 0)
                    {
                        singleSelectableIndex--;
                    }
                   return true;
                case Settings.RIGHT:
                    if(singleSelectableIndex < getSize() - 1)
                    {
                        singleSelectableIndex++;
                    }
                    return true;
               }
          }
//          if((isGroupSelect()  && (keycode == Settings.KEY_SOFT_CENTER || gameKey == Settings.FIRE )))
//          {
//              return super.keyPressed(keycode,gameKey);
//          }
        if(scrollType == SMOOTH_SCROLL)
        {
        	if(totalWidth > getWidth())
            {
                if(gameKey == Settings.LEFT)
                {
                    if(moveScrollBarHorizontal(-15))
                        return true;
                }else  if(gameKey == Settings.RIGHT)
                {
                    if(moveScrollBarHorizontal(15))
                        return true;
                }
            }
        	if(totalHeight > getHeight())
            {
                if(gameKey == Settings.DOWN)
                {
                    if(moveScrollBarVertical(15))
                        return true;
                }else  if(gameKey == Settings.UP)
                {
                    if(moveScrollBarVertical(-15))
                        return true;
                }
            }
            
            
         }
         if(selectedChild >= 0 && selectedChild < childrens.size())
         {
             Control control = (Control)childrens.elementAt(selectedChild);
             if(control.keyPressed(keycode,gameKey))
                return true;
         }
          if(super.keyPressed(keycode,gameKey))
          {
              return true;
          }
        int newIndex = -1;
        int findId = -1;
        if(selectedChild >= 0 && selectedChild < childrens.size() && ((Control)childrens.elementAt(selectedChild)).isSelected())
        {
            Control control = (Control)childrens.elementAt(selectedChild); 
            switch(gameKey)
            {
                case Settings.LEFT:
                    findId = control.leftChild;
                break;
                case Settings.RIGHT:
                    findId = control.rightChild;
                break;
                case Settings.UP:
                    findId = control.upChild;
                break;
                case Settings.DOWN:
                    findId = control.downChild;
                break;
            }
        }
        
        if(findId != -1)
        {
            return selectAnyChild(findId);
        }else{
            if(selector != null)
            {
                newIndex = selector.getNextChildIndex(gameKey, selectedChild, this);
            }
            if(newIndex == -1)
            {
                newIndex = getNextChildIndex(gameKey,selectedChild);
            }
            if(newIndex != -1 && newIndex != selectedChild)
            {
                selectChild(newIndex,false);
                return true;
            }else {
                if(totalHeight > getHeight()){
                    if(gameKey == Settings.UP)
                    {
                        setScrolledY(0);
                    }else if(gameKey == Settings.DOWN)
                    {
                       setScrolledY(totalHeight - getBoundHeight());
                    }
                }
                 if(totalWidth > getWidth()){
                    if(gameKey == Settings.LEFT)
                    {
                        setScrolledX(0);
                        
                    }else if(gameKey == Settings.RIGHT)
                    {
                        setScrolledX(totalWidth - getBoundWidth());
                    }
                }
            }
                
        }
        return false;
    }
    private boolean takeEventIfNoScroll = false;
    public void setTakeEventIfNoScroll(boolean takeEventIfNoScroll) {
        this.takeEventIfNoScroll = takeEventIfNoScroll;
    }
    public void setScrolledX(int scrolledX) {
        if(this.scrolledX != scrolledX )
        {
            didScrolled = true;
        }
        this.scrolledX = scrolledX;
    }

    public void setScrolledY(int scrolledY) {
        if(this.scrolledY != scrolledY )
        {
            didScrolled = true;
        }
        this.scrolledY = scrolledY;
    }
    
    int getNextChildIndex(int gameKey, int index)
    {
        int oldIndex = -1;
        if(index >= 0 && index < childrens.size())
        {
            Control currentChild = (Control)childrens.elementAt(index);
            int currentPosX = currentChild.getX();
            int currentPosY = currentChild.getY();
            switch(gameKey)
            {
                case Settings.UP:
                    currentPosX += (currentChild.getWidth() >> 1);
                break;
                case Settings.DOWN:
                    currentPosY += currentChild.getHeight();
                    currentPosX += (currentChild.getWidth() >> 1);
                break;
                case Settings.LEFT:
                    currentPosY += (currentChild.getHeight() >> 1);
                break;
                case Settings.RIGHT:
                    currentPosY += (currentChild.getHeight() >> 1);
                    currentPosX += (currentChild.getWidth());
                break;
            }
            int oldDist = Integer.MAX_VALUE, dist;
            for (int i = 0; i < childrens.size(); i++) {
                Control control = (Control)childrens.elementAt(i);
                boolean shellSelect = shellSelectControl(control);
                if(control.isVisible() && control.isSelectable() && shellSelect)
                {
                    int controlX = control.getX() + (control.getWidth() >> 1);
                    int controlY = control.getY() + (control.getHeight() >> 1);
                    if( (gameKey == Settings.UP && controlY < currentPosY ) || (gameKey == Settings.DOWN && controlY > currentPosY) || (gameKey == Settings.LEFT && controlX < currentPosX) || (gameKey == Settings.RIGHT && controlX > currentPosX))
                    {
                        dist = Util.getApproxDistance(controlX,controlY , currentPosX , currentPosY);
                        if(dist < oldDist)
                        {
                            oldDist = dist;
                            oldIndex = i;
                        }
                    }     
                    
                }
            }
         
        }
       return oldIndex;
    }
   public void resetSelecton()
   {
       selectedChild = 0;
       for (int i = 0; i < childrens.size(); i++) {
           if(childrens.elementAt(i) instanceof Container)
           {
               ((Container)childrens.elementAt(i)).resetSelecton();
           }
       }
   }
   public void setSelected(boolean selected,boolean isFromTouch) {
	   super.setSelected(selected,isFromTouch);
       if(selected == true)
       {
           if(selectedChild >= 0 && selectedChild < childrens.size())
           {
               Control control = (Control)childrens.elementAt(selectedChild);
               boolean shellSelect = shellSelectControl(control);
               if(!control.isSelectable() || !shellSelect)
               {
                   selectedChild = -1;
                   for (int i = 0; i < childrens.size(); i++) {
                       Control object = (Control)childrens.elementAt(i);
                       shellSelect = shellSelectControl(object);
                       if(object.isSelectable() && shellSelect)
                       {
                           selectedChild = i;
                           break;
                       }
                  }
              }
               if(selectedChild != -1)
                   selectChild(selectedChild,isFromTouch);
           }
       }
       else
       {
           if(selectedChild >= 0 && selectedChild < childrens.size())
           {
               Control control = (Control)childrens.elementAt(selectedChild);
               control.setSelected(false);
           }
       }
   }
    public void setSelected(boolean selected) {
        setSelected(selected,false);
    }
    private boolean selectAnyChild(int id)
    {
        if(id == -1)
            return false;
        Container root = this;
        Container ownerParent = this;
        boolean success = false;
        while(root.getParent() != null)
        {
            root = root.getParent();
        }
        ownerParent = findAndSelectOwnChild(id);
        success = (ownerParent != null);
        if(ownerParent != null)
        {
            while(ownerParent.getParent() != null)
            {
                int index = ownerParent.getParent().getChildrenIndex(ownerParent);
                ownerParent.getParent().selectChild(index,false);
                ownerParent = ownerParent.getParent();
            }
        }
        return success;
    }
    
//    protected boolean selectAnyChild(int id)
//    {
//        if(id == -1)
//            return false;
//        // find own
//        if(findAndSelectOwnChild(id) != null) 
//        {
//            return false;
//        }
//        //searching outer side
//        Container parent = getParent();
//        while(parent != null)
//        {
//            
//             //searching own
//            for (int i = 0; i < parent.getSize(); i++) {
//                if(((Control)parent.getChild(i)).getId() == id)
//                {
//                    parent.selectChild(i);
//                    parent = 
//                    while(parent != null)
//                    {
//                        
//                    }
//                    return;
//                }   
//            }
//            parent = parent.getParent();
//        }
//    }
    public void selectChild(Control child,boolean isFromTouch)
    {
        int index = childrens.indexOf(child);
        selectChild(index, isFromTouch);
    }
    public void selectChild(int index,boolean isFromTouch)
    {
        if(selectedChild >= 0 && selectedChild < childrens.size())
        {
            Control control = (Control)childrens.elementAt(selectedChild);
            control.setSelected(false,isFromTouch);
        }
        if(index >= 0 && index < childrens.size())
        {
            Control control = (Control)childrens.elementAt(index);
             if(Settings.TOUCH_DEVICE)
             {
                unselectChild();
                isAnyChildSelected = true;
                selectionDelayTimer = 0;
                selectedChildSelectionDelay = control.getDelayInSelection();
                if(control instanceof ScrollableContainer)
                {
                    ((ScrollableContainer)control).selectedChild = -1;
                }
                //else if(control instanceof Container)
                //                {
                //                    control.setSelected(false);
                //                }
             }
            control.setSelected(true,isFromTouch);
            if(selectedChild != index && getEventListener() != null)
            {
                getEventListener().event( new Event(EventManager.SELECTION_CHANGED, control,new Integer(index)));
            }
            selectedChild = index;
           
            if(!isFromTouch){
            	if(scrollType == SELECT_COMPONENT_SCROLL &&  ((Control) childrens.elementAt(selectedChild)) instanceof Container)
                {
                    Control container = ((Control) childrens.elementAt(selectedChild));
                    if(totalHeight > getHeight())
                    {
                        if (selectedChild == upperMostElement) {
                            setScrolledY(getScrolledY(container, 0));
                        } else {
                            setScrolledY(getScrolledY(container, scrolledY));
                        }
                    }
                    if(totalWidth > getWidth())
                    {
                        if (selectedChild == leftMostElement) {
                            setScrolledX(getScrolledX(container, 0));
                        } else {
                            setScrolledX(getScrolledX(container, scrolledX));
                        }
                    }       
                } else {
                            setScrollBarPositionVertical(((Control) childrens.elementAt(this.selectedChild)).getY(),
                                    ((Control) childrens.elementAt(selectedChild)).getHeight());
                            
                            setScrollBarPositionHorizontal(((Control) childrens.elementAt(this.selectedChild)).getX(),
                                    ((Control) childrens.elementAt(selectedChild)).getWidth());
                }
            }
            
        }
        
    }
   

    private int getScrolledY(Control base, int virtualScrollHeight) {
        int tmpHeight = 0;
        if (base instanceof ScrollableContainer && !((ScrollableContainer)(base)).groupSelect ) {
            int _yPos = 0;
            Control component = base;
            while (component instanceof ScrollableContainer && !((ScrollableContainer)component).groupSelect) {
                _yPos += component.getY();
                Control tmp = ((ScrollableContainer) component).getSelectedChild();
                if (tmp == null) {
                	_yPos -= component.getY();
                    break;
                }
                component = tmp;
            }
            if (component != null) {
                if (_yPos + component.getY() + component.getHeight() >= getHeight() + virtualScrollHeight) {
                    tmpHeight = _yPos + component.getY() + component.getHeight() - getHeight() ;
                } else if (_yPos + component.getY() < virtualScrollHeight) {
                    tmpHeight = _yPos + component.getY();
                }
            }
        } else {
            if (base.getY() + base.getHeight() >= getHeight() + virtualScrollHeight) {
                tmpHeight = base.getY() + base.getHeight() - getHeight() ;
            } else if (base.getY() < virtualScrollHeight) {
                tmpHeight = base.getY() ;
            }
        }
        if (tmpHeight == 0) {
            return virtualScrollHeight;
        } else {
            return tmpHeight;
        }
    }
    private int getScrolledX(Control base, int virtualScrollWidth) {
        int tmpWidth = 0;
        
        if (base instanceof ScrollableContainer && !((ScrollableContainer)(base)).groupSelect ) {
            int _xPos = 0;
            Control component = base;
            
            while (component instanceof ScrollableContainer && !((ScrollableContainer)(base)).groupSelect) {
                _xPos += component.getX();
                Control tmp = ((ScrollableContainer) component).getSelectedChild();
                if (tmp == null) {
                	_xPos -= component.getX();
                    break;
                }
                component = tmp;
            }
            if (component != null) {
                if (_xPos + component.getX() + component.getWidth() >= getWidth() + virtualScrollWidth) {
                    tmpWidth = _xPos + component.getX() + component.getWidth() - getWidth();
                } else if (_xPos + component.getX() < virtualScrollWidth) {
                    tmpWidth = _xPos + component.getX();
                }
            }
        } else {
            if (base.getX() + base.getWidth() >= getWidth() + virtualScrollWidth) {
                tmpWidth = base.getX() + base.getWidth() - getWidth() ;
            } else if (base.getX() < virtualScrollWidth) {
                tmpWidth = base.getX() ;
            }
        }
        if (tmpWidth == 0) {
            return virtualScrollWidth;
        } else {
            return tmpWidth;
        }
    }
//    public void resize()
//    {
//        if(layout != null)
//        {
//            layout.applyLayout(this);
//        }
//        if(getHeight() == 0)
//        {
//           int tmp = 0;
//            for (int i = 0; i < childrens.size(); i++) {
//             Control control = (Control)childrens.elementAt(i);
//             if(control.getY() + control.getHeight() > tmp)
//             {
//                 tmp = control.getY() + control.getHeight();
//             }
//             
//            }
//           setHeight(tmp);
//        }
//        if(getWidth() == 0)
//        {
//            setWidth(Settings.SCREEN_WIDTH);
//        }
//    }
    private static int getAvailableWidth(Control control,Container parent)
    {
        int widthAvailable = parent.getBoundWidth();
        for (int i = 0; i < parent.getSize(); i++) {
            Control controlToCheck = (Control)parent.getChild(i);
            if(controlToCheck.getId() != control.getId() && (controlToCheck.getWidthWeight() == 0 || controlToCheck.getWidthWeight() == -2 )/*&& isControlInWidthSeries(control, controlToCheck)*/)
            {
                widthAvailable-= controlToCheck.getWidth();
            }
        }
        return widthAvailable;
    }
//    private static boolean isControlInWidthSeries(Control control1,Control control2)
//    {
//        if( (control1.getY() <= control2.getY() && control1.getY() + control1.getHeight() <= control2.getY())||
//        (control1.getY() >= control2.getY() && control1.getY() + control1.getHeight() >= control2.getY()))
//        {
//            return false;
//        }    
//        return true;     
//    }
//    
    private static int getAvailableHeight(Control control,Container parent)
    {
        int widthAvailable = parent.getBoundHeight();
        for (int i = 0; i < parent.getSize(); i++) {
            Control controlToCheck = (Control)parent.getChild(i);
            if(controlToCheck.getId() != control.getId() && (controlToCheck.getHeightWeight() == 0|| controlToCheck.getHeightWeight() == -2) /*&& isControlInHeightSeries(control, controlToCheck)*/)
            {
                widthAvailable-= controlToCheck.getHeight();
            }
        }
        return widthAvailable;
    }
//    private static boolean isControlInHeightSeries(Control control1,Control control2)
//    {
//        if( (control1.getX() <= control2.getX() && control1.getX() + control1.getWidth() <= control2.getX())||
//        (control1.getX() >= control2.getX() + control2.getWidth() && control1.getX() + control1.getWidth() >= control2.getX() + control2.getWidth()))
//        {
//            return false;
//        }    
//        return true;      
//    }
    public void stretchDimentions()
    {
         for (int i = 0; i < childrens.size(); i++) {
            Control control = (Control)childrens.elementAt(i);
            if(control.getWidthWeight() > 0)
            {
                int newW = getAvailableWidth(control, this);
                control.setWidth((newW * control.getWidthWeight())/100);
            }
            if(control.getHeightWeight() > 0)
            {
                int newW = getAvailableHeight(control, this);
                control.setHeight((newW * control.getHeightWeight())/100);
            }
            if(control instanceof Container)
            {
                ((Container)control).stretchDimentions();
            }
        }
//         if(widthHeightSet)
//         {
//              super.showNotify();
//              for (int i = 0; i < childrens.size(); i++) {
//                    Control control = (Control)childrens.elementAt(i);
//                    control.showNotify();
//              }
//              calculateTotalHeight();
//         }
        
    }
    private boolean isAnyChildSelectable()
    {
        for (int i = 0; i < childrens.size(); i++) {
            Control object = (Control)childrens.elementAt(i);
            if(object.isSelectable())
                return true;
            
        }
        return false;
    }
    public boolean shellSelectControl(Control c)
    {
        if(c instanceof ScrollableContainer)
        {
             boolean anyChildSelectable = true;
             anyChildSelectable = ((ScrollableContainer)c).isAnyChildSelectable();
             if(((ScrollableContainer)c).isGroupSelect())
             {
                 return true;
             }else{
                 return anyChildSelectable;
             }
        }
        return true;
    }
    private void calculateTotalHeight()
    {
        int actHeight = 0;
        int actWidth = 0;
        upperMostElement = -1;
        totalHeight = 0;
        barWidth = 0;
        totalWidth = 0;
        for (int i = 0; i < childrens.size(); i++) {
             Control control = (Control)childrens.elementAt(i);
             if (((Control) childrens.elementAt(i)).isSelectable() && (upperMostElement == -1 || ((Control) childrens.elementAt(i)).getY() < ((Control) childrens.elementAt(upperMostElement)).getY()) ) {
                    upperMostElement = i;
             }
             if (((Control) childrens.elementAt(i)).isSelectable() && (leftMostElement == -1 || ((Control) childrens.elementAt(i)).getX() < ((Control) childrens.elementAt(leftMostElement)).getX()) ) {
                    leftMostElement = i;
             }
             if(control.getY() + control.getHeight() > actHeight && !control.isSkipParentWrapSizeCalc())
             {
                 actHeight = control.getY() + control.getHeight();
             }
             if(control.getX() + control.getWidth() > actWidth && !control.isSkipParentWrapSizeCalc())
             {
                 actWidth = control.getX() + control.getWidth();
             }
             if(!Settings.TOUCH_DEVICE && selectChild )
             {
                 if(defaultSelection == -1)
                 {
                    boolean anyChildSelectable = shellSelectControl(control);
                   
                    if(control.isSelectable() && control.isVisible() && anyChildSelectable)
                    {
                        selectChild = false;
                        selectChild(i,false);
                    }else
                    {
                        control.setSelected(false);
                    }
                 }else{
                      selectChild = false;
//                      for (int j = 0; j < childrens.size(); j++) {
//                         if(getChild(j).getId() == defaultSelection)
//                         {
//                             selectChild(j,false);
//                             break;
//                         }
//                     }
                      selectDefaultChild();
                 }
             }
        }
        if(actHeight > getHeight())
        {
            totalHeight = actHeight;
            scrolledY = staticScrolledPos;
        }
        if(actWidth > getWidth())
        {
            totalWidth = actWidth;
            scrolledX = staticScrolledPos;
        }
       
    }
 private void selectDefaultChild()
 {
      for (int j = 0; j < childrens.size(); j++) {
        if(getChild(j).getId() == defaultSelection)
        {
            selectChild(j,false);
            if((getChild(j) instanceof ScrollableContainer) && ((ScrollableContainer)getChild(j)).getDefaultSelection() != -1)
            {
                ((ScrollableContainer)getChild(j)).selectDefaultChild();
            }
            break;
        }
    }
 }
    public int getScrolledX() {
        return scrolledX;
    }

    public int getScrolledY() {
        return scrolledY;
    }
    
//    private static void wrapSize(Container container)
//    {
//         for (int i = 0; i < container.getSize(); i++) {
//            Control control = (Control)container.getChild(i);
//            if(control instanceof Container)
//            {
//                Container c = (Container)control;
//                if(c.getSizeSetting() == WRAP_CONTENT && ((c.getLayout() instanceof RelativeLayout) || (c.getLayout() instanceof PropotionLayout) || c.getLayout() == null))
//                {
//                    wrapSize(c);
//                }    
//            }
//         }
//         if(container.getSizeSetting() == WRAP_CONTENT && ((container.getLayout() instanceof RelativeLayout) || (container.getLayout() instanceof PropotionLayout) || container.getLayout() == null))
//         {
//              container.setWidth(container.getPreferredWidth());
//              container.setHeight(container.getPreferredHeight());
//         }    
//        
//    }
    
    public void initShowNotify()
    {
        restoreOriogionalXY();
        resize();
        stretchDimentions();
        showNotify();
        for (int i = 0; i < childrens.size(); i++) {
           Control control = (Control)childrens.elementAt(i);
           control.showNotify();
        }
        resize();
        stretchDimentions();
//        showNotify();
//        for (int i = 0; i < childrens.size(); i++) {
//           Control control = (Control)childrens.elementAt(i);
//           control.showNotify();
//        }
        resize();
    }
    public void showNotify() {
        unSelectChild = false;
        isAnyChildSelected = false;
        allStartAnimOver = false;
        
        
//             wrapSize(this);
        
       
        for (int i = 0; i < childrens.size(); i++) {
            Control control = (Control)childrens.elementAt(i);
            control.showNotify();
        }
        super.showNotify();
        if(getParent() == null)
        {
            selectChild = true;
        }
        calculateTotalHeight();
       
    }
    public void setSelectChild(boolean value)
    {
        selectChild = value;
    }
    protected boolean isVerticalScrollBarPresent()
    {
        return (totalHeight > getHeight());
    }
    protected boolean isHorizontalScrollBarPresent()
    {
        return (totalWidth > getWidth());
    }
    public void hideNotify() {
       setScrolledX(0);
       setScrolledY(0);
        selectChild = true;
        super.hideNotify();
        for (int i = 0; i < childrens.size(); i++) {
            Control control = (Control)childrens.elementAt(i);
            control.hideNotify();
        }
    }
    public void takeScrollBackup() {
        scrolledBackupX = scrolledX;
        scrolledBackupY = scrolledY;
         for (int i = 0; i < childrens.size(); i++) {
            Control control = (Control)childrens.elementAt(i);
            if(control instanceof Container)
            {
                ((Container)control).takeScrollBackup();
            }
        }
    }

   
    public void restoreScrollBackup() {
        scrolledX = scrolledBackupX;
        scrolledY = scrolledBackupY;
         for (int i = 0; i < childrens.size(); i++) {
            Control control = (Control)childrens.elementAt(i);
            if(control instanceof Container)
            {
                ((Container)control).restoreScrollBackup();
            }
        }
    }
    public void unselectChild()
    {
        
        for (int i = 0; i < childrens.size(); i++) {
            ((Control)(childrens.elementAt(i))).setSelected(false);
        }
        unSelectChild = false;
        isAnyChildSelected = false;
        selectionDelayTimer = 0;
        selectedChildSelectionDelay = 0;
    }
    public void scrollTillLast()
    {
        if(totalHeight > getHeight())
        {
            setScrolledY(totalHeight - getHeight());
        }
    }

    public int getDrawingYPos() {
       return getY();
    }
    public int getDrawingXPos() {
       return getX();
    }
    public void setScrollBarPositionVertical(int yPos, int controlHeight) {
        if (totalHeight <= getHeight()) {
            if (getParent() != null && getParent() instanceof ScrollableContainer) {
                ((ScrollableContainer) getParent()).setScrollBarPositionVertical(((ScrollableContainer) this).getDrawingYPos() + yPos - totalHeight, controlHeight);
            }
        } else {
            
            if (selectedChild == upperMostElement) {
                setScrolledY(getScrolledY((Control) childrens.elementAt(selectedChild), 0));
            } else if (yPos + controlHeight >= getHeight() + scrolledY) {
                setScrolledY(yPos + controlHeight - getHeight()) ;
            } else if (yPos < scrolledY) {
                 setScrolledY(yPos);
            }
            if (getParent() != null && getParent() instanceof ScrollableContainer) {
                ((ScrollableContainer) getParent()).setScrollBarPositionVertical(((ScrollableContainer) this).getDrawingYPos() + yPos - scrolledY, controlHeight);
            }
            
        }
    }
    public void setScrollBarPositionHorizontal(int xPos, int controlWidth) {
        if (totalWidth <= getWidth()) {
            if (getParent() != null && getParent() instanceof ScrollableContainer) {
                ((ScrollableContainer) getParent()).setScrollBarPositionHorizontal(((ScrollableContainer) this).getDrawingXPos() + xPos - totalWidth, controlWidth);
            }
        } else {
            
            if (selectedChild == leftMostElement) {
                setScrolledX(getScrolledX((Control) childrens.elementAt(selectedChild), 0));
            } else if (xPos + controlWidth >= getWidth() + scrolledX) {
               setScrolledX(xPos + controlWidth - getWidth());
            } else if (xPos < scrolledX) {
                setScrolledX(xPos);
            }
            if (getParent() != null && getParent() instanceof ScrollableContainer) {
                ((ScrollableContainer) getParent()).setScrollBarPositionHorizontal(((ScrollableContainer) this).getDrawingXPos() + xPos - scrolledX, controlWidth);
            }
            
        }
    }
    public String toString() {
       return "Container- ID: "+getId();
    }
   
    private void updateScrollFromVelocity()
    {
    	
        if(totalWidth > getWidth() && velocityX != 0)
        {
        	
            setScrolledX((int)(scrolledX + ((int)(velocityX * FPS_CONSIDERED) >> 7))); 
            if(scrolledX < 0)
            {
                setScrolledX(0);
                velocityX = 0;
            }else if(scrolledX > totalWidth - getWidth())
            {
                setScrolledX(totalWidth - getWidth());
                velocityX = 0 ;
            }else{
               long newVel = 0;
                if(velocityX > 0)
                    newVel = velocityX - (FRICTION);
                else
                    newVel = velocityX + (FRICTION);
                if(velocityX > 0)
                {
                    if(newVel < 0)
                    {
                        velocityX = 0;
                    }else{
                        velocityX = newVel;
                    }
                }if(velocityX < 0)
                {
                    if(newVel > 0)
                    {
                        velocityX = 0;
                    }else{
                        velocityX = newVel;
                    }
                }
            }
        }
        if(totalHeight > getHeight() && velocityY != 0)
        {
            setScrolledY((int)(scrolledY + ((int)((velocityY * FPS_CONSIDERED) >> 7))));
            if(scrolledY < 0)
            {
                setScrolledY(0);
                velocityY = 0;
            }else if(scrolledY > totalHeight - getHeight())
            {
                setScrolledY(totalHeight - getHeight());
                velocityY =  0;
            }else{
                long newVel = 0;
                if(velocityY > 0)
                    newVel = velocityY - (FRICTION);
                else
                    newVel = velocityY + (FRICTION);
                if(velocityY > 0)
                {
                    if(newVel < 0)
                    {
                        velocityY = 0;
                    }else{
                        velocityY = newVel;
                    }
                }if(velocityY < 0)
                {
                    if(newVel > 0)
                    {
                        velocityY = 0;
                    }else{
                        velocityY = newVel;
                    }
                }
            }
        }
    }
     public static boolean  fromPointerDrag = false;
     public boolean doNotHandleRelease = false;
     private boolean singleSelectable = false;
     private int singleSelectableIndex = 0;
     private static int direction = Settings.LEFT;
     private void checkSingleSelectControlIndex()
     {
          if(!singleSelectable)
          {
              return;
          }
         int _index = -1;
         int centerX = getWidth() >> 1; 
         int perCheck = 15;
         int dist = (getWidth() * perCheck) / 100;
        
         for (int i = 0; i <getSize(); i++) {
             
             if(direction == Settings.LEFT)
             {
               if(Util.isRectCollision(getWidth() - dist, 0, dist, getHeight(), getChild(i).getX() - scrolledX, 0, getChild(i).getWidth(), getHeight()))
               {
                 _index = i;
                 break;
               }
             }else{
               if(Util.isRectCollision(0, 0, dist, getHeight(), getChild(i).getX() - scrolledX, 0, getChild(i).getWidth(), getHeight()))
               {
            	   _index = i;
                   break;
               }
             }
         }
        
         if(_index != -1)
         {
             singleSelectableIndex = _index;
//             System.out.println("singleSelectableIndex: "+singleSelectableIndex);
         }
     }
     public static boolean isBringingToCenter = false;
     private void bringControlToCenter()
     {
         if(singleSelectable && !fromPointerDrag)
         {
             int speed = (getWidth() * 10) / 100;
             int finalPos =0;// (getWidth() -getChild(singleSelectableIndex).getWidth())>> 1;
             if(scrolledX - getChild(singleSelectableIndex).getX()  < finalPos)
             {
                 isBringingToCenter = true;
                 scrolledX += speed;
                 if(scrolledX - getChild(singleSelectableIndex).getX()  >= finalPos)
                 {
                     isBringingToCenter = false;
                     scrolledX = Math.abs(finalPos - getChild(singleSelectableIndex).getX());
                     selectChild(singleSelectableIndex, true);
                     singleSelectable=false;
                 }
             }else  if(scrolledX - getChild(singleSelectableIndex).getX()  > finalPos)
             {
                 isBringingToCenter = true;
                 scrolledX -= speed;
                 if(scrolledX - getChild(singleSelectableIndex).getX()  <= finalPos)
                 {
                      isBringingToCenter = false;
                     scrolledX = Math.abs(finalPos - getChild(singleSelectableIndex).getX());
                     selectChild(singleSelectableIndex, true);
                     singleSelectable=false;
                 }
             }
         }
     }
    public void setSingleSelectable(boolean singleSelectable) {
        this.singleSelectable = singleSelectable;
    }
     
     public boolean pointerPressed(int x, int y) {
          Settings.TOUCH_DEVICE = true;
          didScrolled = false;
          if(EventQueue.getInstance().isProcsseing() || !allStartAnimOver || isBringingToCenter)
             return false;
          if(getParent() == null)
          {
             oldY = y;
             oldX = x;
          }
          if(velocityX > 0 || velocityY > 0)
          {
        	  velocityX = 0;
              velocityY = 0;
              lastTouchTime = System.currentTimeMillis();
              lastTouchPressedX = x;
              lastTouchPressedY = y;
              doNotHandleRelease = true;
              
              return true;
          }
          //for smooth scrolling
          velocityX = 0;
          velocityY = 0;
          lastTouchTime = System.currentTimeMillis();
          lastTouchPressedX = x;
          lastTouchPressedY = y;
          doNotHandleRelease = false;
          for (int idx = 0; idx < childrens.size(); idx++) {
           Control control = (Control)childrens.elementAt(idx);
           if((control.isSelectable() || control.isTouchSelectable()) && control.isVisible() && Util.isInRect(control, x - getX() - getLeftInBound() + scrolledX, y - getY() - getTopInBound() + scrolledY ))
           {
               if(!control.isSelected())
               {
                    selectChild(idx,true);
               }
              boolean handle = control.pointerPressed(x - getX() - getLeftInBound() + scrolledX, y - getY() - getTopInBound()+ scrolledY );
              if(handle)
              {
                  return true;
              }
              
           }
        }
        return super.pointerPressed(x, y);
         
    }
    public  synchronized boolean pointerReleased(int x, int y) {

        if(EventQueue.getInstance().isProcsseing() || !allStartAnimOver || isBringingToCenter )
             return false;
        if(Settings.TOUCH_DEVICE)
        {
            if(getParent() == null && fromPointerDrag && !didScrolled)
            {
                if(takeEventIfNoScroll)
                    fromPointerDrag = false;
            }    
             if(isAnyChildSelected)
                 unSelectChild = true;
        }
        didScrolled = false;
       
          //for smooth scrolling
        if(doNotHandleRelease)
     	   return true;
        if(fromPointerDrag)
        {
            long timeDiff = System.currentTimeMillis() - lastTouchTime;
            if(timeDiff > 0 && !singleSelectable)
            {
            	if(totalWidth > getWidth())
            	{
                     velocityX = (((lastTouchPressedX - x)*2) <<7)/timeDiff;
                     int maxVelocity = (((totalWidth - getWidth()) * MAX_SCROOLED_STEPS) << 7) / (100 * FPS_CONSIDERED) + (FRICTION * MAX_SCROOLED_STEPS);
                    if(velocityX < 0)
                    {
                        maxVelocity = -maxVelocity;
                    }
                    if(Math.abs(velocityX) > Math.abs(maxVelocity))
                    {
                        velocityX = maxVelocity;
                    }
                     if(Math.abs(velocityX) < MINIMUM_VELOCITY )
                     {
                         velocityX = 0;
                         checkSingleSelectControlIndex() ;
                     }
            	}
               if(totalHeight > getHeight())
               {
            	   velocityY = (((lastTouchPressedY - y) * 2)<<7)/timeDiff;
                   int maxVelocity = (((getTotalHeight() - getHeight()) * MAX_SCROOLED_STEPS) << 7) / (100 * FPS_CONSIDERED) + (FRICTION * MAX_SCROOLED_STEPS);
                   if(velocityY < 0)
                   {
                       maxVelocity = -maxVelocity;
                   }
                   if(Math.abs(velocityY) > Math.abs(maxVelocity))
                   {
                       velocityY = maxVelocity;
                   }
                   if(Math.abs(velocityY) < MINIMUM_VELOCITY )
                   {
                       velocityY = 0;
                   }
               }
            }else{
                checkSingleSelectControlIndex();
            }
            lastTouchPressedX = x;
            lastTouchPressedY = y;
        }
//        if(getParent() == null)   
//        {
//            if(fromPointerDrag)  
//            {
//                if(Math.abs(diffrenceX) < 5 && Math.abs(diffrenceY) < 5)
//                {
//                    fromPointerDrag = false;
//                }
//            }
////            if(fromPointerDrag)
////            {
////                fromPointerDrag = false;
////                return false;
////            }
//         }
     
         for (int idx = 0; idx < childrens.size(); idx++) {
                    Control control = (Control)childrens.elementAt(idx);
                    if((control.isTouchSelectable() || control.isSelectable()) && control.isVisible() && Util.isInRect(control, x - getX() - getLeftInBound() + scrolledX, y - getY() - getTopInBound() + scrolledY ))
                    {
                        boolean handle = control.pointerReleased(x - getX() - getLeftInBound() + scrolledX, y - getY() - getTopInBound() + scrolledY );
                        if(handle)
                        {
                    	
                            return true;
                        }
                    }
            }
         boolean handle = super.pointerReleased(x, y) && fromPointerDrag;
         if(fromPointerDrag && getParent() == null)
         {
             fromPointerDrag = false;
         }
         return handle ;
         
          
      }
     
       public  synchronized boolean pointerDragged(int x, int y) {
           
         
            if(EventQueue.getInstance().isProcsseing() || !allStartAnimOver || isBringingToCenter)
                return false;
            if(getParent() == null)
            {
                diffrenceY = oldY - y;
                diffrenceX = oldX - x;
                if(diffrenceX > 0)
                {
                    direction = Settings.LEFT;
                }else{
                    direction = Settings.RIGHT;
                }
                if(!fromPointerDrag){
                    if(Math.abs(diffrenceX) >= MINIMUM_SHIFT_TO_DRAG || Math.abs(diffrenceY) >= MINIMUM_SHIFT_TO_DRAG)
                    {
                        fromPointerDrag = true;
                    }else{
                        return false;
                    }
                }
                oldY = y;
                oldX = x;
            }
             for (int idx = 0; idx < childrens.size(); idx++) {
                    Control control = (Control)childrens.elementAt(idx);
                    if((control.isSelectable() || control.isTouchSelectable()) && control.isVisible() && Util.isInRect(control, x - getX() - getLeftInBound() + scrolledX, y - getY() - getTopInBound() + scrolledY))
                    {
                        if(control.pointerDragged(x - getX() - getLeftInBound() + scrolledX, y - getY() - getTopInBound() + scrolledY ))
                                return true;
                    }
                }
                if(totalHeight > getHeight())
                {
                        if(moveScrollBarVertical(diffrenceY))
                        return true;
                }
                 if(totalWidth > getWidth())
                {
                        if(moveScrollBarHorizontal(diffrenceX))
                        return true;
                }
                return false;
         
      }

  
    public int getClassCode() {
        return MenuSerilize.CONTROL_SCROLLABLE_CONTAINER_TYPE;
    }

  
    public byte[] serialize() throws Exception {
        //#ifdef DEKSTOP_TOOL
        //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //# bos.write(super.serialize());
        //# Util.writeInt(bos, getScrollBarVisabilityType(),1);
        //# Util.writeColor(bos, getScrollBarColor());
        //# Util.writeInt(bos, getWidthPersent(),1);
        //# Util.writeInt(bos, getHideInterval(),1);
        //# Util.writeInt(bos,getScrollType(),1);
        //# Util.writeSignedInt(bos,getDefaultSelection(),2);
        //# Util.writeBoolean(bos, isGroupSelect());
        //# MenuSerilize.serialize(childrens, bos);
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
       super.deserialize(bis);
       setScrollBarVisabilityType(Util.readInt(bis,1));
       setScrollBarColor(Util.readColor(bis));
       setWidthPersent(Util.readInt(bis,1));
       setHideInterval(Util.readInt(bis,1));
       setScrollType(Util.readInt(bis,1));
       setDefaultSelection(Util.readSignedInt(bis,2));
       setGroupSelect(Util.readBoolean(bis));
       childrens = (Vector)MenuSerilize.deserialize(bis, MenuSerilize.getInstance());
        for (int i = 0; i < childrens.size(); i++) {
            ((Control)childrens.elementAt(i)).setParent(this);
        }
       bis.close();
       return null;
    }

 
    public void port() {
        super.port();
        for (int i = 0; i < childrens.size(); i++) {
            Control object = (Control)childrens.elementAt(i);
            object.port();
            if(object.getRelativeLocation() != null)
            {
                object.getRelativeLocation().port();
            }
            if(object.getLayout() != null)
            {
                object.getLayout().port();
            }
            if(object.getStartAnimation() != null)
            {
                object.getStartAnimation().port();
            }
            if(object.getEndAnimation() != null)
            {
                object.getEndAnimation().port();
            }
        }
    }

    
    public void cleanup() {
        super.cleanup();
        for (int i = 0; i < getSize(); i++) {
            ((Control)childrens.elementAt(i)).cleanup();
        }
        childrens .removeAllElements();
        selector = null;
    }

   
   
           
}
