package com.example.bongplayer2.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class DynamicLinearLayout extends LinearLayout {


	private float a = 560.0F;

	public DynamicLinearLayout(Context context)
	{
		super(context);
	}

	public DynamicLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
		try
		{
			Context context = getContext();
			int[] arrayOfInt = new int[1];
			arrayOfInt[0] = 16843039;
			this.a = context.obtainStyledAttributes(paramAttributeSet, arrayOfInt).getDimension(0, 560.0F);
			if (this.a <= 1.0F)
				this.a = 560.0F;
			return;
		}
		catch (Exception localException)
		{
			//Log.e("DynamicLinearLayout", localException);
		}
	}

	protected void onMeasure(int paramInt1, int paramInt2)
	{
		int i = View.MeasureSpec.getMode(paramInt1);
		super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(paramInt1), (int)this.a), i), paramInt2);
	}
}


