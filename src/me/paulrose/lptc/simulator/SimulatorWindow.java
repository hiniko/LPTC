package me.paulrose.lptc.simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SimulatorWindow extends JPanel implements 
	MouseMotionListener, MouseListener, MouseWheelListener{
	
	Simulator instance;
	int viewer_x, viewer_y, prev_x, prev_y;
	double zoom, min_zoom, max_zoom;
	boolean leftClick, rightClick;
	private JPanel controlPanel;
	private SimulationPanel viewerPanel;
	private Thread simulationThread;
	
	
	public SimulatorWindow(){
		
		// Set JPanel Preferences
		super(new BorderLayout());
		setPreferredSize(new Dimension(720, 720));
		
		// Init Values
		min_zoom = 0.01;
		zoom = 1.1;
		max_zoom = 2.0;
		
		leftClick = false;
		rightClick = false;
		
		viewer_x = 0;
		viewer_y = 0;
		
		// Create the Special panel that the simulation will be drawn to
		viewerPanel = new SimulationPanel();
		
		// Create a simulation instance and a new world 
		instance = new Simulator();
		instance.createWorld(123456, 4);
		
		
		
		// Create the thread that the simulation will run on 
		simulationThread = new Thread(instance);
		// Create the panel the thread will draw on
		viewerPanel = new SimulationPanel();
		
		controlPanel = new JPanel();
		
		JButton startStopButton = new JButton("Start");
		
		startStopButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Start/Stop Button Pressed");
				
				if(!instance.isRunning()){
					System.out.println("Starting the Simulation");
					simulationThread.start();
					JButton button = (JButton) e.getSource();
					button.setName("Stop");
				}else{
					System.out.println("Stopping the simulation");
					instance.pause();
					JButton button = (JButton) e.getSource();
					button.setName("Start");
				}
				
			}
			
		});
		
		controlPanel.add(startStopButton);
		add(controlPanel, BorderLayout.SOUTH);
		add(viewerPanel, BorderLayout.CENTER);
		
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		
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
			this.leftClick = false;
		
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
		
		viewerPanel.repaint();
		
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
		
		viewerPanel.repaint();
	}
	
	
	private class SimulationPanel extends JPanel 
	{
	
		public Timer updater;
		
		public SimulationPanel()
		{
			super();
			// Create the timer that will update the simulation panel
			Timer updater = new Timer((int) Simulator.STEP_TIME, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e)
				{
					SimulationPanel.this.repaint();
				}
				
			});
		
			
			updater.start();
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
				AffineTransform new_trans = g2d.getTransform();
				
				// Work out the translation to center the world on screen and for zooming
				int center_x = (int) ((getWidth()/2) - (instance.getWorldSize().width * zoom)/ 2);
				int center_y = (int) ((getHeight()/2) - (instance.getWorldSize().height * zoom)/2);
				
				// Add the centering, zoom and screen positioning
				new_trans.translate(center_x, center_y);
				new_trans.scale(zoom, zoom);
				new_trans.translate(viewer_x, viewer_y);
				
				// Apply the transformation to the graphics instance
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
