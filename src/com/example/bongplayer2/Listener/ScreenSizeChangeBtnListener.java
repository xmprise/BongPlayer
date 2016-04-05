package com.example.bongplayer2.Listener;

import com.example.bongplayer2.MediaController;

import android.view.View;

public final class ScreenSizeChangeBtnListener implements View.OnClickListener {

	private MediaController mediaController;
	public ScreenSizeChangeBtnListener(MediaController mediaController)
	{
		this.mediaController = mediaController;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		mediaController.setScreenMode();
	}

}
