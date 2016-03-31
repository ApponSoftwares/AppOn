package com.appon.gamehelper;
/*
 * Resources.java
 *
 * Created on April 20, 2010, 10:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */




import java.io.InputStream;
import java.util.Hashtable;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.appon.animlib.ImagePack;
import com.appon.game.MyGameConstants;
import com.appon.gtantra.GTantra;

/**
 *
 * @author 
 */

public class Resources {

	public static int ACTUL_IMAGE_WIDTH_RESOLUTION ;
	public static int ACTUL_IMAGE_HEIGHT_RESOLUTION ;

	public static int CONSIDERED_IMAGE_WIDTH_RESOLUTION ;
	public static int CONSIDERED_IMAGE_HEIGHT_RESOLUTION ;

	private static Resources instance;
	static byte counter;
	private Hashtable images = new Hashtable();
	public static final byte RESIZE_NONE = 0;
	public static final byte RESIZE_ONLY_WIDTH = 1;
	public static final byte RESIZE_ONLY_HEIGHT = 2;
	public static final byte RESIZE_BOTH = 3;
	public static final byte RESIZE_LARGEFIT = 4;
	public static final byte RESIZE_BG = 5;

	public static final byte RES_LDPI = 0;
	public static final byte RES_MDPI = 1;
	public static final byte RES_HDPI = 2;
	public static final byte RES_XDPI = 3;
	public static final byte RES_XHDPI = 4;
	public static final byte RES_XLDPI = 5;
	public static int resValue;
	/** Images Ids */

	public static Context context;
	public static String resDirectory;
	/** Creates a new instance of Resources */
	public static float resizePercent = 0;
	public static float resizePercentX = 0;
	public static float resizePercentY = 0;
	public static float perX;
	public static float perY;
	public Resources() {
	}
	public static void setResizePercent(float resizePercent)
	{
		Resources.resizePercent = resizePercent;
	}
	public static void setResizePercentX(float resizePercent)
	{
		Resources.resizePercentX = resizePercent;
	}
	public static void setResizePercentY(float resizePercent)
	{
		Resources.resizePercentY = resizePercent;
	}


	public static void init(Context context1)
	{
		inited=true;
		context = context1;
		WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE); 
		Display display = window.getDefaultDisplay();
		int width = Math.max(display.getWidth(),display.getHeight());
		int height = Math.min(display.getWidth(),display.getHeight());

		MyGameConstants.SCREEN_WIDTH=width;
		MyGameConstants.SCREEN_HEIGHT=height;

		ACTUL_IMAGE_WIDTH_RESOLUTION = width;
		ACTUL_IMAGE_HEIGHT_RESOLUTION = height;

		perX=(float)(((float)(ACTUL_IMAGE_WIDTH_RESOLUTION*100))/MyGameConstants.MASTER_SCREEN_WIDTH);
		perY=(float)(((float)(ACTUL_IMAGE_HEIGHT_RESOLUTION*100))/MyGameConstants.MASTER_SCREEN_HEIGHT);
		ImagePack.perX=perX;
		ImagePack.perY=perY;
		resDirectory = getResolutionInfo();
		if(resDirectory.equals("lres")){

			// for Effect 100 means 150;
			com.appon.effectengine.Util.setResizePercentX((int)(perX-100));
			com.appon.effectengine.Util.setResizePercentY((int)(perY-100));

			perX=(float)(((float)(ACTUL_IMAGE_WIDTH_RESOLUTION*100))/320);
			perY=(float)(((float)(ACTUL_IMAGE_HEIGHT_RESOLUTION*100))/240);

			setResizePercent(perY);
			setResizePercentX(perX);
			setResizePercentY(perY);
			GTantra.setResizeGlobalPercentage((int)perY);

		}
		if(resDirectory.equals("mres")){
			setResizePercent(perY);
			setResizePercentX(perX);
			setResizePercentY(perY);
			GTantra.setResizeGlobalPercentage((int)perY);
			// for Effect 100 means 150;
			com.appon.effectengine.Util.setResizePercentX((int)(perX-100));
			com.appon.effectengine.Util.setResizePercentY((int)(perY-100));
		}
		if(resDirectory.equals("hres")){
			setResizePercent(perY);
			setResizePercentX(perX);
			setResizePercentY(perY);
			GTantra.setResizeGlobalPercentage((int)perY);
			// for Effect 100 means 150;
			com.appon.effectengine.Util.setResizePercentX((int)(perX-100));
			com.appon.effectengine.Util.setResizePercentY((int)(perY-100));
		}
		if(resDirectory.equals("xres")){
			setResizePercent(perY);
			setResizePercentX(perX);
			setResizePercentY(perY);
			GTantra.setResizeGlobalPercentage((int)perY);
			// for Effect 100 means 150;
			com.appon.effectengine.Util.setResizePercentX((int)(perX-100));
			com.appon.effectengine.Util.setResizePercentY((int)(perY-100));
		}
		if(resDirectory.equals("xhres"))
		{
			setResizePercent(perY);
			setResizePercentX(perX);
			setResizePercentY(perY);
			GTantra.setResizeGlobalPercentage((int)perY);
			// for Effect 100 means 150;
			com.appon.effectengine.Util.setResizePercentX((int)(perX-100));
			com.appon.effectengine.Util.setResizePercentY((int)(perY-100));
		}
		if(resDirectory.equals("xlarges"))
		{
			setResizePercent(perY);
			setResizePercentX(perX);
			setResizePercentY(perY);
			GTantra.setResizeGlobalPercentage((int)perY);
			// for Effect 100 means 150;
			com.appon.effectengine.Util.setResizePercentX((int)(perX-100));
			com.appon.effectengine.Util.setResizePercentY((int)(perY-100));
		}

	}

	public synchronized static Resources getInstance()
	{
		if(instance == null)
		{
			instance = new Resources();
			//instance.load();
		}
		return instance;
	}
	private static int resInfo[][] = {{240 , 400},{480 , 640},{640,1024},{1000 , 1500},{2000,1500}};//360
	private static int imagesTakenFromArtist[][] = {{240 , 320},{320 , 480},{480,800},{800,1280},{800 ,1280},{800 ,1280}};
	private static String resNames[] = {"lres","mres","hres","xres","xhres","xlarges"};


	private static String getResolutionInfo()
	{
		for (int i = 0; i < resInfo.length; i++)
		{
			if( (ACTUL_IMAGE_WIDTH_RESOLUTION <= resInfo[i][0] && ACTUL_IMAGE_HEIGHT_RESOLUTION <= resInfo[i][1]) || (ACTUL_IMAGE_WIDTH_RESOLUTION <= resInfo[i][1] && ACTUL_IMAGE_HEIGHT_RESOLUTION <= resInfo[i][0]))
			{
				if(!isPortrait())
				{
					CONSIDERED_IMAGE_WIDTH_RESOLUTION = imagesTakenFromArtist[i][1];
					CONSIDERED_IMAGE_HEIGHT_RESOLUTION = imagesTakenFromArtist[i][0];
				}else{
					CONSIDERED_IMAGE_WIDTH_RESOLUTION = imagesTakenFromArtist[i][0];
					CONSIDERED_IMAGE_HEIGHT_RESOLUTION = imagesTakenFromArtist[i][1];
				}
				resValue = i;
				return resNames[i];
			}
		}
		int i = resNames.length - 1;
		if(!isPortrait())
		{
			CONSIDERED_IMAGE_WIDTH_RESOLUTION = imagesTakenFromArtist[i][1];
			CONSIDERED_IMAGE_HEIGHT_RESOLUTION = imagesTakenFromArtist[i][0];
		}else{
			CONSIDERED_IMAGE_WIDTH_RESOLUTION = imagesTakenFromArtist[i][0];
			CONSIDERED_IMAGE_HEIGHT_RESOLUTION = imagesTakenFromArtist[i][1];
		}
		resValue = resNames.length - 1;
		Log.v("Mafia", "resValue :: "+ resValue);
		Log.v("Mafia", "resName  :: "+ resNames[resNames.length - 1]);
		return resNames[resNames.length - 1];
		//	   return resNames[resNames.length - 1];
	}
	public static int getResValue() {
		return resValue;
	}
	public static Bitmap createImage(String name){
		if(name.startsWith("/"))
		{
			name = name.substring(1, name.length());
		}
		try {
			Drawable d = Drawable.createFromStream(context.getAssets().open(getBasePath(name)+name), null);
			Bitmap b = ((BitmapDrawable)d).getBitmap();
			return b;
		} catch (Exception e) {
			System.out.println("Problem loading asset image:  "+name);
			return null;
		}


	}
	private static String commanGroups[] = {"540x897","640x1024","800x1280"};
	private static  int dimentionsRangeWidth[][] = {{897 , 640},{1024,960},{1280,1024}};
	private static  int dimentionsRangeHeight[][] = {{540 , 444},{640,480},{800,720}};
	public static boolean inited=false;
	private static String nameCommanGroup(int width,int heigth)
	{
		for (int i = 0; i < commanGroups.length; i++) {
			if(dimentionsRangeWidth[i][0] >= width && width >= dimentionsRangeWidth[i][1] && dimentionsRangeHeight[i][0] >= heigth && heigth >= dimentionsRangeHeight[i][1])
			{
				return commanGroups[i];
			}
		}
		return null;
	}
	public static String getBasePath(String fileName)
	{
		String specific = null;
		if(isPortrait())
		{
			specific = ACTUL_IMAGE_WIDTH_RESOLUTION + "x" + ACTUL_IMAGE_HEIGHT_RESOLUTION;
		}else{
			specific = ACTUL_IMAGE_HEIGHT_RESOLUTION + "x" + ACTUL_IMAGE_WIDTH_RESOLUTION;
		}

		if(checkFilePresent(fileName, specific))
		{
			return specific +"/";
		}
		String name = nameCommanGroup(ACTUL_IMAGE_WIDTH_RESOLUTION,ACTUL_IMAGE_HEIGHT_RESOLUTION);
		if(name != null)
		{
			if(checkFilePresent(fileName, name))
			{
				return name +"/";
			}
		}
		if(checkFilePresent(fileName, resDirectory))
		{
			return resDirectory +"/";
		}
		if(checkFilePresent(fileName, "all"))
		{
			return "all/";
		}else
		{
			//go in fall back now switch to lower resolution
			int newResValue = resValue - 1;
			if(resDirectory.equals("mres") || resDirectory.equals("hres") || resDirectory.equals("lres"))
				newResValue = RES_XDPI;

			while(newResValue > 0)
			{
				if(checkFilePresent(fileName, resNames[newResValue]))
				{
					return resNames[newResValue] +"/";
				}else{
					newResValue--;
				}
			}
			throw new RuntimeException("File "+fileName+" not present in asset");  
		}

	}
	private static boolean checkFilePresent(String fileName,String group)
	{

		try {
			return assetExists(fileName,group);
		} catch (Exception e) {
			return false;
		}

	}
	public static boolean assetExists(String fileName,String group) {
		boolean bAssetOk = false;
		try {
			InputStream stream = context.getAssets().open(group + "/"+fileName);
			stream.close();
			bAssetOk = true;
		} catch (Exception e) {

		}
		return bAssetOk;
	}

	private static String getAspectRatioPath(String fileName)
	{
		float aspectRatio = 0; 	  
		if(isPortrait())
		{
			aspectRatio = (float)ACTUL_IMAGE_WIDTH_RESOLUTION / (float)ACTUL_IMAGE_HEIGHT_RESOLUTION;
		}else{
			aspectRatio = (float)ACTUL_IMAGE_HEIGHT_RESOLUTION / (float)ACTUL_IMAGE_WIDTH_RESOLUTION;
		}

		float diffFrom75 = Math.abs(.75F - aspectRatio);
		float diffFrom63 = Math.abs(.63F - aspectRatio);
		float diffFrom56 = Math.abs(.56F - aspectRatio);

		if(diffFrom75 < 0.1F && (diffFrom75 < diffFrom63 && diffFrom75 < diffFrom56))
		{
			if(checkFilePresent(fileName, "75"))
			{
				return "75/";
			}
		}
		if(diffFrom63 < 0.1F && (diffFrom63 < diffFrom75 && diffFrom63 < diffFrom56))
		{
			if(checkFilePresent(fileName, "63"))
			{
				return "63/";
			}
		}
		if(diffFrom56 < 0.1F && (diffFrom56 < diffFrom75 && diffFrom56 < diffFrom63))
		{
			if(checkFilePresent(fileName, "56"))
			{
				return "56/";
			}
		}
		return null;
	}
	public static int getAspectRatioWidth(String resDirectory)
	{
		int width = 0;
		if(resDirectory.equals("75/"))
		{
			width = 768;
		}else if(resDirectory.equals("63/"))
		{
			width = 800;
		}else
			width = 720;
		return width;
	}

	public static int getAspectRatioHeight(String resDirectory)
	{
		int height = 0;
		if(resDirectory.equals("75/"))
		{
			height = 1024;
		}else if(resDirectory.equals("63/"))
		{
			height = 1280;
		}else
			height = 1280;
		return height;
	} 
	public static Bitmap loadResizeImage(String str , int width , int height)
	{
		return loadResizeImage(str, width, height, false);
	}
	public static Bitmap loadResizeImage(String str , int width , int height,boolean largeFit)
	{
		try {
			float newWidth = 0 , newHeight = 0;

			String specific =null;
			if(isPortrait())
			{
				specific = ACTUL_IMAGE_WIDTH_RESOLUTION + "x" + ACTUL_IMAGE_HEIGHT_RESOLUTION;
			}else{
				specific = ACTUL_IMAGE_HEIGHT_RESOLUTION + "x" + ACTUL_IMAGE_WIDTH_RESOLUTION;
			}
			if(checkFilePresent(str, specific))
			{
				Bitmap b = createImage(str);
				return b;
			}



			String name = nameCommanGroup(ACTUL_IMAGE_WIDTH_RESOLUTION,ACTUL_IMAGE_HEIGHT_RESOLUTION);
			if(name != null)
			{
				if(checkFilePresent(str, name))
				{
					Bitmap b =   createImage(str);
					return b;
				}
			}
			int consideredWidth = CONSIDERED_IMAGE_WIDTH_RESOLUTION;
			int consideredHeight = CONSIDERED_IMAGE_HEIGHT_RESOLUTION;

			String aspectRatioDir = getAspectRatioPath(str);
			if(aspectRatioDir != null)
			{
				consideredWidth = getAspectRatioWidth(aspectRatioDir);
				consideredHeight = getAspectRatioHeight(aspectRatioDir);

				if(!isPortrait())
				{
					int tmp = consideredWidth;
					consideredWidth = consideredHeight;
					consideredHeight = tmp;
				}
			}

			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			BitmapFactory.decodeStream(context.getAssets().open(getBasePath(str)+str), null, options);

			int newBoundWidth  = options.outWidth;
			int newBoundHeight  = options.outHeight;


			if(largeFit)
			{
				double widthScale = 0, heightScale = 0;
				widthScale = (double)ACTUL_IMAGE_WIDTH_RESOLUTION / (double)newBoundWidth;

				heightScale = (double)ACTUL_IMAGE_HEIGHT_RESOLUTION / (double)newBoundHeight;                

				double scale = Math.max(widthScale, heightScale);

				newWidth = (float)Math.abs(newBoundWidth * scale);
				newHeight = (float)Math.abs(newBoundHeight * scale);
			}else{
				if(width == -1)
					newWidth = newBoundWidth;
				else
					newWidth = (newBoundWidth * width) / consideredWidth;

				if(height == -1)
					newHeight = newBoundHeight;
				else
					newHeight = (newBoundHeight * height) / consideredHeight;
			}

			if((newBoundWidth == newWidth && newBoundHeight == newHeight) || (width == consideredWidth && height == consideredHeight))
			{
				if(resizePercent == 0 )
				{

					Bitmap b= createImage(str);
					return b;
				}else{

					newWidth = (newBoundWidth * resizePercent) / 100;
					newHeight = (newBoundHeight * resizePercent) / 100;

					Bitmap b=  decodeSampledBitmapFromResource(str,(int)newWidth,(int)newHeight, options);
					return b;
				}
			}
			Bitmap b=   decodeSampledBitmapFromResource(str, (int)newWidth,(int)newHeight, options);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null; 
	}
	public static Bitmap decodeSampledBitmapFromResource(String str,
			int reqWidth, int reqHeight,final BitmapFactory.Options options) throws Exception {
		options.inJustDecodeBounds = false;
		Bitmap b = BitmapFactory.decodeStream(context.getAssets().open(getBasePath(str)+str), null, options);
		Bitmap scaledBitmap = Util.createScaledBitmap(b, reqWidth, reqHeight);

		b = null;
		return scaledBitmap;
	}
	protected void setImage(String id, Bitmap image)
	{
		if(image == null)
		{
			images.remove(""+id);
		}else{
			images.put(""+id, image);
		}

	}
	public Bitmap getImageFromKey(String key)
	{
		Object data =  images.get(key);
		if(data == null)
		{
			if(key.indexOf(".") == -1)
			{
				key += ".png";
			}
		}
		data =  images.get(key);
		return (Bitmap)data;
	}
	protected Bitmap getImage(String id)
	{
		return (Bitmap)images.get(id);
	}
	public Bitmap getImage(ImageLoadInfo id)
	{
		return id.getImage();
	}
	public static String getResDirectory() 
	{
		return resDirectory;
	}
	public Hashtable getImages() {
		return images;
	}
	public static boolean isPortrait()
	{
		int ot = context.getResources().getConfiguration().orientation;
		switch(ot)
		{

		case  Configuration.ORIENTATION_LANDSCAPE:

			return false;

		case Configuration.ORIENTATION_PORTRAIT:
			return true;

		default:

			Display getOrient = GameActivity.getInstance().getWindowManager().getDefaultDisplay();
			if(getOrient.getWidth()==getOrient.getHeight()){
				return true;
			} else{ 
				if(getOrient.getWidth() < getOrient.getHeight()){
					return true;
				}else { 
					return false;
				}
			}
		}


	}



	public static Bitmap loadResizeImageFitToScreen(String str , int width , int height,boolean largeFit)
	{
		try {
			float newWidth = 0 , newHeight = 0;

			String specific =null;
			if(isPortrait())
			{
				specific = ACTUL_IMAGE_WIDTH_RESOLUTION + "x" + ACTUL_IMAGE_HEIGHT_RESOLUTION;
			}else{
				specific = ACTUL_IMAGE_HEIGHT_RESOLUTION + "x" + ACTUL_IMAGE_WIDTH_RESOLUTION;
			}
			if(checkFilePresent(str, specific))
			{
				Bitmap b = createImage(str);
				return b;
			}



			String name = nameCommanGroup(ACTUL_IMAGE_WIDTH_RESOLUTION,ACTUL_IMAGE_HEIGHT_RESOLUTION);
			if(name != null)
			{
				if(checkFilePresent(str, name))
				{
					Bitmap b =   createImage(str);
					return b;
				}
			}
			int consideredWidth = CONSIDERED_IMAGE_WIDTH_RESOLUTION;
			int consideredHeight = CONSIDERED_IMAGE_HEIGHT_RESOLUTION;

			String aspectRatioDir = getAspectRatioPath(str);
			if(aspectRatioDir != null)
			{
				consideredWidth = getAspectRatioWidth(aspectRatioDir);
				consideredHeight = getAspectRatioHeight(aspectRatioDir);

				if(!isPortrait())
				{
					int tmp = consideredWidth;
					consideredWidth = consideredHeight;
					consideredHeight = tmp;
				}
			}

			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			BitmapFactory.decodeStream(context.getAssets().open(getBasePath(str)+str), null, options);

			int newBoundWidth  = options.outWidth;
			int newBoundHeight  = options.outHeight;


			if(largeFit)
			{
				double widthScale = 0, heightScale = 0;
				widthScale = (double)ACTUL_IMAGE_WIDTH_RESOLUTION / (double)newBoundWidth;

				heightScale = (double)ACTUL_IMAGE_HEIGHT_RESOLUTION / (double)newBoundHeight;                

				double scale = Math.max(widthScale, heightScale);

				newWidth = (float) Math.abs(newBoundWidth * scale);
				newHeight = (float) Math.abs(newBoundHeight * scale);
			}else{
				if(width == -1)
					newWidth = newBoundWidth;
				else
					newWidth = (newBoundWidth * width) / consideredWidth;

				if(height == -1)
					newHeight = newBoundHeight;
				else
					newHeight = (newBoundHeight * height) / consideredHeight;
			}

			if((newBoundWidth == newWidth && newBoundHeight == newHeight) || (width == consideredWidth && height == consideredHeight))
			{
				if(resizePercentX == 0 && resizePercentY== 0)
				{

					Bitmap b= createImage(str);
					return b;
				}else{
					newWidth = (newBoundWidth * resizePercentX) / 100;
					newHeight = (newBoundHeight * resizePercentY) / 100;
					Bitmap b=  decodeSampledBitmapFromResource(str, (int)newWidth,(int)newHeight, options);
					return b;
				}
			}
			Bitmap b=   decodeSampledBitmapFromResource(str, (int)newWidth,(int)newHeight, options);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null; 
	}

}