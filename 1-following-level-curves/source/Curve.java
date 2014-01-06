// Curve.java

import functionParser.*;

/**
 * Performs the calculations to iteratively find
 * points on the graph of a level curve.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class Curve {

	private Evaluator evaluator;
	
	/** Initial position */
	private Vector2d xnot;
	
	/** Number of iterations */
	private int N;
	
	/** Step size */
	private double epsilon;
	
	/** epsilon for calculating partial derivatives */
	private double gradientEpsilon;
	
	/** Whether a corrective measure is employed at each step */
	private boolean useCorrections;
	
	private DisplayPanel display;
	
	private enum Var {x,y}

	private boolean paramsSet = false;
	private boolean evaluatorSet = false;
	
	public Curve(DisplayPanel display) {
		this.display = display;
	}
	
	public void update(double xnotX, double xnotY, double epsilon,
			int N, double gradientEpsilon, boolean useCorrections) {
		this.xnot = new Vector2d(xnotX, xnotY);
		this.epsilon = epsilon;
		this.N = N;
		this.gradientEpsilon = gradientEpsilon;
		this.useCorrections = useCorrections;
		paramsSet = true;
		redraw();
	}
	
	public void update(Evaluator evaluator) {
		this.evaluator = evaluator;
		evaluatorSet = true;
		redraw();
	}

	private void redraw() {
		// Can't do anything without all the data
		if (!(paramsSet && evaluatorSet)) return;

		// Clear the existing curve
		display.clearCurve();

		// Initialize x1 at starting value
		Vector2d x1 = xnot;

		// Calculate the height of this level curve (used for making corrections)
		double altitude = evaluator.evaluate(x1.getX(), x1.getY());
		
		for (int n=0; n<N; n++) {
			
			// Define x2: epsilon distance along the tangent line from x1
			Vector2d gradient = calculateGradient(x1);
			double gradientMagnitude = gradient.magnitude();
			Vector2d scaledGradient;
			try {
				scaledGradient = gradient.divide(gradientMagnitude).multiply(epsilon);
			} catch (ArithmeticException e) { // attempted division by zero
				break; // gradient equals zero - there's nowhere else to go
			}
			Vector2d x2 = x1.add(scaledGradient.perp());
			
			// If corrections are turned on, adjust x2 accordingly
			if (useCorrections) {
				double altitudeShift = altitude - evaluator.evaluate(x2.getX(), x2.getY());
				Vector2d p2Gradient = calculateGradient(x2);
				x2 = x2.add(p2Gradient.multiply(altitudeShift/Math.pow(p2Gradient.magnitude(),2)));
			}
			
			// Draw a line connecting the two points
			display.drawCurveLine(x1, x2);
			
			// Set x2 as the new x1
			x1 = x2;

		}
		
	}
	
	private double partialDerivative(double x, double y, Var variable) {
		double deriv;
		switch (variable) {
			case x:
				deriv = ( evaluator.evaluate(x+gradientEpsilon, y) - evaluator.evaluate(x-gradientEpsilon, y) )
				 		/ (2*gradientEpsilon);
				break;
			case y:
				deriv = ( evaluator.evaluate(x, y+gradientEpsilon) - evaluator.evaluate(x, y-gradientEpsilon) )
		 				/ (2*gradientEpsilon);
				break;
			default:
				deriv = 0;
		}
		return deriv;
	}

	/**
	 * Calculate the gradient at a position.
	 */
	private Vector2d calculateGradient(Vector2d v) {
		return new Vector2d( partialDerivative(v.getX(), v.getY(), Var.x),
		                     partialDerivative(v.getX(), v.getY(), Var.y));
	}
	
}
