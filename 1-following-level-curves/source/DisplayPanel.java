// DisplayPanel.java

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.JPanel;

/**
 * A panel that displays a graph.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class DisplayPanel extends JPanel {
	
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
	private static final Color DEFAULT_CURVE_COLOR = Color.BLACK;
	private static final Color DEFAULT_AXES_COLOR = Color.LIGHT_GRAY;
	
	private int width, height;
	private double xmin, xmax, ymin, ymax;
	
	private BufferedImage curve;
	private Graphics curveG;
	private Color curveColor = DEFAULT_CURVE_COLOR;
	
	private BufferedImage axes;
	private Graphics axesG;
	private Color axesColor = DEFAULT_AXES_COLOR;
	
	public DisplayPanel(int width, int height) {
		this.width = width;
		this.height = height;
		setBackground(DEFAULT_BACKGROUND_COLOR);
	}

	public DisplayPanel(int width, int height, MouseListener ml) {
		this(width, height);
		this.addMouseListener(ml);
	}
	
	public void setCurveColor(Color c) {
		curveColor = c;
	}
	
	public void setAxesColor(Color c) {
		axesColor = c;
	}
	
	public void clearCurve() {
		curve = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		curveG = curve.getGraphics();
		curveG.setColor(curveColor);
		repaint();
	}

	public int translateXToPoint(double x) {
		return (int) (((x-xmin)/(xmax-xmin))*width+.5);
	}
	
	public int translateYToPoint(double y) {
		return (int) (height-(((y-ymin)/(ymax-ymin))*height+.5));
	}
	
	public Point translateToPoint(double x, double y) {
		return new Point(translateXToPoint(x), translateYToPoint(y));
	}
	
	public double translateXFromPoint(double x) {
		return ( ( (x-.5)*(xmax-xmin) ) / ((double)width) ) + xmin;
	}
	
	public double translateYFromPoint(double y) {
		return -( ( (y-.5)*(ymax-ymin) ) / ((double)height) ) + ymax;
	}
	
	public double[] translateFromPoint(Point p) {
		double[] ret = new double[2];
		ret[0] = translateXFromPoint(p.getX());
		ret[1] = translateYFromPoint(p.getY());
		return ret;
	}
	
	public void drawCurveLine(Vector2d v1, Vector2d v2) {
		Point p1 = translateToPoint(v1.getX(), v1.getY());
		Point p2 = translateToPoint(v2.getX(), v2.getY());
		curveG.drawLine(p1.x, p1.y, p2.x, p2.y);
	}
	
	public void clearAxes() {
		axes = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		axesG = axes.getGraphics();
		axesG.setColor(axesColor);
	}
	
	public void redrawAxes(double xmin, double xmax, double ymin, double ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		
		clearAxes();
		
		int xAxisPos = translateYToPoint(0);
		axesG.drawLine(0, xAxisPos, height, xAxisPos);
		int yAxisPos = translateXToPoint(0);
		axesG.drawLine(yAxisPos, 0, yAxisPos, width);
		
		repaint();
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(axes, 0, 0, new Color(0, 0, 0, 0), this);
		g.drawImage(curve, 0, 0, new Color(0, 0, 0, 0), this);
	}
	
}
