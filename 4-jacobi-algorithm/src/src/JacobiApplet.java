// JacobiApplet.java

import java.awt.*;
import javax.swing.*;

/**
 * Launches the Jacobi Algorithm program
 * as an Applet.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class JacobiApplet extends JApplet {

	Dimension appletSize = new Dimension(640, 480);
	Dimension displaySize = new Dimension(300, 300);
		
	public void start() {
		setSize(appletSize);
		this.getContentPane().add(new MainPanel(displaySize));
	}

}
