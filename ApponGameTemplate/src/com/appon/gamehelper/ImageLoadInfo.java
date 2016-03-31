package com.appon.gamehelper;
/*
 * ImageLoadInfo.java
 *
 * Created on April 20, 2010, 11:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



import android.graphics.Bitmap;

public class ImageLoadInfo 
{
	String imageName;
	private byte resizeOption;

	private boolean isSplash=false;
	public ImageLoadInfo(String name,byte resizeOption,boolean clear){
		this(name , resizeOption );
		this.isSplash = clear;
	}

	public ImageLoadInfo(String name,byte resizeOption)
	{
		this.imageName = name;
		this.resizeOption = resizeOption;

	}

	public void clear()
	{
		try {
			if (getImage() != null && !getImage().isRecycled()) {
				getImage().recycle();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		Resources.getInstance().setImage(imageName ,null);
	}
	public boolean isNull()
	{
		return  Resources.getInstance().getImage(imageName) == null;
	}
	public Bitmap getImage()
	{
		return  Resources.getInstance().getImage(imageName) ;
	}
	public int getWidth()
	{
		return getImage().getWidth();
	}
	public int getHeight()
	{

		return getImage().getHeight();
	}
	public void loadImage()
	{
		if(!isNull())
		{
			return;
		}
		switch (resizeOption)
		{
		case Resources.RESIZE_NONE:
			Resources.getInstance().setImage(imageName ,  Resources.loadResizeImage(imageName,-1,-1)) ;
			break;
		case Resources.RESIZE_ONLY_WIDTH:
			Resources.getInstance().setImage(imageName , Resources.loadResizeImage(imageName, Resources.ACTUL_IMAGE_WIDTH_RESOLUTION,-1)) ;
			break;
		case Resources.RESIZE_ONLY_HEIGHT:
			Resources.getInstance().setImage(imageName ,  Resources.loadResizeImage(imageName,-1, Resources.ACTUL_IMAGE_HEIGHT_RESOLUTION)) ;
			break;
		case Resources.RESIZE_BOTH:
			Resources.getInstance().setImage(imageName , Resources.loadResizeImage(imageName, Resources.ACTUL_IMAGE_WIDTH_RESOLUTION, Resources.ACTUL_IMAGE_HEIGHT_RESOLUTION)) ;
			break;
		case Resources.RESIZE_LARGEFIT:
			Resources.getInstance().setImage(imageName , Resources.loadResizeImage(imageName, Resources.ACTUL_IMAGE_WIDTH_RESOLUTION, Resources.ACTUL_IMAGE_HEIGHT_RESOLUTION,true)) ;
			break;
		case Resources.RESIZE_BG:
			if(isSplash &&( Resources.getResDirectory().equals("lres") || Resources.getResDirectory().equals("mres")))
				Resources.getInstance().setImage(imageName , Resources.loadResizeImage(imageName, Resources.ACTUL_IMAGE_WIDTH_RESOLUTION, Resources.ACTUL_IMAGE_HEIGHT_RESOLUTION)) ;
			else
				Resources.getInstance().setImage(imageName , Resources.loadResizeImageFitToScreen(imageName,-1,-1,false)) ;
			break;
		}
	}     
}