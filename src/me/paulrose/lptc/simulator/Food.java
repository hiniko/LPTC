package me.paulrose.lptc.simulator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Food extends Entity
{
	
	private String type;
	private int amount, density;
	private float carrySize;
	private boolean drawType;
	private Color droppedColor;
	

	public Food(String n, int density, int amount, int x, int y, 
			Color colour, Color droppedColor, World wo)
	{	
		super(n, x, y, 0, 0, wo);
		
		this.type = n;
		this.amount = amount;
		this.density = density;
		this.colour = colour;
		this.droppedColor = droppedColor;
		
		this.carrySize = 3;
		
		float size = amount / (density /2);
		
		// Set minimum size
		if (size < 5)
			size = 5;
		
		bounds.setWidth(size);
		bounds.setHeight(size);
		
		// Check to see if we are new out of bounds with the world
		if(bounds.getMaxX() > world.getWidth())
			bounds.setX(bounds.getX() - bounds.getWidth());
		
		if(bounds.getMaxY() > world.getHeight())
			bounds.setY(bounds.getY() - bounds.getHeight());
	}
	
	public Food harvest(int a)
	{
		
		if(a > amount)
			a = amount;

		float shrinkageX = (bounds.getWidth() / a)  ;
		float shrinkageY = (bounds.getHeight() / a) ;
		
		bounds.setWidth(bounds.getWidth() - shrinkageX);
		bounds.setHeight(bounds.getHeight() - shrinkageY);

		update();

		if(amount > a)
		{
			changeAmount(-a);
			return world.foodFactory.harvest(this, a);
		}
		else
			return this;
	}
	
	public void update()
	{
		if(amount <= 0)
		{
			delete();
		}
	}
	
	public String getType()
	{
		return type;
	}


	public int getAmount()
	{
		return amount;
	}

	public void changeAmount(int change)
	{
		amount += change;
	}

	public int getDensity()
	{
		return density;
	}

	
	public int getTotalFood()
	{
		return amount * density;
	}
	public void draw(Graphics g)
	{
		
		// Draw the food!
		if(!dropped)
			g.setColor(colour);
		else
			g.setColor(droppedColor);
		
		if(!isBeingCarried())
		{
			g.fillOval(bounds.getX(), bounds.getY(), bounds.getWidth(), 
					bounds.getHeight());
			
			
			if(drawType)
			{
				g.setColor(Color.black);
				g.drawString(type, bounds.getCenterX() - ((9 * type.length()) /2 ),
						bounds.getY() + bounds.getHeight());
			}
			
		}else{
			
			g.fillOval(bounds.getCenterX() - carrySize/2, bounds.getCenterY() - carrySize/2 , carrySize, 
					carrySize);
		}
		
		
		// Draw entity specific settings
		super.draw(g);
		
	}
	
	public Color getDroppedColor()
	{
		return droppedColor;
	}

}
