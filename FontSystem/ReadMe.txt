1. Install the FontAndroidRND.apk on any android devices
2. Load the TTF into application
3. Use various settings to create a becautiful font design.

Technical integration of font system game with game is mentioned below:

-----------------------------------------------------------------------
\\\\\\\\\\\\Font System//////////////
-----------------------------------------------------------------------

1. Declare GFont variable in MyGameConstants class 
	public static GFont Font=null;

2. Load the IMPACT_0.TTF file stored in assets folder to load a font. (any TTF can be used)

	MyGameConstants.Font= new GFont(FontStyleGenerator.COLOR_ID_WHITE, "IMPACT_0.TTF", MyGameActivity.getInstance());

3. In FontStyleGenerator Class you can create various font Styles by assigning it color, size, shodow, border,etc.

	public static final int COLOR_ID_WHITE = 0;
	public static final int COLOR_ID_BlACK = 0;

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
			case COLOR_ID_BlACK: 
				f = new FontStyle();
				f.setFontSize(40);
				f.setFontStyle(0);//(1)
				f.setFontColor(0xFF000000);
				f.port(800, MyGameConstants.SCREEN_HEIGHT);
				return f;
			default:
				return new FontStyle();

			}

		}

4. Before painting is started, set font style by using setColor method and then start Painting.

	MyGameConstants.Font.setColor(FontStyleGenerator.COLOR_ID_WHITE);

	MyGameConstants.Font.drawString(g, "string", posX, posY, GraphicsUtil.ORIGINAL, paintObject);
			or
	MyGameConstants.Font.drawPage(c, "string", posX, posY,pageWidth,pageHeight, GraphicsUtil.HCENTER|GraphicsUtil.BASELINE, paintObject);




