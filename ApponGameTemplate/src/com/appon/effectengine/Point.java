/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.effectengine;

/**
 *
 * @author acer
 */
public class Point {

    public Point() {
    }
    
    public int x;
    public int y;
    public void setPoints(int x,int y)
    {
        setX(x);
        setY(y);
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
