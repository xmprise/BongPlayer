package com.example.bongplayer2.adapter;

import java.util.ArrayList;

import com.example.bongplayer2.FolderInfo;
import com.example.bongplayer2.R;
import com.example.bongplayer2.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MediaInfoAdapter extends ArrayAdapter<FolderInfo> {

	private ArrayList<FolderInfo> folderInfoList;
	private Context context;
	private int Resid;
	//private ViewHolder viewHolder;

	public MediaInfoAdapter(Context context, int textViewResourceId, ArrayList<FolderInfo> folderInfoList) {
		super(context, textViewResourceId, folderInfoList);

		this.folderInfoList = folderInfoList;
		this.context = context;
		this.Resid = textViewResourceId;
	}

	public void setResid(int resid) {
		this.Resid = resid;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public FolderInfo getItem(int position) {
		return folderInfoList.get(position);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(Resid, null);

			//viewHolder.setTextView((TextView)v.findViewById(R.id.toptext));
			//v.setTag(R.id.video_name, v.findViewById(R.id.video_name));
			//v.setTag(R.id.video_size, v.findViewById(R.id.video_size));
		}
		FolderInfo p = folderInfoList.get(position);

		System.out.println("p.getFolderName()>>" + p.getFolderName());
		System.out.println("p.getVideoNum()>>" + p.getVideoNum());

		if (p != null) {
			ImageView mediaIcon = (ImageView) v.findViewById(R.id.media_icon);
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			
			if(tt != null)
				tt.setText(p.getFolderName());
			
			if(bt != null)
				bt.setText("비디오: " + p.getVideoNum());
		}
		return v;
	}	
}

