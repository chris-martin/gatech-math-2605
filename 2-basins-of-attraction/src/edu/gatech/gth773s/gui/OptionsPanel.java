package edu.gatech.gth773s.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.Format;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @author Christopher Martin
 * @version 1.0
 */
public class OptionsPanel extends JPanel
					implements ActionListener, FocusListener, DocumentListener {
	
	public static final int ACTION_EDITING = 1;
	public static final int ACTION_CHANGED = 2;
	public static final int ACTION_SUBMIT = 3;
	
	private JPanel section, table;
	private GridBagConstraints sectionConstraints, tableConstraints;
	
	private ActionListener actionListener;
	
	public OptionsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		nextSection();
	}
	
	public void addActionListener(ActionListener a) {
		actionListener = a;
	}
	
	public void nextCol() {
		tableConstraints.gridx++;
	}
	
	public void nextRow() {
		tableConstraints.gridy++;
		tableConstraints.gridx = 0;
	}
	
	public void nextTable() {
		table = new JPanel();
		table.setOpaque(false);
		table.setLayout(new GridBagLayout());
		section.add(table, sectionConstraints);
		sectionConstraints.gridy++;
		tableConstraints = new GridBagConstraints();
		tableConstraints.gridy = 0;
		tableConstraints.gridx = 0;
	}
	
	public void nextSection() {
		section = new JPanel();
		section.setOpaque(false);
		section.setLayout(new GridBagLayout());
		super.add(section);
		sectionConstraints = new GridBagConstraints();
		sectionConstraints.gridx = 0;
		sectionConstraints.gridy = 0;
		nextTable();
	}

	public void add(JComponent c) {
		table.add(c, tableConstraints);
		nextCol();
	}
	
	public JLabel addLabel(String str) {
		JLabel j = new JLabel(str);
		add(j);
		return j;
	}

	public JButton addButton(String str) {
		JButton b = new JButton(str);
		b.addActionListener(this);
		b.setMargin(new Insets(0,5,0,5));
		add(b);
		return b;
	}
	
	public JFormattedTextField addFormattedTextField(
			int columns, Format format, Object initialValue) {
		JFormattedTextField j = new JFormattedTextField(format);
		j.setValue(initialValue);
		j.setColumns(columns);
		j.addActionListener(this);
		j.addFocusListener(this);
		j.getDocument().addDocumentListener(this);
		add(j);
		return j;
	}
	
	public JCheckBox addCheckbox(boolean initialState) {
		JCheckBox j = new JCheckBox();
		j.setOpaque(false);
		j.setSelected(initialState);
		j.addActionListener(this);
		add(j);
		return j;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (actionListener != null) {
			Object actionSource = this;
			/* If the action is coming from a button, set
			 * the source as the button itself; otherwise,
			 * the listener doesn't know what button the
			 * event came from. */
			if (e.getSource() instanceof JButton)
				actionSource = e.getSource();
			actionListener.actionPerformed(new ActionEvent(
					actionSource, ACTION_SUBMIT, "The form has been submitted."));
		}
	}

	public void focusLost(FocusEvent e) {
		if (actionListener != null)
			actionListener.actionPerformed(new ActionEvent(
					this, ACTION_CHANGED, "A field has changed."));
	}
	
	public void focusGained(FocusEvent e) {}
	
	public void changedUpdate(DocumentEvent e) {}
	
	private void documentChanged() {
		if (actionListener != null)
			actionListener.actionPerformed(new ActionEvent(
				this, ACTION_EDITING, "A field is being edited."));
	}
	
	public void insertUpdate(DocumentEvent e) {
		documentChanged();
	}
	
	public void removeUpdate(DocumentEvent e) {
		documentChanged();
	}
	
}