package com.HCC.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Sentence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6454587963308903750L;
	private String SENTENCE_DESCRIPTION="";
	private String GRAPH1_ID="";
	private String GRAPH2_ID="";
	private String GRAPH3_ID="";
	private String GRAPH4_ID="";
	private String SENTENCE_ID="";
	private int graph_num=0;
	public static List<Sentence> sentences=new LinkedList<Sentence>();
	public void setSENTENCE_DESCRIPTION(String SENTENCE_DESCRIPTION){
		this.SENTENCE_DESCRIPTION=SENTENCE_DESCRIPTION;
	}
	public void setGRAPH1_ID(String GRAPH1_ID){
		this.GRAPH1_ID=GRAPH1_ID;
	}
	public void setGRAPH2_ID(String GRAPH2_ID){
		this.GRAPH2_ID=GRAPH2_ID;
	}
	public void setGRAPH3_ID(String GRAPH3_ID){
		this.GRAPH3_ID=GRAPH3_ID;
	}
	public void setGRAPH4_ID(String GRAPH4_ID){
		this.GRAPH4_ID=GRAPH4_ID;
	}
	public void setSENTENCE_ID(String SENTENCE_ID){
		this.SENTENCE_ID=SENTENCE_ID;
	}
	public void setGRAPH_ID(int i,String id){
		System.out.println(""+i+id);
		if(i==0){
			this.setGRAPH1_ID(id);
		}else if(i==1){
			this.setGRAPH2_ID(id);
		}else if(i==2){
			this.setGRAPH3_ID(id);
		}else if(i==3){
			this.setGRAPH4_ID(id);
		}
	}
	public String getSENTENCE_ID(){
		return this.SENTENCE_ID;
	}
	public String getGRAPH1_ID(){
		return this.GRAPH1_ID;
	}
	public String getGRAPH2_ID(){
		return this.GRAPH2_ID;
	}
	public String getGRAPH3_ID(){
		return this.GRAPH3_ID;
	}
	public String getGRAPH4_ID(){
		return this.GRAPH4_ID;
	}
	public String getSENTENCE_DESCRIPTION(){
		return this.SENTENCE_DESCRIPTION;
	}
	public int getgraph_num(){
		return this.graph_num;
	}
	public void setgraph_num(int i){
		this.graph_num=i;
	}
	public String getGRAPH_ID(int i){
		if(i==0){
			return this.getGRAPH1_ID();
		}else if(i==1){
			return this.GRAPH2_ID;
		}else if(i==2){
			return this.getGRAPH3_ID();
		}else if(i==3){
			return this.getGRAPH4_ID();
		}
		return "";
	}
}
