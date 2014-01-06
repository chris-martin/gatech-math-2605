// JacobiApplication.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Jabobi Algorithm program
 * as an application.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class JacobiApplication extends JFrame {

	private static final Dimension windowSize = new Dimension(640, 480);
	private static final Dimension displaySize = new Dimension(300, 300);
	
	public JacobiApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(windowSize);
		setTitle("Jacobi Algorithm");
		getContentPane().add(new MainPanel(displaySize));
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new JacobiApplication();
	}
	
}
