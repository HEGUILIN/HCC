package com.HCC.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginInterface extends JFrame {

	private JPanel contentPane;
	private final JLabel lblEmail = new JLabel("Email:");
	private final JTextField textField = new JTextField();
	private final JLabel lblPassword = new JLabel("Password:");
	private final JPasswordField passwordField = new JPasswordField();
	private final JLabel lblPassword_1 = new JLabel("Password:");
	private final JPasswordField passwordField_1 = new JPasswordField();
	private final JLabel lblPleaseConfirmThe = new JLabel("Please confirm the password:");
	private final JPasswordField passwordField_2 = new JPasswordField();
	private final JLabel lblNewLabel = new JLabel("");
	private final JButton btnNewButton = new JButton("Login Now");
	private final JLabel lblNoAccountRegister = new JLabel("No account? Register Now.");
	private final JLabel lblForgetYourPassword = new JLabel("Forget your password? Find here.");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginInterface frame = new LoginInterface();
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
	public LoginInterface() {
		textField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		textField.setBounds(278, 62, 237, 34);
		textField.setColumns(10);
		initGUI();
	}
	private void initGUI() {
		setTitle("Login Interface");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(101, 62, 154, 34);
		
		contentPane.add(lblEmail);
		
		contentPane.add(textField);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(133, 123, 144, 34);
		
		contentPane.add(passwordField);
		lblPassword_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword_1.setBounds(101, 132, 154, 34);
		
		contentPane.add(lblPassword_1);
		passwordField_1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		passwordField_1.setBounds(278, 132, 237, 34);
		
		contentPane.add(passwordField_1);
		lblPleaseConfirmThe.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPleaseConfirmThe.setBounds(0, 196, 255, 34);
		
		contentPane.add(lblPleaseConfirmThe);
		passwordField_2.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		passwordField_2.setBounds(278, 196, 237, 34);
		
		contentPane.add(passwordField_2);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(530, 69, 133, 161);
		
		contentPane.add(lblNewLabel);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnNewButton.setBounds(312, 256, 167, 40);
		
		contentPane.add(btnNewButton);
		lblNoAccountRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblNoAccountRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoAccountRegister.setBounds(43, 335, 286, 34);
		
		contentPane.add(lblNoAccountRegister);
		lblForgetYourPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblForgetYourPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblForgetYourPassword.setBounds(332, 331, 255, 38);
		
		contentPane.add(lblForgetYourPassword);
	}

}
