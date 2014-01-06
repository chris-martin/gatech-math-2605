// FunctionField.java

package edu.gatech.gth773s.gui.math;

import functionParser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * A text field, wherein the user can enter a function
 * in two variables.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class FunctionField extends JTextField {
	
	private FunctionParser parser;
	private Evaluator evaluator;
	
	private ActionListener actionListener;
	
	private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
	private static final Color VALID_TEXT_COLOR = Color.BLUE;
	private static final Color INVALID_TEXT_COLOR = Color.RED;
	
	public FunctionField(int cols) {
		super(cols);
		FunctionListener fListener = new FunctionListener(); 
		super.addActionListener(fListener);
		getDocument().addDocumentListener(fListener);
	}
	
	public FunctionField(int cols, String function) {
		this(cols);
		setText(function);
		setFunction(function);
	}
	
	public void addActionListener(ActionListener a) {
		actionListener = a;
	}
	
	private void setFunction(String func) {
		try {
			parser = new FunctionParser("x, y");
			evaluator = new Evaluator(2);
			parser.parse(func,evaluator);
			if (actionListener != null)
				actionListener.actionPerformed(new ActionEvent(this, 0, "Update function"));
			setForeground(VALID_TEXT_COLOR);
		} catch (FunctionParsingException e) {
			setForeground(INVALID_TEXT_COLOR);
		}
	}
	
	public Evaluator getEvaluator() {
		return evaluator;
	}
	
	private class FunctionListener implements ActionListener, DocumentListener {
	
		public void actionPerformed(ActionEvent e) {
			setFunction(getText());
		}
		
		public void changedUpdate(DocumentEvent e) {}

		public void insertUpdate(DocumentEvent e) {
			setForeground(DEFAULT_TEXT_COLOR);
		}

		public void removeUpdate(DocumentEvent e) {
			setForeground(DEFAULT_TEXT_COLOR);
		}
		
	}
	
}
