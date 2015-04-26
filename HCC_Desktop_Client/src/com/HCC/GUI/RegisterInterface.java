package com.HCC.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import net.sf.json.JSONObject;

import com.HCC.Controller.WebConnect;
import com.HCC.Model.Teacher;
import com.HCC.Utils.ClientSocket;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class RegisterInterface extends JFrame {

	private JPanel contentPane;
	private final JLabel label = new JLabel("New label");
	private final JLabel lblEmail = new JLabel("Email:");
	private final JTextField textField = new JTextField();//email
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
	static RegisterInterface frame=null;
	private String headportrait="";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegisterInterface();
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
		setBounds(100, 100, 952, 831);
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
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setAcceptAllFileFilterUsed(false);
				final String[][] fileENames = { { ".bmp", ".bmp" },
				           { ".jpg", ".jpg" },
				           { ".png", ".png" }
				            };
				for (String fileEName[] : fileENames) {
					fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
						public boolean accept(File file) { 
							if (file.getName().endsWith(fileEName[0]) || file.isDirectory()) {
								return true;
							}
							return false;
						}

						@Override
						public String getDescription() {
							// TODO Auto-generated method stub
							return fileEName[1];
						}
					});
				}
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if(returnVal == JFileChooser.APPROVE_OPTION){       
					headportrait= fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println(headportrait);
				}
			}			  
		});
					
		btnNewButton.setBounds(611, 463, 94, 37);
		
		contentPane.add(btnNewButton);
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email=textField.getText();//get the email
				if(email.equals("")){
					label_1.setText("No email");
					return;
				}
				
				String password=passwordField.getText();
				String password2=passwordField_1.getText();
				if(password.equals("")){
					label_1.setText("No password");
					return;
				}
				if(password2.equals("")){
					label_1.setText("No confirm password");
					return;
				}
				if(!password.equals(password2)){
					label_1.setText("two passwords are not the same");
					return;
				}
				
				String username=textField_1.getText();
				if(username.equals("")){
					label_1.setText("No username");
					return;
				}
				
				Teacher t=new Teacher();
				t.setemail(email);
				t.setusername(username);
				t.setpassword(password);
				String result=new WebConnect().register(username, password,1, email);
				if(result.equals("register success")){
					if(!headportrait.equals("")){
						new WebConnect().uploadHeadPortrait(headportrait,username);
					}
					frame.setVisible(false);
					OccasionChatInterface oci=new OccasionChatInterface();
					oci.setVisible(true);
				}
			}
		});
		btnRegister.setBounds(335, 559, 243, 46);
		
		contentPane.add(btnRegister);
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setBounds(738, 150, 177, 348);
		
		contentPane.add(label_1);
		lblHasRegisteredLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblHasRegisteredLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblHasRegisteredLogin.setBounds(220, 676, 556, 57);
		
		contentPane.add(lblHasRegisteredLogin);
	}

}
