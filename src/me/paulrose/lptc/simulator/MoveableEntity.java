package me.paulrose.lptc.simulator;


import javax.vecmath.Vector2d;
import org.newdawn.slick.Graphics;

public class MoveableEntity extends Entity
{
	
	protected double acel, decel, vel, maxVel, rotation, bounceRotation;
	protected Vector2d dest, direction;
	protected boolean atDestination, walkToActive, stopped;
	

	public MoveableEntity(String name, float x, float y, int w, int h,
			World wo)		
	{
		super(name, x, y, w, h, wo);
		
		acel = 0.01;
		vel = 0;
		maxVel = 0.1;
		if(!World.deltaMovement)
			maxVel = 1;
		
		dest = new Vector2d(x,y);
		
		atDestination = false;
		walkToActive = false;
		stopped = false;
	}

	public void setRotation(double rot)
	{
		rotation = Math.toRadians(rot) - Math.toRadians(90);
	}
	
	
	public void update(int delta)
	{
		
		//bounceRotation = Math.toRadians( world.random.nextInt(180 - 135 + 1) + 135 ); 
		bounceRotation = Math.toRadians(world.random.nextInt(180 + 1));
		//bounceRotation = Math.toRadians(135);
		
		if (world.random.nextDouble() > 0.5)
			bounceRotation = -bounceRotation;
	
		// Check to see if we are still within the world
		// A check for all four corners
		if( bounds.getCenterX() > world.getWidth())
		{
			bounds.setCenterX(world.getWidth());
			rotation += bounceRotation;
		}
		else if (bounds.getCenterX() < 0)
		{
			bounds.setCenterX(0);
			rotation += bounceRotation;
		}
			
		if( bounds.getCenterY() > world.getHeight())
		{
			bounds.setCenterY(world.getHeight());
			rotation += bounceRotation;
		}
		else if (bounds.getCenterY() < 0)
		{
			bounds.setCenterY(0);
			rotation += bounceRotation;
		}
		
		rotation = rotation % (2 * Math.PI);
		
		if(!stopped)
		{
			if(walkToActive)
			{
				if(target.exists())
				{
					dest.x = target.bounds.getCenterX();
					dest.y = target.bounds.getCenterY();
				}else
				{
					walkToActive = false;
					target = null;
				}

				walkToTarget(delta);
			}
			else
			{
				walkForwards(delta);
			}
		}
		
		// If we are carrying anything make sure we move it with ourselves
		if (isCarrying)
		{
			carrying.bounds.setCenterX(bounds.getCenterX());
			carrying.bounds.setCenterY(bounds.getCenterY());
		}
		
	}
	
	public void walkForwards(int delta)
	{
		// Work out the direction we have to go in and the length
		double sn = Math.sin(rotation);
		double cs = Math.cos(rotation);
		// Create the direction vector
		direction = new Vector2d(cs,sn);
		
		
		// Normalise so we can move in that direction
		direction.normalize(); 
		
		// work out how much we should move this update
		vel += acel * delta;
		if(vel >= maxVel)
			vel = maxVel;
		
		// Calculate the new position
		if (World.deltaMovement)
		{
			bounds.setCenterX( (float) (bounds.getCenterX() + (direction.x * vel) * delta) );
			bounds.setCenterY( (float) (bounds.getCenterY() + (direction.y * vel) * delta) );			
		}
		else
		{
			bounds.setCenterX( (float) (bounds.getCenterX() + (direction.x * vel)) );
			bounds.setCenterY( (float) (bounds.getCenterY() + (direction.y * vel)) );
		}
		
	}
	
	public void stop()
	{
		stopped = true;
	}
	
	public void go()
	{
		stopped = false;
	}
	
	public void walkToTarget(int delta)
	{	
		if(target != null)
		{
			direction =	new Vector2d(dest.x-bounds.getCenterX(), dest.y-bounds.getCenterY());
			rotation =  Math.atan2(direction.y, direction.x);
			double distance = direction.length();
			direction.normalize(); 
			
			vel += acel * delta;
			
			if(vel >= maxVel){
				vel = maxVel;
			}
			
			float framesLeft = (float) (distance / vel);
			
			float a = sizeRadius + target.sizeRadius;
			float dx = bounds.getCenterX() - target.bounds.getCenterX();
			float dy = bounds.getCenterY() - target.bounds.getCenterY();
			boolean isCollided = (a * a) > (dx * dx + dy * dy);
	
			if(isCollided){
			
				atDestination = true;
				vel = 0;
				walkToActive = false;
				
			}else{
	 			// Calculate the new position
				if(World.deltaMovement){
					bounds.setCenterX( bounds.getCenterX() + (float) (direction.x * vel * delta ) );
					bounds.setCenterY( bounds.getCenterY() + (float) (direction.y * vel * delta ) );
				}
				else
				{
					bounds.setCenterX( bounds.getCenterX() + (float) (direction.x * vel ) );
					bounds.setCenterY( bounds.getCenterY() + (float) (direction.y * vel ) );
				}
				atDestination = false;	
			}
		}else
		{
			walkToActive = false;
			
		}
		
	}
	
	public void goTo(Entity e)
	{
		walkToActive = true;
		target = e;
	}
	
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}
	
	public void setMaxVelocity(float f)
	{
		maxVel = f;
	}

}
