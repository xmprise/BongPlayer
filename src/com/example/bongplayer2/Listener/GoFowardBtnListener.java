package com.example.bongplayer2.Listener;

import com.example.bongplayer2.MediaController;
import com.example.bongplayer2.MediaController.MediaPlayerControl;
import com.yixia.zi.utils.StringUtils;

import android.view.View;

public final class GoFowardBtnListener implements View.OnClickListener {

	private MediaController mediaController;
	
	public GoFowardBtnListener(MediaController mediaController)
	{
		this.mediaController = mediaController;
	}
	
	@Override
	public final void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("GoFowardBtn onClick!");
		
		mediaController.show(3000);
		long time = mediaController.mediaPlayerControl.goForward();
		if(time != 0L)
		{
			mediaController.setScreenCenterMsg(StringUtils.generateTime(time), 1000L);
		}
		
	}

}
