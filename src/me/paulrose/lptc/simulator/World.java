package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class World
{
	
	private static final int MAX_WORLD_SIZE = 5000;
	private static final int MIN_WORLD_SIZE = MAX_WORLD_SIZE / 10;
	private static final double LOG_MODIFIER = 2.06370277381252;

	public Dimension size;
	private LinkedList<Entity> entities;
	private int cx, cy, cr, players;
	
	World(int p)
	{

		if (p <= 1)
			throw new IllegalArgumentException("Need more than one player!!!");
		
		players = p;
		// Create lists
		entities = new LinkedList<Entity>(); 
		
		// Work out size of the world
		
		int hw =   (int) (MAX_WORLD_SIZE * (Math.log10(players) /LOG_MODIFIER) + MIN_WORLD_SIZE) ;
		
		System.out.println("World Size is " + hw);
		
		size = new Dimension(hw,hw);
		// Work out Placement of the colonies
		int placement_radius = hw  / 2 - (hw / 10);
		this.cr = placement_radius;
	
		
		cx = (hw / 2);
		cy = (hw / 2);
		
		// Test Ant
		//Ant humble_ant = new Ant(null, null);
		
		
	}
	
	public void initWorld()
	{
		// Create the colonies 
		int placement_radius = size.width  / 2 - (size.width / 10);
		cr = placement_radius;
		int placement_angle = 360 / players;
		for(int i=0; i<players; i++)
		{
			String name = "Player " + (i+1) ;
			int a = (placement_angle * i);
			double x =  this.cx + placement_radius * Math.cos(Math.toRadians(a));
			double y =  this.cy + placement_radius * Math.sin(Math.toRadians(a));
			
			entities.add( new Colony((int)x,(int) y, name, "Ant", 100) );
		}
	}
	
	public int getPlayers()
	{
		return this.players;
	}
	
	public void update()
	{
		for (Entity e : entities) 
		{
			e.update();
		}
	}
	
	public Dimension getSize()
	{
		return this.size;
	}
	
	
	public void draw(Graphics2D g2d)
	{
		// Draw background and that
		g2d.setColor(Color.WHITE);
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, size.width, size.height);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, size.width, size.height);
		
		g2d.setColor(Color.GREEN);
		int hw = cr*2;
		
		g2d.drawOval(cx-(hw/2), cy-(hw/2), hw, hw);
		
		

		for(Entity e : entities)
		{
			e.draw(g2d);
		}
	}

}
