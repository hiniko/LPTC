package me.paulrose.lptc.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;


public class EditorPanel extends JPanel{
	
	
	public static final String TEMPLATE_PATH = "/resources/AntTemplate.txt";
	private String template;
	private JTabbedPane tabs;
	private JToolBar toolbar;
	private JPanel helpbox;
	private JTextArea output;
	private JTextField playerNameBox;
	private JTextField antNameBox;
	private Editor editor;
	private final JFileChooser fileChooser;
	private final FileNameExtensionFilter fileFilter;
	private Compiler compiler;
	private String compilerFile, antFile;
	
	
	private int editorCount = 1;
	
	public EditorPanel(){
		
		// Set Panel Properties
		setPreferredSize(new Dimension(360, 720));
		setBorder(BorderFactory.createEmptyBorder());
		setLayout(new BorderLayout());
		
		// Create the file chooser
	
		fileFilter = new FileNameExtensionFilter("Saved Ant Files!", ".ant");
	
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(fileFilter);
		
		// Create the editor
		editor =  new Editor(editorCount);
		
		// Toolbar
		createToolBar();
		
		// Create Tabbed pane
		tabs = new JTabbedPane();
		
		// Create a empty tab for the JTabbedPane
		newDocument();
		
		// Create help box at bottom
		helpbox = new JPanel();
		helpbox.setLayout(new BorderLayout());
		helpbox.setPreferredSize(new Dimension(300, 300));
		
		JPanel outputBox = new JPanel();
		outputBox.setLayout(new BorderLayout());
		JLabel outputLabel = new JLabel("Output: ");
		
		output = new JTextArea();
		output.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		output.setWrapStyleWord(false);
		output.setEditable(false);
		
		JScrollPane sp = new JScrollPane(output);
		sp.setHorizontalScrollBar(new JScrollBar());
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		outputBox.add(outputLabel, BorderLayout.NORTH);
		outputBox.add(sp, BorderLayout.CENTER);
		
		
		
		// Instantiate the compiler
		compiler = new Compiler(output);
		compiler.setCompileOptions(new String[]{"-d", System.getProperty("user.dir") + "/ants/"});
		
		// Load the template into memory exit if we cannot find it
		try
		{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(Editor.class.getResourceAsStream(TEMPLATE_PATH), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			
			String l;
			while( (l = br.readLine()) != null )
			{
				sb.append(l).append("\n");	
			}
			template = sb.toString();
			br.close();

			//System.out.println(template);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Couldn't find the template file! ABORT!");
			System.exit(-1);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Couldn't load the template file! ABORT!");
		}
		
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.PAGE_AXIS));
		
		JLabel nameLabel = new JLabel("Name:");
		playerNameBox = new JTextField();
		playerNameBox.setPreferredSize(new Dimension(200, 20));
		
		JLabel antNameLabel = new JLabel("Ant Name:");
		antNameBox = new JTextField();
		antNameBox.setPreferredSize(new Dimension(200, 20));
		
		detailPanel.add(nameLabel);
		detailPanel.add(playerNameBox);
		detailPanel.add(antNameLabel);
		detailPanel.add(antNameBox);
		
		helpbox.add(detailPanel, BorderLayout.NORTH);
		helpbox.add(outputBox, BorderLayout.CENTER);
		
		add(toolbar, BorderLayout.NORTH);
		add(tabs, BorderLayout.CENTER);
		add(helpbox, BorderLayout.SOUTH);
				
	}
	
	public void newDocument(){
		
		JScrollPane scroller = new JScrollPane(editor,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scroller.setName("Untitled Agent " + editorCount);
		
		tabs.add(scroller);
		editorCount++;
	}
	
	private void createToolBar(){
		
		ImageIcon icon;
		JButton button;
		
		// Create toolbar
		toolbar = new JToolBar("Editor Tools");
		toolbar.setFloatable(false);
		toolbar.setBackground(new Color(235,235,235));
		
		// Create tool bar icons

		// Load
		icon = createImageIcon("/resources/icons/36/119.png");
		button = new JButton(icon);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//TODO Find out why with a file filter I cannot pick files that
				//fit the filter, honestly what?
				
				fileChooser.setFileFilter(null);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				int returnVal = fileChooser.showOpenDialog(EditorPanel.this);
				
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					
					File f = fileChooser.getSelectedFile();
					
					loadFile(f);
				}
				
				fileChooser.setFileFilter(fileFilter);
				
			}
		});
		
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		button.setToolTipText("Load your Ant");
		toolbar.add(button);
		
		// Save 
		icon = createImageIcon("/resources/icons/36/7.png");
		button = new JButton(icon);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				showSaveDialog();
			}
		});
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		button.setToolTipText("Save your Ant Code");
		toolbar.add(button);
		
		// Undo
		icon = createImageIcon("/resources/icons/36/72.png");
		button = new JButton(icon);
		button.setAction(editor.getUndoAction());
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		button.setToolTipText("Undo last change");
		toolbar.add(button);
		// Redo
		icon = createImageIcon("/resources/icons/36/71.png");
		button = new JButton(icon);
		button.setAction(editor.getRedoAction());
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		button.setToolTipText("Redo last change");
		toolbar.add(button);
		
		//Compile
		icon = createImageIcon("/resources/icons/36/45.png");
		button = new JButton(icon);
		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Compile the file!!
				// Check to see if the file has been saved first
				if(playerNameBox.getText().isEmpty() 
					|| playerNameBox.getText().startsWith(" ")
					|| playerNameBox.getText().endsWith(" ")
					|| antNameBox.getText().isEmpty() 
					|| antNameBox.getText().startsWith(" ")
					|| antNameBox.getText().endsWith(" ")
						
						)
				{
					JOptionPane.showMessageDialog(EditorPanel.this,
						    "Please check your name and the name of your ant. \n " +
						    "They cannot be blank, start or end with a space",
						    "Hey, Listen...",
						    JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					boolean fileLocationKnown = false;

					if(editor.getFileLocation() == null)
					{
						fileLocationKnown = showSaveDialog();
					}
					else
					{
						fileLocationKnown = true;
						saveFile(antFile);
					}

					if(fileLocationKnown)
					{
						compiler.compileFile(compilerFile);		
					}
					
				}
				
			}
			
		});
		
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		button.setToolTipText("Complie your Ant");
		toolbar.add(button);
		
		
		/*
		// New
		icon = createImageIcon("/resources/icons/36/8.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		// Delete
		icon = createImageIcon("/resources/icons/36/9.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		// Clone
		icon = createImageIcon("/resources/icons/36/34.png");
		button = new JButton(icon);
		button.setBorderPainted(false);
		button.setBackground(new Color(235,235,235));
		toolbar.add(button);
		*/
		
	}
	
	private boolean showSaveDialog()
	{
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Pick a foler to save in ...");
		fileChooser.setSelectedFile(new File(playerNameBox.getText() + antNameBox.getText()));
		int returnVal = fileChooser.showSaveDialog(EditorPanel.this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File f = fileChooser.getSelectedFile();
			
			String filePath = f.getAbsolutePath();
			// Check to see if the file extension exists
			if(!filePath.endsWith(fileFilter.getExtensions()[0]))
					filePath = f.getAbsolutePath()	+ fileFilter.getExtensions()[0];
			
			antFile = filePath;
			saveFile(filePath);
			
			return true;
		}
		
		return false;
	}
	
	private void saveFile(String filePath )
	{
		try
		{
			// Save the raw code 
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(editor.getText());
			bw.close();
			editor.setFileLoction(filePath);
			// Save a template compiled version
			
			// Make the compiled source
			String userTemplate = new String(template);
			
			String playerName = playerNameBox.getText();
			String antName = antNameBox.getText();
			
			char[] temp = playerName.toCharArray();
			temp[0] = Character.toUpperCase(temp[0]);
			playerName = new String(temp);
			
			temp = antName.toCharArray();
			temp[0] = Character.toUpperCase(temp[0]);
			antName = new String(temp);
			
			userTemplate = userTemplate.replaceAll("--NAME--", playerName + antName);
			userTemplate = userTemplate.replaceAll("--USERCODE--", editor.getText());
			
			// Save the raw code 
			compilerFile = System.getProperty("user.dir") + 
					"/ants/" + playerName + antName + "Ant.java";
			
			bw = new BufferedWriter(new FileWriter(compilerFile));
			bw.write(userTemplate);
			bw.close();
			
					
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void loadFile(File f)
	{
		try
		{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			editor.read(br);
			
		}catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
	}

	protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = EditorPanel.class.getResource(path);
	    return new ImageIcon(imgURL);
	}
}
