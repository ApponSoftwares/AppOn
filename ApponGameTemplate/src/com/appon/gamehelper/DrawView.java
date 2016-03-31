package com.appon.gamehelper;

import java.util.Timer;
import java.util.TimerTask;

import com.appon.game.MyGameCanvas;
import com.appon.game.MyGameConstants;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;



public class DrawView extends SurfaceView implements SurfaceHolder.Callback{
	GameCanvas canvas;

	private GameThread _thread;
	public static ThreadStarter starter;
	private boolean surfaceCreated = false;

	public DrawView(Context context, Object object) {
		super(context);
		getHolder().setFormat(PixelFormat.RGBA_8888);
		getHolder().addCallback(this);
		setFocusable(true);
		if(object != null)
		{
			canvas = (GameCanvas)object;
		}else{
			canvas = GameCanvas.getNewInstance(context);
			MyGameCanvas.setMyGameCanvasState(MyGameConstants.MYGAME_CANVAS_LOGO_STATE);
			canvas.setParent(this);
		}
		canvas.setParent(this);
		starter = new ThreadStarter();
	}	
	class ThreadStarter
	{
		public boolean working = false;

		private boolean haltOperation = false;

		public void setHaltOperation(boolean haltOperation) {
			this.haltOperation = haltOperation;
		}
		private boolean isWorking()
		{
			return working;
		}
		private boolean isThreadAlreadyRunning()
		{
			if(_thread == null || !_thread.checkRunning())
				return false;
			else
				return true;
		}

		private void startTheThread()
		{

			if(!isWorking())
			{
				haltOperation = false;
				working = true;
				Timer time = new Timer();
				time.schedule(new TimerTask() {
					@Override
					public void run() {
						internalStart();
					}
				}, 300);

			}
		}
		private void internalStart()
		{

			if(surfaceCreated && !isThreadAlreadyRunning() && GameActivity.getInstance().isInForground())
			{
				try {
					GameActivity.handler.removeCallbacksAndMessages(null);
				} catch (Exception e) {
					// TODO: handle exception
				}

				_thread = new GameThread(getHolder(), DrawView.this);
				_thread.setRunning(true);
				_thread.start();
				working = false;
			}else{
				if(!isThreadAlreadyRunning() && !haltOperation && GameActivity.getInstance().isInForground())
				{
					Timer time = new Timer();
					time.schedule(new TimerTask() {
						@Override
						public void run() {
							internalStart();

						}
					}, 300);
				}

			}
		}

	}
	public GameCanvas getCanvas() {
		return canvas;
	}
	/**
	 * Constructor
	 */

	protected void customDraw(Canvas g) {
		try {
			canvas.paint(g);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		setMeasuredDimension(Math.max(display.getWidth(), display.getHeight()),Math.min(display.getWidth(), display.getHeight()));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(canvas!=null)
		{
			int act = event.getAction();
			int action =(act & event.ACTION_MASK);
			int ptrIndex=(act >> event.ACTION_POINTER_ID_SHIFT);

			int _x=(int)(event.getX(ptrIndex));
			int _y=(int)(event.getY(ptrIndex));

			if( action== MotionEvent.ACTION_DOWN || action== MotionEvent.ACTION_POINTER_DOWN)
			{
				canvas.pointerPressed(_x, _y);
				return true;
			}
			if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP)
			{
				canvas.pointerReleased(_x, _y);
				return true;
			}
			if(action == MotionEvent.ACTION_MOVE)
			{
				for(int j=0;j<event.getPointerCount();j++)
				{
					int id=(event.getPointerId(j));
					int _newX=(int)(event.getX(j));
					int _newY=(int)(event.getY(j));

					canvas.pointerDragged(_newX, _newY);
				}
				return true;
			}

		}
		return false;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		surfaceCreated = true;
		//threadStart();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// simply copied from sample application LunarLander:
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode

		threadStop();
		surfaceCreated = false;
	}

	public void threadStop()
	{
		if(_thread != null && _thread.checkRunning())
		{
			starter.setHaltOperation(true);
			_thread.setRunning(false);
			_thread = null;
		}
	}
	public void threadStart()
	{
		if(starter != null)
			starter.startTheThread();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		// TODO Auto-generated method stub

	}


}
