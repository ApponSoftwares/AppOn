/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.util;

import java.util.Stack;



//#ifdef J2ME
//# import javax.microedition.lcdui.Graphics;
//#elifdef ANDROID
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
//#elifdef DEKSTOP_TOOL
//# import java.awt.Graphics;
//# import java.awt.Color;
//#endif



import com.appon.animlib.basicelements.APShapeElement;

/**
 *
 * @author acer
 */
public class Util {

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
	public static double getMinimumNumber(double array[]) {
		double value = Integer.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			if (value > array[i]) {
				value = array[i];
			}
		}
		return value;
	}
	public static double getMaximumNumber(double array[]) {
		double value = Integer.MIN_VALUE;
		for (int i = 0; i < array.length; i++) {
			if (value < array[i]) {
				value = array[i];
			}
		}
		return value;
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
	public static int getColorAlphaRemover(int color) {

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
	public static int getColor(int color) {

		return color;

	}
	// #endif

	// #ifdef ANDROID
	public static void drawPolygon(Canvas g, double xPoints[], double yPoints[], int thickness, Paint paintObj)
	// #else
	// # public static void drawPolygon(Graphics g, double xPoints[], double
	// yPoints[],int thickness)
	// #endif

	{

		// #ifdef ANDROID
		float strokeBackup = paintObj.getStrokeWidth();
		paintObj.setStrokeWidth(thickness);
		paintObj.setStyle(Paint.Style.STROKE);
		Path p = new Path();
		p.moveTo(Util.roundTheValues(xPoints[0]), Util.roundTheValues(yPoints[0]));
		for (int i = 1; i < yPoints.length; i++) {
			p.lineTo(Util.roundTheValues(xPoints[i]), Util.roundTheValues(yPoints[i]));
		}
		p.lineTo(Util.roundTheValues(xPoints[0]), Util.roundTheValues(yPoints[0]));
		g.drawPath(p, paintObj);
		paintObj.setStrokeWidth(strokeBackup);
		// #else
		// # int max = yPoints.length - 1;
		// # for(int i = 0; i < max; i++)
		// # {
		// # drawThickLine(g, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i
		// + 1], thickness);
		// # }
		// # drawThickLine(g, xPoints[max], yPoints[max], xPoints[0],
		// yPoints[0], thickness);
		// #endif

	}

	// #ifdef ANDROID
	public static void fillPolygon(Canvas g, double xPoints[], double yPoints[], Paint paintObj)
	// #else
	// # public static void fillPolygon(Graphics g, double xPoints[], double
	// yPoints[])
	// #endif

	{
		// #ifdef ANDROID
		paintObj.setStyle(Paint.Style.FILL);
		Path p = new Path();
		p.moveTo(Util.roundTheValues(xPoints[0]), Util.roundTheValues(yPoints[0]));
		for (int i = 1; i < yPoints.length; i++) {
			p.lineTo(Util.roundTheValues(xPoints[i]), Util.roundTheValues(yPoints[i]));
		}
		p.lineTo(Util.roundTheValues(xPoints[0]), Util.roundTheValues(yPoints[0]));
		g.drawPath(p, paintObj);
		// #else
		// # Stack stack = new Stack();
		// # fillPolygon(g, xPoints, yPoints, stack);
		// # // for(; !stack.isEmpty(); fillPolygon(g, (double[])stack.pop(),
		// (double[])stack.pop(), stack)) { }
		// #endif

	}
	private static double xTrianglePoints[] = new double[3];
	private static double yTrianglePoints[] = new double[3];

	private static int xTrianglePointsInt[] = new int[3];
	private static int yTrianglePointsInt[] = new int[3];
	// #ifdef ANDROID
	public static void fillTriangle(Canvas g, double x1, double y1, double x2, double y2, double x3, double y3, Paint p)
	// #else
	// # public static void fillTriangle(Graphics g,double x1,double y1,double
	// x2,double y2,double x3,double y3)
	// #endif

	{
		xTrianglePoints[0] = Util.roundTheValues(x1);
		yTrianglePoints[0] = Util.roundTheValues(y1);
		xTrianglePoints[1] = Util.roundTheValues(x2);
		yTrianglePoints[1] = Util.roundTheValues(y2);
		xTrianglePoints[2] = Util.roundTheValues(x3);
		yTrianglePoints[2] = Util.roundTheValues(y3);
		// #ifdef ANDROID
		fillPolygon(g, xTrianglePoints, yTrianglePoints, p);
		// #elifdef J2ME
		// # g.fillTriangle(xTrianglePoints[0], yTrianglePoints[0],
		// xTrianglePoints[1], yTrianglePoints[1], xTrianglePoints[2],
		// yTrianglePoints[2]);
		// #elifdef DEKSTOP_TOOL
		// # xTrianglePointsInt[0] = Util.roundTheValues(x1);
		// # yTrianglePointsInt[0] = Util.roundTheValues(y1);
		// # xTrianglePointsInt[1] = Util.roundTheValues(x2);
		// # yTrianglePointsInt[1] = Util.roundTheValues(y2);
		// # xTrianglePointsInt[2] = Util.roundTheValues(x3);
		// # yTrianglePointsInt[2] = Util.roundTheValues(y3);
		// # g.fillPolygon(xTrianglePointsInt, yTrianglePointsInt, 3);
		// #endif

	}

	// #ifdef ANDROID
	public static void fillPolygon(Canvas g, double xPoints[], double yPoints[], Stack stack, Paint p)
	// #else
	// # private static void fillPolygon(Graphics g, double xPoints[], double
	// yPoints[], Stack stack)
	// #endif

	{
		if (xPoints.length != yPoints.length)
			return;
		while (yPoints.length > 2) {
			int a = indexOfLeast(xPoints);
			int b = (a + 1) % xPoints.length;
			int c = a <= 0 ? xPoints.length - 1 : a - 1;
			int leastInternalIndex = -1;
			boolean leastInternalSet = false;
			if (yPoints.length > 3) {
				for (int i = 0; i < yPoints.length; i++) {
					if (i != a && i != b && i != c && withinBounds(xPoints[i], yPoints[i], xPoints[a], yPoints[a], xPoints[b], yPoints[b], xPoints[c], yPoints[c]) && (!leastInternalSet || xPoints[i] < xPoints[leastInternalIndex])) {
						leastInternalIndex = i;
						leastInternalSet = true;
					}
				}

			}
			if (!leastInternalSet) {
				// #ifdef ANDROID
				fillTriangle(g, xPoints[a], yPoints[a], xPoints[b], yPoints[b], xPoints[c], yPoints[c], p);
				// #else
				// # fillTriangle(g,xPoints[a], yPoints[a], xPoints[b],
				// yPoints[b], xPoints[c], yPoints[c]);
				// #endif

				// g.fillTriangle(xPoints[a], yPoints[a], xPoints[b],
				// yPoints[b], xPoints[c], yPoints[c]);
				double trimmed[][] = trimEar(xPoints, yPoints, a);
				xPoints = trimmed[0];
				yPoints = trimmed[1];
				continue;
			}
			double split[][][] = split(xPoints, yPoints, a, leastInternalIndex);
			double poly1[][] = split[0];
			double poly2[][] = split[1];
			stack.push(poly2[1]);
			stack.push(poly2[0]);
			stack.push(poly1[1]);
			stack.push(poly1[0]);
			break;
		}
	}
	static boolean withinBounds(double px, double py, double ax, double ay, double bx, double by, double cx, double cy) {
		if (px < min(ax, bx, cx) || px > max(ax, bx, cx) || py < min(ay, by, cy) || py > max(ay, by, cy)) {
			return false;
		}
		boolean sameabc = sameSide(px, py, ax, ay, bx, by, cx, cy);
		boolean samebac = sameSide(px, py, bx, by, ax, ay, cx, cy);
		boolean samecab = sameSide(px, py, cx, cy, ax, ay, bx, by);
		return sameabc && samebac && samecab;
	}

	static double[][][] split(double xPoints[], double yPoints[], int aIndex, int bIndex) {
		int firstLen;
		if (bIndex < aIndex) {
			firstLen = (yPoints.length - aIndex) + bIndex + 1;
		} else {
			firstLen = (bIndex - aIndex) + 1;
		}
		int secondLen = (yPoints.length - firstLen) + 2;
		double first[][] = new double[2][firstLen];
		double second[][] = new double[2][secondLen];
		for (int i = 0; i < firstLen; i++) {
			int index = (aIndex + i) % yPoints.length;
			first[0][i] = xPoints[index];
			first[1][i] = yPoints[index];
		}

		for (int i = 0; i < secondLen; i++) {
			int index = (bIndex + i) % yPoints.length;
			second[0][i] = xPoints[index];
			second[1][i] = yPoints[index];
		}

		double result[][][] = new double[2][][];
		result[0] = first;
		result[1] = second;
		return result;
	}

	static double[][] trimEar(double xPoints[], double yPoints[], double earIndex) {
		double newXPoints[] = new double[xPoints.length - 1];
		double newYPoints[] = new double[yPoints.length - 1];
		double newPoly[][] = new double[2][];
		newPoly[0] = newXPoints;
		newPoly[1] = newYPoints;
		int p = 0;
		for (int i = 0; i < yPoints.length; i++) {
			if (i != earIndex) {
				newXPoints[p] = xPoints[i];
				newYPoints[p] = yPoints[i];
				p++;
			}
		}

		return newPoly;
	}

	static int indexOfLeast(double elements[]) {
		int index = 0;
		double least = elements[0];
		for (int i = 1; i < elements.length; i++) {
			if (elements[i] < least) {
				index = i;
				least = elements[i];
			}
		}

		return index;
	}

	private static boolean sameSide(double p1x, double p1y, double p2x, double p2y, double l1x, double l1y, double l2x, double l2y) {
		double lhs = (p1x - l1x) * (l2y - l1y) - (l2x - l1x) * (p1y - l1y);
		double rhs = (p2x - l1x) * (l2y - l1y) - (l2x - l1x) * (p2y - l1y);
		double product = lhs * rhs;
		boolean result = ((long) product) >= 0L;
		return result;
	}

	private static double min(double a, double b, double c) {
		return Math.min(Math.min(a, b), c);
	}

	private static double max(double a, double b, double c) {
		return Math.max(Math.max(a, b), c);
	}

	public static int approx_distance(int dx, int dy) {
		int min, max;
		if (dx < 0)
			dx = -dx;
		if (dy < 0)
			dy = -dy;
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
	private static double xPoints[] = new double[4];
	private static double yPoints[] = new double[4];

	// #ifdef ANDROID
	public static void drawThickLine(Canvas g, double x1, double y1, double x2, double y2, int thickness, Paint paintObj) {
		// #else
		// # public static void drawThickLine(Graphics g, double x3, double y3,
		// double x4, double y4, int thickness) {
		// #
		// #
		// # int x1 = Util.roundTheValues(x3);
		// # int y1 = Util.roundTheValues(y3);
		// #
		// # int x2 = Util.roundTheValues(x4);
		// # int y2 = Util.roundTheValues(y4);
		// #endif

		// #ifdef ANDROID
		float strokeBackup = paintObj.getStrokeWidth();
		paintObj.setStrokeWidth(thickness);
		paintObj.setStyle(Paint.Style.STROKE);
		g.drawLine(Util.roundTheValues(x1), Util.roundTheValues(y1), Util.roundTheValues(x2), Util.roundTheValues(y2), paintObj);
		paintObj.setStrokeWidth(strokeBackup);
		// #else
		// # // The thick line is in fact a filled polygon
		// #
		// # if(thickness <= 1)
		// # {
		// # g.drawLine(x1, y1, x2, y2);
		// # return;
		// # }
		// # int dX = x2 - x1;
		// # int dY = y2 - y1;
		// # // line length
		// #
		// # int lineLength = approx_distance(dX, dY);
		// #
		// # if(lineLength == 0)
		// # lineLength = 1;
		// # int scale = (thickness << 14) / (2 * lineLength);
		// #
		// # // The x,y increments from an endpoint needed to create a
		// rectangle...
		// # int ddx = -scale * dY;
		// # int ddy = scale * dX;
		// # ddx += ((ddx >> 14) > 0) ? 8192 : -8192;
		// # ddy += ((ddy >> 14) > 0) ? 8192 : -8192;
		// # int dx = (int)ddx >> 14;
		// # int dy = (int)ddy >> 14;
		// #
		// # // Now we can compute the corner points...
		// #
		// #
		// # xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
		// # xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
		// # xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
		// # xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;
		// # fillPolygon(g, xPoints, yPoints);
		// #
		// #endif
	}
	// #ifdef ANDROID
	public static void drawArc(Canvas g, double x, double y, int width, int height, int startangle, int endAngle, int thickness, Paint paintObj)
	// #else
	// # public static void drawArc(Graphics g,int x,int y,int width,int
	// height,int startangle, int endAngle,int thickness)
	// #endif
	{

		// #ifdef ANDROID
		float strokeBackup = paintObj.getStrokeWidth();
		paintObj.setStrokeWidth(thickness);
		paintObj.setStyle(Paint.Style.STROKE);
		g.drawArc(new RectF((int) x, (int) y, (int) x + width, (int) y + height), startangle, endAngle, true, paintObj);
		paintObj.setStrokeWidth(strokeBackup);
		// #else
		// # if(thickness <= 1)
		// # {
		// # g.drawArc(x, y, width, height, startangle, endAngle);
		// # return;
		// # }
		// # for (int i = 0; i < thickness; i++) {
		// # g.drawArc(x + i, y + i, width - (i << 1), height- (i << 1),
		// startangle, endAngle);
		// # }
		// #endif

	}
	// #ifdef ANDROID
	public static void fillArc(Canvas g, double x, double y, int width, int height, int startangle, int endAngle, Paint paintObj)
	// #else
	// # public static void fillArc(Graphics g,int x,int y,int width,int
	// height,int startangle, int endAngle,int thickness)
	// #endif
	{

		// #ifdef ANDROID
		float strokeBackup = paintObj.getStrokeWidth();
		paintObj.setStyle(Paint.Style.FILL);
		g.drawArc(new RectF((int) x, (int) y, (int) x + width, (int) y + height), startangle, endAngle, true, paintObj);
		paintObj.setStrokeWidth(strokeBackup);
		// #else
		// # if(thickness <= 1)
		// # {
		// # g.drawArc(x, y, width, height, startangle, endAngle);
		// # return;
		// # }
		// # for (int i = 0; i < thickness; i++) {
		// # g.drawArc(x + i, y + i, width - (i << 1), height- (i << 1),
		// startangle, endAngle);
		// # }
		// #endif

	}

	// #ifdef ANDROID
	public static void fillRect(int x, int y, int width, int height, Canvas g, Paint paintObj)
	// #else
	// # public static void fillRect(int x,int y,int width,int height,Graphics
	// g)
	// #endif

	{
		// #ifdef ANDROID
		float strokeBackup = paintObj.getStrokeWidth();
		paintObj.setStyle(Paint.Style.FILL);
		g.drawRect((new RectF(x, y, x + width, y + height)), paintObj);
		paintObj.setStrokeWidth(strokeBackup);
		// #else
		// #
		// #endif

	}
	// #ifdef ANDROID
	public static void drawRectangle(Canvas g, int x, int y, int width, int height, int thickness, Paint paintObj)
	// #else
	// # public static void drawRectangle(Graphics g,int x,int y,int width,int
	// height,int thickness)
	// #endif

	{
		// #ifdef ANDROID
		float strokeBackup = paintObj.getStrokeWidth();
		paintObj.setStrokeWidth(thickness);
		paintObj.setStyle(Paint.Style.STROKE);
		g.drawRect((new RectF(x, y, x + width, y + height)), paintObj);
		paintObj.setStrokeWidth(strokeBackup);
		// #else
		// # if(thickness <= 1)
		// # {
		// # g.drawRect(x, y, width, height);
		// # return;
		// # }
		// # for (int i = 0; i < thickness; i++) {
		// # g.drawRect(x + i, y + i, width - (i << 1), height- (i << 1));
		// # }
		// #endif

	}
	public static Point pointToRotate = new Point();

	public static Point rotatePoint(Point pt, double anchorX, double anchorY, double theta, double zoom, APShapeElement shape) {
		if (zoom != 0) {
			pt.x = getScaleValue(pt.x, zoom);
			pt.y = getScaleValue(pt.y, zoom);
		}
		if (theta == 0) {
			return pt;
		}
		pt.x += (shape.getWidth() / 2);
		pt.y += (shape.getHeight() / 2);
		int d = Util.roundTheValues(theta);
		int tmpX1 = Util.roundTheValues(pt.x);
		int tmpY1 = Util.roundTheValues(pt.y);
		double centerX = anchorX;
		double centerY = anchorY;
		tmpX1 -= centerX;
		tmpY1 -= centerY;
		int tmpValue = 0;
		tmpValue = ((tmpX1 * cos(d)) - (tmpY1 * sin(d))) >> 14;
		tmpY1 = ((tmpX1 * sin(d)) + (tmpY1 * cos(d))) >> 14;
		tmpX1 = tmpValue;
		tmpX1 += centerX;
		tmpY1 += centerY;
		pt.x = (int) tmpX1;
		pt.y = (int) tmpY1;
		pt.x -= (shape.getWidth() / 2);
		pt.y -= (shape.getHeight() / 2);
		return pt;
	}
	public static int getScaleValue(int value, int scale) {
		if (scale == 0)
			return value;
		value = (value + ((value * scale) / 100));
		return (int) (value);
	}
	public static double getScaleValue(double value, double scale) {
		if (scale == 0)
			return value;
		value = (value + ((value * scale) / 100));
		return (int) (value);
	}
	public static int getScalePer(int value, int newValue) {
		return ((newValue - value) * 100) / value;
	}
	public static int sin(int theta) {

		theta = theta % 360;
		if (theta < 0)
			theta = 360 - theta;
		return sin_tabel[theta];
	}
	public static int cos(int theta) {
		theta = theta % 360;
		if (theta < 0)
			theta = 360 - theta;
		return cos_tabel[theta];
	}
	public static int roundTheValues(double value) {
		return (int) Math.round(value);
	}
}
