package com.HCC.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import net.sf.json.JSONObject;

public class ClientSocket {
	static Socket clientsocket=null;
	final private static String IP="137.189.97.84";
	final private static int port = 50000;
	static DataInputStream fromserver=null;
	static DataOutputStream outtoserver=null;
	ClientSocket(){
		try {
			if(clientsocket==null){
				
				clientsocket=new Socket(IP,port);
				fromserver=new DataInputStream(clientsocket.getInputStream());
				outtoserver=new DataOutputStream(clientsocket.getOutputStream());
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String sendMessage(JSONObject dataouttoserver){
		try {
			if(outtoserver!=null){
				outtoserver.write(dataouttoserver.toString().getBytes());
				
			}else{
				outtoserver=new DataOutputStream(clientsocket.getOutputStream());
				outtoserver.write(dataouttoserver.toString().getBytes());
			}
			return "send success"; 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	public static JSONObject resvMessage(){
		JSONObject datafromserver=null;
		try {
			if(fromserver==null){
				fromserver=new DataInputStream(clientsocket.getInputStream());
			}
			int limit=fromserver.available();
			byte[] datafromserver_bytes=new byte[1024];
			fromserver.read(datafromserver_bytes);
			String temp=new String(datafromserver_bytes);
			datafromserver=JSONObject.fromObject(temp);
			return datafromserver;
			 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	public static Socket getClientSocket(){
		return clientsocket;
	}
	public static void refreshSocket(){
		try {
			clientsocket=new Socket(IP,port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendInt(int i){
		
		try {
			outtoserver.writeInt(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendLong(long i){
		
		try {
			outtoserver.writeLong(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendBytes(byte[] b){
		
		try {
			outtoserver.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
