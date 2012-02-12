package me.paulrose.lptc.simulator;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public abstract class Entity 
{
	protected static int entityCount;

	protected String name;
	protected Point size;

	protected Point pos;
	protected int ID;
	protected World world;
	protected BufferedImage sprite;
	protected boolean redrawSprite;
	
	public Entity(String n, int x, int y, World wo)
	{
		name = n;
		pos = new Point(x,y);
		size = new Point(0,0);
		ID = ++entityCount;
		world = wo;
		redrawSprite = false;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void update(){}
	
	public void drawSprite(){}
	
	public void draw(Graphics2D g2d){}
}
