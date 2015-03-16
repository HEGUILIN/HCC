package com.HCC.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ImportImage extends JFrame {

	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Choose Pictures Location:");
	private final JTextField textField = new JTextField();
	private final JButton btnScan = new JButton("Browse");
	private final JLabel lblPleaseNameThe = new JLabel("Please give the meaning of the picture.");
	private final JTextField textField_1 = new JTextField();
	private final JButton btnCreate = new JButton("Create ");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportImage frame = new ImportImage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImportImage() {
		textField_1.setBounds(10, 297, 462, 44);
		textField_1.setColumns(10);
		textField.setBounds(10, 134, 462, 90);
		textField.setColumns(10);
		initGUI();
	}
	private void initGUI() {
		setTitle("Import New Pictures");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel.setBounds(10, 72, 196, 44);
		
		contentPane.add(lblNewLabel);
		
		contentPane.add(textField);
		btnScan.setBounds(328, 72, 140, 44);
		
		contentPane.add(btnScan);
		lblPleaseNameThe.setBounds(10, 237, 462, 54);
		
		contentPane.add(lblPleaseNameThe);
		
		contentPane.add(textField_1);
		btnCreate.setBounds(131, 363, 215, 65);
		
		contentPane.add(btnCreate);
	}
}
