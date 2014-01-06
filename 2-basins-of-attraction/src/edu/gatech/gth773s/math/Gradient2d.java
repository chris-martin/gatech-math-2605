// Gradient2d.java

package edu.gatech.gth773s.math;

import functionParser.*;

/**
 * @author Christopher Martin
 * @version 1.0
 */
public class Gradient2d extends Vector2d {

	private enum Var {x,y}
	
	/**
	 * Calculates the gradient of a function at a position.
	 * @param e the Evaluator for the function
	 * @param x the position at which to calculate the gradient
	 * @param epsilon the epsilon used in the approximation
	 */
	public Gradient2d(Evaluator e, Vector2d x, double epsilon) {
		super(
			partialDerivative(e, x, epsilon, Var.x),
			partialDerivative(e, x, epsilon, Var.y)
		);
	}
	
	/**
	 * Calculates the partial derivative of a function
	 * at a point with respect to a particular variable
	 * @param e the Evaluator representing the function
	 * @param p the point at which to evaluate the partial
	 * @param v the variable with which to differentiate
	 */
	private static double partialDerivative(Evaluator e, Vector2d p, double epsilon, Var v) {
		double deriv;
		switch (v) {
			case x:
				deriv = ( e.evaluate(p.getX()+epsilon, p.getY()) - e.evaluate(p.getX()-epsilon, p.getY()) )
				 		/ (2*epsilon);
				break;
			case y:
				deriv = ( e.evaluate(p.getX(), p.getY()+epsilon) - e.evaluate(p.getX(), p.getY()-epsilon) )
		 				/ (2*epsilon);
				break;
			default:
				deriv = 0;
		}
		return deriv;
	}
	
}
