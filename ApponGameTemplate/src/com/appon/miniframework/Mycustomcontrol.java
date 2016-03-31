/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.miniframework;

import com.appon.miniframework.controls.CustomControl;

import android.graphics.Canvas;
import android.graphics.Paint;



/**
 *
 * @author user
 */
public class Mycustomcontrol extends CustomControl{
	
    public int id;
    public int identifier;
    public boolean isControlSelected;        
    public Mycustomcontrol(int id,int identifier) {
    	this.id=id;
        this.identifier=identifier;
    }
    public void paint(Canvas g, Paint paintObject) {
    	
    	
    }
    public int getPreferredHeight() {
    	return 1;
    }
    public int getPreferredWidth() { 
    	return 1;
     }
    public int getClassCode() {
       return 0;
    }
	@Override
	public void callpointerPressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void callpointerRealease(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
}
