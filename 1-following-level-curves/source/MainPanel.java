// MainPanel.java

import functionParser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The primary class of the Level Curves application.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class MainPanel extends JPanel implements MouseListener {

	private DisplayPanel display;
	private OptionsPanel options;
	private FunctionPanel function;
	private Curve curve;
	
	public MainPanel(Dimension size, Dimension displaySize) {
		
		setLayout(new BorderLayout());
		
		display = new DisplayPanel(
				(int)displaySize.getWidth(),
				(int)displaySize.getHeight(),
				this);
		display.setPreferredSize(displaySize);
		display.setBackground(new Color(200, 200, 240));
		display.setAxesColor(new Color(180, 180, 180));
		display.setCurveColor(new Color(000, 000, 100));
		display.setBorder(new EtchedBorder());
		
		curve = new Curve(display);
		
		options = new OptionsPanel(this);
		
		function = new FunctionPanel(this);
		
		add(new FixedSizePanel(display),BorderLayout.CENTER);
		add(options,BorderLayout.EAST);
		add(function,BorderLayout.NORTH);
		
		
	}

	public void setFunction(Evaluator evaluator) {
		curve.update(evaluator);
	}

	public void setParameters(
			double xmin, double xmax, double ymin, double ymax,
			double xnotX, double xnotY, double epsilon, int N,
			double gradientEpsilon, boolean useCorrections) {
		display.redrawAxes(xmin, xmax, ymin, ymax);
		curve.update(xnotX, xnotY, epsilon, N, gradientEpsilon, useCorrections);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()==display)
			options.setXnot(display.translateFromPoint(e.getPoint()));
	}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e)  {}
	
}