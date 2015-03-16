package com.HCC.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class RegisterInterface extends JFrame {

	private JPanel contentPane;
	private final JLabel label = new JLabel("New label");
	private final JLabel lblEmail = new JLabel("Email:");
	private final JTextField textField = new JTextField();
	private final JLabel lblPassword = new JLabel("Password:");
	private final JPasswordField passwordField = new JPasswordField();
	private final JLabel lblNewLabel = new JLabel("Please confirm the password:");
	private final JPasswordField passwordField_1 = new JPasswordField();
	private final JLabel lblPleaseEnterYour = new JLabel("Please enter your username:");
	private final JTextField textField_1 = new JTextField();
	private final JLabel lblPleaseChooseYour = new JLabel("Please choose your head portrait:");
	private final JTextField textField_2 = new JTextField();
	private final JButton btnNewButton = new JButton("Browse");
	private final JButton btnRegister = new JButton("Register");
	private final JLabel label_1 = new JLabel("");
	private final JLabel lblHasRegisteredLogin = new JLabel("Has registered? Login in here.");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterInterface frame = new RegisterInterface();
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
	public RegisterInterface() {
		textField_2.setBounds(320, 463, 292, 37);
		textField_2.setColumns(10);
		textField_1.setBounds(320, 387, 382, 37);
		textField_1.setColumns(10);
		textField.setBounds(320, 150, 382, 37);
		textField.setColumns(10);
		initGUI();
	}
	private void initGUI() {
		setTitle("Register Interface");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 948);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagConstraints gbc_label = new GridBagConstraints();
		contentPane.setLayout(null);
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(133, 150, 156, 37);
		
		contentPane.add(lblEmail);
		
		contentPane.add(textField);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(133, 212, 156, 37);
		
		contentPane.add(lblPassword);
		passwordField.setBounds(320, 212, 382, 37);
		
		contentPane.add(passwordField);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(46, 280, 243, 37);
		
		contentPane.add(lblNewLabel);
		passwordField_1.setBounds(320, 280, 382, 37);
		
		contentPane.add(passwordField_1);
		lblPleaseEnterYour.setBounds(82, 389, 207, 37);
		
		contentPane.add(lblPleaseEnterYour);
		
		contentPane.add(textField_1);
		lblPleaseChooseYour.setBounds(46, 463, 243, 37);
		
		contentPane.add(lblPleaseChooseYour);
		
		contentPane.add(textField_2);
		btnNewButton.setBounds(611, 463, 94, 37);
		
		contentPane.add(btnNewButton);
		btnRegister.setBounds(335, 559, 243, 46);
		
		contentPane.add(btnRegister);
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setBounds(738, 150, 177, 348);
		
		contentPane.add(label_1);
		lblHasRegisteredLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblHasRegisteredLogin.setBounds(220, 676, 556, 57);
		
		contentPane.add(lblHasRegisteredLogin);
	}

}
