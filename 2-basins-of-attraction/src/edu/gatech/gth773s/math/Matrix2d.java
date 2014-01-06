// Matrix2d.java

package edu.gatech.gth773s.math;

/**
 * Represents a 2x2 matrix.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class Matrix2d {
	
	/**
	 * Values in the positions (1,1) (1,2) (2,1) (2,2).
	 */
	protected double a, b, c, d;

	/**
	 * Create a new matrix with 0 values.
	 */
	public Matrix2d() {
		this(0, 0, 0, 0);
	}
	
	/**
	 * Create a new matrix with the given values.
	 */
	public Matrix2d(double a, double b, double c, double d) {
		setValues(a, b, c, d);
	}
	
	/**
	 * Establish the values of the numbers in the matrix.
	 */
	protected void setValues(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	/**
	 * Return the values of the numbers in the matrix.
	 */
	public double[] getValues() {
		double[] v = new double[4];
		v[0] = a;
		v[1] = b;
		v[2] = c;
		v[3] = d;
		return v;
	}
	
	/**
	 * Calculate the determinant of the matrix.
	 */
	public double det() {
		return(a*d)-(b*c);
	}
	
	/**
	 * Multiply the matrix by a scalar value.
	 */
	public Matrix2d multiply(double s) {
		return new Matrix2d(a*s, b*s, c*s, d*s);
	}
	
	/**
	 * Divide the matrix by a scalar value.
	 */
	public Matrix2d divide(double s) {
		return new Matrix2d(a/s, b/s, c/s, d/s);
	}
	
	/**
	 * Calculate the inverse of the matrix.
	 */
	public Matrix2d inverse() {
		return (new Matrix2d(d, -1*b, -1*c, a)).divide(det());
	}
	
	/**
	 * Apply the matrix to a vector.
	 */
	public Vector2d applyTo(Vector2d v) {
		return new Vector2d(
			a * v.getX() + b * v.getY(),
			c * v.getX() + d * v.getY()
		);
	}
	
}
