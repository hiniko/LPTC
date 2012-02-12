package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class World
{
	
	private static final int MAX_WORLD_SIZE = 2000;
	private static final int MIN_WORLD_SIZE = MAX_WORLD_SIZE / 20;
	private static final double LOG_MODIFIER = 2.06370277381252;

	
	public Random random;
	
	private Point size;
	private CopyOnWriteArrayList<Entity> entities;
	private LinkedList<Entity> newEntities;
	private int cx, cy, cr, players, updateFrameCount;
	private long seed;
	private Simulator simulator;
	public AntFactory antFactory;
	public ColonyFactory colonyFactory;
	
	public World(long s, int p, Simulator sim)
	{	
		// Referece to the simulator 
		// TODO have a shutdown method that will null the reference to avoid 
		// cyclic references
		simulator = sim;
		
		// How many times to output stats
		updateFrameCount = Simulator.UPS * 2;
		
		
		random = new Random();
		
		// Set up random generator
		if( s == 0 )
			seed = random.nextLong();
		else
			seed = s;
		
		System.out.println("Seed is " + seed);
		
		random.setSeed(seed); 
		
		// Set the number of players
		players = p;

		// Work out size of the world if Players is more then 1
		//TODO Make sizes of the world specifiable as well
		//TODO Work out new LOG_MODIFIER value
		if(p > 1)
		{
			// work out world size
			int hw = (int) (MAX_WORLD_SIZE * (Math.log10(players) /LOG_MODIFIER) + MIN_WORLD_SIZE) ;
			size = new Point(hw,hw);
			// Work out Placement of the colonies
			cr = hw  / 2 - (hw / 10);
			// Center point of the world
			cx = (hw / 2);
			cy = (hw / 2);
		}
		else
		{
			int hw = 500;
			size = new Point(hw, hw);
			cr = hw  / 2 - (hw / 10);
			cx = hw /2;
			cy = hw /2;
		} 
		
		System.out.println("World Size is " + size.x + "x" + size.y);
		
		
	}
	
	public void initWorld()
	{
		// Create Lists
		entities = new CopyOnWriteArrayList<Entity>();
		newEntities = new LinkedList<Entity>();
		
		// Create the factories
		antFactory = new AntFactory(this);
		colonyFactory = new ColonyFactory(this);
		
		// Create the colonies 
		int placement_radius = size.x  / 2 - (size.x / 10);
		cr = placement_radius;
		int placement_angle = 360 / players;
		
		for(int i=0; i<players; i++)
		{
			String name = "Player " + (i+1) ;
			int a = (placement_angle * i);
			double x =  this.cx + placement_radius * Math.cos(Math.toRadians(a));
			double y =  this.cy + placement_radius * Math.sin(Math.toRadians(a));
			
			colonyFactory.create(name, (int)x, (int)y, 1000, "PaulsAnt");
		}
	}
	
	public void addEntity(Entity e)
	{
		//newEntities.add(e);
		entities.add(e);
	}
	
	public int getNumberOfPlayers()
	{
		return players;
	}
	
	public void update()
	{
		// Check if there are any new entities to add to the entity list
		// This is to avoid modification of the entities list during iterations
		// which would cause ConcurrentModificationExceptions
/*
		synchronized(entities)
		{
			for(Entity e : newEntities)
			{
				entities.add(e);
			}
		}
*/
		newEntities.clear();
		
		for (Entity e : entities) 
		{
			e.update();
		}
		
		// Incremental informational updates
		if(simulator.getFrames() % updateFrameCount == 0)
		{
			System.out.println("Entity count : " + entities.size());
		}
	}
	
	public Point getSize()
	{
		return size;
	}
	
	
	
	public int getHeight()
	{
		return size.y;
	}
	
	public int getWidth()
	{
		return size.x;
	}
	
	public void draw(Graphics2D g2d)
	{
		// Draw background and that
		g2d.setColor(Color.WHITE);
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, size.x, size.y);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, size.x, size.y);
		
		g2d.setColor(Color.GREEN);
		int hw = cr*2;
		
		g2d.drawOval(cx-(hw/2), cy-(hw/2), hw, hw);

		for(Entity e : entities)
		{
			e.draw(g2d);
		}
	}
}
