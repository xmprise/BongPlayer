package com.example.bongplayer2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bongplayer2.R;
import com.example.bongplayer2.Listener.pref.PrefBrightnessSeekListener;
import com.example.bongplayer2.Listener.pref.PrefSeekIntervalSeekListener;
import com.example.bongplayer2.Listener.pref.PrefSeekbarListener;
import com.example.bongplayer2.Listener.pref.PrefSubFontSizeSeekListener;
import com.example.bongplayer2.Listener.pref.PrefSubPositionSeekListener;
import com.example.bongplayer2.fragment.PreferenceFragment;


public final class PreferencesExpanableListAdapter extends BaseExpandableListAdapter
{
	private final String LOGTAG ="PreferencesExpanableListAdapter";
	private LayoutInflater layoutInflater;

	private PreferenceFragment preferenceFragment;
	private int groupPosition;
	public ImageView groupSelectColor;
	
	public PreferencesExpanableListAdapter(PreferenceFragment paramPreferenceFragment)
	{
		super();
		Log.d(LOGTAG,"PreferencesExpanableListAdapter");
		layoutInflater = LayoutInflater.from(paramPreferenceFragment.getActivity());
		//layoutInflater = (LayoutInflater) paramPreferenceFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.preferenceFragment = paramPreferenceFragment;

	}

	private static float a(String paramString)
	{
		//		try
		//		{
		//			float f2 = Float.parseFloat(paramString);
		//			f1 = f2;
		//			return f1;
		//		}
		//		catch (Exception localException)
		//		{
		//			while (true)
		//				float f1 = 0.0F;
		//		}
		return 1;
	}

	public final Object getChild(int paramInt1, int paramInt2)
	{
		Log.d(LOGTAG,"getChild");
		return null;
	}

	public final long getChildId(int paramInt1, int paramInt2)
	{
		Log.d(LOGTAG,"getChildId");
		return 0L;
	}

	public final View getChildView(int paramInt1, int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
	{
		Log.d(LOGTAG,"getChildView");
		View view;
		groupPosition = preferenceFragment.getGroupPosition();
		if (paramView == null)
			paramView = layoutInflater.inflate(R.layout.preference_fragment_item_checkbox, null);
		else
			view = paramView;
		TextView title = (TextView)paramView.findViewById(android.R.id.title);
		CheckBox checkBox = (CheckBox)paramView.findViewById(android.R.id.checkbox);
		SeekBar seekBar = (SeekBar)paramView.findViewById(R.id.seek);
		TextView seekValueText  = (TextView)paramView.findViewById(R.id.pref_seek_value);

		switch (PreferenceFragment.PREFERENCES_TYPE[groupPosition][paramInt1])
		{
		default:
			break;

		case 2://List CheckBoxType	
			String[] childTitleArray = preferenceFragment.getResources().getStringArray(PreferenceFragment.PREFERENCES_CHILD_TITLE[groupPosition][paramInt1]);
			String[] childValueArray = preferenceFragment.getResources().getStringArray(PreferenceFragment.PREFERENCES_CHILD_VALUE[groupPosition][paramInt1]);

			title.setVisibility(View.VISIBLE);
			checkBox.setVisibility(View.VISIBLE);
			seekBar.setVisibility(View.GONE);
			seekValueText.setVisibility(View.GONE);

			title.setText(childTitleArray[paramInt2]);
			checkBox.setTag(childValueArray[paramInt2]);
			checkBox.setChecked(PreferenceFragment.PREFERENCES_LOAD_VALUE[groupPosition][paramInt1].equals(childValueArray[paramInt2]));
			break;

		case 3://SeekBar Type
			title.setVisibility(View.GONE);
			checkBox.setVisibility(View.GONE);
			seekBar.setVisibility(View.VISIBLE);
			seekValueText.setVisibility(View.VISIBLE);
						
			seekBar.setMax(PreferenceFragment.PREFERENCES_SEEKBAR_MAX[groupPosition][paramInt1]);
			
			String seekValue = preferenceFragment.getaPreference().getString(preferenceFragment.getString(PreferenceFragment.PREFERENCES_SAVE_KEY[groupPosition][paramInt1]), PreferenceFragment.PREFERENCES_DEFAULT_VALUE[groupPosition][paramInt1]);
			
			seekBar.setProgress(Integer.parseInt(seekValue));
			seekValueText.setText(seekValue);

			//String str = preferenceFragment.getString(PreferenceFragment.h()[groupPosition][paramInt1]);

			seekBar.setOnSeekBarChangeListener(new PrefSeekbarListener(preferenceFragment, seekValueText, groupPosition, paramInt1));
			
			break;
		}

		return paramView;
	}

	public final int getChildrenCount(int paramInt)
	{
		Log.d(LOGTAG,"getChildrenCount");
		Log.d(LOGTAG,"getChildrenCount paramInt>>>"+paramInt);

		int i = 0;

		groupPosition = preferenceFragment.getGroupPosition();

		if (PreferenceFragment.PREFERENCES_TYPE[groupPosition][paramInt] == 2)
			i = preferenceFragment.getResources().getStringArray(PreferenceFragment.PREFERENCES_CHILD_TITLE[groupPosition][paramInt]).length;
		else if (PreferenceFragment.PREFERENCES_TYPE[groupPosition][paramInt] == 3)
			i = 1;

		Log.d(LOGTAG,"getChildrenCount>>>"+i);

		return i;
	}

	public final Object getGroup(int paramInt)
	{
		Log.d(LOGTAG,"getGroup");
		return null;
	}

	public final int getGroupCount()
	{
		groupPosition = preferenceFragment.getGroupPosition();
		Log.d(LOGTAG,"getGroupCount");
		Log.d(LOGTAG,"getGroupCount>>>"+PreferenceFragment.PREFERENCES_TITLE[groupPosition].length);

		return PreferenceFragment.PREFERENCES_TITLE[0].length;
	}

	public final long getGroupId(int paramInt)
	{
		Log.d(LOGTAG,"getGroupId");
		return 0L;
	}

	public final View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
	{
		Log.d(LOGTAG,"getGroupView");

		View view;
		if (paramView == null)
			paramView = layoutInflater.inflate(R.layout.preference_fragment_item, null);
		else
			view = paramView;

		groupPosition = preferenceFragment.getGroupPosition();

		ImageView groupImageView = (ImageView)paramView.findViewById(android.R.id.icon);
		TextView groupTitle = (TextView)paramView.findViewById(android.R.id.title);
		TextView groupSum = (TextView)paramView.findViewById(android.R.id.summary);
		CheckBox groupCheckBox = (CheckBox)paramView.findViewById(android.R.id.checkbox);
		ImageView groupSelectIcon = (ImageView)paramView.findViewById(android.R.id.selectedIcon);
		groupSelectColor = (ImageView)paramView.findViewById(R.id.selectedColor);

		groupImageView.setImageResource(PreferenceFragment.PREFERENCES_IMAGE[groupPosition][paramInt]);
		groupTitle.setText(PreferenceFragment.PREFERENCES_TITLE[groupPosition][paramInt].intValue());
		groupSum.setText(PreferenceFragment.PREFERENCES_SUM[groupPosition][paramInt]);
		
		groupCheckBox.setVisibility(View.GONE);
		groupSelectIcon.setVisibility(View.GONE);
		groupSelectColor.setVisibility(View.GONE);
		
		
		switch (PreferenceFragment.PREFERENCES_TYPE[groupPosition][paramInt])
		{
		case 0:
			break;
		default:
			break;

		case 1:
			groupCheckBox.setVisibility(View.VISIBLE);
			groupCheckBox.setChecked("true".equals(preferenceFragment.PREFERENCES_LOAD_VALUE[groupPosition][paramInt]));
			break;
		case 2:
		case 3:
			groupSelectIcon.setImageResource(R.drawable.arrow);
			groupSelectIcon.setVisibility(View.VISIBLE);
			break;
		case 4:
			//groupSelectColor.setBackground(preferenceFragment.PREFERENCES_LOAD_VALUE[groupPosition][paramInt]));
			
			int color = Integer.parseInt(preferenceFragment.getaPreference().getString(preferenceFragment.getString(PreferenceFragment.PREFERENCES_SAVE_KEY[groupPosition][paramInt]), PreferenceFragment.PREFERENCES_DEFAULT_VALUE[groupPosition][paramInt])); 
			
			groupSelectColor.setBackgroundColor(color);
			groupSelectColor.setVisibility(View.VISIBLE);
			break;
		}
		return paramView;
	}

	public final boolean hasStableIds()
	{
		Log.d(LOGTAG,"hasStableIds");
		return true;
	}

	public final boolean isChildSelectable(int paramInt1, int paramInt2)
	{
		Log.d(LOGTAG,"isChildSelectable");
		return true;
	}
}