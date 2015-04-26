package com.iems5722.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.iems5722.GroupMap.MainActivity;

public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2423668821951419696L;
	public String name="";
	public Double longitude=0.0;
	public Double latitude=0.0;
	public String mac="";
	public String group="";
	public String message="";
	public String new_message="";
	public boolean visible=true;
	public int groupcheck=0;
	public long showtime=0;
	public static List<Person> pl=new LinkedList<Person>();
	public static List<Marker> ml=new LinkedList<Marker>();
	public Person(){
		this.showtime=System.currentTimeMillis();
	}
	public String localfilename="currentperson";
	public static String localfilename_list="groupperson";
	transient public MarkerOptions mo=new MarkerOptions();
	transient public Marker mark=null;
	public boolean ismarked=false;
	public boolean isCurrent=false;
	public void refreshMarkerOptions(){
		LatLng l=new LatLng(this.latitude,this.longitude);
		mo.position(l);
		mo.title(this.name+" say:");
		mo.visible(true);
		mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		mo.perspective(true);
		mo.snippet(message);
	}
	public void refreshMarker(){
		LatLng l=new LatLng(this.latitude,this.longitude);
		mark.setPosition(l);
		mark.setTitle(this.name);
		mark.setVisible(true);
		mark.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		mark.setPerspective(true);
		mark.setSnippet(message);
	}
}
