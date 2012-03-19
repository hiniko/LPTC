package me.paulrose.lptc.editor;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.undo.UndoManager;


public class HighlightedDocument extends DefaultStyledDocument
{
	private TextHighlighter colourer;
	private SimpleAttributeSet defaultStyle;
	private Editor editor;
	private int indentLevel;

	
	public HighlightedDocument(final Editor e)
	{
		// init class variables
		defaultStyle = new SimpleAttributeSet();	
		// Define the default style
		StyleConstants.setFontSize(defaultStyle, 12);
		StyleConstants.setFontFamily(defaultStyle, "Monospaced");
		StyleConstants.setForeground(defaultStyle, Color.BLACK);
		StyleConstants.setBold(defaultStyle, false);
		StyleConstants.setItalic(defaultStyle, false);
		
		colourer = new TextHighlighter(defaultStyle);
		
		editor = e;	
		
		indentLevel = 0;
	}
	
	public void rehighlight()
	{
		Element e = getRootElements()[0];
		colourer.style(e.getStartOffset(), e.getEndOffset(), this);
	}
	
	public void insertString(int offs, String str, AttributeSet a)
	throws BadLocationException
	{	
		// Text Transformations take place here
		// TODO optional auto completes
		// Replace Tabs with 4 spaces
		if( str.contains("\t") )
			str = str.replaceAll("\t", "    ");
		
		StringBuilder input  = new StringBuilder(str);
		
		for(int i=0; i<indentLevel; i++)
		{
			input.append("    ");
		}
		
		super.insertString(offs, input.toString(), defaultStyle);
		
		// Get the current sentance and send to the colourer
		Element e = getParagraphElement(editor.getCaretPosition());
		colourer.style(e.getStartOffset(), e.getEndOffset(), this);
		
	}
                    
	public void remove(int offs, int len)
	throws BadLocationException
	{
		super.remove(offs, len);
		
		// Call the Highlighter to fix words that may not match now
		Element e = getParagraphElement(editor.getCaretPosition());
		colourer.style(e.getStartOffset(), e.getEndOffset(), this);
	}
}
	

