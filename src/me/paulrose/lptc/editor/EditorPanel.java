package me.paulrose.lptc.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class EditorPanel extends JPanel{
	
	private Editor editor;
	private JToolBar toolbar;
	
	public EditorPanel(){
		
		// Set Panel Properties
		setPreferredSize(new Dimension(360, 720));
		setBorder(BorderFactory.createEmptyBorder());
		setLayout(new BorderLayout());
		
		// Create editor instance
		editor = new Editor(360 , 720);
		
		//Toolbar
		createToolBar();
		
		add(toolbar, BorderLayout.NORTH);
		add(editor, BorderLayout.CENTER);
	
		
	}
	
	
	private void createToolBar(){
		
		// Create toolbar
		toolbar = new JToolBar("Editor Tools");
		toolbar.setFloatable(false);
		
		// Create tool bar icons
		ImageIcon icon = createImageIcon("/resources/icons/floppy_disk_48.png");
		JButton button = new JButton(icon);
		
		toolbar.add(button);
		
		
	}

	protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = EditorPanel.class.getResource(path);
	    return new ImageIcon(imgURL);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
