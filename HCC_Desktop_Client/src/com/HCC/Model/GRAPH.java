package com.HCC.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GRAPH implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7019583571038282559L;
	private String GRAPH_ID="";
	private String GRAPH_DESCRIPTION="";
	private String GRAPH_LINK="";
	private String GRAPH_ONLINE_LINK="";
	public static List<GRAPH> GRAPH_list=new LinkedList<GRAPH>();
	public void setGRAPH_ID(String GRAPH_ID){
		this.GRAPH_ID=GRAPH_ID;
	}
	public void setGRAPH_DESCRIPTION(String GRAPH_DESCRIPTION){
		this.GRAPH_DESCRIPTION=GRAPH_DESCRIPTION;
	}
	public void setGRAPH_LINK(String GRAPH_LINK){
		this.GRAPH_LINK=GRAPH_LINK;
	}
	public String getGRAPH_ID(){
		return GRAPH_ID;
	}
	public String getGRAPH_DESCRIPTION(){
		return GRAPH_DESCRIPTION;
	}
	public String getGRAPH_LINK(){
		return GRAPH_LINK;
	}
	public String getGRAPH_ONLINE_LINK(){
		return this.GRAPH_ONLINE_LINK;
	}
	public void setGRAPH_ONLINE_LINK(String GRAPH_ONLINE_LINK){
		this.GRAPH_ONLINE_LINK=GRAPH_ONLINE_LINK;
	}
	public String getGRAPH_ID(String link){
		for(GRAPH g:GRAPH_list){
			if(g.getGRAPH_LINK().equals(link)){
				
				return g.getGRAPH_ID();
			}
		}
		return "";
	}
	public String getGRAPH_LINK(String id){
		for(GRAPH g:GRAPH_list){
			if(g.getGRAPH_ID().equals(id)){
				
				return g.getGRAPH_LINK();
			}
		}
		return "";
	}
}
