package com.example.bongplayer2.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bongplayer2.R;
import com.example.bongplayer2.bean.StreamListViewData;
import com.example.bongplayer2.fragment.PreferenceFragmentCategory;

@SuppressLint("ResourceAsColor")
public class PreferenceCategoryArrayAdapter extends ArrayAdapter{

	private final String LOGTAG ="PreferenceCategoryArrayAdapter";
	private PreferenceFragmentCategory preferenceFragmentCategory;
	private LayoutInflater inflater;
	private Context context;
	private ArrayList<Integer> paramArrayOfInteger;

	//	public PreferenceCategoryArrayAdapter(PreferenceFragmentCategory paramVPreferenceFragmentCategory, Context paramContext, Integer[] paramArrayOfInteger)
	//	{
	//		super(paramContext, paramArrayOfInteger);
	//		Log.d(LOGTAG,"PreferenceCategoryArrayAdapter");
	//		this.preferenceFragmentCategory = paramVPreferenceFragmentCategory;
	//	}

	public PreferenceCategoryArrayAdapter(Context context, PreferenceFragmentCategory preferenceFragmentCategory, int textViewResourceId, ArrayList<Integer> paramArrayOfInteger)
	{
		super(context, textViewResourceId, paramArrayOfInteger);

		Log.d(LOGTAG, "StreamAdapter");

		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.context = context;
		this.paramArrayOfInteger = paramArrayOfInteger;
		this.preferenceFragmentCategory = preferenceFragmentCategory;
	}

	public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	{
		Log.d(LOGTAG,"getView");
		Log.d(LOGTAG,"paramInt>>>>"+paramInt);

		int listRowID;
		View view = null;

		if (paramView == null)
		{
			Log.d(LOGTAG,"paramView == null");
			//LayoutInflater layoutInflater = mInflater;

			//			if (preferenceFragmentCategory.getIsNotTablet())
			//			{
			//				Log.d(LOGTAG,"paramView getIsNotTablet");
			//				
			//				paramView = layoutInflater.inflate(R.layout.pref_list_row, null);
			//			}
			//paramView = layoutInflater.inflate(R.layout.pref_list_row, null);
			paramView = inflater.inflate(R.layout.preference_category_item, null);
			Log.d(LOGTAG,"paramView == null end");
		}
		else
		{
			view = paramView;
		}
		Log.d(LOGTAG,"paramView != null");
		((ImageView)paramView.findViewById(android.R.id.icon)).setImageResource(PreferenceFragmentCategory.CATEGORY_IMAGE[paramInt]);
		//((TextView)paramView.findViewById(android.R.id.title)).setText(PreferenceFragmentCategory.CATEGORY_TITLE[paramInt].intValue());
		((TextView)paramView.findViewById(android.R.id.title)).setText(paramArrayOfInteger.get(paramInt).intValue());

		TextView textView = (TextView)paramView.findViewById(android.R.id.summary);

		if (textView.getVisibility() == View.GONE)
			textView.setVisibility(View.VISIBLE);

		textView.setText(PreferenceFragmentCategory.CATEGORY_SUM[paramInt]);

		View checkImage = paramView.findViewById(R.id.preference_check);

	
		if (checkImage != null && (paramInt != preferenceFragmentCategory.getSelectItemNum()))
			checkImage.setVisibility(View.INVISIBLE);
		else if(checkImage != null && (paramInt == preferenceFragmentCategory.getSelectItemNum()))
			checkImage.setVisibility(View.VISIBLE);

		Log.d(LOGTAG,"paramView>>>"+paramView);
		//		listRowID = R.layout.preference_category_item;
		return paramView;
	}
}

