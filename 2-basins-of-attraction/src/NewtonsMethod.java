// NewtonsMethod.java

import functionParser.*;
import edu.gatech.gth773s.math.*;

/**
 * @author Christopher Martin
 * @version 1.0
 */
public class NewtonsMethod {

	private Evaluator functionF, functionG;
	private Vector2d positionX;
	
	private static final double DERIVATIVE_EPSILON = Math.pow(10,-6);
	
	public NewtonsMethod(Evaluator e1, Evaluator e2) {
		functionF = e1;
		functionG = e2;
	}
	
	public void setPosition(Vector2d v) {
		positionX = v;
	}
	
	public Vector2d next() {
		positionX = positionX.subtract(evaluateJacobian().inverse().applyTo(evaluateFunction()));
		return positionX;
	}
	
	private Vector2d evaluateFunction() {
		return new Vector2d(
			functionF.evaluate(positionX.getX(), positionX.getY()),
			functionG.evaluate(positionX.getX(), positionX.getY())
		);
	}
	
	private Jacobian2d evaluateJacobian() {
		return new Jacobian2d(functionF, functionG, positionX, DERIVATIVE_EPSILON);
	}
	
}
