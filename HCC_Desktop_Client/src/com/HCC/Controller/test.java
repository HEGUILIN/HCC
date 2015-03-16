package com.HCC.Controller;

import net.sf.json.JSONObject;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseGet messager=new BaseGet();
		JSONObject message=messager.register("BAI Xue3", "baixue");
		System.out.println(message);
	}

}
