package me.paulrose.lptc.simulator;

public class PaulsAnt extends Ant{
	
	PaulsAnt(String name, int x, int y, Colony c, World w)
	{
		super(name, x, y,c, w);
		
		setRotation((int)(world.random.nextDouble() * 360));
	}
	
	public void update()
	{
		super.update();
		run();
	}
	
	public void randomDirection()
	{
		// HEad
		dest.x = world.random.nextDouble() * world.getWidth();
		dest.y = world.random.nextDouble() * world.getHeight();
	}
	
	
	public void walkForwards()
	{
		
	}
	
	public void run()
	{
		// This is my logic for my ant
		
	}


}