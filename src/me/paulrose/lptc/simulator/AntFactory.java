package me.paulrose.lptc.simulator;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AntFactory extends BaseFactory
{	
	
	private HashMap<String, Class> registeredClasses;
	
	public AntFactory(World w) 
	{
		super(w);
		registeredClasses = new HashMap<String, Class>();
	
	}
	
	public boolean registerFromFolder(String f)
	{
		File folder = new File(f);
		System.out.println("Checking for user ants in folder '" + f + "'");
		
		// Check the folder exists and is a folder
		if(!folder.exists())
		{
			System.out.println("Folder " + folder + " does not exist");
			return false;
		}
		
		if(!folder.isDirectory())
		{
			System.out.println("Folder " + folder + " is not a folder");
			return false;
		}
		
			// Create a new classloader which will load in the files we need
		try
		{
			URLClassLoader classloader = 
					URLClassLoader.newInstance(new URL[] {folder.toURI().toURL()} );
		
			
			String[] filenames = folder.list(new ClassFilenameFilter());
			
			for(int i=0; i<filenames.length; i++)
			{
				try
				{
					String className = filenames[i].split(".class")[0];
					Class<?> c = Class.forName(className, true, classloader);
					
					registeredClasses.put(className, c);
					
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("Could not find the class/file " + filenames[i]);
				}
			}
			
			// Print out all the registered classes
			System.out.println("AntFactory Registerd Classes contains::");
			for(String s : registeredClasses.keySet())
			{
				System.out.println(s);
			}
		
		} catch (MalformedURLException e1)
		{
			e1.printStackTrace();
			return false;
		}
		
		
		return false;
	}
	
	public Set<String> getRegisteredAntNames()
	{
		return registeredClasses.keySet();
	}
	
	
	public Ant createAnt(String type, float x, float y, Colony c)
	{
		Ant a = null;
		
		if(registeredClasses.containsKey(type))
		{

			Constructor<?> ctor;
			try
			{
				// Param list to get right constructor
				Class<?>[] pramList = new Class[5];
				pramList[0] = String.class;
				pramList[1] = Float.TYPE;
				pramList[2] = Float.TYPE;
				pramList[3] = Colony.class;
				pramList[4] = World.class;
				
				
				
				ctor = registeredClasses.get(type).getDeclaredConstructor(pramList);

				Object[] args = new Object[5];
				args[0] = type;
				args[1] = x;
				args[2] = y;
				args[3] = c;
				args[4] = world;
				
				a = (Ant) ctor.newInstance(args);
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		if (a != null)
			world.addEntity(a);
		
		return a;
			
	}
	
	
	public Image createAntSprite(Color c, boolean d)
	{
		
		try
		{
				
			Graphics g = AntArena.c.getGraphics();
			
			g.clear();
			g.setColor(c);
			g.fillOval(0, 0, Ant.BODY_SIZE, Ant.BODY_SIZE);
			
			if (d)
			{
				g.setColor(Color.red);
				g.drawLine(Ant.BODY_SIZE/2-1, 0, Ant.BODY_SIZE/2-1, Ant.BODY_SIZE-1);
				g.drawLine(Ant.BODY_SIZE/2, 0, Ant.BODY_SIZE/2, Ant.BODY_SIZE);
				g.drawLine(0, Ant.BODY_SIZE-1, Ant.BODY_SIZE, Ant.BODY_SIZE-1);
			}
			
			
			Image s = new Image(Ant.BODY_SIZE, Ant.BODY_SIZE);
			
			g.copyArea(s, 0, 0);
			
			g.flush();
		
		return s;
			
		}catch(SlickException e)
		{
			System.out.println("Couldn't create ant images. Exiting");
			System.exit(-1);
		}
		
		return null;
		
	}
	
	private class ClassFilenameFilter implements FilenameFilter
	{
		String acceptedFilename = ".class";

		@Override
		public boolean accept(File file, String name)
		{
			
			if( name.endsWith(acceptedFilename))
				return true;
			else
				return false;
		}
		
	}
	
}
