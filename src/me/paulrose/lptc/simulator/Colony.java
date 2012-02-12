package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.LinkedList;

import javax.vecmath.Vector2d;

public class Colony extends Entity
{
	
	private static final int BASE_SIZE = 20;
	private String name;
	private String antClass;
	private LinkedList<Ant> ants;
	private int diameter, maxAnts, antCost;
	private Color color;
	private long food;
	public BufferedImage antSprite;
	
	Colony(String n, int x, int y, String c, long f, World wo)
	{
		super(n, x, y, wo);
		
		name = n;
		antClass = c;
		food = f;
		
		// The size of the colony 
		diameter = (int) (BASE_SIZE);
		size = new Point(diameter, diameter);
		// The color of the colony and its ants
		int r = (int)(world.random.nextDouble() * 255);
		int g = (int)(world.random.nextDouble() * 255);
		int b = (int)(world.random.nextDouble() * 255);
		color = new Color(r,g,b);
		
		// Flag a new buffered image to be created
		drawSprite();
		drawAntSprite();
		redrawSprite = false;
		
		
		// List of all ants
		ants = new LinkedList<Ant>();
		
		maxAnts = 101;
		antCost = 100;
		food = maxAnts * antCost; //world.random.nextInt(101);

	}
	
	public void update()
	{
		super.update();
		// update the sprite if needed
		if(redrawSprite)
		{
			drawSprite();
			drawAntSprite();
		}
			
		
		
		if(ants.size() <= maxAnts && food >= antCost)
		{
			ants.add(world.antFactory.createAnt(antClass, (int)pos.x, (int)pos.y, this));
			food -= antCost;
		}
		
		food++;
		

	}
	
	public void drawSprite()
	{
		
		int x = (int)size.x;
		int y = (int)size.y;

		sprite = new BufferedImage(x,y , BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = (Graphics2D) sprite.getGraphics();		
		// Draw a circle for now
		g2d.setColor(color);
		g2d.fillOval( 0, 0, x, y);
		//g2d.setColor(Color.MAGENTA);
		// Draw the name of the player
		//g2d.drawString(name, (int) pos.x + (size.x /2), (int) pos.y + (size.y / 2) );
		
		redrawSprite = false;
	}
	
	public void drawAntSprite()
	{
		int x = Ant.ANT_SIZE;
		int y = Ant.ANT_SIZE;
		
		//sprite = world.createImage(x, y);
		antSprite = new BufferedImage(x,y , BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = (Graphics2D) antSprite.getGraphics();
		
		g2d.setColor(color);
		g2d.fillOval(0, 0, x-1 , y-1);
					
		g2d.setColor(Color.RED);
		g2d.drawLine(x/2, 0, x/2, y);
		g2d.drawLine(0, y-1, x-1, y-1);
		
	}
	
	public void draw(Graphics2D g2d)
	{	
		int x = (int)pos.x;
		int y = (int)pos.y;
		int w = (int)size.x;
		int h = (int)size.y;
		
		g2d.drawImage(sprite, (int)(x - (w /2)), 
				(int)(y - (h /2)), null);
	}

	
	public Color getColor()
	{
		return color;
	}
	
	
}
