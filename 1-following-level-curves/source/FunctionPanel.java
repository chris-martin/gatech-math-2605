// FunctionPanel.java

import functionParser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * A panel containing a text field, wherein the
 * user can enter a function in two variables.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class FunctionPanel extends JPanel {
	
	private JTextField functionField;
	
	private FunctionParser parser;
	private Evaluator evaluator;
	
	private MainPanel controller;
	
	private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
	private static final Color VALID_TEXT_COLOR = Color.BLUE;
	private static final Color INVALID_TEXT_COLOR = Color.RED;

	private static final String DEFAULT_FUNCTION = "(x^2+y^2)^2-x^2+y^2";
	
	public FunctionPanel(MainPanel controller) {
		this.controller = controller;
		setOpaque(false);
		setBorder(BorderFactory.createMatteBorder(5, 20, 5, 20, new Color(0, 0, 0, 0)));
		setLayout(new BoxLayout(this, 0));
		add(new JLabel("f(x,y) = "));
		add(functionField = new JTextField(DEFAULT_FUNCTION));
		FunctionListener fListener = new FunctionListener(); 
		functionField.addActionListener(fListener);
		functionField.getDocument().addDocumentListener(fListener);
		setFunction(DEFAULT_FUNCTION);
	}
	
	private void setFunction(String func) {
		try {
			parser = new FunctionParser("x, y");
			evaluator = new Evaluator(2);
			parser.parse(func,evaluator);
			controller.setFunction(evaluator);
			functionField.setForeground(VALID_TEXT_COLOR);
		} catch (FunctionParsingException fpe) {
			functionField.setForeground(INVALID_TEXT_COLOR);
		}
	}
	
	private class FunctionListener implements ActionListener, DocumentListener {
	
		public void actionPerformed(ActionEvent e) {
			setFunction(functionField.getText());
		}
		
		public void changedUpdate(DocumentEvent e) {}

		public void insertUpdate(DocumentEvent e) {
			functionField.setForeground(DEFAULT_TEXT_COLOR);
		}

		public void removeUpdate(DocumentEvent e) {
			functionField.setForeground(DEFAULT_TEXT_COLOR);
		}
		
	}
	
}
