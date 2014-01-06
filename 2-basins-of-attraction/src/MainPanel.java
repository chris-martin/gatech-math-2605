// MainPanel.java

import edu.gatech.gth773s.gui.*;
import edu.gatech.gth773s.gui.math.*;
import edu.gatech.gth773s.math.*;
import functionParser.Evaluator;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Christopher Martin
 * @version 1.0
 */
public class MainPanel extends JPanel implements ActionListener {

	private JPanel statusPanel;
	private DisplayPanel2d displayPanel;
	private OptionsPanel functionsPanel;
	private OptionsPanel optionsPanel;

	private Dimension displaySize;
	
	private static final Color BACKGROUND_COLOR = new Color(220, 230, 240);
	private static final Border BORDER =
		BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createLineBorder(BACKGROUND_COLOR.darker(), 1)
		);
	
	private static final int MAX_ZEROS = 500;
	
	/** Functions */
	private FunctionField fField, gField;
	
	//private static final String DEFAULT_FUNCTION_F = "x^3*y-4*x^2-y^3+3";
	//private static final String DEFAULT_FUNCTION_G = "x^2+y^3-4*x-2*y-2";
	
	//private static final String DEFAULT_FUNCTION_F = "x^3*y-4*x^2-y^3+3";
	//private static final String DEFAULT_FUNCTION_G = "x^2+y^4-5*x*y^3";
	
	private static final String DEFAULT_FUNCTION_F = "x^3*y-4*x^2-y^2+3";
	private static final String DEFAULT_FUNCTION_G = "x^2+y^3-4*x-2*y-2";
	
	//private static final String DEFAULT_FUNCTION_F = "x^4+x^2-1";
	//private static final String DEFAULT_FUNCTION_G = "y^2-x^2";
	
	private static final int FUNCTION_INPUT_COLS = 20;
	private Evaluator functionF, functionG;
	
	/** Window range */
	private JFormattedTextField xminField, xmaxField, yminField, ymaxField;
	private static final double DEFAULT_XMIN = -6;
	private static final double DEFAULT_XMAX = 6;
	private static final double DEFAULT_YMIN = -6;
	private static final double DEFAULT_YMAX = 6;
	private static final int WINDOW_RANGE_INPUT_COLS = 4;
	private static final NumberFormat WINDOW_RANGE_INPUT_FORMAT = new DecimalFormat("#.###");
	private double xmin, xmax, ymin, ymax;
	
	/** Changing the window range */
	private JButton zoomInButton, zoomOutButton;
	private static final double ZOOM_SCALE = 1.5;
	
	/** Whether coloring should be altered based on step count */
	private JCheckBox stepColoringField;
	private static final boolean DEFAULT_STEPCOLORING = true;
	private boolean stepColoring;
	
	private static final float BRIGHTNESS_MEAN = .6f;
	private static final float BRIGHTNESS_RANGE = .4f;
	
	/** Decimal precision for calculating zeros */
	private JFormattedTextField decimalPrecisionField;
	private static final double DEFAULT_DECIMAL_PRECISION = 8;
	private static final int DECIMAL_PRECISION_INPUT_COLS = 4;
	private static final NumberFormat DECIMAL_PRECISION_INPUT_FORMAT = new DecimalFormat("#");
	private int decimalPrecision;
	private double epsilon;
	
	/** Maximum number of steps before algorithm terminates */
	private JFormattedTextField maxStepsField;
	private static final double DEFAULT_MAX_STEPS = 64;
	private static final int MAX_STEPS_INPUT_COLS = 4;
	private static final NumberFormat MAX_STEPS_INPUT_FORMAT = new DecimalFormat("#");
	private int maxSteps;
	
	public MainPanel(Dimension windowSize, Dimension displaySize) {
		setPreferredSize(windowSize);
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOR);
		setBorder(new CompoundBorder(
				BORDER,
				new MatteBorder(25, 25, 25, 25, BACKGROUND_COLOR)
		));
		
		this.displaySize = displaySize; 
		
		createFunctionsPanel();
		createOptionsPanel();
		createDisplayPanel();
		createStatusPanel();

		add(new FixedSizePanel(displayPanel), BorderLayout.CENTER);
		add(functionsPanel, BorderLayout.SOUTH);
		add(optionsPanel, BorderLayout.EAST);
		add(statusPanel, BorderLayout.WEST);
		
		processNewton();
		
	}
	
	private void createDisplayPanel() {
		displayPanel = new DisplayPanel2d(displaySize);
		displayPanel.setBackground(BACKGROUND_COLOR);
		displayPanel.setBorder(BORDER);
	}
	
	private void createFunctionsPanel() {
		
		functionsPanel = new OptionsPanel();
		functionsPanel.setOpaque(false);
		functionsPanel.addActionListener(this);
		
		functionsPanel.addLabel("f(x,y) = ");
		functionsPanel.add(fField = new FunctionField(FUNCTION_INPUT_COLS, DEFAULT_FUNCTION_F));
		fField.addActionListener(functionsPanel);
		
		functionsPanel.nextRow();
		
		functionsPanel.addLabel("g(x,y) = ");
		functionsPanel.add(gField = new FunctionField(FUNCTION_INPUT_COLS, DEFAULT_FUNCTION_G));
		gField.addActionListener(functionsPanel);
		
		retrieveFunctions();
		
	}
	
	private void createOptionsPanel() {
		
		optionsPanel = new OptionsPanel();
		optionsPanel.setOpaque(false);
		optionsPanel.addActionListener(this);
		
		optionsPanel.nextCol();
		optionsPanel.addLabel("min");
		optionsPanel.addLabel("max");
		
		optionsPanel.nextRow();
		
		optionsPanel.addLabel(" x ");
		xminField = optionsPanel.addFormattedTextField(
				WINDOW_RANGE_INPUT_COLS,
				WINDOW_RANGE_INPUT_FORMAT,
				DEFAULT_XMIN);
		xmaxField = optionsPanel.addFormattedTextField(
				WINDOW_RANGE_INPUT_COLS,
				WINDOW_RANGE_INPUT_FORMAT,
				DEFAULT_XMAX);
		
		optionsPanel.nextRow();
		
		optionsPanel.addLabel(" y ");
		yminField = optionsPanel.addFormattedTextField(
				WINDOW_RANGE_INPUT_COLS,
				WINDOW_RANGE_INPUT_FORMAT,
				DEFAULT_YMIN);
		ymaxField = optionsPanel.addFormattedTextField(
				WINDOW_RANGE_INPUT_COLS,
				WINDOW_RANGE_INPUT_FORMAT,
				DEFAULT_YMAX);
		
		optionsPanel.nextSection();
		
		optionsPanel.addLabel("Zoom: ");
		zoomInButton = optionsPanel.addButton("in");
		zoomOutButton = optionsPanel.addButton("out");
		
		optionsPanel.nextSection();
		
		stepColoringField = optionsPanel.addCheckbox(DEFAULT_STEPCOLORING);
		optionsPanel.addLabel("Step-count coloring"); 
		
		optionsPanel.nextSection();
		
		optionsPanel.addLabel("Decimal precision ");
		decimalPrecisionField = optionsPanel.addFormattedTextField(
				DECIMAL_PRECISION_INPUT_COLS,
				DECIMAL_PRECISION_INPUT_FORMAT,
				DEFAULT_DECIMAL_PRECISION);
		
		optionsPanel.nextRow();
		
		optionsPanel.addLabel("Maximum steps ");
		maxStepsField = optionsPanel.addFormattedTextField(
				MAX_STEPS_INPUT_COLS,
				MAX_STEPS_INPUT_FORMAT,
				DEFAULT_MAX_STEPS);
		
		retrieveOptions();
		
	}
	
	private void createStatusPanel() {
		statusPanel = new JPanel();
		statusPanel.setOpaque(false);
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
	}
	
	private void retrieveFunctions() {
		functionF = fField.getEvaluator();
		functionG = gField.getEvaluator();
	}
	
	private void retrieveOptions() {
		xmin = ((Number)xminField.getValue()).doubleValue();
		xmax = ((Number)xmaxField.getValue()).doubleValue();
		ymin = ((Number)yminField.getValue()).doubleValue();
		ymax = ((Number)ymaxField.getValue()).doubleValue();
		stepColoring = stepColoringField.isSelected();
		decimalPrecision = ((Number)decimalPrecisionField.getValue()).intValue();
			epsilon = Math.pow(10,-1*decimalPrecision);
		maxSteps = ((Number)maxStepsField.getValue()).intValue();
	}
	
	private void setOptions() {
		xminField.setValue(xmin);
		xmaxField.setValue(xmax);
		yminField.setValue(ymin);
		ymaxField.setValue(ymax);
		stepColoringField.setSelected(stepColoring);
		decimalPrecisionField.setValue(decimalPrecision);
		maxStepsField.setValue(maxSteps);
	}
	
	private void processNewton() {
		displayPanel.removeAll();
		displayPanel.setWindow(xmin, xmax, ymin, ymax);
		ArrayList<NewtonZero> zeros = new ArrayList<NewtonZero>();
		NewtonsMethod newton = new NewtonsMethod(functionF, functionG);
		
		Statistics stepCountStat = new Statistics();
		
		for (int i=0; i<displaySize.width; i++) {
			for (int j=0; j<displaySize.height; j++) {
				
				Vector2d initialPosition = displayPanel.translateFromPoint(new Point(i,j));
				newton.setPosition(initialPosition);
				Vector2d oldPosition;
				Vector2d position = initialPosition;
				
				int stepCount = 0;
				boolean algorithmTerminated = true;
				/* Run the Newton's Method algorithm */
				do {					
					oldPosition = position;
					position = newton.next();
					/* Terminate Newton's method if maximum
					 * number of steps has been reached */
					if (++stepCount>=maxSteps) {
						algorithmTerminated = false;
						break;
					}	
				/* Newton's method ends when the distance between
				 * one point and the next is sufficiently small */
				} while ((oldPosition.distance(position)) > epsilon);
				
				if (algorithmTerminated) {
					Vector2d thisZero = position;
					NewtonStartPoint thisStartPoint = new NewtonStartPoint(new Point(i, j), stepCount);
					boolean zeroFound = false;
					for (NewtonZero z : zeros) {
						if (z.equals(thisZero)) {
							z.add(thisStartPoint);
							zeroFound = true;
							break;
						}
					}
					if (!zeroFound) {
						/* If the number of roots found is very high,
						 * this is likely an indicator of something gone
						 * wrong, such as if the two functions overlap
						 * for some significant span.  Not much to do here
						 * except kill the algorithm to prevent an
						 * overflow exception. */
						if (zeros.size()>MAX_ZEROS)
							return;
						NewtonZero newZero = new NewtonZero(thisZero);
						newZero.add(thisStartPoint);
						zeros.add(newZero);
					}
					if (stepColoring)
						stepCountStat.add(stepCount);
				}
			}
		}
		
		float hue;
		float brightness = BRIGHTNESS_MEAN;
		double stepCountMean = 0, stepCountRange = 0;
		float brightnessMean = BRIGHTNESS_MEAN, brightnessRange = BRIGHTNESS_RANGE;
		if (stepColoring) {
			stepCountMean = stepCountStat.mean();
			stepCountRange = 2*stepCountStat.standardDeviation();
		}
		
		/* Sorting the zeros ensures that, as the window parameters
		 * change, zeros are consistently assigned the same colors. */
		NewtonZero[] orderedZeros = zeros.toArray(new NewtonZero[0]);
		Arrays.sort(orderedZeros);
		
		for (int i=0; i<orderedZeros.length; i++) {
			System.out.println(orderedZeros[i].toString(decimalPrecision));
			hue = (1/(float)orderedZeros.length)*i;
			displayPanel.addImageAbove(i);
			Color c = Color.getHSBColor(hue, 1, brightnessMean);
			displayPanel.setColor(c, i);
			orderedZeros[i].setColor(c);
			for (NewtonStartPoint p : orderedZeros[i].getStartPoints()) {
				if (stepColoring)
					brightness = (float)(1-((p.getStepCount()-stepCountMean)*brightnessRange/stepCountRange)+brightnessMean);
				displayPanel.setColor(Color.getHSBColor(hue, 1, brightness), i);
				displayPanel.drawPoint(p.getPoint(), i);
			}
		}
		
		updateStatusPanel(orderedZeros);
	}
	
	private void updateStatusPanel(NewtonZero[] zeros) {
		statusPanel.removeAll();
		statusPanel.add(Box.createVerticalGlue());
		statusPanel.add(new JLabel("Solutions:"));
		statusPanel.add(Box.createVerticalStrut(12));
		for (NewtonZero z : zeros) {
			JLabel label = new JLabel("  "+z.toString(2));
			label.setForeground(z.getColor());
			statusPanel.add(label);
		}
		statusPanel.add(Box.createVerticalGlue());
		statusPanel.updateUI();
	}
	
	private void zoom(double scale) {
		double dist, center;
		dist = (xmax-xmin)/2;
		center = xmin+dist;
		xmin = center-(dist*scale);
		xmax = center+(dist*scale);
		dist = (ymax-ymin)/2;
		center = ymin+dist;
		ymin = center-(dist*scale);
		ymax = center+(dist*scale);
		setOptions();
		processNewton();
	}
	
	private void zoomIn() {
		zoom(1/ZOOM_SCALE);
	}
	
	private void zoomOut() {
		zoom(ZOOM_SCALE);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == functionsPanel) {
			if (e.getID() == OptionsPanel.ACTION_SUBMIT) {
				retrieveFunctions();
				processNewton();
			}
		} else if (source == optionsPanel) {
			switch (e.getID()) {
				case OptionsPanel.ACTION_CHANGED:
					retrieveOptions();
					break;
				case OptionsPanel.ACTION_SUBMIT:
					retrieveOptions();
					processNewton();
					break;
			}
		} else if (source == zoomInButton) {
			zoomIn();
		} else if (source == zoomOutButton) {
			zoomOut();
		}
	}
	
	private class NewtonZero implements Comparable<NewtonZero> {
		private ArrayList<NewtonStartPoint> points;
		private Vector2d zero;
		private Color color;
		public NewtonZero(Vector2d zero) {
			this.zero = zero;
			points = new ArrayList<NewtonStartPoint>();
		}
		public void add(NewtonStartPoint point) {
			points.add(point);
		}
		public ArrayList<NewtonStartPoint> getStartPoints() {
			return points;
		}
		public void setColor(Color c) {
			color = c;
		}
		public Color getColor() {
			return color;
		}
		public boolean equals(Vector2d vector) {
			if (zero.distance(vector) < epsilon)
				return true;
			return false;
		}
		public int compareTo(NewtonZero n) {
			double comp;
			comp = zero.getX() - n.zero.getX();
			if (comp > 1) return 1;
			if (comp < 1) return -1;
			comp = zero.getY() - n.zero.getY();
			if (comp > 1) return 1;
			if (comp < 1) return -1;
			return 0;
		}
		public String toString() {
			return toString(2);
		}
		public String toString(int decimalPrecision) {
			String fmtStr = "#.";
			for (int i=decimalPrecision; i>0; i--)
				fmtStr += "#";
			DecimalFormat fmt = new DecimalFormat(fmtStr);
			return "("+fmt.format(zero.getX())+" , "+fmt.format(zero.getY())+")";
		}
	}
	
	private class NewtonStartPoint {
		private Point point;
		private int stepCount;
		public NewtonStartPoint(Point point, int stepCount) {
			this.point = point;
			this.stepCount = stepCount;
		}
		public Point getPoint() {
			return point;
		}
		public int getStepCount() {
			return stepCount;
		}
	}
	
}
