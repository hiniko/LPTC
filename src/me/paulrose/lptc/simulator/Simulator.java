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
	
	public Simulator()
	{
		running = false;
		paused = false;	
	} 
 	
	
	public void createWorld(long s, int p)
	{
		world = new World(s,p);
		world.initWorld();
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
