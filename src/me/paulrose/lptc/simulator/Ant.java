package me.paulrose.lptc.simulator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public abstract class Ant extends MoveableEntity
{
	
	protected static boolean firstRun = true;
	

	public static final int BODY_SIZE = 10;
	public static final int VIEW_RADIUS = 25;  
	
	static final int CARRY_CAPACITY = 20;
	static final int EAT_SPEED = 50;
	
	public static final int WALK_COST = 1;
	public static final int CARRY_COST = 3;
	public static final int ATTACK_COST = 15;
	
	public static final int ATTACK_DAMAGE = 25;
	
	public static final int MAX_ENERGY = 5000;
	
	public static final Color ATTACKED_COLOR = new Color(182,45,23);
	public static final Color ENERGY_TOTAL_COLOR = new Color(255,0,0);
	public static final Color ENERGY_LEFT_COLOR = new Color(0,255,0);
	
	private int processOnTick;
	
	protected Colony colony;
	protected int energy, maxCarryWeight;
	protected boolean drawViewRadius, drawCollisions, drawEnergyBar;
	
	protected boolean foundFood, atFood, atColony, atEnemyAnt, atFriendlyAnt, attacked;
	protected boolean fullEnergy, halfEnergy, eating;
	protected Ant nearestAnt, nearestFriendlyAnt, nearestEnemyAnt,  attackerAnt;
	protected Food nearestFood;
	protected Food biggestFood;
	protected float nearestFoodDistance;
	protected float biggestFoodAmount;

	
	private Rectangle energyTotal, energyLeft;

	
	public Ant(String n, float x, float y, Colony c, World wo)
	{
		super(n, x, y, VIEW_RADIUS * 2, VIEW_RADIUS * 2, wo);

		// Starting values for the ant
		colony = c;
		//energy = 1000;
		energy = MAX_ENERGY;
		maxCarryWeight = 100;
		sizeRadius = BODY_SIZE / 2;

		drawViewRadius = true;
		drawCollisions = false;
		drawEnergyBar = true;
		
		processOnTick = world.random.nextInt(4)+1;

		setRotation(world.random.nextInt(360 + 1));
	}

	public void run()
	{
		// User code will go here in the end
	}
	
	
	public void update(int delta)
	{
		super.update(delta);
		
		if(world.getTickNumber() == processOnTick)
		{
	
			collisions.clear();
			atColony = false;
			atFood = false;
			
			// Check if we have full energy before this loops deductions
			fullEnergy = (energy == MAX_ENERGY) ? true : false;
			halfEnergy = (energy >= MAX_ENERGY / 2) ? true : false;
	
			// Find the nearest food to us
			nearestFoodDistance = Ant.VIEW_RADIUS;
			float nearestAntDistance = Ant.VIEW_RADIUS;
	
			for (Entity e : viewCollisions)
			{
				// First see what is around us
				// is there food on the floor
				if (e instanceof Food && !e.isBeingCarried())
				{
					Food f = (Food) e;
					// Find nearest food
					float distance = distanceTo(e);
					if (distance < nearestFoodDistance)
					{
						nearestFood = f;
						nearestFoodDistance = distance;
					}
	
					// Find biggest food
					if (f.getTotalFood() > biggestFoodAmount)
					{
						biggestFood = f;
						biggestFoodAmount = f.getTotalFood();
					}
				}
				// is there another ant about?
				if(e instanceof Ant)
				{
					Ant a = (Ant)e;
					
					float distance = distanceTo(e);
					if(distance < nearestAntDistance)
					{
						nearestAnt = a;
						nearestAntDistance  = distance;
						
						if(a.colony == colony)
							nearestFriendlyAnt = a;
						else
							nearestEnemyAnt = a;
					}
					
				}
				
				// Second make finer detections to see if we are touching
				// anything
	
				float a = sizeRadius + e.sizeRadius;
				float dx = bounds.getCenterX() - e.bounds.getCenterX();
				float dy = bounds.getCenterY() - e.bounds.getCenterY();
				boolean isCollided = (a * a) > (dx * dx + dy * dy);
	
				if (isCollided)
					collisions.add(e);
	
			}
	
			// Check the collisions list for flags we can activate
			for (Entity e : collisions)
			{
				if (e instanceof Food && !e.isBeingCarried())
					atFood = true;
	
				if (e instanceof Colony && e == colony)
					atColony = true;
				if (e instanceof Ant)
				{
					if(((Ant) e).colony == colony)
						atFriendlyAnt = true;
					else
						atEnemyAnt = true;
				}
			}
	
			// Check to see if any objets were near us
			// reset refereces if there were none
			if (nearestFoodDistance == Ant.VIEW_RADIUS)
			{
				nearestFood = null;
				atFood = false;
			}
			
			if(nearestAntDistance == Ant.VIEW_RADIUS)
			{
				nearestAnt = null;
				nearestFriendlyAnt = null;
				nearestEnemyAnt = null;
				atFriendlyAnt = false;
				atEnemyAnt = false;
			}

			// Run user code here
			run();
			
			if(firstRun)
				firstRun = false;
		}
		
		// Update all other important things
		// Deduct energy for various continuous states
		if(!stopped)
			energy -= WALK_COST;
		
		if(isCarrying)
			energy -= CARRY_COST;
		
		
		//Check energy reserves and kill this ant if need be
		if(energy < 0)
		{
			die();
		}
	}
	
	
	private void die()
	{
		//Remove the and from the colony, the colony will remove it from the world
		colony.removeAnt(this);
		// drop whatever we are holding
		drop();
	}

	public void draw(Graphics g)
	{

		super.draw(g);

		float x = bounds.getCenterX();
		float y = bounds.getCenterY();
		float w = bounds.getWidth();
		float h = bounds.getHeight();
		float hw = w / 2;
		float hh = h / 2;

		Image sprite = colony.getAntSprite();

		if (world.isRotationEnabled() && g.getWorldClip().contains(x, y))
		{
			sprite.setRotation((float) Math.toDegrees(rotation) - 90);
			g.drawImage(sprite, x - BODY_SIZE / 2, y - BODY_SIZE / 2);

			// Viewing Circle of the Ant
			if (drawViewRadius)
			{
				g.setLineWidth(1.0f);
				g.setColor(Color.magenta);
				g.drawOval(bounds.getX(), bounds.getY(), VIEW_RADIUS * 2,
						VIEW_RADIUS * 2);
			}

			if (drawCollisions)
			{
				g.setColor(Color.blue);
				for (Entity e : viewCollisions)
				{
					g.drawLine(bounds.getCenterX(), bounds.getCenterY(),
							e.bounds.getCenterX(), e.bounds.getCenterY());
				}

			}

			// Draw what the ant is carrying
			if (isCarrying)
			{
				g.setColor(carrying.getColour());
				g.drawOval(bounds.getCenterX() - 3, bounds.getCenterY() - 3, 7,
						7);
			}
			
			// Draw the ants health bar
			if(drawEnergyBar)
			{
				// draw total energy bar
				g.setColor(ENERGY_TOTAL_COLOR);
				g.fillRect(bounds.getCenterX()-VIEW_RADIUS/2, bounds.getCenterY()+10, 
						VIEW_RADIUS, 2);
				
				
				float energyBarPercent = (float)VIEW_RADIUS / MAX_ENERGY * energy;
				
				g.setColor(ENERGY_LEFT_COLOR);
				g.fillRect(bounds.getCenterX()-VIEW_RADIUS/2, bounds.getCenterY()+10, 
						 energyBarPercent, 2);

				
			}
			
			
			if(attacked)
			{
				g.setColor(ATTACKED_COLOR);
				g.drawString("!", bounds.getCenterX()-2, bounds.getCenterY()-10);
				
			}

		} else if (world.drawClip.contains(x, y))
		{
			g.drawImage(sprite, x - BODY_SIZE / 2, y - BODY_SIZE / 2);
		}

		// Draw the viewing circle of the ant

	}

	public void randomDirection()
	{
		dest.x = world.random.nextDouble() * world.getWidth();
		dest.y = world.random.nextDouble() * world.getHeight();
	}

	public void harvest(Food f)
	{
		if (!isCarrying && !f.isBeingCarried())
		{
			carrying = f.harvest(CARRY_CAPACITY);
			isCarrying = true;
			carrying.setBeingCarried(true);
		}

	}

	public void print(String msg)
	{
			System.out.println(msg);
	}

	public boolean isCarrying()
	{
		return (isCarrying) ? true : false;
	}

	public boolean isNotCarrying()
	{
		return (!isCarrying) ? true : false;
	}

	public boolean isCarryingFood()
	{
		if (isCarrying && carrying instanceof Food)
			return true;
		else
			return false;
	}

	public boolean isCarryingAnt()
	{
		return (isCarrying && carrying instanceof Ant) ? true : false;

	}

	public boolean nearFood()
	{
		return (nearestFood != null) ? true : false;
	}
	
	public boolean nearAnt()
	{
		return (nearestAnt != null) ? true : false;
	}
	
	public boolean nearFriendlyAnt()
	{
		return (nearestFriendlyAnt != null) ? true : false;
	}
	
	public boolean nearEnemyAnt()
	{
		return (nearestEnemyAnt != null) ? true : false;
	}
	
	public boolean atFood()
	{
		return (atFood) ? true : false;
	}
	
	public boolean atFriendlyAnt()
	{
		return (atFriendlyAnt) ? true : false;
	}
	
	public boolean atEnemyAnt()
	{
		return (atEnemyAnt) ? true : false;
	}
	
	
	public boolean atColony()
	{
		return (atColony) ? true : false;
	}
	
	public void drop()
	{
		if(isCarrying)
		{
			carrying.dropped();
		}
	}

	public void dropOffFood()
	{
		if(isCarrying)
		{
			colony.addFood((Food) carrying); 	
			carrying = null;
			walkToActive = false;
			isCarrying = false;
		}
	}

	public void attack(Ant a)
	{
		if(a != null)
		{
			a.takeDamage();
			energy -= ATTACK_COST;
		}
	}
	
	public void takeDamage()
	{
		attacked = true;
		energy -= ATTACK_DAMAGE;
	}
	
	public boolean beingAttacked()
	{
		return (attacked) ? true : false;
	}	
	
	public void resetAttack()
	{
		attacked = false;
	}
	
	public boolean isEating()
	{
		return (eating) ? true : false;
	}
	
	public void stopEating()
	{
		eating = false;
	}
	
	public void stop()
	{
		stopped = true;
	}
	
	public void walk()
	{
		stopped = false;
	}
	
	public void eatFood(Food f)
	{
		eating = true;
		Food piece = f.harvest(EAT_SPEED);
		energy += piece.getTotalFood();
		piece.delete();
		carrying = null;
		isCarrying = false;
	}
}
