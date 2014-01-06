// Math2d.java

package edu.gatech.gth773s.math;

import functionParser.Evaluator;

public abstract class Math2d {

	enum Var {x, y}
	
	/**
	 * Calculates the gradient of a function at a position.
	 * @param e the Evaluator for the function
	 * @param x the position at which to calculate the gradient
	 * @param epsilon the epsilon used in the approximation
	 */
	public static Vector2d createGradient(Evaluator e, Vector2d x, double epsilon) {
		return new Vector2d(
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
	public static double partialDerivative(Evaluator e, Vector2d p, double epsilon, Var v) {
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
	
	public static Matrix2d createJacobian(Evaluator f, Evaluator g, Vector2d x, double epsilon) {
		Vector2d gradF = createGradient(f, x, epsilon); 
		Vector2d gradG = createGradient(g, x, epsilon);
		return new Matrix2d(
			gradF.getX(), gradF.getY(),
			gradG.getX(), gradG.getY()
		);		
	}
	
	public static Matrix2d createHessian(Evaluator f, Vector2d x, double epsilon) {
		throw new UnsupportedOperationException();
		//return new Matrix2d(...);
	}
	
}
