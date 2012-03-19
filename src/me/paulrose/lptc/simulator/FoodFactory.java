package me.paulrose.lptc.simulator;

import org.newdawn.slick.Color;

public class FoodFactory extends BaseFactory
{
	
	// Food Colors
	Color sugar = new Color(141,210,247);
	Color droppedSugar = new Color(127,161,247);
	
	FoodFactory(World w)
	{
		super(w);
	}

	
	public Food create(String type, int amount, int x, int y)
	{
		Food f = null;
		
		if (type.equalsIgnoreCase("Sugar"))
		{
			int density = 10;
			f = new Food("Sugar", density, amount, x, y, sugar, droppedSugar, world);
		
		}
		
		if (f != null)
			world.addEntity(f);
		
		return f;
	}
	
	public Food harvest(Food o, int amount)
	{

			Food f = new Food(o.getType(), o.getDensity(), amount, (int) o.bounds.getCenterX(),
					(int)o.bounds.getCenterY(), o.getColour(), o.getDroppedColor(), world);
			
			world.addEntity(f);
			
			return f;
		
	}
}
