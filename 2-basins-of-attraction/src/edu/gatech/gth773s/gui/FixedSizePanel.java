// FixedSizePanel.java

package edu.gatech.gth773s.gui;

import java.awt.Dimension;
import javax.swing.*;

/**
 * @author Christopher Martin
 * @version 1.1
 */
public class FixedSizePanel extends JPanel {
	
	public FixedSizePanel(JPanel jpanel) {
		innerPanel = new JPanel();
		innerPanel = jpanel;
		size = innerPanel.getPreferredSize();
		setOpaque(false);
		setLayout(new BoxLayout(this, 1));
		JPanel jpanel1 = new JPanel();
		jpanel1.setOpaque(false);
		jpanel1.setLayout(new BoxLayout(jpanel1, 1));
		jpanel1.add(Box.createVerticalGlue());
		jpanel1.add(innerPanel);
		jpanel1.add(Box.createVerticalGlue());
		add(Box.createHorizontalGlue());
		add(jpanel1);
		add(Box.createHorizontalGlue());
		setSize(size);
	}

	public void setSize(Dimension dimension) {
		innerPanel.setMinimumSize(dimension);
		innerPanel.setPreferredSize(dimension);
		innerPanel.setMaximumSize(dimension);
	}
	
	private Dimension size;
	private JPanel innerPanel;
	
}
