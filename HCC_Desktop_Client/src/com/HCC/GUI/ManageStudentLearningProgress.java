package com.HCC.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;

public class ManageStudentLearningProgress extends JPanel {
	private final JLabel lblChooseAStudent = new JLabel("Choose a Student");
	private final JComboBox comboBox = new JComboBox();
	private final JList list = new JList();
	private final JList list_1 = new JList();
	private final JButton btnDelete = new JButton("Delete");
	private final JButton btnLearn = new JButton("Learn");

	/**
	 * Create the panel.
	 */
	public ManageStudentLearningProgress() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		lblChooseAStudent.setBounds(56, 72, 163, 53);
		
		add(lblChooseAStudent);
		comboBox.setBounds(254, 72, 287, 53);
		
		add(comboBox);
		list.setBounds(56, 160, 550, 689);
		
		add(list);
		list_1.setBounds(661, 160, 550, 692);
		
		add(list_1);
		btnDelete.setBounds(452, 865, 158, 63);
		
		add(btnDelete);
		btnLearn.setBounds(1056, 868, 158, 60);
		
		add(btnLearn);
	}
}
