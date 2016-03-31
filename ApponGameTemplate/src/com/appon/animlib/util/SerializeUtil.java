/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.animlib.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//#elifdef J2ME
//# import javax.microedition.lcdui.Image;
//# import javax.microedition.lcdui.Graphics;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Image;
//# import java.awt.Graphics;
//# import java.awt.Color;
//#endif
import java.io.DataInputStream;
import java.io.DataOutputStream;
//#ifdef ANDROID
/**
 *
 * @author Swaroop Kumar
 */
public class SerializeUtil {

	private static int sin_tabel[] = {0, 286, 572, 857, 1143, 1428, 1713, 1997, 2280, 2563, 2845, 3126, 3406, 3686, 3964, 4240, 4516, 4790, 5063, 5334, 5604, 5872, 6138, 6402, 6664, 6924, 7182, 7438, 7692, 7943, 8193, 8438, 8682, 8923, 9162, 9397, 9630, 9860, 10087, 10311, 10531, 10749, 10963,
			11174, 11381, 11585, 11786, 11982, 12176, 12365, 12551, 12733, 12911, 13085, 13255, 13421, 13583, 13741, 13894, 14044, 14189, 14330, 14466, 14598, 14726, 14849, 14968, 15082, 15191, 15296, 15396, 15491, 15582, 15668, 15749, 15826, 15897, 15964, 16026, 16083, 16135, 16182, 16225, 16262,
			16294, 16322, 16344, 16362, 16374, 16382, 16384, 16382, 16374, 16362, 16344, 16322, 16294, 16262, 16225, 16182, 16135, 16083, 16026, 15964, 15897, 15826, 15749, 15668, 15582, 15491, 15396, 15296, 15191, 15082, 14968, 14849, 14726, 14598, 14466, 14330, 14189, 14044, 13894, 13741, 13583,
			13421, 13255, 13085, 12911, 12733, 12551, 12365, 12176, 11982, 11786, 11585, 11381, 11174, 10963, 10749, 10531, 10311, 10087, 9860, 9630, 9397, 9162, 8923, 8682, 8438, 8193, 7943, 7692, 7438, 7182, 6924, 6664, 6402, 6138, 5872, 5604, 5334, 5063, 4790, 4516, 4240, 3964, 3686, 3406, 3126,
			2845, 2563, 2280, 1997, 1713, 1428, 1143, 857, 572, 286, 0, -285, -571, -857, -1142, -1427, -1712, -1996, -2280, -2563, -2845, -3126, -3406, -3685, -3963, -4240, -4516, -4790, -5062, -5334, -5603, -5871, -6137, -6401, -6663, -6924, -7182, -7438, -7691, -7943, -8192, -8438, -8682, -8923,
			-9161, -9397, -9630, -9860, -10086, -10310, -10531, -10748, -10963, -11173, -11381, -11585, -11785, -11982, -12175, -12365, -12550, -12732, -12910, -13084, -13254, -13420, -13582, -13740, -13894, -14043, -14188, -14329, -14466, -14598, -14725, -14848, -14967, -15081, -15190, -15295,
			-15395, -15491, -15582, -15668, -15749, -15825, -15897, -15964, -16025, -16082, -16135, -16182, -16224, -16261, -16294, -16321, -16344, -16361, -16374, -16381, -16384, -16381, -16374, -16361, -16344, -16321, -16294, -16261, -16224, -16182, -16135, -16082, -16025, -15964, -15897, -15825,
			-15749, -15668, -15582, -15491, -15395, -15295, -15190, -15081, -14967, -14848, -14725, -14598, -14466, -14329, -14188, -14043, -13894, -13740, -13582, -13420, -13254, -13084, -12910, -12732, -12550, -12365, -12175, -11982, -11785, -11585, -11381, -11173, -10963, -10748, -10531, -10310,
			-10086, -9860, -9630, -9397, -9161, -8923, -8682, -8438, -8192, -7943, -7691, -7438, -7182, -6924, -6663, -6401, -6137, -5871, -5603, -5334, -5062, -4790, -4516, -4240, -3963, -3685, -3406, -3126, -2845, -2563, -2280, -1996, -1712, -1427, -1142, -857, -571, -285};
	private static int cos_tabel[] = {16384, 16382, 16374, 16362, 16344, 16322, 16294, 16262, 16225, 16182, 16135, 16083, 16026, 15964, 15897, 15826, 15749, 15668, 15582, 15491, 15396, 15296, 15191, 15082, 14968, 14849, 14726, 14598, 14466, 14330, 14189, 14044, 13894, 13741, 13583, 13421, 13255,
			13085, 12911, 12733, 12551, 12365, 12176, 11982, 11786, 11585, 11381, 11174, 10963, 10749, 10531, 10311, 10087, 9860, 9630, 9397, 9162, 8923, 8682, 8438, 8192, 7943, 7692, 7438, 7182, 6924, 6664, 6402, 6138, 5872, 5604, 5334, 5063, 4790, 4516, 4240, 3964, 3686, 3406, 3126, 2845, 2563,
			2280, 1997, 1713, 1428, 1143, 857, 572, 286, 0, -285, -571, -857, -1142, -1427, -1712, -1996, -2280, -2563, -2845, -3126, -3406, -3685, -3963, -4240, -4516, -4790, -5062, -5334, -5603, -5871, -6137, -6401, -6663, -6924, -7182, -7438, -7691, -7943, -8191, -8438, -8682, -8923, -9161,
			-9397, -9630, -9860, -10086, -10310, -10531, -10748, -10963, -11173, -11381, -11585, -11785, -11982, -12175, -12365, -12550, -12732, -12910, -13084, -13254, -13420, -13582, -13740, -13894, -14043, -14188, -14329, -14466, -14598, -14725, -14848, -14967, -15081, -15190, -15295, -15395,
			-15491, -15582, -15668, -15749, -15825, -15897, -15964, -16025, -16082, -16135, -16182, -16224, -16261, -16294, -16321, -16344, -16361, -16374, -16381, -16384, -16381, -16374, -16361, -16344, -16321, -16294, -16261, -16224, -16182, -16135, -16082, -16025, -15964, -15897, -15825, -15749,
			-15668, -15582, -15491, -15395, -15295, -15190, -15081, -14967, -14848, -14725, -14598, -14466, -14329, -14188, -14043, -13894, -13740, -13582, -13420, -13254, -13084, -12910, -12732, -12550, -12365, -12175, -11982, -11785, -11585, -11381, -11173, -10963, -10748, -10531, -10310, -10086,
			-9860, -9630, -9397, -9161, -8923, -8682, -8438, -8192, -7943, -7691, -7438, -7182, -6924, -6663, -6401, -6137, -5871, -5603, -5334, -5062, -4790, -4516, -4240, -3963, -3685, -3406, -3126, -2845, -2563, -2280, -1996, -1712, -1427, -1142, -857, -571, -285, 0, 286, 572, 857, 1143, 1428,
			1713, 1997, 2280, 2563, 2845, 3126, 3406, 3686, 3964, 4240, 4516, 4790, 5063, 5334, 5604, 5872, 6138, 6402, 6664, 6924, 7182, 7438, 7692, 7943, 8192, 8438, 8682, 8923, 9162, 9397, 9630, 9860, 10087, 10311, 10531, 10749, 10963, 11174, 11381, 11585, 11786, 11982, 12176, 12365, 12551,
			12733, 12911, 13085, 13255, 13421, 13583, 13741, 13894, 14044, 14189, 14330, 14466, 14598, 14726, 14849, 14968, 15082, 15191, 15296, 15396, 15491, 15582, 15668, 15749, 15826, 15897, 15964, 16026, 16083, 16135, 16182, 16225, 16262, 16294, 16322, 16344, 16362, 16374, 16382};
	public static int sin(int theta) {
		theta = theta % 360;
		while (theta < 0)
			theta = 360 - theta;
		return sin_tabel[theta];
	}
	public static int cos(int theta) {
		theta = theta % 360;
		while (theta < 0)
			theta = 360 - theta;
		return cos_tabel[theta];
	}

	public static boolean isInRect(int xPos, int yPos, int width, int height, int x, int y) {
		if (x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height) {
			return true;
		}
		return false;
	}

	public static int getApproxDistance(int x1, int y1, int x2, int y2) {
		int dx = Math.abs(x1 - x2);
		int dy = Math.abs(y1 - y2);
		int min, max;
		if (dx < dy) {
			min = dx;
			max = dy;
		} else {
			min = dy;
			max = dx;
		}
		// coefficients equivalent to ( 123/128 * max ) and ( 51/128 * min )
		return (((max << 8) + (max << 3) - (max << 4) - (max << 1) + (min << 7) - (min << 5) + (min << 3) - (min << 1)) >> 8);
	}

	// #ifdef DEKSTOP_TOOL
	// # public static Color getColor(int color)
	// # {
	// # // byte[] b = Util.intToBytes(color, 4);
	// # // byte[] b = Util.intToBytes(0x8fff0000 & 0xffffffff, 4);
	// # long val = color & 0xffffffff;
	// # int b = (int)(val & 0x00FF);
	// # int g = (int) ((val >> 8) & 0x000000FF);
	// # int r = (int) ((val >> 16) & 0x000000FF);
	// # int a = (int) ((val >> 24) & 0x000000FF);
	// # if(a == 0)
	// # {
	// # a = 255;
	// # }
	// # return new Color(r,g,b ,a);
	// # }
	// #elifdef J2ME
	// # public static int getColor(int color)
	// # {
	// # return color & 0xffffff;
	// # }
	// #elifdef ANDROID
	public static int getColor(int color) {
		long val = color & 0xffffffff;
		int b = (int) (val & 0x00FF);
		int g = (int) ((val >> 8) & 0x000000FF);
		int r = (int) ((val >> 16) & 0x000000FF);
		int a = (int) ((val >> 24) & 0x000000FF);
		if (a == 0) {
			a = 255;
		}
		return (a << 24) + (r << 16) + (g << 8) + (b);

	}
	// #endif

	// ////////////writting foundation

	public static int readSignedInt(ByteArrayInputStream bis, int numOfBytes) throws Exception {
		// byte[] data = new byte[numOfBytes];
		// bis.read(data);

		int value = read(bis, numOfBytes);
		int signBit = 0;
		int valueWitoutSign = value;
		if (numOfBytes == 1) {
			signBit = value & 0x80;
			valueWitoutSign = valueWitoutSign & 0x7f;
		} else if (numOfBytes == 2) {
			signBit = value & 0x8000;
			valueWitoutSign = valueWitoutSign & 32767;
		} else if (numOfBytes == 3) {
			signBit = value & 0x800000;
			valueWitoutSign = valueWitoutSign & (0x800000 - 1);
		} else if (numOfBytes == 4) {
			signBit = value & 0x800000;
			valueWitoutSign = valueWitoutSign & (0x800000 - 1);
		}

		if (signBit > 0) {
			valueWitoutSign = -valueWitoutSign;
		}
		return valueWitoutSign;
	}
	public static void writeDouble(ByteArrayOutputStream bos, double i) throws Exception {
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeDouble(i);
		dos.flush();
	}
	public static double readDouble(ByteArrayInputStream bos) throws Exception {
		DataInputStream dis = new DataInputStream(bos);
		return dis.readDouble();
	}
	public static void writeSignedInt(ByteArrayOutputStream bos, int i, int numOfBytes) throws Exception {
		int valueWithoutSign = Math.abs(i);

		int signValue = 1;
		if (i < 0) {
			if (numOfBytes == 1) {
				signValue = 1 << 7;
			} else if (numOfBytes == 2) {
				signValue = 1 << 15;
			} else if (numOfBytes == 3) {
				signValue = 1 << 23;
			} else {
				signValue = 1 << 23;
			}
			valueWithoutSign = signValue | valueWithoutSign;
		}
		write(bos, valueWithoutSign, numOfBytes);
	}
	public static int read(ByteArrayInputStream fis, int noofBytes) throws Exception {

		byte[] data = new byte[noofBytes];
		fis.read(data);
		return byteToint(data, noofBytes);
	}
	public static int byteToint(byte[] temp, int noofBytes) {
		int _lib_pOffset = 0;
		switch (noofBytes) {
			case 1 :
				_lib_pOffset = (temp[0] & 0xFF);
				break;
			case 2 :
				_lib_pOffset = (temp[0] & 0xFF);
				_lib_pOffset += (temp[1] & 0xFF) << 8;
				break;
			case 3 :
				_lib_pOffset = (temp[0] & 0xFF);
				_lib_pOffset += (temp[1] & 0xFF) << 8;
				if (temp[2] == (byte) 1)
					_lib_pOffset = _lib_pOffset * (-1);

				break;
			case 4 :
				_lib_pOffset = (temp[0] & 0xFF);
				_lib_pOffset += (temp[1] & 0xFF) << 8;
				_lib_pOffset += (temp[2] & 0xFF) << 16;
				_lib_pOffset += (temp[3] & 0xFF) << 24;
				break;
		}
		return _lib_pOffset;
	}
	public static void write(ByteArrayOutputStream bos, int value, int noofBytes) throws Exception {
		bos.write(intToBytes(value, noofBytes));
	}

	private static byte[] bytes1 = new byte[1];
	private static byte[] bytes2 = new byte[2];
	private static byte[] bytes3 = new byte[3];
	private static byte[] bytes4 = new byte[4];
	public static byte[] intToBytes(int i, int noofBytes) {
		// System.out.println("wrote offset="+);
		boolean negative = false;
		if (i < 0) {
			negative = true;
			i = i * (-1);
		}
		byte[] dword = null;
		switch (noofBytes) {
			case 1 :
				dword = bytes1;
				dword[0] = (byte) (i & 0x00FF);
				break;
			case 2 :
				dword = bytes2;
				dword[0] = (byte) (i & 0x00FF);
				dword[1] = (byte) ((i >> 8) & 0x000000FF);
				break;
			case 3 :
				dword = bytes3;
				dword[0] = (byte) (i & 0x00FF);
				dword[1] = (byte) ((i >> 8) & 0x000000FF);
				if (negative)
					dword[2] = (byte) 1;
				else
					dword[2] = (byte) 0;
				break;
			case 4 :
				dword = bytes4;
				dword[0] = (byte) (i & 0x00FF);
				dword[1] = (byte) ((i >> 8) & 0x000000FF);
				dword[2] = (byte) ((i >> 16) & 0x000000FF);
				dword[3] = (byte) ((i >> 24) & 0x000000FF);
				break;
		}
		return dword;
	}

	public static void writeInt(ByteArrayOutputStream bos, int value, int numOfBytes) throws Exception {
		bos.write(intToBytes(value, numOfBytes));
	}
	public static void writeBoolean(ByteArrayOutputStream bos, boolean value) throws Exception {
		byte val = 0;
		if (value) {
			val = 1;
		}
		bos.write(val);
	}
	public static boolean readBoolean(ByteArrayInputStream bis) throws Exception {
		byte val = (byte) bis.read();
		return val == 1;
	}
	public static void writeColor(ByteArrayOutputStream bos, int value) throws Exception {
		if (value == -1) {
			bos.write(0);
		} else {
			bos.write(1);
			if (value < 0) {
				bos.write(1);
			} else {
				bos.write(0);
			}
			bos.write(intToBytes(Math.abs(value), 4));
		}

	}
	public static int readColor(ByteArrayInputStream bis) throws Exception {
		int val = bis.read();
		if (val == 0) {
			return -1;
		}
		int sign = bis.read();

		bis.read(bytes4);
		int finalValue = byteToint(bytes4, 4);
		if (sign == 1) {
			finalValue = -finalValue;
		}
		return finalValue;
	}
	public static void writeString(ByteArrayOutputStream bos, String value) throws Exception {
		if (value == null) {
			bos.write(0);
		} else {
			bos.write(1);
			APSerilize.serialize(value, bos);
		}
	}
	public static int readInt(ByteArrayInputStream bis, int numOfBytes) throws Exception {
		byte[] b = getByteArray(numOfBytes);
		bis.read(b);
		return byteToint(b, numOfBytes);
	}
	public static String readString(ByteArrayInputStream bis) throws Exception {
		int value = bis.read();
		if (value == 0)
			return null;
		return (String) Serilize.deserialize(bis, null);
	}
	private static byte[] getByteArray(int numOfBytes) {
		switch (numOfBytes) {
			case 1 :
				return bytes1;
			case 2 :
				return bytes2;
			case 3 :
				return bytes3;
			default :
				return bytes4;
		}
	}

	public static void writeIntArray(ByteArrayOutputStream bos, int value[]) throws Exception {
		if (value == null) {
			bos.write(0);
		} else {
			bos.write(1);
			Serilize.serialize(value, bos);
		}
	}
	public static int[] readIntArray(ByteArrayInputStream bis) throws Exception {
		int value = bis.read();
		if (value == 0)
			return null;
		return (int[]) Serilize.deserialize(bis, null);
	}
	public static void write2DIntArray(ByteArrayOutputStream bos, int value[][]) throws Exception {
		if (value == null) {
			bos.write(0);
		} else {
			bos.write(1);
			Serilize.serialize(value, bos);
		}
	}
	public static int[][] read2DIntArray(ByteArrayInputStream bis) throws Exception {
		int value = bis.read();
		if (value == 0)
			return null;
		return (int[][]) Serilize.deserialize(bis, null);
	}
	public static void writeBooleanArray(ByteArrayOutputStream bos, boolean value[]) throws Exception {
		if (value == null) {
			bos.write(0);
		} else {
			bos.write(1);
			Serilize.serialize(value, bos);
		}
	}
	public static boolean[] readBooleanArray(ByteArrayInputStream bis) throws Exception {
		int value = bis.read();
		if (value == 0)
			return null;
		return (boolean[]) Serilize.deserialize(bis, null);
	}

}
