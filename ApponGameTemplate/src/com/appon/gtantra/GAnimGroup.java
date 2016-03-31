/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.gtantra;

/**
 *
 * @author acer
 */
public class GAnimGroup {
    private GAnim[] anims;
    public GAnimGroup(GTantra gTantra) {
        anims = new GAnim[gTantra.getTotalAnimations()];
        for (int i = 0; i < anims.length; i++) {
            anims[i] = new GAnim(gTantra, i);
        }
    }
    public GAnim getAnim(int id)
    {
        return anims[id];
    }
    public void setAnimListener(GAnimListener animListener) {
        for (int i = 0; i < anims.length; i++) {
          anims[i].setAnimListener(animListener);
      }
     
  }
   public void setGroupUid(int groupUid)
   {
        for (int i = 0; i < anims.length; i++) {
          anims[i].setAnimUID(groupUid);
      }
   }
    
            
}
