package com.appon.gamehelper;


import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.view.WindowManager;

import com.appon.game.MyGameCanvas;



public abstract class GameCanvas {

	public static boolean shutDown=false;
	protected static Context context;
	protected DrawView parentView;
	
	public static Context getContext() {
		return context;
	}
	
	protected GameCanvas(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
	}
	public void setParent(DrawView parent) {
		this.parentView = parent;
		
   }


	public abstract void paint(Canvas canvas);
	public abstract void pointerPressed(int x,int y);
	public abstract void pointerReleased(int x,int y);
	public abstract void pointerDragged(int x,int y);
	public abstract void hideNotify();
	public abstract void showNotify();
	public abstract void update();
	
    public static int getWidth()
    {
    	Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	return Math.max(display.getWidth(),display.getHeight());
    }
    public static int getHeight()
    {
    	Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	return Math.min(display.getWidth(),display.getHeight());

    }

    public void shutDown()
	{
		shutDown = true;
	}
    
  
    public static GameCanvas getNewInstance(Context context)
	{
		return MyGameCanvas.getInstance(context);
	}

	
}

