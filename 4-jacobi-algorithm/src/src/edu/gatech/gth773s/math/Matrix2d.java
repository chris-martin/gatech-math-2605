// Matrix2d.java

package edu.gatech.gth773s.math;

/**
 * Represents a 2x2 matrix.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class Matrix2d extends Matrix {
	
	/**
	 * Create a new matrix with 0 values.
	 */
	public Matrix2d() {
		super(2, 2);
	}
	
	/**
	 * Create a new matrix with the given values.
	 */
	public Matrix2d(double a, double b, double c, double d) {
		this();
		setValues(a, b, c, d);
	}
	
	/**
	 * Create a new matrix from an array of values.
	 */
	public Matrix2d(double[] values) {
		this();
		setValues(values[0], values[1], values[2], values[3]);
	}
	
	public Matrix2d(Matrix m) {
		super(m, 2, 2);
	}
	
	/**
	 * Establish the values of the numbers in the matrix.
	 */
	protected void setValues(double a, double b, double c, double d) {
		entries[0][0] = a;
		entries[0][1] = b;
		entries[1][0] = c;
		entries[1][1] = d;
	}
	
	/**
	 * Return the values of the numbers in the matrix.
	 */
	public double[] getValueArray() {
		double[] v = new double[4];
		v[0] = entries[0][0];
		v[1] = entries[0][1];
		v[2] = entries[1][0];
		v[3] = entries[1][1];
		return v;
	}

	public boolean isSymmetric(double epsilon) {
		return (Math.abs(entries[0][1]-entries[1][0])<epsilon);
	}
	
	public Matrix2d times(Matrix2d B) {
		return new Matrix2d(super.times(B));
	}
	
	public Matrix2d times(double s) {
		return new Matrix2d(super.times(s));
	}
	
	public Matrix2d plus(Matrix2d B) {
		return new Matrix2d(super.plus(B));
	}
	
	public Matrix2d plus(Matrix B) {
		return plus(new Matrix2d(B));
	}
	
	public Matrix2d minus(Matrix2d B) {
		return new Matrix2d(super.minus(B));
	}
	
	public Matrix2d minus(Matrix B) {
		return minus(new Matrix2d(B));
	}
	
	/**
	 * Calculate the determinant of the matrix. (ad-bc)
	 */
	public double det() {
		return (entries[0][0]*entries[1][1])-(entries[0][1]*entries[1][0]);
	}
		
	/**
	 * Calculate the inverse of the matrix. ([[d,-b],[-c,a]])
	 */
	public Matrix2d inverse() {
		return (new Matrix2d(entries[1][1], -1*entries[0][1], -1*entries[1][0], entries[0][0])).times(det());
	}
	
}
