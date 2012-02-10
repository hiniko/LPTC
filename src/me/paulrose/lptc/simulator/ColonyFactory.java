package me.paulrose.lptc.simulator;

public class ColonyFactory extends BaseFactory
{

	ColonyFactory(World w){ super(w); }

	public Colony create(
			final double x, final double y, final String name, 
			final int food, final String antName)
	{
		
		Colony c = new Colony((int)x,(int) y, name, "PaulsAnt", 100, world);
		world.addEntity(c);
		
		System.out.println("ColonyFactory: Created " + name + " Colony" );
		return c;
	}
}

