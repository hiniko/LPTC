package me.paulrose.lptc.simulator;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class Simulator implements Runnable{
	
	// Update per second as we do not handle drawing here, 
	// Which I hope works
	public final static int UPS = 30; 
	public final static int MAX_UPDATES = 10;
	public final static long  STEP_TIME = 1000 / UPS;
	private long seed, previousUpdateTime,
				 frameCount, accumulator, elapsedTime;
	
	private World world;
	private boolean running, paused;
	
	public Random random;
	private static Simulator instance;
	
	
	
	private Simulator()
	{
		random = new Random();
		
		seed = random.nextLong();
		random.setSeed(seed); 
		
		running = false;
		paused = false;
		
	}
	
	public static Simulator instance()
	{
		if ( instance == null)
		{
			instance = new Simulator();
		}
		
		return instance;
	}
	
	public void createWorld(int players)
	{
		world = new World(players);
		world.initWorld();
	}
	
	public void setSeed(long seed)
	{
		this.random.setSeed(seed);
		this.seed = seed;
		
		if(world != null)
		{
			world = new World(world.getPlayers());
		}
	}
	
	public static void addEntity(Entity e)
	{
		
	}

	public void draw(Graphics2D g2d)
	{
		world.draw(g2d);
	}
	
	public Dimension getWorldSize()
	{
		return world.getSize();
	}


	@Override
	public void run()
	{
		if( world != null )
		{
			running = true;
			previousUpdateTime = System.currentTimeMillis();
			while (running)
			{
				
				while(!paused)
				{ 
					
					long currentTime = System.currentTimeMillis();
					elapsedTime =  currentTime - previousUpdateTime;
					previousUpdateTime = currentTime;
					
					accumulator += elapsedTime;
					
					// Update as much as we can in this loop
					while (accumulator >= STEP_TIME)
					{
						//System.out.println("Updating Simulation");
						world.update();
						accumulator -= STEP_TIME;
						frameCount++;
					}
					
					
					try
					{
						Thread.sleep(STEP_TIME);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			// Finished with this run, Clean up
			
			System.out.println("\n World finished: Seed was " + seed);
			
		}
	}
	
	public void pause()
	{
		paused = true;
	}

	public void unpause()
	{
		paused = false;
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public void stop()
	{
		running = false;
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	public long getElapsedTime()
	{
		return elapsedTime;
	}
	
	public long getFrames()
	{
		return frameCount;
	}
}
