package com.HCC.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import com.HCC.Utils.ClientSocket;

import net.sf.json.JSONObject;

public class BaseGet {
	JSONObject data_send=null;
	JSONObject data_resv=null;
	BaseGet(){
		data_send=new JSONObject();
		if(ClientSocket.getClientSocket()==null){
			ClientSocket.refreshSocket();
		}
	}
	public JSONObject register(String name,String password){
		this.refresh();
		data_send.put("NAME", name);
		data_send.put("PASSWORD", password);
		data_send.put("OPERATION", "Register");
		this.sendMessage(data_send);
		data_resv=ClientSocket.resvMessage();
		return data_resv;
	}
	void refresh(){
		data_send.clear();
	}
	public void sendMessage(JSONObject jo){
		String feed=ClientSocket.sendMessage(jo);
		if(ClientSocket.sendMessage(jo)==null){
			ClientSocket.refreshSocket();
			ClientSocket.sendMessage(jo);
		}		
	}
	public JSONObject sendHeadPortrait(File imagefile){
		this.refresh();
		
		this.data_send.put("operation", "Send Head Portrait");
		
		this.sendMessage(data_send);
		
		long filelength=imagefile.length();
		
		FileInputStream filein = null;
		try {
			filein = new FileInputStream(imagefile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int available_length = 0;
		try {
			available_length = filein.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(available_length>=filelength){
			byte[] filecontent =new byte[available_length];
			try {
				filein.read(filecontent);
				filein.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] images_bytes=Base64.getEncoder().encode(filecontent);
			ClientSocket.sendInt(images_bytes.length);
			ClientSocket.sendBytes(images_bytes);
			
		}
		this.data_resv=ClientSocket.resvMessage();
		return data_resv;
	}
}
