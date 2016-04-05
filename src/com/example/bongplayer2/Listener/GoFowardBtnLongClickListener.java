package com.example.bongplayer2.Listener;

import com.example.bongplayer2.MediaController;
import com.example.bongplayer2.MediaController.MediaPlayerControl;
import com.yixia.zi.utils.StringUtils;

import android.view.View;

public final class GoFowardBtnLongClickListener implements View.OnLongClickListener {

	private MediaController mediaController;

	public GoFowardBtnLongClickListener(MediaController mediaController)
	{
		this.mediaController = mediaController;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		
		mediaController.show(3000);
		long time = mediaController.mediaPlayerControl.goForward();
		if(time != 0L)
		{
			mediaController.setScreenCenterMsg(StringUtils.generateTime(time), 1000L);
		}

		return false;
	}

}
