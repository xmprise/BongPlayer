package com.example.bongplayer2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Intro extends Activity {
	
	private TextView intro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try
				{
					intro = (TextView)findViewById(R.id.logo);
					Animation alphaAnim = AnimationUtils.loadAnimation(Intro.this, R.anim.alpha);
					
					intro.startAnimation(alphaAnim);
					Thread.sleep(3000);
					isIntro();
					
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro, menu);
		return true;
	}
	
	private void isIntro()
	{
		Intent intent = new Intent(this, BongPlayerExplorer.class);  //이동하고 싶은 액티비티.class 로 수정
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent); 
		finish();
	}

}
