/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.gtantra.GAnim;
import com.appon.gtantra.GTantra;

/**
 *
 * @author user
 */
public class MovingObject {
	int x;
	int y;
	int direction;
	private int storedX;
	private int storedY;
	private boolean isFromDragged = false;
	GTantra coinGTantra=new GTantra();
	GAnim coinAnim=null;
	public MovingObject() {

	}
	public void load(){
		coinGTantra.Load(GTantra.getFileByteData("coin_anim.GT", MyGameActivity.getInstance()),false);
		coinAnim=new GAnim(coinGTantra, 0);
	}
	public void unLoad(){
		coinGTantra.unload();;
		coinAnim=null;
	}
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paint(Canvas g,Paint paintObject)
	{
		//		GraphicsUtil.fillRect(x, y, MyGameConstants.MOVING_OBJECT_WIDTH, MyGameConstants.MOVING_OBJECT_HEIGHT, 0xFFFF0000, g, paintObject);
		coinAnim.render(g, x, y, 0, true, paintObject);
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public void copy(MovingObject part)
	{
		setX(part.getX());
		setY(part.getY());
		setDirection(getDirection());
	}

	public String toString() {
		return "X: "+getX() + " Y: " + getY();
	}
	public void update()
	{
		switch(direction)
		{
		case MyGameConstants.DIRECTION_LEFT:
			setX(getX()-MyGameConstants.Object_SPEED);
			if((getX()+MyGameConstants.MOVING_OBJECT_WIDTH)<=0)
				setX(MyGameConstants.SCREEN_WIDTH);
			break;
		case MyGameConstants.DIRECTION_RIGHT:
			setX(getX()+MyGameConstants.Object_SPEED);
			if((getX())>=MyGameConstants.SCREEN_WIDTH)
				setX(0);
			break;
		case MyGameConstants.DIRECTION_UP:
			setY(getY()-MyGameConstants.Object_SPEED);
			if((getY()+MyGameConstants.MOVING_OBJECT_HEIGHT)<=0)
				setY(MyGameConstants.SCREEN_HEIGHT);
			break;
		case MyGameConstants.DIRECTION_DOWN:
			setY(getY()+MyGameConstants.Object_SPEED);
			if((getY())>=MyGameConstants.SCREEN_HEIGHT)
				setY(0);
			break;
		}
	}


	public void poiterPressed(int x, int y) {
		storedX = x;
		storedY = y;
	}
	public void poiterRealesed(int x, int y) {
		if(isFromDragged && Math.abs(storedX - x) > Math.abs(storedY - y) && Math.abs(storedX - x) > 0)
		{
			// left
			if(storedX > x)
			{
				setDirection(MyGameConstants.DIRECTION_LEFT);
			} // right
			else{
				setDirection(MyGameConstants.DIRECTION_RIGHT);
			}

		} // movement on Y
		else if(isFromDragged && Math.abs(storedY - y) > 0)
		{
			// up
			if(storedY > y)
			{
				setDirection(MyGameConstants.DIRECTION_UP);
			} // down
			else{
				setDirection(MyGameConstants.DIRECTION_DOWN);
			}
		}
	}
	public void poiterDragged(int x, int y) {
		isFromDragged = true;
	}





}




