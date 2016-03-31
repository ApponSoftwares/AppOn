/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib;

import com.appon.animlib.util.Util;

import java.io.ByteArrayInputStream;



//#ifdef DEKSTOP_TOOL
//# import javax.imageio.ImageIO;
//# import java.awt.Image;
//#elifdef ANDROID
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//#endif
import android.graphics.Matrix;

/**
 *
 * @author Swaroop1
 */
public class ImagePack {
	/** For Image Formate */
	public final static int IMAGE_RAW_PNG_DATA = 0x00;
	public final static int IMAGE_DATA_ONLY = 0x01;
	// #ifdef ANDROID
	public static float perX;
	public static float perY;
	private Bitmap[] modules;
	// #else
	// # private Image[] modules;
	// #endif
	private int x[];
	private int y[];
	private int width[];
	private int height[];
	private static boolean _bDebug = false;
	private int packType = 0;

	// #ifdef ANDROID
	public Bitmap getImage(int index) {
		return modules[index];
	}
	// #endif
	public void load(byte file[]) {
		load(file, 0, 0);
	}
	public void load(byte file[], int xscale, int yscale) {
		try {
			int offset = 0;
			// reading version number
			int version_no = file[offset++]; // 1 byte
			if (_bDebug)
				System.out.println("version_no:" + version_no);
			// reading pixel format
			packType = file[offset++]; // 1 byte

			if (_bDebug)
				System.out.println("PackType:" + packType);

			int totalModules = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF) << 8); // 2
																							// bytes

			if (packType == IMAGE_RAW_PNG_DATA) {
				// #ifdef ANDROID
				modules = new Bitmap[totalModules];
				// #else
				// # modules = new Image[totalModules];
				// #endif

				for (int i = 0; i < totalModules; i++) {
					int size = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF) << 8) + ((file[offset++] & 0xFF) << 16) + ((file[offset++] & 0xFF) << 24);
					// #ifdef ANDROID
					Bitmap b = BitmapFactory.decodeByteArray(file, offset, size);
					// modules[i] = b;

					modules[i] = resizeImageWithTransperency(b, Util.getScaleValue(b.getWidth(), xscale), Util.getScaleValue(b.getHeight(), yscale));
					// #else
					// # modules[i] = ImageIO.read(new
					// ByteArrayInputStream(file, offset, size));
					// #
					// #endif

					offset += size;

				}
			} else {

				x = new int[totalModules];
				y = new int[totalModules];
				width = new int[totalModules];
				height = new int[totalModules];
				for (int i = 0; i < totalModules; i++) {
					x[i] = bytesToInt(file, offset, 2);
					offset += 2;
					y[i] = bytesToInt(file, offset, 2);
					offset += 2;
					width[i] = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF) << 8);
					height[i] = ((file[offset++] & 0xFF)) + ((file[offset++] & 0xFF) << 8);
					if (_bDebug)
						System.out.println("x: " + x[i] + "y: " + y[i] + "Width: " + width[i] + " Height: " + height[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int bytesToIntWithSign(byte[] buffer, int offset, int numOfBytes) {
		int value = bytesToInt(buffer, offset, numOfBytes);
		int signBit = 0;
		int valueWitoutSign = value;
		if (numOfBytes == 1) {
			signBit = value & 0x80;
			valueWitoutSign = valueWitoutSign & 0x7f;
		} else {
			signBit = value & 0x8000;
			valueWitoutSign = valueWitoutSign & 32767;
		}
		if (signBit > 0) {
			valueWitoutSign = -valueWitoutSign;
		}
		return valueWitoutSign;
	}
	public int bytesToInt(byte[] buffer, int offset, int numOfBytes) {
		int value = 0;
		for (int i = 0; i < numOfBytes; i++) {
			value += (buffer[offset++] & 0xff) << (8 * i);
		}
		return value;

	}

	static Matrix matrix = new Matrix();
	// #ifdef ANDROID
	public static Bitmap resizeImageWithTransperency(Bitmap bm, int newWidth, int newHeight) {
		return com.appon.gamehelper.Util.resizeImageWithTransperency(bm, newWidth, newHeight);
	}
	// #endif
}
