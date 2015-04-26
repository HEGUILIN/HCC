package com.HCC.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.HCC.Model.GRAPH;
import com.HCC.Model.Sentence;
import com.HCC.Utils.Memory;

public class WebConnect {
	public final static int CONNECT_TO_REMOTE_SERVER=0;
	public final static int CONNECT_TO_LOCAL_SERVER=1;
	private String ip="";
	private int port=0;
	private int apache_port=8080;
	public WebConnect(){
		int i=1;
		if(i==CONNECT_TO_REMOTE_SERVER){
			ip="137.189.97.84";
			port=50000;
		}else if(i==CONNECT_TO_LOCAL_SERVER){
			ip="127.0.0.1";
			port=50000;
		}
	}
	public void getAllSentenceId(){
		String url = "http://"+ip+":"+port+"/getAllSentenceId"; 
		HttpClient http_client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url); 
		HttpResponse response = null;
		try {
			response = http_client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		String text="";
		try {
			text = EntityUtils.toString(entity, HTTP.UTF_8);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sentence.sentences.clear();
		JSONObject jo=JSONObject.fromObject(text);
		JSONArray ja=jo.getJSONArray("sentence id list");
		for(int i=0; i<ja.size();i++){
			JSONObject jo_temp=ja.getJSONObject(i);
			Sentence s=new Sentence();
			s.setSENTENCE_DESCRIPTION(jo_temp.getString("sentence description"));
			s.setSENTENCE_ID(jo_temp.getString("sentence id"));
			JSONArray ja_temp=jo_temp.getJSONArray("graph id list");
			s.setgraph_num(ja_temp.size());
			for(int j=0;j<ja_temp.size();j++){
				s.setGRAPH_ID(j, ja_temp.getJSONObject(j).getString("graph id"));
			}
			Sentence.sentences.add(s);
		}
		
		Memory.writeSENTENCE();
	}
	
	public String createSentence(String description,List<String> imagelist){
		String url = "http://"+ip+":"+port+"/create_sentence"; 
		HttpClient http_client = new DefaultHttpClient(); 
		HttpPost request = new HttpPost(url); 
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("operation", "create sentence"));
		params.add(new BasicNameValuePair("sentence description", description)); 
		params.add(new BasicNameValuePair("num", ""+imagelist.size())); 
		for(int i=0;i<imagelist.size();i++){
			params.add(new BasicNameValuePair("graph_id"+i, new GRAPH().getGRAPH_ID(imagelist.get(i)))); 
		}
		try {
			request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpResponse response = null;
		try {
			response = http_client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		HttpEntity entity = response.getEntity(); 
		String text="";
		try {
			text = EntityUtils.toString(entity, HTTP.UTF_8);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	public String register(String username,String password,int admin,String email){
		String url = "http://"+ip+":"+port+"/register?username="+username+"&password="+password+"&admin="+admin+"&email="+email; 
		HttpClient http_client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = http_client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		String text="";
		try {
			text = EntityUtils.toString(entity, HTTP.UTF_8);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;
	}
	public String register(String username,String password,String email){
		String url = "http://"+ip+":"+port+"/register?username="+username+"&password="+password+"&email="+email; 
		HttpClient http_client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = http_client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		String text="";
		try {
			text = EntityUtils.toString(entity, HTTP.UTF_8);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;
	}
	public String login(String username,String password){
		String url = "http://"+ip+":"+port+"/login?username="+username+"&password="+password; 
		HttpClient http_client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = http_client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		String text="";
		try {
			text = EntityUtils.toString(entity, HTTP.UTF_8);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;
	}
	public void getAllGraphIdandStore(){
		getAllGraphId();
		List<String> graph_id_delete_list=new LinkedList<String>();
		for(int i=0;i<GRAPH.GRAPH_list.size();i++){
			boolean res=downloadGraph(GRAPH.GRAPH_list.get(i));
			if(res==false){
				graph_id_delete_list.add(GRAPH.GRAPH_list.get(i).getGRAPH_ID());
			}
		}
		for(String s:graph_id_delete_list ){
			for(int i=0;i<GRAPH.GRAPH_list.size();i++){
				if(GRAPH.GRAPH_list.get(i).getGRAPH_ID().equals(s)){
					GRAPH.GRAPH_list.remove(i);
					break;
				}
			}
		}
		Memory.writeGRAPH();
	}
	public void getAllGraphId(){
		String url = "http://"+ip+":"+port+"/getAllGraphId"; 
		HttpClient http_client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url); 
		HttpResponse response = null;
		try {
			response = http_client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity(); 
		String text="";
		try {
			text = EntityUtils.toString(entity, HTTP.UTF_8);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jo =JSONObject.fromObject(text);
		JSONArray ja=jo.getJSONArray("graph id list");
		GRAPH.GRAPH_list.clear();
		for(int i=0;i<ja.size();i++){
			GRAPH g=new GRAPH();
			JSONObject jo_temp=ja.getJSONObject(i);
			g.setGRAPH_ONLINE_LINK(jo_temp.getString("graph link"));
			g.setGRAPH_ID(jo_temp.getString("graph id"));
			g.setGRAPH_DESCRIPTION(jo_temp.getString("graph description"));
			GRAPH.GRAPH_list.add(g);
		}
		return ;
	}
	public boolean downloadGraph(GRAPH g){
		if(g.getGRAPH_ONLINE_LINK().equals("")){
			System.out.println(g.getGRAPH_ID()+" has no online link");
		}else{
			URL url = null;
			try {
				url = new URL(g.getGRAPH_ONLINE_LINK());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			boolean judge=isUrlValid(url);
			if(judge==false){
				System.out.println(g.getGRAPH_ONLINE_LINK()+"is invalid");
				return false;
			}else{
				
			}
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			conn.setReadTimeout(10000); 
			conn.setConnectTimeout(15000); 
			try {
				conn.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} conn.setDoInput(true);
			try {
				conn.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				int response = conn.getResponseCode();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				InputStream is = conn.getInputStream();
				FileOutputStream fout=new FileOutputStream(new File(System.getProperty("user.dir")+"/images/"+g.getGRAPH_DESCRIPTION()+".png"));
				IOUtils.copy(is,fout);
				is.close();
				fout.close();
				g.setGRAPH_LINK("images/"+g.getGRAPH_DESCRIPTION()+".png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	private boolean isUrlValid(URL url) {
		// TODO Auto-generated method stub
		HttpURLConnection url_url_connection = null;
		try {
			url_url_connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int code = 0;
		try {
			code = url_url_connection.getResponseCode();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(code!=200){
			return false;
		}else{
			return true;
		}
	}
	public String uploadHeadPortrait(String imagefile,String username){
		
		return "";
	}
	public String downloadHeadPortrait(String username){
		
		return "imagefilename";
	}
	public String createNewChattingRoom(String username,String roomname,String beaconid){
		String url = "http://"+ip+":"+port+"/create_chatting_room"; 
		HttpClient http_client = new DefaultHttpClient(); 
		HttpPost request = new HttpPost(url); 
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("username", username)); 
		params.add(new BasicNameValuePair("roomname", roomname)); 
		params.add(new BasicNameValuePair("beaconid", beaconid)); 
		try {
			request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpResponse response = null;
		try {
			response = http_client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		HttpEntity entity = response.getEntity(); 
		String text="";
		try {
			text = EntityUtils.toString(entity, HTTP.UTF_8);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return text;
	}
}
