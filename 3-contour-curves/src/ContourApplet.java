// ContourApplet.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Contour Curves program
 * as an Applet.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class ContourApplet extends JApplet {

	Dimension appletSize = new Dimension(640, 480);
	Dimension displaySize = new Dimension(256, 256);
		
	public void start() {
		setSize(appletSize);
		this.getContentPane().add(new MainPanel(appletSize, displaySize));
	}

}
