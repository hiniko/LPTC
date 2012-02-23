package me.paulrose.lptc.simulator;

public class FoodFactory extends BaseFactory
{

	FoodFactory(World w)
	{
		super(w);
	}

	
	public Food createFood(String type, int size, int amount, int x, int y)
	{
		Food f = null;
		
		if (type.equalsIgnoreCase("Sugar"))
		{
			int value = 10;
			f = new Food("Sugar", size, amount, value, x, y, world);
		}
		
		return f;
	}
}
