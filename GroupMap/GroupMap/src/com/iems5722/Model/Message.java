package com.iems5722.Model;

import java.util.LinkedList;
import java.util.List;

public class Message {
	public String sender="";
	public String message="";
	public long sendtime=0;
	public String headportrait="";
	public String sendermac="";
	public int type=0;
	public static final int TYPE_LEFT_MESSAGE=0;
	public static final int TYPE_RIGHT_MESSAGE=1;
}
