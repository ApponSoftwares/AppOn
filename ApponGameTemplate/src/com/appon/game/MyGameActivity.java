package com.appon.game;


import com.appon.gamehelper.GameActivity;

public class MyGameActivity extends GameActivity {


	private static MyGameActivity activity;

	public MyGameActivity(){
		activity =this;
	}

	public static MyGameActivity getInstance(){
		return activity;
	}

	public void destroyApp() {
		MyGameActivity.getInstance().closeGame();
	}

	@Override
	public void onBackPressed() {
		//handle Back Button
		switch (MyGameCanvas.getMyGameCanvasState()) {
		case MyGameConstants.MYGAME_CANVAS_MAINMENU_STATE:
			destroyApp();
			break;
		case MyGameConstants.MYGAME_CANVAS_GAMEPLAY_STATE:
		{
			switch (MyGameEngine.getMyGameEngineState()) {
			case MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE:
				MyGameEngine.setMyGameEngineState(MyGameConstants.MYGAME_ENGINE_PAUSE_STATE);
				break;
			case MyGameConstants.MYGAME_ENGINE_PAUSE_STATE:
				MyGameEngine.setMyGameEngineState(MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE);
				break;	
			}
		}
		break;	

		}
	}


}
