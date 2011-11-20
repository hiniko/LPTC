package me.paulrose.lptc.editor;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

public class HighlightedDocument extends DefaultStyledDocument
{
	private int lastSpaceIndex = 0;
	private HighlightedColours hc = new HighlightedColours();
	
	public HighlightedDocument()
	{
		
	}
	
	public void insertString(int offs, String str, AttributeSet a)
		throws BadLocationException
	{
		if( str.compareToIgnoreCase(" ") == 0 || str.compareToIgnoreCase("\n") == 0)
		{
			System.out.println("Space has been pressed!");
			super.insertString(offs, str, a);
			
			runColourer(lastSpaceIndex, offs);
			lastSpaceIndex = offs+1;
			
		
		}else{
			// Render string as normal
			super.insertString(offs, str, a);
		}
		
	}
	
	public void runColourer(int start, int end)
	{
		// Get the word at the offset
		try 
		{
			// Calculate the length of the word
			int length = end - start;
			
			// Length has to be greater then 0
			if(length > 0){
				String text = getText(start, (end - start));
				
				// Run the text though matching and return the styles
				SimpleAttributeSet style =  hc.getStyle(text);
				// If there was a successful match...
				if (style != null){
					System.out.println("Changing styles of :: " + text);
					setCharacterAttributes(start, length, style, false );
					
				}
			}
			
		} catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
			
	}

}
