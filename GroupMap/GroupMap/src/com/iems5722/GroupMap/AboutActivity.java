package com.iems5722.GroupMap;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AboutActivity extends Activity {
	ListView aboutlist=null;
	Button backbutton=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		aboutlist=(ListView)findViewById(R.id.aboutlist);
		backbutton=(Button)findViewById(R.id.backbutton);
		List<String> str_list=new LinkedList<String>();
		str_list.add("Version: 1.0");
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		str_list.add("Version Update Time:"+sdf.format(new Date()));;
		str_list.add("Functions:\r\n"
				+ "    1. Location sharing\r\n"
				+ "    2. Group chat\r\n"
				+ "    3. Chat based on the location");
		str_list.add("Team Members:\r\n"
				+ "    HE,Guilin\r\n"
				+ "    WANG,Shupeng\r\n"
				+ "    CHENG,Yafeng");
		str_list.add("Special Thanks:\r\n"
				+ "    Map and Location API    provided by lbs.amap.com\r\n"
				+ "    Web Hosting    provided by sina.com\r\n"
				+ "    Android Mobile Test    provided by JIANG,Yi");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str_list);
		aboutlist.setAdapter(adapter);
		backbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
