// DisplayPanel.java

package edu.gatech.gth773s.gui.math;

import edu.gatech.gth773s.math.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Christopher Martin
 * @version 1.0
 */
public class DisplayPanel2d extends JPanel {
	
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

	private Insets insets;
	
	private Dimension size;
	private double xmin, xmax, ymin, ymax;

	private ArrayList<Object> imageIDs;
	private Hashtable<Object, Img> images;
	
	public DisplayPanel2d(Dimension size) {
		this.size = size;
		insets = getInsets();
		calculateSize();
		setBackground(DEFAULT_BACKGROUND_COLOR);
		removeAll();
	}

	public DisplayPanel2d(Dimension size, MouseListener ml) {
		this(size);
		this.addMouseListener(ml);
	}
	
	public void setWindow(double xmin, double xmax, double ymin, double ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}
	
	private void calculateSize() {
		setPreferredSize(new Dimension(
				size.width+insets.left+insets.right,
				size.height+insets.top+insets.bottom
		));
	}
	
	public void setBorder(Border b) {
		super.setBorder(b);
		/* Don't do this when super()
		 * constructor calls setBorder */
		if (size != null) {
			insets = getInsets();
			calculateSize();
		}
	}
	
	public void addImageAbove(Object o) {
		if (imageIDs.contains(o))
			throw new IllegalArgumentException("Image already exists");
		imageIDs.add(o);
		images.put(o, new Img());
	}
	
	public void clearImage(Object key) {
		if (!(imageIDs.contains(key)))
			throw new IllegalArgumentException("Image does not exist");
		images.put(key, new Img());
		repaint();
	}
	
	public void removeImage(Object key) {
		if (!(imageIDs.contains(key)))
			throw new IllegalArgumentException("Image does not exist");
		images.remove(key);
	}

	public void clearAll() {
		for (Object key : images.keySet())
			images.put(key, new Img());
	}
	
	public void removeAll() {
		imageIDs = new ArrayList<Object>();
		images = new Hashtable<Object, Img>();
		repaint();
	}
	
	public void setColor(Color c, Object key) {
		Graphics g = images.get(key).getGraphics();
		g.setColor(c);
	}
	
	public void drawPoint(Point point, Object key) {
		Graphics g = images.get(key).graphics;
		g.drawLine(point.x, point.y, point.x, point.y);
	}
	
	public void drawPoint(Vector2d point, Object key) {
		drawPoint(translateToPoint(point), key);
	}

	public void drawLine(Point p1, Point p2, Object key) {
		Graphics g = images.get(key).graphics;
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}
	
	public void drawLine(Vector2d p1, Vector2d p2, Object key) {
		drawLine(translateToPoint(p1), translateToPoint(p2), key);
	}
	
	public int translateXToPoint(double x) {
		return (int) (((x-xmin)/(xmax-xmin))*size.width+.5);
	}
	
	public int translateYToPoint(double y) {
		return (int) (size.height-(((y-ymin)/(ymax-ymin))*size.height+.5));
	}
	
	public Point translateToPoint(Vector2d point) {
		return new Point(
				translateXToPoint(point.getX()),
				translateYToPoint(point.getY())
		);
	}
	
	public double translateXFromPoint(double x) {
		return ( ( (x-.5)*(xmax-xmin) ) / ((double)size.width) ) + xmin;
	}
	
	public double translateYFromPoint(double y) {
		return -( ( (y-.5)*(ymax-ymin) ) / ((double)size.height) ) + ymax;
	}
	
	public Vector2d translateFromPoint(Point p) {
		return new Vector2d(
			translateXFromPoint(p.getX()),
			translateYFromPoint(p.getY())
		);
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Object id : imageIDs)
			g.drawImage(images.get(id).image, insets.left, insets.top,
					new Color(0, 0, 0, 0), this);
	}
	
	private class Img {
		private BufferedImage image;
		private Graphics graphics;
		public Img() {
			image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
			graphics = image.getGraphics();
		}
		public Graphics getGraphics() {
			return graphics;
		}
	}
	
}
