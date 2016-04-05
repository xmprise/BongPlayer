package com.example.bongplayer2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.bongplayer2.helper.hm;
import com.example.bongplayer2.interfaces.tv;

public final class TunerTabLayout extends LinearLayout
implements View.OnClickListener
{
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private TextView selectedTab;
	private tv g;
	private Context context;
	public TunerTabLayout(Context paramContext)
	{
		super(paramContext);
		context = paramContext;
	}

	public TunerTabLayout(Context paramContext, AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
		context = paramContext;
	}

	public void selectedTab(int tabIndex)
	{
		selectedTab = null;

		TextView[] textViewArray = getTabs();
		
		if (0 >= textViewArray.length)
		{
			if (this.g != null)
				this.g.a(selectedTab, tabIndex);
		}
		
		for(int i=0; i<textViewArray.length ; i++)
		{
			TextView textView = textViewArray[i];
			
			if (i == tabIndex)
			{
				textView.setBackgroundResource(R.drawable.tuner_selected_tab);
				textView.setTextColor(this.d);
				textView.setSelected(isFocused());
				selectedTab = textView;
			}
			else
			{
				textView.setBackgroundResource(R.drawable.tuner_deselected_tab);
				textView.setTextColor(this.e);
				textView.setSelected(false);
			}
			
			textView.setPadding(this.b, this.a, this.c, this.a);
			if (this.g != null)
				this.g.a(selectedTab, tabIndex);
		}
	}

	public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
	{
		boolean bool = true;
		
		switch (paramKeyEvent.getKeyCode())
		{
		default:
			bool = super.dispatchKeyEvent(paramKeyEvent);
		case 19:
			if (paramKeyEvent.getAction() != 0);
			int j = getSelectedIndex();
			if (j <= 0)
				
				selectedTab(j - 1);
		case 20:
			if (paramKeyEvent.getAction() != 0);
	
			int i = getSelectedIndex();
			if (i >= -1 + getTabCount())
				
				selectedTab(i + 1);
		}
		return true;
	}

	protected void finalize()
	{
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TextView getSelected()
	{
		return selectedTab;
	}

	public int getSelectedIndex()
	{
		if (selectedTab == null)
			return -1;
		else
			return ((Integer)selectedTab.getTag()).intValue();			
	}

	public int getTabCount()
	{
		int i = 0;
		int j = getChildCount();

		for (int k = 0; ; k++)
		{
			if (k >= j)
				return i;
			View view = getChildAt(k);

			if ((view.getId() == -1) || (!(view instanceof TextView)))
				continue;
			i++;
		}
	}

	public TextView[] getTabs()
	{
		ArrayList arrayList = new ArrayList();

		int i = getChildCount();

		for(int j=0;j<i;j++)
		{
			View view = getChildAt(j);
			if ((view.getId() == -1) || (!(view instanceof TextView)))
				continue;
			arrayList.add((TextView)view);
		}
		return (TextView[])arrayList.toArray(new TextView[arrayList.size()]);
	}

	public void onClick(View paramView)
	{
		Integer integer = (Integer)paramView.getTag();
		if (integer != null)
			selectedTab(integer.intValue());
	}

	/*
     * xml 로 부터 모든 뷰를 inflate 를 끝내고 실행된다.
     * 
     * 대부분 이 함수에서는 각종 변수 초기화가 이루어 진다.
     * 
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
	protected void onFinishInflate()
	{
		int i = 0;
		super.onFinishInflate();
		
		Resources resources = getContext().getResources();
		
		this.d = resources.getColor(R.color.tuner_selected_text);
		this.e = resources.getColor(R.color.tuner_deselected_text);
		hm.a(context);
		this.a = (int)hm.c(10.0F);
		this.b = (int)hm.c(8.0F);
		this.c = (int)hm.c(8.0F);
		
		TextView[] arrayOfTextView = getTabs();
		
		int j = arrayOfTextView.length;
		int m;
		
		for (int k = 0; ; k = m)
		{
			if (i >= j)
				return;
			TextView localTextView = arrayOfTextView[i];
			localTextView.setOnClickListener(this);
			m = k + 1;
			localTextView.setTag(Integer.valueOf(k));
			i++;
		}
	}

	protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
	{
		if (selectedTab != null)
			selectedTab.setSelected(paramBoolean);
	}

	public void setListener(tv paramtv)
	{
		this.g = paramtv;
	}
}