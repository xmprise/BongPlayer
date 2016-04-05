/*
 * FFmpegSurfaceView.java
 * Copyright (c) 2012 Jacek Marchwicki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.appunite.ffmpeg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.appunite.ffmpeg.FFmpegPlayer.RenderedFrame;
import com.example.bongplayer2.HWDecodingVideoView.SurfaceCallback;
import com.yixia.zi.utils.DeviceUtils;
//import com.example.bongplayer2.HWDecodingVideoView.SurfaceCallback;
//import com.yixia.zi.utils.DeviceUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.ViewGroup;

public class FFmpegGLSurfaceView extends GLSurfaceView implements FFmpegDisplay {

	private FFmpegPlayer mMpegPlayer = null;
	private Object mMpegPlayerLock = new Object();
	private TutorialThread mThread = null;
	private Paint mPaint;
	private FFmpegGLSurfaceView mFFmpegGLSurfaceView;
	private Object mFpsCounterLock = new Object();
	private FpsCounter mFpsCounter = null;
	int rendersuccess = 0;
	private Renderer mRenderer;
	private Activity videoActivity;
	private SurfaceHolder surfaceHolder;
	private SurfaceCallback surfaceCallback; 
	public int mVideoHeight;
	private int xPoint;
	private int yPoint;
	private int videoMode = 1; 
	public static final int VIDEO_LAYOUT_ORIGIN = 0;
	public static final int VIDEO_LAYOUT_SCALE = 1;
	public static final int VIDEO_LAYOUT_SCALE_ZOOM = 4;
	public static final int VIDEO_LAYOUT_STRETCH = 2;
	public static final int VIDEO_LAYOUT_ZOOM = 3;
	
	public FFmpegGLSurfaceView(Context context) {
		super(context);
		// Log.e("FFmpegGLSurfaceView", "1");
		setEGLContextClientVersion(2);

		mRenderer = new Renderer();
		setRenderer(mRenderer);
		mFpsCounter = new FpsCounter(10);
		this.setRenderMode(RENDERMODE_WHEN_DIRTY);
		mFFmpegGLSurfaceView = this;
	}

	public FFmpegGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Log.e("FFmpegGLSurfaceView", "2");
		setEGLContextClientVersion(2);
		mRenderer = new Renderer();
		setRenderer(mRenderer);
		mFpsCounter = new FpsCounter(10);
		this.setRenderMode(RENDERMODE_WHEN_DIRTY);
		mFFmpegGLSurfaceView = this;
	}

	public FFmpegGLSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		// Log.e("FFmpegGLSurfaceView", "3");
		setEGLContextClientVersion(2);
		mRenderer = new Renderer();
		setRenderer(mRenderer);
		mFpsCounter = new FpsCounter(10);
		this.setRenderMode(RENDERMODE_WHEN_DIRTY);
		mFFmpegGLSurfaceView = this;
	}

	@SuppressWarnings("deprecation")
	public void initialize(Activity paramActivity, SurfaceCallback paramSurfaceCallback, boolean paramBoolean)
	{
		//Log.e(LEVEL2, "initialize");
		videoActivity = paramActivity;

		surfaceCallback = paramSurfaceCallback;

		if (surfaceHolder == null)
			surfaceHolder = getHolder();

		if (paramBoolean)
			getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		else
			getHolder().setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
	}
	
	public void setVideoLayout(int videoMode, float prefScreenRatio, int videoWidth, int videoHeight, float videoAspectRatio)
	{
		//Log.e(LEVEL2, "setVideoLayout");
		this.videoMode = videoMode;

		ViewGroup.LayoutParams layoutParams1 = getLayoutParams();

			
		int deviceScreenWidth = DeviceUtils.getScreenWidth(videoActivity);
		int deviceScreenHeight = DeviceUtils.getScreenHeight(videoActivity);

		float deviceRatio = deviceScreenWidth / deviceScreenHeight;

		
		if (prefScreenRatio <= 0.01F)
			prefScreenRatio = videoAspectRatio;

		yPoint = videoHeight;
		xPoint = videoWidth;
		int n = 0;

		
		if ((this.videoMode == VIDEO_LAYOUT_ORIGIN) && (xPoint < deviceScreenWidth) && (yPoint < deviceScreenHeight))
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_ORIGIN");
			layoutParams1.width = ((int)(prefScreenRatio * yPoint));
			layoutParams1.height = ((int)(xPoint / prefScreenRatio));
		}
		else if(this.videoMode == VIDEO_LAYOUT_ZOOM)
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_ZOOM");
			if (deviceRatio > prefScreenRatio) //
			{
				layoutParams1.width = deviceScreenWidth;
				layoutParams1.height = deviceScreenHeight;
			}
			else
			{
				layoutParams1.width = (int)prefScreenRatio * deviceScreenHeight;
				layoutParams1.height = (int)(deviceScreenWidth / prefScreenRatio);
			}
		}
		else if(this.videoMode == VIDEO_LAYOUT_STRETCH)
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_STRETCH");
			layoutParams1.width = deviceScreenWidth;
			layoutParams1.height = deviceScreenHeight;
		}
		else if ((this.videoMode == VIDEO_LAYOUT_SCALE_ZOOM) && (this.mVideoHeight > 0))
		{
			Log.d("setVideoLayout", "VIDEO_LAYOUT_SCALE_ZOOM");
			Log.d("setVideoLayout", "this mVideoHeight" + this.mVideoHeight);

			layoutParams1.width = ((int)(prefScreenRatio * this.mVideoHeight));
			layoutParams1.height = this.mVideoHeight;

			Log.d("setVideoLayout", "this layoutParams1.width>>> " + layoutParams1.width);
			Log.d("setVideoLayout", "this layoutParams1.height>>> " + layoutParams1.height);
		}

		this.mVideoHeight = layoutParams1.height;

		setLayoutParams(layoutParams1);
		getHolder().setFixedSize(xPoint, yPoint);

		//		Object[] arrayOfObject = new Object[10];
		//		arrayOfObject[0] = Integer.valueOf(videoWidth);
		//		arrayOfObject[1] = Integer.valueOf(videoHeight);
		//		arrayOfObject[2] = Float.valueOf(videoAspectRatio);
		//		arrayOfObject[3] = Integer.valueOf(xPoint);
		//		arrayOfObject[4] = Integer.valueOf(yPoint);
		//		arrayOfObject[5] = Integer.valueOf(layoutParams1.width);
		//		arrayOfObject[6] = Integer.valueOf(layoutParams1.height);
		//		arrayOfObject[7] = Integer.valueOf(screenWidth);
		//		arrayOfObject[8] = Integer.valueOf(screenHeight);
		//		arrayOfObject[9] = Float.valueOf(f1);		
		//		Log.d("VIDEO: %dx%dx%f, Surface: %dx%d, LP: %dx%d, Window: %dx%dx%f", arrayOfObject);
	}
	
	@Override
	public void setMpegPlayer(FFmpegPlayer fFmpegPlayer) {
		// Log.e("ffmpegGlsurfaceview", "setMpegPlayer");
		if (mMpegPlayer != null)
			throw new RuntimeException(
					"setMpegPlayer could not be called twice");

		synchronized (mMpegPlayerLock) {
			this.mMpegPlayer = fFmpegPlayer;
			mMpegPlayerLock.notifyAll();
		}
	}

	public void showFpsCounter(boolean showFpsCounter) {
		synchronized (mFpsCounterLock) {
			if (showFpsCounter) {
				if (mFpsCounter == null) {
					mFpsCounter = new FpsCounter(10);
				}
			} else {
				mFpsCounter = null;
			}
		}
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(mMpegPlayer!=null)
		{
			mMpegPlayer.audioseekNative(1000);
			Log.e("onTouchEvent", "audioseekNative call");
		}
		return true;
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Log.e("glsurfaveview", "surfaceDestroyed");
		if (mRenderer != null) {
			mRenderer.surfaceDestroyed();
		}
	}

	public class Renderer implements GLSurfaceView.Renderer,
			SurfaceTexture.OnFrameAvailableListener {

		private int frame_width, frame_height, frameresult,
				texture_maxsize = 2048;
		int i = 0;
		String fps;

		public Renderer() {
			frame_width = 0;
			frame_height = 0;
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// Log.e("ffmpegSurfaceView", "surfacecreated");

		}

		public void surfaceDestroyed() {
			// Log.e("ffmpegSurfaceView", "surfaceDestroyed");
			if (mThread != null) {
				mThread.setRunning(false);
				if(mMpegPlayer != null)
					mMpegPlayer.renderFrameStop();
				mThread.interrupt();
				// GLES20.glDeleteTextures(3, gFrameTexture, 0);
			}
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			surfaceDestroyed();
			initGL();
			// Log.e("ffmpegSurfaceView", "surfaceChanged");
			if(mMpegPlayer != null)
				mMpegPlayer.renderFrameStart();
			mThread = new TutorialThread(this);
			mThread.setRunning(true);
			// mThread.setSurfaceParams(width, height);
			mThread.start();

		}

		public void onDrawFrame(GL10 gl) {
			// Log.e("frame", "end");
			if (frame_height == 0) {
				if(mMpegPlayer != null){
					this.frame_width = mMpegPlayer.getframewidth();
					this.frame_height = mMpegPlayer.getframeheight();
				}
				
				if (frame_height != 0) {
					mVerticesData[4] = (float) frame_height / texture_maxsize;
					mVerticesData[13] = (float) frame_width / texture_maxsize;
					mVerticesData[14] = (float) frame_height / texture_maxsize;
					mVerticesData[18] = (float) frame_width / texture_maxsize;
				}
				mVertices = ByteBuffer
						.allocateDirect(mVerticesData.length * FLOAT_SIZE_BYTES)
						.order(ByteOrder.nativeOrder()).asFloatBuffer();
				mVertices.put(mVerticesData).position(0);

			}
			drawframe();
		}

		private int loadShader(int shaderType, String source) {
			int shader = GLES20.glCreateShader(shaderType);
			if (shader != 0) {
				GLES20.glShaderSource(shader, source);
				GLES20.glCompileShader(shader);
				int[] compiled = new int[1];
				GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS,
						compiled, 0);
				if (compiled[0] == 0) {
					Log.e(TAG, "Could not compile shader " + shaderType + ":");
					Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
					GLES20.glDeleteShader(shader);
					shader = 0;
				}
			}
			return shader;
		}

		private int createProgram(String vertexSource, String fragmentSource) {
			int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
			if (vertexShader == 0) {
				return 0;
			}
			int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
					fragmentSource);
			if (pixelShader == 0) {
				return 0;
			}

			int program = GLES20.glCreateProgram();
			if (program != 0) {
				GLES20.glAttachShader(program, vertexShader);
				checkGlError("glAttachShader");
				GLES20.glAttachShader(program, pixelShader);
				checkGlError("glAttachShader");
				GLES20.glLinkProgram(program);
				int[] linkStatus = new int[1];
				GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS,
						linkStatus, 0);
				if (linkStatus[0] != GLES20.GL_TRUE) {
					Log.e(TAG, "Could not link program: ");
					Log.e(TAG, GLES20.glGetProgramInfoLog(program));
					GLES20.glDeleteProgram(program);
					program = 0;
				}
			}
			return program;
		}

		private void checkGlError(String op) {
			int error;
			while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
				Log.e(TAG, op + ": glError " + error);
				throw new RuntimeException(op + ": glError " + error);
			}
		}

		public void drawframe() {
			GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			// GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
			// | GLES20.GL_COLOR_BUFFER_BIT);
			GLES20.glUseProgram(mProgram);
			//
			checkGlError("glUseProgram");
			if(mMpegPlayer != null)
				frameresult = mMpegPlayer.getFrame(shaderHandle, gFrameTexture);
			// Log.e("frame", "frameresult " + frameresult);
			if (frameresult != 1) {
//				Log.e("frame", "frameresult = 0");
				mVertices.position(0);
				GLES20.glVertexAttribPointer(maPositionHandle, 3,
						GLES20.GL_FLOAT, false, VERTICES_DATA_STRIDE_BYTES,
						mVertices);
				checkGlError("glVertexAttribPointer maPosition");
				GLES20.glEnableVertexAttribArray(maPositionHandle);
				checkGlError("glEnableVertexAttribArray maPositionHandle");

				mVertices.position(3);
				GLES20.glVertexAttribPointer(maTextureHandle, 3,
						GLES20.GL_FLOAT, false, VERTICES_DATA_STRIDE_BYTES,
						mVertices);
				checkGlError("glVertexAttribPointer maTextureHandle");
				GLES20.glEnableVertexAttribArray(maTextureHandle);
				checkGlError("glEnableVertexAttribArray maTextureHandle");
				GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
				// GLES20.glDeleteTextures(3, gFrameTexture, 0);
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
				checkGlError("glDrawArrays");
			}
			// if(frameresult==1)
			// GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			fps = mFpsCounter.tick();
			// Log.e("fps", "" + fps);
		}

		public void initGL() {
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,
					GLES20.GL_ONE_MINUS_SRC_ALPHA);
			GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

			/* Set up shaders and handles to their variables */
			mProgram = createProgram(mVertexShader, mFragmentShader);
			if (mProgram == 0) {
				return;
			}
			maPositionHandle = GLES20
					.glGetAttribLocation(mProgram, "aPosition");
			checkGlError("glGetAttribLocation aPosition");
			if (maPositionHandle == -1) {
				throw new RuntimeException(
						"Could not get attrib location for aPosition");
			}
			maTextureHandle = GLES20.glGetAttribLocation(mProgram,
					"aTextureCoord");
			checkGlError("glGetAttribLocation aTextureCoord");
			if (maTextureHandle == -1) {
				throw new RuntimeException(
						"Could not get attrib location for aTextureCoord");
			}

			checkGlError("glGetUniformLocation uCRatio");
			if (muMVPMatrixHandle == -1) {
				throw new RuntimeException(
						"Could not get attrib location for uCRatio");
			}
			shaderHandle[0] = GLES20.glGetUniformLocation(mProgram, "sampler0");
			checkGlError("glGetUniformLocation sampler0");
			if (shaderHandle[0] == -1) {
				throw new RuntimeException(
						"Could not get attrib location for sampler0");
			}
			shaderHandle[1] = GLES20.glGetUniformLocation(mProgram, "sampler1");
			checkGlError("glGetUniformLocation sampler1");
			if (shaderHandle[0] == -1) {
				throw new RuntimeException(
						"Could not get attrib location for sampler1");
			}
			shaderHandle[2] = GLES20.glGetUniformLocation(mProgram, "sampler2");
			checkGlError("glGetUniformLocation sampler2");
			if (shaderHandle[2] == -1) {
				throw new RuntimeException(
						"Could not get attrib location for sampler2");
			}
			GLES20.glUseProgram(mProgram);
			GLES20.glGenTextures(3, gFrameTexture, 0);
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, gFrameTexture[0]);
			GLES20.glUniform1i(shaderHandle[0], gFrameTexture[0]);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE,
					2 << 10, 2 << 10, 0, GLES20.GL_LUMINANCE,
					GLES20.GL_UNSIGNED_BYTE, null);

			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, gFrameTexture[1]);
			GLES20.glUniform1i(shaderHandle[1], gFrameTexture[1]);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE,
					2 << 9, 2 << 9, 0, GLES20.GL_LUMINANCE,
					GLES20.GL_UNSIGNED_BYTE, null);

			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, gFrameTexture[2]);
			GLES20.glUniform1i(shaderHandle[2], gFrameTexture[2]);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE,
					2 << 9, 2 << 9, 0, GLES20.GL_LUMINANCE,
					GLES20.GL_UNSIGNED_BYTE, null);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		}

		private static final int FLOAT_SIZE_BYTES = 4;
		private static final int VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;
		private static final int VERTICES_DATA_POS_OFFSET = 0;
		private static final int VERTICES_DATA_UV_OFFSET = 3;
		private final float[] mVerticesData = {
				// X, Y, Z, U, V
				-1.0f, -1.0f, 0.f, 0.f, (float) 1080 / 2048f, -1.0f, 1.0f, 0.f,
				0.f, 0.f, 1.0f, -1.0f, 0.f, (float) 1920 / 2048,
				(float) 1080 / 2048, 1.0f, 1.0f, 0.f, (float) 1920 / 2048, 0.f, };
		// -1.0f, -1.0f, 0,
		// 1.0f, -1.0f, 0,
		// -1.0f, 1.0f, 0,
		// 1.0f, 1.0f, 0, };
		// -1.0f, -1.0f, 0, 0.f, 0.f,
		// 1.0f, -1.0f, 0, 1.f, 0.f,
		// -1.0f, 1.0f, 0, 0.f, 1.f,
		// 1.0f, 1.0f, 0, 1.f, 1.f, };

		private FloatBuffer mVertices;

		private final String mVertexShader = "attribute vec4 aPosition;\n"
				+ "attribute vec4 aTextureCoord;\n"
				+ "varying highp vec2 _texcoord;\n" + "void main() {\n"
				+ "  gl_Position = aPosition;\n"
				+ "  _texcoord = aTextureCoord.xy;\n" + "}\n";

		private final String mFragmentShader = "uniform sampler2D sampler0; \n"
				+ "uniform sampler2D sampler1; \n"
				+ "uniform sampler2D sampler2; \n"
				+ "varying highp vec2 _texcoord; \n" + "void main() \n" + "{\n"
				+ "highp float y = texture2D(sampler0, _texcoord).r; \n"
				+ "highp float u = texture2D(sampler1, _texcoord).r; \n"
				+ "highp float v = texture2D(sampler2, _texcoord).r; \n"
				+ "y = 1.1643 * (y - 0.0625); \n" + "u = u - 0.5; \n"
				+ "v = v - 0.5; \n" + "highp float r = y + 1.5958 * v; \n"
				+ "highp float g = y - 0.39173 * u - 0.81290 * v; \n"
				+ "highp float b = y + 2.017 * u; \n"
				+ "gl_FragColor = vec4(r, g, b, 1.0); \n" + "}\n";

		private int mProgram;
		private int mTextureID;
		private int[] gFrameTexture = { 1, 2, 3 };// , 0};
		private int muMVPMatrixHandle;
		private int muSTMatrixHandle;
		private int maPositionHandle;
		private int[] shaderHandle = new int[3];
		private int maTextureHandle;

		private float mRatio = 1.0f;
		private SurfaceTexture mSurface;
		private boolean updateSurface = false;

		private static final String TAG = "MyRenderer";

		// Magic key
		private static final int GL_TEXTURE_EXTERNAL_OES = 0x8D65;

		public SurfaceTexture getSurfaceTexture() {
			return mSurface;
		}

		public void onFrameAvailable(SurfaceTexture surfaceTexture) {
			// TODO Auto-generated method stub

		}
	}

	class TutorialThread extends Thread {// AsyncTask<Void, Void, Void> {
		private Renderer mRenderer;
		private boolean mRun = false;
		FFmpegGLSurfaceView mParent;

		public TutorialThread(Renderer renderer) {
			mRenderer = renderer;
			mParent = mFFmpegGLSurfaceView;
			// this.setPriority(MAX_PRIORITY);
		}

		public void setSurfaceParams(int width, int height) {

		}

		public synchronized void setRunning(boolean run) {
			mRun = run;
		}

		public synchronized boolean isRunning() {
			return mRun;
		}

		@Override
		public void run() {

			while (isRunning()) {
				try {
					synchronized (mMpegPlayerLock) {
						while (mMpegPlayerLock == null)
							mMpegPlayerLock.wait();
						rendersuccess = 0;
						// Log.e("run", "renderFrameNative call");
						if(mMpegPlayer != null)
							rendersuccess = mMpegPlayer.renderFrameNative();
						// Log.e("run", "renderFrameNative end");
						if (rendersuccess == 1) {
							try {
								mParent.requestRender();
							} finally {
								mMpegPlayer.releaseFrame();
							}
						}
						else if (rendersuccess == -1)
						{
							mMpegPlayer.releaseFrame();
						}
					}
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// @Override
		// protected Void doInBackground(Void... params) {
		// // TODO Auto-generated method stub
		//
		// while (isRunning()) {
		// try {
		// mRenderer.initGL();
		// synchronized (mMpegPlayerLock) {
		// while (mMpegPlayerLock == null)
		// Log.e("asynctask", "while");
		// mMpegPlayerLock.wait();
		// try {
		// mParent.requestRender();
		// } finally {
		// mMpegPlayer.releaseFrame();
		// }
		// }
		// } catch (InterruptedException e) {
		// }
		// }
		// return null;
		// }

		// private void renderFrame(FFmpegPlayer mpegPlayer) throws
		// InterruptedException {
		// RenderedFrame renderFrame = mpegPlayer.renderFrame();
		// if (renderFrame == null)
		// throw new RuntimeException();
		// if (renderFrame.bitmap == null)
		// throw new RuntimeException();
		// try {
		// drawFrame(renderFrame);
		// } finally {
		// mMpegPlayer.releaseFrame();
		// }
		// }

		//
		// private void drawFpsCounter(Canvas canvas, float moveX, float moveY)
		// {
		// synchronized (mFpsCounterLock) {
		// if (mFpsCounter != null) {
		// String fps = mFpsCounter.tick();
		// canvas.drawText(fps, 40 - moveX, 40 - moveY, mPaint);
		// }
		// }
		// }
	}
	//
	// @Override
	// public void surfaceChanged(SurfaceHolder holder, int format, int width,
	// int height) {
	// surfaceDestroyed(holder);
	// this.mMpegPlayer.renderFrameStart();
	// mThread = new TutorialThread(getHolder());
	// mThread.setRunning(true);
	// mThread.setSurfaceParams(width, height);
	// mThread.start();
	//
	// }
	//
	// @Override
	// public void surfaceCreated(SurfaceHolder holder) {
	//
	// }
	//
	// @Override
	// public void surfaceDestroyed(SurfaceHolder holder) {
	// if (mThread != null) {
	// mThread.setRunning(false);
	// this.mMpegPlayer.renderFrameStop();
	// mThread.interrupt();
	// }
	// }

}
