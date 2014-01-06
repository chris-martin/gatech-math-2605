// OptionsPanel.java

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * The panel allowing a user to change parameters.
 * 
 * @author Christopher Martin
 * @version 1.0
 */
public class OptionsPanel extends JPanel {
	
	private double xmin, xmax, ymin, ymax;
	private double xnotX, xnotY, epsilon;
	private int N;
	private double gradientEpsilon;
	private boolean useCorrections;
	
	private JFormattedTextField
			xminField, xmaxField, yminField, ymaxField,
			xnotXField, xnotYField, epsilonField, NField,
			gradientEpsilonField;
	private JCheckBox useCorrectionsCheck;
	
	private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
	//private static final Color VALID_TEXT_COLOR = Color.BLUE;
	private static final Color INVALID_TEXT_COLOR = Color.RED;

	private MainPanel controller;
	
	private static final double DEFAULT_xminField = -1.5;
	private static final double DEFAULT_xmaxField = 1.5;
	private static final double DEFAULT_yminField = -1.5;
	private static final double DEFAULT_ymaxField = 1.5;
	private static final double DEFAULT_XNOT_X = 1;
	private static final double DEFAULT_XNOT_Y = 0;
	private static final double DEFAULT_EPSILON = Math.pow(10,-3);
	private static final int DEFAULT_N = (int) Math.pow(10,4);
	private static final double DEFAULT_GRADIENT_EPSILON = Math.pow(10,-5);
	private static final boolean DEFAULT_USE_CORRECTIONS = false;
	
	public OptionsPanel(MainPanel controller) {
		this.controller = controller;
		
		setOpaque(false);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		GridBagConstraints c;
		
		JPanel tableWindow = new JPanel();
		tableWindow.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		c.gridy = 0;
		
		c.gridx = GridBagConstraints.RELATIVE;
		tableWindow.add(new JLabel("x-min: ", JLabel.TRAILING), c);
		c.gridx = GridBagConstraints.RELATIVE;
		xminField = new JFormattedTextField(new DecimalFormat("#.###"));
		xminField.setColumns(4);
		xminField.setValue(DEFAULT_xminField);
		c.gridx = GridBagConstraints.RELATIVE;
		tableWindow.add(xminField, c);
		
		c.gridx = GridBagConstraints.RELATIVE;
		tableWindow.add(new JLabel("   x-max: ", JLabel.TRAILING), c);
		c.gridx = GridBagConstraints.RELATIVE;
		xmaxField = new JFormattedTextField(new DecimalFormat("#.###"));
		xmaxField.setColumns(4);
		xmaxField.setValue(DEFAULT_xmaxField);
		tableWindow.add(xmaxField, c);
		
		c.gridy = 1;
		
		c.gridx = GridBagConstraints.RELATIVE;
		tableWindow.add(new JLabel("y-min: ", JLabel.TRAILING), c);
		c.gridx = GridBagConstraints.RELATIVE;
		yminField = new JFormattedTextField(new DecimalFormat("#.###"));
		yminField.setColumns(4);
		yminField.setValue(DEFAULT_yminField);
		tableWindow.add(yminField, c);
		
		c.gridx = GridBagConstraints.RELATIVE;
		tableWindow.add(new JLabel("   y-max: ", JLabel.TRAILING), c);
		c.gridx = GridBagConstraints.RELATIVE;
		ymaxField = new JFormattedTextField(new DecimalFormat("#.###"));
		ymaxField.setColumns(4);
		ymaxField.setValue(DEFAULT_ymaxField);
		tableWindow.add(ymaxField, c);
		
		JPanel tableCorrections = new JPanel();
		tableCorrections.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = GridBagConstraints.RELATIVE;
		useCorrectionsCheck = new JCheckBox("Use corrections",DEFAULT_USE_CORRECTIONS);
		tableCorrections.add(useCorrectionsCheck);
//		c.gridx = GridBagConstraints.RELATIVE;
//		tableWindow.add(new JLabel(" "), c);
		
		JPanel tableXnot = new JPanel();
		tableXnot.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = GridBagConstraints.RELATIVE;
		tableXnot.add(new JLabel("X(0) = ("));
		c.gridx = GridBagConstraints.RELATIVE;
		xnotXField = new JFormattedTextField(new DecimalFormat("#.###"));
		xnotXField.setColumns(3);
		xnotXField.setValue(DEFAULT_XNOT_X);
		tableXnot.add(xnotXField);
		c.gridx = GridBagConstraints.RELATIVE;
		tableXnot.add(new JLabel(", "));
		c.gridx = GridBagConstraints.RELATIVE;
		xnotYField = new JFormattedTextField(new DecimalFormat("#.###"));
		xnotYField.setColumns(3);
		xnotYField.setValue(DEFAULT_XNOT_Y);
		tableXnot.add(xnotYField);
		c.gridx = GridBagConstraints.RELATIVE;
		tableXnot.add(new JLabel(")"));
		
		JPanel tableParams = new JPanel();
		tableParams.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = GridBagConstraints.RELATIVE;
		tableParams.add(new JLabel("epsilon = ", JLabel.TRAILING), c);
		c.gridx = GridBagConstraints.RELATIVE;
		epsilonField = new JFormattedTextField(new DecimalFormat("0.E0"));
		epsilonField.setColumns(4);
		epsilonField.setValue(DEFAULT_EPSILON);
		tableParams.add(epsilonField, c);
		c.gridy = 1;
		c.gridx = GridBagConstraints.RELATIVE;
		tableParams.add(new JLabel("N = ", JLabel.TRAILING), c);
		c.gridx = GridBagConstraints.RELATIVE;
		NField = new JFormattedTextField(new DecimalFormat("0.E0"));
		NField.setColumns(4);
		NField.setValue(DEFAULT_N);
		tableParams.add(NField, c);

		JPanel tableMisc = new JPanel();
		tableMisc.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = GridBagConstraints.RELATIVE;
		tableMisc.add(new JLabel("epsilon for gradients = ", JLabel.TRAILING), c);
		c.gridx = GridBagConstraints.RELATIVE;
		gradientEpsilonField = new JFormattedTextField(new DecimalFormat("0.E0"));
		gradientEpsilonField.setColumns(4);
		gradientEpsilonField.setValue(DEFAULT_GRADIENT_EPSILON);
		tableMisc.add(gradientEpsilonField, c);
		
		add(Box.createVerticalGlue());
		add(tableWindow);
		add(tableCorrections);
		add(tableParams);
		add(tableXnot);
		add(tableMisc);
		add(Box.createVerticalGlue());
		
		addOptionsFieldListener(xminField, xmaxField, yminField, ymaxField,
							xnotXField, xnotYField, epsilonField, NField,
							gradientEpsilonField);
		addOptionsListener(useCorrectionsCheck);
		
		submitData();
		
	}
	
	private void addOptionsFieldListener(JFormattedTextField... fields) {
		for (JFormattedTextField f : fields) {
			OptionsFieldListener listener = new OptionsFieldListener(f);
			f.addActionListener(listener);
			f.addFocusListener(listener);
			f.getDocument().addDocumentListener(listener);
		}
	}
	
	private void addOptionsListener(JCheckBox... boxes) {
		for (JCheckBox b : boxes) {
			OptionsCheckListener listener = new OptionsCheckListener(b);
			b.addActionListener(listener);
		}
	}
	
	public void setXnot(double[] vars) {
		xnotXField.setValue(vars[0]);
		xnotYField.setValue(vars[1]);
		submitData();
	}
	
	private boolean verifyInput() {
		if ( xmin >= xmax ) {
			xminField.setForeground(INVALID_TEXT_COLOR);
			xmaxField.setForeground(INVALID_TEXT_COLOR);
			return false;
		}
		if ( ymin >= ymax ) {
			yminField.setForeground(INVALID_TEXT_COLOR);
			ymaxField.setForeground(INVALID_TEXT_COLOR);
			return false;
		}
		xminField.setForeground(DEFAULT_TEXT_COLOR);
		xmaxField.setForeground(DEFAULT_TEXT_COLOR);
		yminField.setForeground(DEFAULT_TEXT_COLOR);
		ymaxField.setForeground(DEFAULT_TEXT_COLOR);
		xnotXField.setForeground(DEFAULT_TEXT_COLOR);
		xnotYField.setForeground(DEFAULT_TEXT_COLOR);
		epsilonField.setForeground(DEFAULT_TEXT_COLOR);
		NField.setForeground(DEFAULT_TEXT_COLOR);
		gradientEpsilonField.setForeground(DEFAULT_TEXT_COLOR);
		return true;
	}
	
	private void convertFieldsToData() {
		xmin = ((Number)(xminField.getValue())).doubleValue();
		xmax = ((Number)(xmaxField.getValue())).doubleValue();
		ymin = ((Number)(yminField.getValue())).doubleValue();
		ymax = ((Number)(ymaxField.getValue())).doubleValue();
		xnotX = ((Number)(xnotXField.getValue())).doubleValue();
		xnotY = ((Number)(xnotYField.getValue())).doubleValue();
		epsilon = ((Number)(epsilonField.getValue())).doubleValue();
		N = ((Number)(NField.getValue())).intValue();
		gradientEpsilon = ((Number)(gradientEpsilonField.getValue())).doubleValue();
		useCorrections = useCorrectionsCheck.isSelected();
	}
	
	private void updateFields() {
		convertFieldsToData();
		verifyInput();
	}
	
	private void submitData() {
		convertFieldsToData();
		if (verifyInput()) {
			controller.setParameters(xmin, xmax, ymin, ymax, xnotX, xnotY,
					epsilon, N, gradientEpsilon, useCorrections);
		}
	}
	
	private class OptionsFieldListener
	implements ActionListener, FocusListener, DocumentListener {

		public OptionsFieldListener(JFormattedTextField field) {}
		
		public void actionPerformed(ActionEvent e) {
			submitData();
		}
		
		public void focusLost(FocusEvent e) {
			updateFields();
		}
		
		public void focusGained(FocusEvent e) {}
		
		public void changedUpdate(DocumentEvent e) {}
		
		public void insertUpdate(DocumentEvent e) {
			updateFields();
		}
		
		public void removeUpdate(DocumentEvent e) {
			updateFields();
		}
		
	}
	
	private class OptionsCheckListener implements ActionListener {
		
		public OptionsCheckListener(JCheckBox box) {}
		
		public void actionPerformed(ActionEvent e) {
			submitData();
		}
		
	}
	
}
