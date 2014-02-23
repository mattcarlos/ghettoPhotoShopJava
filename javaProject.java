/*
 * Welcome to Matt Carlos's simple paint program
 * 
 * Main method is contained in this file, the implementation is contained in brain.java
 * 
 * Note the background file must be set to the precise system location
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
@SuppressWarnings({ "unchecked", "rawtypes" })

//main class
public class javaProject {
	//initialize variables, objects and buttons
	private static JFrame application = new JFrame("A simple paint program");
	private static JFrame application2 = new JFrame("A simple paint program");
	private static JButton showColorButton = new JButton("Change Color");
	
	//stores possible brush sizes, and creates JList to show the list
	private static final String[] brushSizeNames = {"Extra Small", "Small", "Medium", "Large", "Extra Large"};
	private static final int[] brushSizes = {2, 5, 8, 11, 14};
	private static JList brushSizeList = new JList(brushSizeNames);
	
	//this variable must be set to the exact location of the file (at least in Linux)
	private static brain brain1 = new brain(new ImageIcon("/home/hibyprime/workspace/javaFinalProject/src/background.gif").getImage());
	
	//main method
	public static void main(String[] args) {
		//adds background to JFrame
		application.getContentPane().add(brain1);
		
		//adds brain object to frame
		application.add(brain1, BorderLayout.CENTER);
		
		//adds the color button to the frame
		application.add(showColorButton, BorderLayout.SOUTH);
		
		//adds brush size list chooser to the frame, makes scrollable, sets number of rows, and sets selection type
		application.add(brushSizeList, BorderLayout.NORTH);
		application.add(new JScrollPane(brushSizeList), BorderLayout.NORTH);
		brushSizeList.setVisibleRowCount(3);
		brushSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//adds an action listener that looks for when you press the button to the color button
		showColorButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					//shows the JColorChooser
					brain1.showColors();
					
					//gets rid of the old JFrame NOTE: old frame causes bugs after changing color
					application.dispose();
					
					//creates the new JFrame and adds all required items to it
					application2.add(brain1, BorderLayout.CENTER);
					application2.add(new JLabel("Drag the mouse to draw"), BorderLayout.NORTH);
					application2.add(showColorButton, BorderLayout.SOUTH);
					application2.add(brushSizeList, BorderLayout.NORTH);
					application2.add(new JScrollPane(brushSizeList), BorderLayout.NORTH);
					application2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					application2.setSize(500, 600);
					application2.setVisible(true);
				}
			}
		);
		
		//adds action listener to look for when you click in the JList
		brushSizeList.addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						//sets the brush size to chosen size
						brain1.setSize(brushSizes[brushSizeList.getSelectedIndex()]);
					}
				}
		);
		
		//closes application when the X is clicked
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//size of the panel
		application.setSize(500, 600);
		
		//make application visible
		application.setVisible(true);
	}
}



