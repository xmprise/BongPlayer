package com.example.bongplayer2.Listener.pref;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PrefSeekIntervalSeekListener implements OnSeekBarChangeListener {

	private TextView textView;
	public PrefSeekIntervalSeekListener(TextView textView)
	{
		this.textView = textView;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

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
