package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.vecmath.Vector2d;

public class Colony extends Entity
{
	private String name;
	private String ant_class;
	
	private LinkedList<Ant> ants;
	private Vector2d vec;
	private int diameter, r, g, b, x, y;
	private Dimension size;
	
	private long food;
	
	Colony(final int x, final int y, final String n, final String c, final long f)
	{
		super(n, x,y );
		
		this.name = n;
		this.ant_class = c;
		this.food = f;
		
		// The size of the colony 
		this.diameter = (int) this.food / 4;
		this.vec = new Vector2d(x,y);
		this.x = x;
		this.y = y;
		this.size = new Dimension(this.diameter, this.diameter);
		
		r = (int)Math.random() * 255;
		g = (int)Math.random() * 255;
		b = (int)Math.random() * 255;
	}
	
	public void update()
	{
		
	}
	
	public void draw(Graphics2D g2d)
	{
		// Draw a circle for now
		g2d.setColor( new Color( r, g, b) );
		g2d.fillOval( x-(size.width /2), y-(size.width /2), size.width, size.height);
		g2d.setColor(Color.MAGENTA);
		
		g2d.drawString(this.name, (int) vec.x + (size.width /2), (int) vec.y + (size.height / 2) );
		
	}
	
	public void createAnt()
	{
		
	}
	
}
