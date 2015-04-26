package com.iems5722.GroupMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iems5722.GroupMap.MainActivity.FileConnection;
import com.iems5722.Model.District;
import com.iems5722.Model.Message;
import com.iems5722.Model.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class LBSChatActivity extends Activity {
	Context context=null;
	ListView listview=null;
	Button sendbutton=null;
	Button backbutton=null;
	EditText sendcontent=null;
	TextView textview=null;
	Person mp=null;
	String current_district="";
	Message latest_message=new Message();
	Timer timer; 
    TimerTask timerTask; 
    final Handler handler = new Handler();
    ChatListAdapter cla=null;
    boolean timer_continue=true;
    int timer_count=0;
    boolean update=true;
    List<Message> messages=new LinkedList<Message>();
    //List<Message> messages_saved=new LinkedList<Message>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lbs_chat);
		
		listview=(ListView)findViewById(R.id.listView1);
		sendbutton=(Button)findViewById(R.id.sendbutton2);
		backbutton=(Button)findViewById(R.id.backbutton);
		sendcontent=(EditText)findViewById(R.id.sendcontent2);
		textview=(TextView)findViewById(R.id.district);
		
		
		mp=MainActivity.mp;
		Log.i("currentuser", mp.name);
		
		Bundle in =this.getIntent().getExtras();
		if(in!=null){
			current_district=in.getString("address");
			textview.setText(current_district);
		}else{
			Log.e("district","cannot confirm where is the district");
		}
		sendbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message newmessage=new Message();
				newmessage.sender=mp.name;
				newmessage.message=sendcontent.getText().toString();
				newmessage.sendtime=System.currentTimeMillis();
				newmessage.type=Message.TYPE_RIGHT_MESSAGE;
				newmessage.sendermac=mp.mac;
				messages.add(newmessage);
				latest_message=newmessage;
				listAdapt();
				sendcontent.setText("");
				new HTTPConnection(newmessage).execute(current_district);
			}
		});
		
		backbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LBSChatActivity.this.finish();
			}
		});
		listAdapt();
		initTimerAndTask();
	}
	public void listAdapt(){
		int index = listview.getFirstVisiblePosition();
		View v = listview.getChildAt(0);
		int top = (v == null) ? 0 : v.getTop();
		if(cla==null){
			cla=new ChatListAdapter(this, messages);
			listview.setAdapter(cla);
		}else{
			cla.notifyDataSetChanged();
		}
		listview.setSelectionFromTop(index, top);
	}
	private void initTimerAndTask() {
		// TODO Auto-generated method stub
		timer=new Timer();
		initializeTimerTask();
		
		timer.schedule(timerTask, 0, 1500); 
	}
	public void initializeTimerTask() {
		timerTask = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						timer_count=timer_count+1;
						if(timer_continue==true){
							timer_continue=false;
							getNews(System.currentTimeMillis()-2000);
							}
						else{
							if(timer_count%4==0)
								timer_continue=true;
						}
			}});
		}};
	}
	public void getNews(Long time){
		if(!current_district.equals(""))
			new HTTPConnection(0,time).execute(current_district);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lbschat, menu);
		//menu.add(1, 1, 1, "Map");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.action_settings) {
			return true;
		}*/ 
		if(id==R.id.map){
			Intent intent = new Intent();
            intent.setClass(LBSChatActivity.this, MainActivity.class);
            startActivity(intent);
		}else if (id ==R.id.about){
			Intent intent = new Intent();
	        intent.setClass(LBSChatActivity.this, AboutActivity.class);
	        startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
    protected void onResume() {
        super.onResume();
        
    }
	class HTTPConnection extends AsyncTask<String,Void,String> {
		final String TAG="HTTPConnection";
		String message="";
		int type=0;
		long time;
		Message m;
		public HTTPConnection(int type,long time){
			super();
			this.type=type;
			this.time=time;
		}
		public HTTPConnection(Message m){
			super();
			this.m=m;
			this.type=1;
		}

		protected String doInBackground(String... ps) {
			if(type==0){
				sendAndReceiveMessageWithServer(0,time,m,ps[0]);
				return null;
			}else if (type==1){
				sendAndReceiveMessageWithServer(1,time,m,ps[0]);
				return null;
			}
			return null;
		}
		protected void onPostExecute(String text) {
			//MainActivity.this.writePersonList(Person.pl);
			listAdapt();
			timer_continue=true;
		}
	}
	private void sendAndReceiveMessageWithServer(int type,long time,Message m,String p){
		String url="";
		if(type==0)
			url = "http://1.mapapper.sinaapp.com/lbschatget";
			
		else if(type==1)
			url = "http://1.mapapper.sinaapp.com/lbschat";
		URL url_url = null;
		try {
			url_url = new URL("http://1.mapapper.sinaapp.com/test");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection url_url_connection = null;
		try {
			url_url_connection = (HttpURLConnection) url_url.openConnection();
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
			Log.e("HTTP Connection Error",""+code);
			return;
		}else{
			Log.e("HTTP Connection Return",""+code);
		}
		//Person p=ps[0];
		HttpClient http_client=null;
		//Log.e("type", ""+type);
		//Log.e("type", ""+mp.mac);
		String text="";
		if(type==0){
			http_client = new DefaultHttpClient(); 
			HttpPost request = new HttpPost(url); 
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("place", getUnicode(p)));
			params.add(new BasicNameValuePair("time", ""+time));
			try {
				request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpResponse response = null;
			try {
				response = http_client.execute(request);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpEntity entity = response.getEntity(); 
			try {
				text = EntityUtils.toString(entity, HTTP.UTF_8);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONArray ja=null;
			messages.clear();
			try {
				ja=new JSONArray(text);
				for(int i=0;i<ja.length();i++){
					JSONObject jo=ja.getJSONObject(i);
					if(jo.has("name")){
						//Log.e("MAC",""+mp.mac+"  "+jo.getString("mac")+"  "+jo.getString("content"));
						if(!jo.getString("mac").equals(mp.mac)){
							Message m_new=new Message();
							m_new.message=jo.getString("content");
							m_new.sender=jo.getString("name");
							m_new.sendtime=Long.parseLong(jo.getString("time"));
							m_new.type=Message.TYPE_LEFT_MESSAGE;
							m_new.sendermac=jo.getString("mac");
							//Log.e("content",jo.getString("content"));
							messages.add(m_new);
						}else{
							//Log.e("content222",jo.getString("content"));
							Message m_new=new Message();
							m_new.message=jo.getString("content");
							m_new.sender=jo.getString("name");
							m_new.sendtime=Long.parseLong(jo.getString("time"));
							m_new.sendermac=jo.getString("mac");
							m_new.type=Message.TYPE_RIGHT_MESSAGE;
							messages.add(m_new);
						}
					}
				}
				//messagescheckUpdated(messages,messages_saved);
				if(!latest_message.sendermac.equals("") && !latest_message.message.equals("")){
					int i=0;
					for(i=0;i<messages.size();i++){
						if(messages.get(i).sendermac.equals(latest_message.sendermac) &&
							messages.get(i).message.equals(latest_message.message)){
							Log.e("111  "+messages.get(i).sendermac,latest_message.sendermac);
							Log.e("111  "+messages.get(i).message,latest_message.message);
							break;
						}
					}
					if(i==messages.size()){
						messages.add(latest_message);
						Log.e(latest_message.sendermac,latest_message.message);
						
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(type==1){
			http_client = new DefaultHttpClient(); 
			HttpPost request = new HttpPost(url); 
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", m.sender));
			params.add(new BasicNameValuePair("place", getUnicode(p)));
			params.add(new BasicNameValuePair("content", m.message));
			params.add(new BasicNameValuePair("time", ""+m.sendtime));
			//Log.e("mac","");
			params.add(new BasicNameValuePair("mac", ""+m.sendermac));
		//	Log.e("mac",""+m.sendermac);
			try {
				request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpResponse response = null;
			try {
				response = http_client.execute(request);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			text="";
			try {
				HttpEntity entity = response.getEntity();
				text = EntityUtils.toString(entity, HTTP.UTF_8);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	void checkUpdated(List<Message> messages2, List<Message> messages_saved2){
		if(messages_saved2.size()==0){
			update=true;
			return;
		}
		
		if(messages2.size()==messages_saved2.size()){
			for(int i=0;i<messages2.size();i++){
				if(messages2.get(i).equals(messages_saved2.get(i))){
					
				}else{
					update=false;
					return;
				}
			}
		}else{
			update=false;
			return;
		}
		update=true;
	}
static String getUnicode(String s) {

        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
