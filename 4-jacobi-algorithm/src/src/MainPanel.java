// MainPanel.java

import functionParser.*;
import edu.gatech.gth773s.gui.*;
import edu.gatech.gth773s.gui.math.*;
import edu.gatech.gth773s.math.*;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

public class MainPanel extends JPanel implements ActionListener {
	
	private static final Color BACKGROUND_COLOR = new Color(220, 230, 240);
	private static final Color DISPLAY_BACKGROUND_COLOR = new Color(200, 200, 220);
	private static final Border BORDER =
		BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createLineBorder(BACKGROUND_COLOR.darker(), 1)
		);
	
	private static final double EPSILON = Math.pow(10, -9);
	
	private JButton buttonSorted, buttonUnsorted;
	private JLabel labelSorted, labelUnsorted;
	
	private JLabel[][] cellsResult;
	private JLabel[] eigenResult;
	private JLabel stepResult, timeResult;
	
	private FunctionGraphPanel display;
	private OptionsPanel options;
	
	public MainPanel(Dimension displaySize) {
		setBackground(BACKGROUND_COLOR);
		setBorder(new CompoundBorder(
				BORDER,
				new MatteBorder(25, 25, 25, 25, BACKGROUND_COLOR)
		));
		setLayout(new BorderLayout());
		
		display = new FunctionGraphPanel(displaySize);
		display.setBackground(DISPLAY_BACKGROUND_COLOR);
		display.setWindow(0, 40, 0, 10);
		
		options = new OptionsPanel();
		options.addActionListener(this);
		options.setOpaque(false);
		
		labelSorted = options.addLabel(">");
		buttonSorted = options.addButton("Largest");
		options.nextRow();
		options.addLabel(" ");
		options.nextRow();
		labelUnsorted = options.addLabel(" ");
		buttonUnsorted = options.addButton("Unsorted");
		
		JPanel result = new JPanel();
		result.setOpaque(false);
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		result.add(Box.createVerticalGlue());
		result.add(new JLabel("Matrix:"));
		JPanel matrix = new JPanel();
		matrix.setOpaque(false);
		matrix.setLayout(new GridLayout(5, 5, 6, 6));
		cellsResult = new JLabel[5][5];
		for (int i=0; i<5; i++)
			for (int j=0; j<5; j++) {
				cellsResult[i][j] = new JLabel();
				matrix.add(cellsResult[i][j]);
			}
		result.add(matrix);
		result.add(Box.createVerticalGlue());
		JPanel eigen = new JPanel();
		eigen.setOpaque(false);
		eigen.setLayout(new GridLayout(6, 1, 6, 3));
		eigen.add(new JLabel("Eigenvalues:"));
		eigenResult = new JLabel[5];
		for (int i=0; i<5; i++) {
			eigenResult[i] = new JLabel();
			eigen.add(eigenResult[i]);
		}
		result.add(eigen);
		result.add(Box.createVerticalGlue());
		stepResult = new JLabel();
		result.add(stepResult);
		result.add(Box.createVerticalGlue());
		timeResult = new JLabel();
		result.add(timeResult);
		result.add(Box.createVerticalGlue());
		
		add(result, BorderLayout.WEST);
		add(new FixedSizePanel(display), BorderLayout.CENTER);
		add(options, BorderLayout.EAST);
		
		process(JacobiMethod.LARGEST_OFFDIAG);
	}
	
	private void process(int type) {
		DecimalFormat twoPlaces = new DecimalFormat("#.##");
		Matrix m = new Matrix(5, 5);
		m.fillRandomSymmetric();
		System.out.print("Matrix A:\n"+m+"\n\nOff(A):\n");
		for (int i=0; i<5; i++)
			for (int j=0; j<5; j++)
				cellsResult[i][j].setText(twoPlaces.format(m.getValue(i, j)));
		
		display.removeAll();
		
		long timer = System.nanoTime();
		
		JacobiMethod j = new JacobiMethod(m, type);
		
		FunctionParser parser = new FunctionParser("x");
		Evaluator bound = new Evaluator(1);
		try {
			parser.parse("x*(log[9/10]/log[2.7182818])+(log["+m.offDiag()+"]/log[2.7182818])", bound);
		} catch (FunctionParsingException e) { // this shouldn't happen
			System.err.println("Bad function parse...");
			System.exit(-1);
		}
		display.addFunction(bound, new Color(200, 140, 140), "bound");
		
		display.addStatPlot("actual");
		
		double off;
		off = j.off();
		System.out.println(off);
		display.addStatPoint(new Vector2d(j.iterations(), Math.log(off)), "actual");
		do {
			j.jacobiStep();
			off = j.off();
			System.out.println(off);
			display.addStatPoint(new Vector2d(j.iterations(), Math.log(off)), "actual");
		} while (off > EPSILON);
		
		timer = (System.nanoTime()-timer)/1000000;
		
		System.out.print("\nEigenvalues:\n");
		double[] eigs = j.diagonals();
		for (int i=0; i<eigs.length; i++) {
			eigenResult[i].setText(twoPlaces.format(eigs[i]));
			System.out.print(eigs[i]+" ");
		}
		stepResult.setText("Steps: "+j.iterations());
		timeResult.setText("Time: "+timer+" ms");
		System.out.print("\n\nCompleted in:\n"+timer+" ms\n\n");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonSorted) {
			process(JacobiMethod.LARGEST_OFFDIAG);
			labelSorted.setText(">");
			labelUnsorted.setText(" ");
		} else if (e.getSource() == buttonUnsorted) {
			process(JacobiMethod.UNSORTED);
			labelSorted.setText(" ");
			labelUnsorted.setText(">");
		}
	}
	
}
