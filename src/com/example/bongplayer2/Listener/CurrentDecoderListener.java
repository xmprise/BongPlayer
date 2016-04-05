package com.example.bongplayer2.Listener;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bongplayer2.MediaController;
import com.example.bongplayer2.R;

public final class CurrentDecoderListener implements View.OnClickListener {

	private final String LOGTAG = "CurrentDecoderListener";
	private MediaController mediaController;
	
	public CurrentDecoderListener(MediaController mediaController)
	{
		this.mediaController = mediaController;
	}
	
	@Override
	public final void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG, "onClick!");
		
		String decoder = mediaController.mediaPlayerControl.changeDecoder();
		mediaController.currentDecoder.setText(decoder);
	}
}