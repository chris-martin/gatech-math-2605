// Vector2d.java

package edu.gatech.gth773s.math;

/**
 * Represents a vector in two dimensions.
 * 
 * @author Christopher Martin
 * @version 1.1
 */
public class Vector2d extends Vector {
	
	/**
	 * Construct a new 2-dimensional vector
	 * with the given numeric components.
	 */
	public Vector2d(double x, double y) {
		super(2);
		values[0] = x;
		values[1] = y;
	}
	
	/**
	 * Construct a new 2-dimensional vector
	 * with the given numeric components.
	 */
	public Vector2d(double[] c) {
		super(c);
		if (c.length != 2)
			throw new IllegalArgumentException(
					"argument must be of size 2");
	}
	
	/** Return the x component of the vector */
	public double getX() {
		return values[0];
	}
	
	/** Return the y component of the vector */
	public double getY() {
		return values[1];
	}
	
	/**
	 * Return a vector whose direction is perpendicular.
	 */
	public Vector2d perp() {
		return new Vector2d(-1*values[1], values[0]);
	}

	public double dYdX() {
		return values[1]/values[0];
	}
	
}