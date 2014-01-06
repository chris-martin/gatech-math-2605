// ContourApplication.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Contour Curves program
 * as an application.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class ContourApplication extends JFrame {

	Dimension windowSize = new Dimension(640, 480);
	Dimension displaySize = new Dimension(256, 256);
	
	public ContourApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(windowSize);
		setTitle("Contour Curves");
		getContentPane().add(new MainPanel(windowSize, displaySize));
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ContourApplication();
	}
	
}
