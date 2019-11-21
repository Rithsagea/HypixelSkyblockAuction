package com.rithsagea.skyblock.graphing;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = -8882979079296934988L;

	private static int xMin = 0;
	private static int yMin = 0;
	private static int xMax = 0;
	private static int yMax = 0;
	
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public GraphPanel() {
		
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public void update() {
		
	}
}
