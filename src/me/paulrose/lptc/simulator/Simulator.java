package me.paulrose.lptc.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Simulator extends JPanel{
	
	JPanel canvas;
	
	
	public Simulator(){
		
		setPreferredSize(new Dimension(720, 720));
		
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		
		// Paint white for now
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,720, 720);
		g.setColor(Color.WHITE);
		g.drawChars("Simulator Screen".toCharArray(), 0, 16, 310, 320);
		
	}

}
