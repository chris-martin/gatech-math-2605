// LevelCurvesApplication.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Level Curves program as an application.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class LevelCurvesApplication extends JFrame {

	Dimension windowSize = new Dimension(600, 400);
	Dimension displaySize = new Dimension(300, 300);
	
	public LevelCurvesApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(windowSize);
		setTitle("Level Curves");
		getContentPane().add(new MainPanel(windowSize, displaySize));
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new LevelCurvesApplication();
	}
	
}
