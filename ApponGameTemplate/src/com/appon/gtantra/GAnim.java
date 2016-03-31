package com.appon.gtantra;




import com.appon.game.MyGameConstants;
import com.appon.game.MyGameEngine;

import android.graphics.Canvas;
import android.graphics.Paint;




/**
 *
 * @author user
 */
public class GAnim {
     private int currentFrame;
     private int timer;
     public GTantra gTantra;
     private int animId;
     private GAnimListener animListener;
     private int animUID;
     private Object userData;

     public GAnim(GTantra gTantra,int animId) {
         this.gTantra = gTantra;
         this.animId = animId;
     }
     public GAnim(GTantra gTantra,int animId,int UID) {
         this(gTantra , animId);
         this.animUID = UID;
     }
    public void setUserData(Object userData) {
        this.userData = userData;
    }

    public Object getUserData() {
        return userData;
    }
    public int getCurrentFrameWidth()
    {
        return gTantra.getFrameWidth(gTantra.getFrameId(animId, currentFrame));
    }
    public int getCurrentFrameHeight()
    {
        return gTantra.getFrameHeight(gTantra.getFrameId(animId, currentFrame));
    }
    public int getCurrentFrameMinimumX()
    {
        return gTantra.getFrameMinimumX(gTantra.getFrameId(animId, currentFrame));
    }
    public int getCurrentFrameMinimumY()
    {
        return gTantra.getFrameMinimumY(gTantra.getFrameId(animId, currentFrame));
    }
//    public boolean isRectCollision(int positionX, int positionY,int arr[])
//    {
//    	
//        int frameId = gTantra.getFrameId(animId, currentFrame);
//        positionX += gTantra._iAnimFrameX[animId][currentFrame];
//        positionY += gTantra._iAnimFrameY[animId][currentFrame];
//       int col[]=new int[4];
//       gTantra.getCollisionRect(frameId, col);
//        positionX += col[0];
//        positionY += col[1];
//        return Util.isRectCollision(positionX, positionY, col[2],col[3], arr[0],arr[1], arr[2], arr[3]);
//    }
//    public boolean isBugInRect(int positionX, int positionY,int arr[])
//   {
//       int frameId = gTantra.getFrameId(animId, currentFrame);
//       positionX += gTantra._iAnimFrameX[animId][currentFrame];
//       positionY += gTantra._iAnimFrameY[animId][currentFrame];
//   
//         int col[] =new int[4];
//      
//       gTantra.getCollisionRect(frameId, col);
//       positionX += col[0];
//       positionY += col[1];
//       return Util.isRectCollision(arr[0],arr[1], arr[2], arr[3], positionX,positionY,col[2],col[3]);
//   }

    public void setAnimUID(int animUID) {
        this.animUID = animUID;
    }
    public int getAnimUID() {
        return animUID;
    }
    public void setAnimListener(GAnimListener animListener) {
        this.animListener = animListener;
    }
    public boolean isAnimationOver()
    {
        if(gTantra._iAnimFrameCnt[animId] - 1 == currentFrame)
        {
            return true;
        }
        return false;
    }
    public void reset()
    {
        currentFrame = 0;
    }
    public int getCurrentFrame()
    {
        return gTantra._iAnimFrames[animId][currentFrame];
    }
    public  int getAnimationFrameX(int frameLocation)
    {
        return gTantra._iAnimFrameX[animId][frameLocation];
    }
    public int getAnimationFrameY(int frameLocation)
    {
        return gTantra._iAnimFrameY[animId][frameLocation];
    }
    public int getAnimationCurrentCycle(int animation)
    {
        return currentFrame;
    }
    public int getNumberOfFrames()
    {
        return gTantra._iAnimFrameCnt[animId];
    }
    
    public void renderAtwon(Canvas g, int posX, int posY, int flags,boolean loop, Paint paintObject)
    {
        if(gTantra._iAnimFrameCnt[animId] == 0)
            return;
        int currentFrameTime = gTantra.frameTimer[animId][currentFrame];
        {
           gTantra.DrawAnimationFrame(g,animId,currentFrame,posX,posY,flags,paintObject);
        }
        updateFrame(loop);
        
    }
    
    
    public void render(Canvas g, int posX, int posY, int flags,boolean loop, Paint paintObject)
    {
        if(gTantra._iAnimFrameCnt[animId] == 0)
            return;
        int currentFrameTime = gTantra.frameTimer[animId][currentFrame];
        {
           gTantra.DrawAnimationFrame(g,animId,currentFrame,posX,posY,flags,paintObject);
        }
        if(MyGameEngine.getMyGameEngineState() != MyGameConstants.MYGAME_ENGINE_PAUSE_STATE)
        {
            updateFrame(loop);
        }
     
    }
    public void renderwithoutUpadate(Canvas g, int posX, int posY, int flags,boolean loop, Paint paintObject)
    {
        if(gTantra._iAnimFrameCnt[animId] == 0)
            return;
           gTantra.DrawAnimationFrame(g,animId,currentFrame,posX,posY,flags);
    }
    
    
    public void Update(boolean loop)
    {
        if(gTantra._iAnimFrameCnt[animId] == 0)
            return;
        if(MyGameEngine.getMyGameEngineState() != MyGameConstants.MYGAME_ENGINE_PAUSE_STATE)
        {
            updateFrame(loop);
        }
    }
    
  public void render(Canvas g, int posX, int posY, int flags,boolean loop, Paint paintObject,boolean isScale,float scalePercentage)
  {
      if(gTantra._iAnimFrameCnt[animId] == 0)
          return;
      int currentFrameTime = gTantra.frameTimer[animId][currentFrame];
      {
         gTantra.DrawAnimationFrame(g,animId,currentFrame,posX,posY,flags,isScale,scalePercentage,paintObject);
      }
      if(MyGameEngine.getMyGameEngineState() != MyGameConstants.MYGAME_ENGINE_PAUSE_STATE)
      {
          updateFrame(loop);
      }
   
  }
  public void renderwithoutUpadate(Canvas g, int posX, int posY, int flags,boolean loop, Paint paintObject,boolean isScale,float scalePercentage)
  {
      if(gTantra._iAnimFrameCnt[animId] == 0)
          return;
      int currentFrameTime = gTantra.frameTimer[animId][currentFrame];
      {
         gTantra.DrawAnimationFrame(g,animId,currentFrame,posX,posY,flags,isScale,scalePercentage,paintObject);
      }
      
   
  }  
    public void SpcialStaterender(Canvas g, int posX, int posY, int flags,boolean loop, Paint paintObject)
    {
        if(gTantra._iAnimFrameCnt[animId] == 0)
            return;
        int currentFrameTime = gTantra.frameTimer[animId][currentFrame];
        {
           gTantra.DrawAnimationFrame(g,animId,currentFrame,posX,posY,flags);
        }
        
            updateFrame(loop);
        
     
    }
    private void updateFrame(boolean loop)
    {
        int currentTimer = gTantra.frameTimer[animId][currentFrame];
        if(timer + 1 >= currentTimer)
        {
            timer = 0;
            currentFrame++;
            if(currentFrame >= gTantra._iAnimFrameCnt[animId])
            {
                if(loop)
                {
                    currentFrame = 0;
                   
                }else{
                    currentFrame = gTantra._iAnimFrameCnt[animId] - 1;
                }
                 if(animListener != null)
                    animListener.animationOver(this);
            }
            
            if(animListener != null)
            {
                animListener.frameChanged(this);
            }
        }else{
            timer++;
        }
    }
    public int getAnimId() {
        return animId;
    }

    public void setAnimId(int animId) {
        this.animId = animId;
    }
    public GTantra getgTantra() {
		return gTantra;
	}
    public int getAnimationCurrentCycle()
    {
        return currentFrame;
    }
    public int getCurrentFrameIndex()
    {
        return currentFrame;
    }
    public void renderWithoutUpdate(Canvas g, int posX, int posY, int flags,boolean loop)
    {
           gTantra.DrawAnimationFrame(g,animId,currentFrame,posX,posY,flags);
    }
	public boolean isAnimRendering() {
		 if(this.getAnimationCurrentCycle()>=1 && this.getAnimationCurrentCycle()<=this.getNumberOfFrames()){
            return true;
        }
		return false;
	}
}
