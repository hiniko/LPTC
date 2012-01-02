package me.paulrose.lptc.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;


public class EditorPanel extends JPanel{
	
	private JTabbedPane tabs;
	private JToolBar toolbar;
	private JPanel helpbox;
	
	
	private int editorCount = 1;
	
	public EditorPanel(){
		
		// Set Panel Properties
		setPreferredSize(new Dimension(360, 720));
		setBorder(BorderFactory.createEmptyBorder());
		setLayout(new BorderLayout());
		
		// Toolbar
		createToolBar();
		
		// Create Tabbed pane
		tabs = new JTabbedPane();
		
		// Create a empty tab for the JTabbedPane
		newDocument();
		
		// Create help box at bottom
		helpbox = new JPanel();
		helpbox.setPreferredSize(new Dimension(360, 200));
		
		
		add(toolbar, BorderLayout.NORTH);
		add(tabs, BorderLayout.CENTER);
		add(helpbox, BorderLayout.SOUTH);
				
	}
	
	public void newDocument(){
		
		JScrollPane scroller = new JScrollPane( new Editor(editorCount),
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scroller.setName("Untitled Agent " + editorCount);
		
		tabs.add(scroller);
		editorCount++;
	}
	
	private void createToolBar(){
		
		ImageIcon icon;
		JButton button;
		
		// Create toolbar
		toolbar = new JToolBar("Editor Tools");
		toolbar.setFloatable(false);
		toolbar.setBackground(new Color(235,235,235));
		
		// Create tool bar icons
		// Save 
		icon = createImageIcon("/resources/icons/36/7.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		// Undo
		icon = createImageIcon("/resources/icons/36/72.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		// Redo
		icon = createImageIcon("/resources/icons/36/71.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		// New
		icon = createImageIcon("/resources/icons/36/8.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		// Delete
		icon = createImageIcon("/resources/icons/36/9.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		// Clone
		icon = createImageIcon("/resources/icons/36/34.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		
	}

	protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = EditorPanel.class.getResource(path);
	    return new ImageIcon(imgURL);
	}
}
