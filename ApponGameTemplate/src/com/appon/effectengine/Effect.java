/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;

import com.appon.game.MyGameConstants;
import com.appon.game.MyGameEngine;
import com.appon.util.Serilizable;

/**
 *
 * @author acer
 */
public class Effect implements Serilizable{
	private Vector effectLayers = new Vector();
	private int timeFrameId;
	private int maxTimeFrame;
	EffectListener listener;
	private Hashtable timerHashtable = new Hashtable();
	private int timer;

	public Hashtable getTimerHashtable() {
		return timerHashtable;
	}

	private int aliasBackupFlags;
	private void makeEffectSmooth(Paint p)
	{
		aliasBackupFlags = p.getFlags();
		p.setFlags(Paint.ANTI_ALIAS_FLAG);
	}
	private void restoreAlies(Paint p)
	{
		p.setFlags(aliasBackupFlags);
	}
	public void addTimer(int index, int timer)
	{
		if(timer == 1)
		{
			if(timerHashtable.get(index +"") != null)
			{
				timerHashtable.remove(index+"");
			}
		}else if(timer >= 2){
			timerHashtable.put(index +"", new Integer(timer));
		}
	}
	public Effect clone()
	{
		Effect tf = new Effect();
		for (int i = 0; i < effectLayers.size(); i++) {
			Object object = effectLayers.elementAt(i);
			tf.getEffectLayers().addElement(((EffectLayer)object).clone());
		}
		tf.reset();
		tf.setListener(listener);
		Enumeration e = timerHashtable.keys();
		while(e.hasMoreElements())
		{
			Object key = e.nextElement();
			tf.timerHashtable.put(key, timerHashtable.get(key));
		}
		return tf;
	}
	public boolean isEffectOver() {
		return timeFrameId>=maxTimeFrame;
	}

	public boolean isEffectGoingToOver() {
		return timeFrameId>=(maxTimeFrame>>1);
	}
	public boolean isEffectOverAtCount(int count) {
		return timeFrameId>=(maxTimeFrame-count);
	}
	//#ifdef ANDROID
	public void paintFrame(Canvas g,int x,int y,int frameIndex,Paint paint)
	//#else
	//# public void paintFrame(Graphics g,int x,int y,int frameIndex)
	//#endif

	{
		makeEffectSmooth(paint);
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			TimeFrame tframe = object.getTimeFrame(frameIndex);
			if(tframe != null)
			{
				//#ifdef ANDROID
				tframe.paint(g, x, y, true, 0, 0,0,0,this,paint,false);
				//#else
				//# tframe.paint(g, x, y, true, 0, 0,0,0,this,false);
				//#endif
			}
		}
		restoreAlies(paint);
	}
	//#ifdef ANDROID
	public void paintFrame(Canvas g,int x,int y,int frameIndex,int theta,int zoom,int anchorX,int anchorY,Paint paint)
	//#else
	//# public void paintFrame(Graphics g,int x,int y,int frameIndex,int theta,int zoom,int anchorX,int anchorY)
	//#endif

	{
		makeEffectSmooth(paint);
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			TimeFrame tframe = object.getTimeFrame(frameIndex);
			if(tframe != null)
			{
				//#ifdef ANDROID
				tframe.paint(g, x, y, true, theta, zoom,anchorX,anchorY,this,paint,false);
				//#else
				//# tframe.paint(g, x, y, true, theta, zoom,anchorX,anchorY,this,false);
				//#endif

			}
		}
		restoreAlies(paint);
	} 
	public int[] getCollisionRect(int layerId,int frameId,int shapeIndex,int rect[],int zoom)
	{
		EffectLayer object = (EffectLayer)effectLayers.elementAt(layerId);
		TimeFrame tframe = object.getTimeFrame(frameId);
		if(tframe != null)
		{
			if(tframe.getShapes().elementAt(shapeIndex) instanceof ERect)
			{
				ERect efrect =((ERect)tframe.getShapes().elementAt(shapeIndex));
				rect[0] = Util.getScaleValue(efrect.getX(),zoom);
				rect[1] = Util.getScaleValue(efrect.getY(),zoom);
				rect[2] = Util.getScaleValue(efrect.getWidth(),zoom);
				rect[3] =  Util.getScaleValue(efrect.getHeight(),zoom);
			}
		}
		return rect;
	}
	public int[] getCollisionPolyX(int layerId,int frameId,int shapeIndex)
	{
		EffectLayer object = (EffectLayer)effectLayers.elementAt(layerId);
		TimeFrame tframe = object.getTimeFrame(frameId);
		if(tframe != null)
		{
			if(tframe.getShapes().elementAt(shapeIndex) instanceof EPolygon)
			{
				EPolygon efrect =((EPolygon)tframe.getShapes().elementAt(shapeIndex));
				return efrect.getTmpXPoints();
			}
		}
		return null;
	}
	public int[] getCollisionPolyY(int layerId,int frameId,int shapeIndex)
	{
		EffectLayer object = (EffectLayer)effectLayers.elementAt(layerId);
		TimeFrame tframe = object.getTimeFrame(frameId);
		if(tframe != null)
		{
			if(tframe.getShapes().elementAt(shapeIndex) instanceof EPolygon)
			{
				EPolygon efrect =((EPolygon)tframe.getShapes().elementAt(shapeIndex));
				return efrect.getTmpYPoints();
			}
		}
		return null;
	}

	public void paint(Canvas g,int x,int y,boolean loop, Paint p,boolean iscale,float zoom)
	{
		if(!iscale)
			zoom=0;
		else
			zoom-=100;

		makeEffectSmooth(p);
		int backupColor=p.getColor();
		ColorFilter backupColorFilter = p.getColorFilter();

		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			/* //#ifdef ANDROID
             	 object.paint(g, x, y, timeFrameId,true,this,p);
                 //#else
                 //# object.paint(g, x, y, timeFrameId,true,this);
                 //#endif*/
                 //#ifdef ANDROID
                 object.paint(g, x, y, timeFrameId,true,0,(int)zoom,0,0,this,p);
                 //#else
                 //# object.paint(g, x, y, timeFrameId,true,0,zoom,0,0,this);
                 //#endif

		}
		p.setColor(backupColor);
		p.setColorFilter(backupColorFilter);

		if(MyGameEngine.getMyGameEngineState() != MyGameConstants.MYGAME_ENGINE_PAUSE_STATE){
			updateEffect(loop);	
		}
		restoreAlies(p);
	}




	//#ifdef ANDROID
	public void paint(Canvas g,int x,int y,boolean loop,int zoom , Paint p)
	//#else
	//# public void paint(Graphics g,int x,int y,boolean loop)
	//#endif
	{
		makeEffectSmooth(p);
		int backupColor=p.getColor();
		ColorFilter backupColorFilter = p.getColorFilter();

		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			/* //#ifdef ANDROID
            	 object.paint(g, x, y, timeFrameId,true,this,p);
                //#else
                //# object.paint(g, x, y, timeFrameId,true,this);
                //#endif*/
			//#ifdef ANDROID
			object.paint(g, x, y, timeFrameId,true,0,zoom,0,0,this,p);
			//#else
			//# object.paint(g, x, y, timeFrameId,true,0,zoom,0,0,this);
			//#endif

		}
		p.setColor(backupColor);
		p.setColorFilter(backupColorFilter);

		if(MyGameEngine.getMyGameEngineState() != MyGameConstants.MYGAME_ENGINE_PAUSE_STATE){
			updateEffect(loop);	
		}
		restoreAlies(p);
	}

	private void updateEffect(boolean loop)
	{
		int currentTimer = 1;
		Object obj = timerHashtable.get(timeFrameId+"");
		if(obj != null)
		{
			currentTimer = ((Integer)obj).intValue();
		}
		if(timer + 1 >= currentTimer)
		{
			timeFrameId++;
			timer = 0;
			if(listener != null)
			{
				listener.effectTimeFrameChanged(timeFrameId);
			}
			if(timeFrameId > maxTimeFrame)
			{
				if(loop)
				{
					timeFrameId = 0;

				}else{
					timeFrameId = maxTimeFrame;
					if(listener != null)
					{
						listener.effectOver(this);
					}
				}
			}
		}else{
			timer++;
		}
	}
	public void paintWithOutUpdate(Canvas g,int x,int y,int theta,int zoom,int anchorX,int anchorY,Paint p)
	{
		makeEffectSmooth(p);
		for (int i = 0; i < effectLayers.size(); i++) {
			((EffectLayer)effectLayers.elementAt(i)).paint(g, x, y, timeFrameId,true,theta,zoom,anchorX,anchorY,this,p);
		}
		restoreAlies(p);
	}
	public void update(boolean loop)
	{
		if(MyGameEngine.getMyGameEngineState() != MyGameConstants.MYGAME_ENGINE_PAUSE_STATE){
			updateEffect(loop);
		}
	}

	//#ifdef ANDROID
	public void paint(Canvas g,int x,int y,boolean loop,int theta,int zoom,int anchorX,int anchorY,Paint p)
	//#else
	//# public void paint(Graphics g,int x,int y,boolean loop,int theta,int zoom,int anchorX,int anchorY)
	//#endif

	{
		makeEffectSmooth(p);
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			//#ifdef ANDROID
			object.paint(g, x, y, timeFrameId,true,theta,zoom,anchorX,anchorY,this,p);
			//#else
			//# object.paint(g, x, y, timeFrameId,true,theta,zoom,anchorX,anchorY,this);
			//#endif

		}

		if(MyGameEngine.getMyGameEngineState() != MyGameConstants.MYGAME_ENGINE_PAUSE_STATE){
			updateEffect(loop);
		}
		restoreAlies(p);
	}
	public void setBgColorCustom(int color) {
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			object.setBgColorCustom(color);
		}

	}
	public void setBgColorCustom(int color,int sahpeId) {
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			object.setBgColorCustom(color,sahpeId);
		}

	}
	public void setListener(EffectListener listener) {
		this.listener = listener;
	}

	private int getMaxTimeFrame()
	{
		Vector vector  = effectLayers;
		int maxTimeFrame = 0;
		for (int i = 0; i < vector.size(); i++) {
			EffectLayer effectLayer = (EffectLayer)vector.elementAt(i);
			Vector timeFrames = effectLayer.getTimeFrames();
			for (int j = 0; j < timeFrames.size(); j++) {
				TimeFrame object = (TimeFrame)timeFrames.elementAt(j);
				if(object.getTimeFrameId() > maxTimeFrame)
				{
					maxTimeFrame =  object.getTimeFrameId();
				}
			}
		}
		return maxTimeFrame;
	}
	public void reset()
	{
		timeFrameId = 0;
		maxTimeFrame = getMaxTimeFrame();
	}
	public Vector getEffectLayers() {
		return effectLayers;
	}


	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		EffectsSerilize.serialize(effectLayers, bos);
		EffectsSerilize.serialize(timerHashtable, bos);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}


	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		effectLayers = (Vector)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
		if(Util.LOADING_VERSION >= 2)
		{
			timerHashtable= (Hashtable)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
		}
		bis.close();
		return null;
	}

	public void port(int xper,int yper)
	{
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer effectLayer = (EffectLayer)effectLayers.elementAt(i);
			effectLayer.port(xper, yper);
		}
	}
	public int getClassCode() {
		return EffectsSerilize.EFFECT_TYPE;
	}
	public int getMaxFrame()
	{
		return this.maxTimeFrame;
	}

	public void setAngle(int _angle)
	{
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			object.setAngle(_angle);
		}

	}
	public int getTimeFrameId() {
		return timeFrameId;
	}
	public int getTimer() {
		return timer;
	}

	//#ifdef ANDROID
	public void paintUnconditional(Canvas g,int x,int y,boolean loop,int zoom,Paint p)
	//#else
		//# public void paintUnconditional(Graphics g,int x,int y,boolean loop)
		//#endif
		{
		makeEffectSmooth(p);
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			/*//#ifdef ANDROID
            	 object.paint(g, x, y, timeFrameId,true,this,p);
                //#else
                //# object.paint(g, x, y, timeFrameId,true,this);
                //#endif*/

			//#ifdef ANDROID
			object.paint(g, x, y, timeFrameId,true,0,zoom,0,0,this,p);
			//#else
			//# object.paint(g, x, y, timeFrameId,true,theta,zoom,anchorX,anchorY,this);
			//#endif


		}
		updateEffect(loop);
		restoreAlies(p);
		}
	//#ifdef ANDROID
	public void paintWithoutUpdate(Canvas g,int x,int y,boolean loop, int zoom,Paint p)
	//#else
	//# public void paintWithoutUpdate(Graphics g,int x,int y,boolean loop)
	//#endif
	{
		makeEffectSmooth(p);
		for (int i = 0; i < effectLayers.size(); i++) {
			EffectLayer object = (EffectLayer)effectLayers.elementAt(i);
			/*//#ifdef ANDROID
            	 object.paint(g, x, y, timeFrameId,true,this,p);
                //#else
                //# object.paint(g, x, y, timeFrameId,true,this);
                //#endif
			 * *
			 */
			//#ifdef ANDROID
			object.paint(g, x, y, timeFrameId,true,0,zoom,0,0,this,p);
			//#else
			//# object.paint(g, x, y, timeFrameId,true,theta,zoom,anchorX,anchorY,this);
			//#endif

		}
		restoreAlies(p);
	}
	public boolean isEffectRendering() {

		if(this.getTimeFrameId()>=1 && this.getTimeFrameId()<=this.getMaxFrame()){
			return true;
		}
		return false;

	}

}
