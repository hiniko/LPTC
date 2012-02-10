package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.vecmath.Vector2d;

public class MoveableEntity extends Entity
{
	
	protected double acel, decel, vel, maxVel, rotation;
	protected Vector2d dest, direction;
	protected boolean atDestination;
	

	public MoveableEntity(String name, int x, int y, World w)		
	{
		super(name, x, y, w);
		
		acel = 0.5;
		vel = 0;
		maxVel = 2;
		
		dest = new Vector2d(0,0);
		
		atDestination = false;
		
		// HEad
		dest.x = x;
		dest.y = y;
	}

	public void setRotation(double rot)
	{
		//rotation = rot - (45 / (180/Math.PI));
		rotation = Math.toRadians(rot) - Math.toRadians(90);
	}
	
	
	public void update()
	{
		// Work out the direction we have to go in and the length
		//direction =	new Vector2d(dest.x-pos.x, dest.y-pos.y);
		double sn = Math.sin(rotation);
		double cs = Math.cos(rotation);
		
		
		direction = new Vector2d(cs,sn);
		
		double distance = direction.length();

		
	    // rotation =  Math.atan2(direction.y, direction.x) - (90 / (180/Math.PI));

		// Normalise so we can move in that direction
		direction.normalize(); 
		
		// work out how much we should move this update
		vel += acel;
		
		if(vel >= maxVel){
			vel = maxVel;
		}
		
		int framesLeft = (int) (distance / vel);
		
/*		if(framesLeft == 0){
			pos.x = dest.x;
			pos.y = dest.y;
			
			atDestination = true;
			vel = 0;
			
		}else{
		*/
			// Calculate the new position
			pos.x += direction.x * vel;
			pos.y += direction.y * vel;
			atDestination = false;
		//}
	}
	
	
	public void draw(Graphics2D g2d)
	{
		super.draw(g2d);
		// Draw the target
		g2d.setColor(Color.BLACK);
		g2d.fillOval((int)dest.x-(size.width /4), (int)dest.y-(size.width /4), size.width/4, size.height/4);
		// Draw a line to the target
		g2d.drawLine((int)pos.x, (int)pos.y, (int)dest.x, (int)dest.y);
		
	}

}
