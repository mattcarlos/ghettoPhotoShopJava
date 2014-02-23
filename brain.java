/*
 * Implementation of simple paint program
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
@SuppressWarnings("serial")

//brain class that includes JPanel
public class brain extends JPanel {
	//keeps count total number of points
	private int pointCount = 0;
	
	//keeps count of when to change to old colors
	private int colorCount1 = 0;
	private int colorCount2 = 0;
	private int colorCount3 = 0;
	
	//keeps track of colors
	private Color color;
	private Color oldColor1;
	private Color oldColor2;
	private Color oldColor3;
	
	//default pen size
	private int size = 2;
	
	//old pen sizes
	private int oldSize1 = 0;
	private int oldSize2 = 0;
	private int oldSize3 = 0;
	
	//keeps count of when to change to old sizes
	private int oldSizeCount1 = 0;
	private int oldSizeCount2 = 0;
	private int oldSizeCount3 = 0;
	
	//stores each point that the user draws
	private Point[] points = new Point[100000];
	
	//image variable
	private Image img;
	
	//constructor method
	public brain(Image img) {
		//sets the class's img variable to the supplied img
		this.img = img;
		
		//local variable for the size of the image
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		
		//sets size defaults
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
		
		//call a method that looks for mouse movement
		addMouseMotionListener(
			//when mouse movement happens, consider the method mouseDragged called
			new MouseMotionAdapter() {
				//MouseEvent is given the mouse positions
				public void mouseDragged(MouseEvent event) {
					//if the total number of points is less than 10000 (total Point array value)
					//adds the point location from the current location in variable points
					//increments counter, and repaints the panel
					if (pointCount < points.length) {
						points[pointCount] = event.getPoint();
						pointCount += 1;
						repaint();
					}
				}
			}
		);
	}
	
	//turns a string into an image
	//second constructor method
	public brain(String img) {
		this(new ImageIcon(img).getImage());
	}
	
	//method that does the painting
	public void paintComponent(Graphics g) {
		//repaint previous panel
		super.paintComponent(g);
		
		//draws the background image
		g.drawImage(img, 0, 0, null);
		
		//draw every single point again, because Java is gay
		for (int i = 0; i < pointCount; i++) {
			//sets the color based on where in the loop we are, to keep old colors intact
			if (i < colorCount3) {
				g.setColor(oldColor3);
			} else if (i < colorCount2) {
				g.setColor(oldColor2);
			} else if (i < colorCount1) {
				g.setColor(oldColor1);
			} else {
				g.setColor(color);
			}
			
			//sets the size based on where we are in the loop, to keep old sizes intact
			//draws the dots
			if (i < oldSizeCount3) {
				g.fillOval(points[i].x,  points[i].y, oldSize3, oldSize3);
			} else if (i < oldSizeCount2) {
				g.fillOval(points[i].x,  points[i].y, oldSize2, oldSize2);
			} else if (i < oldSizeCount1) {
				g.fillOval(points[i].x,  points[i].y, oldSize1, oldSize1);
			} else {
				g.fillOval(points[i].x,  points[i].y, size, size);
			}
		}
	}
	
	//sets color
	public void setColor1(Color selectedColor) {
		//color counter set to point count in order to save old colors, after 3, the oldest is deleted
		if (colorCount1 == 0) {
			colorCount1 = pointCount;
		} else if (colorCount2 == 0) {
			colorCount2 = colorCount1;
			colorCount1 = pointCount;
		} else {
			colorCount3 = colorCount2;
			colorCount2 = colorCount1;
			colorCount1 = pointCount;
		}
		
		//saves the old colors, after 3 old colors, the oldest is deleted
		if (oldColor1 == null) {
			oldColor1 = color;
		} else if (oldColor2 == null) {
			oldColor2 = oldColor1;
			oldColor1 = color;
		} else {
			oldColor3 = oldColor2;
			oldColor2 = oldColor1;
			oldColor1 = color;
		}
		
		//sets the selected color
		if (selectedColor != null) {
			color = selectedColor;
		} else {
			color = Color.BLACK;
		}
	}
	
	//method to set the size of the pen
	public void setSize (int enteredSize) {
		//if size didn't change, do nothing
		if (enteredSize != size) {
			//keeps the last 3 sizeCounters intact, after that they are deleted
			if (oldSizeCount1 == 0) {
				oldSizeCount1 = pointCount;
			} else if (oldSizeCount2 == 0) {
				oldSizeCount2 = oldSizeCount1;
				oldSizeCount1 = pointCount;
			} else {
				oldSizeCount3 = oldSizeCount2;
				oldSizeCount2 = oldSizeCount1;
				oldSizeCount1 = pointCount;
			}
			
			//keeps the last 3 sizes intact, after that they are deleted
			if (enteredSize > 0) {
				if (oldSize1 == 0) {
					oldSize1 = size;
					size = enteredSize;
				} else if (oldSize2 == 0) {
					oldSize2 = oldSize1;
					oldSize1 = size;
					size = enteredSize;
				} else {
					oldSize3 = oldSize2;
					oldSize2 = oldSize1;
					oldSize1 = size;
					size = enteredSize;
				}
			}
		}
	}
	
	public void showColors() {
		//opens the color chooser dialog
		Color selectedColor = JColorChooser.showDialog(this, "Choose a color", color);
		
		//add the panels and such to the frame
		setSize(400, 130);
		setVisible(true);
		
		//if no color is chosen, set color to black
		if (selectedColor == null) {
			selectedColor = Color.BLACK;
		}
		
		//set brains color to the selected color
		setColor1(selectedColor);
	}
}









