Technical integration of Effect creator with game is mentioned below:

-----------------------------------------------------------------------
\\\\\\\\\\\\Effect Creator//////////////
-----------------------------------------------------------------------	

1. First load the .effect file stored in assets/all folder.
	
	 public static EffectGroup effectGroup=null;
	[Effect group contains several different effects, which can be accessed by entering their id.]

        [ResourceManager class is used to manage font & images.]
	try {
    	ResourceManager.getInstance().setImageResource(0, Constants.Image.getImage());
	if(effectGroup==null)
		effectGroup=com.appon.effectengine.Util.loadEffect(GTantra.getFileByteData("/filename.effect",GameActivity.getInstance()),false,true);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


2. After loading you can get Effect as follows
	Effect effect=Constants.arrowEffectGroup.getEffect(effectId).clone();
	
3. To reset an effect call
	effect.reset();

4. To paint an effect call the method mentioned below:
	effect.paint(g, effectX, effectY, loop, zoom, paintObject);
	
	loop = true for continue animation
	loop = false for one time animation
	zoom - value to scale up or scale down

4. To check if an effect has finished playing-
	this.effect.isEffectOver()
	

5. We can get Eshape used inside EffectGroup to change or use its properties programatically.
	E.g.-
	 ERect eShape=(ERect) com.appon.effectengine.Util.findShape(effectGroup, EFFECT_ID);
      		eShape.getX();
       		eShape.getY();
        	eShape.getWidth();
        	eShape.getHeight();
----------------and many more properties.

