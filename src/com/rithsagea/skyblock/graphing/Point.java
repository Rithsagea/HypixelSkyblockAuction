package com.rithsagea.skyblock.graphing;

import java.awt.Color;

public class Point {
	
	protected double x;
	protected double y;
	
	protected Color color;
	
	public Point(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Color getColor() {
		return color;
	}
}
