package me.paulrose.lptc.simulator;

public class ColonyFactory extends BaseFactory
{

	ColonyFactory(World w){ super(w); }

	public Colony create(String n, int x, int y, int f, String atype)
	{		
		Colony c = new Colony(n, x, y, atype, 100, world);
		world.addEntity(c);
		return c;
	}
}

