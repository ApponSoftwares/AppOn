/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.miniframework.Container;
import com.appon.miniframework.Control;
import com.appon.miniframework.EventManager;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.ScrollableContainer;
import com.appon.miniframework.Settings;
import com.appon.miniframework.Util;
import com.appon.miniframework.exception.OperationNotSupported;
//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
//#endif
/**
 *
 * @author acer
 */
public class TabControl extends Container{
    private Vector tabPane = new Vector();
    private int selectedIndex = 0;
    private boolean isSelectionOnTabButton = false;
    int maxTabButtonHeight = 0;
    public static final int LEFT_ALLIGNMENT = 0;
    public static final int RIGHT_ALLIGNMENT = 1;
    public static final int CENTER_ALLIGNMENT = 2;
    private int tabButtonAllignment = CENTER_ALLIGNMENT;
    private int startX;
    private int padding;
    private int highlightButtonIndex = 0; 
    public TabControl(int id) {
        super(id);
    }
    public void setEventManager(EventManager manager)
    {
        super.setEventManager(manager);
        for (int idx = 0; idx < tabPane.size(); idx++) {
           Control control = (Control)tabPane.elementAt(idx);
           control.setEventManager(manager);
        }
    }
    public TabButton getSelectedTabButton()
    {
        if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
        {
            return getTabButton(selectedIndex);
        }
        return null;
    }

    
    public void port() {
        super.port();
         for (int i = 0; i < tabPane.size(); i++) {
            TabPane pane = (TabPane)tabPane.elementAt(i);
            pane.port();
        }
    }
    
    public int getTabButtonPanelHeight()
    {
        return maxTabButtonHeight;
    }
    public ScrollableContainer getSelectedTabBody()
    {
        if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
        {
            return getContainer(selectedIndex);
        }
        return null;
    }
    public TabPane getSelectedTabPane()
    {
        if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
        {
            return ((TabPane)tabPane.elementAt(selectedIndex));
        }
        return null;
    }

   
    public void takeScrollBackup() {
        for (int i = 0; i < tabPane.size(); i++) {
            TabPane object = (TabPane)tabPane.elementAt(i);
            object.takeScrollBackup();
        }
    }

   
    public void restoreScrollBackup() {
         for (int i = 0; i < tabPane.size(); i++) {
            TabPane object = (TabPane)tabPane.elementAt(i);
            object.restoreScrollBackup();
        }
    }
    
    private ScrollableContainer getContainer(int index)
    {
         return ((TabPane)tabPane.elementAt(index)).getContainer();
    }
    private TabButton getTabButton(int index)
    {
         return ((TabPane)tabPane.elementAt(index)).getTabButton();
    }
    
    //#ifdef ANDROID
    public void paint(Canvas g, Paint paintObject)
    //#else
    //# public void paint(Graphics g)
    //#endif
    {
//        isSelectionOnTabButton = isSelectionOnTabButton && isSelected();
        
       
        if(selectedIndex != -1 && selectedIndex < tabPane.size())
        {
        	//#ifdef ANDROID
            getContainer(selectedIndex).paintUI(g,paintObject);
            //#else
            //# getContainer(selectedIndex).paintUI(g);
            //#endif
        }
      
        for (int i = 0; i < tabPane.size(); i++) {
            TabButton button = getTabButton(i);
            if(selectedIndex != i)
            {
//                button.paint(g, getStartX(i), maxTabButtonHeight - button.getHeight(), false);
            	//#ifdef ANDROID
                button.paint(g, (i == highlightButtonIndex) && isSelectionOnTabButton && isSelected(),paintObject);
                //#else
                //# button.paint(g, (i == highlightButtonIndex) && isSelectionOnTabButton && isSelected());
                //#endif
            }
        }
        if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
        {
            TabButton button = getTabButton(selectedIndex);

          //#ifdef ANDROID
            button.paint(g, (this.selectedIndex == highlightButtonIndex) && isSelectionOnTabButton && isSelected(),paintObject);
            //#else
            //# button.paint(g, (this.selectedIndex == highlightButtonIndex) && isSelectionOnTabButton && isSelected());
            //#endif
             
        }
       
    }
    public int getSelectedIndex() {
        return selectedIndex;
    }
    public void selectTabPane(TabPane pane)
    {
        int index = tabPane.indexOf(pane);
        if(index != -1)
        {
            setSelectedIndex(index);
        }
        isSelectionOnTabButton = false;
    }
     public boolean keyPressed(int keycode, int gameKey) {
        if(isSelectionOnTabButton && tabPane.size() > 0)
        {
            int backupIndex = this.selectedIndex;
            if(gameKey == Settings.DOWN)
            {
                isSelectionOnTabButton = false;
                getContainer(this.selectedIndex).setSelected(true);
                return true;
            }else if(gameKey == Settings.LEFT)
            {
                backupIndex--;
                if(backupIndex < 0)
                {
                    backupIndex = tabPane.size() - 1;
                }
            }else if(gameKey == Settings.RIGHT)
            {
                backupIndex++;
                if(backupIndex > tabPane.size() - 1)
                {
                    backupIndex = 0;
                }
            }
            if(selectedIndex != backupIndex)
            {
                setSelectedIndex(backupIndex);
            }
        }else{
            if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
            {
               Container c = getContainer(this.selectedIndex);
               boolean handle = c.keyPressed(keycode, gameKey);
               if(!handle && gameKey == Settings.UP)
               {
                    handle = true;
                    getContainer(this.selectedIndex).setSelected(false);
                    isSelectionOnTabButton = true;
               }else if(!handle && (gameKey == Settings.LEFT ||gameKey == Settings.RIGHT ) && tabPane.size() > 1)
               {
                   isSelectionOnTabButton = true;
                   if((gameKey == Settings.LEFT && selectedIndex > 0 )||(gameKey == Settings.RIGHT && selectedIndex < tabPane.size() - 1 ))
                   {
                      getContainer(this.selectedIndex).setSelected(false); 
                      if(gameKey == Settings.LEFT)
                      {
                           getContainer(this.selectedIndex - 1).setSelected(true); 
                      }else{
                           getContainer(this.selectedIndex + 1).setSelected(true); 
                      }
                   }    
                   return  keyPressed(keycode, gameKey);
               }
               return handle;
            }
            
        }
        return false;
    }
    public boolean pointerPressed(int x, int y) {
        x = x - getX();
        y = y - getY();
       if(Util.isInRect(0, 0, getWidth(), maxTabButtonHeight, x, y))
       {
            for (int i = 0; i < tabPane.size(); i++) {
                TabButton button = getTabButton(i);
                if(Util.isInRect(button.getX(),0,button.getWidth(),maxTabButtonHeight,x,y) )
                {
                    highlightButtonIndex = i;
                    isSelectionOnTabButton = true;
                    return true;
                }
            }
       }else if(Util.isInRect(0, maxTabButtonHeight, getWidth(), getHeight() - maxTabButtonHeight, x, y)){
            isSelectionOnTabButton = false;
            if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
            {
                return getContainer(this.selectedIndex).pointerPressed(x, y);
            }
       }
       return false;
    }

    
    public boolean pointerReleased(int x, int y) {
        x = x - getX();
        y = y - getY();
//        System.out.println("x: "+x + " y: "+y + " getWidth(): "+getWidth() + " height: "+maxTabButtonHeight);
       if(Util.isInRect(0, 0, getWidth(), maxTabButtonHeight, x, y))
       {
            for (int i = 0; i < tabPane.size(); i++) {
                TabButton button = getTabButton(i);
//                System.out.println("i: "+i+" x: "+button.getX() + " width: "+button.getWidth());
                if( i != this.selectedIndex && Util.isInRect(button.getX(),0,button.getWidth(),maxTabButtonHeight,x,y)&& !ScrollableContainer.fromPointerDrag)
                {
                   
                    setSelectedIndex(i);
                    return true;
                }
            }
          
       }else if(Util.isInRect(0, maxTabButtonHeight, getWidth(), getHeight() - maxTabButtonHeight, x, y)){
            if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
            {
                isSelectionOnTabButton = false;
                return getContainer(this.selectedIndex).pointerReleased(x, y);
            }
       }
       return false;
    }

    
    public boolean pointerDragged(int x, int y) {
        x = x - getX();
        y = y - getY();
       if(Util.isInRect(0, 0, getWidth(), maxTabButtonHeight, x, y))
       {
          

       }else if(Util.isInRect(0, maxTabButtonHeight, getWidth(), getHeight() - maxTabButtonHeight, x, y)){
            if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
            {
                return getContainer(this.selectedIndex).pointerDragged(x, y);
            }
       }
       return false;
    }
   
    public void setSelectedIndex(int selectedIndex) {
//        if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
//        {
//            getContainer(this.selectedIndex).hideNotify();
//        }
        this.selectedIndex = selectedIndex;
        for (int i = 0; i < tabPane.size(); i++) {
            TabButton button = getTabButton(i);
            if(selectedIndex == i)
            {
                button.setSelected(true);
            }else{
                button.setSelected(false);
            }
        }
        if(this.selectedIndex != -1 && this.selectedIndex < tabPane.size())
        {
         
            getContainer(this.selectedIndex).setSelected(false);
        }
        highlightButtonIndex =  this.selectedIndex;
    }

    
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        
        if(selected)
        {
            if(isSelectionOnTabButton)
            {
                getSelectedTabBody().setSelected(false);
            }else{
                getSelectedTabBody().setSelected(true);
            }
           
        }else{
              
              getSelectedTabBody().setSelected(false);
        }
       
    }
    
    
    public int getClassCode() {
        return MenuSerilize.CONTROL_TAB_CONTROL_TYPE;
    }
    public void addNewTab(int tabPaneId,int tabBodyContainerId)
    {
        TabPane pane = new TabPane(tabPaneId, tabBodyContainerId);
        pane.setParent(this);
        tabPane.addElement(pane);
    }

    
    public void showNotify() {
        super.showNotify();
        for (int i = 0; i < tabPane.size(); i++) {
             Container container = getContainer(i);
             if(i != selectedIndex)
                container.showNotify();
        }
         
        if(tabPane.size() > 0 && selectedIndex != -1)
        {
            getContainer(this.selectedIndex).showNotify();
            setSelectedIndex(selectedIndex);
        }
        isSelectionOnTabButton = true;
//        if(selectedIndex != -1 && selectedIndex < tabButtons.size())
//        {
//            ((Container)tabContainer.elementAt(selectedIndex)).showNotify();
//        }
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getTabButtonAllignment() {
        return tabButtonAllignment;
    }

    public void setTabButtonAllignment(int tabButtonAllignment) {
        this.tabButtonAllignment = tabButtonAllignment;
    }

    
    public void hideNotify() {
        super.hideNotify();
        for (int i = 0; i < tabPane.size(); i++) {
             Container container = getContainer(i);
             if(i != selectedIndex)
                container.hideNotify();
        }
        if(selectedIndex != -1 && selectedIndex < tabPane.size())
        {
            getContainer(selectedIndex).hideNotify();
        }
    }

    private int getStartX(int index)
    {
        int _x = startX;
        if(index >= 0 && index < tabPane.size())
        {
          
//            if(tabButtonAllignment == RIGHT_ALLIGNMENT)
//            {
//                 for (int i = tabPane.size() - 1; i >= index; i--) {
//                    TabButton button = getTabButton(i);
//                    _x -= button.getWidth();
//                }
//                 if(index > 0)
//                 {
//                     _x -= (padding * (tabPane.size() - index));
//                 }
//            }else{
                
                 for (int i = 0; i < index; i++) {
                    TabButton button = getTabButton(i);
                    _x += button.getWidth();
                 }
                 if(index > 0)
                 {
                     _x += (padding * index);
                 }
//            }
        }
        return _x;
    }
    
    public void resize() {
        super.resize();
        int _width = 0;
        for (int i = 0; i < tabPane.size(); i++) {
            TabButton button = getTabButton(i);
            button.resize();
            if(maxTabButtonHeight < button.getHeight())
            {
                maxTabButtonHeight = button.getHeight();
            }
            _width += button.getWidth();
        }
        if(tabPane.size() > 0)
        {
            _width += padding * (tabPane.size() - 1);
        }
        if(tabButtonAllignment == CENTER_ALLIGNMENT)
        {
            startX = (getWidth() - _width) >> 1;
        }else if(tabButtonAllignment == LEFT_ALLIGNMENT){
            startX = 0;
        }else{
            startX = getWidth() - _width;
        }
        for (int i = 0; i < tabPane.size(); i++) {
            Container container = getContainer(i);
            container.setSizeSettingX(CUSTOM_SIZE);
            container.setSizeSettingY(CUSTOM_SIZE);
            container.setWidth(getWidth());
            container.setHeight(getHeight() - maxTabButtonHeight);
            container.resize();
            container.setPoistion(0, maxTabButtonHeight);
        }
        
        for (int i = 0; i < tabPane.size(); i++) {
            TabButton button = getTabButton(i);
            button.setX(getStartX(i));
            button.setY(maxTabButtonHeight - button.getHeight());
        }
    }

    
    public int getSize() {
        return tabPane.size();
//          return 1;
    }

    
    public Control getChild(int index) {
//        if(index != selectedIndex)
//        {
//            
//        }
       return (Control)tabPane.elementAt(index);
//        return getSelectedTabPane();
    }

    
    public int getScrolledX() {
        return 0;
    }

    
    public int getScrolledY() {
        return 0;
    }

    
    public int getChildrenIndex(Control control) {
        return tabPane.indexOf(control);
    }

    
    public void removeChildren(Control children) {
        int index = getChildrenIndex(children);
        if(index != -1)
        {
            tabPane.removeElementAt(index);
        }
        if(getSelectedIndex() >= tabPane.size())
        {
            setSelectedIndex(0);
        }
    }

    
    public void addChildrenAt(Control control, int index) {
        if(!(control instanceof TabPane))
        {
            throw new OperationNotSupported();
        }
        tabPane.insertElementAt(control,index);
    }

    
    public void resetSelecton() {
        
    }

    @Override
    public void setSelected(boolean selected, boolean isFromTouch) {
        super.setSelected(selected, isFromTouch);
        if(!isSelectionOnTabButton)
        {
            getContainer(selectedIndex).setSelected(selected, isFromTouch);
        }
    }

    
    public void selectChild(int index,boolean isFromTouch) {
       setSelectedIndex(index);
    }

    
    public void stretchDimentions() {
        for (int i = 0; i < tabPane.size(); i++) {
            getContainer(i).stretchDimentions();
        }
    }

    
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        MenuSerilize.serialize(tabPane, bos);
        Util.writeSignedInt(bos, getSelectedIndex(), 1);
        Util.writeInt(bos, getTabButtonAllignment(), 1);
        Util.writeSignedInt(bos, getPadding(), 2);
        return bos.toByteArray();
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
        super.deserialize(bis);
        tabPane = (Vector)MenuSerilize.deserialize(bis, MenuSerilize.getInstance());
        for (int i = 0; i < tabPane.size(); i++) {
            ((TabPane)tabPane.elementAt(i)).setParent(this);
        }
        selectedIndex = Util.readSignedInt(bis, 1);
        setTabButtonAllignment(Util.readInt(bis, 1));
        setPadding(Util.readSignedInt(bis, 2));
        return bis;
    }
     
    public String toString() {
        return "TabControl: "+getId();
    }
    
}
