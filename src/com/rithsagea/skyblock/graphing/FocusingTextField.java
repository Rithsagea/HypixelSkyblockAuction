package com.rithsagea.skyblock.graphing;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class FocusingTextField extends JTextField implements FocusListener {
	
	private static final long serialVersionUID = 220396619566538035L;
	private boolean showingHint = true;
	private final String hint;
	
	public FocusingTextField(String hint) {
		super(hint);
		this.hint = hint;
		showingHint = true;
		super.setForeground(Color.LIGHT_GRAY);
		super.addFocusListener(this);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setForeground(Color.BLACK);
			super.setText("");
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			super.setForeground(Color.LIGHT_GRAY);
			showingHint = true;
		}
	}
	
	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}
