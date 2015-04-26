package com.HCC.GUI;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.HCC.Model.Message;

public class ChatSlice extends JPanel {
	private final JLabel label = new JLabel("");
	private final JLabel label_1 = new JLabel("");
	private final JLabel label_2 = new JLabel("");
	private final JLabel label_3 = new JLabel("");
	private final JLabel label_4 = new JLabel("");
	private final JLabel label_5 = new JLabel("");
	public List<String> imagelist=new LinkedList<String>();
	public static List<ChatSlice> chats=new LinkedList<ChatSlice>();
	/**
	 * Create the panel.
	 */
	public ChatSlice() {
		initGUI();
		this.setBounds(0, 0, 1000, 140);
	}
	private void initGUI() {
		setLayout(null);
		label.setBounds(0, 0, 160, 128);
		
		add(label);
		label_1.setBounds(180, 0, 160, 128);
		
		add(label_1);
		label_2.setBounds(340, 0, 160, 128);
		
		add(label_2);
		label_3.setBounds(500, 0, 161, 128);
		
		add(label_3);
		
		label_5.setBounds(840, 0, 160, 128);
		
		add(label_4);
		label_4.setBounds(660, 0, 160, 128);
		
		add(label_5);
	}
	public void setImage(String label, String imagecontent){
		if(label.equals("label")){
			setIcon(this.label,imagecontent);
		}else if(label.equals("label_1")){
			setIcon(this.label_1,imagecontent);
		}else if(label.equals("label_2")){
			setIcon(this.label_2,imagecontent);
		}else if(label.equals("label_3")){
			setIcon(this.label_3,imagecontent);
		}else if(label.equals("label_4")){
			setIcon(this.label_4,imagecontent);
		}else if(label.equals("label_5")){
			setIcon(this.label_5,imagecontent);
		}
	}
	public void setIcon(JLabel l, String imagecontent){
		if(!imagecontent.equals("")){
			Image img = new ImageIcon(imagecontent).getImage();
            Image newimg = img.getScaledInstance(160, 128,  java.awt.Image.SCALE_SMOOTH);
			l.setIcon(new ImageIcon(newimg));
		}else{
			l.setIcon(null);
		}
	}
	public static void refreshChats(){
		chats.clear();
		for(Message m:Message.messages){
			ChatSlice cs=new ChatSlice();
			for(int i=0;i<6;i++){
				if(!m.getImage("image"+i).equals("")){
					if(i==0)
						cs.setImage("label", m.getImage("image"+i));
					else{
						System.out.println(m.getImage("image"+i));
						cs.setImage("label_"+i, m.getImage("image"+i));
					}
				}
			}
			chats.add(cs);
		}
	}
	public String getImageString(String label){
		if(label.equals("label")){
			return this.label.getText();
		}else if(label.equals("label_1")){
			return this.label_1.getText();
		}else if(label.equals("label_2")){
			return this.label_2.getText();
		}else if(label.equals("label_3")){
			return this.label_3.getText();
		}else if(label.equals("label_4")){
			return this.label_4.getText();
		}else if(label.equals("label_5")){
			return this.label_5.getText();
		}else 
		return "";
	}
}
