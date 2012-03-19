package me.paulrose.lptc.simulator;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public abstract class Entity 
{
	protected static int entityCount;

	protected String name;

	protected int ID;
	
	protected Rectangle bounds, bodyBounds;
	protected World world;
	protected Image sprite;
	protected boolean redrawSprite, drawID, drawBounds;
	protected boolean isBeingCarried, isCarrying, dropped;
	protected Entity carrying;
	protected ArrayList<Entity> viewCollisions, collisions;
	protected int sizeRadius;
	protected Color colour;
	
	public Entity(String n, float x, float y, int w, int h, World wo)
	{
		name = n;
		bounds = new Rectangle(0,0,w,h);
		bounds.setCenterX(x);
		bounds.setCenterY(y);
		ID = ++entityCount;
		world = wo;
		redrawSprite = false;
	
		viewCollisions = new ArrayList<Entity>();
		collisions = new ArrayList<Entity>();
		
		drawID = false;
		drawBounds = false ;
		isBeingCarried  = false;
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void update(int delta){}
	
	public void drawSprite(){}
	
	public void draw(Graphics g)
	{
		 
		// Draw the center point
		g.setColor(Color.green);
		g.fillRect(bounds.getCenterX(), bounds.getCenterY(), 1, 1);
		
		// Draw the ID of the Entity			
		if(drawID)
		{
			g.setColor(Color.black);
			g.drawString("" + ID, bounds.getX(), bounds.getY());
		}
		// Draw the collision bounds of the ant
		if(drawBounds)
		{
			g.setColor(Color.cyan);
			g.draw(bounds);
		}
		
	}
	
	public float distanceTo(Entity e)
	{
		
		float dx = Math.abs(bounds.getCenterX()) - Math.abs(bounds.getCenterX());
		float dy = Math.abs(bounds.getCenterY()) - Math.abs(bounds.getCenterY());
		
		float distance = dx + dy;
		
		return distance;
	}

	public void dropped()
	{
		isBeingCarried = false;
		dropped = true;
	}
	
	public Color getColour()
	{
		if(colour != null) return colour;
		else return null;
	}
	
	public boolean isBeingCarried()
	{
		return (isBeingCarried) ? true : false;
	}
	
	public boolean isNotBeingCarried()
	{
		return (! isBeingCarried ) ? true : false;
	}
	
	public void setBeingCarried(Boolean b)
	{
		isBeingCarried = b;
	}
	
	public boolean isCarrying()
	{
		return isCarrying;
	}
	
	public void delete()
	{
		world.deleteEntity(this);
	}
}
