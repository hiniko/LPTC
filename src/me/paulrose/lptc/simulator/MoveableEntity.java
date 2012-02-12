package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.vecmath.Vector2d;

public class MoveableEntity extends Entity
{
	
	protected double acel, decel, vel, maxVel, rotation, bounceRotation;
	protected Vector2d dest, direction;
	protected boolean atDestination, walkToActive, stopped;
	

	public MoveableEntity(String name, int x, int y, 
			World wo)		
	{
		super(name, x, y, wo);
		
		acel = 0.5;
		vel = 0;
		maxVel = 2;
		
		dest = new Vector2d(x,y);
		
		atDestination = false;
		walkToActive = false;
		stopped = false;
	}

	public void setRotation(double rot)
	{
		rotation = Math.toRadians(rot) - Math.toRadians(90);
	}
	
	
	public void update()
	{
		
		//bounceRotation = Math.toRadians( world.random.nextInt(180 - 135 + 1) + 135 ); 
		bounceRotation = Math.toRadians(world.random.nextInt(180 + 1));
		//bounceRotation = Math.toRadians(135);
		if (world.random.nextDouble() > 0.5)
			bounceRotation = -bounceRotation;
	
		// Check to see if we are still within the world
		// A check for all four corners
		if( pos.x > world.getWidth())
		{
			pos.x = world.getWidth();
			rotation += bounceRotation;
		}
		else if (pos.x < 0)
		{
			pos.x = 0;
			rotation += bounceRotation;
		}
			
		if( pos.y > world.getHeight())
		{
			pos.y = world.getHeight();
			rotation += bounceRotation;
		}
		else if (pos.y < 0)
		{
			pos.y = 0;
			rotation += bounceRotation;
		}
		
		rotation = rotation % (2 * Math.PI);
		
		//
		if(walkToActive)
			walkToPoint();
		else
			walkForwards();
	}
	
	public void walkForwards()
	{
		// Work out the direction we have to go in and the length
		double sn = Math.sin(rotation);
		double cs = Math.cos(rotation);
		// Create the direction vector
		direction = new Vector2d(cs,sn);
		
		
		// Normalise so we can move in that direction
		direction.normalize(); 
		
		// work out how much we should move this update
		vel += acel;
		if(vel >= maxVel)
			vel = maxVel;
		
		// Calculate the new position
		pos.x += direction.x * vel;
		pos.y += direction.y * vel;
		
		
	}
	
	public void walkToPoint()
	{	
		direction =	new Vector2d(dest.x-pos.x, dest.y-pos.y);
		rotation =  Math.atan2(direction.y, direction.x);
		direction.normalize(); 
		
		vel += acel;
		
		if(vel >= maxVel){
			vel = maxVel;
		}
		
		int framesLeft = (int) (direction.length() / vel);
		
		if(framesLeft == 0){
			pos.x = (int)dest.x;
			pos.y = (int)dest.y;
			atDestination = true;
			vel = 0;
			walkToActive = false;
			
		}else{
			// Calculate the new position
			pos.x += direction.x * vel;
			pos.y += direction.y * vel;
			atDestination = false;
		}
		
	}
	
	
	public void draw(Graphics2D g2d)
	{
		super.draw(g2d);
		
	}

}
