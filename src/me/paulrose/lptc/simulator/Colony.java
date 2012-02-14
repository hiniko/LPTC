package me.paulrose.lptc.simulator;


import java.util.LinkedList;

import javax.vecmath.Vector2d;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Colony extends Entity
{
	
	private static final int BASE_SIZE = 20;
	private String name;
	private String antClass;
	private LinkedList<Ant> ants;
	private int diameter, maxAnts, antCost;
	Color colour;
	private long food;
	public Image sprite, antSprite;
	
	Colony(String n, int x, int y, String c, long f, World wo)
	{
		super(n, x, y, wo);
		
		name = n;
		antClass = c;
		food = f;
		
		// The size of the colony 
		diameter = (int) (BASE_SIZE);
		size = new Point(diameter, diameter);
		// The color of the colony and its ants
		int r = (int)(world.random.nextDouble() * 255);
		int g = (int)(world.random.nextDouble() * 255);
		int b = (int)(world.random.nextDouble() * 255);
		colour = new Color(r,g,b);
		
		// Flag a new buffered image to be created
		antSprite = world.antFactory.createAntSprite(colour);
		
		redrawSprite = false;
		
		
		// List of all ants
		ants = new LinkedList<Ant>();
		
		maxAnts = 2;
		antCost = 100;
		food = maxAnts * antCost; //world.random.nextInt(101);

	}
	
	public void update()
	{
		super.update();
		// update the sprite if needed

			
		
		
		if(ants.size() <= maxAnts && food >= antCost)
		{
			ants.add(world.antFactory.createAnt(antClass, (int)pos.x, (int)pos.y, this));
			food -= antCost;
		}
		
		food++;
		

	}
	
	public void draw(Graphics g)
	{	
		int x = (int)pos.x;
		int y = (int)pos.y;
		int w = (int)size.x;
		int h = (int)size.y;
		
		g.setColor(colour);
		g.fillOval((float)pos.x, (float)pos.y, (float)size.x, (float)size.y);
		
	}

	
	public Color getColor()
	{
		return colour;
	}
	
	
}
