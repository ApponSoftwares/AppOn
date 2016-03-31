/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;

/**
 *
 * @author acer
 */
public abstract class Container extends Control{

    public Container(int id) {
        super(id);
    }
    public abstract int getSize();
    public abstract Control getChild(int index);
    public abstract int getScrolledX();
    public abstract int getScrolledY();
    public abstract int getChildrenIndex(Control control);
    public abstract void removeChildren(Control children);
    public abstract void addChildrenAt(Control control,int index);
    public abstract void resetSelecton();
    public abstract void selectChild(int index,boolean isFromTouch);
    public abstract void stretchDimentions();
    public abstract void takeScrollBackup();
    public abstract void restoreScrollBackup();
    public Container findAndSelectOwnChild(int id)
    {
        //searching own
        for (int i = 0; i < getSize(); i++) {
            if(getChild(i).getId() == id)
            {
                selectChild(i,false);
                return this;
            } 
            if(getChild(i) instanceof Container)
            {
                Container value = ((Container)getChild(i)).findAndSelectOwnChild(id);
                if(value != null)
                {
                    return value;
                }
            }
            
        }
        return null;
    }
  
}
