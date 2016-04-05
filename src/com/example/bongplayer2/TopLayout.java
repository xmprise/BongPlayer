package com.example.bongplayer2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.bongplayer2.interfaces.IOnTopLayoutSizeChanged;

public class TopLayout extends RelativeLayout
  implements Runnable
{
  private IOnTopLayoutSizeChanged iOnTopLayoutSizeChanged;

  public TopLayout(Context paramContext)
  {
    super(paramContext);
  }

  public TopLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public TopLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (iOnTopLayoutSizeChanged != null)
      post(this);
  }

  public void run()
  {
	  iOnTopLayoutSizeChanged.onTopLayoutSizeChanged(this);
  }

  public void setListener(IOnTopLayoutSizeChanged iOnTopLayoutSizeChanged)
  {
    this.iOnTopLayoutSizeChanged = iOnTopLayoutSizeChanged;
  }
}