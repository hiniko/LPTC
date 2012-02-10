package me.paulrose.lptc.simulator;

public class AntFactory extends BaseFactory
{	
	public AntFactory(World w) { super(w); }
	
	public Ant createAnt(String type, double x, double y, Colony c)
	{
		Ant a = null;
		
		if(type.equalsIgnoreCase("PaulsAnt")){
			a = new PaulsAnt(type, (int)x, (int)y, c, world);
		}
	
		if (a != null)
		{
			world.addEntity(a);
			return a;
		}
		else
		{
			return null;
		}
			
	}
	
}
