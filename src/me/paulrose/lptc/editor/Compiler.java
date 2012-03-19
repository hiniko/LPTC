package me.paulrose.lptc.editor;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;



public class Compiler
{
	
	private JavaCompiler jc;
	private String[] compileOptions;
	private JTextArea output;
	
	
	public Compiler(JTextArea o)
	{
		jc = ToolProvider.getSystemJavaCompiler();
		output = o;
	}
	
	public boolean compileFile(String filePath)
	{
		
		output.setText("");
		StringWriter sw = new StringWriter();
		
		StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
		Iterable fileObjects = sjfm.getJavaFileObjects(filePath);
		boolean result = jc.getTask(sw, null, null, Arrays.asList(compileOptions), null, fileObjects)
			.call();
		
		if(!result)
			output.append(sw.toString());
		else
			output.append("File compiled Successfully. \n Run the simulation to see your ant work!");
		
		return result;
		
	}
	
	public void setCompileOptions(String[] o)
	{
		compileOptions = o;
	}
	
	public String[] getCompileOptions()
	{
		return compileOptions;
	}

}
