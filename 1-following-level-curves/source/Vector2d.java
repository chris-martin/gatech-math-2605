// Vector2d.java

/**
 * Represents a vector in two dimensions.
 * 
 * This class contains a set of simple methods
 * to perform operations on and between vectors.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
class Vector2d {
	
	private double x;
	public double getX() { return x; }
	
	private double y;
	public double getY() { return y; }
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Calculate the length of the vector.
	 */
	public double magnitude() {
		return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
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
	
}