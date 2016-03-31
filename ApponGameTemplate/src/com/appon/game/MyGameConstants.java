package com.appon.game;

import com.appon.gamehelper.ImageLoadInfo;
import com.appon.gamehelper.Resources;
import com.appon.gamehelper.Util;
import com.appon.gtantra.GFont;

public class MyGameConstants {

	public static final int MASTER_SCREEN_WIDTH = 1280;
	public static final int MASTER_SCREEN_HEIGHT = 800;
	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;

	public static int MOVING_OBJECT_WIDTH = 30;
	public static int MOVING_OBJECT_HEIGHT = 30;
	public static int Object_SPEED=20;

	public static int PAUSE_X=3;
	public static int PAUSE_Y=2;

	public static final int DIRECTION_LEFT = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_UP = 2;
	public static final int DIRECTION_DOWN = 3;

	public static GFont Font=null;


	public static final ImageLoadInfo APPON_LOGO = new ImageLoadInfo("appon_logo.jpg", Resources.RESIZE_NONE);
	public static final ImageLoadInfo BUTTON_BG = new ImageLoadInfo("button-icon.png", Resources.RESIZE_NONE);
	public static final ImageLoadInfo HOME = new ImageLoadInfo("home_icon.png", Resources.RESIZE_NONE);
	public static final ImageLoadInfo PLAY = new ImageLoadInfo("play.png", Resources.RESIZE_NONE);
	public static final ImageLoadInfo EXIT = new ImageLoadInfo("exit.png", Resources.RESIZE_NONE);
	public static final ImageLoadInfo PAUSE = new ImageLoadInfo("pause.png", Resources.RESIZE_NONE);


	//Game States
	public static final int MYGAME_CANVAS_LOGO_STATE = 0;
	public static final int MYGAME_CANVAS_MAINMENU_STATE = MYGAME_CANVAS_LOGO_STATE+1;
	public static final int MYGAME_CANVAS_GAMEPLAY_STATE = MYGAME_CANVAS_MAINMENU_STATE+1;
	public static final int MYGAME_ENGINE_GAMEPLAY_STATE = MYGAME_CANVAS_GAMEPLAY_STATE+1;
	public static final int MYGAME_ENGINE_PAUSE_STATE = MYGAME_ENGINE_GAMEPLAY_STATE+1;
	public static final int MYGAME_CANVAS_GAMELOAD_STATE = MYGAME_ENGINE_PAUSE_STATE+1;
	public static final int MYGAME_CANVAS_GAMEUNLOAD_STATE = MYGAME_CANVAS_GAMELOAD_STATE+1;

	public static void port()
	{
		int xScale = ((100 * (SCREEN_WIDTH - MASTER_SCREEN_WIDTH) )/MASTER_SCREEN_WIDTH) ;
		int yScale = ((100 * (SCREEN_HEIGHT - MASTER_SCREEN_HEIGHT) )/MASTER_SCREEN_HEIGHT) ;
		MOVING_OBJECT_WIDTH = Util.getScaleValue(MOVING_OBJECT_WIDTH,yScale);
		MOVING_OBJECT_HEIGHT = Util.getScaleValue(MOVING_OBJECT_HEIGHT,yScale);
		Object_SPEED = Util.getScaleValue(Object_SPEED,yScale);
		PAUSE_X=Util.getScaleValue(PAUSE_X,xScale);
		PAUSE_Y=SCREEN_HEIGHT-Util.getScaleValue(PAUSE_Y,yScale);
	}

}
