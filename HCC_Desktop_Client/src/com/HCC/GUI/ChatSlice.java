package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class ChatSlice extends JPanel {
	private final JLabel label = new JLabel("");
	private final JLabel label_1 = new JLabel("");
	private final JLabel label_2 = new JLabel("");
	private final JLabel label_3 = new JLabel("");
	private final JLabel label_4 = new JLabel("");
	private final JLabel label_5 = new JLabel("");
	/**
	 * Create the panel.
	 */
	public ChatSlice() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		label.setBounds(0, 0, 160, 128);
		
		add(label);
		label_1.setBounds(180, 0, 160, 128);
		
		add(label_1);
		label_2.setBounds(340, 0, 160, 128);
		
		add(label_2);
		label_3.setBounds(500, 0, 161, 128);
		
		add(label_3);
		label_4.setBounds(840, 0, 160, 128);
		
		add(label_4);
		label_5.setBounds(660, 0, 160, 128);
		
		add(label_5);
	}
}
