package com.appon.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.SyncStateContract.Constants;

import com.appon.gamehelper.Util;
import com.appon.gtantra.GraphicsUtil;
import com.appon.menu.PauseMenu;

public class MyGameEngine {

	private static MyGameEngine myGameEngine;
	private static int myGameEngineState;
	private MovingObject movingObject=null;
	private MyGameEngine() {
		// TODO Auto-generated constructor stub
	}

	public static MyGameEngine getInstance(){
		if(myGameEngine == null){
			myGameEngine =new MyGameEngine();
		}
		return myGameEngine; 
	}

	public void setMovingObject(MovingObject snake) {
		this.movingObject = snake;
	}
	public MovingObject getMovingObject() {
		return movingObject;
	}
	public static void setMyGameEngineState(int myGameEngineState) {
		MyGameEngine.myGameEngineState = myGameEngineState;
	}
	public static int getMyGameEngineState() {
		return myGameEngineState;
	}

	public void updateMyGameEngine() {
		switch (myGameEngineState) {
		case MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE:
			if(getMovingObject()!=null)
				getMovingObject().update();
			break;
		case MyGameConstants.MYGAME_ENGINE_PAUSE_STATE:
			PauseMenu.getInstance().update();
			break;	
		}
	}
	public void paintMyGameEngine(Canvas c, Paint paintObject) {
		switch (myGameEngineState) {
		case MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE:
			paintGamplay(c,paintObject);
			paintPauseButton(c, MyGameConstants.PAUSE_X, MyGameConstants.PAUSE_Y, MyGameConstants.BUTTON_BG.getWidth(), MyGameConstants.BUTTON_BG.getHeight(), paintObject);
			break;
		case MyGameConstants.MYGAME_ENGINE_PAUSE_STATE:
			paintGamplay(c,paintObject);
			PauseMenu.getInstance().paint(c, paintObject);
			break;	
		}

	}
	private void paintGamplay(Canvas c, Paint paintObject) {
		GraphicsUtil.fillRect(0, 0, MyGameConstants.SCREEN_WIDTH,MyGameConstants.SCREEN_HEIGHT, 0xFFFFFFFF,c, paintObject);
		if(getMovingObject()!=null)
			getMovingObject().paint(c, paintObject);

	}

	private void paintPauseButton(Canvas g,int x,int y,int w,int h,Paint paintObject){
		y-=h;
		GraphicsUtil.drawBitmap(g, MyGameConstants.BUTTON_BG.getImage(), x, y, 0, paintObject);
		x+=((w-MyGameConstants.PAUSE.getWidth())>>1);
		y+=((h-MyGameConstants.PAUSE.getHeight())>>1);
		GraphicsUtil.drawBitmap(g, MyGameConstants.PAUSE.getImage(), x, y, 0, paintObject);
	}	

	public void pointerPressed(int x, int y) {
		switch (myGameEngineState) {
		case MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE:
			if(getMovingObject()!=null)
				getMovingObject().poiterPressed(x,y);
			if(Util.isInRect(MyGameConstants.PAUSE_X, MyGameConstants.PAUSE_Y-MyGameConstants.BUTTON_BG.getHeight(), MyGameConstants.BUTTON_BG.getWidth(), MyGameConstants.BUTTON_BG.getHeight(), x, y))
			{
				setMyGameEngineState(MyGameConstants.MYGAME_ENGINE_PAUSE_STATE);
			}
			break;
		case MyGameConstants.MYGAME_ENGINE_PAUSE_STATE:
			PauseMenu.getInstance().pointerPressed(x, y);
			break;	
		}
	}

	public void pointerReleased(int x, int y) {
		switch (myGameEngineState) {
		case MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE:
			if(getMovingObject()!=null)
				getMovingObject().poiterRealesed(x, y);;
				break;
		case MyGameConstants.MYGAME_ENGINE_PAUSE_STATE:
			PauseMenu.getInstance().pointerReleased(x, y);
			break;	
		}
	}

	public void pointerDragged(int x, int y) {
		switch (myGameEngineState) {
		case MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE:
			if(getMovingObject()!=null)
				getMovingObject().poiterDragged(x, y);
			break;
		case MyGameConstants.MYGAME_ENGINE_PAUSE_STATE:
			PauseMenu.getInstance().pointerDragged(x, y);
			break;	
		}
	}


}
