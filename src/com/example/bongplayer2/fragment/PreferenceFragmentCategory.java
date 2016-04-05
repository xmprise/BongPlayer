package com.example.bongplayer2.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.example.bongplayer2.PreferenceActivity;
import com.example.bongplayer2.R;
import com.example.bongplayer2.R.drawable;
import com.example.bongplayer2.R.layout;
import com.example.bongplayer2.R.string;
import com.example.bongplayer2.adapter.PreferenceCategoryArrayAdapter;
import com.yixia.zi.utils.UIUtils;
import android.support.v4.app.FragmentActivity;

public class PreferenceFragmentCategory extends SherlockFragment
implements AdapterView.OnItemClickListener
{
	private final String LOGTAG = "PreferenceFragmentCategory";
	public static final Integer[] CATEGORY_TITLE = new Integer[2];
	public static final ArrayList<Integer> CATEGORY_TITLE2 = new ArrayList<Integer>();
	public static final int[] CATEGORY_IMAGE = new int[2];
	public static final int[] CATEGORY_SUM = new int[2];
	private PreferenceActivity preferenceActivity;
	private ListView listView;
	private PreferenceCategoryArrayAdapter preferenceCategoryArrayAdapter;
	private int selectItemNum = 0;
	private boolean isNotTablet;

	static
	{
		CATEGORY_TITLE[0] = Integer.valueOf(R.string.pref_cat_player);
		CATEGORY_TITLE[1] = Integer.valueOf(R.string.pref_cat_subtitle);
				
		CATEGORY_TITLE2.add(Integer.valueOf(R.string.pref_cat_player));
		CATEGORY_TITLE2.add(Integer.valueOf(R.string.pref_cat_subtitle));
		
		CATEGORY_IMAGE[0] = R.drawable.pref_cat_video;
		CATEGORY_IMAGE[1] = R.drawable.pref_cat_text;
		
		CATEGORY_SUM[0] = R.string.pref_cat_player_sum;
		CATEGORY_SUM[1] = R.string.pref_cat_subtitle_sum;
		
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
	{
		Log.d(LOGTAG,"onCreateView");
		preferenceActivity = ((PreferenceActivity)getActivity());
		int layoutID;
		
		
		if (!UIUtils.isTablet(getActivity()))
		{
			isNotTablet = true;
			layoutID = R.layout.menu_list;
		}
		else
		{
			isNotTablet = false;
			layoutID = R.layout.preference_category;
		}
		
		layoutID = R.layout.preference_category;
		View view = paramLayoutInflater.inflate(layoutID, paramViewGroup);
		
		getActivity().setTitle(R.string.action_preferences);
		
		if (isNotTablet)
			view.setPadding(25, 0, 25, 0);
		
		listView = ((ListView)view.findViewById(android.R.id.list));
		listView.setOnItemClickListener(this);
		
		//preferenceCategoryArrayAdapter = new PreferenceCategoryArrayAdapter(this, getActivity(), CATEGORY_TITLE);
		preferenceCategoryArrayAdapter = new PreferenceCategoryArrayAdapter(getActivity(), this, R.layout.preference_category_item ,CATEGORY_TITLE2);
		
		listView.setAdapter(preferenceCategoryArrayAdapter);
		
		return view;
	}

	public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
	{
		Log.d(LOGTAG,"onItemClick");
		Log.d(LOGTAG,"onItemClick paramInt>>>"+paramInt);
		selectItemNum = paramInt;
		
		if (preferenceActivity != null)
			preferenceActivity.switchCategory(paramInt);
		
		preferenceCategoryArrayAdapter.notifyDataSetChanged();
	}

	public boolean getIsNotTablet() {
		Log.d(LOGTAG,"getIsNotTablet");
		return isNotTablet;
	}

	public int getSelectItemNum() {
		return selectItemNum;
	}
}