package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.vecmath.Vector2d;

public class Colony extends Entity
{
	private static final int pheromoneMapReducer = 20;
	private static final int pheromoneUpdateCount = 30;
	
	private String name;
	private String ant_class;
	private LinkedList<PaulsAnt> ants;
	private int diameter, maxAnts, antCost, antCount, pheromoneSize, 
		pheromoneUpdate;
	private Color color;
	private Dimension size;
	private long food;
	private int[][] pheromone;
	
	Colony(final int x, final int y, final String n, final String c, final long f)
	{
		super(n, x, y );
		
		name = n;
		ant_class = c;
		food = f;
		
		// The size of the colony 
		diameter = (int) food / 10;
		size = new Dimension(diameter, diameter);
		
		
		// The color of the colony and its ants
		int r = (int)(Simulator.instance().random.nextDouble() * 255);
		int g = (int)(Simulator.instance().random.nextDouble() * 255);
		int b = (int)(Simulator.instance().random.nextDouble() * 255);
		color = new Color(r,g,b);
		
		// List of all ants
		ants = new LinkedList<PaulsAnt>();
		
		// Pheromone trails for the ants
		pheromoneSize = Simulator.instance().getWorldSize().width / pheromoneMapReducer;
		pheromone = new int[pheromoneSize][pheromoneSize];
		pheromoneUpdate = 10;

		
		for (int i=0; i < pheromoneSize; i++)
		{
			for (int j=0; j < pheromoneSize; j++)
			{
				pheromone[i][j] = (int) 0;
			}
		}

		maxAnts = 20;
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
		
		// pheromone evaporation
		pheromoneUpdate++;
		if(pheromoneUpdate >= pheromoneUpdateCount)
		{
			pheromoneUpdate = 0;
			for (int i=0; i < pheromoneSize; i++)
			{
				for (int j=0; j < pheromoneSize; j++)
				{
					pheromone[i][j] -= 1;
				}
			}
		}
		
		
		for(PaulsAnt a : ants)
		{
			a.update();
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
		
		for(PaulsAnt a : ants)
		{
			//TODO Need to move drawing of entities to WORLD and also provide synchronous access
			a.draw(g2d);
		}

		
		
		g2d.setColor(Color.CYAN);
		for (int i=0; i < pheromoneSize; i++)
		{
			for (int j=0; j < pheromoneSize; j++)
			{
				//g2d.fillOval(i*pheromoneMapReducer, j*pheromoneMapReducer, 
				//		(int) (pheromone[i][j]/10), (int) (pheromone[i][j]/10));
			}
		}
		
		
	}
	
	public Color getColor()
	{
		return color;
	}
	
	
	public void createAnt()
	{
		
		ants.add(new PaulsAnt( "Ant " + antCount, (int)this.pos.x, (int)this.pos.y, this) );
	}
	
}
