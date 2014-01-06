// Vector.java

package edu.gatech.gth773s.math;

public class Vector {
	
	protected double[] values;
	
	/**
	 * Create a zero-filled vector of the
	 * specified dimension.
	 */
	public Vector(int dimension) {
		values = new double[dimension];
	}
	
	public Vector(double[] values) {
		this.values = values;
	}
	
	public int dimension() {
		return values.length;
	}
	
	public double[] getValueArray() {
		return values;
	}
	
	/**
	 * Calculate the length of the vector.
	 */
	public double magnitude() {
		double squared = 0;
		for (double d : values)
			squared += Math.pow(d,2);
		return Math.sqrt(squared);
	}
	
	/**
	 * Return a unit vector in the direction
	 * of this vector.
	 */
	public Vector unit() {
		double m = magnitude();
		if (m == 0)
			throw new ArithmeticException(
				"Zero vector cannot be normalized");
		return divide(m);
	}
	

	/**
	 * Return the distance between this vector
	 * and another vector.
	 */
	public double distance(Vector v) {
		return minus(v).magnitude();
	}
	
	/**
	 * Add this vector to another vector.
	 */
	public Vector plus(Vector v) {
		if (values.length != v.values.length)
			throw new DimensionMismatchException(
				"Dimension mismatch for vector subtraction");
		Vector ret = new Vector(values.length);
		for (int i=values.length-1; i>=0; i--)
			ret.values[i] = values[i] + v.values[i];
		return ret;
	}
	
	/**
	 * Add this vector to another vector.
	 */
	public void plusEquals(Vector v) {
		if (values.length != v.values.length)
			throw new DimensionMismatchException(
				"Dimension mismatch for vector subtraction");
		for (int i=values.length-1; i>=0; i--)
			values[i] += v.values[i];
	}
	
	/**
	 * Subtract another vector from this vector.
	 */
	public Vector minus(Vector v) {
		if (values.length != v.values.length)
			throw new DimensionMismatchException(
				"Dimension mismatch for vector subtraction");
		Vector ret = new Vector(values.length);
		for (int i=values.length-1; i>=0; i--)
			ret.values[i] = values[i] - v.values[i];
		return ret;
	}
	
	/**
	 * Subtract another vector from this vector.
	 */
	public void minusEquals(Vector v) {
		if (values.length != v.values.length)
			throw new DimensionMismatchException(
				"Dimension mismatch for vector subtraction");
		for (int i=values.length-1; i>=0; i--)
			values[i] -= v.values[i];
	}
	
	/**
	 * Multiply this vector by a scalar.
	 */
	public Vector times(double s) {
		Vector v = new Vector(values.length);
		for (int i=values.length-1; i>=0; i--)
			v.values[i] = values[i]*s;
		return v;
	}
	
	/**
	 * Multiply this vector by a scalar.
	 */
	public void timesEquals(double s) {
		for (int i=values.length-1; i>=0; i--)
			values[i] *= s;
	}
	
	/**
	 * Divide this vector by a scalar.
	 */
	public Vector divide(double s) {
		if (s==0) // can't divide by zero
			throw new ArithmeticException("divide by zero");
		Vector v = new Vector(values.length);
		for (int i=values.length-1; i>=0; i--)
			v.values[i] = values[i]/s;
		return v;
	}
	
	/**
	 * Divide this vector by a scalar.
	 */
	public void divideEquals(double s) {
		if (s==0) // can't divide by zero
			throw new ArithmeticException("divide by zero");
		for (int i=values.length-1; i>=0; i--)
			values[i] /= s;
	}
	
	/**
	 * Calculate the dot-product of this vector with another.
	 */
	public double dot(Vector v) {
		if (values.length != v.values.length)
			throw new DimensionMismatchException(
				"Dimension mismatch for vector dot-product");
		double ret = 0;
		for (int i=values.length-1; i>=0; i--)
			ret += values[i]*v.values[i];
		return ret;
	}
	
	/**
	 * Apply a 2x2 matrix to this vector.
	 */
	public Vector apply(Matrix m) {
		return m.applyTo(this);
	}
	
	/**
	 * Return a string representation of this vector's entries.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		for (int i=0; i<values.length; i++)
			str.append(values[i]+",");
		str.setCharAt(str.length()-1, ']');
		return str.toString();
	}
	
}
