/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;




/**
 *
 * @author acer
 */
public class ProjectileMotion {
    private int g = 10;
    private int x, y;
    private int initialX , initialY;
    private int theta = 0;
    private int time;
    private int velocity;
    private static int sin_tabel[] = {0,286,572,857,1143,1428,1713,1997,2280,2563,2845,3126,3406,3686,3964,4240,4516,4790,5063,5334,5604,5872,6138,6402,6664,6924,7182,7438,7692,7943,8193,8438,8682,8923,9162,9397,9630,9860,10087,10311,10531,10749,10963,11174,11381,11585,11786,11982,12176,12365,12551,12733,12911,13085,13255,13421,13583,13741,13894,14044,14189,14330,14466,14598,14726,14849,14968,15082,15191,15296,15396,15491,15582,15668,15749,15826,15897,15964,16026,16083,16135,16182,16225,16262,16294,16322,16344,16362,16374,16382,16384,16382,16374,16362,16344,16322,16294,16262,16225,16182,16135,16083,16026,15964,15897,15826,15749,15668,15582,15491,15396,15296,15191,15082,14968,14849,14726,14598,14466,14330,14189,14044,13894,13741,13583,13421,13255,13085,12911,12733,12551,12365,12176,11982,11786,11585,11381,11174,10963,10749,10531,10311,10087,9860,9630,9397,9162,8923,8682,8438,8193,7943,7692,7438,7182,6924,6664,6402,6138,5872,5604,5334,5063,4790,4516,4240,3964,3686,3406,3126,2845,2563,2280,1997,1713,1428,1143,857,572,286,0,-285,-571,-857,-1142,-1427,-1712,-1996,-2280,-2563,-2845,-3126,-3406,-3685,-3963,-4240,-4516,-4790,-5062,-5334,-5603,-5871,-6137,-6401,-6663,-6924,-7182,-7438,-7691,-7943,-8192,-8438,-8682,-8923,-9161,-9397,-9630,-9860,-10086,-10310,-10531,-10748,-10963,-11173,-11381,-11585,-11785,-11982,-12175,-12365,-12550,-12732,-12910,-13084,-13254,-13420,-13582,-13740,-13894,-14043,-14188,-14329,-14466,-14598,-14725,-14848,-14967,-15081,-15190,-15295,-15395,-15491,-15582,-15668,-15749,-15825,-15897,-15964,-16025,-16082,-16135,-16182,-16224,-16261,-16294,-16321,-16344,-16361,-16374,-16381,-16384,-16381,-16374,-16361,-16344,-16321,-16294,-16261,-16224,-16182,-16135,-16082,-16025,-15964,-15897,-15825,-15749,-15668,-15582,-15491,-15395,-15295,-15190,-15081,-14967,-14848,-14725,-14598,-14466,-14329,-14188,-14043,-13894,-13740,-13582,-13420,-13254,-13084,-12910,-12732,-12550,-12365,-12175,-11982,-11785,-11585,-11381,-11173,-10963,-10748,-10531,-10310,-10086,-9860,-9630,-9397,-9161,-8923,-8682,-8438,-8192,-7943,-7691,-7438,-7182,-6924,-6663,-6401,-6137,-5871,-5603,-5334,-5062,-4790,-4516,-4240,-3963,-3685,-3406,-3126,-2845,-2563,-2280,-1996,-1712,-1427,-1142,-857,-571,-285};
    private static int cos_tabel[] = {16384,16382,16374,16362,16344,16322,16294,16262,16225,16182,16135,16083,16026,15964,15897,15826,15749,15668,15582,15491,15396,15296,15191,15082,14968,14849,14726,14598,14466,14330,14189,14044,13894,13741,13583,13421,13255,13085,12911,12733,12551,12365,12176,11982,11786,11585,11381,11174,10963,10749,10531,10311,10087,9860,9630,9397,9162,8923,8682,8438,8192,7943,7692,7438,7182,6924,6664,6402,6138,5872,5604,5334,5063,4790,4516,4240,3964,3686,3406,3126,2845,2563,2280,1997,1713,1428,1143,857,572,286,0,-285,-571,-857,-1142,-1427,-1712,-1996,-2280,-2563,-2845,-3126,-3406,-3685,-3963,-4240,-4516,-4790,-5062,-5334,-5603,-5871,-6137,-6401,-6663,-6924,-7182,-7438,-7691,-7943,-8191,-8438,-8682,-8923,-9161,-9397,-9630,-9860,-10086,-10310,-10531,-10748,-10963,-11173,-11381,-11585,-11785,-11982,-12175,-12365,-12550,-12732,-12910,-13084,-13254,-13420,-13582,-13740,-13894,-14043,-14188,-14329,-14466,-14598,-14725,-14848,-14967,-15081,-15190,-15295,-15395,-15491,-15582,-15668,-15749,-15825,-15897,-15964,-16025,-16082,-16135,-16182,-16224,-16261,-16294,-16321,-16344,-16361,-16374,-16381,-16384,-16381,-16374,-16361,-16344,-16321,-16294,-16261,-16224,-16182,-16135,-16082,-16025,-15964,-15897,-15825,-15749,-15668,-15582,-15491,-15395,-15295,-15190,-15081,-14967,-14848,-14725,-14598,-14466,-14329,-14188,-14043,-13894,-13740,-13582,-13420,-13254,-13084,-12910,-12732,-12550,-12365,-12175,-11982,-11785,-11585,-11381,-11173,-10963,-10748,-10531,-10310,-10086,-9860,-9630,-9397,-9161,-8923,-8682,-8438,-8192,-7943,-7691,-7438,-7182,-6924,-6663,-6401,-6137,-5871,-5603,-5334,-5062,-4790,-4516,-4240,-3963,-3685,-3406,-3126,-2845,-2563,-2280,-1996,-1712,-1427,-1142,-857,-571,-285,0,286,572,857,1143,1428,1713,1997,2280,2563,2845,3126,3406,3686,3964,4240,4516,4790,5063,5334,5604,5872,6138,6402,6664,6924,7182,7438,7692,7943,8192,8438,8682,8923,9162,9397,9630,9860,10087,10311,10531,10749,10963,11174,11381,11585,11786,11982,12176,12365,12551,12733,12911,13085,13255,13421,13583,13741,13894,14044,14189,14330,14466,14598,14726,14849,14968,15082,15191,15296,15396,15491,15582,15668,15749,15826,15897,15964,16026,16083,16135,16182,16225,16262,16294,16322,16344,16362,16374,16382};
    private int maxHeight;
    private boolean isOnHalf;
    private int updateSpeed = 25;
    private int lastY,lastDirection;
    private boolean reachedTop;
    public void init(int intialX,int intialY,int velocity,int theta)
    {
        reachedTop = false;
        x = this.initialX = intialX;
        y = this.initialY = intialY;
        this.theta = theta;
        this.velocity = velocity;
        time = 0;
        maxHeight = (((velocity*sin(theta)) >> 14)*((velocity*sin(theta)) >> 14))/(2*g);
        setIsOnHalf(false);
         lastY = lastDirection = -1;
    }
    public void initFromHeight(int intialX,int intialY,int maxHeight,int theta)
    {
        reachedTop = false;
        x = this.initialX = intialX;
        y = this.initialY = intialY;
        this.theta = theta;
        time = 0;
        this.maxHeight = maxHeight;
        velocity = (sqrt(2*g*maxHeight) << 14) / sin(theta);
        setIsOnHalf(false);
        lastY = lastDirection = -1;
    }
    public void setUpdateSpeed(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }
    public int getCurrentHeight()
    {
        return Math.abs(y - initialY);
    }
    public int getX() {
        return x;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getY() {
        return y;
    }
    
//      x  += ( (time * (Math.cos(Math.toRadians(theta)) * velocity) + 0.5 * g *(time*time)) );
//        y  += ( (time * (Math.sin(Math.toRadians(theta)) * velocity) + 0.5 * g *(time*time)) );
//          velocity -= 1;
    public void update()
    {
        // always roatating time by 7 for fixed point calculation
        x = (velocity*cos(theta) *time) >> 21;
        int yFirstPart = (int)(velocity*sin(theta)*time) >> 21;
        int ySecondPart = (int)((g*time*time) >> 15);
        y = yFirstPart - ySecondPart;
        y =initialY - y;
        x= x + initialX;
        time+= updateSpeed;
       if(lastY == -1)
        {
            lastY = y;
        }else{
            int direction = lastY - y;
            if(lastDirection == -1)
            {
                lastDirection = direction;
            }else{
                if(lastDirection != 0 && direction != 0)
                {
                   
                    int signOfDirection = lastDirection / Math.abs(lastDirection);
                    int signOfLastDirection = direction / Math.abs(direction);
                    if(signOfDirection != signOfLastDirection)
                    {
                        if(!reachedTop)
                        {
                          onTop();
                          reachedTop = true;
                        }
                          
                        setIsOnHalf(true);
                    }
                }
                lastY = y;
            }
            
        }
    }
 public void onTop()
 {
//    Hero.onTop=true;
 }
    public boolean isOnHalf() {
        return isOnHalf;
    }
    
    public static int sin(int theta)
    {
        theta = theta % 360;
        if(theta < 0)
            theta = 360 - theta;
        return sin_tabel[theta];
    }
    public static int cos(int theta)
    {
        theta = theta % 360;
        if(theta < 0)
            theta = 360 - theta;
        return cos_tabel[theta];
    }
      public static int sqrt (int nRoot){
            int nSqrt = 0;
            int nTemp;

            for(int i = 0x10000000; i != 0; i >>= 2)
            {
                nTemp = nSqrt + i;
                nSqrt >>= 1;
                if(nTemp <= nRoot)
                {
                    nRoot -= nTemp;
                    nSqrt += i;
                }
        }
        return nSqrt;
    }
      
   
//     public void update()
//    {
//      
//        x =(int)(velocity*Math.cos(Math.toRadians(theta))*time) >> 7;
//        int yFirstPart = (int)(velocity*Math.sin(Math.toRadians(theta)) *time) >> 7;
//        int ySecondPart = (int)(((g*time*time) >> 1) >> 14);
//        y = yFirstPart - ySecondPart;
//        y =initialY - y;
//        x= x + initialX;
//        time+= 10;
//    }
  public void setIsOnHalf(boolean isOnHalf) {
        this.isOnHalf = isOnHalf;
    }
}
