This document explains how to create a game from scratch using Native android.


1. MyGameActivity Class

	a. Create Your Activity Class extend it with GameActivity Class and declare it in androidmanifest.xml to 
	   launch Actvity.

		public class MyGameActivity extends GameActivity {

		}

		<activity
           	android:name="com.appon.game.MyGameActivity"
            	android:label="Appon Game Template"
            	android:launchMode="standard"
            	android:screenOrientation="sensorLandscape"
            	android:theme="@android:style/Theme.NoTitleBar.Fullscreen" >
            	<intent-filter>
                	<action android:name="android.intent.action.MAIN" />

                	<category android:name="android.intent.category.LAUNCHER" />
            	</intent-filter>
        	</activity>
        

	b. Create an instance of MyGameActivity to access it wherever required.

     		private static MyGameActivity activity;

		public MyGameActivity(){
			activity =this;
		}

		public static MyGameActivity getInstance(){
			return activity;
		}
 
 		Also Override onBackPressed() to handle Android BackPress button
 	
 		@Override
		public void onBackPressed() {
			//handle Back Button
		}
	

2. MyGameCanvas class

	a. Create your Canvas Class extend it with GameCanvas Class.


		public class MyGameCanvas extends GameCanvas{

		}

	b. Create an instance of MyGameCanvas to access it wherever required.


		private static MyGameCanvas myGameCanvas=null;

		protected MyGameCanvas(Context context) {
			super(context);
		}
		public static MyGameCanvas getInstance(Context context){
			if(myGameCanvas == null){
				myGameCanvas =new MyGameCanvas(context);
			}
			return myGameCanvas; 
		}
		public static MyGameCanvas getInstance(){
			if(myGameCanvas == null){
				myGameCanvas =new MyGameCanvas(context);
			}
			return myGameCanvas; 
		}
	
	c. Add unimplemented methods. 

	[Update & paint methods are called in thread continuosly according to the FPS decided in GameThread Class (public static int FPS = 15;)
	Update & paint methods are used for game updation & painting.]
	
	
		@Override
		public void update() {
			// Use for game calculation before painting 
		}
	
		create Paint Object used for painting on Canvas
	
		public Paint paintCanvas =new Paint();
	
	
		@Override
		public void paint(Canvas canvas) {
			paint(canvas,paintCanvas);
		}
	
		protected void paint(Canvas c, Paint paintObject) {
			// Use for game Painting
		}
	
		used to handle pointer Press operation 
		@Override
		public void pointerPressed(int x, int y) {
			// handle pointer Press
		}
		used to handle pointer Realse operation 
		@Override
		public void pointerReleased(int x, int y) {
			// handle pointer Realse
		}
		used to handle pointer Drag operation 
		@Override
		public void pointerDragged(int x, int y) {
			// handle pointer Drag
		}
	
		used to perform operation when activity goes in background
		@Override
		public void hideNotify() {
			//on Game hide 
	
		}
		used to perform operation when activity comes from background to foreground
		@Override
		public void showNotify() {
			// on Game Shown
	
		}

3. MyGameConstants class: It is used to declare game Related Constants.

4. ImageLoadInfo Class: It is used to load & unload images-
	
	a. First declare image in MyGameConstants class 

	 	 public static final ImageLoadInfo APPON_LOGO = new ImageLoadInfo("appon_logo.jpg", Resources.RESIZE_NONE);
	 	 public static final ImageLoadInfo BUTTON_BG = new ImageLoadInfo("button-icon.png", Resources.RESIZE_NONE);
	
	b. When the image declared above is required, load it before using.

	  	MyGameConstants.APPON_LOGO.loadImage();

	c. To paint use GraphicsUtil Class methods
	  
	 	GraphicsUtil.drawBitmap(c, MyGameConstants.APPON_LOGO.getImage(), (MyGameConstants.SCREEN_WIDTH-MyGameConstants.APPON_LOGO.getWidth())>>1,(MyGameConstants.SCREEN_HEIGHT-MyGameConstants.APPON_LOGO.getHeight())>>1, 0, paintObject);
	
	d. If the image is no longer required unload it to reduce memory usage
	 
	 	MyGameConstants.APPON_LOGO.clear();
	
	
	
5. Resources Class: It is used to handle porting calculations according to resolution. Calculation for porting is done in init(Context context1) method.
	
	We have grouped different resolutions and prepared our own folder structure for those groups. These are-
	"lres","mres","hres","xres","xhres","xlarges"
	
	Devices that come in the range of the resolution groups mentioned below will belong to that particular group. E.g. A device with a resolution of 240x320 will belong to the lres group, as it comes in the range of 240,400.
	The resolution groups are as follows:
	{240 , 400} 		------ lres
	{480 , 640}		------ mres
	{640 , 1024}		------ hres
	{1000 , 1500}		------ xres
	{2000 , 1500}		------ xhres
	greater than above	------ xlarges
	
	Resolution folders are created in assests folder, assets folder also contains "all" folder to store files which are common for all resolutions.
	
	
	
	
	