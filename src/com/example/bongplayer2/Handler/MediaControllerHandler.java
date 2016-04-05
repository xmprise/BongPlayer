package com.example.bongplayer2.Handler;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.bongplayer2.MediaController;
import com.example.bongplayer2.MediaController.MediaPlayerControl;
import com.yixia.zi.utils.StringHelper;


public class MediaControllerHandler extends Handler{

	private final String LOGTAG = "MediaControllerHandler";
	private WeakReference weakReference;

	public MediaControllerHandler(MediaController paramMediaController)
	{
		weakReference = new WeakReference(paramMediaController);
	}

	public final void handleMessage(Message paramMessage)
	{
		MediaController mediaController = (MediaController)weakReference.get();
		MediaPlayerControl mediaPlayerControl = mediaController.getMediaPlayerControl();

		if (mediaController == null)
			return;
		else
		{
			switch (paramMessage.what)
			{
			default:
				Log.d(LOGTAG, "default");
				break;
			case 1:
				Log.d(LOGTAG, "1");
				mediaController.hide();
				break;
			case 2:
				Log.d(LOGTAG, "2");
				long pos = mediaController.setProgress();

				if (/*!mDragging &&*/ mediaController.isShowing() && mediaPlayerControl.isPlaying()) {
					sendMessageDelayed(obtainMessage(2), 1000 - (pos % 1000));
				}
				break;
			case 3:
				Log.d(LOGTAG, "3");
				if (!mediaController.isShowing())
					mediaController.show();
				break;
			case 4: // 현재 실제 시간 표시
				//mediaController.getCurretnPlayTime().setText(StringHelper.currentTimeString());
				sendEmptyMessageDelayed(4, 1000L);
				break;
			case 5:
				mediaController.getCurrentPlayTimeCenter().setVisibility(View.GONE);
				break;
				//			case 6:
				//				MediaController.l(mediaController).setVisibility(8);
			}
		}
	}
}
