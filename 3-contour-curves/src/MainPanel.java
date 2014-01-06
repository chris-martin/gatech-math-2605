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

	private DisplayPanel2d displayPanel;
	private OptionsPanel functionPanel;
	private OptionsPanel optionsPanel;

	private Dimension displaySize;
	
	private static final Color BACKGROUND_COLOR = new Color(220, 230, 240);
	private static final Color DISPLAY_BACKGROUND_COLOR = new Color(255, 255, 255);
	private static final Border BORDER =
		BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createLineBorder(BACKGROUND_COLOR.darker(), 1)
		);
	
	/** Function */
	private FunctionField functionField;	
	private static final String DEFAULT_FUNCTION = "(x^2+y^2)^2-x^2+y^2";
	private static final int FUNCTION_INPUT_COLS = 24;
	private Evaluator function;
	
	/** Window range */
	private JFormattedTextField xminField, xmaxField, yminField, ymaxField;
	private static final double DEFAULT_XMIN = -1.2;
	private static final double DEFAULT_XMAX = 1.2;
	private static final double DEFAULT_YMIN = -1.2;
	private static final double DEFAULT_YMAX = 1.2;
	private static final int WINDOW_RANGE_INPUT_COLS = 4;
	private static final NumberFormat WINDOW_RANGE_INPUT_FORMAT = new DecimalFormat("#.###");
	private double xmin, xmax, ymin, ymax;
	
	/** Changing the window range */
	private JButton zoomInButton, zoomOutButton;
	private static final double ZOOM_SCALE = 1.5;
	
	/** Whether adaptive tile sizing should be used */
	private JCheckBox adaptiveTileField;
	private static final boolean DEFAULT_ADAPTIVETILE = false;
	private boolean adaptiveTile;
	
	/** Whether gridlines should be displayed */
	private JCheckBox showGridlinesField;
	private static final boolean DEFAULT_SHOWGRIDLINES = true;
	private boolean showGridlines;
	
	/** The number of rows and columns for the grid */
	private JFormattedTextField gridRowsField, gridColsField;
	private static final int DEFAULT_GRID_ROWS = 15;
	private static final int DEFAULT_GRID_COLS = 15;
	private static final int GRID_SIZE_INPUT_COLS = 4;
	private static final NumberFormat GRID_SIZE_INPUT_FORMAT = new DecimalFormat("#");
	private int gridRows, gridCols;
	
	public MainPanel(Dimension windowSize, Dimension displaySize) {
		setPreferredSize(windowSize);
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOR);
		setBorder(new CompoundBorder(
				BORDER,
				new MatteBorder(25, 25, 25, 25, BACKGROUND_COLOR)
		));
		
		this.displaySize = displaySize; 
		
		createFunctionPanel();
		createOptionsPanel();
		createDisplayPanel();

		add(new FixedSizePanel(displayPanel), BorderLayout.CENTER);
		add(functionPanel, BorderLayout.SOUTH);
		add(optionsPanel, BorderLayout.EAST);
		
		process();
		
	}
	
	private void createDisplayPanel() {
		displayPanel = new DisplayPanel2d(displaySize);
		displayPanel.setBackground(DISPLAY_BACKGROUND_COLOR);
		displayPanel.setBorder(BORDER);
	}
	
	private void createFunctionPanel() {
		
		functionPanel = new OptionsPanel();
		functionPanel.setOpaque(false);
		functionPanel.addActionListener(this);
		
		functionPanel.addLabel("f(x,y) = ");
		functionPanel.add(functionField = new FunctionField(FUNCTION_INPUT_COLS, DEFAULT_FUNCTION));
		functionField.addActionListener(functionPanel);

		retrieveFunction();
		
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
		
		adaptiveTileField = optionsPanel.addCheckbox(DEFAULT_ADAPTIVETILE);
		optionsPanel.addLabel("Adaptive tile sizing"); 
		
		optionsPanel.nextSection();
		
		showGridlinesField = optionsPanel.addCheckbox(DEFAULT_SHOWGRIDLINES);
		optionsPanel.addLabel("Show gridlines"); 
		
		optionsPanel.nextSection();

		optionsPanel.addLabel("Default grid size:");
		optionsPanel.nextTable();
		gridRowsField = optionsPanel.addFormattedTextField(
				GRID_SIZE_INPUT_COLS,
				GRID_SIZE_INPUT_FORMAT,
				DEFAULT_GRID_ROWS);
		optionsPanel.addLabel(" rows");
		optionsPanel.nextRow();
		gridColsField = optionsPanel.addFormattedTextField(
				GRID_SIZE_INPUT_COLS,
				GRID_SIZE_INPUT_FORMAT,
				DEFAULT_GRID_COLS);
		optionsPanel.addLabel(" columns");
		
		retrieveOptions();
		
	}
	
	private void retrieveFunction() {
		function = functionField.getEvaluator();
	}
	
	private void retrieveOptions() {
		xmin = ((Number)xminField.getValue()).doubleValue();
		xmax = ((Number)xmaxField.getValue()).doubleValue();
		ymin = ((Number)yminField.getValue()).doubleValue();
		ymax = ((Number)ymaxField.getValue()).doubleValue();
		adaptiveTile = adaptiveTileField.isSelected();
		showGridlines = showGridlinesField.isSelected();
		gridRows = ((Number)gridRowsField.getValue()).intValue();
		gridCols = ((Number)gridColsField.getValue()).intValue();
	}
	
	private void setOptions() {
		xminField.setValue(xmin);
		xmaxField.setValue(xmax);
		yminField.setValue(ymin);
		ymaxField.setValue(ymax);
		adaptiveTileField.setSelected(adaptiveTile);
		gridRowsField.setValue(gridRows);
		gridColsField.setValue(gridCols);
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
		process();
	}
	
	private void zoomIn() {
		zoom(1/ZOOM_SCALE);
	}
	
	private void zoomOut() {
		zoom(ZOOM_SCALE);
	}

	private void process() {
		displayPanel.removeAll();
		displayPanel.setWindow(xmin, xmax, ymin, ymax);
		if (showGridlines) {
			displayPanel.addImageAbove("gridlines");
			displayPanel.setColor(new Color(210, 210, 210), "gridlines");
		}
		displayPanel.addImageAbove("axis");
		displayPanel.setColor(new Color(210, 175, 175), "axis");
		displayPanel.drawLine(new Vector2d(xmin, 0), new Vector2d(xmax, 0), "axis");
		displayPanel.drawLine(new Vector2d(0, ymin), new Vector2d(0, ymax), "axis");
		displayPanel.addImageAbove("curve");
		displayPanel.setColor(Color.blue, "curve");
		Stack<Tile> tiles = new Stack<Tile>();
		double tileHeight = (ymax-ymin)/(double)gridRows;
		double tileWidth = (xmax-xmin)/(double)gridCols;
		// epsilon = the area of one pixel
		double epsilon = ( (xmax-xmin)/displaySize.width ) * ( (ymax-ymin)/displaySize.height );
		for (int row=0; row<gridRows; row++) {
			double posY = ymin+(row*tileHeight);
			for (int col=0; col<gridCols; col++) {
				double posX = xmin+(col*tileWidth);
				tiles.add(new Tile(posX, posY, tileWidth, tileHeight));
			}
		}
		while (!tiles.isEmpty()) {
			Tile t = tiles.pop();
			if (showGridlines) {
				displayPanel.drawLine(new Vector2d(t.xmin, t.ymax), new Vector2d(t.xmax, t.ymax), "gridlines");
				displayPanel.drawLine(new Vector2d(t.xmin, t.ymax), new Vector2d(t.xmin, t.ymin), "gridlines");
			}
			double[] corner = new double[4];
			corner[0] = function.evaluate(t.xmin, t.ymax);
			corner[1] = function.evaluate(t.xmax, t.ymax);
			corner[2] = function.evaluate(t.xmin, t.ymin);
			corner[3] = function.evaluate(t.xmax, t.ymin);
			int above = ((corner[0]>0)?1000:0)
			          + ((corner[1]>0)?100:0)
			          + ((corner[2]>0)?10:0)
			          + ((corner[3]>0)?1:0);
			if (adaptiveTile) {
				if (t.isDegenerate(function, epsilon)) {
					splitTile(t, tiles, epsilon);
					continue;
				}
			}
			double a1 = 0, a2 = 0;
			Vector2d v1 = new Vector2d(0, 0), v2 = new Vector2d(0, 0);
			switch (above) {
				case 1111: case 0:
					//displayPanel.drawLine(new Vector2d(t.xmin,t.ymin), new Vector2d(t.xmax, t.ymax), "gridlines");
					//displayPanel.drawLine(new Vector2d(t.xmin,t.ymax), new Vector2d(t.xmax, t.ymin), "gridlines");
					continue;
				case 1010: case 101:
					a1 = 1-(corner[1]/(corner[1]-corner[0]));
					a2 = 1-(corner[3]/(corner[3]-corner[2]));
					v1 = new Vector2d(t.xmin+a1*(t.xmax-t.xmin), t.ymax);
					v2 = new Vector2d(t.xmin+a2*(t.xmax-t.xmin), t.ymin);
					break;
				case 1100: case 11:
					a1 = 1-(corner[2]/(corner[2]-corner[0]));
					a2 = 1-(corner[3]/(corner[3]-corner[1]));
					v1 = new Vector2d(t.xmin, t.ymax-a1*(t.ymax-t.ymin));
					v2 = new Vector2d(t.xmax, t.ymax-a2*(t.ymax-t.ymin));
					break;
				case 1000: case 111:
					a1 = 1-(corner[1]/(corner[1]-corner[0]));
					a2 = 1-(corner[2]/(corner[2]-corner[0]));
					v1 = new Vector2d(t.xmin+a1*(t.xmax-t.xmin), t.ymax);
					v2 = new Vector2d(t.xmin, t.ymax-a2*(t.ymax-t.ymin));
					break;
				case 100: case 1011:
					a1 = 1-(corner[1]/(corner[1]-corner[0]));
					a2 = 1-(corner[3]/(corner[3]-corner[1]));
					v1 = new Vector2d(t.xmin+a1*(t.xmax-t.xmin), t.ymax);
					v2 = new Vector2d(t.xmax, t.ymax-a2*(t.ymax-t.ymin));
					break;
				case 10: case 1101:
					a1 = 1-(corner[2]/(corner[2]-corner[0]));
					a2 = 1-(corner[3]/(corner[3]-corner[2]));
					v1 = new Vector2d(t.xmin, t.ymax-a1*(t.ymax-t.ymin));
					v2 = new Vector2d(t.xmin+a2*(t.xmax-t.xmin), t.ymin);
					break;
				case 1: case 1110:
					a1 = 1-(corner[3]/(corner[3]-corner[1]));
					a2 = 1-(corner[3]/(corner[3]-corner[2]));
					v1 = new Vector2d(t.xmax, t.ymax-a1*(t.ymax-t.ymin));
					v2 = new Vector2d(t.xmin+a2*(t.xmax-t.xmin), t.ymin);
					break;
			}
			//v1.subtract(v2).dYdX()
			if (adaptiveTile) {
				double slopeDiff = 
					Math.abs( Math2d.createGradient(function, v1, epsilon).unit().dot( Math2d.createGradient(function, v2, epsilon).unit()) );
				if (slopeDiff < .5) {
					splitTile(t, tiles, epsilon);
					continue;
				}
			}
			displayPanel.drawLine(v1, v2, "curve");
		}
	}
	
	private void splitTile(Tile t, Stack<Tile> tiles, double epsilon) {
		if (t.area() > epsilon)
			for (Tile s : t.split())
				tiles.add(s);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == functionPanel) {
			if (e.getID() == OptionsPanel.ACTION_SUBMIT) {
				retrieveFunction();
				process();
			}
		} else if (source == optionsPanel) {
			switch (e.getID()) {
				case OptionsPanel.ACTION_CHANGED:
					retrieveOptions();
					break;
				case OptionsPanel.ACTION_SUBMIT:
					retrieveOptions();
					process();
					break;
			}
		} else if (source == zoomInButton) {
			zoomIn();
		} else if (source == zoomOutButton) {
			zoomOut();
		}
	}
	
}
