package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public abstract class Ant extends MoveableEntity
{
	
	protected Colony colony;
	protected int energy, view_radius;
	protected static final int ANT_SIZE = 10;

	
	public Ant(String n, int x, int y, Colony c, World wo)
	{
		super(n, x, y, wo);
		
		colony = c;
		size.x = ANT_SIZE;
		size.y = ANT_SIZE;
		
		// Starting values for the ant
		energy = 100;
		view_radius = 20;

	}
	
	public void update()
	{
		super.update();
		run();
	}
	
	/*
	 * This method will contain the logic from the player to the 
	 */
	public void run(){}

	public void draw(Graphics2D g2d)
	{
		int x = (int)pos.x;
		int y = (int)pos.y;
		int w = (int)size.x;
		int h = (int)size.y;
		int hw = (int)size.x /2;
		int hh = (int)size.y /2;
		
		AffineTransform rotation_trans = new AffineTransform();
		rotation_trans.rotate(rotation - Math.toRadians(90), hw, hh);
		
		g2d.drawImage(colony.antSprite, new AffineTransformOp(rotation_trans, 
				AffineTransformOp.TYPE_BICUBIC), x - hw, y - hh);
		
		

		
		
		
	}

}
