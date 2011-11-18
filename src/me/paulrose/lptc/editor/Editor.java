package me.paulrose.lptc.editor;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JEditorPane;
import javax.swing.border.EmptyBorder;

public class Editor extends JEditorPane {

	
	public Editor(int width, int height){
		
		
		setPreferredSize(new Dimension(width, height));
	}
	
}
