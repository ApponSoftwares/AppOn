/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.gtantra;

/**
 *
 * @author acer
 */
public class Layer
{
    public int cells[];
    public int flags[];
    private boolean marker = false;
    public int[] getCells() {
        return cells;
    }

    public int[] getFlags() {
        return flags;
    }

    public boolean isMarker() {
        return marker;
    }

    public void setMarker(boolean marker) {
        this.marker = marker;
    }

}