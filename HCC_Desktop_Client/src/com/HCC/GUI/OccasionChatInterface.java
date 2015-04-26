package com.HCC.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import com.HCC.Controller.WebConnect;
import com.HCC.Model.GRAPH;
import com.HCC.Model.Message;
import com.HCC.Model.Sentence;
import com.HCC.Model.Teacher;
import com.HCC.Utils.Memory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

public class OccasionChatInterface extends JFrame {

	private JPanel contentPane;
	private final JList list = new JList();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mntmExit = new JMenuItem("Exit");
	private final JMenu mnAbout = new JMenu("About");
	private final JMenuItem mntmVersion = new JMenuItem("Version");
	private final JButton btnSend = new JButton("Send");
	private final JLabel lblStatement = new JLabel("Statement");
	private final JList list_1 = new JList();
	private final JMenu mnView = new JMenu("View");
	private final JMenuItem mntmControlInterface = new JMenuItem("New Chatting Room");
	private final JMenuItem mntmSaveChattingRecord = new JMenuItem("Save Chatting Record");
	private final JLabel lblStudentNameList = new JLabel("Student Name List");
	private final JLabel lblCurrentRoomLocation = new JLabel("Current Room Location");
	private final JMenu mnManageSentences = new JMenu("Manage Sentences");
	private final JMenuItem mntmCreateSentence = new JMenuItem("Create Sentence");
	private final JMenuItem mntmDeleteSentence = new JMenuItem("Delete Sentence");
	private final JMenuItem mntmSaveChattingRoom = new JMenuItem("Save Chatting Room");
	private final JMenuItem mntmManageChattingRooms = new JMenuItem("Manage Chatting Rooms");
	private final JMenuItem mntmManageStudentsLearning = new JMenuItem("Manage Students' Learning Progress");
	private final JComboBox comboBox = new JComboBox();
	private final JTextField textField = new JTextField();
	private Object[] shows=null;
	private final JMenuItem mntmRefreshChatMessages = new JMenuItem("Refresh Chatting Messages");
	OccasionChatInterface frame_this;
	CreateSentence cs=new CreateSentence();
	ActionListener comboBox_al=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OccasionChatInterface frame = new OccasionChatInterface();
					frame.setVisible(true);
					//initData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OccasionChatInterface() {
		super();
		initGUI();
		initUser();
		frame_this=this;
		initGRAPH();
		//cs.initGrid();
	}
	void initGRAPH(){
		Memory.extractGRAPH();
	};
	void initUser(){
		Teacher t=Memory.extractTeacher();
	    lblStatement.setText(t.getusername());

	}
	private void initGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 1038);
		
		setJMenuBar(menuBar);
		
		menuBar.add(mnFile);
		
		mnFile.add(mntmSaveChattingRoom);
		
		mnFile.add(mntmSaveChattingRecord);
		mntmRefreshChatMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Memory.refreshGRAPH();
				Memory.refreshSentence();
				comboBoxInit();
				cs.initGrid();
			}
		});
		
		mnFile.add(mntmRefreshChatMessages);
		
		mnFile.add(mntmExit);
		
		menuBar.add(mnView);
		mntmControlInterface.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateChattingRoom ccr=new CreateChattingRoom();
				ccr.setVisible(true);
			}
		});
		
		mnView.add(mntmControlInterface);
		
		mnView.add(mnManageSentences);
		mntmCreateSentence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("create sentence");
				frame_this.setContentPane(cs);
				frame_this.setBounds(cs.getBounds());
				frame_this.revalidate();
				frame_this.repaint();
			}
		});
		
		mnManageSentences.add(mntmCreateSentence);
		
		mnManageSentences.add(mntmDeleteSentence);
		
		mnView.add(mntmManageChattingRooms);
		
		mnView.add(mntmManageStudentsLearning);
		
		menuBar.add(mnAbout);
		
		mnAbout.add(mntmVersion);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setBounds(0, 0, 1000, 768);
		
		contentPane.add(scrollPane);
		scrollPane.setViewportView(list);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String input=textField.getText();
				if(input.equals("")){
					
				}else{
					int index=comboBox.getSelectedIndex()-1;
					System.out.println(index);
					Message m=new Message();
					m.setImage("image5", "");
					Sentence s=Sentence.sentences.get(index);
					for(Sentence s1:Sentence.sentences){
						System.out.println("ID"+s1.getGRAPH1_ID());
					}
					m.setImage(s);
					
					Message.messages.add(m);
					initList();
				}				
			}
		});
		btnSend.setBounds(829, 767, 171, 144);
		
		contentPane.add(btnSend);
		lblStatement.setBounds(0, 909, 1278, 36);
		
		contentPane.add(lblStatement);
		list_1.setBounds(1001, 516, 277, 395);
		
		contentPane.add(list_1);
		lblStudentNameList.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentNameList.setBounds(999, 468, 279, 32);
		
		contentPane.add(lblStudentNameList);
		lblCurrentRoomLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentRoomLocation.setBounds(999, 2, 279, 458);
		
		contentPane.add(lblCurrentRoomLocation);
		comboBox.setBounds(49, 845, 733, 54);
		
		comboBoxInit();
		
		contentPane.add(comboBox);
		
		contentPane.add(textField);
		
		/*JLabel pingLabel = new JLabel("images/image1.jpg", JLabel.LEFT);
		pingLabel.setIcon(new ImageIcon("images/image1.jpg"));
		JPanel pingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pingPanel.add(pingLabel);
		Object[] panels = {pingPanel};
		list.setCellRenderer(new ImageListCellRenderer());
		list.setListData(panels);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setFixedCellHeight(46);
		*/
		initList();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textField.setBounds(48, 785, 734, 62);
		textField.setColumns(10);
	}
	private void initList(){
		list.setCellRenderer(new ImageListCellRenderer());
		ChatSlice.refreshChats();
		shows=new Object[ChatSlice.chats.size()];
		for(int i=0;i<ChatSlice.chats.size();i++){
			shows[i]=ChatSlice.chats.get(i);
		}
		list.setListData(shows);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setFixedCellHeight(140);
	}
	private void comboBoxInit(){
		if(comboBox_al!=null){
			comboBox.removeActionListener(comboBox_al);
			comboBox_al=null;
		}
		comboBox.removeAllItems();
		comboBox.addItem("");
		Memory.extractSENTENCE();
		for(Sentence s:Sentence.sentences){
			comboBox.addItem(s.getSENTENCE_DESCRIPTION());
		}
		comboBox_al=new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				textField.setText(comboBox.getSelectedItem().toString());
			}
			
		};
		comboBox.addActionListener(comboBox_al);
	}
	private void initData() {
		// TODO Auto-generated method stub
		comboBoxInit();
		initList();
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
class ImageListCellRenderer implements ListCellRenderer
{
  /**
   * From <a title="http://java.sun.com/javase/6/docs/api/javax/swing/ListCellRenderer.html:" href="http://java.sun.com/javase/6/docs/api/javax/swing/ListCellRenderer.html:">http://java.sun.com/javase/6/docs/api/javax/swing/ListCellRenderer.html:</a>
   * 
   * Return a component that has been configured to display the specified value. 
   * That component's paint method is then called to "render" the cell. 
   * If it is necessary to compute the dimensions of a list because the list cells do not have a fixed size, 
   * this method is called to generate a component on which getPreferredSize can be invoked. 
   * 
   * jlist - the jlist we're painting
   * value - the value returned by list.getModel().getElementAt(index).
   * cellIndex - the cell index
   * isSelected - true if the specified cell is currently selected
   * cellHasFocus - true if the cell has focus
   */
  public Component getListCellRendererComponent(JList jlist, 
                                                Object value, 
                                                int cellIndex, 
                                                boolean isSelected, 
                                                boolean cellHasFocus)
  {
    if (value instanceof JPanel)
    {
      Component component = (Component) value;
      component.setForeground (Color.white);
      component.setBackground (isSelected ? UIManager.getColor("Table.focusCellForeground") : Color.white);
      return component;
    }
    else
    {
      // TODO - I get one String here when the JList is first rendered; proper way to deal with this?
      //System.out.println("Got something besides a JPanel: " + value.getClass().getCanonicalName());
      return new JLabel("???");
    }
  }
}
