package me.paulrose.lptc.simulator;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class World
{

	private static final int MAX_WORLD_SIZE = 2000;
	private static final int MIN_WORLD_SIZE = MAX_WORLD_SIZE / 20;
	private static final double LOG_MODIFIER = 2.06370277381252;
	public static final boolean deltaMovement = true;

	public Random random;

	private Point size;
	private CopyOnWriteArrayList<Entity> entities;
	private int players, maxFood, maxFoodSize, currentTotalFood, tickCount;
	private float cx, cy, cr;
	private long seed, updates;
	public AntFactory antFactory;
	public ColonyFactory colonyFactory;
	public FoodFactory foodFactory;
	private boolean drawRotationEnabled, drawQuadTree, drawColonyCircle, testRun;
	private QuadTree qtree;
	public Rectangle drawClip;
	private String testAntName;

	
	public void createAllAntBattleWorld(long seed, String folder)
	{
		// Create world basics
		createRandomGenerator(seed);
		createFactoriesAndLists();
		
		// Work out player count
		players = antFactory.getRegisteredAntNames().size();
		
		// Size the world
		sizeWorld();
		createColonies();
		createFood();
		defaultSettings();
		
		
	}
	
	public void createPlayerAntTestWorld(long seed,
			int totalPlayers)
	{
		// Argument Cleansing
		if(totalPlayers < 2)
			totalPlayers = 2;
		
		// Create core elements of world
		createRandomGenerator(seed);
		createFactoriesAndLists();
		
		// Set player count
		players = antFactory.getRegisteredAntNames().size();
		
		// Size the world
		sizeWorld();
		
		// Work out how many ants we need to load for this run
		int antsToLoad = totalPlayers - 1;
		this.testRun = true;
		
		createColonies();
		
		createFood();
		
		defaultSettings();
		
	}
	
	private void createColonies()
	{
		// Create the colonies
		float placement_radius = size.x / 2 - (size.x / 10);
		cr = placement_radius;
		float placement_angle = 0;
		
		if(players > 1)
			placement_angle = 360 / players;
		
		
		// Get the list of loaded ants and shuffle them
		LinkedList<String> antNames = new LinkedList<String>(antFactory.getRegisteredAntNames());
		Collections.shuffle(antNames);
		
		for (int i = 0; i < players; i++)
		{
			
			float a = (placement_angle * i);
			float x = (float) (this.cx + placement_radius
					* Math.cos(Math.toRadians(a)));
			float y = (float) (this.cy + placement_radius
					* Math.sin(Math.toRadians(a)));
			
			String name = antNames.pop();
			colonyFactory.create(name, x, y, 1000, name);
			
		}
		
	}
	
	private void defaultSettings()
	{
		drawColonyCircle = true;
		maxFoodSize = 1000;
	}
	
	private void createFood()
	{
		// Place food about
		while (currentTotalFood < maxFood)
		{
			foodFactory.create("Sugar", maxFoodSize, random.nextInt(size.x - 200) + 100,
					random.nextInt(size.y - 200) + 100);
			
			currentTotalFood += maxFoodSize;

		}

	}
	

	
	private void sizeWorld()
	{
		// Work out size of the world if Players is more then 1
		// TODO Make sizes of the world specifiable as well
		// TODO Work out new LOG_MODIFIER value
		if (players > 2)
		{
			// work out world size
			int hw = (int) (MAX_WORLD_SIZE
					* (Math.log10(players) / LOG_MODIFIER) + MIN_WORLD_SIZE);
			
			size = new Point(hw, hw);
			// Work out Placement of the colonies
			cr = hw / 2 - (hw / 10);
			// Center point of the world
			cx = (hw / 2);
			cy = (hw / 2);
		} else
		{
			int hw = 500;
			size = new Point(hw, hw);
			cr = hw / 2 - (hw / 10);
			cx = hw / 2;
			cy = hw / 2;
		}
		
		// Create the quad tree based on the world size
		qtree = new QuadTree(0, 0, size.x, size.y, 5, 3);
	}
	
	private void createRandomGenerator(long seed)
	{
		random = new Random();

		// Set up random generator
		if (seed == 0)
			this.seed = random.nextLong();
		else
			this.seed = seed;

		System.out.println("Seed is " +seed);
		random.setSeed(seed);
	}
	
	
	private void createFactoriesAndLists()
	{
		
		// Create Factories
		antFactory = new AntFactory(this);
		antFactory.registerFromFolder(System.getProperty("user.dir") +  "/ants/");
		colonyFactory = new ColonyFactory(this);
		foodFactory = new FoodFactory(this);
		
		// Create Lists
		entities = new CopyOnWriteArrayList<Entity>();
		
		// Reset Entity count
		Entity.entityCount = 0;
		
		//
		drawClip = new Rectangle(0, 0, 0, 0);
	}
	

	public void addEntity(Entity e)
	{
		// newEntities.add(e);
		entities.add(e);
		qtree.insert(e);
	}
	
	public void removeEntity(Entity e)
	{
		entities.remove(e);
		qtree.delete(e);
	}

	public void deleteEntity(Entity e)
	{
		entities.remove(e);
		qtree.delete(e);
	}

	public int getNumberOfPlayers()
	{
		return players;
	}

	public void update(int delta)
	{
		
		tickCount++;
		
		if(tickCount / 4 ==1)
			tickCount = 0;

		// Find all colliding entities
		qtree.update();

		for (Entity e : entities)
			e.viewCollisions.clear();

		qtree.findCollisions();
		
		// Call update for all entities
		for (Entity e : entities)
		{
			e.update(delta);
			
			// Entity specific tasks               

		}

		if (updates % 100 == 0)
		{
			System.out.println("Entity count : " + entities.size());
		}

		updates++;
	}

	public Point getSize()
	{
		return size;
	}

	public void setMaxFood(int f)
	{
		maxFood = f;
	}

	public int getMaxFood()
	{
		return maxFood;
	}
	
	public void setMaxFoodSize(int f)
	{
		maxFoodSize = f;
	}
	
	public int getMaxFoodSize()
	{
		return maxFoodSize;
	}

	public int getHeight()
	{
		return size.y;
	}

	public int getWidth()
	{
		return size.x;
	}

	public long getUpdates()
	{
		return updates;
	}

	public void draw(Graphics g)
	{
		// Draw background and that

		g.setColor(Color.white);
		g.fillRect(0, 0, size.x, size.y);

		if (drawQuadTree)
			qtree.draw(g);

		if (drawColonyCircle)
		{
			g.setColor(Color.green);
			float hw = cr * 2;
			g.drawOval(cx - (hw / 2), cy - (hw / 2), hw, hw);
		}

		for (Entity e : entities)
		{
			e.draw(g);
		}
	}

	public void disableRotation()
	{
		drawRotationEnabled = false;
	}

	public void enableRotation()
	{
		drawRotationEnabled = true;
	}

	public boolean isRotationEnabled()
	{
		return drawRotationEnabled;
	}
	
	public int getTickNumber()
	{
		return tickCount;
	}
}
