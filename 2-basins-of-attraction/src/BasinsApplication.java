// BasinsApplication.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Basins of Attraction program
 * as an application.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class BasinsApplication extends JFrame {

	Dimension windowSize = new Dimension(480, 360);
	Dimension displaySize = new Dimension(100, 100);
	
	public BasinsApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(windowSize);
		setTitle("Basins of Attraction");
		getContentPane().add(new MainPanel(windowSize, displaySize));
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BasinsApplication();
	}
	
}
