package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JButton;

public class CreateSentence extends JPanel {
	private final JList list = new JList();
	private final JList list_1 = new JList();
	private final JButton btnCreateSentence = new JButton("Create Sentence");

	/**
	 * Create the panel.
	 */
	public CreateSentence() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		list.setBounds(15, 16, 1179, 635);
		
		add(list);
		list_1.setBounds(15, 667, 958, 208);
		
		add(list_1);
		btnCreateSentence.setBounds(988, 667, 206, 208);
		
		add(btnCreateSentence);
	}
}
