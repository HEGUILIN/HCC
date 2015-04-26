package com.iems5722.GroupMap;

import java.util.List;

import com.iems5722.Model.Message;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {
	List<Message> messages=null;
	private LayoutInflater mInflater;
	public ChatListAdapter(Context context,List<Message> messages) {
		// TODO Auto-generated constructor stub
		this.messages=messages;
		//Log.e("number",""+messages.size());
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("position",""+position);
		Log.e("number",""+messages.size());
		ViewHolder viewHolder = null;
		//if (convertView == null) {
			viewHolder = new ViewHolder();
			
				if (messages.get(position).type==Message.TYPE_LEFT_MESSAGE) {
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_left, null);
					//Log.e("Left",messages.get(position).message);
					viewHolder.tosend = (TextView) convertView
							.findViewById(R.id.tv_chatcontentl);
					viewHolder.toname = (TextView) convertView
							.findViewById(R.id.tv_usernamel);
				} else if(messages.get(position).type==Message.TYPE_RIGHT_MESSAGE) {
					//Log.e("Right",messages.get(position).message);
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_right, null);
					viewHolder.tosend = (TextView) convertView
							.findViewById(R.id.tv_chatcontentr);
					viewHolder.toname = (TextView) convertView
							.findViewById(R.id.tv_usernamer);
				}
			
			convertView.setTag(viewHolder);
		//} else {
		//	viewHolder = (ViewHolder) convertView.getTag();
		//}
		if(position<messages.size()){
			viewHolder.tosend.setText(messages.get(position).message);
			viewHolder.toname.setText(messages.get(position).sender);
		}
		return convertView;
	}
	static class ViewHolder {
		public TextView tosend;
		public TextView toname;
	}
}
