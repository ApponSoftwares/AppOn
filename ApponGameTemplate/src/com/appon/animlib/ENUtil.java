/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib;

//#ifdef ANDROID
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
//#endif



import com.appon.animlib.util.APSerilize;
import com.appon.gamehelper.Resources;

/**
 *
 * @author Swaroop1
 */
public class ENUtil {
	public static final int READING_ENGINE_VERSION_NO = 1;
	public static ENAnimationGroup loadAnimationGroup(byte data[]) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int versionNo = bis.read();
		ENAnimationGroup group = (ENAnimationGroup) APSerilize.deserialize(bis, APSerilize.getInstance());
		return group;
	}
	// #ifdef ANDROID
	public static byte[] getFileByteData(String path, Context context) {

		if (path.startsWith("/")) {
			path = path.substring(1, path.length());
		}
		Log.v("RT", "GT Path: " + path);
		int file_size = 0;
		byte buffer[] = null;
		try {
			InputStream _is = null;
			_is = context.getAssets().open(Resources.getBasePath(path) + path);

			file_size = _is.available();
			buffer = new byte[file_size];
			_is.read(buffer);
			_is.close();
		} catch (Exception e) {
			System.out.println("Problem while loading asset " + (path));
			e.printStackTrace();
		}

		return buffer;
	}
	// #endif
}
