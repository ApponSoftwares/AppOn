/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.gtantra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.appon.fontstyle.FontStyle;
import com.appon.fontstyle.FontStyleGenerator;

/**
 * 
 * @author use
 */
public class GFont {



	GTantra gTantra;
	public String map_char_arry = "";
	public int _iSpaceCharWidth = 0;
	public int _iCharCommanHeight = 0;
	public static final int FONT_FRAME_ID = 0;
	public static int EXTRA_SPACE_WIDTH = 0;
	public static int EXTRA_SPACE_HEIGHT = 0;
	public static final boolean _bDebugFont = false;

	static int MAX_LINES_ALLOWED = 140;
	int extraFontHeight;
	int color = -1;
	private int vowels[];
	GFont palFont;

	//	int fontSize = -1;
	boolean usingSystemFont = false;
	//	Paint staticPaintObject = new Paint();
	Typeface font;
	private int sysPalletsArray[];

	private char specialCharactors[];
	private Bitmap specialCharactorsImages[];

	private int lastUsedStyle = -1;
	public GFont(byte[] file, String char_arry, boolean caching) {
		this.gTantra = new GTantra();
		// this.gTantra.setSkipHeightModuleIndexes(skipModuleHeightIndexes);
		this.gTantra.Load(file, caching);
		this.map_char_arry = char_arry;
		_iSpaceCharWidth = getCharWidth('2') >> 1;
		_iCharCommanHeight = gTantra._iCharCommanHeight;
	}

	public GFont(byte[] file, String char_arry, boolean caching, int[] skipModuleHeightIndexes) {
		this.gTantra = new GTantra();
		this.gTantra.setSkipHeightModuleIndexes(skipModuleHeightIndexes);
		this.gTantra.Load(file, caching);
		this.map_char_arry = char_arry;
		_iSpaceCharWidth = getCharWidth('2') >> 1;
		_iCharCommanHeight = gTantra._iCharCommanHeight;
		usingSystemFont = false;
	}
	private FontStyle fontStyle;
	public GFont(int fontStyleId, String fontName,Context context) {
		FontStyle style = FontStyleGenerator.getInst().getFontStyle(fontStyleId);
		font = Typeface.createFromAsset(context.getAssets(), fontName);
		style.setTypeFace(font);
		this.fontStyle = style;
		usingSystemFont = true;
		_iCharCommanHeight = style.getSystemStringSize(null);
		_iSpaceCharWidth = (getStringWidth("1 0") - getStringWidth("10"));
		lastUsedStyle = fontStyleId;

	}
	public void setFontStyle(int index)
	{
		usingSystemFont = true;
		FontStyle style = FontStyleGenerator.getInst().getFontStyle(index);
		this.fontStyle = style;
		style.setTypeFace(font);
		_iCharCommanHeight = style.getSystemStringSize(null);
		_iSpaceCharWidth = (getStringWidth("1 0") - getStringWidth("10"));
	}

	public void setSpecialCharactorsInfo(char specialCharactors[], Bitmap specialCharactorsImages[]) {
		this.specialCharactors = specialCharactors;
		this.specialCharactorsImages = specialCharactorsImages;
	}
	public void resetFontStyle(int index)
	{
		if(index == -1)
			return;
		if(lastUsedStyle == -1 || index != lastUsedStyle)
		{
			setFontStyle(index);
			lastUsedStyle = index;
		}
	}
	public Bitmap getSpecialCharImage(char ch) {
		if (specialCharactors == null)
			return null;
		for (int i = 0; i < specialCharactors.length; i++) {
			if (ch == specialCharactors[i])
				return specialCharactorsImages[i];
		}
		return null;
	}

	public void setSysPalletsArray(int[] sysPalletsArray) {
		this.sysPalletsArray = sysPalletsArray;
	}

	public void setUsingSystemFont(boolean usingSystemFont) {
		this.usingSystemFont = usingSystemFont;
	}



	public int[] getVowels() {
		return vowels;
	}

	public void setVowels(int[] vowels) {
		this.vowels = vowels;
	}

	public void setColor(int color) {
		this.color = color;
		if(usingSystemFont)
			setFontStyle(this.color);
	}

	public void setPalFont(GFont palFont) {
		this.palFont = palFont;
		palFont.setVowels(new int[] { 3636, 3660, 3657, 3637, 3656, 3655, 3640, 3639, 3633, 3641, 3638, 3659, 3658 });
	}

	public GFont getPalFont() {
		return palFont;
	}

	public int getStringHeight(String text) {
		if (usingSystemFont) {
			return fontStyle.getSystemStringHeight(text);
		}
		return GFactory.getStaticStringHeight(this, text);
	}

	public int getFontHeight() {
		if (usingSystemFont) {
			return fontStyle.getSystemStringSize(null);
		}
		return _iCharCommanHeight;
	}
	public int getTotalHeight()
	{
		//		if (usingSystemFont) {
		//			return fontStyle.getSystemStringSize(null);
		//		}
		return _iCharCommanHeight;
	}

	public void setFontHeight(int height) {
		_iCharCommanHeight = height;
	}

	public int getCharHeight(char c) {
		if (usingSystemFont) {
			return fontStyle.getSystemStringHeight(c + "");
		}
		return GFactory.getCharHeight(this, c);
	}

	public int getCharWidth(char c) {
		if (usingSystemFont) {
			Bitmap b = getSpecialCharImage(c);
			if (b == null) {
				return getSystemStringWidth(c + "");
			} else {
				return b.getWidth();
			}

		}
		return GFactory.getCharWidth(this, c);
	}

	public int getStringWidth(String str) {
		if (usingSystemFont) {
			return getSystemStringWidth(str);
		}
		int length = 0;
		for (int i = 0; i < str.length(); i++) {
			length += getCharWidth(str.charAt(i));
		}
		return length;
	}
	//	public int getStringWidth(String str,int index) {
	//		if (usingSystemFont) {
	//			return getSystemStringWidth(str,index);
	//		}
	//		int length = 0;
	//		for (int i = 0; i < str.length(); i++) {
	//			length += getCharWidth(str.charAt(i));
	//		}
	//		return length;
	//	}
	public FontStyle getFontStyle() {
		return fontStyle;
	}

	public void drawString(Canvas g, String str, int posX, int posY, int anchor, Paint paintObject) {

		if (usingSystemFont) {

			int backupStyle = lastUsedStyle;
			resetFontStyle(color);
			GFactory.drawStringSystem(this, g, str, posX, posY, anchor);
			resetFontStyle(backupStyle);
			return;
		}
		if (palFont != null && color == 1) {
			// palFont.drawString(g, str, posX, posY, anchor,paintObject);

			GFactory.drawString(palFont, g, str, posX, posY, anchor, paintObject);

		} else {
			int pal = 0;
			if(color != -1)
			{
				pal = color;
			}
			gTantra.setCurrentPallete(pal);
			GFactory.drawString(this, g, str, posX, posY, anchor, paintObject);
		}
	}

	public void drawStringWithAlpha(Canvas g, int alpha ,String str ,int posX , int posY , int anchor, Paint paintObject)
	{
		if (usingSystemFont) {

			int backupStyle = lastUsedStyle;
			resetFontStyle(color);
			paintObject.setAlpha(alpha);
			GFactory.drawStringSystemWithAlpha(this, g, str, posX, posY, anchor,paintObject);
			resetFontStyle(backupStyle);
			return;
		}
		if (palFont != null && color == 1) {
			// palFont.drawString(g, str, posX, posY, anchor,paintObject);

			GFactory.drawString(palFont, g, str, posX, posY, anchor, paintObject);

		} else {
			int pal = 0;
			if(color != -1)
			{
				pal = color;
			}
			gTantra.setCurrentPallete(pal);
			GFactory.drawString(this, g, str, posX, posY, anchor, paintObject);
		}
	}

	public int getNumberOfLines(String str, int width) {
		return GFactory.getNumberOfLines(this, str, width);
	}

	public String[] getBoxString(String str, int width, int height) {
		return GFactory.getBoxString(this, str, width, height);
	}

	public void drawPage(Canvas g, String str[], int posX, int posY, int width, int height, int anchor, Paint paintObject) {
		if (usingSystemFont) {
			int backupStyle = lastUsedStyle;
			resetFontStyle(color);
			GFactory.drawPageSystemFont(this, g, str, posX, posY, width, height, anchor, false, -1);
			resetFontStyle(backupStyle);
			return;
		}
		if (palFont != null && color == 1) {
			GFactory.drawPage(palFont, g, str, posX, posY, width, height, anchor, false, -1, paintObject);
		} else {
			int pal = 0;
			if(color != -1)
			{
				pal = color;
			}
			gTantra.setCurrentPallete(pal);

			GFactory.drawPage(this, g, str, posX, posY, width, height, anchor, false, -1, paintObject);

		}
	}

	public int drawPage(Canvas g, String str, int posX, int posY, int width, int height, int anchor, Paint paintObject) {
		if (usingSystemFont) {
			int backupStyle = lastUsedStyle;
			resetFontStyle(color);
			int num = GFactory.drawPageSystem(this, g, str, posX, posY, width, height, anchor);
			resetFontStyle(backupStyle);
			return num;
		}
		if (palFont != null && color == 1) {
			return GFactory.drawPage(palFont, g, str, posX, posY, width, height, anchor, paintObject);
		} else {
			int pal = 0;
			if(color != -1)
			{
				pal = color;
			}
			gTantra.setCurrentPallete(pal);
			return GFactory.drawPage(this, g, str, posX, posY, width, height, anchor, paintObject);
		}
	}

	public void setExtraFontHeight(int extraFontHeight) {
		this.extraFontHeight = extraFontHeight;
	}

	public int getSpaceCharactorWidth() {

		return _iSpaceCharWidth;
	}

	public void setSpaceCharactorWidth(int width) {
		_iSpaceCharWidth = width;
	}

	public int getMaxCharHeight(String text) {
		return GFactory.getMaxCharHeight(this, text);
	}

	public GTantra getgTantra() {
		return gTantra;
	}



	public boolean isSpecialCharAvailable(String text) {
		if (specialCharactors == null)
			return false;
		for (int i = 0; i < specialCharactors.length; i++) {
			if (text.indexOf(specialCharactors[i]) != -1) {
				return true;
			}
		}
		return false;
	}

	private int getCharsCount(String line, char ch) {
		return line.length() - line.replace(ch + "", "").length();
	}
	static Rect bounds = new Rect();

	public int getSystemStringWidth(String text) {


		int specialCharsWidth = 0;
		if (isSpecialCharAvailable(text) && text != null) {
			for (int i = 0; i < specialCharactors.length; i++) {
				if (text.indexOf(specialCharactors[i]) != -1) {
					int count = getCharsCount(text, specialCharactors[i]);
					text = text.replace(specialCharactors[i] + "", "");
					specialCharsWidth += getSpecialCharImage(specialCharactors[i]).getWidth() * count;
				}
			}
		}

		fontStyle.getPrimaryPaintObject().getTextBounds(text, 0, text.length(), bounds);

		int extraLength = (text.length() - text.trim().length()) * _iSpaceCharWidth;
		int _w = bounds.width();
		if (_w < 0)
			_w = 0;
		int width = _w + specialCharsWidth + extraLength;
		return width;
	}

	public int getSystemStringWidth(String text,int index) {


		int specialCharsWidth = 0;
		if (isSpecialCharAvailable(text) && text != null) {
			for (int i = 0; i < specialCharactors.length; i++) {
				if (text.indexOf(specialCharactors[i]) != -1) {
					int count = getCharsCount(text, specialCharactors[i]);
					text = text.replace(specialCharactors[i] + "", "");
					specialCharsWidth += getSpecialCharImage(specialCharactors[i]).getWidth() * count;
				}
			}
		}

		FontStyleGenerator.getInst().getFontStyle(index).getPrimaryPaintObject().getTextBounds(text, 0, text.length(), bounds);

		int extraLength = (text.length() - text.trim().length()) * _iSpaceCharWidth;
		int _w = bounds.width();
		if (_w < 0)
			_w = 0;
		int width = _w + specialCharsWidth + extraLength;
		return width;
	}



	public int getSystemStringHeightWithSpecialChar(String text) {

		if (!usingSystemFont) {
			return _iCharCommanHeight;
		}

		int specialCharsHeight = 0;
		if (isSpecialCharAvailable(text)) {
			for (int i = 0; i < specialCharactors.length; i++) {
				if (text.indexOf(specialCharactors[i]) != -1) {
					text = text.replace(specialCharactors[i] + "", "");
					specialCharsHeight = Math.max(getSpecialCharImage(specialCharactors[i]).getHeight(), specialCharsHeight);
				}
			}
		} else {
			return getFontHeight() + 1;
		}
		fontStyle.getPrimaryPaintObject().getTextBounds(text, 0, text.length(), bounds);

		int height = bounds.bottom - bounds.top + 1;
		// int height = (int) (Math.abs(paintObject.ascent() +
		// paintObject.descent()) + 1);
		return Math.max(height, specialCharsHeight);
	}



	StringBuffer buffer = new StringBuffer();

	public void drawSystemTextWithSpecialChars(Canvas g, String text, int posX, int posY, int strHeight) {

		int backupStyle = lastUsedStyle;
		resetFontStyle(color);

		while (isSpecialCharAvailable(text)) {
			char ch = findFirstSpecialChar(text);
			int index = text.indexOf(ch);
			String subString = text.substring(0, index);
			if (subString.length() > 0) {
				// GFactory.drawStringSystem(this, g, subString, posX, posY, 0,
				// paintObject);
				fontStyle.drawTextWithStyle(g, subString,  posX, posY);
				//	g.drawText(subString, posX, posY, fontStyle.getPrimaryPaintObject());
				posX += getSystemStringWidth(subString);
			}
			Bitmap b = getSpecialCharImage(ch);
			drawSpecialChar(g, ch, b, posX, posY - (strHeight / 2) - (b.getHeight() / 2), fontStyle.getSpecialCharPaintObject());
			text = text.substring(index + 1, text.length());
			posX += b.getWidth();
		}
		if (text.length() > 0) {
			fontStyle.drawTextWithStyle(g, text,  posX, posY);
			//	g.drawText(text, posX, posY, fontStyle.getPrimaryPaintObject());
			// GFactory.drawStringSystem(this, g, text, posX, posY, 0,
			// paintObject);
		}
		resetFontStyle(backupStyle);
	}

	public void drawSystemTextWithSpecialCharsWithAlph(Canvas g, String text, int posX, int posY, int strHeight,Paint paintObject)
	{

		int backupStyle = lastUsedStyle;
		resetFontStyle(color);

		while (isSpecialCharAvailable(text)) {
			char ch = findFirstSpecialChar(text);
			int index = text.indexOf(ch);
			String subString = text.substring(0, index);
			if (subString.length() > 0) {
				// GFactory.drawStringSystem(this, g, subString, posX, posY, 0,
				// paintObject);
				fontStyle.drawTextWithStyleWithAlpha(g, subString,  posX, posY,paintObject);
				//	g.drawText(subString, posX, posY, fontStyle.getPrimaryPaintObject());
				posX += getSystemStringWidth(subString);
			}
			Bitmap b = getSpecialCharImage(ch);
			drawSpecialChar(g, ch, b, posX, posY - (strHeight / 2) - (b.getHeight() / 2), fontStyle.getSpecialCharPaintObject());
			text = text.substring(index + 1, text.length());
			posX += b.getWidth();
		}
		if (text.length() > 0) {
			fontStyle.drawTextWithStyleWithAlpha(g, text,  posX, posY,paintObject);
			//	g.drawText(text, posX, posY, fontStyle.getPrimaryPaintObject());
			// GFactory.drawStringSystem(this, g, text, posX, posY, 0,
			// paintObject);
		}
		resetFontStyle(backupStyle);
	}
	private void drawSpecialChar(Canvas c, char ch, Bitmap b, int x, int y, Paint paintObj) {
		switch (ch) {

		default:
			GraphicsUtil.drawBitmap(c, b, x, y, 0, paintObj);
			break;
		}
	}

	private char findFirstSpecialChar(String text) {
		int index = Integer.MAX_VALUE;
		for (int i = 0; i < specialCharactors.length; i++) {
			int foundIndex = text.indexOf(specialCharactors[i]);
			if (foundIndex != -1) {
				index = Math.min(foundIndex, index);
			}
		}
		return specialCharactors[findSpecialCharIndex(text.charAt(index))];
	}

	private int findSpecialCharIndex(char ch) {
		for (int i = 0; i < specialCharactors.length; i++) {
			if (specialCharactors[i] == ch) {
				return i;
			}
		}
		return 0;
	}



}
