// BasinsApplet.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Basins of Attraction program
 * as an Applet.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class BasinsApplet extends JApplet {

	Dimension appletSize = new Dimension(480, 360);
	Dimension displaySize = new Dimension(100, 100);
		
	public void start() {
		setSize(appletSize);
		this.getContentPane().add(new MainPanel(appletSize, displaySize));
	}

}
