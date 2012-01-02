package me.paulrose.lptc.editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.yaml.snakeyaml.Yaml;

public class TextHighlighter
{
	
	private HashMap<String, Object> map;
	private SimpleAttributeSet highlightStyle, defaultStyle, functionStyle, 
							   commentStyle, numbersStyle, typesStyle, stringsStyle;
	private ArrayList<Pattern> keywords, comments, types;
	private Pattern functions, numbers, strings;
	
	
	public TextHighlighter(final SimpleAttributeSet defStyle)
	{
		// instantiate 
		
		keywords = new ArrayList<Pattern>();
		comments = new ArrayList<Pattern>();
		types = new ArrayList<Pattern>();
			
		// Get a new instance of the Yaml to load options
		Yaml y = new Yaml();
		// Load the file with the keywords and regexes
		map = (HashMap<String, Object>) y.load(TextHighlighter.class.getResourceAsStream("/resources/syntax_java.yml"));
			
		// Create highlight styles
		defaultStyle = defStyle;
		
		// Keyword style
		highlightStyle = new SimpleAttributeSet(defaultStyle);
		StyleConstants.setForeground(highlightStyle, new Color(205,0,147));
		StyleConstants.setBold(highlightStyle, true);
		// Function Style // Green
		functionStyle = new SimpleAttributeSet(defaultStyle);
		StyleConstants.setForeground(functionStyle, new Color(17,202,0)); 
		// Numbers Style
		numbersStyle = new SimpleAttributeSet(defaultStyle);
		StyleConstants.setForeground(numbersStyle, Color.RED);
		// Types style
		typesStyle = new SimpleAttributeSet(defaultStyle);
		StyleConstants.setForeground(typesStyle, Color.CYAN);
		// Strings Style
		stringsStyle = new SimpleAttributeSet(defaultStyle);
		StyleConstants.setForeground(stringsStyle, Color.GRAY);
		
		
		// get raw keywords
		
		
		HashMap<String, Object> regex = (HashMap<String, Object>) map.get("regex");		
		
		// get function regex
		functions = Pattern.compile((String)regex.get("functions"));
		numbers = Pattern.compile((String)regex.get("numbers"));
		strings = Pattern.compile((String)regex.get("strings"));
		
		ArrayList<String> commentsReg = (ArrayList<String>) regex.get("comments");
		System.out.println(commentsReg);
		
		for (String s : commentsReg) {
			comments.add(Pattern.compile(s));			
		}
		
		// Compile list of patterns		
		keywords = new ArrayList<Pattern>();
		
		ArrayList<String> allKeywords = (ArrayList<String>) map.get("keywords");
				// Assign to the keywords to a list
		for (String i : allKeywords) {
			//keywords.add(Pattern.compile(""+i+"(\\((.+?)?\\))?"));
			keywords.add(Pattern.compile("\\b"+i+"\\b"));
		}

		ArrayList<String> allTypes = (ArrayList<String>) map.get("types");
		
		// Assign to the TYPES to a list
		for (String i : allTypes) {
			//keywords.add(Pattern.compile(""+i+"(\\((.+?)?\\))?"));
			types.add(Pattern.compile("\\b"+i+"\\b"));
		}

	}
	
	public void style(final int offs_start, final int offs_end, final HighlightedDocument doc)
	{
		// First check the keywords
		// Create list of compiled Regexes
		
		String text = " ";
		
		int sentanceLength = offs_end - offs_start;
		
		// reset highlighting on this sentance
		doc.setCharacterAttributes(offs_start, sentanceLength, defaultStyle, true);
		
		try {
			text = doc.getText(offs_start, sentanceLength);
			
			// Find function definitions
			Matcher m = functions.matcher(text);
			while(m.find()){
				int matchLength = m.end() - m.start();
				doc.setCharacterAttributes(offs_start + m.start(), matchLength-1, functionStyle, true);
			}
			
			// Find numbers
			m = numbers.matcher(text);
			while(m.find()){
				int matchLength = m.end() - m.start();
				doc.setCharacterAttributes(offs_start + m.start(), matchLength, numbersStyle, true);
			}
			
			// Find numbers
			m = strings.matcher(text);
			while(m.find()){
				int matchLength = m.end() - m.start();
				doc.setCharacterAttributes(offs_start + m.start(), matchLength, stringsStyle, true);
			}
			
			// Find all keywords
			for (Pattern p : keywords) 
			{
				m =  p.matcher(text);
				
				while(m.find())
				{
					int matchLength = m.end() - m.start();
					doc.setCharacterAttributes(offs_start + m.start(), matchLength, highlightStyle, true);
				}
			}
			
			
			// Find all types
			for (Pattern p : types) 
			{
				m =  p.matcher(text);
				
				while(m.find())
				{
					int matchLength = m.end() - m.start();
					doc.setCharacterAttributes(offs_start + m.start(), matchLength, typesStyle, true);
				}
			}

		}
		catch (BadLocationException e) 
		{
			System.out.println("BadLocation");
		}	

	}
}
