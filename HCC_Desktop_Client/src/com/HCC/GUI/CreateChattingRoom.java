package com.HCC.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class CreateChattingRoom extends JFrame {

	private JPanel contentPane;
	private final JLabel lblRoomName = new JLabel("Room Name:");
	private final JTextField textField = new JTextField();
	private final JLabel lblRoomLocation = new JLabel("Room Location:");
	private final JButton btnSearch = new JButton("Search");
	private final JButton btnConfirm = new JButton("Confirm and Invite students");
	private final JTextArea textArea = new JTextArea();
	private final JLabel lblNewLabel = new JLabel("");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateChattingRoom frame = new CreateChattingRoom();
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
	public CreateChattingRoom() {
		textField.setBounds(174, 87, 228, 30);
		textField.setColumns(10);
		initGUI();
	}
	private void initGUI() {
		setTitle("Create Chatting Room");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblRoomName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRoomName.setBounds(33, 87, 124, 30);
		
		contentPane.add(lblRoomName);
		
		contentPane.add(textField);
		lblRoomLocation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRoomLocation.setBounds(33, 133, 124, 30);
		
		contentPane.add(lblRoomLocation);
		btnSearch.setBounds(417, 131, 82, 32);
		
		contentPane.add(btnSearch);
		btnConfirm.setBounds(160, 231, 254, 54);
		
		contentPane.add(btnConfirm);
		textArea.setBounds(174, 138, 228, 74);
		
		contentPane.add(textArea);
		lblNewLabel.setBounds(417, 87, 129, 30);
		
		contentPane.add(lblNewLabel);
	}
}
