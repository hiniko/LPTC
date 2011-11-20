package me.paulrose.lptc.editor;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.yaml.snakeyaml.Yaml;

public class HighlightedColours 
{
	
	private HashMap<String, Object> map;
	private ArrayList<String> keywords;
	
	
	public HighlightedColours()
	{
		// Get a new instance of the Yaml to load options
		Yaml y = new Yaml();
		// Load the file with the keywords and regexes
		map = (HashMap<String, Object>) y.load(HighlightedColours.class.getResourceAsStream("/resources/syntax_java.yml"));
		
		// Assign to the keywords to a list
		keywords = (ArrayList<String>) map.get("keywords");		
	}
	
	public SimpleAttributeSet getStyle(String text)
	{
		// First check the keywords
		if(keywords.contains(text)){
			System.out.println("Found Entry for " + text);
			
			
			SimpleAttributeSet style = new SimpleAttributeSet();
			
			StyleConstants.setFontFamily(style, "Monospaced");
			StyleConstants.setFontSize(style, 14);
			StyleConstants.setForeground(style, Color.MAGENTA);
			
			return style;
			
		}else{
			System.out.println("Got nothing =[ ");
			
			return null;
		}
		
	}
}
