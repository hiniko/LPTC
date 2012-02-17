package me.paulrose.lptc.simulator;

import java.awt.event.MouseEvent;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;

import com.sun.xml.internal.ws.api.server.Container;

public class AntArena extends BasicGame{

	private float ZOOM_STEP = 0.05f;
	private float ZOOM_MIN = 0.2f;
	private float ZOOM_MAX	= 2.5f;
		
	private World world;
	int viewer_x, viewer_y, shutter;
	float zoom;
	
	public static GameContainer c;
	
	
	public AntArena() 
	{
		super("The Ant Arena - There can only be One! (Colony)");
	}
	
	
	@Override
	public void init(GameContainer container) throws SlickException {
		
		// Container settings
		container.setMinimumLogicUpdateInterval(16);
		container.setClearEachFrame(true);
		container.setVSync(true);
		container.setShowFPS(true);
		container.setAlwaysRender(true);
		container.setUpdateOnlyWhenVisible(true);
		c = container;
		// Value init
		zoom = 1f;

		// Create the world
		world = new World(123456, 50);
		world.initWorld();

	}
		

	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		if(!container.isPaused())
		{
			world.update(delta);
		}
	}
	

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		
	
		int w = container.getWidth();
		int h = container.getHeight();

		
		// Fill the world Colours
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, w, h);
		
		// Push transform matrix
		g.pushTransform();
		// Work out the translation to center the world on screen and for zooming
		float center_x = (float) ((w/2) - (world.getWidth()  * zoom) /2);
		float center_y = (float) ((h/2) - (world.getHeight() * zoom) /2);
		
		g.translate(center_x, center_y);
		g.scale(zoom, zoom);
		g.translate(viewer_x, viewer_y);
		
		g.pushTransform();
		
		g.setColor(Color.black);
		g.setLineWidth(2);
		
		float x = -(viewer_x + (center_x / zoom));
		float y = -(viewer_y + (center_y / zoom));
		float w2 = w /zoom ;
		float h2 = h /zoom;
		
		world.drawClip.setBounds( x, y, w2, h2);

		g.setWorldClip(x, y, w2, h2);
		
		if (zoom < 0.8)
			world.disableRotation();
		else
			world.enableRotation();
		
		world.draw(g);
		
		Ant.drawCount = 0;
		world.draw(g);	
		
		
		
		g.popTransform();
		g.popTransform();
		
		
		
		
		g.setColor(Color.magenta);
		g.drawString("Ant Simulator", 25, 25);
		
		
		g.setColor(Color.magenta);
		g.drawString("Zoom: " + zoom, 10, 50);
		
	}


	@Override
	public void mouseWheelMoved(int change)
	{
		// Check the direction of the wheel change
		if(change > 0)
			zoom -= ZOOM_STEP;
		else
			zoom += ZOOM_STEP;
		// Check we are within zoom values
		if ( zoom < ZOOM_MIN  )
			zoom = ZOOM_MIN;
		else if (zoom > ZOOM_MAX)
			zoom = ZOOM_MAX;
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy){
			
			int deltaX = newx - oldx;
			int deltaY = newy - oldy;
						
			viewer_x += deltaX / this.zoom;
			viewer_y += deltaY / this.zoom;
		
	}
	
	

}
