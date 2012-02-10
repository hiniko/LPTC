package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public abstract class Ant extends MoveableEntity
{
	
	protected Colony colony;
	protected int energy, view_radius;

	
	public Ant(String name, int x, int y, Colony c, World w)
	{
		super(name, x, y, w);
		
		colony = c;
		size = new Dimension(10,10);

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
		// Position
		int x = (int)pos.x;
		int y = (int)pos.y;
		// Half size
		int hw =  (int)(size.width/2);
		int hh = (int)(size.height/2);
		// Center position
		int cx = x + hw;
		int cy = y + hh;
		
		// Create and assign transform
		AffineTransform old_trans = g2d.getTransform();
		AffineTransform new_trans = g2d.getTransform();
		
		new_trans.rotate(rotation - Math.toRadians(90), cx, cy);
		g2d.setTransform(new_trans);
		
		g2d.setColor(colony.getColor());
		g2d.fillOval(x, y, size.width , size.height);
					
		g2d.setColor(Color.RED);
		g2d.drawLine(cx, y, x+hw, y+size.height);
		g2d.fillRect(x, y+size.height, size.width, 1);
		
		// Restore old transformation
		g2d.setTransform(old_trans);
		
		// Reference point
		// Draw destination for sanity
		g2d.setColor(colony.getColor());
		g2d.fillOval((int)dest.x, (int)dest.y, 4,4);
		
		
	}

}
