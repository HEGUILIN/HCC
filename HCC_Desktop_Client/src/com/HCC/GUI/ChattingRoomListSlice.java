package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ChattingRoomListSlice extends JPanel {
	private final JLabel lblNewLabel = new JLabel("Chatting Room Name");
	private final JButton btnNewButton = new JButton("Set Default");
	private final JButton btnDelete = new JButton("Delete");

	/**
	 * Create the panel.
	 */
	public ChattingRoomListSlice() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 246, 83);
		
		add(lblNewLabel);
		btnNewButton.setBounds(251, 0, 115, 43);
		
		add(btnNewButton);
		btnDelete.setBounds(251, 40, 115, 43);
		
		add(btnDelete);
	}

}
