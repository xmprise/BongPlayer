package com.example.bongplayer2.Listener;

import com.example.bongplayer2.MediaController;

import android.view.View;
import android.view.View.OnClickListener;

public final class VideoMenuListener implements View.OnClickListener{
	
	private MediaController mediaController;
	public VideoMenuListener(MediaController mediaController)
	{
		this.mediaController = mediaController;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mediaController.getMediaPlayerControl().showMenu();
	}
}