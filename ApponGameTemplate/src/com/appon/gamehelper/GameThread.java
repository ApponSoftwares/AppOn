package com.appon.gamehelper;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private DrawView _panel;
	private boolean _run = false;
	private long timeHolder;
	public static int FPS = 15;
	public static int WAIT_TIME = 1000 / FPS;
	public GameThread(SurfaceHolder surfaceHolder, DrawView panel) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;

	}

	public void setRunning(boolean run) {
		_run = run;
	}
	public boolean checkRunning()
	{
		return _run;
	}

	@Override
	public void run() {
		Canvas c;
		while (_run) {
			c = null;
			try {
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					timeHolder = System.currentTimeMillis();
					if(c!=null){
						_panel.getCanvas().update();
						_panel.customDraw(c);
					}
					long timeTaken = (System.currentTimeMillis() - timeHolder);
					int remainingTime = (int)(WAIT_TIME - timeTaken);
					if(remainingTime > 0)
					{
						try {
							Thread.sleep(remainingTime);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

				}
			}catch (Exception e) {
				e.printStackTrace();
			} 
			finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}