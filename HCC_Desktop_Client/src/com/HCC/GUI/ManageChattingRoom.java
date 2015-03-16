package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;

public class ManageChattingRoom extends JPanel {
	private final JList list = new JList();
	private final JList list_1 = new JList();
	private final JList list_2 = new JList();
	private final JLabel lblNewLabel = new JLabel("New label");

	/**
	 * Create the panel.
	 */
	public ManageChattingRoom() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		list.setBounds(15, 53, 1015, 883);
		
		add(list);
		list_1.setBounds(1039, 556, 317, 380);
		
		add(list_1);
		list_2.setBounds(1042, 16, 314, 524);
		
		add(list_2);
		lblNewLabel.setBounds(15, 0, 1007, 44);
		
		add(lblNewLabel);
	}

}
