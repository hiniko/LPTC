package me.paulrose.lptc;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import me.paulrose.lptc.editor.EditorPanel;
import me.paulrose.lptc.simulator.Simulator;

public class Window extends JFrame {
	
	private EditorPanel editor;
	private Simulator simulator;
	private JMenuBar menuBar;

	public Window(){
		
		// Set window properties
		setSize(1080, 720);
		setTitle("Learning Through Programming");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		// Create an instance of the editor
		editor = new EditorPanel();
		
		// Create and instance of the simulator
		simulator = new Simulator();
		
		
		
		
		// Create Menubar
		createMenuBar();
		
		// Set up all window elements, Show time
		add(menuBar, BorderLayout.NORTH);
		add(editor, BorderLayout.WEST);
		add(simulator, BorderLayout.CENTER);
		
		
			
		setVisible(true); 
		
	}
	
	
	private void createMenuBar(){
		
		menuBar = new JMenuBar();
		
		JMenu menu;
		JMenuItem item;
		
		// Create the file menu
		menu = new JMenu("File");
		// Items in the file menu
		item = new JMenuItem("New Simulation");
		menu.add(item);
		
		menuBar.add(menu);
		
		
		
	}
	
}
