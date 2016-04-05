package com.example.bongplayer2;

import io.vov.vitamio.ThumbnailUtils;
import io.vov.vitamio.widget.OutlineTextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Html;
import android.text.style.BulletSpan;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appunite.ffmpeg.FFmpegDisplay;
import com.appunite.ffmpeg.FFmpegGLSurfaceView;
import com.appunite.ffmpeg.FFmpegPlayer;
import com.appunite.ffmpeg.FFmpegStreamInfo;
import com.example.bongplayer2.Listener.ScreenSizeChangeBtnListener;
import com.example.bongplayer2.Utils.CursorUtils;
import com.example.bongplayer2.adapter.MediaFolderCursorAdapter.MeidaFolderCursorIconClickListener;
import com.example.bongplayer2.bean.PlayPrefInfo;
import com.example.bongplayer2.bean.SMIData;
import com.example.bongplayer2.dialog.TabDialog;
import com.example.bongplayer2.dialog.TunerDialog;
import com.yixia.zi.preference.APreference;
import com.yixia.zi.utils.IntentHelper;

public class VideoActivity extends BaseActivity implements MediaController.MediaPlayerControl, SurfaceHolder.Callback, View.OnTouchListener, OnPreparedListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnSeekCompleteListener, OnVideoSizeChangedListener, Handler.Callback 
{
	private SurfaceHolder surfaceHolder;
	//private PlayerFunctionInterface player;
	private MediaPlayer mediaPlayer;
	private FFmpegPlayer ffPlayer;

	private MediaController mediaController;

	private HWDecodingVideoView hwView;
	private FFmpegGLSurfaceView swView;
	private View currentView;

	private SurfaceView sv;
	private final static String LOGTAG = "VIDEO_ACITIVITY";
	private final String Level1 = "Level1"; 
	private final String Level2 = "Level2";

	private String videoPath = null;
	private static final IntentFilter intentFilter;

	private boolean isLocked;
	private boolean isShowMenu;
	private Uri uri;
	private Uri path;
	private String i;
	private String k; 

	private View videoRoot;
	private View videoLoading;
	private View subtitleContainer;
	private OutlineTextView subtitleText;
	private TextView videoLoadingText;
	private ImageView subtitleImage;

	private Animation loadingRotate;
	private View videoLoadingProgress;

	private int videoWidth = 0;
	private int videoHeight = 0;
	private Display currentDisplay;
	private APreference aPreference;
	private String decoderOption;
	private FFmpegStreamInfo audioStream = null;
	private FFmpegStreamInfo subtitleStream = null;
	private boolean isActivityCreate = false;
	private boolean reOpen = false;
	private boolean isPlayerPrepare = false;
	private boolean isRestart = false;
	private boolean surfaceDestroy = false;
	private HashMap<String, String> videoPlayTimeInfo;

	private ArrayList playList;

	private long currentPosition = 0L;
	private int videoMode = 0;

	private String fileName;
	private AtomicBoolean atomicBool = new AtomicBoolean(Boolean.FALSE.booleanValue());
	private PlayPrefInfo playPrefInfo;

	private ArrayList<SMIData> parsedSmi;
	private boolean useSmi;
	private int countSmi;
	private Handler subtitleHandler;
	private float prefRatio;
	private SubTitleThread subTitleThread;
	private String smbURL;
	private ArrayList<String> videoPaths;
	static
	{
		intentFilter = new IntentFilter("");
	}

	public void videoPrefSetting()
	{
		prefRatio = playPrefInfo.getScreenRatio();
		subtitleText.setTextColor(playPrefInfo.getSubColor());
		subtitleText.setTextSize(playPrefInfo.getSubFontSize());
		//subtitleText.setTextSize()

	}

	private void setController()
	{
		Log.d(Level1, "setController()");

		if (mediaController != null)
		{
			isLocked = mediaController.isLocked();
			mediaController.release();
		}

		mediaController = new MediaController(VideoActivity.this);
		mediaController.setMediaPlayer(VideoActivity.this);

		mediaController.setAnchorView(videoRoot);

		//		if (this.C != null)
		//			if ((this.C.getScheme() != null) && (!this.C.getScheme().equals("file")))
		//				break label142;
		//		label142: for (String str = FileUtils.getName(this.C.toString()); ; str = this.C.getLastPathSegment())
		//		{
		//			if (str == null)
		//				str = "null";
		//			if (this.i == null)
		//				this.i = str;
		//			mediaController.setFileName(this.i);
		//			setBatteryLevel();
		//			return;
		//		}

		mediaController.setFileName(playPrefInfo.getFileName());
	}


	private void a(Intent paramIntent)
	{
		Log.d(Level1, "a(Intent paramIntent)");
		//		this.g = paramIntent.getBooleanExtra("notification", false);
		//		if (this.g)
		//		{
		//			this.n = true;
		//			this.m = paramIntent.getIntExtra("loopCount", 1);
		//			return;
		//		}
		//		Uri localUri1 = IntentHelper.getIntentUri(paramIntent);
		//		if (localUri1 == null)
		//			a(-7);
		//		String str = localUri1.toString();
		//		if (!str.equals(localUri1.toString()));
		//		for (Uri localUri2 = Uri.parse(str); ; localUri2 = localUri1)
		//			try
		//		{
		//				Playlist localPlaylist = this.B;
		//				this.B = Playlist.createPlaylist(str);
		//				if ((this.B != null) && (this.B.size() > 0) && (this.B.hasNext()))
		//				{
		//					Object[] arrayOfObject2 = new Object[1];
		//					arrayOfObject2[0] = str;
		//					Log.i("PLAYLIST: %s", arrayOfObject2);
		//					Track localTrack = this.B.next();
		//					paramIntent.putExtra("displayName", localTrack.getTitle());
		//					this.C = Uri.parse(localTrack.getUrl());
		//				}
		//				while (true)
		//				{
		//					if ((this.B == null) || (this.B.size() <= 0))
		//					{
		//						this.B = null;
		//						this.C = localUri2;
		//					}
		//					this.h = paramIntent.getBooleanExtra("lockScreen", false);
		//					this.i = paramIntent.getStringExtra("displayName");
		//					this.l = paramIntent.getBooleanExtra("fromStart", false);
		//					this.n = paramIntent.getBooleanExtra("savePosition", false);
		//					this.o = paramIntent.getBooleanExtra("saveUri", true);
		//					this.q = paramIntent.getFloatExtra("startPosition", -1.0F);
		//					this.m = paramIntent.getIntExtra("loopCount", 1);
		//					this.p = paramIntent.getIntExtra("parentId", 0);
		//					this.s = paramIntent.getStringExtra("subPath");
		//					this.j = paramIntent.getStringExtra("httpHeader");
		//					this.D.put("vplayer_sub_enabled", paramIntent.getBooleanExtra("subShown", this.D.getBoolean("vplayer_sub_enabled", true)));
		//					Object[] arrayOfObject1 = new Object[5];
		//					arrayOfObject1[0] = Boolean.valueOf(this.h);
		//					arrayOfObject1[1] = this.i;
		//					arrayOfObject1[2] = Boolean.valueOf(this.l);
		//					arrayOfObject1[3] = Float.valueOf(this.q);
		//					arrayOfObject1[4] = Integer.valueOf(this.m);
		//					Log.i("L: %b, N: %s, S: %b, P: %f, LP: %d", arrayOfObject1);
		//					break;
		//					this.B = localPlaylist;
		//				}
		//		}
		//		catch (Exception localException)
		//		{
		//			while (true)
		//			{
		//				Log.e("parseIntent", localException);
		//				this.B = null;
		//			}
		//		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//System.out.println("core!!!!!!!!>>>>>>" + Runtime.getRuntime().availableProcessors());
		//Log.d(LOGTAG, "onCreate!!");
		Log.d(Level1, "onCreate");
		super.setNoCustTheme(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen mode

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		if(bundle != null)
		{
			path = bundle.getParcelable("uri");
			videoPaths = bundle.getStringArrayList("videoPaths");
			smbURL = intent.getStringExtra("smburl");
		}
		subtitleHandler = new Handler(this);
		aPreference = new APreference(this);
		decoderOption = aPreference.getString(getString(R.string.pref_key_decoder_choice), "HW");
		//videoPaths
		playPrefInfo = new PlayPrefInfo(aPreference, this); 

		//aPreference.registerObserver(l);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		videoPath = intent.getStringExtra("videoPath");
		isActivityCreate = true;

		setUILayout();

		currentDisplay = getWindowManager().getDefaultDisplay();
		a(getIntent());
		receiverRegister();

	}

	private void setUILayout()
	{
		Log.d(Level1, "setUILayout");
		setContentView(R.layout.video_view);

		getWindow().setBackgroundDrawable(null); // background set

		videoRoot = findViewById(R.id.video_root);
		readSubtileFile();
		videoLoadingText = ((TextView)findViewById(R.id.video_loading_text));
		subtitleContainer = findViewById(R.id.subtitle_image);
		subtitleText = ((OutlineTextView)findViewById(R.id.subtitle_text));
		subtitleImage = ((ImageView)findViewById(R.id.subtitle_image));
		videoLoading = findViewById(R.id.video_loading);
		videoLoadingProgress = videoLoading.findViewById(R.id.video_loading_progress);
		loadingRotate = AnimationUtils.loadAnimation(this, R.anim.loading_rotate);
		getWindow().addFlags(WindowManager.LayoutParams.ALPHA_CHANGED);

		if(decoderOption.equals("HW"))
		{
			setHWDecoder();
		}
		else
		{
			setSWDecoder();
		}
		startSubtile();
	}

	@SuppressWarnings("deprecation")
	public void setHWDecoder()
	{
		Log.d(Level1, "setHWDecoder");

		hwView = ((HWDecodingVideoView)findViewById(R.id.hw_view));
		hwView.initialize(this, null, true);
		currentView = hwView;
		if(ffPlayer != null)
			releaseSWDecoder();

		if(swView != null)
			swView.setVisibility(View.INVISIBLE);

		hwView.setVisibility(View.VISIBLE);

		surfaceHolder = hwView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
			decoderOption ="HW";
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnErrorListener(this);
			mediaPlayer.setOnInfoListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
		} 
		//		else {
		//			mediaPlayer.reset();
		//		}

		try {
			if(path != null)
				mediaPlayer.setDataSource(this, path);
			else if(videoPath != null)
				mediaPlayer.setDataSource(videoPath);
		}catch (IllegalArgumentException e) {
			Log.v(LOGTAG, e.getMessage());
			finish();
		} catch (IllegalStateException e) {
			Log.v(LOGTAG, e.getMessage());
			finish();
		} catch (IOException e) {
			Log.v(LOGTAG, e.getMessage());
			finish();
		}
	}

	@SuppressWarnings("deprecation")
	public void setSWDecoder()
	{	
		Log.d(Level1, "setSWDecoder");
		swView = (FFmpegGLSurfaceView)findViewById(R.id.sw_view);
		swView.initialize(this, null, true);
		currentView = swView;
		if(mediaPlayer !=null)
			releaseHWDecoder();

		if(hwView != null)
			hwView.setVisibility(View.INVISIBLE);
		swView.setVisibility(View.VISIBLE);

		surfaceHolder = swView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		ffPlayer = new FFmpegPlayer((FFmpegDisplay) swView, this);
		decoderOption ="SW";
		if(path != null)
			ffPlayer.setDataSource(path.toString(), null, null, audioStream, subtitleStream);
		else if(videoPath != null)
			ffPlayer.setDataSource(videoPath, null, null, audioStream, subtitleStream);

		ffPlayer.resume();
		seekTo(currentPosition);
	}

	public void releaseHWDecoder()
	{
		Log.d(Level1, "releaseHWDecoder");
		if(mediaPlayer != null)
		{
			if(isPlaying())
			{
				mediaPlayer.stop();				
			}
		}

		mediaPlayer.release();
		mediaPlayer = null;
		hwView = null;
		surfaceHolder = null;
	}

	public void releaseSWDecoder()
	{
		Log.d(Level1, "releaseSWDecoder");
		if(ffPlayer != null)
		{
			//			if(isPlaying())
			//			{	
			ffPlayer.pause();
			ffPlayer.stop();
			//			}
		}

		swView = null;
		surfaceHolder = null;
	}

	private void setBatteryLevel()
	{
		if (mediaController != null)
			mediaController.setBatteryLevel(this.k);
	}

	private ArrayList getPlayList()
	{
		if (playList == null)
			playList = new ArrayList();
		try
		{
			String str1 = uri.toString();
			String str2 = str1.substring(0, str1.lastIndexOf("/"));

			ContentResolver contentResolver = getContentResolver();
			Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
			String[] proj = {CursorUtils.VIDEO_NAME, CursorUtils.VIDEO_PATH};
			String[] selections = new String[1];
			selections[0] = (str2 + "%");
			Cursor cursor = contentResolver.query(uri, proj, "_data like ?", selections, null);

			if(cursor != null)
			{

			}

			if (cursor.moveToNext())
			{
				String mediaName = cursor.getString(0);
				String mediaPath = cursor.getString(1);
				playList.add(Pair.create(mediaName, Uri.parse(mediaPath)));
			}

			return playList;
		}
		finally
		{
			//			if (cursor != null)
			//				cursor.close();
		}
	}


	public void onConfigurationChanged(Configuration paramConfiguration)
	{
		Log.d(Level1, "onConfigurationChanged");
		setScreenSize(videoMode);
		setController();

		super.onConfigurationChanged(paramConfiguration);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.d(Level1, "onPrepared");

		videoWidth = mp.getVideoWidth();
		videoHeight = mp.getVideoHeight();

		if (videoWidth > currentDisplay.getWidth()
				|| videoHeight > currentDisplay.getHeight()) {
			float heightRatio = (float) videoHeight
					/ (float) currentDisplay.getHeight();
			float widthRatio = (float) videoWidth
					/ (float) currentDisplay.getWidth();

			if (heightRatio > 1 || widthRatio > 1) {
				if (heightRatio > widthRatio) {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) heightRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) heightRatio);
				} else {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) widthRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) widthRatio);
				}
			}
		}

		//hwView.etLayoutParams(new LinearLayout.LayoutParams(videoWidth, videoHeight));
		isPlayerPrepare = true;
		mp.start();
	}

	public void onCompletion(MediaPlayer mp) {
		Log.d(Level1, "onCompletion");
		finish();
	}

	public boolean onError(MediaPlayer mp, int whatError, int extra) {
		Log.d(Level1, "onError");

		if (whatError == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			Log.v(LOGTAG, "Media Error, Server Died " + extra);
		} else if (whatError == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
			Log.v(LOGTAG, "Media Error, Error Unknown " + extra);
		}

		return false;
	}

	public boolean onInfo(MediaPlayer mp, int whatInfo, int extra) {
		Log.d(Level1, "onInfo");

		if (whatInfo == MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING) {
			Log.v(LOGTAG, "Media Info, Media Info Bad Interleaving " + extra);
		} else if (whatInfo == MediaPlayer.MEDIA_INFO_NOT_SEEKABLE) {
			Log.v(LOGTAG, "Media Info, Media Info Not Seekable " + extra);
		} else if (whatInfo == MediaPlayer.MEDIA_INFO_UNKNOWN) {
			Log.v(LOGTAG, "Media Info, Media Info Unknown " + extra);
		} else if (whatInfo == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING) {
			Log.v(LOGTAG, "MediaInfo, Media Info Video Track Lagging " + extra);
			/*
			 * Android Version 2.0 and Higher } else if (whatInfo ==
			 * MediaPlayer.MEDIA_INFO_METADATA_UPDATE) {
			 * Log.v(LOGTAG,"MediaInfo, Media Info Metadata Update " + extra);
			 */
		}
		return false;
	}

	public int getVideoWidth()
	{
		Log.d(Level1, "getVideoWidth");
		if(decoderOption.equals("SW"))
		{
			return ffPlayer.getframewidth();
		}
		else
		{
			return mediaPlayer.getVideoWidth();
		}
	}
	public int getVideoHeight()
	{
		Log.d(Level1, "getVideoHeight");
		if(decoderOption.equals("SW"))
		{
			return ffPlayer.getframeheight();
		}
		else
		{
			return mediaPlayer.getVideoHeight();
		}
	}		

	public int getVideoAspectRatio()
	{
		Log.d(Level1, "getVideoAspectRatio");
		return 1;
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		Log.d(Level1, "onVideoSizeChanged");
	}


	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.d(Level1, "onSeekComplete");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d(Level1, "surfaceCreated");

		if(mediaPlayer != null)
		{
			mediaPlayer.setDisplay(holder);

			if(!surfaceDestroy){
				try{
					mediaPlayer.prepare();

				} catch (Exception e) {
					Toast.makeText(this, "error : " + e.getMessage(), 
							Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				startPlayer();
				seekTo(currentPosition);
				surfaceDestroy = false;
			}
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

		Log.d(Level1, "surfaceChanged");

		if(mediaPlayer != null)
		{
			mediaPlayer.setDisplay(holder);
		}

		setScreenSize(videoMode);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d(Level1, "surfaceDestroyed");


		currentTimeSave();

		surfaceHolder = null;

		if(hwView != null)
			hwView = null;

		if(swView != null)
			swView = null;

		surfaceDestroy = true;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCurrentPosition() {
		// TODO Auto-generated method stub

		Log.d(Level1, "getCurrentPosition");

		int gurrentPosition = 0 ;

		if(decoderOption.equals("SW"))
		{
			//return ffPlayer.getCurrentPosition();
			gurrentPosition = ffPlayer.getCurrentPosition();
			Log.d(LOGTAG, "getCurrent()>>>"+gurrentPosition);

			return gurrentPosition * 1000;
		}
		else
		{
			gurrentPosition = mediaPlayer.getCurrentPosition();
			Log.d(LOGTAG, "getCurrent()>>>"+gurrentPosition);	

			return gurrentPosition;
		}
	}

	@Override
	public long getDuration() {
		// TODO Auto-generated method stub
		Log.d(Level1, "getDuration");

		int duration = 0 ;

		if(decoderOption.equals("SW"))
		{
			duration = ffPlayer.getVideoDurationNative();
			Log.d(LOGTAG, "getDuration()>>>"+duration);
			return duration * 1000;
		}
		else
		{
			duration = mediaPlayer.getDuration();
			Log.d(LOGTAG, "getDuration()>>>"+duration);
			return duration;
		}


	}

	@Override
	public long goBack() {
		// TODO Auto-generated method stub
		Log.d(Level1, "goBack");
		long startTime = 0L;

		long seekTime = getCurrentPosition() - 1000 * aPreference.getInt("pref_key_micro_seek_duration", 5);

		if(seekTime <= startTime)
			seekTime = startTime;

		seekTo(seekTime);
		return seekTime;
	}

	@Override
	public long goForward() {
		// TODO Auto-generated method stub
		Log.d(Level1, "goForward");
		long seekTime = getCurrentPosition() + 1000 * aPreference.getInt("pref_key_micro_seek_duration", 5);
		long finalTime = getDuration();

		if (seekTime >= finalTime)
			seekTime = finalTime;

		seekTo(seekTime);

		return seekTime;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		Log.d(Level1, "isPlaying");

		if(decoderOption.equals("HW"))
			return mediaPlayer.isPlaying();
		else
		{
			if(ffPlayer.isPlaying() == 0)
				return true;
			else 
				return false;
		}
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		//
		//		if (this.A == null)
		//			this.A = i();
		//		Pair localPair1 = null;
		//		int i1 = this.A.size();
		//		for (int i2 = 0; ; i2++)
		//			if (i2 < i1)
		//			{
		//				Pair localPair2 = (Pair)this.A.get(i2);
		//				if ((this.C.toString().equals(((Uri)localPair2.second).toString())) && (i2 + 1 < i1))
		//					localPair1 = (Pair)this.A.get(i2 + 1);
		//			}
		//			else
		//			{
		//				if (localPair1 != null)
		//					a((Uri)localPair1.second, (String)localPair1.first, true);
		//				return;
		//			}

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Log.d(Level1, "pause");
		if(decoderOption.equals("SW"))
		{
			ffPlayer.pause();
		}
		else
		{
			mediaPlayer.pause();
		}
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLoadingView() {
		// TODO Auto-generated method stub
		Log.d(Level1, "removeLoadingView");
		videoLoading.setVisibility(View.GONE);
	}

	@Override
	public float scale(float paramFloat) {
		// TODO Auto-generated method stub
		Log.d(Level1, "scale");
		//float prefRatio = playPrefInfo.getScreenRatio();

		int videoWidth = getVideoWidth();
		int videoHeight = getVideoHeight();

		float VideoAspectRatio = getVideoAspectRatio();

		//Log.d(LOGTAG, "paramFloa = "+paramFloat+" mvH/vH = "+(float)hwView.mVideoHeight / (float)videoHeight);

		// hw view / soft view
		float changeRatio = (float)hwView.mVideoHeight / (float)videoHeight + (paramFloat - 1.0F);

		//		float changeRatio = 1.0F;
		//		if(paramFloat > 1)
		//			changeRatio = (float)hwView.mVideoHeight / (float)videoHeight + 0.01F;
		//		else if(paramFloat <= 1 )
		//			changeRatio = (float)hwView.mVideoHeight / (float)videoHeight - 0.01F;

		//		Log.d(LOGTAG, "changeRatio>>>>>>>>>>>"+changeRatio);

		if (changeRatio * videoWidth >= 2048.0F)
			changeRatio = 2048.0F / videoWidth;

		if (changeRatio * videoHeight >= 2048.0F)
			changeRatio = 2048.0F / videoHeight;

		float f4;

		if (changeRatio < 0.5F)
			f4 = 0.5F;
		else
		{
			f4 = changeRatio;
		}

		//		Log.d(LOGTAG, "f4>>>>>>>>>>>"+f4);

		//		if(paramFloat >= 1)
		//		{
		//			hwView.mVideoHeight += (int)(hwView.mVideoHeight * 0.01F);
		//		}
		//		else
		//		{
		//			hwView.mVideoHeight -= (int)(hwView.mVideoHeight * 0.01F);
		//		}
		//
		//		if(hwView.mVideoHeight < (int)(videoHeight * 0.5F))
		//			hwView.mVideoHeight = (int)(videoHeight * 0.5F);
		//		else if(hwView.mVideoHeight > (int)(videoHeight * 1.5F))
		//			hwView.mVideoHeight = (int)(videoHeight * 1.5F);
		//		
		//		hwView.mVideoHeight = (int)(videoHeight * 0.5F);

		hwView.mVideoHeight = (int)(f4 * videoHeight);

		//		Log.d(LOGTAG, "mVideoHeight>>>>>>>>>>>"+(int)(f4 * videoHeight));
		hwView.setVideoLayout(4, prefRatio, videoWidth, videoHeight, VideoAspectRatio);
		return f4;
	}

	@Override
	public void seekTo(long paramLong) {
		// TODO Auto-generated method stub
		Log.d(Level1, "seekTo");

		if(decoderOption.equals("SW"))
		{

			ffPlayer.seek((int)paramLong/1000);
		}
		else
		{
			mediaPlayer.seekTo((int)paramLong);
		}
	}

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub

		Log.d(Level1, "showMenu");

		isShowMenu = false;
		if (/*(!this.D.getBoolean("vplayer_background_playback", false)) && */(isPlaying()))
		{
			stopPlayer();
			isShowMenu = true;
		}

		//		TabDialog tabDialog = new TabDialog(this, R.style.Theme_ZI_Dialog_Translucent/*, this.M*/);
		//		tabDialog.setOnDismissListener(new pm(this));
		//		tabDialog.show();

		TunerDialog tunerDialog = new TunerDialog(this, null, null, 1);
		tunerDialog.setOnDismissListener(new pm(this));
		tunerDialog.show();
	}

	@Override
	public void snapshot() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		Log.d(Level1, "start");
		if(decoderOption.equals("SW"))
		{
			ffPlayer.resume();
		}
		else
		{
			mediaPlayer.start();
		}
	}

	public void startPlayer()
	{
		Log.d(Level1, "startPlayer");

		if(isAliveActivity())
			if (!isPlaying())
			{
				start();
			}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		Log.d(Level1, "stop");
		onBackPressed();
	}

	public void stopPlayer()
	{
		Log.d(Level1, "stopPlayer");
		if(decoderOption.equals("SW"))
		{
			ffPlayer.pause();
		}
		else
		{
			mediaPlayer.pause();
		}
	}


	@Override
	public void toggleVideoMode(int paramInt) {
		//TODO Auto-generated method stub
		Log.d(Level1, "toggleVideoMode");
		videoMode = paramInt;
		//		this.aa.put(this.D + ".mode", this.Z);
		setScreenSize(paramInt);
	}

	private void setScreenSize(int videoMode)
	{
		Log.d(Level1, "setScreenSize");
		if(decoderOption.equals("HW"))
			hwView.setVideoLayout(videoMode, (float)aPreference.getDouble("pref_key_screen_ratio", 0.0D), getVideoWidth(), getVideoHeight(), getVideoAspectRatio());
		else
			swView.setVideoLayout(videoMode, (float)aPreference.getDouble("pref_key_screen_ratio", 0.0D), getVideoWidth(), getVideoHeight(), getVideoAspectRatio());
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		Log.d(Level1, "release");
		if(decoderOption.equals("SW"))
		{
			ffPlayer.stop();
			ffPlayer = null;
		}
		else
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String changeDecoder() {
		// TODO Auto-generated method stub

		mediaController.getHandler().removeMessages(1);
		mediaController.getHandler().removeMessages(2);
		mediaController.getHandler().removeMessages(3);
		mediaController.getHandler().removeMessages(4);
		mediaController.getHandler().removeMessages(5);		

		currentTimeSave();
		stopPlayer();

		Log.d(Level1, "changeDecoder");
		if(decoderOption.equals("SW"))
		{
			setHWDecoder();

		}
		else
		{
			setSWDecoder();

		}
		return decoderOption;
	}

	public Object alivePlayer()
	{
		Log.d(Level1, "alivePlayer");
		if(decoderOption.equals("SW"))
		{
			if(ffPlayer != null)
			{
				Log.d(Level1, "alive ffPlayer");
				return ffPlayer;
			}
		}
		else
		{
			if(mediaPlayer != null)
			{
				Log.d(Level1, "alive mediaPlayer");
				return mediaPlayer;
			}
		}
		Log.d(Level1, "alivePlayer Died");
		return null;
	}


	public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
	{
		Log.d(LOGTAG, "dispatchTouchEvent");
		//		this.k.a();
		//		af();
		return super.dispatchTouchEvent(paramMotionEvent);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		Log.d(Level1, "onTouchEvent");

		if (mediaController.isShowing()) {
			mediaController.hide();
		} else {
			mediaController.show();
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		Log.d(Level1, "onTouch");

		int eventValue = event.getAction();

		switch(eventValue)
		{
		case 1:
		default:
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Log.d(LOGTAG, "onKeyDown");
		Log.d(LOGTAG, "keyCode>>>"+keyCode);


		return false;
	}

	public void onBackPressed()
	{
		Log.d(Level1, "onBackPressed");

		//		if ((isActivityCreate) && (isAliveActivity()) && (aPreference.getBoolean("MangoPlayer_background_playback", false)) && (isPlaying()))
		if ((isActivityCreate) && (isAliveActivity()) && (isPlaying()))
			releaseSurface();
		super.onBackPressed();
	}

	public void onDestroy()
	{
		Log.d(Level1, "onDestroy");
		super.onDestroy();

		playPrefInfo.savePref(aPreference);

		if (!isActivityCreate)
			return;
		else
		{
			receiverRegister(); // broadcast receiver unregi

			if(alivePlayer() != null)
			{
				if(decoderOption.equals("SW"))
				{
					ffPlayer = null;
				}
				else
				{
					mediaPlayer = null;
				}
			}

			if (mediaController != null)
				mediaController.release();
			aPreference.unregisterObserver();
		}
		setResult(RESULT_OK);
		finish();
	}

	public void onPause()
	{
		Log.d(Level1, "onPause");
		super.onPause();
		if(subTitleThread != null)
			subTitleThread.setRunningState(false);
		currentTimeSave();
		if (!isActivityCreate)
			return;
		else
		{
			//			if ((!isAliveActivity()) || (aPreference == null) || (aPreference.getBoolean("MangoPlayer_background_playback", false)) || (!isPlaying()))
			mediaController.getHandler().removeMessages(1);
			mediaController.getHandler().removeMessages(2);
			mediaController.getHandler().removeMessages(3);
			mediaController.getHandler().removeMessages(4);
			mediaController.getHandler().removeMessages(5);

			stopPlayer();
		}

		if(videoPath != null)
		{
			aPreference.put("Last_Play_Video_Path", videoPath);
			aPreference.put("Last_Play_Video_CurrentTime", currentPosition);
		}
	}

	public void currentTimeSave()
	{
		if(decoderOption.equals("SW"))
		{
			currentPosition =  ffPlayer.getCurrentPosition();
		}
		else
		{
			currentPosition =  mediaPlayer.getCurrentPosition();
		}
	}

	private boolean isAliveActivity()
	{
		Log.d(Level1, "isAliveActivity");
		Log.d(Level1, ""+((isActivityCreate) && (alivePlayer() != null)));
		return ((isActivityCreate) && (alivePlayer() != null));		
	}


	private void receiverRegister()
	{
		Log.d(Level1, "receiverRegister");
		//		if (!this.J)
		//		{
		//			this.F = new pw(this, 0);
		//			registerReceiver(this.F, c);
		//			this.H = new px(this, 0);
		//			registerReceiver(this.H, b);
		//			this.I = new pv(this, 0);
		//			registerReceiver(this.I, e);
		//			this.G = new VideoActivity.HeadsetPlugReceiver(this);
		//			registerReceiver(this.G, d);
		//			this.J = true;
		//		}
		//		while (true)
		//		{
		//			return;
		//			try
		//			{
		//				if (this.F != null)
		//					unregisterReceiver(this.F);
		//				if (this.H != null)
		//					unregisterReceiver(this.H);
		//				if (this.G != null)
		//					unregisterReceiver(this.G);
		//				if (this.I != null)
		//					unregisterReceiver(this.I);
		//				label172: this.J = false;
		//			}
		//			catch (IllegalArgumentException localIllegalArgumentException)
		//			{
		//				break label172;
		//			}
		//		}
	}

	protected void onSaveInstanceState(Bundle paramBundle)
	{
		Log.d(Level1, "onSaveInstanceState");
		super.onSaveInstanceState(paramBundle);
	}

	public void onStart()
	{
		Log.d(Level1, "onStart");
		super.onStart();
		if (!isActivityCreate)
			return;
		else
		{
			setRequestedOrientation(aPreference.getInt("screen_orientation", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));
		}
	}

	@SuppressWarnings("deprecation")
	public void onResume()
	{
		Log.d(Level1, "onResume");
		super.onResume();

		if (!isActivityCreate)
			return;
		else
		{
			setRequestedOrientation(aPreference.getInt("screen_orientation", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));


			//			if (isAliveActivity())
			//			{
			//				//screen Lock
			//				if (((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode())
			//				{
			//					
			//				}
			//				startPlayer();
			//			}
			if(isRestart){
				if(subTitleThread != null)
					subTitleThread.setRunningState(true);
			}

			if(surfaceDestroy)
			{
				if(decoderOption.equals("HW"))
				{
					hwView = ((HWDecodingVideoView)findViewById(R.id.hw_view));
					hwView.initialize(this, null, true);
					currentView = hwView;
					if(ffPlayer != null)
						releaseSWDecoder();

					if(swView != null)
						swView.setVisibility(View.INVISIBLE);

					hwView.setVisibility(View.VISIBLE);

					surfaceHolder = hwView.getHolder();
					surfaceHolder.addCallback(this);
					surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				}
				else
				{
					Log.d(Level1, "setSWDecoder");
					swView = (FFmpegGLSurfaceView)findViewById(R.id.sw_view);
					swView.initialize(this, null, true);
					currentView = swView;
					if(mediaPlayer !=null)
						releaseHWDecoder();

					if(hwView != null)
						hwView.setVisibility(View.INVISIBLE);
					swView.setVisibility(View.VISIBLE);
					surfaceHolder = swView.getHolder();
					surfaceHolder.addCallback(this);
					surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	
				}
			}
			else if(isActivityCreate && !surfaceDestroy && isRestart)
			{
				startPlayer();

			}
		}
	}
	@Override
	public void onRestart()
	{
		Log.d(Level1, "onRestart");
		super.onRestart();
		isRestart = true;
	}

	public void reOpen()
	{
		Log.d(Level1, "reOpen");
		a(uri, fileName, false);
	}

	private void a(Uri paramUri, String paramString, boolean paramBoolean)
	{
		Log.d(Level1, "a(Uri paramUri, String paramString, boolean paramBoolean)");
		if (isAliveActivity()) 		
		{
			currentTimeSave();
			release();
			//releaseContext();
		}

		Intent localIntent = getIntent();

		if (mediaController!= null)
			localIntent.putExtra("lockScreen", mediaController.isLocked());

		localIntent.putExtra("startPosition", 7.699999809265137D);
		localIntent.putExtra("fromStart", paramBoolean);
		localIntent.putExtra("displayName", paramString);
		localIntent.setData(paramUri);

		a(localIntent);

		uri = paramUri;
		if (videoRoot != null)
			videoRoot.invalidate();

		//this.T.set(false);

		setUILayout(); // UI Set;
	}


	public void releaseSurface()
	{
		Log.d(Level1, "releaseSurface");
		if(decoderOption.equals("SW"))
		{
			swView = null;
			surfaceHolder = null;
		}
		else
		{
			hwView = null;
			surfaceHolder = null;
		}
	}

	public void onStop()
	{
		Log.d(Level1, "onStop");
		super.onStop();

	}

	// current activity focus check
	//	hasFocus == true (Activity focus O)		1) onCreate()		2) onResume()
	//	hasFocus == false (Activity focus X)	1) onPause()		2) onDestory()
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus == true) {

			Log.d(LOGTAG, "onWindowFocusChanged has true");
			setController();
		} else {
			Log.d(LOGTAG, "onWindowFocusChanged has false");


		}
	}

	public final class pm implements DialogInterface.OnDismissListener
	{
		public pm(VideoActivity paramVideoActivity)
		{

		}

		public final void onDismiss(DialogInterface paramDialogInterface)
		{
			//if (VideoActivity.c(this.a))
			startPlayer();
		}
	}

	@Override
	public String getCurrentDecoder() {
		// TODO Auto-generated method stub
		Log.d(Level1, "getCurrentDecoder");
		return decoderOption;
	}

	public void readSubtileFile()
	{
		//readData
		String smiPath = null;
		if(videoPath != null)
			smiPath = videoPath.substring(0,videoPath.lastIndexOf(".")) + ".smi";
		else if(path != null)
		{
			String uriVideoPath = path.toString();
			Log.d(LOGTAG, "uriVideoPath>>>"+uriVideoPath);
			smiPath = smbURL.substring(0,smbURL.lastIndexOf(".")) + ".smi";
			Log.d(LOGTAG, "smiPath>>>"+smiPath);
		}

		if(smiPath != null && path != null)
		{
			try {
				SmbFile file;
				file = new SmbFile(smiPath, new NtlmPasswordAuthentication(null, "jlansrv", "jlan"));
				

				try {
					if(true) {
						useSmi = true;
						parsedSmi = new ArrayList<SMIData>();
						try {
							String enCodingType = encodingCheck(file.toString());
							//
							BufferedReader in;

							in = new BufferedReader(new InputStreamReader(new SmbFileInputStream(file), enCodingType));
							
							String s;
							long time = -1;
							String text = null;
							boolean smistart = false;

							while ((s = in.readLine()) != null) {
								if(s.contains("<SYNC")) {
									smistart = true;
									if(time != -1) {
										parsedSmi.add(new SMIData(time, text));
									}
									time = Integer.parseInt(s.substring(s.indexOf("=")+1, s.indexOf(">")));
									text = s.substring(s.indexOf(">")+1, s.length());
									text = text.substring(text.indexOf(">")+1, text.length());
								} else {
									if(smistart == true) {
										text += s;
									}
								}
							}
							in.close();
						} catch (IOException e) {
							useSmi = false;
							System.err.println(e);
							System.exit(1);
						}
					}else {
						useSmi = false;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		else if (smiPath != null && videoPath != null)
		{
			File smiFile = new File(smiPath);
			
			if(smiFile.isFile() && smiFile.canRead()) {
				useSmi = true;
				parsedSmi = new ArrayList<SMIData>();
				try {
					String enCodingType = encodingCheck(smiFile.toString());
					//
					BufferedReader in;
					in = new BufferedReader(new InputStreamReader(
							//new FileInputStream(new File(smiFile.toString())), playPrefInfo.getSubEncoding()));
							new FileInputStream(new File(smiFile.toString())), enCodingType));
					//		
					
					String s;
					long time = -1;
					String text = null;
					byte[] euckr = text.getBytes("EUC-KR");
					
					boolean smistart = false;
					
					while ((s = in.readLine()) != null) {
						if(s.contains("<SYNC")) {
							smistart = true;
							if(time != -1) {
								parsedSmi.add(new SMIData(time, text));
							}
							time = Integer.parseInt(s.substring(s.indexOf("=")+1, s.indexOf(">")));
							
							text = s.substring(s.indexOf(">")+1, s.length());
							text = text.substring(text.indexOf(">")+1, text.length());
						} else {
							if(smistart == true) {
								text += s;
							}
						}
					}
					in.close();
				} catch (IOException e) {
					useSmi = false;
					System.err.println(e);
					System.exit(1);
				}
			} else {
				useSmi = false;
			}

		}
	}
	
	public static void convertEncoding(SmbFile file, String encoding) throws Exception
    {
        try 
        {
            //BufferedReader in  = new BufferedReader(new FileReader(file));
            BufferedReader in = new BufferedReader(new InputStreamReader(new SmbFileInputStream(file)));
            String read = null;
            List list = new ArrayList();
            while ((read = in.readLine()) != null)
            {
                list.add(read);
            }
            in.close();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new SmbFileOutputStream(file), encoding));
            for(int i=0,j=list.size(); i<j; i++)
            {
                out.write((String)list.get(i));
                out.newLine();
            }
           
            out.close();
        } catch (IOException e) 
        {
            e.printStackTrace();
            throw e;
        }
    }
	
	public String encodingCheck(String path)
	{
		FileInputStream fis;
		byte[] BOM = new byte[4];
		try {
			fis = new FileInputStream(new File(path));
			fis.read(BOM, 0, 4);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if( (BOM[0] & 0xFF) == 0xEF && (BOM[1] & 0xFF) == 0xBB && (BOM[2] & 0xFF) == 0xBF )
		{
			System.out.println("UTF-8");
			return "UTF-8";
		}
		else if( (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF )
		{
			System.out.println("UTF-16BE");
			return "UTF-16BE";
		}
		else if( (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFE )
		{
			System.out.println("UTF-16LE");
			return "UTF-16LE";
		}
		else if( (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00 &&
				(BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF)
		{
			System.out.println("UTF-32BE");
			return "UTF-32BE";
		}
		else if( (BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE &&
				(BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00)
		{
			System.out.println("UTF-32LE");
			return "UTF-32LE";
		}
		else
		{
			System.out.println("EUC-KR");
			return "EUC-KR";
		}
	}

	public void startSubtile()
	{
		if(useSmi == true) {
			subTitleThread  = new SubTitleThread();
			subTitleThread.start();
		}
	}

	public int getSyncIndex(long playTime) {
		int l=0,m,h=parsedSmi.size();

		while(l <= h) {
			m = (l+h)/2;
			if(parsedSmi.get(m).gettime() <= playTime && playTime < parsedSmi.get(m+1).gettime()) {
				return m;
			}
			if(playTime > parsedSmi.get(m+1).gettime()) {
				l=m+1;
			} else {
				h=m-1;
			}
		}
		return 0;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Log.d("handleMessage", "handleMessage");
		countSmi = getSyncIndex(getCurrentPosition());
		subtitleText.setText(Html.fromHtml((parsedSmi.get(countSmi).gettext())).toString());

		return true;
	}

	class SubTitleThread extends Thread {

		private boolean isRunning = true;

		public void run() {
			try {
				while(isRunning) {
					Thread.sleep(300);
					subtitleHandler.sendMessage(subtitleHandler.obtainMessage());
				}
			} catch (Throwable t) {
				// Exit Thread
			}

		} 

		public void setRunningState(boolean state) {
			isRunning = state;
		} 
	} 
}
