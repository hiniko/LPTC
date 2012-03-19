import me.paulrose.lptc.simulator.Ant;
import me.paulrose.lptc.simulator.Colony;
import me.paulrose.lptc.simulator.Food;
import me.paulrose.lptc.simulator.World;

public class PaulWarAntAnt extends Ant
{

	public PaulWarAntAnt(String name, float x, float y, Colony c, World w)
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
		if(energy < 2500 && !eating)
{

    if(nearFood())
        goTo(nearestFood);

    if(atFood())
    {
        stop();
        eatFood(nearestFood);
    }
}
else if (eating)
{
    if(energy != MAX_ENERGY && nearestFood != null)
        eatFood(nearestFood);
    else
    {
        stopEating();
        walk();
    }

}
else
{
    if(nearEnemyAnt())
        goTo(nearestEnemyAnt);
    
    if(atEnemyAnt())
        attack(nearestEnemyAnt);

}

	}
}
