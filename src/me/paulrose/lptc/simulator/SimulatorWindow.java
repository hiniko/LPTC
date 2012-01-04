package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class SimulatorWindow extends JPanel implements 
	MouseMotionListener, MouseListener, MouseWheelListener{
	
	Simulator instance;
	int viewer_x, viewer_y, prev_x, prev_y;
	double zoom, min_zoom, max_zoom;
	boolean leftClick, rightClick;
	
	
	public SimulatorWindow(){
		setPreferredSize(new Dimension(720, 720));
		
		min_zoom = 0.01;
		zoom = 1.0;
		max_zoom = 1.5;
		
		leftClick = false;
		rightClick = false;
		
		this.viewer_x = 0;
		this.viewer_y = 0;
		
		newSimulation();
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		
	}
	
	
	
	public void newSimulation()
	{
		instance = new Simulator(0);
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		if(instance == null){
			// Paint white for now
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0,0,720, 720);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Simulator Screen", 310, 320);
	
		}else{	
			// Clean the screen!
			g2d.setColor(Color.DARK_GRAY);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			
			// Create and assign transform
			AffineTransform old_trans = g2d.getTransform();
			AffineTransform new_trans = new AffineTransform();
			
			
			new_trans.translate( (getWidth()/2) - (instance.world.size.width * zoom)/2, 
					(getHeight()/2) - (instance.world.size.height * zoom)/2);
			
			new_trans.scale(zoom, zoom);
			new_trans.translate(viewer_x, viewer_y);
			
			
			
			g2d.setTransform(new_trans);
			 
			// Ask the Simulation to draw itself
			instance.draw(g2d);
			
			
			// Restore old transformation
			g2d.setTransform(old_trans);
		
			// Draw any HUD items
			g.setColor(Color.BLACK);
			g.drawString("Ant Simulator", 25, 25);	
		
			
		}	
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// Checked for a clicked mouse and which key
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			this.leftClick = true;
			this.prev_x = e.getX();
			this.prev_y = e.getY();
		}
		
		if(e.getButton() == MouseEvent.BUTTON2)
		{
			this.rightClick = true;	
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e){
		// Checked for a clicked mouse and which key
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			this.leftClick = false;

		}
		
		if(e.getButton() == MouseEvent.BUTTON2)
			this.rightClick = false;
		
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e){
		
		if ( e.getButton() == MouseEvent.BUTTON1) {
			
			int deltaX = e.getX() - prev_x;
			int deltaY = e.getY() - prev_y;
			
			this.prev_x = e.getX();
			this.prev_y = e.getY();
			
			
			this.viewer_x += deltaX / this.zoom;
			this.viewer_y += deltaY / this.zoom;
		}
		
		this.repaint();
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(e.getWheelRotation() < 0)
			this.zoom -= 0.008 * e.getScrollAmount();
		else
			this.zoom += 0.008 * e.getScrollAmount();
		
		if ( zoom < min_zoom  )
			zoom = min_zoom;
		else if (zoom > max_zoom)
			zoom= max_zoom;
		

		
		this.repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e){}
	@Override
	public void mouseClicked(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}





}