package me.paulrose.lptc.simulator;

public class PaulsAnt extends Ant
{
	private Colony colony;

	PaulsAnt(String name, float x, float y, Colony c, World w)
	{
		super(name, x, y, c, w);
		colony = c;
	}

	public void update(int delta)
	{
		super.update(delta);
		run();
	}

	public void run()
	{
		// This is my logic for my ant
		if ( isNotCarrying() && nearFood() )
			if (nearestFood.isNotBeingCarried())
				goTo(nearestFood);

		if (atFood() && isNotCarrying())
			harvest(nearestFood);

		if (isCarryingFood())
			goTo(colony);

		if (isCarryingFood() && atColony())
		{
			dropOffFood();
		}

	}
}
