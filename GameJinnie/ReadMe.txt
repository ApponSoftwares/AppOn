
Please use GameJinni.chm for understanding the GameJinnie in detail.

Thechnical integration of GameJinnie with native android game is explained below:
-----------------------------------------------------------------------
\\\\\\\\\\\\Game Jinni//////////////
-----------------------------------------------------------------------	

	Gtantra contains modules, frames, animations and colliders. Modules are the parts of images cut on sprite sheets, frames are created by joining one or more modules and animations are created using two or more frames.

	Collionsion contains {collisionX,collisionY,collisionWidth,collisionHeight} set in the frame with collisionId.
	Each frame can have multiple collisions.


1. Declare GTantra Object to load 
	GTantra gTantra=new GTantra();

2. Use GAnim to get animation from gtantra at particular animid
	GAnim anim=null;
	
	a. Load .GT File when required
		
		gTantra.Load(GTantra.getFileByteData("coin_anim.GT", MyGameActivity.getInstance()),false);

	b. Load anim
		anim=new GAnim(gTantra, animid);
	
	c. Unload it when not Required
		gTantra.unload();
	

3. We have many methods in our Game Jinni SDK, for example-
	
	a. To get module height
		gTantra.getModuleHeight(moduleID);
	
	b. To get module width
		gTantra.getModuleWidth(moduleID);
	
	c. To get frame height
		gTantra.getFrameHeight(frameID)
	
	d. To get frame width
	 gTantra.getFrameWidth(frameID);

	e. To get collision rect from a specified frame frame
		int tmp=new int[4];
		gTantra.getCollisionRect(frameId, tmp, collsionindex)

	f. To paint frame module
	 	gTantra.DrawModule(g, module_id, posX, posY, flag, paint);

	g. To paint frame module
		gTantra.DrawFrame(g, frame, posX, posY, flags, paint);
	
	h. To paint animation
		anim.render(g, posX, posY, flags, loop, paintObject);
		flags value is set to be 0-original, 1-flip in X, 2- flip in Y
		loop = true for continue animation
		loop = false for one tume animation
	
	i. To check if animation is over
		anim.isAnimationOver()
	
	j. To check if animation is rendering
		anim.isAnimRendering()
	
	


