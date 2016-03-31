package com.appon.menu;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.game.MyGameActivity;
import com.appon.game.MyGameCanvas;
import com.appon.game.MyGameConstants;
import com.appon.gamehelper.GameActivity;
import com.appon.gtantra.GTantra;
import com.appon.gtantra.GraphicsUtil;
import com.appon.miniframework.Event;
import com.appon.miniframework.EventManager;
import com.appon.miniframework.ResourceManager;
import com.appon.miniframework.ScrollableContainer;


public class MainMenu {


	private static MainMenu instance;
	private ScrollableContainer mainMenu;


	private MainMenu() {
		// TODO Auto-generated constructor stub
	} 
	public static MainMenu getInstance(){
		if(instance == null){
			instance =new MainMenu();
		}
		return instance;
	}
	public void update() {

	}
	public void paint(Canvas c, Paint paintObject) {
		GraphicsUtil.fillRect(0, 0, MyGameConstants.SCREEN_WIDTH,MyGameConstants.SCREEN_HEIGHT, 0xFFFFFFFF,c, paintObject);
		mainMenu.paintUI(c, paintObject);
	}
	private void loadMainMenu() {

		//loading the menu.meunex
		MyGameConstants.BUTTON_BG.loadImage();
		MyGameConstants.PLAY.loadImage();
		MyGameConstants.EXIT.loadImage();

		try {
			ResourceManager.getInstance().setFontResource(0, MyGameConstants.Font);
			ResourceManager.getInstance().setImageResource(0,MyGameConstants.BUTTON_BG.getImage());
			ResourceManager.getInstance().setImageResource(1,MyGameConstants.PLAY.getImage());
			ResourceManager.getInstance().setImageResource(2,MyGameConstants.EXIT.getImage());

			setContainerMenu( com.appon.miniframework.Util.loadContainer(GTantra.getFileByteData("/mainmenu.menuex",GameActivity.getInstance()),1280, 800, MyGameConstants.SCREEN_WIDTH,MyGameConstants.SCREEN_HEIGHT,true));

			getContainerMenu().setEventManager(new EventManager() {
				public void event(Event event) {
					if(event.getEventId() == FIRE_PRESSED ||  event.getEventId() == CONTROL_CLICKED){
						switch (event.getSource().getId()) {
						case 1:
							// play Button
							MyGameCanvas.setMyGameCanvasState(MyGameConstants.MYGAME_CANVAS_GAMELOAD_STATE);
							break;
						case 2:
							// Quit Button
							MyGameActivity.getInstance().closeGame();
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
		this.mainMenu=loadContainer;
	}
	public ScrollableContainer getContainerMenu() {
		return this.mainMenu;
	}
	public void load() {
		loadMainMenu();		
	}

	public void unload() {
		this.mainMenu=null;
	}
	public void pointerPressed(int x, int y) {
		this.mainMenu.pointerPressed(x, y);
	}
	public void pointerReleased(int x, int y) {
		this.mainMenu.pointerReleased(x, y);
	}
	public void pointerDragged(int x, int y) {
		this.mainMenu.pointerDragged(x, y);
	}


}
