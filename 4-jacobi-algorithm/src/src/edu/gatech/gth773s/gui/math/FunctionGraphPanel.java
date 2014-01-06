// FunctionGraphPanel.java

package edu.gatech.gth773s.gui.math;

import functionParser.*;
import edu.gatech.gth773s.math.*;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class FunctionGraphPanel extends JPanel {
	
	private DisplayPanel2d display;
	private Dimension displaySize;
	protected double xmin=-10, xmax=10, ymin=-10, ymax=10;
	protected BufferedImage statPlotSymbol;
	
	public FunctionGraphPanel(Dimension size) {
		display = new DisplayPanel2d(size);
		displaySize = size;
		setPreferredSize(size);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(display);
		statPlotSymbol = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics sym = statPlotSymbol.getGraphics();
		sym.setColor(Color.blue);
		sym.drawLine(0,2,2,0);
		sym.drawLine(2,0,4,2);
		sym.drawLine(4,2,2,4);
		sym.drawLine(2,4,0,2);
		sym.setColor(Color.black);
		sym.drawLine(2,1,3,2);
		sym.drawLine(1,2,2,3);
	}
	
	public void setBackground(Color color) {
		if (display != null)
			display.setBackground(color);
	}
	
	public void setWindow(double xmin, double xmax, double ymin, double ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		display.setWindow(xmin, xmax, ymin, ymax);
	}
	
	public void removeAll() {
		display.removeAll();
	}
	
	public void clearAll() {
		display.clearAll();
	}
	
	public void addFunction(Evaluator function, Color color, Object key) {
		display.addImageAbove(key);
		display.setColor(color, key);
		Vector2d p1 = new Vector2d(xmin, function.evaluate(xmin));
		Vector2d p2;
		for (int i=1; i<displaySize.width; i++) {
			double x = display.translateXFromPoint(i);
			p2 = new Vector2d(x, function.evaluate(x));
			display.drawLine(p1, p2, key);
			p1 = p2;
		}
	}

	public void addStatPlot(Object key) {
		display.addImageAbove(key);
	}

	public void addStatPoint(Vector2d p, Object key) {
		display.drawImageCentered(statPlotSymbol, p, key);
	}
	
	/**
	 * Stat plot given by discrete keys (0, 1, 2, 3...)
	 */
	public void addStatPlot(double[] values, Object key) {
		addStatPlot(key);
		for (int i=0; i<values.length; i++)
			addStatPoint(new Vector2d(i, values[i]), key);
	}
	
}
