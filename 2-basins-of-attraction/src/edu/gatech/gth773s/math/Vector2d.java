// Vector2d.java

package edu.gatech.gth773s.math;

/**
 * Represents a vector in two dimensions.
 * 
 * This class contains a set of simple methods
 * to perform operations on and between vectors.
 * 
 * @author Christopher Martin
 * @version 1.1
 */
public class Vector2d {

	/** The x component of the vector */
	private double x;
	
	/** The y component of the vector */
	private double y;
	
	/**
	 * Construct a new 2-dimensional vector
	 * with the given numeric components.
	 */
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Construct a new 2-dimensional vector
	 * with the given numeric components.
	 */
	public Vector2d(double[] c) {
		if (c.length != 2)
			throw new IllegalArgumentException(
					"argument must be of size 2");
		x = c[0];
		y = c[1];
	}
	
	/** Return the x component of the vector */
	public double getX() {
		return x;
	}
	
	/** Return the y component of the vector */
	public double getY() {
		return y;
	}
	
	/** Convert this vector to a pair of doubles */
	public double[] toDouble() {
		double[] d = new double[2];
		d[0] = x;
		d[1] = y;
		return d;
	}
	
	/**
	 * Calculate the length of the vector.
	 */
	public double magnitude() {
		return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
	}

	/**
	 * Return a unit vector in the direction of this vector.
	 */
	public Vector2d unit() {
		double m = magnitude();
		return divide(m);
	}
	
	/**
	 * Return the distance between this vector and another vector.
	 */
	
	public double distance(Vector2d v) {
		return subtract(v).magnitude();
	}
	
	/**
	 * Add this vector to another vector.
	 */
	public Vector2d add(Vector2d v) {
		return new Vector2d(x+v.x, y+v.y);
	}
	
	/**
	 * Subtract another vector from this vector.
	 */
	public Vector2d subtract(Vector2d v) {
		return new Vector2d(x-v.x, y-v.y);
	}
	
	/**
	 * Calculate the dot-product of this vector with another.
	 */
	public Vector2d dot(Vector2d v) {
		return new Vector2d(x*v.x, y*v.y);
	}
	
	/**
	 * Multiply this vector by a scalar.
	 */
	public Vector2d multiply(double n) {
		return new Vector2d(x*n, y*n);
	}
	
	/**
	 * Divide this vector by a scalar.
	 */
	public Vector2d divide(double n) {
		if (n==0) // can't divide by zero
			throw new ArithmeticException("divide by zero");
		return new Vector2d(x/n, y/n);
	}
	
	/**
	 * Return a vector whose direction is perpendicular.
	 */
	public Vector2d perp() {
		return new Vector2d(-1*y, x);
	}

	/**
	 * Apply a 2x2 matrix to this vector.
	 */
	public Vector2d apply(Matrix2d m) {
		return m.applyTo(this);
	}
	
	/**
	 * Return a string representation of this vector
	 */
	public String toString() {
		return ("["+x+", "+y+"]");
	}
	
}