package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.vecmath.Vector2d;

public class Colony extends Entity
{
	private String name;
	private String antClass;
	private LinkedList<Ant> ants;
	private int diameter, maxAnts, antCost, antCount;
	private Color color;
	private Dimension size;
	private long food;
	
	Colony(final int x, final int y, final String n, 
			final String c, final long f, final World w)
	{
		super(n, x, y, w);
		name = n;
		antClass = c;
		food = f;
		
		// The size of the colony 
		diameter = (int) food / 10;
		size = new Dimension(diameter, diameter);
		
		
		// The color of the colony and its ants
		int r = (int)(world.random.nextDouble() * 255);
		int g = (int)(world.random.nextDouble() * 255);
		int b = (int)(world.random.nextDouble() * 255);
		
		color = new Color(r,g,b);
		
		// List of all ants
		ants = new LinkedList<Ant>();
		
		maxAnts = 2;
		antCost = 1000;
		food = antCost * maxAnts;
		antCount = 0;
	}
	
	public void update()
	{
		super.update();
		
		if(ants.size() <= maxAnts && food > antCost)
		{
			createAnt();
			food -= antCost;
		}
	}
	
	public void draw(Graphics2D g2d)
	{
		// Draw a circle for now
		g2d.setColor(color);
		g2d.fillOval( (int)pos.x-(size.width /2), (int)pos.y-(size.width /2), size.width, size.height);
		g2d.setColor(Color.MAGENTA);
		// Draw the name of the player
		g2d.drawString(name, (int) pos.x + (size.width /2), (int) pos.y + (size.height / 2) );
		
		for(Ant a : ants)
		{
			//TODO Needd to move drawing of entities to WORLD and also provide synchronous access
			a.draw(g2d);
		}		
	}
	
	public Color getColor()
	{
		return color;
	}
	
	
	public void createAnt()
	{
		
		ants.add(world.antFactory.createAnt(antClass, (int)pos.x, (int)pos.y, this));
	}
	
}
