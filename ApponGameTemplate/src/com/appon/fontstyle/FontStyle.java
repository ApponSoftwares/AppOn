package com.appon.fontstyle;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;

public class FontStyle {
	
	public static final int FONT_STYLE_PLAIN = 0;
	public static final int FONT_STYLE_BOLD = 1;
	public static final int FONT_STYLE_ITALIC = 2;
	public static final int FONT_STYLE_BOLD_ITALIC = 3;
	// basic settings
	private int fontSize = 250;
	private int fontColor=0xff0000ff;
	private boolean roundJoint = false;
	private int fontStyle = 0;
		
	// border settings
	private boolean enableBorder = false;
	private boolean drawBorderOnly = false;
	private int strokeSize = 1;
	private int borderColor = 0xff00ff00;
	private boolean applyShadowOnBorder = false;
	private boolean reverseBorderDrawSequence = false;
	
	//shadow setting
	private boolean useShadow = false;
	private float shadowRadious = 1;
	private float shadowDx = 1;
	private float shadowDy = 1;
	private int shadowColor = 0xff000000;
	
	//gradient system
	private boolean useGradient = false;
	private int gradientEndColor = 0xffffff00;
	
	/////////////////// PAINT OBJECTS //////////////////////
	Paint fillPaintObject   = new Paint();
	Paint borderPaintObject = new Paint();
	Paint specialCharPaintObject = new Paint();
	static Rect bounds = new Rect();
	Typeface boldTypeFace;
	Typeface italicTypeFace;
	Typeface boldItalicTypeFace;
	public FontStyle() {
		// TODO Auto-generated constructor stub
		fillPaintObject.setStyle(Style.FILL);
		fillPaintObject.setAntiAlias(true);
		fillPaintObject.setFilterBitmap(true);
		fillPaintObject.setDither(true);
		
		borderPaintObject.setStyle(Style.STROKE);
		borderPaintObject.setAntiAlias(true);
		borderPaintObject.setFilterBitmap(true);
		borderPaintObject.setDither(true);
		
		
		specialCharPaintObject.setAntiAlias(true);
		specialCharPaintObject.setFilterBitmap(true);
		specialCharPaintObject.setDither(true);
		
		init();
	}
	public void port(int masterHeight,int screenHeight)
	{
		int yScale = ((100 * (screenHeight - masterHeight) )/ masterHeight) ;
		
		fontSize = (int)getScaleValue(fontSize, yScale);
		fillPaintObject.setTextSize(fontSize);
		borderPaintObject.setTextSize(fontSize);
		strokeSize = (int)getScaleValue(strokeSize, yScale);
		if(strokeSize <= 0)
		{
			strokeSize = 1;
		}
		borderPaintObject.setStrokeWidth(strokeSize);
		shadowDx= getScaleValue(shadowDx, yScale);
		shadowDy= getScaleValue(shadowDy, yScale);
	}
	public static float getScaleValue(float value, float scale)
    {
        if(scale < 0)
        {
            scale = Math.abs(scale);
            value = (value -  (( Math.abs(value) * scale) / 100f));
        }else{
            value =  (value + ((value * scale) / 100f));

        }
        return value;
    }
	public void init()
	{
		setFontSize(fontSize);
		setFontColor(fontColor);
		setRoundJoint(roundJoint);
		setFontStyle(fontStyle);
//		setEnableBorder(enableBorder);
		setDrawBorderOnly(drawBorderOnly);
//		setStrokeSize(strokeSize);
		setBorderColor(borderColor);
		setApplyShadowOnBorder(applyShadowOnBorder);
		setReverseBorderDrawSequence(reverseBorderDrawSequence);
		setUseShadow(useShadow);
		setShadowColor(shadowColor);
		setShadowDx(shadowDx);
		setShadowDy(shadowDy);
		setUseGradient(useGradient);
		setGradientEndColor(gradientEndColor);
	}
	public void setTypeFace(Typeface typeFace)
	{
		fillPaintObject.setTypeface(typeFace);
		borderPaintObject.setTypeface(typeFace);
		specialCharPaintObject.setTypeface(typeFace);
		boldTypeFace = null;
		italicTypeFace = null;
		boldItalicTypeFace = null;
	}
	public Paint getSpecialCharPaintObject() {
		return specialCharPaintObject;
	}
	public Paint getPrimaryPaintObject()
	{
		Paint paintObject = fillPaintObject;
		if(drawBorderOnly)
		{
			paintObject = borderPaintObject;
		}
		return paintObject;
	}
	public int getSystemStringSize(String text) {
		Paint paintObject = fillPaintObject;
		if(drawBorderOnly)
		{
			paintObject = borderPaintObject;
		}
		if (text == null) {
			return (int) paintObject.getTextSize();
		} else {
			return getSystemStringHeight(text);
		}
	}
	public int getSystemStringHeight(String text) {
		Paint paintObject = fillPaintObject;
		if(drawBorderOnly)
		{
			paintObject = borderPaintObject;
		}
		
		int specialCharsHeight = 0;
		
		paintObject.getTextBounds(text, 0, text.length(), bounds);

		int height = (int) (Math.abs(bounds.top + bounds.bottom));
		return (Math.max(height+2, specialCharsHeight));
	}
	////////////////////// GETTERS /////////////////////////
	public int getFontSize() {
		return fontSize;
	}
	public int getFontColor() {
		return fontColor;
	}
	public boolean isRoundJoint() {
		return roundJoint;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public boolean isEnableBorder() {
		return enableBorder;
	}
	public boolean isDrawBorderOnly() {
		return drawBorderOnly;
	}
	public int getStrokeSize() {
		return strokeSize;
	}
	public int getBorderColor() {
		return borderColor;
	}
	public boolean isApplyShadowOnBorder() {
		return applyShadowOnBorder;
	}
	public boolean isReverseBorderDrawSequence() {
		return reverseBorderDrawSequence;
	}
	public boolean isUseShadow() {
		return useShadow;
	}
	public float getShadowRadious() {
		return shadowRadious;
	}
	public float getShadowDx() {
		return shadowDx;
	}
	public float getShadowDy() {
		return shadowDy;
	}
	public int getShadowColor() {
		return shadowColor;
	}
	public boolean isUseGradient() {
		return useGradient;
	}
	public int getGradientEndColor() {
		return gradientEndColor;
	}
	
	
	////////////////////// SETTERS /////////////////////////
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		fillPaintObject.setTextSize(fontSize);
		borderPaintObject.setTextSize(fontSize);
	}
	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
		fillPaintObject.setColor(fontColor);
	}
	public void setRoundJoint(boolean roundJoint) {
		this.roundJoint = roundJoint;
		if(roundJoint)
		{
			fillPaintObject.setStrokeJoin(Join.ROUND);
			fillPaintObject.setStrokeMiter(10);
			borderPaintObject.setStrokeJoin(Join.ROUND);
			borderPaintObject.setStrokeMiter(10);
		}else{
			fillPaintObject.setStrokeJoin(Join.MITER);
			borderPaintObject.setStrokeJoin(Join.MITER);
		}
	}
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	public void setEnableBorder(boolean enableBorder) {
		this.enableBorder = enableBorder;
	}
	public void setDrawBorderOnly(boolean drawBorderOnly) {
		this.drawBorderOnly = drawBorderOnly;
	}
	public void setStrokeSize(int strokeSize) {
		this.strokeSize = strokeSize;
		borderPaintObject.setStrokeWidth(strokeSize);
	}
	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
		borderPaintObject.setColor(borderColor);
	}
	public void setApplyShadowOnBorder(boolean applyShadowOnBorder) {
		this.applyShadowOnBorder = applyShadowOnBorder;
		if(applyShadowOnBorder)
		{
			borderPaintObject.setShadowLayer(shadowRadious, shadowDx,shadowDy,shadowColor);
		}else{
			borderPaintObject.setShadowLayer(0, 0,0,0);
		}
	}
	public void setReverseBorderDrawSequence(boolean reverseBorderDrawSequence) {
		this.reverseBorderDrawSequence = reverseBorderDrawSequence;
	}
	public void setUseShadow(boolean useShadow) {
		this.useShadow = useShadow;
		if(useShadow)
		{
			fillPaintObject.setShadowLayer(shadowRadious, shadowDx,shadowDy,shadowColor);
		}else{
			fillPaintObject.setShadowLayer(0, 0,0,0);
		}
		
	}
	public void setShadowRadious(float shadowRadious) {
		this.shadowRadious = shadowRadious;
		setUseShadow(useShadow);
		setApplyShadowOnBorder(applyShadowOnBorder);
	}
	public void setShadowDx(float shadowDx) {
		this.shadowDx = shadowDx;
		setUseShadow(useShadow);
		setApplyShadowOnBorder(applyShadowOnBorder);
	}
	public void setShadowDy(float shadowDy) {
		this.shadowDy = shadowDy;
		setUseShadow(useShadow);
		setApplyShadowOnBorder(applyShadowOnBorder);
	}
	public void setShadowColor(int shadowColor) {
		this.shadowColor = shadowColor;
		setUseShadow(useShadow);
		setApplyShadowOnBorder(applyShadowOnBorder);
	}
	public void setUseGradient(boolean useGradient) {
		this.useGradient = useGradient;
	}
	public void setGradientEndColor(int gradientEndColor) {
		this.gradientEndColor = gradientEndColor;
	}
	
	public void drawTextWithStyle(Canvas c,String str,int x,int y)
	{
		Typeface plainFont = fillPaintObject.getTypeface();
		if(fontStyle != FONT_STYLE_PLAIN)
		{
			if(fontStyle == FONT_STYLE_BOLD && boldTypeFace == null)
			{
				boldTypeFace = Typeface.create(plainFont, Typeface.BOLD);
			}
			if(fontStyle == FONT_STYLE_ITALIC && italicTypeFace == null)
			{
				italicTypeFace = Typeface.create(plainFont, Typeface.ITALIC);
			}
			if(fontStyle == FONT_STYLE_BOLD_ITALIC && boldItalicTypeFace == null)
			{
				boldItalicTypeFace = Typeface.create(plainFont, Typeface.BOLD_ITALIC);
			}
			if(fontStyle == FONT_STYLE_BOLD)
			{
				setTypeFace(boldTypeFace);
			}if(fontStyle == FONT_STYLE_ITALIC)
			{
				setTypeFace(italicTypeFace);
			}if(fontStyle == FONT_STYLE_BOLD_ITALIC)
			{
				setTypeFace(boldItalicTypeFace);
			}
		}
		if(!reverseBorderDrawSequence)
		{
			drawFilledText(c, str, x, y);
			drawBorderText(c, str, x, y);
		}else{
			drawBorderText(c, str, x, y);
			drawFilledText(c, str, x, y);
		}
		setTypeFace(plainFont);
	}
	
	public void drawTextWithStyleWithAlpha(Canvas c,String str,int x,int y,Paint paintObject)
	{
		Typeface plainFont = fillPaintObject.getTypeface();
		fillPaintObject.setAlpha(paintObject.getAlpha());
		borderPaintObject.setAlpha(paintObject.getAlpha());
		specialCharPaintObject.setAlpha(paintObject.getAlpha());
		if(fontStyle != FONT_STYLE_PLAIN)
		{
			if(fontStyle == FONT_STYLE_BOLD && boldTypeFace == null)
			{
				boldTypeFace = Typeface.create(plainFont, Typeface.BOLD);
			}
			if(fontStyle == FONT_STYLE_ITALIC && italicTypeFace == null)
			{
				italicTypeFace = Typeface.create(plainFont, Typeface.ITALIC);
			}
			if(fontStyle == FONT_STYLE_BOLD_ITALIC && boldItalicTypeFace == null)
			{
				boldItalicTypeFace = Typeface.create(plainFont, Typeface.BOLD_ITALIC);
			}
			if(fontStyle == FONT_STYLE_BOLD)
			{
				setTypeFace(boldTypeFace);
			}if(fontStyle == FONT_STYLE_ITALIC)
			{
				setTypeFace(italicTypeFace);
			}if(fontStyle == FONT_STYLE_BOLD_ITALIC)
			{
				setTypeFace(boldItalicTypeFace);
			}
		}
		if(!reverseBorderDrawSequence)
		{
			drawFilledText(c, str, x, y);
			drawBorderText(c, str, x, y);
		}else{
			drawBorderText(c, str, x, y);
			drawFilledText(c, str, x, y);
		}
		setTypeFace(plainFont);
		fillPaintObject.setAlpha(0xff);
		borderPaintObject.setAlpha(0xff);
		specialCharPaintObject.setAlpha(0xff);
	}
	
	public void drawFilledText(Canvas c,String text,int x,int y)
	{
		if(!drawBorderOnly)
		{
			if(useShadow && useGradient)
			{
				
				Shader object = fillPaintObject.getShader();
				drawPlainText(c, text, x, y, fillPaintObject);
				fillPaintObject.clearShadowLayer();
				fillPaintObject.getTextBounds(text, 0, text.length(), bounds);
				LinearGradient shader = new LinearGradient(0,  y+ bounds.top, 0,  y+ bounds.bottom, fontColor, gradientEndColor, TileMode.CLAMP);
				
				fillPaintObject.setShader(shader);
				drawPlainText(c, text, x, y, fillPaintObject);
				fillPaintObject.setShader(object);
				setUseShadow(true);
			}else{
				Shader object = fillPaintObject.getShader();
				if(useGradient)
				{
					fillPaintObject.getTextBounds(text, 0, text.length(), bounds);
					Shader shader = new LinearGradient(0,  y+ bounds.top, 0,  y+ bounds.bottom, fontColor,gradientEndColor, TileMode.CLAMP);
					fillPaintObject.setShader(shader);
				}
				drawPlainText(c, text, x, y, fillPaintObject);
				fillPaintObject.setShader(object);
			}

		}
	}
	 private void drawBorderText(Canvas c,String text,int x,int y)
	    {
	    	if(enableBorder || drawBorderOnly)
			{
				drawPlainText(c, text, x, y, borderPaintObject);
			}
	    }
	public void drawPlainText(Canvas g,String text,int x,int y,Paint paint)
	{
		paint.setDither(true);
		paint.setAntiAlias(true);
		g.drawText(text, x, y, paint);
	}
}
