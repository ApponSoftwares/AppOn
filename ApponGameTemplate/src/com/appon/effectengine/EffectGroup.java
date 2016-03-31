/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;

import com.appon.util.Serilizable;

/**
 *
 * @author acer
 */
public class EffectGroup  implements Serilizable{
    private Vector effects = new Vector();
    
    public int getSize()
    {
        return effects.size();
    }

    public Vector getEffects() {
        return effects;
    }
    
    public Effect getEffect(int index)
    {
        return (Effect)effects.elementAt(index);
    }
     
    public byte[] serialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        EffectsSerilize.serialize(effects, bos);
        bos.flush();
        byte data[] = bos.toByteArray();
        bos.close();
        bos = null;
        return data;
    }
    public Effect createEffect(int index) throws Exception
    {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        EffectsSerilize.serialize(getEffect(index), bos);
//        bos.flush();
//        byte data[] = bos.toByteArray();
//        bos.close();
//        bos = null;
//        ByteArrayInputStream bis = new ByteArrayInputStream(data);
//        Effect e = (Effect)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
//        return e;
        return getEffect(index).clone();
    }
    
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
       effects = (Vector)EffectsSerilize.deserialize(bis, EffectsSerilize.getInstance());
       bis.close();
       return null;
    }

    public void port(int xper,int yper)
    {
        for (int i = 0; i < effects.size(); i++) {
            Effect object = (Effect)effects.elementAt(i);
            object.port(xper, yper);
        }
    }
    public int getClassCode() {
        return EffectsSerilize.EFFECT_GROUP_TYPE;
    }
}
