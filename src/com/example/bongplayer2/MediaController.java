package com.example.bongplayer2;

import java.lang.reflect.Method;
import java.util.Formatter;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bongplayer2.Handler.MediaControllerHandler;
import com.example.bongplayer2.Listener.ControllerAnimationListener;
import com.example.bongplayer2.Listener.CurrentDecoderListener;
import com.example.bongplayer2.Listener.GoBackBtnListener;
import com.example.bongplayer2.Listener.GoFowardBtnListener;
import com.example.bongplayer2.Listener.GoFowardBtnLongClickListener;
import com.example.bongplayer2.Listener.NextMediaPlayBtnListener;
import com.example.bongplayer2.Listener.PlayBtnListener;
import com.example.bongplayer2.Listener.PrevMediaPlayBtnListener;
import com.example.bongplayer2.Listener.ScreenSizeChangeBtnListener;
import com.example.bongplayer2.Listener.SeekBarListener;
import com.example.bongplayer2.Listener.VideoMenuListener;
import com.example.bongplayer2.Listener.ViewLockBtnListener;
import com.example.bongplayer2.Listener.pa;
import com.yixia.zi.provider.Session;
import com.yixia.zi.utils.UIUtils;

@SuppressLint("NewApi")
public class MediaController extends FrameLayout implements Handler.Callback{

	private final String LOGTAG = "MediaController";
	public SeekBar seekBar;
	public SeekBarListener seekBarListener;
	//private PlaySeekBarChangeListener playSeekBarChangeListener = new PlaySeekBarChangeListener(this);

	private ImageButton viewLockBtn;
	private ImageButton playBtn;
	private ImageButton screenSizeChangeBtn;
	private ImageButton nextMediaBtn;
	private ImageButton prevMediaBtn;
	private ImageButton goFowardBtn;
	private ImageButton goBack;
	private ImageButton mediaLock;
	public Button currentDecoder;

	private ViewLockBtnListener viewLockBtnListener = new ViewLockBtnListener(this);
	private PlayBtnListener playBtnListener = new PlayBtnListener(this);
	private ScreenSizeChangeBtnListener screenSizeChangeBtnListener = new ScreenSizeChangeBtnListener(this); 
	private NextMediaPlayBtnListener nextMediaPlayBtnListener = new NextMediaPlayBtnListener(this);
	private PrevMediaPlayBtnListener prevMediaPlayBtnListener = new PrevMediaPlayBtnListener(this);
	private GoFowardBtnListener goFowardBtnListener = new GoFowardBtnListener(this);
	private GoFowardBtnLongClickListener goFowardBtnLongClickListener = new GoFowardBtnLongClickListener(this);
	private GoBackBtnListener  goBackBtnListener = new GoBackBtnListener(this);

	private VideoMenuListener videoMenuListener = new VideoMenuListener(this);

	private CurrentDecoderListener currentDecoderListener = new CurrentDecoderListener(this);

	private Activity videoActivity;
	private Context mContext;

	private PopupWindow controlWindow;
	private View optionView;
	private View controlView;
	private View infoPanel;
	public View mediaController;
	private View videoMenu;
	private View mediacontrollerControls;
	private View mediacontrollerControlsBtn;
	public View brightnNessView;
	private View mRoot;
	private View mAnchor;

	private ImageView operationBg;
	private ImageView operationPercent;

	private boolean isShow = false;
	private boolean isLock = false;
	private boolean mUseFastForward;

	public int screenMode;
	public int maxVolume;
	public int currentVolume = 0;
	public float I = 0.01F;

	public MediaPlayerControl mediaPlayerControl;
	private AudioManager audioManager;

	private Animation inTop;
	private Animation inBottom;
	private Animation outTop;
	private Animation outBottom;

	private Handler handler;

	private TextView currentPlayTime;
	private TextView currentPlayTimeCenter;
	private TextView totalPlayTime;
	private TextView fileName;

	private CommonGestures commonGestures;
	private CommonGestures.TouchListener gestureTouchListener = new pa(this);
	private Session session;

	StringBuilder               mFormatBuilder;
	Formatter                   mFormatter;
	@SuppressWarnings("deprecation")
	public MediaController(Context context)
	{
		super(context);

		videoActivity = ((Activity)context);

		controlWindow = new PopupWindow(videoActivity);
		controlWindow.setFocusable(true);
		controlWindow.setBackgroundDrawable(null);
		controlWindow.setOutsideTouchable(true);

		handler = new MediaControllerHandler(this);
		audioManager = ((AudioManager)videoActivity.getSystemService("audio"));

		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		commonGestures = new CommonGestures(videoActivity);
		commonGestures.setTouchListener(gestureTouchListener, true);

		outBottom = AnimationUtils.loadAnimation(videoActivity, R.anim.slide_out_bottom);
		outTop = AnimationUtils.loadAnimation(videoActivity, R.anim.slide_out_top);
		inBottom = AnimationUtils.loadAnimation(videoActivity, R.anim.slide_in_bottom);
		inTop = AnimationUtils.loadAnimation(videoActivity, R.anim.slide_in_top);
		outBottom.setAnimationListener(new ControllerAnimationListener(this));
		removeAllViews();

		optionView = ((LayoutInflater)videoActivity.getSystemService("layout_inflater")).inflate(R.layout.mediacontroller2, this);

		controlWindow.setContentView(optionView);
		controlWindow.setWidth(-1);
		controlWindow.setHeight(-1);

		playBtn = (ImageButton)findViewById(R.id.mediacontroller_play_pause);
		playBtn.setOnClickListener(playBtnListener);

		goBack = (ImageButton)findViewById(R.id.mediacontroller_pre_skip);
		goBack.setOnClickListener(goBackBtnListener);

		goFowardBtn = (ImageButton)findViewById(R.id.mediacontroller_next_skip);
		goFowardBtn.setOnClickListener(goFowardBtnListener);

		screenSizeChangeBtn = (ImageButton)findViewById(R.id.mediacontroller_screen_size);
		screenSizeChangeBtn.setOnClickListener(screenSizeChangeBtnListener);

		mediaLock = ((ImageButton)optionView.findViewById(R.id.mediacontroller_lock));
		mediaLock.setOnClickListener(viewLockBtnListener);

		mediaController = optionView.findViewById(R.id.mediacontroller2);
		infoPanel = optionView.findViewById(R.id.info_panel);
		mediacontrollerControls = optionView.findViewById(R.id.mediacontroller_controls);
		mediacontrollerControlsBtn = optionView.findViewById(R.id.mediacontroller_controls_buttons);

		currentPlayTime = ((TextView)optionView.findViewById(R.id.mediacontroller_time_current));
		currentPlayTimeCenter = ((TextView)optionView.findViewById(R.id.operation_info));
		totalPlayTime = ((TextView)optionView.findViewById(R.id.mediacontroller_time_total));

		currentDecoder = ((Button)optionView.findViewById(R.id.current_decoder));
		currentDecoder.setOnClickListener(currentDecoderListener);
		fileName = (TextView)optionView.findViewById(R.id.mediacontroller_file_name);
		
		brightnNessView = optionView.findViewById(R.id.operation_volume_brightness);
		operationBg = (ImageView)optionView.findViewById(R.id.operation_bg);
		operationPercent = (ImageView)optionView.findViewById(R.id.operation_percent);

		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

		videoMenu = optionView.findViewById(R.id.video_menu);
		videoMenu.setOnClickListener(videoMenuListener);

		seekBar = (SeekBar)optionView.findViewById(R.id.mediacontroller_seekbar);
		seekBarListener = new SeekBarListener(this);
		seekBar.setOnSeekBarChangeListener(seekBarListener);
		seekBar.setMax(1000);
		
		hideSystemBar(false);
		if (UIUtils.hasHoneycomb())
			optionView.setOnSystemUiVisibilityChangeListener(new SystemUIVisibilityChangeListener());
		//		this(context, true);


	}

	public MediaController(Context context, boolean useFastForward)
	{
		//this(paramContext);
		//this.n = paramBoolean;
		//a(this.n);
		super(context);
		mContext = context;
		mUseFastForward = useFastForward;
		//        initFloatingWindowLayout();
		//        initFloatingWindow();
	}

	public void setPlayButtonImg()
	{
		if (mediaPlayerControl.isPlaying())
			playBtn.setImageResource(R.drawable.mediacontroller_pause);
		else
			playBtn.setImageResource(R.drawable.mediacontroller_play);
	}

	public void setScreenBrightness(float brightness)
	{
		WindowManager.LayoutParams layoutParams = videoActivity.getWindow().getAttributes();

		if (brightness > 1.0F)
			layoutParams.screenBrightness = 1.0F;
		else if (brightness < 0.01F)
			layoutParams.screenBrightness = 0.01F;

		videoActivity.getWindow().setAttributes(layoutParams);
		//session.put("mediacontroller_brightness", layoutParams.screenBrightness);
	}

	public void setScreenMode()
	{
		if(screenMode > 4)
			screenMode = 0;
		
		mediaPlayerControl.toggleVideoMode(++screenMode);
		
		switch (screenMode)
		{
		default:
		case 0:
			setScreenCenterMsg(videoActivity.getString(R.string.video_original), 500L);
			screenSizeChangeBtn.setImageResource(R.drawable.mediacontroller_sreen_size_100);
			break;
		case 1:
			setScreenCenterMsg(videoActivity.getString(R.string.video_fit_screen), 500L);
			screenSizeChangeBtn.setImageResource(R.drawable.mediacontroller_screen_fit);
			break;
		case 2:
			setScreenCenterMsg(videoActivity.getString(R.string.video_stretch), 500L);
			screenSizeChangeBtn.setImageResource(R.drawable.mediacontroller_screen_size);
			break;
		case 3:
			setScreenCenterMsg(videoActivity.getString(R.string.video_crop), 500L);
			screenSizeChangeBtn.setImageResource(R.drawable.mediacontroller_sreen_size_crop);
			break;
		}
		
		
	}

	public void setScreenCenterMsg(String paramString, long paramLong)
	{
		currentPlayTimeCenter.setText(paramString);
		currentPlayTimeCenter.setVisibility(View.VISIBLE);
		handler.removeMessages(5);
		handler.sendEmptyMessageDelayed(5, paramLong);
	}

	public void screenLock(boolean paramBoolean)
	{
		if (paramBoolean)
		{
			mediaLock.setImageResource(R.drawable.mediacontroller_lock);
			videoMenu.setVisibility(View.GONE);
			mediacontrollerControlsBtn.setVisibility(View.GONE);
			currentDecoder.setVisibility(View.GONE);
			seekBar.setEnabled(false);

			if (isLock != paramBoolean)
				setScreenCenterMsg(videoActivity.getString(R.string.video_screen_locked), 1000L);

			isLock = true;
		}
		else
		{
			mediaLock.setImageResource(R.drawable.mediacontroller_unlock);
			videoMenu.setVisibility(View.VISIBLE);
			mediacontrollerControlsBtn.setVisibility(View.VISIBLE);
			currentDecoder.setVisibility(View.VISIBLE);
			seekBar.setEnabled(true);

			setScreenCenterMsg(videoActivity.getString(R.string.video_screen_unlocked), 1000L);

			isLock = false;
		}

		commonGestures.setTouchListener(gestureTouchListener, !isLock);
	}

	private void setVolumeOrBrightnessDialog(int resourceID, float paramFloat)
	{
		operationBg.setImageResource(resourceID);

		currentPlayTimeCenter.setVisibility(View.GONE);
		brightnNessView.setVisibility(View.VISIBLE);

		Log.d(LOGTAG, "setVolumeOrBrightnessDialog paramFloat>>>"+paramFloat);

		ViewGroup.LayoutParams layoutParams = operationPercent.getLayoutParams();
		layoutParams.height = ((int)(paramFloat * findViewById(R.id.operation_full).getLayoutParams().height));

		Log.d(LOGTAG, "setVolumeOrBrightnessDialog layoutParams.width>>>"+layoutParams.height);

		operationPercent.setLayoutParams(layoutParams);
	}

	// 볼륨 값 검사 후 이미지 셋팅
	public void setVolume(int paramInt)
	{
		Log.d(LOGTAG, "maxVolume>>"+maxVolume);

		if (paramInt > maxVolume)
			paramInt = maxVolume;
		else if (paramInt < 0)
		{
			paramInt = 0;
		}

		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, paramInt, 0);
		setVolumeOrBrightnessDialog(R.drawable.video_volumn_bg, ((float)paramInt / (float)maxVolume));
	}

	@SuppressWarnings("static-access")
	private void setButtonBacklight(boolean paramBoolean)
	{
		WindowManager.LayoutParams layoutParams = videoActivity.getWindow().getAttributes();

		float value;

		if (paramBoolean)
			value = layoutParams.BRIGHTNESS_OVERRIDE_NONE;
		else
			value = layoutParams.BRIGHTNESS_OVERRIDE_OFF;

		try
		{
			layoutParams.getClass().getField("buttonBrightness").set(layoutParams, Float.valueOf(value));
			videoActivity.getWindow().setAttributes(layoutParams);
		}
		catch (Exception exception)
		{
			Log.e(LOGTAG, "dimButtons>>"+exception);
		}
	}

	@TargetApi(14)
	private void hideSystemBar(boolean paramBoolean)
	{
		if(UIUtils.hasHoneycomb())
			if(!paramBoolean)
				optionView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}

	public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
	{
		Log.d(LOGTAG, "dispatchKeyEvent!!!");
		int keyCode = paramKeyEvent.getKeyCode();
		Log.d(LOGTAG, "keyCode>>>"+keyCode);

		switch(keyCode)
		{
		default:
			if(isLocked())
				//show();
				break;
		case KeyEvent.KEYCODE_VOLUME_MUTE:
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			break;
		case KeyEvent.FLAG_KEEP_TOUCH_MODE:
			release();
			mediaPlayerControl.stop();
			break;

		}
		return super.dispatchKeyEvent(paramKeyEvent);
	}

	public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
	{
		Log.d(LOGTAG, "dispatchTouchEvent!!!");
		return super.dispatchTouchEvent(paramMotionEvent);
	}

	public boolean dispatchTrackballEvent(MotionEvent paramMotionEvent)
	{
		Log.d(LOGTAG, "dispatchTrackballEvent!!");

		return super.dispatchTrackballEvent(paramMotionEvent);
		//		boolean bool = true;
		//		if (this.a.b() > 0)
		//			bool = super.dispatchTrackballEvent(paramMotionEvent);
		//		while (true)
		//		{
		//			return bool;
		//			this.k.a();
		//			switch (paramMotionEvent.getAction())
		//			{
		//			case 1:
		//			default:
		//				break;
		//			case 0:
		//				a(bool);
		//				break;
		//			case 2:
		//				c((int)(20000.0F * paramMotionEvent.getX()), bool);
		//			}
		//		}
	}

	@Override   
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Log.d(LOGTAG, "onKeyDown!!!");
		Log.d(LOGTAG, "keyCode>>>"+keyCode);
		if(keyCode == KeyEvent.KEYCODE_BACK) {        
		}
		return false;    
	}

	public boolean isLocked()
	{
		return isLock;
	}

	public boolean onTouchEvent(MotionEvent motionEvent)
	{
		Log.d(LOGTAG, "onTouchEvent");

		handler.removeMessages(3);
		handler.sendEmptyMessageDelayed(3, 3000L);

		return (commonGestures.onTouchEvent(motionEvent)) || (super.onTouchEvent(motionEvent));
	}

	public boolean onTrackballEvent(MotionEvent paramMotionEvent)
	{
		show(3000);
		return false;
	}

	public void onBackPressed()
	{

	}

	public void release()
	{
		if (controlWindow != null)
		{
			controlWindow.dismiss();
			controlWindow = null;
		}
		handler.removeMessages(1);
		handler.removeMessages(2);
		handler.removeMessages(3);
		handler.removeMessages(4);
		handler.removeMessages(5);
	}

	public void setAnchorView(View paramView)
	{
		controlView = paramView;
		int[] arrayOfInt = new int[2];
		controlView.getLocationOnScreen(arrayOfInt);
		Rect rect = new Rect(arrayOfInt[0], arrayOfInt[1], arrayOfInt[0] + controlView.getWidth(), arrayOfInt[1] + controlView.getHeight());
		System.out.println("Rect left:"+arrayOfInt[0]);
		System.out.println("Rect top:"+arrayOfInt[1]);
		System.out.println("Rect rigth:"+arrayOfInt[0] + controlView.getWidth());
		System.out.println("Rect bottom:"+arrayOfInt[1] + controlView.getHeight());

		System.out.println("controlView.getWidth():"+ controlView.getWidth());
		System.out.println("controlView.getHeight():"+ controlView.getHeight());

		setWindowLayoutType();
		controlWindow.showAtLocation(controlView, Gravity.NO_GRAVITY, rect.left, rect.bottom);
	}

	public void setBatteryLevel(String paramString)
	{
		//		this.B.setVisibility(0);
		//		this.B.setText(paramString);
	}

	public void setDownloadRate(String paramString)
	{
		//		this.z.setVisibility(0);
		//		this.z.setText(paramString);
	}

	public void setFileName(String paramString)
	{
		fileName.setText(paramString);
	}

	public void setMediaPlayer(MediaPlayerControl paramMediaPlayerControl)
	{
		System.out.println("setMediaPlayer");
		mediaPlayerControl = paramMediaPlayerControl;

		currentDecoder.setText(mediaPlayerControl.getCurrentDecoder());
		//setPlayButtonImg();
	}

	public void changeDecoderBtn()
	{

	}

	public void playOrPause()
	{
		if (mediaPlayerControl.isPlaying())
		{
			mediaPlayerControl.pause();
			playBtn.setImageResource(R.drawable.mediacontroller_play);
		}
		else
		{			
			mediaPlayerControl.start();
			playBtn.setImageResource(R.drawable.mediacontroller_pause);
		}
	}

	@TargetApi(16)
	public void setWindowLayoutType()
	{
		if (UIUtils.hasICS())
		{
			//optionView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
			//optionView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
			try {
				controlView.setSystemUiVisibility(512);

				Class[] arrayOfClass = new Class[1];
				arrayOfClass[0] = Integer.TYPE;
				Method localMethod;

				localMethod = PopupWindow.class.getMethod("setWindowLayoutType", arrayOfClass);
				PopupWindow localPopupWindow = controlWindow;
				Object[] arrayOfObject = new Object[1];
				arrayOfObject[0] = Integer.valueOf(1003);
				localMethod.invoke(localPopupWindow, arrayOfObject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isShowing()
	{
		return isShow;
	}

	public void show()
	{
		show(3000);
	}

	public void show(int paramInt)
	{
		if (paramInt != 0)
		{
			handler.removeMessages(1);
			handler.sendMessageDelayed(handler.obtainMessage(1), paramInt);
		}

		if (!isShow)
		{
			setButtonBacklight(true);
			handler.removeMessages(3);
			hideSystemBar(true);
			playBtn.requestFocus();
			mediacontrollerControls.startAnimation(inTop);
			infoPanel.startAnimation(inBottom);
			mediaController.setVisibility(VISIBLE);
			setPlayButtonImg();
			handler.sendEmptyMessage(4);
			handler.sendEmptyMessage(2);
			isShow = true;
		}
	}

	public void hide()
	{
		if(isShow)
		{
			handler.removeMessages(4); 
			handler.removeMessages(2);

			mediacontrollerControls.startAnimation(outTop);
			infoPanel.startAnimation(outBottom);
			isShow = false;
		}
		hideSystemBar(false);
	}

	public String stringForTime(long timeMs) {
		long totalSeconds = timeMs / 1000;

		long seconds = totalSeconds % 60;
		long minutes = (totalSeconds / 60) % 60;
		long hours   = totalSeconds / 3600;

		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	public long setProgress() {

		if (mediaPlayerControl == null /* || mDragging*/) {
			return 0;
		}

		long position = mediaPlayerControl.getCurrentPosition();
		long duration = mediaPlayerControl.getDuration();

		if (seekBar != null) {
			if (duration > 0) {
				// use long to avoid overflow
				long pos = 1000L * position / duration;
				seekBar.setProgress( (int) pos);
			}
			int percent = mediaPlayerControl.getBufferPercentage();
			seekBar.setSecondaryProgress(percent * 10);
		}

		if (totalPlayTime != null)
			totalPlayTime.setText(stringForTime(duration));
		if (currentPlayTime != null)
			currentPlayTime.setText(stringForTime(position));

		return position;
	}

	public static boolean isAutoBrightness(Activity activity)
	{
		try
		{
			int mode = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);

			if (mode == 1)
				return true;
			else
				return false;
		}
		catch (Settings.SettingNotFoundException settingNotFoundException)
		{
			settingNotFoundException.printStackTrace();
			return false;
		}
	}

	public static void stopAutoBrightness(Activity paramActivity)
	{
		Settings.System.putInt(paramActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	public final class SystemUIVisibilityChangeListener implements View.OnSystemUiVisibilityChangeListener
	{
		public void onSystemUiVisibilityChange(int visibility) {
			// TODO Auto-generated method stub
			Log.d(LOGTAG, "visibility>>>"+ visibility);
			if (visibility == 0)
			{
				//handler.sendEmptyMessageDelayed(3, 3000L);
				handler.sendEmptyMessage(3);
			}
		}
	}

	public MediaPlayerControl getMediaPlayerControl()
	{
		return mediaPlayerControl;
	}

	public TextView getCurrentPlayTime() {
		return currentPlayTime;
	}

	public Handler getHandler() {
		return handler;
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public TextView getCurrentPlayTimeCenter() {
		return currentPlayTimeCenter;
	}

	public Activity getVideoActivity() {
		return videoActivity;
	}

	public SeekBar getSeekBar() {
		return seekBar;
	}

	public Button getCurrentDecoder() {
		return currentDecoder;
	}

	public int getScreenMode() {
		return screenMode;
	}

	public abstract interface MediaPlayerControl
	{
		public abstract int getBufferPercentage();

		public abstract long getCurrentPosition();

		public abstract long getDuration();

		public abstract long goBack();

		public abstract long goForward();

		public abstract boolean isPlaying();

		public abstract void next();

		public abstract void pause();

		public abstract void previous();

		public abstract void removeLoadingView();

		public abstract float scale(float paramFloat);

		public abstract void seekTo(long paramLong);

		public abstract void showMenu();

		public abstract void snapshot();

		public abstract void start();

		public abstract void stop();

		public abstract void toggleVideoMode(int paramInt);

		public abstract void release();

		public abstract String changeDecoder();
		
		public abstract String getCurrentDecoder();
	}
}
