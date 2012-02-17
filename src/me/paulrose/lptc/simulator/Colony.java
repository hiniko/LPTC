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
	public Image antSpriteFull, antSpriteMin;
	
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
		antSpriteFull = world.antFactory.createAntSprite(colour,false);
		antSpriteMin = world.antFactory.createAntSprite(colour, true);
		
		redrawSprite = false;
		
		
		// List of all ants
		ants = new LinkedList<Ant>();
		
		maxAnts = 10;
		antCost = 100;
		food = maxAnts * antCost; //world.random.nextInt(101);

	}
	
	public void update(int delta)
	{
		super.update(delta);
		// update the sprite if needed

		if(ants.size() <= maxAnts && food >= antCost)
		{
			ants.add(world.antFactory.createAnt(antClass, (int)pos.x, (int)pos.y, this));
			food -= antCost;
		}
		
		//food++;
		

	}
	
	public void draw(Graphics g)
	{	
		float x = (float)pos.x;
		float y = (float)pos.y;
		float w = (float)size.x;
		float h = (float)size.y;
		float hw = w /2;
		float hh = h /2;
		
		
		g.setColor(colour);
		g.fillOval(x - hw, y-hh, w, h);
		
	}

	
	public Color getColor()
	{
		return colour;
	}
	
	public Image getAntSprite()
	{
		if(world.isRotationEnabled())
			return antSpriteMin;
		else
			return antSpriteFull;
		
	}
	
	
}
