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

import net.sf.json.JSONObject;

import com.HCC.Controller.WebConnect;
import com.HCC.Model.Teacher;
import com.HCC.Utils.Memory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginInterface extends JFrame {

	private JPanel contentPane;
	private final JLabel lblUsername = new JLabel("User name:");
	private final JTextField textField = new JTextField();
	private final JLabel lblPassword_1 = new JLabel("Password:");
	private final JPasswordField passwordField_1 = new JPasswordField();
	private final JLabel lblNewLabel = new JLabel("");
	private final JButton btnNewButton = new JButton("Login Now");
	private final JLabel lblNoAccountRegister = new JLabel("No account? Register Now.");
	private final JLabel lblForgetYourPassword = new JLabel("Forget your password? Find here.");
	static LoginInterface frame=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginInterface();
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
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(101, 62, 154, 34);
		
		contentPane.add(lblUsername);
		
		contentPane.add(textField);
		lblPassword_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword_1.setBounds(101, 132, 154, 34);
		
		contentPane.add(lblPassword_1);
		passwordField_1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		passwordField_1.setBounds(278, 132, 237, 34);
		
		contentPane.add(passwordField_1);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(530, 69, 133, 161);
		
		contentPane.add(lblNewLabel);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username=textField.getText();
				if(username.equals("")){
					lblNewLabel.setText("No user name");
					return;
				}
				
				String password=passwordField_1.getText();
				if(password.equals("")){
					lblNewLabel.setText("Password should not be null");
					return;
				}
				
				String result =new WebConnect().login(username, password);
				
				if(result.equals("login success")){
					lblNewLabel.setText("Login Success");
					
					frame.setVisible(false);
					
					Teacher t=new Teacher();
					t.setusername(username);
					
					Memory.writeTeacher(t);
					OccasionChatInterface oci=new OccasionChatInterface();
					oci.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(309, 240, 167, 40);
		
		contentPane.add(btnNewButton);
		lblNoAccountRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				RegisterInterface ri=new RegisterInterface();
				ri.setVisible(true);
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
