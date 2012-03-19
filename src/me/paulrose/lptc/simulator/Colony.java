package me.paulrose.lptc.simulator;


import java.util.LinkedList;

import javax.vecmath.Vector2d;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Colony extends Entity
{
	
	private static final int BASE_RADIUS = 20;

	
	private String name, antClass;
	private LinkedList<Ant> ants;
	private int diameter, maxAnts, antCost, energy, energyMinimum;
	private Image antSpriteFull, antSpriteMin;
	private float centerX, centerY;
	protected boolean drawEnergyCount, drawAntCount, drawOwnerName;

	
	Colony(String n, float x, float y, String c, int f, World wo)
	{
		super(n, x, y, BASE_RADIUS * 2, BASE_RADIUS *2, wo);
		
		sizeRadius = BASE_RADIUS;
		
		name = n;
		antClass = c;
		energy = f;
		
		// The size of the colony 
		// The color of the colony and its ants
		int r = (int)(world.random.nextDouble() * 255);
		int g = (int)(world.random.nextDouble() * 255);
		int b = (int)(world.random.nextDouble() * 255);
		colour = new Color(r,b,g);
		
		createAntSprites();
		
		redrawSprite = false;
		drawEnergyCount = true;
		drawAntCount = true;
		drawOwnerName = true;
		
		// List of all ants
		ants = new LinkedList<Ant>();
		
		maxAnts = 100;
		antCost = 500;
		energy = 5000;
		
		centerX = bounds.getCenterX();
		centerY = bounds.getCenterY();

	}

	
	private void createAntSprites()
	{
		antSpriteFull = world.antFactory.createAntSprite(colour,false);
		antSpriteMin = world.antFactory.createAntSprite(colour, true);
	}
	
	public void update(int delta)
	{
		super.update(delta);
		// update the sprite if needed
		
		// Shall we produce another ant?
		if(ants.size() <= maxAnts 
			&& (energy - antCost) >= antCost 
			&& (energy - antCost) > energyMinimum)
		{
			ants.add(world.antFactory.createAnt(antClass, bounds.getCenterX(), 
					bounds.getCenterY(), this));
			energy -= antCost;
			
		}
		
		if(world.getUpdates() % 1000 == 0)
		{
			energy -= 100;
		}
		
		
		float sizeW = BASE_RADIUS*2 + energy / 100;
		float sizeH = BASE_RADIUS*2 + energy / 100;
		
		bounds.setWidth(sizeW);
		bounds.setHeight(sizeH);
		
		bounds.setCenterX(centerX);
		bounds.setCenterY(centerY);
		
	}
	
	public void draw(Graphics g)
	{	
		
		
		
		float x = bounds.getX();
		float y = bounds.getY();
		float w = bounds.getWidth();
		float h = bounds.getHeight();
		float hw = w /2;
		float hh = h /2;
		
		if(g.getWorldClip().contains(x, y))
		{
			g.setColor(colour);
			g.fillOval(x , y, w, h);
			
			if(drawEnergyCount)
			{
				String amount = ""+energy;
				g.setColor(Color.black);
				g.drawString(amount, bounds.getCenterX() - ((9 * amount.length()) /2 ),
						bounds.getY() + bounds.getHeight());
			}
			
			if(drawAntCount)
			{
				String antsCount = ""+ants.size();
				g.setColor(Color.black);
				g.drawString(antsCount, bounds.getCenterX() - ((9 * antsCount.length()) /2 ),
						bounds.getCenterY()  -6);
			}
			
			if(drawOwnerName)
			{
				g.setColor(Color.black);
				g.drawString(antClass, bounds.getCenterX() - ((9 * antClass.length()) /2 ),
						bounds.getCenterY() - 35);
			}
		}
		
		super.draw(g);
		
	}
	
	public void removeAnt(Ant a)
	{
		// Check if the ant is dead first
		// Malicious players could be abusing this
		if(a.energy < 0)
			ants.remove(a);
			world.removeEntity(a);
	}

	
	public Color getColor()
	{
		return colour;
	}
	
	public void setColor(int r, int g, int b)
	{
		colour = new Color(r,g,b);
		antSpriteFull = world.antFactory.createAntSprite(colour,false);
		antSpriteMin = world.antFactory.createAntSprite(colour, true);
	}
	
	public Image getAntSprite()
	{
		if(world.isRotationEnabled())
			return antSpriteMin;
		else
			return antSpriteFull;
		
	}
	
	public void addFood(Food f)
	{
		energy += (int)f.getTotalFood();
		f.delete();
	}
	
	public void setMinimumEnergy(int i)
	{
		energyMinimum = i;
	}
}
