package com.appon.menu;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.game.MyGameActivity;
import com.appon.game.MyGameCanvas;
import com.appon.game.MyGameConstants;
import com.appon.game.MyGameEngine;
import com.appon.gamehelper.GameActivity;
import com.appon.gtantra.GTantra;
import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.Event;
import com.appon.miniframework.EventManager;
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.ScrollableContainer;


public class PauseMenu {


	private static PauseMenu instance;
	private ScrollableContainer pauseMenu;


	private PauseMenu() {
		// TODO Auto-generated constructor stub
	} 
	public static PauseMenu getInstance(){
		if(instance == null){
			instance =new PauseMenu();
		}
		return instance;
	}
	public void update() {

	}
	public void paint(Canvas c, Paint paintObject) {
		pauseMenu.paintUI(c, paintObject);
	}
	private void loadPauseMenu() {

		//loading the menu.meunex
		MyGameConstants.BUTTON_BG.loadImage();
		MyGameConstants.PLAY.loadImage();
		MyGameConstants.HOME.loadImage();

		try {
			ResourceManager.getInstance().setImageResource(0,MyGameConstants.BUTTON_BG.getImage());
			ResourceManager.getInstance().setImageResource(1,MyGameConstants.HOME.getImage());
			ResourceManager.getInstance().setImageResource(2,MyGameConstants.PLAY.getImage());

			setContainerMenu( com.appon.miniframework.Util.loadContainer(GTantra.getFileByteData("/pauseMenu.menuex",GameActivity.getInstance()),1280, 800, MyGameConstants.SCREEN_WIDTH,MyGameConstants.SCREEN_HEIGHT,true));

			getContainerMenu().setEventManager(new EventManager() {
				public void event(Event event) {
					if(event.getEventId() == FIRE_PRESSED ||  event.getEventId() == CONTROL_CLICKED){
						switch (event.getSource().getId()) {
						case 1:
							MyGameCanvas.setMyGameCanvasState(MyGameConstants.MYGAME_CANVAS_GAMEUNLOAD_STATE);
							break;
						case 2:
							MyGameEngine.setMyGameEngineState(MyGameConstants.MYGAME_ENGINE_GAMEPLAY_STATE);
							break;
						}
					}
				}
			});
		} catch (Exception e) {

		}
		ResourceManager.getInstance().clear();  

	}


	private void setContainerMenu(ScrollableContainer loadContainer) {
		this.pauseMenu=loadContainer;
	}
	public ScrollableContainer getContainerMenu() {
		return this.pauseMenu;
	}
	public void load() {
		loadPauseMenu();		
	}

	public void unload() {
		this.pauseMenu=null;
	}
	public void pointerPressed(int x, int y) {
		this.pauseMenu.pointerPressed(x, y);
	}
	public void pointerReleased(int x, int y) {
		this.pauseMenu.pointerReleased(x, y);
	}
	public void pointerDragged(int x, int y) {
		this.pauseMenu.pointerDragged(x, y);
	}


}
