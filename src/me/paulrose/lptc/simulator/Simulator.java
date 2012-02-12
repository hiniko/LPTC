package me.paulrose.lptc.simulator;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

public class Simulator implements Runnable{
	
	// Update per second as we do not handle drawing here, 
	// Which I hope works
	public final static int UPS = 60; 
	public final static int MAX_UPDATES = 10;
	public final static long  STEP_TIME = 1000 / UPS;
	
	private long previousUpdateTime,
				 frameCount, accumulator, elapsedTime;
	
	private World world;
	private boolean running, paused;
	private GraphicsConfiguration gc;
	
	public static void main(String[] args)
	{
		
		Simulator instance = new Simulator();
		instance.createWorld(123456, 50);
		
		
		Thread t  = new Thread(instance);
		t.start();
	}
	
	public Simulator()
	{
		running = false;
		paused = false;	
		
	} 
 	
	public void createWorld(long s, int p)
	{
		world = new World(s,p, this);
		world.initWorld();
	}
	

	public void draw(Graphics2D g2d)
	{
		world.draw(g2d);
	}
	
	public Point getWorldSize()
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
				if(paused){
					previousUpdateTime  = System.currentTimeMillis();
				}
				while(!paused)
				{ 
					int updates = 0;
					long currentTime = System.currentTimeMillis();
					elapsedTime =  currentTime - previousUpdateTime;
					previousUpdateTime = currentTime;
					
					accumulator += elapsedTime;
					
					// Update as much as we can in this loop
					while (accumulator >= STEP_TIME && updates < MAX_UPDATES)
					{
						//System.out.println("Updating Simulation");
						world.update();
						accumulator -= STEP_TIME;
						frameCount++;
						updates++;
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
