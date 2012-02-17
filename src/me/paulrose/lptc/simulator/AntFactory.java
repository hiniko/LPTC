package me.paulrose.lptc.simulator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AntFactory extends BaseFactory
{	
	
	public AntFactory(World w) 
	{
		super(w);
	
	}
	
	public Ant createAnt(String type, double x, double y, Colony c)
	{
		Ant a = null;
		
		if(type.equalsIgnoreCase("PaulsAnt")){
			a = new PaulsAnt(type, (int)x, (int)y, c, world);
		}
	
		if (a != null)
		{
			world.addEntity(a);
			return a;
		}
		else
		{
			return null;
		}
			
	}
	
	
	public Image createAntSprite(Color c, boolean d)
	{
		
		try
		{
				
			Graphics g = AntArena.c.getGraphics();
			
			g.clear();
			g.setColor(c);
			g.fillOval(0, 0, Ant.SIZE, Ant.SIZE);
			
			if (d)
			{
				g.setColor(Color.red);
				g.drawLine(Ant.SIZE/2-1, 0, Ant.SIZE/2-1, Ant.SIZE-1);
				g.drawLine(Ant.SIZE/2, 0, Ant.SIZE/2, Ant.SIZE);
				g.drawLine(0, Ant.SIZE-1, Ant.SIZE, Ant.SIZE-1);
			}
			
			
			Image s = new Image(Ant.SIZE, Ant.SIZE);
			
			g.copyArea(s, 0, 0);
			
			g.flush();
		
		return s;
			
		}catch(SlickException e)
		{
			System.out.println("Couldn't create ant images. Exiting");
			System.exit(-1);
		}
		
		return null;
		
	}
	
}
