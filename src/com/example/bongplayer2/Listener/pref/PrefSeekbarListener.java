package com.example.bongplayer2.Listener.pref;

import com.example.bongplayer2.fragment.PreferenceFragment;
import com.yixia.zi.preference.APreference;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PrefSeekbarListener implements OnSeekBarChangeListener {

	private TextView textView;
	private int groupPosition;
	private int childIndex;
	private PreferenceFragment preferenceFragment;
	
	public PrefSeekbarListener(PreferenceFragment preferenceFragment, TextView textView, int groupPosition, int childIndex)
	{
		this.textView = textView;
		this.preferenceFragment = preferenceFragment;
		this.groupPosition = groupPosition;
		this.childIndex = childIndex;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		String value = Integer.toString(progress);
		textView.setText(value);

		preferenceFragment.getaPreference().put(preferenceFragment.getString(PreferenceFragment.PREFERENCES_SAVE_KEY[groupPosition][childIndex]), value);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

}
