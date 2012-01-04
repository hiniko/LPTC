package me.paulrose.lptc.simulator;

import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class Simulator{
	
	private JPanel canvas;
	public World world;
	private long seed;
	public static Random random;
	
	
	
	public Simulator(long seed)
	{
		// Init any settings needed for a general simulation
		
		// Create random generator
		random = new Random();
		if( seed == 0 ){
			seed = random.nextLong();
		}
		random.setSeed(seed);
		
		
		// Create new Simulation
		newSimulation(36);
	}
	
	
	public void newSimulation(int participants)
	{
		// Create a new world for the right amount of participants
		world = new World(participants);
		
	}
	
	public void draw(Graphics2D g2d)
	{
		world.draw(g2d);
	}
	
	
	

}
