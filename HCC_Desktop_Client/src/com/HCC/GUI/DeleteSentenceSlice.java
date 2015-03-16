package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;

public class DeleteSentenceSlice extends JPanel {
	private final JList list = new JList();
	private final JLabel lblNewLabel = new JLabel("New label");
	private final JLabel label = new JLabel("New label");
	private final JLabel label_1 = new JLabel("New label");
	private final JLabel label_2 = new JLabel("New label");
	private final JLabel label_3 = new JLabel("New label");
	private final JLabel label_4 = new JLabel("New label");
	private final JLabel lblNewLabel_1 = new JLabel("New label");
	private final JLabel label_5 = new JLabel("New label");
	private final JLabel label_6 = new JLabel("New label");
	private final JLabel label_7 = new JLabel("New label");
	private final JLabel label_8 = new JLabel("New label");
	private final JLabel label_9 = new JLabel("New label");
	private final JButton btnDelete = new JButton("Delete");

	/**
	 * Create the panel.
	 */
	public DeleteSentenceSlice() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		list.setBounds(0, 0, 1094, 189);
		
		add(list);
		lblNewLabel.setBounds(0, 1, 181, 188);
		
		add(lblNewLabel);
		label.setBounds(183, 1, 181, 188);
		
		add(label);
		label_1.setBounds(365, 1, 181, 188);
		
		add(label_1);
		label_2.setBounds(549, 1, 181, 188);
		
		add(label_2);
		label_3.setBounds(731, 1, 181, 188);
		
		add(label_3);
		label_4.setBounds(913, 1, 181, 188);
		
		add(label_4);
		lblNewLabel_1.setBounds(0, 190, 181, 48);
		
		add(lblNewLabel_1);
		label_5.setBounds(183, 190, 181, 48);
		
		add(label_5);
		label_6.setBounds(365, 190, 181, 48);
		
		add(label_6);
		label_7.setBounds(549, 190, 181, 48);
		
		add(label_7);
		label_8.setBounds(731, 190, 181, 48);
		
		add(label_8);
		label_9.setBounds(913, 190, 181, 48);
		
		add(label_9);
		btnDelete.setBounds(1099, -3, 87, 241);
		
		add(btnDelete);
	}
}
