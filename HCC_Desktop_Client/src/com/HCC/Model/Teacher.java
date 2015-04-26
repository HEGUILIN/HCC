package com.HCC.Model;

import java.io.Serializable;

public class Teacher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6445199079734938428L;
	String email=null;
	String username=null;
	String password=null;
	String headportrait=null;
	public Teacher(){
		
	}
	public String getemail(){
		return this.email;
	}
	public void setemail(String email){
		this.email=email;
	}
	public String getusername(){
		return this.username;
	}
	public void setusername(String username){
		this.username=username;
	}
	public String getpassword(){
		return this.password;
	}
	public void setpassword(String password){
		this.password=password;
	}
}
