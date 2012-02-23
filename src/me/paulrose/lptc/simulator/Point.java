package me.paulrose.lptc.simulator;

public class Point
{
	public double x,y;
	
	public Point(int x, int y)
	{
		this.x = (double)x; this.y = (double)y;
	}

	public Point(double x, double y) 
	{
		this.x = x; this.y = y;
	}
	
	public int intX()
	{
		return (int)x;
	}

	public int intY()
	{
		return (int)y;
	}
}
	
