package me.paulrose.lptc.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.yaml.snakeyaml.Yaml;


public class RegexTest {
	
	
	private HashMap<String, ArrayList<Pattern>> rxs;
	
	public RegexTest(){
		
		Yaml y = new Yaml();
		// Load the file with the keywords and regexes
		HashMap<String, Object> map = (HashMap<String, Object>) y.load(RegexTest.class.getResourceAsStream("/resources/syntax_java.yml"));
		
		// Assign to the keywords to a list
		ArrayList<String> allKeywords = (ArrayList<String>) map.get("keywords");		
		 	
		// Create of highlightable words
		
		rxs = new HashMap<String, ArrayList<Pattern>>();
		
		// Create list of compiled Regexes
		ArrayList<Pattern> keywords = new ArrayList<Pattern>();
		
		for (String i : allKeywords) {
			keywords.add(Pattern.compile(" "+i+" *(\\(.+?\\))?"));
		}
		
		String s = "thisisisi is what happens switch if(conditon) switch(condition)";
		
		for (Pattern p : keywords) {		
			
			Matcher m =  p.matcher(s);
			
			if(m.find()){
				System.out.println(s.substring( m.start(), m.end()));
			}
			
			
		}
		
		
		
		
		
		
		
		
		//Populate

		
		
	}

}
