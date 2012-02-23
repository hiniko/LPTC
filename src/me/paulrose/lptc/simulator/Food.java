package me.paulrose.lptc.simulator;


public class Food extends Entity
{
	
	private String type;
	private int size, amount, value;

	public Food(String n, int x, int y, World wo)
	{
		super(n, x, y, wo);
		
		type = "Sweet Grass";
		size = 50;
		x = world.random.nextInt(world.getWidth());
		y = world.random.nextInt(world.getHeight());
		
	}

	public Food(String n, int size, int amount, int value, int x, int y, World wo)
	{
		super(n, x, y, wo);
		
		this.size = size;
		this.amount = size;
		this.value = value;
		
	}

}
