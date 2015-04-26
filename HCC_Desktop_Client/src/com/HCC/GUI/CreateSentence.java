package com.HCC.GUI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

import com.HCC.Controller.WebConnect;
import com.HCC.Model.GRAPH;
import com.HCC.Utils.Memory;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.JTextField;

public class CreateSentence extends JPanel {
	private final JButton btnCreateSentence = new JButton("Create");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JPanel panel = new JPanel();
	private final JButton btnNewButton = new JButton("Clear");
	private final ChatSlice_ImageList panel_1 = new ChatSlice_ImageList();
	private final JLabel lblNewLabel = new JLabel("Status");
	private final JTextField textField = new JTextField();
	private final JLabel lblNewLabel_1 = new JLabel("Enter Description");
	private final JLabel label_1 = new JLabel("");
	/**
	 * Create the panel.
	 */
	public CreateSentence() {
		super();
		textField.setBounds(179, 881, 794, 70);
		textField.setColumns(10);
		initGUI();
		initGrid();
	}
	private void initGUI() {
		setLayout(null);
		this.setBounds(0, 0, 1230, 1100);
		btnCreateSentence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String description=textField.getText();
				if(description.equals("")){
					label_1.setForeground(Color.red);
					label_1.setText("Enter description of sentence");
				}else{
					String res=updateCreateSentence(description);
					btnNewButton.doClick();
				}
				
			}
		});
		btnCreateSentence.setBounds(1095, 667, 99, 208);
		
		add(btnCreateSentence);
		scrollPane.setBounds(15, 16, 1176, 635);
		
		add(scrollPane);
		
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.imagelist.clear();
				panel_1.setImage();
				textField.setText("");
			}
		});
		btnNewButton.setBounds(988, 667, 99, 208);
		
		add(btnNewButton);
		panel_1.setBounds(15, 677, 958, 198);
		
		add(panel_1);
		lblNewLabel.setBounds(18, 961, 1176, 43);
		
		add(lblNewLabel);
		
		add(textField);
		lblNewLabel_1.setBounds(15, 881, 151, 68);
		
		add(lblNewLabel_1);
		label_1.setBounds(987, 881, 207, 70);
		
		add(label_1);
	}
	void initGrid(){
		Memory.extractGRAPH();
		GridBagLayout gl=new GridBagLayout();
		panel.setLayout(gl);
		panel.removeAll();
		List<JLabel> jls=new LinkedList<JLabel>();
		for (int i = 0; i < GRAPH.GRAPH_list.size(); i++) {
			final int ii=i;
            GridBagConstraints gbc = makeGbc(i%4, i/4);
            JLabel label = new JLabel();
            System.out.println(""+i+" "+GRAPH.GRAPH_list.get(i).getGRAPH_LINK());
            Image img = new ImageIcon(GRAPH.GRAPH_list.get(i).getGRAPH_LINK()).getImage();
            Image newimg = img.getScaledInstance(160, 128,  java.awt.Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(newimg));
            label.addMouseListener(new MouseAdapter() {
            	public void mouseClicked(MouseEvent me) {
                   System.out.println(ii);
                   panel_1.imagelist.add(GRAPH.GRAPH_list.get(ii).getGRAPH_LINK());
                   panel_1.setImage();
                }

            });
            panel.add(label, gbc);
        }
	}
	private GridBagConstraints makeGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 10.0;
        gbc.weighty = 10.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        //gbc.anchor = (x == 0) ? GridBagConstraints.LINE_START : GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }
	String updateCreateSentence(String description){
		return new WebConnect().createSentence(description, panel_1.imagelist);
	}
}
