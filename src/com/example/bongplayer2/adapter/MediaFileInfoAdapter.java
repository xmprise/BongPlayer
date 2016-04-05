package com.example.bongplayer2.adapter;

import java.util.ArrayList;

import com.example.bongplayer2.R;
import com.example.bongplayer2.R.id;
import com.example.bongplayer2.R.layout;
import com.example.bongplayer2.bean.VideoFileInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MediaFileInfoAdapter extends ArrayAdapter<VideoFileInfo> {
	
	private ArrayList<VideoFileInfo> videoFileInfoList;
	private Context context;
	
	public MediaFileInfoAdapter(Context context, int textViewResourceId, ArrayList<VideoFileInfo> videoFileInfoList)
	{
		super(context, textViewResourceId, videoFileInfoList);
		this.videoFileInfoList = videoFileInfoList;
		this.context = context;
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.media_info_row, null);
            }
            VideoFileInfo p = videoFileInfoList.get(0);
            
            System.out.println("getVideoName>>"+p.getVideoName());
            System.out.println("getVideoTime>>"+p.getVideoTime());
            
            if (p != null) {
            		Log.d("z", "zz");
                    TextView tt = (TextView) v.findViewById(R.id.video_name);
                    TextView bt = (TextView) v.findViewById(R.id.video_size);
                    ImageView iv = (ImageView) v.findViewById(R.id.video_thumb);
                    if (tt != null){
                    	Log.d("z", "video name");
                    	tt.setText(p.getVideoName());                            
                    }
                    if(bt != null){
                    	Log.d("z", "video time");
                    		bt.setText("비디오 time: "+p.getVideoTime());
                    }
                    if(iv != null)
                    {
                    	Log.d("z", "video thumb");
                    	iv.setImageBitmap(p.getVideoThumnail());
                    }
            }
            return v;
    }
}