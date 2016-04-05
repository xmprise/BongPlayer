package com.example.bongplayer2.adapter;

import java.util.ArrayList;

import com.example.bongplayer2.R;
import com.example.bongplayer2.R.layout;
import com.example.bongplayer2.dialog.ListMenuItem;
import com.yixia.zi.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ListMenuArrayAdapter extends ArrayAdapter{
	
	public ListMenuArrayAdapter(Context paramContext, ArrayList paramArrayList)
	{
		super(paramContext, paramArrayList);
	}

	public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	{
		if (paramView == null)
			paramView = mInflater.inflate(R.layout.menu_list_row, null);
		ListMenuItem listMenuItem = (ListMenuItem)getItem(paramInt);
		((ImageView)paramView.findViewById(android.R.id.icon)).setImageResource(listMenuItem.icon);
		((TextView)paramView.findViewById(android.R.id.title)).setText(listMenuItem.title);
		return paramView;
	}
}
