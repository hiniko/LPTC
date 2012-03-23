package me.paulrose.lptc.editor;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.undo.UndoManager;


public class Editor extends JTextPane {

	private HighlightedDocument document = new HighlightedDocument(this);
	private CompoundUndoManager undo;
	private String fileLocation;
	
	
	public Editor(int instance)
	{
		setDocument(document);
		setAutoscrolls(true);
		setMargin(new Insets(10, 10, 10, 10));
		
		
		undo = new CompoundUndoManager(this);
		fileLocation = null;
	}
	
	// Override to turn on Antialiased text
	@Override
	public void paintComponent( Graphics g )
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON );
		super.paintComponent( g );
	}
	
	public void read(BufferedReader br)
	{
		// Read in data preserving the document type!
		
		// Empty the text we have
		setText("");
		// String builder for its mutablenessss
		StringBuilder file = new StringBuilder();
		String l;
		try
		{
			while( (l = br.readLine()) != null )
			{
			file.append(l);	
			file.append("\n"); // Manually add the line breaks back
			}
			setText(file.toString()); // Add the text to the document
			// Force a complete re-evaluation of highlighting , could take a while
			// esp. on large documents, but we will see
			((HighlightedDocument)getDocument()).rehighlight();
			
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Reading file went terribly wrong, sorry about that!");
		}
	}
	
	public void setFileLoction(String l)
	{
		fileLocation = l;
	}
	
	public String getFileLocation()
	{
		return fileLocation;
	}
	
	public Action getUndoAction()
	{
		return undo.getUndoAction();
	}
	
	public Action getRedoAction()
	{
		return undo.getRedoAction();
	}
	

}
