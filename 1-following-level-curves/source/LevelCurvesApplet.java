// LevelCurvesApplet.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Level Curves program as an Applet.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class LevelCurvesApplet extends JApplet {

	Dimension appletSize = new Dimension(600, 400);
	Dimension displaySize = new Dimension(300, 300);
	
	public void start() {
		setSize(appletSize);
		this.getContentPane().add(new MainPanel(appletSize, displaySize));
	}
	
}
