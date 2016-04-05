/*
 * Copyright (C) 2012 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bongplayer2;

import io.vov.vitamio.Vitamio;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

public class InitActivity extends Activity {
	public static final String FROM_ME = "fromVitamioInitActivity";
	public static final String EXTRA_MSG = "EXTRA_MSG";
	public static final String EXTRA_FILE = "EXTRA_FILE";
	private ProgressDialog mPD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		new AsyncTask<Object, Object, Object>() {
			@Override
			protected void onPreExecute() {
				mPD = new ProgressDialog(InitActivity.this);
				mPD.setCancelable(false);
				mPD.setMessage("Initializing decoders...");
				mPD.show();
			}

			@Override
			protected Object doInBackground(Object... params) {

				Vitamio.initialize(getApplicationContext());
				uiHandler.sendEmptyMessage(0);
				return null;
			}
		}.execute();
	}

	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mPD.dismiss();
			Intent src = getIntent();
			Intent i = new Intent();
			i.setClassName(src.getStringExtra("package"),
					src.getStringExtra("className"));
			i.setData(src.getData());
			i.putExtras(src);
			i.putExtra(FROM_ME, true);
			startActivity(i);

			finish();
		}
	};
}
