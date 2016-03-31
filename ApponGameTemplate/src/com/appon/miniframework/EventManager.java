/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.miniframework;

/**
 *
 * @author Swaroop Kumar
 */
public interface EventManager {
public static final int CONTROL_CLICKED = 0x0;    
public static final int SELECTION_CHANGED = 0x1;
public static final int STATE_CHANGED = 0x2;    
public static final int RADIO_GROUP_ITEAM_SELECTED = 0x3;
public static final int FIRE_PRESSED = 0x4;
public static final int START_ANIMATION_OVER = 0x5;
public static final int END_ANIMATION_OVER = 0x6;
public void event(Event event);
}
