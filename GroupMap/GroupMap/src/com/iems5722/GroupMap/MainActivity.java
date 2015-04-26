package com.iems5722.GroupMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format.Field;
import java.util.ArrayList;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapManager.OfflineMapDownloadListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.iems5722.Model.Person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.SyncStateContract.Constants;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationSource, AMapLocationListener, OfflineMapDownloadListener, OnGeocodeSearchListener{
	
	Context context=null;
	ListView listview=null;
	Button sendbutton=null;
	EditText edittext=null;
	
	private MapView mapView=null;
	private AMap aMap=null;
	
	private GeocodeSearch geocoderSearch;
	
	private OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;

	private OfflineMapManager amapManager;
    
	public static Person mp=null;
	
	String provinceTouched="";
	String cityTouched="";
	String districtTouched="";
	
	boolean addressUpdated=false;
	
	double timeline=0.0;
	Timer timer; 
    TimerTask timerTask; 
    final Handler handler = new Handler();
    
    static boolean timer_continue=true;
    static int timer_count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        init();

		sendbutton=(Button)findViewById(R.id.sendbutton);
		listview=(ListView)findViewById(R.id.listView1);
		edittext=(EditText)findViewById(R.id.sendcontent);
		context=this.getApplicationContext();
		mp=new Person();
		new FileConnection("read").execute(mp);
		
		mp.showtime=System.currentTimeMillis();
		mp.mark=null;
		
		WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String address = info.getMacAddress();
		mp.mac=address;
		
		Log.i("local mac address",mp.mac);
		
		mp.isCurrent=true;
		Person.pl.add(mp);
		
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
		
		MyOrientationDetector detector;

		detector = new MyOrientationDetector(this, SensorManager.SENSOR_DELAY_NORMAL);
		
		aMap.setOnMapLongClickListener(new OnMapLongClickListener(){

			@Override
			public void onMapLongClick(LatLng arg0) {
				// TODO Auto-generated method stub
				LatLonPoint llp=new LatLonPoint(arg0.latitude,arg0.longitude);
				goToLBS(llp);
			}
		});
		
		sendbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mp.new_message=edittext.getText().toString();
				mp.showtime=System.currentTimeMillis();
				drawMarkers(Person.pl);
				new HTTPConnection(1).execute(mp);
				edittext.setText("");
			}
		});
		getOverflowMenu();
		//drawMarkers(Person.pl);		
	}
	public class MyOrientationDetector extends OrientationEventListener{
	    public MyOrientationDetector( Context context ) {
	        super(context );
	    }
	    public MyOrientationDetector(Context context, int rate){
	    	super(context, rate);
	    }
	    @Override
	    public void onOrientationChanged(int orientation) {
	    	int currentOrientation = 0;
			if(currentOrientation!=Configuration.ORIENTATION_LANDSCAPE)  
	    		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  

	    }
	}

	private void getOverflowMenu() {
	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private void goToLBS(LatLonPoint llp){
		//LatLonPoint llp=new LatLonPoint(arg0.latitude,arg0.longitude);
		getAddress(llp);
	}
	private String[] getAddress(LatLonPoint llp){
		RegeocodeQuery query = new RegeocodeQuery(llp, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
		return null;
	}
	private void initTimerAndTask() {
		// TODO Auto-generated method stub
		timer=new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 0, 1000); 
	}
	public void initializeTimerTask() {
		timerTask = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 handler.post(new Runnable() {
					@Override
					public void run() {
						timer_count=timer_count+1;
						// TODO Auto-generated method stub
						if(timer_continue==true){
							timer_continue=false;
							new HTTPConnection(0).execute(mp);
							Log.e("task","start");
						}else{
							if(timer_count%5==0)
								timer_continue=true;
							Log.e("task","pause");
						}
			}});
		}};
	}
	public void stoptimertask() { 
	    //stop the timer, if it's not already null 
	    if (timer != null) { 
	        timer.cancel(); 
	        timer = null; 
	    } 
	}
	private void showGroupDialog(){
			final EditText inputServer = new EditText(MainActivity.this);
			inputServer.setText(mp.group);
	        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	        builder.setTitle("Enter group name").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
	        		.setNegativeButton("Cancel", null);
	        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface dialog, int which) {
	            	mp.group=inputServer.getText().toString();
	            	new FileConnection("write").execute(mp);
	             }
	        });
	        builder.show();
		
	}
	private void showNameDialog(){
		final EditText inputServer = new EditText(MainActivity.this);
		inputServer.setText(mp.name);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Enter your name").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
               mp.name=inputServer.getText().toString();
               new FileConnection("write").execute(mp);
             }
        });
        builder.show();
	}
	private void getGroupMembers(Person lmp){
		if(!lmp.longitude.equals("") && !lmp.latitude.equals("")){
			mp.groupcheck++;
			//String sendstr=edittext.getText().toString();
			edittext.setText("");
			new HTTPConnection(0).execute(lmp);
		}
	}
	private void drawMarkers(List<Person> pl) {
		// TODO Auto-generated method stub
		//Log.e("time",""+System.currentTimeMillis());
		Log.e("person","num: "+pl.size());
		for(Person pe:pl){
			if((System.currentTimeMillis()-pe.showtime)/1000<=5){
				if(pe.mark==null){
					if(pe.longitude!=0.0){
						Log.e("mark null","null");
						Log.e("MMM11111",""+(pe.mark==null));
						if(!pe.message.equals(pe.new_message) && !pe.new_message.equals("")){
							pe.message=pe.new_message;
							pe.new_message="";
							pe.refreshMarkerOptions();
							pe.mark=aMap.addMarker(pe.mo);
							pe.mark.showInfoWindow();
							pe.ismarked=true;
							Log.e("MMM",""+(pe.mark==null));
						}
					}else{
						Log.e("drawMarkers",pe.name+" has not init the longitude");
					}
				}else{
					if(pe.mark.isInfoWindowShown()){
						if(!pe.message.equals(pe.new_message) && !pe.new_message.equals("")){
							Log.e("info close","false");
							pe.mark.setTitle(pe.name+" say:");
							pe.mark.setSnippet(pe.new_message);
							pe.message=pe.new_message;
							pe.new_message="";
							pe.mark.setPosition(new LatLng(pe.latitude, pe.longitude));
							pe.mark.showInfoWindow();						}
					}else{
						if(!pe.message.equals(pe.new_message) && !pe.new_message.equals("")){
							Log.e("mark",""+pe.mark.isInfoWindowShown());
							Log.e("info close","true");
							pe.mark.setTitle(pe.name+" say:");
							pe.message=pe.new_message;
							pe.new_message="";
							pe.mark.setSnippet(pe.message);
							pe.mark.setPosition(new LatLng(pe.latitude, pe.longitude));
							pe.mark.showInfoWindow();
						}
					}
				}
			}else{
				if(pe.mark!=null){
					//pe.mark.destroy();
					//pe.mark=null;
					pe.mark.hideInfoWindow();
				}
			}
		}
	}
	/**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        initTimerAndTask();
        new FileConnection("read").execute(mp);
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
        stoptimertask();
        new FileConnection("write").execute(mp);
    }
     
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        stoptimertask();
        new FileConnection("write").execute(mp);
    }

	private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }
	private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	 	//menu.add(1, 1, 1, "NAME");
	 	//menu.add(2, 2, 2, "GROUP");
	 	//menu.add(3, 3, 3, "LBS");
	 	
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
		} */
		if(id==R.id.name){
			showNameDialog();
		}else if(id==R.id.group){
			showGroupDialog();
		}else if(id==R.id.lbs){
			while(true){
				if(mp.latitude!=0){
					break;
				}
			}
			LatLonPoint llp=new LatLonPoint(mp.latitude,mp.longitude);
			goToLBS(llp);
		}else if(id==R.id.about){
			Intent intent = new Intent();
	        intent.setClass(MainActivity.this, AboutActivity.class);
	        startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
            	Log.i("onLocationChanged",""+amapLocation.getLongitude());
            	Log.i("onLocationChanged",""+amapLocation.getLatitude());
            	mp.longitude=amapLocation.getLongitude();
            	mp.latitude=amapLocation.getLatitude();
            	mp.showtime=System.currentTimeMillis();
            	this.drawMarkers(Person.pl);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            }
        }
	}
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
            //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
            //在定位结束后，在合适的生命周期调用destroy()方法      
            //其中如果间隔时间为-1，则定位只定一次
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
        }

	}
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;

	}
	@Override
	public void onDownload(int arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}
	class HTTPConnection extends AsyncTask<Person,Void,String> {
		final String TAG="HTTPConnection";
		String message="";
		int type=0;
		public HTTPConnection(int type){
			super();
			this.type=type;
		}

		public HTTPConnection(String string) {
			// TODO Auto-generated constructor stub
			message=string;
			type=1;
		}

		protected String doInBackground(Person... ps) {
			if(type==0){
				sendAndReceiveMessageWithServer(0,ps[0]);
				return null;
			}else if (type==1){
				sendAndReceiveMessageWithServer(1,ps[0]);
				return null;
			}
			return null;
		}
		protected void onPostExecute(String text) {
			//MainActivity.this.writePersonList(Person.pl);
			drawMarkers(Person.pl);
		}
	}
	private void sendAndReceiveMessageWithServer(int type,Person p){
		String url="";
		if(type==0)
			url = "http://1.mapapper.sinaapp.com/location";
			
		else if(type==1)
			url = "http://1.mapapper.sinaapp.com/chat";
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
		}
		HttpClient http_client = new DefaultHttpClient(); 
		HttpPost request = new HttpPost(url); 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//Person p=ps[0];
		if(type==0){
			params.add(new BasicNameValuePair("name", p.name));
			params.add(new BasicNameValuePair("group", p.group));
			params.add(new BasicNameValuePair("longitude", ""+p.longitude));
			params.add(new BasicNameValuePair("latitude", ""+p.latitude));
			params.add(new BasicNameValuePair("mac", ""+p.mac));
			params.add(new BasicNameValuePair("time", ""+System.currentTimeMillis()));
		}else if(type==1){
			params.add(new BasicNameValuePair("name", p.name));
			params.add(new BasicNameValuePair("group", p.group));
			params.add(new BasicNameValuePair("content", p.message));
			params.add(new BasicNameValuePair("mac", ""+p.mac));
		}
		try {
			request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(type==0){
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
			
			String text="";
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
			JSONArray ja=null;
			try {
				ja=new JSONArray(text);
				
				for(int i=0;i<ja.length();i++){
					JSONObject jo=ja.getJSONObject(i);
					Log.e("message","p:"+jo.getString("content"));
					if(jo.has("name")){
						int j=0;
						for(j=0;j<Person.pl.size();j=j+1){
							if(Person.pl.get(j).mac.equals(""+jo.get("mac"))){
								if(Person.pl.get(j).isCurrent==false){
									if((Person.pl.get(j).message.equals(jo.getString("content")))==false){
										Person.pl.get(j).new_message=jo.getString("content");
										//Person.pl.get(j).message=jo.getString("content");
										Person.pl.get(j).longitude=Double.parseDouble(jo.getString("longitude"));
										Person.pl.get(j).latitude=Double.parseDouble(jo.getString("latitude"));
										Person.pl.get(j).name=jo.getString("name");
										Person.pl.get(j).showtime=System.currentTimeMillis();
										Person.pl.get(j).mac=jo.getString("mac");
										Log.e(Person.pl.get(j).name,jo.getString("content"));
									}
								}else{
									
								}
								break;
							}		
						}
						if(j==Person.pl.size()){
							Log.e("MACMAC",jo.getString("mac"));
							Person ap=new Person();
							ap.name=jo.getString("name");
							ap.longitude= Double.parseDouble(jo.getString("longitude"));
							ap.latitude= Double.parseDouble(jo.getString("latitude"));
							ap.mac=jo.getString("mac");
							if(!ap.message.equals(jo.getString("content"))){
								ap.new_message=jo.getString("content");
								ap.showtime=System.currentTimeMillis();
							}
							Person.pl.add(ap);
						}
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(type==1){
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
			
			String text="";
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
		timer_continue=true;
	}
	class FileConnection extends AsyncTask<Person,Void,Person>{
		String operation=null;
		FileConnection(String s){
			this.operation=s;
		}
		@Override
		protected Person doInBackground(Person... params) {
			// TODO Auto-generated method stub
			Person p=null;
			if(operation.equals("write")){
				writeCurrentUser(params[0]);
			}else if(operation.equals("read")){
				p=getCurrentUser();
			}
			return p;
		}
		protected void onPostExecute(Person p) {
			if(this.operation.equals("read")){
				if(p!=null){
					if(!p.name.equals("")){
						mp.name=p.name;
					}
					if(!p.group.equals("")){
						mp.group=p.group;	
					}
					if(!p.mac.equals("")){
						mp.mac=p.mac;
					}
				}else{
					
				}
			}else if(this.operation.equals("write")){
				
			}
		}
		
	}
	protected boolean writeCurrentUser(Person p){
		FileOutputStream fos = null;
	    ObjectOutputStream oos = null;
	    try {
	        fos = this.openFileOutput(p.localfilename, this.MODE_PRIVATE);
	        oos = new ObjectOutputStream(fos);
	        oos.writeObject(p);
	        oos.flush();
	        Log.i("w","true");
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            oos.close();
	        } catch (Exception e) {
	        }
	        try {
	            fos.close();
	        } catch (Exception e) {
	        }
	    }

	}
	Person getCurrentUser(){
		Person p=new Person();
		p.isCurrent=true;
		FileInputStream fos = null;
	    ObjectInputStream oos = null;
	    try {
	        fos = this.openFileInput(new Person().localfilename);
	        oos = new ObjectInputStream(fos);
	        p=(Person) oos.readObject();
	        return p;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return p;
	    } finally {
	        try {
	            oos.close();
	        } catch (Exception e) {
	        }
	        try {
	            fos.close();
	        } catch (Exception e) {
	        }
	    }
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
		provinceTouched=arg0.getRegeocodeAddress().getProvince();
		cityTouched=arg0.getRegeocodeAddress().getCity();
		districtTouched=arg0.getRegeocodeAddress().getDistrict();
		
		addressUpdated=true;
	
		Intent intent = new Intent();
        intent.setClass(MainActivity.this, LBSChatActivity.class);
        intent.putExtra("address", provinceTouched+cityTouched+districtTouched);
        
        startActivity(intent);
        addressUpdated=false;
	}
}

