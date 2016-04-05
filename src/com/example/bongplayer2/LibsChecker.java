package com.example.bongplayer2;

import android.app.Activity;
import android.content.Intent;
import io.vov.vitamio.Vitamio;

public final class LibsChecker {
	public static final String FROM_ME = "fromVitamioInitActivity";

	public static final boolean checkVitamioLibs(Activity ctx) {
		if ((!Vitamio.isInitialized(ctx))
				&& (!ctx.getIntent().getBooleanExtra("fromVitamioInitActivity",
						false))) {
			Intent i = new Intent();
			i.setClassName(ctx.getPackageName(),
					"com.example.bongplayer2.InitActivity");
			i.putExtras(ctx.getIntent());
			i.setData(ctx.getIntent().getData());
			i.putExtra("package", ctx.getPackageName());
			i.putExtra("className", ctx.getClass().getName());
			ctx.startActivity(i);
			ctx.finish();
			return false;
		}
		return true;
	}
}
