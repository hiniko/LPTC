package me.paulrose.lptc.simulator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;


public abstract class Ant extends MoveableEntity
{
	
	protected Colony colony;
	protected int energy, view_radius;
	protected static final int SIZE = 10;
	public static int drawCount = 0;

	
	public Ant(String n, int x, int y, Colony c, World wo)
	{
		super(n, x, y, wo);
		
		colony = c;
		size.x = SIZE;
		size.y = SIZE;
		
		// Starting values for the ant
		energy = 100;
		view_radius = 20;

	}
	
	public void update(int delta)
	{
		super.update(delta);
		run();
	}
	
	/*
	 * This method will contain the logic from the player to the 
	 */
	public void run(){}

	public void draw(Graphics g)
	{
		int x = (int)pos.x;
		int y = (int)pos.y;
		int w = (int)size.x;
		int h = (int)size.y;
		int hw = (int)size.x /2;
		int hh = (int)size.y /2;
		
		Image sprite = colony.getAntSprite();
		
		
		if(world.isRotationEnabled() && g.getWorldClip().contains(x, y))
		{
			sprite.setRotation((float)Math.toDegrees(rotation)-90);
			g.drawImage(sprite, x, y);
			drawCount++;
		}
		else if (world.drawClip.contains(x, y))
		{
			g.drawImage(sprite, x, y);
		}	
	}
		

}
