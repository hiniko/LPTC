import me.paulrose.lptc.simulator.Ant;
import me.paulrose.lptc.simulator.Colony;
import me.paulrose.lptc.simulator.Food;
import me.paulrose.lptc.simulator.World;

public class PaulFoodAntUnsafeAnt extends Ant
{

	public PaulFoodAntUnsafeAnt(String name, float x, float y, Colony c, World w)
	{
		super(name, x, y, c, w);
	}

	public void update(int delta)
	{
		super.update(delta);
	}

	@Override
	public void run()
	{
		if(firstRun)
{
    colony.setMinimumEnergy(4000);
}

if(nearFood())
    goTo(nearestFood);


if(atFood())
    harvest(nearestFood);

if(isCarryingFood())
    goTo(colony);

if(atColony())
    dropOffFood();

	}
}
