package me.paulrose.lptc.editor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Editor extends JTextPane {

	private HighlightedDocument document = new HighlightedDocument();
	
	public Editor(int instance){
		
		setDocument(document);
		setAutoscrolls(true);
		
	}
	
	
	// Override to turn on Antialiased text
	public void paintComponent( Graphics g )
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON );
		super.paintComponent( g );
	}
	
}
