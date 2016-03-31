/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

/**
 *
 * @author acer
 */
public interface EffectListener {
    public void effectOver(Effect effect);
    public void effectTimeFrameChanged(int index);
}
