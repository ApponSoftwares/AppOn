package com.appon.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.fontstyle.FontStyleGenerator;
import com.appon.gamehelper.GameCanvas;
import com.appon.gtantra.GFont;
import com.appon.gtantra.GraphicsUtil;
import com.appon.menu.MainMenu;
import com.appon.menu.PauseMenu;

public class MyGameCanvas extends GameCanvas{
	private static MyGameCanvas myGameCanvas=null;
	private static int myGameCanvasState;
	public Paint paintCanvas =new Paint();
	private int logoTime=0;
	private final int maxLogoTime=50;
	private int loadingCount=0;

	protected MyGameCanvas(Context context) {
		super(context);
	}
	public static MyGameCanvas getInstance(Context context){
		if(myGameCanvas == null){
			myGameCanvas =new MyGameCanvas(context);
		}
		return myGameCanvas; 
	}
	public static MyGameCanvas getInstance(){
		if(myGameCanvas == null){
			myGameCanvas =new MyGameCanvas(context);
		}
		return myGameCanvas; 
	}
	@Override
	public void update() {
		//update game
		switch (myGameCanvasState) {
		case MyGameConstants.MYGAME_CANVAS_LOGO_STATE:
			loadAtLogo(logoTime);
			logoTime++;
			if(logoTime>=maxLogoTime){
				MyGameConstants.APPON_LOGO.clear();
				setMyGameCanvasState(MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE);
			}
			break;
		case MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE:
			MainMenu.getInstance().update();
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMELOAD_STATE:
			loadGame();
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEUNLOAD_STATE:
			unLoadGame();
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEPLAY_STATE:
			MyGameEngine.getInstance().updateMyGameEngine();
			break;	
		}
	}

	private void loadGame() {

		switch (loadingCount) {
		case 0:
			MyGameEngine.getInstance().setMovingObject(new MovingObject());
			MyGameEngine.getInstance().getMovingObject().load();
			loadingCount++;
			break;
		case 1:
			PauseMenu.getInstance().load();
			loadingCount++;
			break;
		case 2:
			MyGameConstants.PAUSE.loadImage();
			loadingCount++;
			break;	
		case 10:
			loadingCount=0;
			MyGameEngine.setMyGameEngineState(MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE);
			MyGameCanvas.setMyGameCanvasState(MyGameConstants.MYGAME_CANVAS_GAMEPLAY_STATE);
			break;	
		default:
			loadingCount++;
			break;
		}
	}
	private void unLoadGame() {

		switch (loadingCount) {
		case 0:
			MyGameEngine.getInstance().getMovingObject().unLoad();
			MyGameEngine.getInstance().setMovingObject(null);
			loadingCount++;
			break;
		case 1:
			PauseMenu.getInstance().unload();
			loadingCount++;
			break;
		case 2:
			MyGameConstants.PAUSE.clear();
			loadingCount++;
			break;	
		case 10:
			loadingCount=0;
			MyGameCanvas.setMyGameCanvasState(MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE);
			break;	
		default:
			loadingCount++;
			break;	
		}
	}
	private void loadAtLogo(int timeValue) {
		switch (timeValue) {
		case 0:
			MyGameConstants.APPON_LOGO.loadImage();
			break;
		case 1:
			MyGameConstants.port();
			break;	
		case 2:
			MyGameConstants.Font= new GFont(FontStyleGenerator.COLOR_ID_WHITE, "IMPACT_0.TTF", MyGameActivity.getInstance());
			break;	
		case 3:
			MainMenu.getInstance().load();
			break;

		}
	}
	@Override
	public void paint(Canvas canvas) {
		paint(canvas,paintCanvas);
	}		

	protected void paint(Canvas c, Paint paintObject) {
		//paint game
		switch (myGameCanvasState) {
		case MyGameConstants.MYGAME_CANVAS_LOGO_STATE:
			paintLogo(c,paintObject);
			break;
		case MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE:
			MainMenu.getInstance().paint(c, paintObject);
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMELOAD_STATE:
			GraphicsUtil.fillRect(0, 0, MyGameConstants.SCREEN_WIDTH,MyGameConstants.SCREEN_HEIGHT, 0xFF000000, c, paintObject);
			MyGameConstants.Font.setColor(FontStyleGenerator.COLOR_ID_WHITE);
			MyGameConstants.Font.drawPage(c, "Game Loading...", 0, 0, MyGameConstants.SCREEN_WIDTH, MyGameConstants.SCREEN_HEIGHT, GraphicsUtil.HCENTER|GraphicsUtil.BASELINE, paintObject);

			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEUNLOAD_STATE:
			GraphicsUtil.fillRect(0, 0, MyGameConstants.SCREEN_WIDTH,MyGameConstants.SCREEN_HEIGHT, 0xFF000000, c, paintObject);
			MyGameConstants.Font.setColor(FontStyleGenerator.COLOR_ID_WHITE);
			MyGameConstants.Font.drawPage(c, "Menu Loading...", 0, 0, MyGameConstants.SCREEN_WIDTH, MyGameConstants.SCREEN_HEIGHT, GraphicsUtil.HCENTER|GraphicsUtil.BASELINE, paintObject);
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEPLAY_STATE:
			MyGameEngine.getInstance().paintMyGameEngine(c,paintObject);
			break;
		}
	}

	private void paintLogo(Canvas c, Paint paintObject) {
		GraphicsUtil.fillRect(0, 0, MyGameConstants.SCREEN_WIDTH,MyGameConstants.SCREEN_HEIGHT, 0xFFFFFFFF, c, paintObject);
		GraphicsUtil.drawBitmap(c, MyGameConstants.APPON_LOGO.getImage(), (MyGameConstants.SCREEN_WIDTH-MyGameConstants.APPON_LOGO.getWidth())>>1,(MyGameConstants.SCREEN_HEIGHT-MyGameConstants.APPON_LOGO.getHeight())>>1, 0, paintObject);
	}
	@Override
	public void pointerPressed(int x, int y) {
		switch (myGameCanvasState) {
		case MyGameConstants.MYGAME_CANVAS_LOGO_STATE:
			break;
		case MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE:
			MainMenu.getInstance().pointerPressed(x, y);
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEPLAY_STATE:
			MyGameEngine.getInstance().pointerPressed(x,y);
			break;	
		}
	}
	@Override
	public void pointerReleased(int x, int y) {
		switch (myGameCanvasState) {
		case MyGameConstants.MYGAME_CANVAS_LOGO_STATE:
			break;
		case MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE:
			MainMenu.getInstance().pointerReleased(x, y);
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEPLAY_STATE:
			MyGameEngine.getInstance().pointerReleased(x,y);
			break;	
		}
	}
	@Override
	public void pointerDragged(int x, int y) {
		switch (myGameCanvasState) {
		case MyGameConstants.MYGAME_CANVAS_LOGO_STATE:
			break;
		case MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE:
			MainMenu.getInstance().pointerDragged(x, y);
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEPLAY_STATE:
			MyGameEngine.getInstance().pointerDragged(x,y);
			break;	
		}
	}


	@Override
	public void hideNotify() {
		// on Game hide 

	}
	@Override
	public void showNotify() {
		// on Game Shown

	}


	public static void setMyGameCanvasState(int myGameCanvasState) {
		MyGameCanvas.myGameCanvasState = myGameCanvasState;
	}

	public static int getMyGameCanvasState() {
		return myGameCanvasState;
	}
}
