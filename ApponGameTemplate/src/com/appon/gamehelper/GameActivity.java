package com.appon.gamehelper;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.appon.game.MyGameCanvas;


public abstract class GameActivity extends Activity {
	private boolean destroied = false;	   
	private static GameActivity instance;

	public static Handler handler = new Handler();
	public static Object savedObject ;

	private LayoutParams layout;
	private RelativeLayout ll;
	private BroadcastReceiver mReceiver;
	private PowerManager.WakeLock mWakeLock;
	private DrawView canvas;

	public DrawView getCanvas() {
		return canvas;
	}


	public void onCreate(final Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		instance = this;
		ll = new RelativeLayout(this);
		setContentView(ll);

		canvas = new DrawView(this,savedObject);
		layout  = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);		
		layout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		ll.addView(canvas, layout);

		savedObject = canvas.getCanvas();

		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		mWakeLock.acquire();

		try {
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_SCREEN_ON);
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			filter.addAction(Intent.ACTION_USER_PRESENT);
			mReceiver = new ScreenReceiver();
			registerReceiver(mReceiver, filter);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		Resources.init(this);
	}

	public static GameActivity getInstance() {
		return instance;
	}

	private boolean isScreenPresent=false;
	public static boolean wasScreenOn = false;

	public boolean iSpresent() {
		return isScreenPresent;
	}
	public class ScreenReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {

			String action=intent.getAction(); 
			if (action.equals(Intent.ACTION_SCREEN_OFF)) {
				wasScreenOn=false;
			}
			else if (action.equals(Intent.ACTION_SCREEN_ON)) 
			{
				wasScreenOn=false;

				if(isScreenPresent)
					wasScreenOn=true;
				if(wasScreenOn){
					MyGameCanvas.getInstance().showNotify();
				}
			}
			else if(action.equals(Intent.ACTION_USER_PRESENT))
			{
				if(isScreenPresent)
					wasScreenOn=true;
				if(wasScreenOn)
					MyGameCanvas.getInstance().showNotify();
			}
		}

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if(hasFocus)
		{
			wasScreenOn=true;
			isScreenPresent=true;
			canvas.getCanvas().showNotify();
		}
		else
		{
			wasScreenOn=false;
			isScreenPresent=false;
		}

	}

	public static boolean wasPaused = false;
	private boolean isForground = false;
	public boolean isInForground()
	{
		return isForground;
	}

	@Override
	protected void onPause() {
		if(canvas != null && !destroied)
		{
			wasPaused = true;
			savedObject = canvas.getCanvas();
			canvas.getCanvas().hideNotify();
		}			
		if(canvas != null)
		{
			canvas.threadStop();
		}
		isForground = false;
		super.onPause();
	}
	@Override
	protected void onResume() {
		isForground = true;
		if(wasPaused && wasScreenOn && !destroied)
		{
			canvas.getCanvas().showNotify();
			wasPaused = false;
			refreshView();
		}
		getCanvas().invalidate();
		refreshView();
		if(canvas != null)
		{
			if(canvas.starter.working)
				canvas.starter.working = false;
			canvas.threadStart();
		}
		refreshView();
		isForground = true;
		super.onResume();
	}	    

	@Override
	public void onDestroy() {
		if(mReceiver!=null)
			unregisterReceiver(mReceiver);
		super.onDestroy();
	}


	public void closeGame()
	{

		if(mWakeLock!=null)
			mWakeLock.release();
		destroied = true;
		if(savedObject != null){
			((GameCanvas)savedObject).shutDown();
		}
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}



	public void refreshView()
	{
		handler.post(new Runnable() {
			public void run() {
				canvas.invalidate();
			}
		});
	}


	public abstract void onBackPressed() ;



	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}


	@Override
	protected void onStart() {
		super.onStart();
	}

	//session end
	@Override
	protected void onStop() {
		super.onStop();
	}


}
