// Jacobian2d.java

package edu.gatech.gth773s.math;

import functionParser.Evaluator;

/**
 * @author Christopher Martin
 * @version 1.0
 */
public class Jacobian2d extends Matrix2d {

	public Jacobian2d(Evaluator f, Evaluator g, Vector2d x, double epsilon) {
		Vector2d gradF = new Gradient2d(f, x, epsilon); 
		Vector2d gradG = new Gradient2d(g, x, epsilon);
		setValues(gradF.getX(), gradF.getY(), gradG.getX(), gradG.getY());		
	}
	
}
