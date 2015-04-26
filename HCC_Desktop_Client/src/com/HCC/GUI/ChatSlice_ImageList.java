package com.HCC.GUI;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.HCC.Model.GRAPH;
import com.HCC.Model.Message;

public class ChatSlice_ImageList extends JPanel {
	private final JLabel label_1 = new JLabel("");
	private final JLabel label_2 = new JLabel("");
	private final JLabel label_3 = new JLabel("");
	private final JLabel label_4 = new JLabel("");
	public List<String> imagelist=new LinkedList<String>();
	/**
	 * Create the panel.
	 */
	public ChatSlice_ImageList() {
		initGUI();
		this.setBounds(0, 0, 958, 198);
	}
	private void initGUI() {
		setLayout(null);
		label_1.setBounds(15, 0, 230, 198);
		
		add(label_1);
		label_2.setBounds(245, 0, 230, 198);
		
		add(label_2);
		label_3.setBounds(475, 0, 230, 198);
		
		add(label_3);
		
		add(label_4);
		label_4.setBounds(705, 0, 230, 198);
	}
	public void setImage(String label, String imagecontent){
		if(label.equals("label_1")){
			setIcon(label_1,imagecontent);
		}else if(label.equals("label_2")){
			setIcon(label_2,imagecontent);
		}else if(label.equals("label_3")){
			setIcon(label_3,imagecontent);
		}else if(label.equals("label_4")){
			setIcon(label_4,imagecontent);
		}
	}
	public String getImageString(String label){
		if(label.equals("label_1")){
			return this.label_1.getText();
		}else if(label.equals("label_2")){
			return this.label_2.getText();
		}else if(label.equals("label_3")){
			return this.label_3.getText();
		}else if(label.equals("label_4")){
			return this.label_4.getText();
		}else 
		return "";
	}
	public void setImage(){
		for(int i=0;i<imagelist.size();i++){
			if(i<=3)
				this.setImage("label_"+(i+1), imagelist.get(i));
		}
		for(int i=imagelist.size();i<=3;i++){
			this.setImage("label_"+(i+1), "");
		}
	}
	public void setIcon(JLabel l, String imagecontent){
		if(!imagecontent.equals("")){
			Image img = new ImageIcon(imagecontent).getImage();
            Image newimg = img.getScaledInstance(230, 198,  java.awt.Image.SCALE_SMOOTH);
			l.setIcon(new ImageIcon(newimg));
		}else{
			l.setIcon(null);
		}
	}
}
