package me.paulrose.lptc.simulator;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.vecmath.Vector2d;

public class Entity 
{

	private Vector2d vec;
	private String name;
	private Dimension size, pos;
	
	Entity(String name, int x, int y)
	{
		this.name = name;
		this.vec = new Vector2d();
		this.vec.x = x;
		this.vec.y = y;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void draw(Graphics2D g2d)
	{
		
	}
}
