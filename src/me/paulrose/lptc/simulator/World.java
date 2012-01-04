package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class World
{
	private static int SIDE = 100;
	public Dimension size;
	private LinkedList<Entity> entities;
	private int cx, cy, cr;
	
	World(int c)
	{
		// Check arguments
		if( c < 2 )
			throw new IllegalArgumentException("Colonies must be a least 2");
		
		// Create lists
		entities = new LinkedList<Entity>(); 
		
		// Work out size of the world
		int hw =  SIDE*c;
		size = new Dimension(hw,hw);
		// Work out angles of the colonies
		int placement_radius = hw  / 2 - 100;
		this.cr = placement_radius;
		int placement_angle = 360 / c;
		
		System.out.println("Placement circle radius : " + placement_radius);
		System.out.println("Placement angle : " + placement_angle);
		
		cx = (hw / 2);
		cy = (hw / 2);
		
		System.out.println("WORLD:: " + hw);
		System.out.println("PC:: " + cx + " - " + cy + " - " + cr);
		
		// Test Ant
		//Ant humble_ant = new Ant(null, null);
		
		// Create the colonies 
		for(int i=0; i<c; i++)
		{
			String name = "Player " + (i+1) ;
			int a = (placement_angle * i);
			System.out.println("Placement Angle: " + a);
			double x =  this.cx + placement_radius * Math.cos(Math.toRadians(a));
			double y =  this.cy + placement_radius * Math.sin(Math.toRadians(a));
			
			entities.add( new Colony((int)x,(int) y, name, "Ant", 100) );
		}
	}
	
	public void update()
	{
		
	}
	
	
	public void draw(Graphics2D g2d)
	{
		// Draw background and that
		g2d.setColor(Color.WHITE);
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, size.width, size.height);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, size.width, size.height);
		
		g2d.setColor(Color.GREEN);
		int hw = cr*2;
		
		g2d.drawOval(cx-(hw/2), cy-(hw/2), hw, hw);
		
		
		for(Entity e : entities)
		{
			e.draw(g2d);
		}

		
	}

}
