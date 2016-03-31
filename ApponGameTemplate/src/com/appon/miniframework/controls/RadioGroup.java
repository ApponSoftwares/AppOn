/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework.controls;

import java.util.Vector;

/**
 *
 * @author dell
 */
public class RadioGroup {
    private Vector listOfToggleControls = new Vector();
    private int selectedIndex;
    public void addToggleControl(ToggleIconControl control)
    {
        listOfToggleControls.addElement(control);
        control.setRadioGroup(this);
    }
    
    public void selectControl(int index)
    {
        selectedIndex = index;
        reset();
    }
    public void selectControl(ToggleIconControl control)
    {
        for (int i = 0; i < listOfToggleControls.size(); i++) {
            ToggleIconControl object = (ToggleIconControl)listOfToggleControls.elementAt(i);
            if(object.equals(control))
            {
                selectedIndex = i;
                control.setToggleSelected(true);
            }
        }
        reset();
    }
    public int getSelectedControlIndex()
    {
        return selectedIndex;
    }
    
    protected void reset()
    {
        for (int i = 0; i < listOfToggleControls.size(); i++) {
            ToggleIconControl object = (ToggleIconControl)listOfToggleControls.elementAt(i);
            object.setToggleSelected(i == selectedIndex);
        }
    }
    
}
