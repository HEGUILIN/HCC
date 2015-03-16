package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class DeleteSentence extends JPanel {
	private final JList list = new JList();
	private final JScrollPane scrollPane = new JScrollPane();

	/**
	 * Create the panel.
	 */
	public DeleteSentence() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		scrollPane.setBounds(0, 0, 1242, 943);
		
		add(scrollPane);
		scrollPane.setViewportView(list);
	}

}
