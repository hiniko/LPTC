package me.paulrose.lptc.simulator;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.vecmath.Vector2d;

public abstract class Entity 
{
	protected static int entityCount;

	protected Vector2d pos;
	protected String name;
	protected Dimension size;
	protected int ID;
	protected World world;
	
	public Entity(String n, int x, int y, World w)
	{
		name = n;
		pos = new Vector2d(x,y);
		size = new Dimension(0,0);
		ID = ++entityCount;
		world = w;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void update(){}
	
	public void draw(Graphics2D g2d){}
}
