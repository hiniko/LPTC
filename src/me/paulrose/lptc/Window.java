package me.paulrose.lptc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

import me.paulrose.lptc.editor.EditorPanel;
import me.paulrose.lptc.simulator.AntArena;

public class Window extends JFrame {
	
	private EditorPanel editor;
	private CanvasGameContainer canvas;
	private JMenuBar menuBar;
	private JPanel controlBar;

	public Window(){
		
		// Set window properties
		setSize(1080, 720);
		setTitle("Learning Programming Through Competition");
				
		// Create an instance of the editor
		editor = new EditorPanel();
	
		try {
			canvas = new CanvasGameContainer(new AntArena());
			
		} catch (SlickException e) {
			System.out.println("The Game didn't work for some reason, COMPENSATE!");
			e.printStackTrace();
		}
		
		// Create menu bar
		createMenuBar();
		createControlBar();
		
		// Set up all window elements, Show time
		add(menuBar, BorderLayout.NORTH);
		add(editor, BorderLayout.WEST);
		add(canvas, BorderLayout.CENTER);
		add(controlBar, BorderLayout.SOUTH);
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		setVisible(true);
		
	}
	
	private void createControlBar() 
	{
		controlBar = new JPanel();
		
		JButton start = new JButton("Start");
		
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				try {
					canvas.start();
					JButton b = (JButton)e.getSource();
					b.setEnabled(false);
				} catch (SlickException e1) {
					System.out.println("Paul, I'ma let you finish, but this exectpion is the best of all time!");
					e1.printStackTrace();
				}
				
			}
			
		});
		
		JButton pause = new JButton("Pause");
		
		pause.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.getContainer().pause();
			}
		});
		
		
		JButton resume = new JButton("Resume");
		
		resume.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.getContainer().resume();
			}

		});
		
		controlBar.add(start);
		controlBar.add(pause);
		controlBar.add(resume);
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
