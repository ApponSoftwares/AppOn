/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.gtantra;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 
 * @author acer
 */
public class GFactory {
	private static int line_width[] = new int[GFont.MAX_LINES_ALLOWED];

	static int getStaticStringHeight(GFont font, String text) {
		int _height = 0;
		for (int i = 0; i < text.length(); i++) {
			if (font.getCharHeight(text.charAt(i)) > _height) {
				_height = font.getCharHeight(text.charAt(i));
			}
		}
		return _height;
	}

	static int getCharHeight(GFont font, char c) {

		if ((int) c == 94) {
			return 0;
		}
		if ((int) c == 32) {
			return font._iSpaceCharWidth;
		}
		int index = font.map_char_arry.indexOf(c);
		if (index < 0 || index >= font.gTantra._iFramesModCnt[GFont.FONT_FRAME_ID]) {
			// if(font._bDebugFont)
			// System.err.println("Invalid Charactor in GetCharWidth");
			return -1;
		}
		index = font.gTantra._iFrameModules[font.FONT_FRAME_ID][index];
		return (font.gTantra._height[index]);
	}

	static int getCharWidth(GFont font, char c) {
		if (isVowel(font, c))
			return 0;
		if ((int) c == 94 || c == '\n') {
			return 0;
		}
		if ((int) c == 32) {
			return font._iSpaceCharWidth;
		}
		int index = font.map_char_arry.indexOf(c);
		if (index < 0 || index >= font.gTantra._iFramesModCnt[font.FONT_FRAME_ID]) {
			// if(font._bDebugFont)
			// System.err.println("Invalid Charactor in GetCharWidth: "+ c
			// +" : " +((int)c));
			return -1;
		}
		index = font.gTantra._iFrameModules[font.FONT_FRAME_ID][index];

		return (font.gTantra._width[index] + font.EXTRA_SPACE_WIDTH);
	}

	static void drawStringSystem(GFont font, Canvas g, String str, int posX, int posY, int anchor) {

		int strHeight = font.getFontStyle().getSystemStringSize(str);
		posY += strHeight;

		if ((anchor & GraphicsUtil.VCENTER) != 0) {
			throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
		}
		if ((anchor & GraphicsUtil.RIGHT) != 0) {
			posX -= font.getSystemStringWidth(str);
		}
		if ((anchor & GraphicsUtil.HCENTER) != 0) {
			posX -= (font.getSystemStringWidth(str) >> 1);
		}
		if ((anchor & GraphicsUtil.BASELINE) != 0) {
			posY -= (font.getFontStyle().getSystemStringSize(str) >> 1);

		}
		if ((anchor & GraphicsUtil.BOTTOM) != 0) {
			posY -= font.getFontStyle().getSystemStringSize(str);
		}
		if (font.isSpecialCharAvailable(str)) {
			font.drawSystemTextWithSpecialChars(g, str, posX, posY, strHeight);
		} else {
			//g.drawText(str, posX, posY, font.getFontStyle().getPrimaryPaintObject());
			font.getFontStyle().drawTextWithStyle(g, str, posX, posY);
		}

	}
	static void drawPageStringSystem(GFont font, Canvas g, String str, int posX, int posY, int anchor) {

		int strHeight = font.getFontStyle().getSystemStringSize(null);
		posY += (strHeight-((strHeight-font.getFontStyle().getSystemStringSize(str))>>1));

		if ((anchor & GraphicsUtil.VCENTER) != 0) {
			throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
		}
		if ((anchor & GraphicsUtil.RIGHT) != 0) {
			posX -= font.getSystemStringWidth(str);
		}
		if ((anchor & GraphicsUtil.HCENTER) != 0) {
			posX -= (font.getSystemStringWidth(str) >> 1);
		}
		if ((anchor & GraphicsUtil.BASELINE) != 0) {
			posY -= (font.getFontStyle().getSystemStringSize(str) >> 1);

		}
		if ((anchor & GraphicsUtil.BOTTOM) != 0) {
			posY -= font.getFontStyle().getSystemStringSize(str);
		}
		if (font.isSpecialCharAvailable(str)) {
			font.drawSystemTextWithSpecialChars(g, str, posX, posY, strHeight);
		} else {
			//g.drawText(str, posX, posY, font.getFontStyle().getPrimaryPaintObject());
			font.getFontStyle().drawTextWithStyle(g, str, posX, posY);
		}

	}
	static void drawStringSystemWithAlpha(GFont font, Canvas g, String str, int posX, int posY, int anchor ,Paint paintObject) 
	{

		int strHeight = font.getFontStyle().getSystemStringSize(str);
		posY += strHeight;

		if ((anchor & GraphicsUtil.VCENTER) != 0) {
			throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
		}
		if ((anchor & GraphicsUtil.RIGHT) != 0) {
			posX -= font.getSystemStringWidth(str);
		}
		if ((anchor & GraphicsUtil.HCENTER) != 0) {
			posX -= (font.getSystemStringWidth(str) >> 1);
		}
		if ((anchor & GraphicsUtil.BASELINE) != 0) {
			posY -= (font.getFontStyle().getSystemStringSize(str) >> 1);

		}
		if ((anchor & GraphicsUtil.BOTTOM) != 0) {
			posY -= font.getFontStyle().getSystemStringSize(str);
		}
		if (font.isSpecialCharAvailable(str)) {
			font.drawSystemTextWithSpecialCharsWithAlph(g, str, posX, posY, strHeight,paintObject);
		} else {
			//g.drawText(str, posX, posY, font.getFontStyle().getPrimaryPaintObject());
			font.getFontStyle().drawTextWithStyleWithAlpha(g, str, posX, posY,paintObject);
		}

	}
	
	static void drawString(GFont font, Canvas g, String str, int posX, int posY, int anchor, Paint paintObject) {
		boolean vowelUsed = false;
		int vowelCounter = 0;

		if ((anchor & GraphicsUtil.VCENTER) != 0){
			throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
		}
		if ((anchor & GraphicsUtil.RIGHT) != 0) {
			posX -= font.getStringWidth(str);
		}
		if ((anchor & GraphicsUtil.HCENTER) != 0) {
			posX -= (font.getStringWidth(str) >> 1);
		}
		if ((anchor & GraphicsUtil.BASELINE) != 0) {
			posY -= (font._iCharCommanHeight >> 1);
		}
		if ((anchor & GraphicsUtil.BOTTOM) != 0) {
			posY -= (font._iCharCommanHeight);
		}
		for (int i = 0; i < str.length(); i++) {
			if ((int) str.charAt(i) == 32) {
				posX += font._iSpaceCharWidth;
			} else {
				int len = font.map_char_arry.length();
				int index = font.map_char_arry.indexOf(str.charAt(i));
				if (index < 0 || index >= font.gTantra._iFramesModCnt[font.FONT_FRAME_ID]) {
					// if(font._bDebugFont)
					// System.err.println("Invalid Charactor In Draw String: "+str.charAt(i));
					continue;
				}
				font.gTantra.DrawFrameModule(g, font.FONT_FRAME_ID, index, posX, posY - font.extraFontHeight, 0, paintObject);
				boolean isVowel = false;
				if (str.length() > i + 1) {
					isVowel = isVowel(font, str.charAt(i + 1));

				}
				if (!isVowel) {
					if (vowelUsed) {
						posX += font.getCharWidth(str.charAt(i - vowelCounter));
						vowelUsed = false;
						vowelCounter = 0;
					} else {
						posX += font.getCharWidth(str.charAt(i));
					}

				} else {
					vowelUsed = true;
					vowelCounter++;
				}

			}
		}
	}

	static String[] split(String text, String delem) {
		Vector texts = new Vector();
		int index = text.indexOf(delem);
		while (index != -1) {
			texts.addElement(text.substring(0, index));
			text = text.substring(index + delem.length(), text.length());
			index = text.indexOf(delem);
		}
		if (text.length() > 0) {
			texts.addElement(text);
		}
		String array[] = new String[texts.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = texts.elementAt(i).toString();
		}
		return array;
	}

	static int getNumberOfLines(GFont font, String str, int width) {
		int numberOfLines = 0;
		LineEnumeration lineEnumaration = new LineEnumeration(font, str, width);
		while (lineEnumaration.hasMoreElements()) {
			String text = lineEnumaration.nextElement().toString();
			if (text.indexOf("\n") != -1) {
				String array[] = split(text, "\n");
				for (int i = 0; i < array.length; i++) {
					String string = array[i];
					numberOfLines++;
				}

			} else {
				numberOfLines++;
			}

		}
		return numberOfLines;
	}

	protected static boolean isVowel(GFont font, char ch) {

		int[] vowels = font.getVowels();
		if (vowels != null) {
			for (int i = 0; i < vowels.length; i++) {
				if (vowels[i] == (int) ch) {
					// System.out.println("char==="+vowels[i]);
					return true;
				}

			}
		}
		return false;
	}

	static String[] getBoxString(GFont font, String str, int width, int height) {
		char[][] pageChars = new char[font.MAX_LINES_ALLOWED][];
		int numberOfLines = 0;
		LineEnumeration lineEnumaration = new LineEnumeration(font, str, width);
		while (lineEnumaration.hasMoreElements()) {
			String text = lineEnumaration.nextElement().toString();
			if (text.indexOf("\n") != -1) {
				String array[] = split(text, "\n");
				for (int i = 0; i < array.length; i++) {
					String string = array[i];
					pageChars[numberOfLines++] = string.toCharArray();

				}

			} else {
				pageChars[numberOfLines++] = text.toCharArray();

			}

		}
		String textArray[] = new String[numberOfLines];
		for (int i = 0; i < numberOfLines; i++) {
			textArray[i] = new String(pageChars[i]);

		}
		return textArray;
	}

	public static boolean drawPage(GFont font, Canvas g, String str[], int posX, int posY, int width, int height, int anchor, boolean goSlowFill, int count, Paint paintObject) {
		int charCounter = 0;
		try { // swaroop
			boolean vowelUsed = false;
			int vowelCounter = 0;
			if ((anchor & GraphicsUtil.VCENTER) != 0) {
				throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
			}
			char[][] pageChars = new char[GFont.MAX_LINES_ALLOWED][];
			int numberOfLines = 0;
			for (int i = 0; i < str.length; i++) {
				pageChars[numberOfLines] = str[i].toCharArray();
				line_width[numberOfLines++] = font.getStringWidth(str[i]);
			}
			int line_y = posY;
			if ((anchor & GraphicsUtil.BASELINE) != 0) {
				line_y += (height - numberOfLines * font._iCharCommanHeight) >> 1;
			}
			if ((anchor & GraphicsUtil.BOTTOM) != 0) {
				line_y += (height - numberOfLines * font._iCharCommanHeight);
			}
			for (int i = 0; i < numberOfLines; i++) {
				int line_x = posX;

				if ((anchor & GraphicsUtil.RIGHT) != 0) {

					line_x += (width - line_width[i]);
				}
				if ((anchor & GraphicsUtil.HCENTER) != 0) {
					line_x += ((width - line_width[i]) >> 1);
				}

				for (int j = 0; j < pageChars[i].length; j++) {
					if ((int) pageChars[i][j] == 32) {
						line_x += font._iSpaceCharWidth;
					} else {
						int index = font.map_char_arry.indexOf(pageChars[i][j]);
						if ((index < 0 || index >= font.gTantra._iFramesModCnt[font.FONT_FRAME_ID]) && (int) pageChars[i][j] != 94) {
							// if(font._bDebugFont)
							// System.err.println("Invalid Charactor In Draw Page:"+(int)(
							// pageChars[i][j])+"index:"+index+"_iFramesModCnt[FONT_FRAME_ID]:"+font.gTantra._iFramesModCnt[font.FONT_FRAME_ID]);
							return true;
						}
						if (charCounter > count && goSlowFill) {
							return false;
						}

						if ((int) pageChars[i][j] != 94) {
							charCounter++;
							font.gTantra.DrawFrameModule(g, font.FONT_FRAME_ID, index, line_x, line_y - font.extraFontHeight, 0, paintObject);
						}

						boolean isVowel = false;
						if (pageChars[i].length > j + 1) {
							isVowel = isVowel(font, pageChars[i][j + 1]);
						}

						if (!isVowel) {
							if (vowelUsed) {

								line_x += font.getCharWidth(pageChars[i][j - vowelCounter]);
								vowelUsed = false;
								vowelCounter = 0;
							} else {

								line_x += font.getCharWidth(pageChars[i][j]);
							}

						} else {
							vowelUsed = true;
							vowelCounter++;
						}

					}
				}
				line_y += font._iCharCommanHeight;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return true;
	}

	static StringBuffer buffer = new StringBuffer();

	public static boolean drawPageSystemFont(GFont font, Canvas g, String str[], int posX, int posY, int width, int height, int anchor, boolean goSlowFill, int count) {
		int charCounter = 0;
		try {

			if ((anchor & GraphicsUtil.VCENTER) != 0) {
				throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
			}
			char[][] pageChars = new char[GFont.MAX_LINES_ALLOWED][];
			int numberOfLines = 0;
			for (int i = 0; i < str.length; i++) {
				pageChars[numberOfLines] = str[i].toCharArray();
				line_width[numberOfLines++] = font.getStringWidth(str[i]);
			}
			int line_y = posY;
			if ((anchor & GraphicsUtil.BASELINE) != 0) {
				line_y += (height - numberOfLines * font.getFontHeight()) >> 1;
			}
			if ((anchor & GraphicsUtil.BOTTOM) != 0) {
				line_y += (height - numberOfLines * font._iCharCommanHeight);
			}
			for (int i = 0; i < numberOfLines; i++) {
				int line_x = posX;

				if ((anchor & GraphicsUtil.RIGHT) != 0) {

					line_x += (width - line_width[i]);
				}
				if ((anchor & GraphicsUtil.HCENTER) != 0) {
					line_x += ((width - line_width[i]) >> 1);
				}
				buffer.delete(0, buffer.length());
				buffer.append(pageChars[i]);
				drawPageStringSystem(font, g, buffer.toString(), line_x, line_y, 0);
				 line_y += font._iCharCommanHeight;
//				line_y += font.getSystemStringHeightWithSpecialChar(buffer.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return true;
	}

	static int drawPageSystem(GFont font, Canvas g, String str, int posX, int posY, int width, int height, int anchor) {
		try {

			if ((anchor & GraphicsUtil.VCENTER) != 0) {
				throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
			}
			char[][] pageChars = new char[GFont.MAX_LINES_ALLOWED][];

			int numberOfLines = 0;
			LineEnumeration lineEnumaration = new LineEnumeration(font, str, width);

			while (lineEnumaration.hasMoreElements()) {
				String text = lineEnumaration.nextElement().toString();
				if (text.indexOf("\n") != -1) {
					String array[] = split(text, "\n");
					for (int i = 0; i < array.length; i++) {
						String string = array[i];
						pageChars[numberOfLines] = string.toCharArray();
						line_width[numberOfLines++] = font.getStringWidth(string);
					}

				} else {
					pageChars[numberOfLines] = text.toCharArray();
					line_width[numberOfLines++] = font.getStringWidth(text);
				}

			}
			int line_y = posY;
			if ((anchor & GraphicsUtil.BASELINE) != 0) {
				line_y += (height - numberOfLines * font._iCharCommanHeight) >> 1;
			}
			if ((anchor & GraphicsUtil.BOTTOM) != 0) {
				line_y += (height - numberOfLines * font._iCharCommanHeight);
			}
			for (int i = 0; i < numberOfLines; i++) {
				int line_x = posX;

				if ((anchor & GraphicsUtil.RIGHT) != 0) {

					line_x += (width - line_width[i]);
				}
				if ((anchor & GraphicsUtil.HCENTER) != 0) {
					line_x += ((width - line_width[i]) >> 1);
				}
				buffer.delete(0, buffer.length());
				buffer.append(pageChars[i]);
				drawPageStringSystem(font, g, buffer.toString(), line_x, line_y, 0);
				line_y += font._iCharCommanHeight;
			}
			return numberOfLines;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	static int drawPage(GFont font, Canvas g, String str, int posX, int posY, int width, int height, int anchor, Paint paintObject) {
		try {
			boolean vowelUsed = false;
			int vowelCounter = 0;
			if ((anchor & GraphicsUtil.VCENTER) != 0) {
				throw new RuntimeException("Please use 'BASELINE' instead of 'VCENTER'");
			}
			char[][] pageChars = new char[GFont.MAX_LINES_ALLOWED][];

			int numberOfLines = 0;
			LineEnumeration lineEnumaration = new LineEnumeration(font, str, width);

			while (lineEnumaration.hasMoreElements()) {
				String text = lineEnumaration.nextElement().toString();
				if (text.indexOf("\n") != -1) {
					String array[] = split(text, "\n");
					for (int i = 0; i < array.length; i++) {
						String string = array[i];
						pageChars[numberOfLines] = string.toCharArray();
						line_width[numberOfLines++] = font.getStringWidth(string);
					}

				} else {
					pageChars[numberOfLines] = text.toCharArray();
					line_width[numberOfLines++] = font.getStringWidth(text);
				}

			}
			int line_y = posY;
			if ((anchor & GraphicsUtil.BASELINE) != 0) {
				line_y += (height - numberOfLines * font._iCharCommanHeight) >> 1;
			}
			if ((anchor & GraphicsUtil.BOTTOM) != 0) {
				line_y += (height - numberOfLines * font._iCharCommanHeight);
			}
			for (int i = 0; i < numberOfLines; i++) {
				int line_x = posX;

				if ((anchor & GraphicsUtil.RIGHT) != 0) {

					line_x += (width - line_width[i]);
				}
				if ((anchor & GraphicsUtil.HCENTER) != 0) {
					line_x += ((width - line_width[i]) >> 1);
				}

				for (int j = 0; j < pageChars[i].length; j++) {
					if ((int) pageChars[i][j] == 32) {
						line_x += font._iSpaceCharWidth;
					} else {
						int index = font.map_char_arry.indexOf(pageChars[i][j]);
						if ((index < 0 || index >= font.gTantra._iFramesModCnt[font.FONT_FRAME_ID]) && (int) pageChars[i][j] != 94) {
							// if(font._bDebugFont)
							// System.err.println("Invalid Charactor In Draw Page:"+(int)(
							// pageChars[i][j])+"index:"+index+"_iFramesModCnt[FONT_FRAME_ID]:"+font.gTantra._iFramesModCnt[font.FONT_FRAME_ID]);
							return numberOfLines;
						}
						if ((int) pageChars[i][j] != 94) {
							font.gTantra.DrawFrameModule(g, font.FONT_FRAME_ID, index, line_x, line_y - font.extraFontHeight, 0, paintObject);
						}

						boolean isVowel = false;
						if (pageChars[i].length > j + 1) {
							isVowel = isVowel(font, pageChars[i][j + 1]);
						}
						if (!isVowel) {
							if (vowelUsed) {
								line_x += font.getCharWidth(pageChars[i][j - vowelCounter]);
								vowelUsed = false;
								vowelCounter = 0;
							} else {
								line_x += font.getCharWidth(pageChars[i][j]);
							}

						} else {
							vowelUsed = true;
							vowelCounter++;
						}
					}
				}
				line_y += font._iCharCommanHeight;
			}
			return numberOfLines;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	static int getMaxCharHeight(GFont font, String text) {
		int max = 0;
		for (int i = 0; i < text.length(); i++) {
			if (max < font.getCharHeight(text.charAt(i))) {
				max = font.getCharHeight(text.charAt(i));
			}
		}
		return max;
	}

}
