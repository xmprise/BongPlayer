package com.example.bongplayer2.Listener;

import com.example.bongplayer2.MediaController;
import com.example.bongplayer2.MediaController.MediaPlayerControl;

import android.view.View;

public final class PlayBtnListener implements View.OnClickListener {

	private MediaPlayerControl mc ;
	private MediaController mediaController;
	
	public PlayBtnListener(MediaController mediaController)
	{
		System.out.println("PlayBtnListener");
		this.mediaController = mediaController;
	}
	
	@Override
	public final void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("PlayBtn onClick!");

		mediaController.playOrPause();

//		if(mc.isPlaying())
//		{
//			System.out.println("PlayBtn onClick isPlaying");
//			mc.pause();
////			mediaController.show(120000);
//		}
//		else
//		{
//			System.out.println("PlayBtn onClick notPlaying");
//			mc.start();
////			mediaController.show();
//		}
//		
//		mediaController.setPlayButtonImg();
	}

}
