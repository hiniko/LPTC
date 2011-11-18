package me.paulrose.lptc.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EditorPanel extends JPanel{
	
	private Editor editor;
	
	public EditorPanel(){
		
		setPreferredSize(new Dimension(360, 720));
		setBorder(BorderFactory.createEmptyBorder() );
		
		// Create editor instance
		editor = new Editor(360 , 720);
		
		add(editor, BorderLayout.CENTER);
		setLayout(new FlowLayout(0,0,0));
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		
		
		// Draw everything else
		//super.paintComponent(g);
		
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
	}
}
