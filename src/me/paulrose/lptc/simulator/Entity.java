package me.paulrose.lptc.simulator;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.vecmath.Vector2d;

public abstract class Entity 
{

	protected Vector2d pos;
	protected String name;
	protected Dimension size;
	
	public Entity(String name, int x, int y)
	{
		this.name = name;
		this.pos = new Vector2d(x,y);
		this.size = new Dimension(0,0);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void update(){}
	
	public void draw(Graphics2D g2d){}
}
