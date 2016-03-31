package com.appon.gtantra;



/*
 /*
 * DimondPick.java
 *
 * Created on October 23, 2007, 10:24 PM
 */

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appon.gamehelper.Resources;
import com.appon.gamehelper.Util;






/**
 *
 * @author  Swaroop.Kumar
 * @version
 */

public class GTantra {

	public static final boolean _bDebug = false;
	public static final boolean _bDebugFont = false;
	/** Creates a new instance of GTantra */
	public  int version_no;
	public int _nModules;
	public int _nFrames;
	public int _nAniamtion;
	public int _nImagesUsed;
	public boolean _bIsCollisionInfo=false;
	// for modules
	public Bitmap _module_images[][];
	static boolean processAlpha=false;
	public int _width[];
	public int _height[];
	int _modulesX[];
	int _modulesY[];
	// private int _iImageIndex[];
	// for frames info
	public int _iFramesModCnt[];
	public int _iFrameModules[][];
	public int _iFrameModX[][];
	public int _iFrameModY[][];
	// frame collision
	public int _iFrameCollX[][];
	private int _iFrameCollY[][];
	private int _iFrameCollWidth[][];
	private int _iFrameCollHeight[][];
	// for animation data
	public int _iAnimFrameCnt[];
	public int _iAnimFrames[][];
	public int _iAnimFrameX[][];
	public int _iAnimFrameY[][];
	/** For Image Formate */
	public int _iImageFormate;
	final static int IMAGE_1=0X01;   // for 1 bit
	final static int IMAGE_4=0X02;   // for 2 bit
	final static int IMAGE_16=0X03;  // for 4 bit
	final static int IMAGE_256=0X04; // for 8 bit
	final static int IMAGE_24_BIT=0X05; // for 8 bit
	final static int IMAGE_RAW_PNG_DATA=0X06; // for 8 bit
	/** For pixel formate */
	public static int _iPixelFormate;
	final static int PIXEL_8888=0x00;
	final static int PIXEL_0888=0x01;
	final static int PIXEL_4444=0x02;
	final static int PIXEL_0333=0x03;
	final static int PIXEL_0555=0x04;
	final static int PIXEL_0565=0x05;
	////-----------------------------

	int colors[][] = new int[256][];
	static int _iNColors ;
	static int modulePixels[];
	byte module_flag[][];
	byte frame_flag[][];
	byte frameTimer[][];
	int localTimeCounter[][][];

	/// --- For Image Decoading-----------
	byte pixel_locations[];
	byte image_data[];
	int module_data_off[];
	int num_pal = 0;
	int crt_pal = 0;
	////----- for GT drawing
	public final static byte FLAG_FLIP_X	= 0x01;
	public final static byte FLAG_FLIP_Y	= 0x02;
	int currentFrame[][];
	private Bitmap projectImage;
	private int bytesToRead = 1;
	public static int resizeGlobalPercentage = 0;
	public static int resizePercentage = -1;
	private boolean isFont = false;
	private boolean isloaded = false;
	public GTantra() {
		isloaded = false;
	}
	public boolean Isloaded() {
		return this.isloaded;
	}

	public void setFont(boolean isFont) {
		this.isFont = isFont;
	}
	public int getTotalAnimations()
	{
		return _nAniamtion;
	}
	public static void setResizeGlobalPercentage(int resizeGlobalPercentage)
	{
		GTantra.resizeGlobalPercentage = resizeGlobalPercentage;
	}
	public void setResizePercentage(int resizePercentage)
	{
		this.resizePercentage = resizePercentage;
	}
	private int getResizePercentage()
	{
		int val =  resizeGlobalPercentage;
		if(isFont)
			return 0;
		if(resizePercentage != -1 )
		{
			val = resizePercentage;
		}
		return val;
	}
	private static String testValue;
	public  int skipHeightModuleIndexes[];
	public void setSkipHeightModuleIndexes(int[] skipHeightModuleIndexes) {
		this.skipHeightModuleIndexes = skipHeightModuleIndexes;
	}

	public void Change_modulesXY(int from,int to) {
		_module_images[crt_pal][from]=this.getModuleImage(to);
	}

	public void Load(byte file[],boolean doCaching)
	{

		try {


			int offset = 0;
			// reading version number

			version_no = file[offset++];


			if(version_no >= 3)
			{
				bytesToRead = file[offset++]; 
			}
			//       if(_bDebug)
			//               System.out.println("version_no:"+version_no);

			// reading pixel format
			_iPixelFormate = file[offset++];

			//       if(_bDebug)
			//               System.out.println("_iPixelFormate:"+_iPixelFormate);

			// reading image format

			_iImageFormate = file[offset++];




			//       if(_bDebug)
			//               System.out.println("_iImageFormate:"+_iImageFormate);

			// reading number of images used
			// _nImagesUsed = file[offset++];

			// if(_bDebug)
			//         System.out.println("_nImagesUsed:"+_nImagesUsed);

			// reading number of pals used
			num_pal  = ( file[offset++]  );

			//        if(_bDebug)
			//               System.out.println("num_pal:"+num_pal);

			// reading collision info status

			if((file[offset++] & 0xFF) != 0)
			{
				_bIsCollisionInfo = true;

				//           if(_bDebug)
				//               System.out.println("Collision Info present..");
			}
			else
			{
				_bIsCollisionInfo = false;

				//           if(_bDebug)
				//               System.out.println("Collision Info is not present..");
			}

			// reading numaber of colors for all the images
			// reading numaber of colors for all the images
			if(_iImageFormate != IMAGE_24_BIT && _iImageFormate != IMAGE_RAW_PNG_DATA)
			{

				_iNColors = (file[offset++] & 0xFF);

				//       if(_bDebug)
				//          System.out.println(" Total Colors: "+_iNColors);

				num_pal = (num_pal == 0) ? 1 : num_pal;

				processAlpha = false;
				// reading color table
				for(int i=0; i < num_pal ; i++)
				{
					//           System.out.println("COLOR TABEL FOR:"+i);
					colors[i] = new int[_iNColors];
					for (int j = 0; j <_iNColors; j++) {

						if (_iPixelFormate == PIXEL_8888)
						{
							colors[i][j] = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF)<<8) + ((file[offset++] & 0xFF)<<16) + ((file[offset++] & 0xFF)<< 24);
							//                    colors[i][j] = 0x00ffffff;
							if( colors[i][j] == 0 ) colors[i][j] = 1;

							if(colors[i][j] == 0xFF00FF)
							{
								colors[i][j] = 0;
								processAlpha = true;
							}
							else
							{
								colors[i][j] = colors[i][j] * (-1) ;
							}

							colors[i][j] = (colors[i][j] & 0x00FFFFFF) * (-1);



						}
						else if (_iPixelFormate == PIXEL_0888)
						{
							colors[i][j] = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF)<<8) + ((file[offset++] & 0xFF)<<16);
							colors[i][j] = 0x00ffffff;

							if( colors[i][j] == 0 ) colors[i][j] = 1;

							if(colors[i][j] == 0xFF00FF)
							{
								colors[i][j] =0;
								processAlpha = true;
							}
							else
							{
								colors[i][j] = colors[i][j] * (-1) ;
							}

							colors[i][j] = (colors[i][j] & 0x00FFFFFF) * (-1);

						}

					}

				}
			}

			//       if(_bDebug)
			//               System.out.println("Pixel Format:"+_iPixelFormate);

			// reading module numaber
			_nModules = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;


			//        if(_bDebug)
			//               System.out.println("_nModules: "+_nModules);

			// reading module width and height
			//_iImageIndex = new int[_nModules];
			_width = new int[_nModules];
			_height = new int[_nModules];

			if(_iImageFormate == IMAGE_24_BIT)
			{
				_modulesX = new int[_nModules];
				_modulesY = new int[_nModules];
			}
			for (int i = 0; i < _nModules; i++) {

				if(_iImageFormate == IMAGE_24_BIT)
				{
					_modulesX[i] = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF)<<8);
					_modulesY[i] = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF)<<8);
				}

				//_iImageIndex[i] = (file[offset++] & 0xFF);
				_width[i] = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
				_height[i] = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
				if(_height[i] > _iCharCommanHeight)
					_iCharCommanHeight = _height[i];
				//           if(_bDebug)
				//               System.out.println("Width: "+_width[i]+" Height: "+_height[i]);
			}

			// reading frame info
			_nFrames = (file[offset++] & 0xFF);

			//       if(_bDebug)
			//               System.out.println("_nFrames: "+_nFrames);

			_iFramesModCnt = new int[_nFrames];
			_iFrameModules = new int[_nFrames][];
			_iFrameModX = new int[_nFrames][];
			_iFrameModY = new int[_nFrames][];

			module_flag = new byte[_nFrames][];
			if(_bIsCollisionInfo)
			{
				if(version_no == 1)
				{
					_iFrameCollX= new int[_nFrames][1];
					_iFrameCollY= new int[_nFrames][1];
					_iFrameCollWidth= new int[_nFrames][1];
					_iFrameCollHeight= new int[_nFrames][1];
				}else{
					_iFrameCollX= new int[_nFrames][];
					_iFrameCollY= new int[_nFrames][];
					_iFrameCollWidth= new int[_nFrames][];
					_iFrameCollHeight= new int[_nFrames][];
				}
			}

			for (int i = 0; i < _nFrames; i++) {

				_iFramesModCnt[i] =  bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
				_iFrameModules[i] = new int[_iFramesModCnt[i]];
				_iFrameModX[i]    = new int[_iFramesModCnt[i]];
				_iFrameModY[i]    = new int[_iFramesModCnt[i]];
				module_flag[i]    = new byte[_iFramesModCnt[i]];
				for (int j = 0; j < _iFramesModCnt[i] ; j++) {

					_iFrameModules[i][j] = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
					_iFrameModX[i][j] =  bytesToIntWithSign(file, offset, bytesToRead); offset += bytesToRead;
					_iFrameModY[i][j] = bytesToIntWithSign(file, offset, bytesToRead); offset += bytesToRead;
					module_flag[i][j] = (byte)(file[offset++] & 0xFF);
				}
				if(_bIsCollisionInfo)
				{
					if(version_no == 1)
					{
						_iFrameCollX[i][0] = byteToIntWithSign((byte)(file[offset++] & 0xFF));
						_iFrameCollY[i][0]= byteToIntWithSign((byte)(file[offset++] & 0xFF));
						_iFrameCollWidth[i][0]  = (file[offset++] & 0xFF);
						_iFrameCollHeight[i][0] = (file[offset++] & 0xFF);
					}else{
						int totalCollisionRectangles = (file[offset++] & 0xFF);
						_iFrameCollX[i] = new int[totalCollisionRectangles];
						_iFrameCollY[i] = new int[totalCollisionRectangles];
						_iFrameCollWidth[i] = new int[totalCollisionRectangles];
						_iFrameCollHeight[i] = new int[totalCollisionRectangles];
						for (int j = 0; j < totalCollisionRectangles; j++) {
							_iFrameCollX[i][j] =  bytesToIntWithSign(file, offset, bytesToRead); offset += bytesToRead;
							_iFrameCollY[i][j]=  bytesToIntWithSign(file, offset, bytesToRead); offset += bytesToRead;
							_iFrameCollWidth[i][j]  = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
							_iFrameCollHeight[i][j] = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
						}
					}


				}
				else
				{
					_iFrameCollX = null;
					_iFrameCollY = null;
					_iFrameCollWidth = null;
					_iFrameCollHeight = null;
				}

			}

			// reading animation info
			_nAniamtion = (file[offset++] & 0xFF);

			_iAnimFrameCnt = new int[_nAniamtion];
			_iAnimFrames = new int[_nAniamtion][];
			_iAnimFrameX = new int[_nAniamtion][];
			_iAnimFrameY = new int[_nAniamtion][];
			frame_flag   = new byte[_nAniamtion][];
			frameTimer        = new byte[_nAniamtion][];
			for (int i = 0; i < _nAniamtion; i++) {

				_iAnimFrameCnt[i] = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
				_iAnimFrameX[i] = new int[_iAnimFrameCnt[i]];
				_iAnimFrameY[i] = new int[_iAnimFrameCnt[i]];
				_iAnimFrames[i] = new int[_iAnimFrameCnt[i]];
				frame_flag[i]   = new byte[_iAnimFrameCnt[i]];
				frameTimer[i]   = new byte[_iAnimFrameCnt[i]];

				for (int j = 0; j < _iAnimFrameCnt[i]; j++) {

					_iAnimFrames[i][j] = bytesToInt(file, offset, bytesToRead); offset += bytesToRead;
					_iAnimFrameX[i][j] = bytesToIntWithSign(file, offset, bytesToRead); offset += bytesToRead;
					_iAnimFrameY[i][j] = bytesToIntWithSign(file, offset, bytesToRead); offset += bytesToRead;
					frame_flag[i][j] = (byte)(file[offset++] & 0xFF);
					frameTimer[i][j] = (byte)(file[offset++] & 0xFF);

				}
			}
			if(_iImageFormate == IMAGE_RAW_PNG_DATA)
			{
				//    	   _module_images = new Bitmap[1][_nModules];
				//           for (int i = 0; i < _nModules; i++) {
				//               int size = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF)<<8) + ((file[offset++] & 0xFF)<<16) + ((file[offset++] & 0xFF)<< 24);
				//               _module_images[0][i] = BitmapFactory.decodeByteArray(file, offset, size);
				//               offset += size;
				//           }

				_module_images = new Bitmap[1][_nModules];
				for (int i = 0; i < _nModules; i++) {
					int size = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF)<<8) + ((file[offset++] & 0xFF)<<16) + ((file[offset++] & 0xFF)<< 24);
					Bitmap b =  BitmapFactory.decodeByteArray(file, offset, size);
					if(getResizePercentage() == 0)
					{
						_module_images[0][i] = b;
					}else{
						_module_images[0][i] =  Util.resizeImageWithTransperency(b, (b.getWidth() * getResizePercentage()) / 100, (b.getHeight() * getResizePercentage()) / 100);
						//            	   b.recycle();
						b = null;
					}

					offset += size;
				}

			}
			else if(_iImageFormate != IMAGE_24_BIT)
			{
				_module_images = new Bitmap[num_pal][_nModules];

				// reading image data
				int image_data_length = 0;
				int mod_data_length = 0;
				module_data_off = new int[_nModules];
				for(int x = 0 ; x < _nModules ; x++)
				{
					mod_data_length = _width[x]*_height[x];
					if(_iImageFormate == IMAGE_1)
					{
						mod_data_length = ( mod_data_length >> 3 );
						if ((_width[x]*_height[x]) % 8 != 0)
						{
							mod_data_length ++;
						}
					}
					else if(_iImageFormate == IMAGE_4)
					{
						mod_data_length = ( mod_data_length >> 2);
						if ((_width[x]*_height[x]) % 4 != 0)
						{
							mod_data_length ++;
						}
					}
					else if(_iImageFormate == IMAGE_16)
					{
						mod_data_length = ( mod_data_length >> 1 );
						if ((_width[x]*_height[x]) % 2 != 0)
						{
							mod_data_length ++;
						}
					}
					else if(_iImageFormate == IMAGE_256)
					{

					}
					module_data_off[x] = mod_data_length;
					image_data_length += mod_data_length;
				}
				image_data = new byte[image_data_length];
				for(int x =0 ; x <image_data_length ; x++)
				{
					image_data[x] = file[offset++];
				}
				// trying to make images
				//SKP

				/*  int pixels[] = new int [ _iImageLength ] ;
         // getting the actual colors pixels
         for(int i=0 ; i< _iImageLength ; i++)
         {
            //System.out.println("pixels [ "+i+" ] "+pixel_locations[i]);
            pixels[i] = colors[_iImageIndex[x]][pixel_locations[i]] * (-1);
         }

         if(_bDebug)
           System.out.println("pixels length: "+pixels.length+" _width[x]: "+_width[x]+"_height[x]: "+_height[x] +"processAlpha: "+processAlpha);

         _module_images[x] = Image.createRGBImage(pixels,_width[x],_height[x],processAlpha);*/


			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		file = null;
		if(doCaching)
		{
			buildCacheImages();
		}
		resizeVariables();
		animInitilazation();
		isloaded=true;
	}




	private void resizeVariables()
	{
		if(getResizePercentage() == 0)
			return;
		resizeArray(_width);
		resizeArray(_height);
		for (int i = 0; i < _iFrameModX.length; i++) {
			resizeArray(_iFrameModX[i]);
			resizeArray(_iFrameModY[i]);
		}
		if(_iAnimFrameX != null)
		{
			for (int i = 0; i < _iAnimFrameX.length; i++) {
				resizeArray(_iAnimFrameX[i]);
				resizeArray(_iAnimFrameY[i]);
			}
		}
		if(_iFrameCollX != null)
		{
			for (int i = 0; i < _iFrameCollX.length; i++) {
				resizeArray(_iFrameCollX[i]);
				resizeArray(_iFrameCollY[i]);
				resizeArray(_iFrameCollWidth[i]);
				resizeArray(_iFrameCollHeight[i]);
			}
		}

	}
	private void resizeArray(int array[])
	{
		for (int i = 0; i < array.length; i++) {
			array[i] = (array[i]*getResizePercentage()) / 100;
		}
	}
	public boolean isInArray(int index)
	{
		for (int i = 0; i < skipHeightModuleIndexes.length; i++) {
			if(skipHeightModuleIndexes[i]==index)
				return true;
		}
		return false;
	}
	public int bytesToInt(byte[] buffer,int offset,int numOfBytes)
	{
		int value = 0;
		for (int i = 0; i <numOfBytes; i++) {
			value += (buffer[offset++] & 0xff) << (8*i);
		}
		return value;
	}
	public int bytesToIntWithSign(byte[] buffer,int offset,int numOfBytes)
	{
		int value = bytesToInt(buffer, offset,numOfBytes);
		int signBit = 0;
		int valueWitoutSign = value;
		if(numOfBytes == 1)
		{
			signBit = value & 0x80;
			valueWitoutSign = valueWitoutSign & 0x7f;
		}
		else 
		{
			signBit = value & 0x8000;
			valueWitoutSign = valueWitoutSign & 32767;
		}
		if(signBit > 0)
		{
			valueWitoutSign = -valueWitoutSign;
		}
		return valueWitoutSign; 
	}





	public int getFrameMinimumX(int frame)
	{
		return getSmallNumber(_iFrameModX[frame]);
	}
	public int getFrameMinimumY(int frame)
	{
		return getSmallNumber(_iFrameModY[frame]);
	}

	public void buildCacheImages()
	{
		if(_iImageFormate == IMAGE_24_BIT || _iImageFormate == IMAGE_RAW_PNG_DATA)
		{
			return;
		}
		for(int i= 0 ; i < num_pal ; i++)
		{
			BuildImages(i);
		}
		freeImageData();
	}
	private int[] RetriveImageData(int pal , int moduleId , int flags)
	{

		// getting the pixel data
		int _iImageLength = _width[moduleId] * _height[moduleId];
		pixel_locations = new byte [ _iImageLength ];
		byte _bReadingByte=0;
		int _iShiftCounter=0;
		int _ILocationCounter=0;
		int offset = 0;
		for(int i=0 ; i< moduleId ; i++)
		{
			offset += module_data_off[i];
		}
		if(_iImageFormate == IMAGE_1)
		{
			for(int i=0 ; i< _iImageLength ; i++)
			{
				_bReadingByte=(byte)(( image_data[offset++] & 0xFF ));
				//System.out.println("_bReadingByte:"+_bReadingByte);
				pixel_locations[i++] = (byte)( (_bReadingByte >> 0 ) & 0x01  );
				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( (_bReadingByte >> 1 ) & 0x01  );
				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( (_bReadingByte >> 2 ) & 0x01  );
				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( (_bReadingByte >> 3 ) & 0x01  );
				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( (_bReadingByte >> 4 ) & 0x01  );
				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( (_bReadingByte >> 5 ) & 0x01  );
				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( (_bReadingByte >> 6 ) & 0x01  );
				if( i < _iImageLength )
					pixel_locations[i  ] = (byte)( (_bReadingByte >> 7 ) & 0x01  );
			}

		}
		else if(_iImageFormate == IMAGE_4)
		{
			for(int i=0 ; i< _iImageLength ; i++)
			{
				_bReadingByte=(byte)(( image_data[offset++] & 0xFF ));
				//System.out.println("_bReadingByte:"+_bReadingByte);
				pixel_locations[i++] = (byte)(( (_bReadingByte & 0xFF) >> 0 ) & 0x3  );

				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( ( (_bReadingByte & 0xFF) >> 2 ) & 0x3  );

				if( i < _iImageLength )
					pixel_locations[i++] = (byte)( ((_bReadingByte & 0xFF)>> 4 ) & 0x3  );

				if( i < _iImageLength )
					pixel_locations[i  ] = (byte)( ( (_bReadingByte & 0xFF) >> 6 ) & 0x3  );

			}

		}
		else if(_iImageFormate == IMAGE_16)
		{
			for(int i=0 ; i< _iImageLength ; i++)
			{
				_bReadingByte=(byte)(( image_data[offset++] & 0xFF ));
				//System.out.println("_bReadingByte:"+_bReadingByte);
				pixel_locations[i++] = (byte)( (_bReadingByte >> 0 ) & 0x0F  );
				if( i < _iImageLength )
					pixel_locations[i  ] = (byte)( (_bReadingByte >> 4 ) & 0x0F  );

			}

		}
		else if(_iImageFormate == IMAGE_256)
		{
			for(int i=0 ; i< _iImageLength ; i++)
			{
				_bReadingByte=(byte)(( image_data[offset++] & 0xFF ));
				pixel_locations[i] = (byte)(( _bReadingByte ) & 0xFF );
			}
		}

		int pixels[] = new int [_width[moduleId] * _height[moduleId]];


		for(int i=0 ; i< pixels.length ; i++)
		{
			// if(this == CGame._GIngameComponent)
			// System.out.println("i:"+ i +"colors:"+colors.length+"pixel_locations:"+pixel_locations.length+"pixel_locations[i]:"+pixel_locations[i]+"module id:"+moduleId);
			pixels[i] = colors[pal][pixel_locations[i] & 0xFF] ;

		}

		pixel_locations = null;

		int i, j, t, t2, j2,clr;

		if ((flags & FLAG_FLIP_X) != 0)
		{
			t2 = _width[moduleId] * _height[moduleId];
			t  = _width[moduleId]  >> 1;
		for(i = 0; i < t2; i += _width[moduleId])
		{

			for(j = 0, j2 = _width[moduleId]-1; j < t; j++, j2--)
			{
				clr = pixels[i + j];
				pixels[i + j] = pixels[i + j2];
				pixels[i + j2] = clr;
			}
		}
		}
		if ((flags & FLAG_FLIP_Y) != 0)
		{
			for(i = 0, t = 0, t2 = _width[moduleId]*(_height[moduleId]-1); i < (_height[moduleId]>>1); i++, t += _width[moduleId], t2 -= _width[moduleId])
			{

				for(j = 0; j < _width[moduleId]; j++)
				{
					clr = pixels[t + j];
					pixels[t + j] = pixels[t2 + j];
					pixels[t2 + j] = clr;
				}
			}
		}
		return pixels;
	}
	public int getSingleModuleFrameWidth(int frame)
	{
		return _width[_iFrameModules[frame][0]];
	}
	public int getSingleModuleFrameHeight(int frame)
	{
		return _height[_iFrameModules[frame][0]];
	}
	public void BuildImages(int pal)
	{
		//         for(int x = 0 ; x < _nModules ; x++)
		//         {
		//             _module_images[pal][x] = Bitmap.createBitmap(RetriveImageData(pal,x,0), _width[x],_height[x],Bitmap.Config.ARGB_8888);
		//            if(_module_images[pal][x].getHeight() > _iCharCommanHeight)
		//                _iCharCommanHeight = _module_images[pal][x].getHeight();
		//         }

		for(int x = 0 ; x < _nModules ; x++)
		{
			Bitmap b = Bitmap.createBitmap(RetriveImageData(pal,x,0), _width[x],_height[x],Bitmap.Config.ARGB_8888);
			if(getResizePercentage() == 0)
			{
				_module_images[pal][x] = b;
			}else{
				_module_images[pal][x] =  Util.resizeImageWithTransperency(b, (b.getWidth() * getResizePercentage()) / 100,(b.getHeight() * getResizePercentage()) / 100);
				//         	   b.recycle();
				b = null;
			}
			if(skipHeightModuleIndexes!=null)
			{
				if(_module_images[pal][x].getHeight() > _iCharCommanHeight&&!!isInArray(x))
				{
					_iCharCommanHeight = _module_images[pal][x].getHeight();

				}
			}else
			{
				if(_module_images[pal][x].getHeight() > _iCharCommanHeight)
					_iCharCommanHeight = _module_images[pal][x].getHeight();
			}

		}

	}
	public void freeImageData()
	{
		pixel_locations = null;
		image_data = null;
		module_data_off = null;
		//        System.gc();
	}
	private int byteToIntWithSign(byte val)
	{
		int j,num;
		j=val&0x80;
		j= j >>7;
		val = (byte)(val & 0x7f);
		if(j==1)
			num=((int)val)*(-1);
		else
			num=((int)val);
		return (num);
	}

	void DrawAnimation(Canvas g, int animation, int posX, int posY, int flags)
	{
		for (int animFrame = 0; animFrame < _iAnimFrameCnt[animation]; animFrame++)
			DrawAnimationFrame (g, animation, animFrame, posX, posY, flags);
	}

	void animInitilazation()
	{
		currentFrame = new int[_nAniamtion][1];
		localTimeCounter = new int[_nAniamtion][][];
		for(int i=0 ; i<_nAniamtion ; i++)
		{
			currentFrame[i][0] = 0;
			localTimeCounter = new int[_nAniamtion][_iAnimFrameCnt[i]][1];
			for(int j= 0 ; j<_iAnimFrameCnt[i] ; j++)
			{
				localTimeCounter[i][j][0] = 0;
			}
		}


	}

	void setAnimationObjects(int animId , int value)
	{
		currentFrame[animId] = new int[value];
		for(int i = 0 ; i < value ; i++)
		{
			currentFrame[animId][i] = 0;

		}
		for(int j = 0 ; j < _iAnimFrameCnt[animId] ; j ++)
			localTimeCounter[animId][j] = new int[value];
	}

	void render(Canvas g,int animation,int object, int posX, int posY, int flags,boolean loop)
	{
		if(_iAnimFrameCnt[animation] == 0)
			return;

		int currentFrameTime = frameTimer[animation][currentFrame[animation][0]];

		if(currentFrameTime != 0)
		{

			DrawAnimationFrame(g,animation,currentFrame[animation][object],posX,posY,flags);
		}


		localTimeCounter[animation][currentFrame[animation][object]][object]++;


		if(localTimeCounter[animation][currentFrame[animation][object]][object] >= currentFrameTime)
		{
			if( currentFrame[animation][object] < _iAnimFrameCnt[animation] )
			{
				currentFrame[animation][object]++ ;
			}
			if(loop && currentFrame[animation] [object]== (_iAnimFrameCnt[animation] ))
			{
				currentFrame[animation][object] = 0;
			}
			localTimeCounter[animation][currentFrame[animation][object]][object] = 0;
		}
	}
	boolean isAnimationOver(int animation)
	{
		if(_iAnimFrameCnt[animation] - 1 == currentFrame[animation][0])
		{
			return true;
		}
		return false;
	}
	void reset(int animation)
	{
		currentFrame[animation][0] = 0;
	}
	int getCurrentAnimFrameCnt(int animation)
	{
		return currentFrame[animation][0];
	}
	int getCurrentFrame(int animation)
	{
		return _iAnimFrames[animation][currentFrame[animation][0]];
	}
	int getAnimationFrameX(int animation,int frameLocation)
	{
		return _iAnimFrameX[animation][frameLocation];
	}
	int getAnimationFrameY(int animation,int frameLocation)
	{
		return _iAnimFrameY[animation][frameLocation];
	}
	int getAnimationCurrentCycle(int animation)
	{
		return currentFrame[animation][0];
	}
	int getNumberOfFrames(int animation)
	{
		return _iAnimFrameCnt[animation];
	}

	void render(Canvas g, int animation, int posX, int posY, int flags,boolean loop)
	{
		if(_iAnimFrameCnt[animation] == 0)
			return;

		int currentFrameTime = frameTimer[animation][currentFrame[animation][0]];

		if(currentFrameTime != 0)
		{

			DrawAnimationFrame(g,animation,currentFrame[animation][0],posX,posY,flags);
		}


		localTimeCounter[animation][currentFrame[animation][0]][0] ++;

		if(localTimeCounter[animation][currentFrame[animation][0]][0] >= currentFrameTime)
		{
			if( currentFrame[animation][0] + 1 <= _iAnimFrameCnt[animation]  )
			{
				currentFrame[animation][0]++ ;
			}
			if(loop && currentFrame[animation] [0]== (_iAnimFrameCnt[animation] ))
			{
				currentFrame[animation][0] = 0;
			}
			if(localTimeCounter[animation].length < _iAnimFrameCnt[animation])
			{
				localTimeCounter[animation] = new int[_iAnimFrameCnt[animation]][1];
			}
			localTimeCounter[animation][currentFrame[animation][0]][0] = 0;
		}
	}
	public void DrawAnimationFrame(Canvas g, int animation,int _aframe, int posX, int posY, int flags)
	{

		int frame = _iAnimFrames[animation][_aframe];

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= _iAnimFrameX[animation][_aframe];
		else
			posX += _iAnimFrameX[animation][_aframe];
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= _iAnimFrameY[animation][_aframe];
		else
			posY += _iAnimFrameY[animation][_aframe];

		/* if ((flags & FLAG_FLIP_X) != 0)
            posX -= _width[module]&0xFF;
        if ((flags & FLAG_FLIP_Y) != 0)
            posY -= _height[module]&0xFF;*/
            DrawFrame (g,frame, posX, posY, flags ^ (frame_flag[animation][_aframe]&0x0F));
	}
	public void DrawAnimationFrame(Canvas g, int animation,int _aframe, int posX, int posY, int flags,Paint paintObject)
	{
		int frame = _iAnimFrames[animation][_aframe];
		if ((flags & FLAG_FLIP_X) != 0)
			posX -= _iAnimFrameX[animation][_aframe];
		else
			posX += _iAnimFrameX[animation][_aframe];
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= _iAnimFrameY[animation][_aframe];
		else
			posY += _iAnimFrameY[animation][_aframe];

		/* if ((flags & FLAG_FLIP_X) != 0)
            posX -= _width[module]&0xFF;
        if ((flags & FLAG_FLIP_Y) != 0)
            posY -= _height[module]&0xFF;*/
		DrawFrame (g,frame, posX, posY, flags ^ (frame_flag[animation][_aframe]&0x0F),paintObject);
	}
	public void DrawFrame (Canvas g, int frame, int posX, int posY, int flags)
	{
		for (int frameModule = 0; frameModule < _iFramesModCnt[frame]; frameModule++)
			DrawFrameModule (g, frame, frameModule, posX, posY, flags);
	}
	public void DrawFrame (Canvas g, int frame, int posX, int posY, int flags,Paint paint)
	{
		for (int frameModule = 0; frameModule < _iFramesModCnt[frame]; frameModule++)
			DrawFrameModule (g, frame, frameModule, posX, posY, flags,paint);
	}
	void DrawFrameModule (Canvas g, int frame, int frameModule, int posX, int posY, int flags)
	{

		int module = _iFrameModules[frame][frameModule];

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= _iFrameModX[frame][frameModule];
		else
			posX += _iFrameModX[frame][frameModule];
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= _iFrameModY[frame][frameModule];
		else
			posY += _iFrameModY[frame][frameModule];

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= _width[module]&0xFF;
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= _height[module]&0xFF;

		DrawModule (g, module, posX, posY, flags ^ (module_flag[frame][frameModule]&0x0F));
	}
	void DrawFrameModule (Canvas g, int frame, int frameModule, int posX, int posY, int flags,Paint paint)
	{

		int module = _iFrameModules[frame][frameModule];

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= _iFrameModX[frame][frameModule];
		else
			posX += _iFrameModX[frame][frameModule];
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= _iFrameModY[frame][frameModule];
		else
			posY += _iFrameModY[frame][frameModule];

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= _width[module]&0xFFFF;
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= _height[module]&0xFFFF;

		DrawModule (g, module, posX, posY, flags ^ (module_flag[frame][frameModule]&0x0F),paint);
	}
	public void DrawAnimationFrame(Canvas g, int animation,int _aframe, int posX, int posY, int flags,boolean isScale,float scalePercentage,Paint paintObject)
	{

		int frame = _iAnimFrames[animation][_aframe];

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= _iAnimFrameX[animation][_aframe];
		else
			posX += _iAnimFrameX[animation][_aframe];
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= _iAnimFrameY[animation][_aframe];
		else
			posY += _iAnimFrameY[animation][_aframe];
		DrawFrame (g,frame, posX, posY, flags ^ (frame_flag[animation][_aframe]&0x0F),isScale, scalePercentage,paintObject);
	}

	public void DrawFrame (Canvas g, int frame, int posX, int posY, int flags,boolean isScale,float scalePercentage,Paint paintObject)
	{
		for (int frameModule = 0; frameModule < _iFramesModCnt[frame]; frameModule++)
			DrawFrameModule (g, frame, frameModule, posX, posY, flags,true,scalePercentage,paintObject);
	}
	public void DrawAlreadyFlipFrame (Canvas g, int frame, int posX, int posY, int flags,boolean isScale,float scalePercentage,Paint paintObject)
	{
		if(isScale)
		{
			g.save();
			int frameWidth = getFrameWidth(frame);
			int frameHeight = getFrameHeight(frame);
			g.scale(scalePercentage / 100 , scalePercentage / 100 , posX + (frameWidth / 2) , posY + (frameHeight / 2));
		}
		for (int frameModule = 0; frameModule < _iFramesModCnt[frame]; frameModule++)
			DrawFrameModule (g, frame, frameModule, posX, posY, flags,false,scalePercentage,paintObject);
		if(isScale)
			g.restore();
	}
	public void DrawFrame (Canvas g, int frame, int posX, int posY,int centerX,int centerY, int flags,int angle,boolean isScale,float scalePercentage,Paint paintObject)
	{
		for (int frameModule = 0; frameModule < _iFramesModCnt[frame]; frameModule++)
			DrawFrameModule (g, frame, frameModule, posX, posY, centerX, centerY,flags,angle,isScale,scalePercentage,paintObject);
	}
	void DrawFrameModule (Canvas g, int frame, int frameModule, int posX, int posY, int flags,boolean isScale,float scalePercentage,Paint paintObject)
	{

		int module = _iFrameModules[frame][frameModule];

		int moduleX=_iFrameModX[frame][frameModule];
		int moduleY=_iFrameModY[frame][frameModule];
		int width=_width[module]&0xFF;
		int height=_height[module]&0xFF;
		if(isScale)
		{
			moduleX = (int) ((moduleX*scalePercentage) / 100); 
			moduleY = (int) ((moduleY*scalePercentage) / 100);

			int newWidth = (int) ((width*scalePercentage) / 100);
			int newHeight = (int) ((height*scalePercentage) / 100);

			//	       moduleX=moduleX+((width-newWidth)>>1);
			//	       moduleY=moduleY+((height-newHeight)>>1);
			width=newWidth;
			height=newHeight;
		}
		if ((flags & FLAG_FLIP_X) != 0)
			posX -= moduleX;
		else
			posX += moduleX;

		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= moduleY;
		else
			posY += moduleY;

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= width;
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= height;

		DrawModule (g, module, posX, posY, flags ^ (module_flag[frame][frameModule]&0x0F),isScale,scalePercentage,paintObject);
	}
	void DrawFrameModule (Canvas g, int frame, int frameModule, int posX, int posY, int centerX, int centerY, int flags,int angle, boolean isScale,float scalePercentage,Paint paintObject)
	{

		int module = _iFrameModules[frame][frameModule];

		int moduleX=_iFrameModX[frame][frameModule];
		int moduleY=_iFrameModY[frame][frameModule];
		int width=_width[module]&0xFF;
		int height=_height[module]&0xFF;
		if(isScale){
			moduleX = (int) ((moduleX*scalePercentage) / 100);
			moduleY = (int) ((moduleY*scalePercentage) / 100);
			width = (int) ((width*scalePercentage) / 100);
			height = (int) ((height*scalePercentage) / 100);
			centerX= (int) ((centerX*scalePercentage) / 100);
			centerY= (int) ((centerY*scalePercentage) / 100);
		}
		if ((flags & FLAG_FLIP_X) != 0)
			posX -= moduleX;
		else
			posX += moduleX;
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= moduleY;
		else
			posY += moduleY;

		if ((flags & FLAG_FLIP_X) != 0)
			posX -= width;
		if ((flags & FLAG_FLIP_Y) != 0)
			posY -= height;

		DrawModule (g, module, posX, posY,centerX,centerY, flags ^ (module_flag[frame][frameModule]&0x0F),angle,isScale,scalePercentage,paintObject);
	}
	public void setProjectImage(Bitmap projectImage) {
		this.projectImage = projectImage;
	}
	public Bitmap getModuleImage(int module_id)
	{
		int sizeX = _width[module_id]&0xFF;
		int sizeY = _height[module_id]&0xFF;
		Bitmap img = _module_images[crt_pal][module_id];
		if(img == null)
		{
			Bitmap b = Bitmap.createBitmap(RetriveImageData(crt_pal, module_id ,0), sizeX, sizeY, Bitmap.Config.ARGB_8888);
		}
		return img;
	}
	public void DrawModule(Canvas g,int module_id,int posX, int posY,int flag)
	{
		int sizeX = _width[module_id]&0xFF;
		int sizeY = _height[module_id]&0xFF;

		int x = 0;
		int y = 0;

		if(_iImageFormate != IMAGE_24_BIT)
		{
			Bitmap img = _module_images[crt_pal][module_id];
			if(img == null)
			{
				Bitmap b = Bitmap.createBitmap(RetriveImageData(crt_pal, module_id ,flag), sizeX, sizeY, Bitmap.Config.ARGB_8888);
				g.drawBitmap(b, posX, posY,null);
				return;
			}

			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.ROTATE_180,0,null);
				}
				else
				{
					GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.MIRROR,0,null);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180,0,null);
			}
			else
			{
				GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.ORIGINAL,0,null);

			}
		}else{
			x = _modulesX[module_id]&0xFFFF;
			y = _modulesY[module_id]&0xFFFF;
			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.ROTATE_180, posX, posY, 0,null);
				}
				else
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY,GraphicsUtil.MIRROR, posX, posY, 0,null);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180, posX, posY, 0,null);
			}
			else
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.ORIGINAL, posX, posY, 0,null);
			}
		}


	}
	public void DrawModule(Canvas g,int module_id,int posX, int posY,int flag,Paint paint)
	{
		int sizeX = _width[module_id]&0xFF;
		int sizeY = _height[module_id]&0xFF;

		int x = 0;
		int y = 0;

		if(_iImageFormate != IMAGE_24_BIT)
		{
			Bitmap img = _module_images[crt_pal][module_id];
			if(img == null)
			{
				Bitmap b = Bitmap.createBitmap(RetriveImageData(crt_pal, module_id ,flag), sizeX, sizeY, Bitmap.Config.ARGB_8888);
				g.drawBitmap(b, posX, posY,paint);
				return;
			}

			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.ROTATE_180,0,paint);
				}
				else
				{
					GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.MIRROR,0,paint);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180,0,paint);
			}
			else
			{
				GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.ORIGINAL,0,paint);

			}
		}else{
			x = _modulesX[module_id]&0xFFFF;
			y = _modulesY[module_id]&0xFFFF;
			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.ROTATE_180, posX, posY, 0,paint);
				}
				else
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY,GraphicsUtil.MIRROR, posX, posY, 0,paint);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180, posX, posY, 0,paint);
			}
			else
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.ORIGINAL, posX, posY, 0,paint);
			}
		}


	}
	public void DrawModule(Canvas g,int module_id,int posX, int posY,int flag,boolean isScale,float scalePercentage,Paint paintObject)
	{
		int sizeX = _width[module_id]&0xFF;
		int sizeY = _height[module_id]&0xFF;

		int x = 0;
		int y = 0;

		if(_iImageFormate != IMAGE_24_BIT)
		{
			Bitmap img = _module_images[crt_pal][module_id];
			if(img == null)
			{
				Bitmap b = Bitmap.createBitmap(RetriveImageData(crt_pal, module_id ,flag), sizeX, sizeY, Bitmap.Config.ARGB_8888);

				if(isScale)
					GraphicsUtil.paintRescaleImage(g, b, posX, posY,scalePercentage,paintObject);
				else	
					g.drawBitmap(b, posX, posY,paintObject);
				return;
			}

			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.ROTATE_180,0,isScale, scalePercentage,paintObject);
				}
				else
				{
					GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.MIRROR,0,isScale,scalePercentage,paintObject);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180,0,isScale,scalePercentage,paintObject);
			}
			else
			{
				GraphicsUtil.drawRegion(g, img, posX, posY, GraphicsUtil.ORIGINAL,0,isScale,scalePercentage,paintObject);

			}
		}else{
			x = _modulesX[module_id]&0xFFFF;
			y = _modulesY[module_id]&0xFFFF;
			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.ROTATE_180, posX, posY, 0,isScale,scalePercentage,paintObject);
				}
				else
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY,GraphicsUtil.MIRROR, posX, posY, 0,isScale,scalePercentage,paintObject);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180, posX, posY, 0,isScale,scalePercentage,paintObject);
			}
			else
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y, sizeX, sizeY, GraphicsUtil.ORIGINAL, posX, posY, 0,isScale,scalePercentage,paintObject);
			}
		}


	}
	public void DrawModule(Canvas g,int module_id,int posX, int posY,int centerX,int centerY,int flag,int angle,boolean isScale,float scalePercentage,Paint paintObject)
	{
		int sizeX = _width[module_id]&0xFF;
		int sizeY = _height[module_id]&0xFF;

		int x = 0;
		int y = 0;

		if(_iImageFormate != IMAGE_24_BIT)
		{
			Bitmap img = _module_images[crt_pal][module_id];
			if(img == null)
			{
				Bitmap b = Bitmap.createBitmap(RetriveImageData(crt_pal, module_id ,flag), sizeX, sizeY, Bitmap.Config.ARGB_8888);

				if(isScale)
				{
					if(angle!=0)
						GraphicsUtil.paintRoatateRescaleImage(g, b, posX, posY,centerX,centerY, angle, scalePercentage, paintObject);
					else
						GraphicsUtil.paintRescaleImage(g, b, posX, posY,scalePercentage,paintObject);
				}
				else	
				{
					if(angle!=0)
						GraphicsUtil.rotateBitmap(g, b, angle, posX, posY,(GraphicsUtil.HCENTER|GraphicsUtil.VCENTER),paintObject);
					else
						g.drawBitmap(b, posX, posY,null);
				}
				return;
			}

			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion(g, img, posX, posY,centerX,centerY, GraphicsUtil.ROTATE_180,0,angle,isScale, scalePercentage,paintObject);
				}
				else
				{
					GraphicsUtil.drawRegion(g, img, posX, posY,centerX,centerY, GraphicsUtil.MIRROR,0,angle,isScale,scalePercentage,paintObject);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion(g, img, posX, posY,centerX,centerY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180,0,angle,isScale,scalePercentage,paintObject);
			}
			else
			{
				GraphicsUtil.drawRegion(g, img, posX, posY,centerX,centerY, GraphicsUtil.ORIGINAL,0,angle,isScale,scalePercentage,paintObject);

			}
		}else{
			x = _modulesX[module_id]&0xFFFF;
			y = _modulesY[module_id]&0xFFFF;
			if ((flag & FLAG_FLIP_X) != 0)
			{
				if ((flag & FLAG_FLIP_Y) != 0)
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y,centerX,centerY, sizeX, sizeY, GraphicsUtil.ROTATE_180, posX, posY, 0,angle,isScale,scalePercentage,paintObject);
				}
				else
				{
					GraphicsUtil.drawRegion (g,projectImage, x, y,centerX,centerY, sizeX, sizeY,GraphicsUtil.MIRROR, posX, posY, 0,angle,isScale,scalePercentage,paintObject);
				}
			}
			else if ((flag & FLAG_FLIP_Y) != 0)
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y,centerX,centerY, sizeX, sizeY, GraphicsUtil.MIRROR | GraphicsUtil.ROTATE_180, posX, posY, 0,angle,isScale,scalePercentage,paintObject);
			}
			else
			{
				GraphicsUtil.drawRegion (g,projectImage, x, y,centerX,centerY, sizeX, sizeY, GraphicsUtil.ORIGINAL, posX, posY, 0,angle,isScale,scalePercentage,paintObject);
			}
		}


	}
	public int[] getFrameRect(int frame , int posX , int posY ,int arry[])
	{
		int X=getSmallNumber(_iFrameModX[frame]);
		int Y=getSmallNumber(_iFrameModY[frame]);
		int temp1=0;
		int temp2=0;
		temp1=_iFrameModX[frame][0]+ _width[_iFrameModules[frame][0]];
		temp2=_iFrameModY[frame][0]+ _height[_iFrameModules[frame][0]];
		for(int i=0;i<_iFramesModCnt[frame];i++)
		{
			if(temp1<(_iFrameModX[frame][i]+_width[_iFrameModules[frame][i]]))
			{
				temp1=_iFrameModX[frame][i]+_width[_iFrameModules[frame][i]];
			}
			if(temp2<(_iFrameModY[frame][i]+_height[_iFrameModules[frame][i]]))
			{
				temp2=_iFrameModY[frame][i]+_height[_iFrameModules[frame][i]];
			}

		}
		arry[0] = X + posX;
		arry[1] = Y + posY;
		arry[2] =  (temp1-X);
		arry[3] =  (temp2-Y);
		return arry;

	}
	public int[] getCollisionRect(int frameId, int[] tmp,int index)
	{
		if(version_no == 1)
		{
			tmp[0] =  _iFrameCollX[frameId][0];
			tmp[1] =  _iFrameCollY[frameId][0];
			tmp[2] = _iFrameCollWidth[frameId][0];
			tmp[3] = _iFrameCollHeight[frameId][0];
		}else{
			tmp[0] =  _iFrameCollX[frameId][index];
			tmp[1] =  _iFrameCollY[frameId][index];
			tmp[2] = _iFrameCollWidth[frameId][index];
			tmp[3] = _iFrameCollHeight[frameId][index];
		}


		return tmp;
	}
	public int getFrameY(int frame)
	{
		return getSmallNumber(_iFrameModY[frame]);
	}
	public int getFrameX(int frame)
	{
		return getSmallNumber(_iFrameModX[frame]);
	}


	public int getFrameY(int frame,boolean iscale,float per)
	{
		if(iscale)
			return (int) (((getSmallNumber(_iFrameModY[frame]))*per) / 100);
		else	
			return getSmallNumber(_iFrameModY[frame]);
	}
	public int getFrameX(int frame,boolean iscale,float per)
	{
		if(iscale)
			return (int) (((getSmallNumber(_iFrameModX[frame]))*per) / 100);
		else	
			return getSmallNumber(_iFrameModX[frame]);

	}

	public int getMinimumFrameX(int frameId)
	{
		return getSmallNumber(_iFrameModX[frameId]);
	}
	public int getMinimumFrameY(int frameId)
	{
		return getSmallNumber(_iFrameModY[frameId]);
	}
	public int getFrameWidth(int frame)
	{

		int X=getSmallNumber(_iFrameModX[frame]);

		int temp1=0;
		temp1=_iFrameModX[frame][0]+ _width[_iFrameModules[frame][0]];

		for(int i=0;i<_iFramesModCnt[frame];i++)
		{
			if(temp1<(_iFrameModX[frame][i]+_width[_iFrameModules[frame][i]]))
			{
				temp1=_iFrameModX[frame][i]+_width[_iFrameModules[frame][i]];
			}

		}
		return (temp1-X);

	} 

	public int getFrameWidth(int frame,boolean isscale,float percentage)
	{

		int X=getSmallNumber(_iFrameModX[frame]);

		int temp1=0;
		temp1=_iFrameModX[frame][0]+ _width[_iFrameModules[frame][0]];

		for(int i=0;i<_iFramesModCnt[frame];i++)
		{
			if(temp1<(_iFrameModX[frame][i]+_width[_iFrameModules[frame][i]]))
			{
				temp1=_iFrameModX[frame][i]+_width[_iFrameModules[frame][i]];
			}

		}


		if(isscale)
			return (int) (((temp1-X)*percentage) / 100);
		else
			return (temp1-X);
	}

	public int getFrameHeight(int frame,boolean isscale,float percentage)
	{
		int Y=getSmallNumber(_iFrameModY[frame]);
		int temp2=0;

		temp2=_iFrameModY[frame][0]+ _height[_iFrameModules[frame][0]];
		for(int i=0;i<_iFramesModCnt[frame];i++)
		{
			if(temp2<(_iFrameModY[frame][i]+_height[_iFrameModules[frame][i]]))
			{
				temp2=_iFrameModY[frame][i]+_height[_iFrameModules[frame][i]];
			}
		}

		if(isscale)
			return (int) (((temp2-Y)*percentage) / 100);
		else
			return (temp2-Y);
	}
	public int getFrameHeight(int frame)
	{
		int Y=getSmallNumber(_iFrameModY[frame]);
		int temp2=0;

		temp2=_iFrameModY[frame][0]+ _height[_iFrameModules[frame][0]];
		for(int i=0;i<_iFramesModCnt[frame];i++)
		{
			if(temp2<(_iFrameModY[frame][i]+_height[_iFrameModules[frame][i]]))
			{
				temp2=_iFrameModY[frame][i]+_height[_iFrameModules[frame][i]];
			}
		}

		return (temp2-Y);
	}

	public void setCurrentPallete(int pal)
	{
		pallate_backup = crt_pal = pal;

	}
	private int pallate_backup;
	public void setCurrentPalleteTemp(int pal)
	{
		pallate_backup = crt_pal;
		crt_pal = pal;
	}
	protected int getSmallNumber(int arry[])
	{
		int temp=0;
		int temp2=0;
		if(arry.length!=0)
		{
			temp=arry[0];
			for(int i=0;i<arry.length;i++)
			{
				if(temp>arry[i])
				{
					temp=arry[i];

				}
			}
		}
		return temp;


	}


	public int _iSpaceCharWidth   = 0;
	public int _iCharCommanHeight = 0;

	public static final int FONT_FRAME_ID = 0;

	public static int EXTRA_SPACE_WIDTH = 0;
	public static int EXTRA_SPACE_HEIGHT = 3;
	public int getFontHeight()
	{

		return _iCharCommanHeight;
	}
	public void setFontHeight(int height)
	{
		_iCharCommanHeight = height;
	}

	private int extraFontHeight;
	public void setExtraFontHeight(int extraFontHeight) {
		this.extraFontHeight = extraFontHeight;
	}

	public static byte[] getFileByteData(String path,Context context)
	{
		testValue = path;
		if(path.startsWith("/"))
		{
			path = path.substring(1, path.length());
		}
		//		if(path.indexOf(".") != -1)
			//		{
			//		   path = path.substring(0, path.indexOf("."));
			//	   	}
		//    	int resID = context.getResources().getIdentifier(path , "drawable", context.getPackageName());
		//
		//        InputStream _is = context.getResources().openRawResource(resID);

		int file_size = 0;
		byte buffer[]  = null;
		try{
			InputStream _is =null;
			_is = context.getAssets().open(Resources.getBasePath(path) + path);	

			file_size = _is.available();
			buffer = new byte[file_size];
			_is.read(buffer);
			_is.close();
		}catch(Exception e)
		{
			System.out.println("Problem while loading asset "+(path));
			e.printStackTrace();
		}
		return buffer;
	}
	public int getSpaceCharactorWidth()
	{

		return _iSpaceCharWidth;
	}
	public void setSpaceCharactorWidth(int width)
	{
		_iSpaceCharWidth = width;
	}


	public int getCharCommanHeight() {
		return _iCharCommanHeight;
	}
	public int getTotalAnimationFrames(int animId)
	{
		return  _iAnimFrameCnt[animId];
	}
	public int getFrameId(int animId, int index)
	{
		return _iAnimFrames[animId][index];
	}
	public int getModuleHeight(int moduleID){
		return _height[moduleID];
	}
	public int getModuleHeight(int moduleID,boolean isscale,int percentage){

		if(isscale)
			return (int) (((_height[moduleID])*percentage) / 100);
		else
			return (_height[moduleID]);
	}
	public int getModuleWidth(int moduleID){
		return _width[moduleID];
	}
	public int getModuleWidth(int moduleID,boolean isscale,int percentage){

		if(isscale)
			return (int) (((_width[moduleID])*percentage) / 100);
		else
			return (_width[moduleID]);
	}

	public int getFrameX(int animId, int frameIndex)
	{
		return _iAnimFrameX[animId][frameIndex];
	}
	public int getFrameY(int animId, int frameIndex)
	{
		return _iAnimFrameY[animId][frameIndex];
	}
	public int getFirstModuleWidth(int frameId)
	{
		return _width[_iFrameModules[frameId][0]];
	}
	public int getFirstModuleHeight(int frameId)
	{
		return _height[_iFrameModules[frameId][0]];
	}
	public void unload()
	{
		_module_images = null;
		freeImageData();
		_width = null;
		_height = null;
		_iFramesModCnt = null;
		_iFrameModules = null;
		_iFrameModX = null;
		_iFrameModY = null;
		_iFrameCollX = null;
		_iFrameCollY = null;
		_iFrameCollWidth= null;
		_iFrameCollHeight=null;
		_iAnimFrameCnt = null;
		_iAnimFrames = null;
		_iAnimFrameX = null;
		_iAnimFrameY = null;
		modulePixels = null;
		module_flag = null;
		frame_flag = null;
		frameTimer = null;
		isloaded=false;
	}

}