package com.example.bongplayer2.dialog;

import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bongplayer2.interfaces.rt;
import com.example.bongplayer2.interfaces.uu;
import com.example.bongplayer2.widget.Spinner2;
import com.yixia.zi.widget.colorpicker.ColorPanelView;

public class TunerPlace
{
  public boolean a;
  private TunerDialog b;
  private rt c;
  private uu d;
  private CheckBox e;
  private ColorPanelView f;
  private Spinner2 g;
  private SeekBar h;
  private TextView i;
  private CheckBox j;

  public TunerPlace(TunerDialog paramrs, ViewGroup paramViewGroup, rt paramrt, uu paramuu)
  {
    this.b = paramrs;
    this.c = paramrt;
    this.d = paramuu;
//    this.g = ((Spinner2)paramViewGroup.findViewById(ra.subtitleAlignment));
//    this.h = ((SeekBar)paramViewGroup.findViewById(ra.subtitleBottomPadding));
//    this.i = ((TextView)paramViewGroup.findViewById(ra.subtitleBottomPaddingText));
//    this.e = ((CheckBox)paramViewGroup.findViewById(ra.subtitleBackground));
//    this.f = ((ColorPanelView)paramViewGroup.findViewById(ra.subtitleBackgroundColor));
//    this.j = ((CheckBox)paramViewGroup.findViewById(ra.fit_subtitle_overlay_to_video));
//    this.g.setClient(paramuu);
//    this.g.setSelection(b(qe.h));
//    this.g.setOnItemSelectedListener(new tc(this));
//    this.i.setMinimumWidth(2 * mc.a(this.i).width());
//    this.i.setText(Integer.toString(qe.r));
//    this.h.setMax(99);
//    this.h.setProgress(qe.r);
//    this.h.setOnSeekBarChangeListener(new td(this));
//    this.e.setChecked(qe.i);
//    this.e.setOnCheckedChangeListener(new te(this));
//    this.f.setColor(qe.j);
//    this.f.setOnClickListener(new tf(this));
//    this.j.setChecked(L.a.getBoolean("subtitle_fit_overlay_to_video", true));
//    this.j.setOnCheckedChangeListener(new th(this));
  }

  private int a(int paramInt)
  {
//    int k;
//    switch (paramInt)
//    {
//    case 1:
//    default:
//      k = 1;
//    case 0:
//    case 2:
//    }
//    while (true)
//    {
//      return k;
//      k = 3;
//      continue;
//      k = 5;
//    }
	  return 1;
  }

  private int b(int paramInt)
  {
//    int k;
//    switch (paramInt)
//    {
//    case 4:
//    default:
//      k = 1;
//    case 3:
//    case 5:
//    }
//    while (true)
//    {
//      return k;
//      k = 0;
//      continue;
//      k = 2;
//    }
	  return 2;
  }

  public void a(SharedPreferences.Editor paramEditor)
  {
//    qe.h = a(this.g.getSelectedItemPosition());
//    qe.r = this.h.getProgress();
//    qe.i = this.e.isChecked();
//    qe.j = this.f.getColor();
//    paramEditor.putInt("subtitle_alignment", qe.h);
//    paramEditor.putInt("subtitle_bottom_padding", qe.r);
//    paramEditor.putBoolean("subtitle_bkcolor_enabled", qe.i);
//    paramEditor.putInt("subtitle_bkcolor", qe.j);
//    paramEditor.putBoolean("subtitle_fit_overlay_to_video", this.j.isChecked());
  }
}
