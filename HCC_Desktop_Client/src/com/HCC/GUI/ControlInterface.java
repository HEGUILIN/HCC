package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class ControlInterface extends JPanel {
	private final JLabel lblNewLabel = new JLabel("New label");
	private final JLabel lblNewLabel_1 = new JLabel("Statement");

	/**
	 * Create the panel.
	 */
	public ControlInterface() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		lblNewLabel.setBounds(604, 5, 69, 20);
		
		add(lblNewLabel);
		lblNewLabel_1.setBounds(0, 914, 1278, 36);
		
		add(lblNewLabel_1);
	}

}
