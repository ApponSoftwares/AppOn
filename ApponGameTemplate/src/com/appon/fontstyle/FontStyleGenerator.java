package com.appon.fontstyle;

import java.util.ArrayList;

import com.appon.game.MyGameConstants;
import com.appon.gtantra.GFont;

public class FontStyleGenerator {


	public static final int MAX_STYLES = 45;
	//COLORS 
	public static final int Level2 =0xFFffff00;
	public static final int Level3=0xFF99ff00;
	public static final int Level4=0xff264fba;//0xff00cc33;
	public static final int WHITE_PLAIN =0xFFFFFFFF;
	public static final int WHITE_ALPHA =0x80FFFFFF;
	public static final int BLACK_PLAINT=0xFF000000;
	public static final int YELLOW_PLAIN=0xffFAEF00;
	public static final int YELLOW_PLAIN_SHADE=0xffd1a400;
	public static final int RED_PLAIN=0xffb71414;
	public static final int BLUE=0xff0000ff;//0x2136f8e7;
	public static final int GREEN=0xff00ff00;//0xff14b714
	public static final int CYAN=0xFF1f91b3;
	public static final int VOILET=0xFF860ea3;
	public static final int Seleted_Day_Yellow=0xfffff95b;
	public static final int Day_Yellow=0xffceb409;
	public static final int Booster_Color=0xff91d5ee;
	public static final int ORANGE=0xffff6623;//0xffb71414

	private static FontStyleGenerator inst;
	ArrayList<FontStyle> fontStyles = new ArrayList<FontStyle>();
	public static FontStyleGenerator getInst() {
		if(inst == null)
		{
			inst = new FontStyleGenerator();
		}
		return inst;
	}

	private FontStyleGenerator()
	{
		for (int i = 0; i < MAX_STYLES; i++) {
			fontStyles.add(createFontStyle(i));
		}
	}
	public FontStyle getFontStyle(int index)
	{
		return fontStyles.get(index);
	}

	
	public static final int COLOR_ID_WHITE = 0;

	
	private FontStyle createFontStyle(int index)
	{ 
		FontStyle f=null;
		switch (index) {
		case COLOR_ID_WHITE: 
			f = new FontStyle();
			f.setFontSize(40);
			f.setFontStyle(0);//(1)
			f.setFontColor(0xFFFFFFFF);
			f.port(800, MyGameConstants.SCREEN_HEIGHT);
			return f;
		default:
			return new FontStyle();

		}

	}
}
