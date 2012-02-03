package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.vecmath.Vector2d;

public class PaulsAnt extends Ant{
	
	PaulsAnt(String name, int x, int y, Colony c)
	{
		super(name, x, y,c);
	}
	
	public void update()
	{
		super.update();
		
		run();
		
	}
	
	public void run()
	{
		// This is my logic for my ant
	}


}
