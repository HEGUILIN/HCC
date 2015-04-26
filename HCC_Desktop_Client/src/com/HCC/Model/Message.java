package com.HCC.Model;

import java.util.LinkedList;
import java.util.List;

public class Message {
	String image0="";
	String image1="";
	String image2="";
	String image3="";
	String image4="";
	String image5="";
	public static List<Message> messages=new LinkedList<Message>();
	public void setImage(String location,String imagefile){
		if(location.equals("image0")){
			image0=imagefile;
		}else if(location.equals("image1")){
			image1=imagefile;
		}else if(location.equals("image2")){
			image2=imagefile;
		}else if(location.equals("image3")){
			image3=imagefile;
		}else if(location.equals("image4")){
			image4=imagefile;
		}else if(location.equals("image5")){
			image5=imagefile;
		}
	}
	public String getImage(String location){
		if(location.equals("image0")){
			return this.image0;
		}else if(location.equals("image1")){
			return this.image1;
		}else if(location.equals("image2")){
			return this.image2;
		}else if(location.equals("image3")){
			return this.image3;
		}else if(location.equals("image4")){
			return this.image4;
		}else if(location.equals("image5")){
			return this.image5;
		}
		return "";
	}
	public void setImage(Sentence s){
		int num=s.getgraph_num();
		for(int i=0;i<num;i++){
			System.out.println((i+5-num));
			System.out.println(new GRAPH().getGRAPH_LINK(s.getGRAPH_ID(i)));
			this.setImage("image"+(i+5-num), new GRAPH().getGRAPH_LINK(s.getGRAPH_ID(i)));
		}
	}
}
