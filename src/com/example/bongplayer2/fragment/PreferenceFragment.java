package com.example.bongplayer2.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.SeekBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.bongplayer2.R;
import com.example.bongplayer2.Listener.PreferenceFragmentExpandListener;
import com.example.bongplayer2.R.array;
import com.example.bongplayer2.R.drawable;
import com.example.bongplayer2.R.string;
import com.example.bongplayer2.adapter.PreferencesExpanableListAdapter;
import com.example.bongplayer2.helper.PreferenceHelper;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.utils.UIUtils;

public class PreferenceFragment extends SherlockFragment
implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener
{
	private final String LOGTAG ="PreferenceFragment";
	private static final int col = 2;
	private static final int row = 7;
	public static final Integer[][] PREFERENCES_TITLE = new Integer[col][row];
	public static final int[][] PREFERENCES_IMAGE = new int[col][row];
	public static final int[][] PREFERENCES_SUM = new int[col][row];
	public static final int[][] PREFERENCES_TYPE = new int[col][row];
	public static final int[][] PREFERENCES_CHILD_TITLE = new int[col][row];
	public static final int[][] PREFERENCES_CHILD_VALUE = new int[col][row];
	public static final int[][] PREFERENCES_SAVE_KEY = new int[col][row];
	public static final int[][] PREFERENCES_SEEKBAR_MAX = new int[col][row];
	public static final String[][] PREFERENCES_DEFAULT_VALUE = new String[col][row];
	public static String[][] PREFERENCES_LOAD_VALUE;
	private ExpandableListView expandableListView;
	private APreference aPreference;
	public PreferencesExpanableListAdapter preferencesExpanableListAdapter;
	private int groupPosition = 0;
	private ExpandableListView.OnGroupExpandListener preferenceFragmentExpandListener = new PreferenceFragmentExpandListener(this);

	static
	{
		PREFERENCES_TITLE[0][0] = Integer.valueOf(R.string.pref_tit_play_continu);
		PREFERENCES_TITLE[0][1] = Integer.valueOf(R.string.pref_tit_play_next);
		PREFERENCES_TITLE[0][2] = Integer.valueOf(R.string.pref_tit_video_size);
		PREFERENCES_TITLE[0][3] = Integer.valueOf(R.string.pref_tit_screen_ratio);
		PREFERENCES_TITLE[0][4] = Integer.valueOf(R.string.pref_tit_brightness);
		PREFERENCES_TITLE[0][5] = Integer.valueOf(R.string.pref_tit_micro_seek_duration);
		PREFERENCES_TITLE[0][6] = Integer.valueOf(R.string.pref_tit_decoder_choice);

		PREFERENCES_TITLE[1][0] = Integer.valueOf(R.string.pref_tit_sub_font);
		PREFERENCES_TITLE[1][1] = Integer.valueOf(R.string.pref_tit_sub_size);
		PREFERENCES_TITLE[1][2] = Integer.valueOf(R.string.pref_tit_sub_color);
		PREFERENCES_TITLE[1][3] = Integer.valueOf(R.string.pref_tit_sub_shown);
		PREFERENCES_TITLE[1][4] = Integer.valueOf(R.string.pref_tit_sub_encoding);
		PREFERENCES_TITLE[1][5] = Integer.valueOf(R.string.pref_tit_sub_position);
		PREFERENCES_TITLE[1][6] = Integer.valueOf(R.string.pref_tit_sub_time);

		PREFERENCES_IMAGE[0][0] = R.drawable.pref_screen_continue;
		PREFERENCES_IMAGE[0][1] = R.drawable.pref_screen_next;
		PREFERENCES_IMAGE[0][2] = R.drawable.pref_screen_size;
		PREFERENCES_IMAGE[0][3] = R.drawable.pref_screen_ratio;
		PREFERENCES_IMAGE[0][4] = R.drawable.pref_screen_brightness;
		PREFERENCES_IMAGE[0][5] = R.drawable.pref_cat_video;
		PREFERENCES_IMAGE[0][6] = R.drawable.pref_cat_video;

		PREFERENCES_IMAGE[1][0] = R.drawable.pref_cat_text;
		PREFERENCES_IMAGE[1][1] = R.drawable.font_size;
		PREFERENCES_IMAGE[1][2] = R.drawable.font_color;
		PREFERENCES_IMAGE[1][3] = R.drawable.show_hidden;
		PREFERENCES_IMAGE[1][4] = R.drawable.sub_encoding;
		PREFERENCES_IMAGE[1][5] = R.drawable.sub_position;
		PREFERENCES_IMAGE[1][6] = R.drawable.pref_cat_text;

		PREFERENCES_SUM[0][0] = R.string.pref_sum_play_continu;
		PREFERENCES_SUM[0][1] = R.string.pref_sum_play_next;
		PREFERENCES_SUM[0][2] = R.string.pref_sum_video_size;
		PREFERENCES_SUM[0][3] = R.string.pref_sum_screen_ratio;
		PREFERENCES_SUM[0][4] = R.string.pref_sum_brightness;
		PREFERENCES_SUM[0][5] = R.string.pref_sum_micro_seek_duration;
		PREFERENCES_SUM[0][6] = R.string.pref_sum_decoder_choice;

		PREFERENCES_SUM[1][0] = R.string.pref_sum_sub_font;
		PREFERENCES_SUM[1][1] = R.string.pref_sum_sub_size;
		PREFERENCES_SUM[1][2] = R.string.pref_sum_sub_color;
		PREFERENCES_SUM[1][3] = R.string.pref_sum_sub_shown;
		PREFERENCES_SUM[1][4] = R.string.pref_sum_sub_encoding;
		PREFERENCES_SUM[1][5] = R.string.pref_sum_sub_position;
		PREFERENCES_SUM[1][6] = R.string.pref_sum_sub_time;

		// 0아무것도x / 1체크박스 / 2리스트체크박스 / 3 seek / 4 color
		PREFERENCES_TYPE[0][0] = 1;
		PREFERENCES_TYPE[0][1] = 1;
		PREFERENCES_TYPE[0][2] = 2;
		PREFERENCES_TYPE[0][3] = 2;
		PREFERENCES_TYPE[0][4] = 3;
		PREFERENCES_TYPE[0][5] = 3;
		PREFERENCES_TYPE[0][6] = 2;

		PREFERENCES_TYPE[1][0] = 2;
		PREFERENCES_TYPE[1][1] = 3;
		PREFERENCES_TYPE[1][2] = 4;
		PREFERENCES_TYPE[1][3] = 1;
		PREFERENCES_TYPE[1][4] = 2;
		PREFERENCES_TYPE[1][5] = 3;
		PREFERENCES_TYPE[1][6] = 3;

		PREFERENCES_CHILD_TITLE[0][0] = 0;
		PREFERENCES_CHILD_TITLE[0][1] = 0;
		PREFERENCES_CHILD_TITLE[0][2] = R.array.screen_size_titles;
		PREFERENCES_CHILD_TITLE[0][3] = R.array.aspect_ratio_titles;
		PREFERENCES_CHILD_TITLE[0][4] = 0;
		PREFERENCES_CHILD_TITLE[0][5] = 0;
		PREFERENCES_CHILD_TITLE[0][6] = R.array.decoder_choice_titles;

		PREFERENCES_CHILD_TITLE[1][0] = R.array.font_titles;
		PREFERENCES_CHILD_TITLE[1][1] = 0;
		PREFERENCES_CHILD_TITLE[1][2] = 0;
		PREFERENCES_CHILD_TITLE[1][3] = 0;
		PREFERENCES_CHILD_TITLE[1][4] = R.array.encoding_titles;
		PREFERENCES_CHILD_TITLE[1][5] = 0;
		PREFERENCES_CHILD_TITLE[1][6] = 0;

		PREFERENCES_CHILD_VALUE[0][0] = 0;
		PREFERENCES_CHILD_VALUE[0][1] = 0;
		PREFERENCES_CHILD_VALUE[0][2] = R.array.screen_size_values;
		PREFERENCES_CHILD_VALUE[0][3] = R.array.aspect_ratio_values;
		PREFERENCES_CHILD_VALUE[0][4] = 0;
		PREFERENCES_CHILD_VALUE[0][5] = 0;
		PREFERENCES_CHILD_VALUE[0][6] = R.array.decoder_choice_values;

		PREFERENCES_CHILD_VALUE[1][0] = R.array.font_values;
		PREFERENCES_CHILD_VALUE[1][1] = 0;
		PREFERENCES_CHILD_VALUE[1][2] = 0;
		PREFERENCES_CHILD_VALUE[1][3] = 0;
		PREFERENCES_CHILD_VALUE[1][4] = R.array.encoding_values;
		PREFERENCES_CHILD_VALUE[1][5] = 0;
		PREFERENCES_CHILD_VALUE[1][6] = 0;

		PREFERENCES_SAVE_KEY[0][0] = R.string.pref_key_play_continu;
		PREFERENCES_SAVE_KEY[0][1] = R.string.pref_key_play_next;
		PREFERENCES_SAVE_KEY[0][2] = R.string.pref_key_video_size;
		PREFERENCES_SAVE_KEY[0][3] = R.string.pref_key_screen_ratio;
		PREFERENCES_SAVE_KEY[0][4] = R.string.pref_key_brightness;
		PREFERENCES_SAVE_KEY[0][5] = R.string.pref_key_micro_seek_duration;
		PREFERENCES_SAVE_KEY[0][6] = R.string.pref_key_decoder_choice;

		PREFERENCES_SAVE_KEY[1][0] = R.string.pref_key_sub_font;
		PREFERENCES_SAVE_KEY[1][1] = R.string.pref_key_sub_size;
		PREFERENCES_SAVE_KEY[1][2] = R.string.pref_key_sub_color;
		PREFERENCES_SAVE_KEY[1][3] = R.string.pref_key_sub_shown;
		PREFERENCES_SAVE_KEY[1][4] = R.string.pref_key_sub_encoding;
		PREFERENCES_SAVE_KEY[1][5] = R.string.pref_key_sub_position;
		PREFERENCES_SAVE_KEY[1][6] = R.string.pref_key_sub_time;

		PREFERENCES_DEFAULT_VALUE[0][0] = "true";
		PREFERENCES_DEFAULT_VALUE[0][1] = "true";
		PREFERENCES_DEFAULT_VALUE[0][2] = "0";
		PREFERENCES_DEFAULT_VALUE[0][3] = "1.78";
		PREFERENCES_DEFAULT_VALUE[0][4] = "10";
		PREFERENCES_DEFAULT_VALUE[0][5] = "10";
		PREFERENCES_DEFAULT_VALUE[0][6] = "HW";

		PREFERENCES_DEFAULT_VALUE[1][0] = "0";
		PREFERENCES_DEFAULT_VALUE[1][1] = "10";
		PREFERENCES_DEFAULT_VALUE[1][2] = "0";
		PREFERENCES_DEFAULT_VALUE[1][3] = "true";
		PREFERENCES_DEFAULT_VALUE[1][4] = "0";
		PREFERENCES_DEFAULT_VALUE[1][5] = "0";
		PREFERENCES_DEFAULT_VALUE[1][6] = "5";
		
		PREFERENCES_SEEKBAR_MAX[0][4] = 10;
		PREFERENCES_SEEKBAR_MAX[0][5] = 10;
		PREFERENCES_SEEKBAR_MAX[1][1] = 20;
		PREFERENCES_SEEKBAR_MAX[1][5] = 5;

	}

	private void savePreference(int paramInt1, int paramInt2, String paramString)
	{
		Log.d(LOGTAG,"a");
		PREFERENCES_LOAD_VALUE[paramInt1][paramInt2] = paramString;
		aPreference.put(getString(PREFERENCES_SAVE_KEY[paramInt1][paramInt2]), paramString);
	}

	public void changePosition(int paramInt)
	{
		Log.d(LOGTAG,"changePosition");
		Log.d(LOGTAG,"changePosition paramInt>>>>"+paramInt);
		groupPosition = paramInt;

		preferencesExpanableListAdapter.notifyDataSetChanged();

		getActivity().setTitle(PreferenceFragmentCategory.CATEGORY_TITLE[groupPosition].intValue());
	}

	public void onActivityCreated(Bundle paramBundle)
	{
		Log.d(LOGTAG,"onActivityCreated");
		super.onActivityCreated(paramBundle);

		aPreference = new APreference(getActivity());

		if (aPreference != null)
		{
			PREFERENCES_LOAD_VALUE = new String[col][row];
			
			for(int i=0;i<col;i++)
			{
				for(int j=0;j<row;j++)
				{
					PREFERENCES_LOAD_VALUE[i][j] = aPreference.getString(getString(PREFERENCES_SAVE_KEY[i][j]), PREFERENCES_DEFAULT_VALUE[i][j]);
				}
			}
		}
	}

	public boolean onChildClick(ExpandableListView paramExpandableListView, View paramView, int paramInt1, int paramInt2, long paramLong)
	{
		Log.d(LOGTAG,"onChildClick");
		switch (PREFERENCES_TYPE[groupPosition][paramInt1])
		{
		case 0:
		case 1:
		default:
		case 2:
		}
		savePreference(groupPosition, paramInt1, paramView.findViewById(android.R.id.checkbox).getTag().toString());
		preferencesExpanableListAdapter.notifyDataSetChanged();
		//if (PREFERENCES_SAVE_KEY[groupPosition][paramInt1] == R.string.pref_key_change_theme);
		//((PlayerApplication)getActivity().getApplication()).setVPlayerTheme();
		return true;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
	{
		Log.d(LOGTAG,"onCreateView");
		View view = paramLayoutInflater.inflate(R.layout.preference_fragment, paramViewGroup);

		expandableListView = ((ExpandableListView)view.findViewById(android.R.id.list));

		if (!UIUtils.isTablet(getActivity()))
			expandableListView.setPadding(2, 0, 2, 0);

		preferencesExpanableListAdapter = new PreferencesExpanableListAdapter(this);
		
		expandableListView.setAdapter(preferencesExpanableListAdapter);
		registerForContextMenu(expandableListView);
		expandableListView.setOnGroupClickListener(this);
		expandableListView.setOnChildClickListener(this);
		expandableListView.setOnGroupExpandListener(preferenceFragmentExpandListener);

		return view;
	}

	private void removeallview() {
		// TODO Auto-generated method stub
		
	}

	public boolean onGroupClick(ExpandableListView paramExpandableListView, View paramView, int paramInt, long paramLong)
	{
		Log.d(LOGTAG,"onGroupClick");
		Log.d(LOGTAG,"onGroupClick paramInt>>"+paramInt);
		Log.d(LOGTAG,"onGroupClick paramLong>>"+paramLong);

		switch (PREFERENCES_TYPE[groupPosition][paramInt])
		{
		default:
			return false;
		case 0:
			return false;
		case 1:
			CheckBox checkBox = (CheckBox)paramView.findViewById(android.R.id.checkbox);
			if (checkBox.isChecked())
			{
				savePreference(groupPosition, paramInt, "false");
				checkBox.setChecked(false);
			}
			else
			{
				savePreference(groupPosition, paramInt, "true");
				checkBox.setChecked(true);
			}
			return true;
		case 4:
			int keyID = PREFERENCES_SAVE_KEY[groupPosition][paramInt];

			switch (keyID)
			{
			default:
				break;
			case R.string.pref_key_sub_color:
				PreferenceHelper.showColorPicker(getActivity(), aPreference, getString(keyID), -1, preferencesExpanableListAdapter);
				break;
			}
			return false;
		}
	}

	public int getGroupPosition() {
		return groupPosition;
	}

	public APreference getaPreference() {
		return aPreference;
	}
}