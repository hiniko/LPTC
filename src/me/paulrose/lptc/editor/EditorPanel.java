package me.paulrose.lptc.editor;

import java.awt.Dimension;

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
		
		add(editor);
		
	}
}
