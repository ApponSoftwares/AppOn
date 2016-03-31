/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.util;

/**
 *
 * @author acer
 */
public class Point {

	public Point() {
	}
	public Point(double x, double y) {
		setPoints(x, y);
	}
	public double x;
	public double y;
	public void setPoints(double x, double y) {
		setX(x);
		setY(y);
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
