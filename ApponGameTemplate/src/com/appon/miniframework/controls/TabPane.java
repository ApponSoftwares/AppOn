/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Canvas;
import android.graphics.Paint;
//#endif

import com.appon.miniframework.Container;
import com.appon.miniframework.Control;
import com.appon.miniframework.EventManager;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.ScrollableContainer;
import com.appon.miniframework.exception.NotValidParameter;
import com.appon.miniframework.exception.OperationNotSupported;
//#ifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Image;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID

/**
 *
 * @author acer
 */
public class TabPane extends Container{
    ScrollableContainer container;
    TabButton tabButton;
    public TabPane(int id,int containerId) {
        super(id);
        tabButton = new TabButton();
        container = new ScrollableContainer(containerId);
        container.setParent(this);
    }
    public TabPane(int id) {
        super(id);
        tabButton = new TabButton();
    }
     public void takeScrollBackup() {
       container.takeScrollBackup();
    }

   
    public void restoreScrollBackup() {
        container.restoreScrollBackup();
    }
    public void setEventManager(EventManager manager)
    {
        super.setEventManager(manager);
        container.setEventManager(manager);
    }
    
    public void port() {
        super.port();
        container.port();
    }
    
    
    public void resize() {
        super.resize();
        tabButton.resize();
        container.setSizeSettingX(CUSTOM_SIZE);
        container.setSizeSettingY(CUSTOM_SIZE);
        container.setWidth(getWidth());
        container.setHeight(getHeight() - tabButton.getHeight());
        container.setPoistion(0, tabButton.getHeight());
        container.resize();
    }

    public ScrollableContainer getContainer() {
        return container;
    }

    public TabButton getTabButton() {
        return tabButton;
    }
    
    
    public int getSize() {
       return 1;
    }
    
    public Control getChild(int index) {
        if(index == 0)
            return container;
        else
            throw new NotValidParameter();
    }

    
    public int getScrolledX() {
        return 0;
    }

    
    public int getScrolledY() {
        return 0;
    }

    
    public int getChildrenIndex(Control control) {
        if(control.equals(container))
        {
            return 0;
        }else
            throw new NotValidParameter();
    }

    
    public void removeChildren(Control children) {
        throw new OperationNotSupported();
    }

    
    public void addChildrenAt(Control control, int index) {
        throw new OperationNotSupported();
    }

    
    public void resetSelecton() {
       
    }

    
    public void selectChild(int index,boolean isFromTouch) {
       
    }

    
    public void stretchDimentions() {
       container.stretchDimentions();
    }

    
    //#ifdef ANDROID
    public void paint(Canvas g, Paint paintObject)
    //#else
    //# public void paint(Graphics g)
    //#endif
    {
    	   //#ifdef ANDROID
    	 tabButton.paint(g,false,paintObject);
         container.paintUI(g,paintObject);
        //#else
         //# tabButton.paint(g,false);
        //# container.paintUI(g);
        //#endif
       
    }
    
    
    public int getClassCode() {
        return MenuSerilize.CONTROL_TAB_PANE_CONTROL_TYPE;
    }

    
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(super.serialize());
        bos.write(tabButton.serialize());
        MenuSerilize.getInstance().serialize(container, bos);
        return bos.toByteArray();
    }

    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
        super.deserialize(bis);
        tabButton.deserialize(bis);
        container = (ScrollableContainer)MenuSerilize.getInstance().deserialize(bis, MenuSerilize.getInstance());
        container.setParent(this);
        return bis;
    }

    
    public String toString() {
        return "TabPane: "+getId();
    }
   
    public int getFinalX() {
    	return tabButton.getX();
    }
    public int getFinalY() {
    	return tabButton.getY();
    }
    public int getFinalWidth() {
    	return tabButton.getWidth();
    }
    public int getFinalHeight() {
    	return tabButton.getHeight();
    }
    
}
