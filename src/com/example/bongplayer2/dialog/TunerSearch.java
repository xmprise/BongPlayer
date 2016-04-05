package com.example.bongplayer2.dialog;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bongplayer2.interfaces.rt;

public class TunerSearch
{
  public boolean a;
  private TunerDialog b;
  private rt c;
  private CheckBox d;
  private SeekBar e;
  private TextView f;
  private boolean g;
  private CheckBox h;
  private SeekBar i;
  private TextView j;
  private CheckBox k;
  private CheckBox l;

  public TunerSearch(TunerDialog paramrs, ViewGroup paramViewGroup, rt paramrt)
  {
//    this.b = paramrs;
//    this.c = paramrt;
//    this.h = ((CheckBox)paramViewGroup.findViewById(ra.showMoveButtons));
//    this.k = ((CheckBox)paramViewGroup.findViewById(ra.show_prev_next));
//    this.i = ((SeekBar)paramViewGroup.findViewById(ra.moveInterval));
//    this.j = ((TextView)paramViewGroup.findViewById(ra.moveIntervalText));
//    Context localContext = paramViewGroup.getContext();
//    int m = L.a.getInt("navi_move_interval", 10);
//    this.j.setMinimumWidth(2 * mc.a(this.j).width());
//    this.j.setText(Integer.toString(m));
//    this.i.setMax(59);
//    this.i.setProgress(m - 1);
//    this.i.setOnSeekBarChangeListener(new sf(this));
//    this.h.setChecked(qe.i());
//    this.h.setOnCheckedChangeListener(new sg(this));
//    this.k.setChecked(L.a.getBoolean("show_prev_next", true));
//    this.k.setOnCheckedChangeListener(new sh(this));
//    this.d = ((CheckBox)paramViewGroup.findViewById(ra.use_gesture_seek));
//    TextView localTextView = (TextView)paramViewGroup.findViewById(ra.gesture_seek_speed_label);
//    this.d.setChecked(L.a.getBoolean("drag_screen", true));
//    this.d.setOnCheckedChangeListener(new si(this));
//    this.e = ((SeekBar)paramViewGroup.findViewById(ra.gesture_seek_speed));
//    float f1 = L.a.getFloat("drag_seek_speed", 10.0F);
//    String str = Locale.getDefault().getCountry();
//    boolean bool;
//    if ((!"GB".equals(str)) && (!"US".equals(str)) && (!"CA".equals(str)))
//    {
//      bool = false;
//      this.g = bool;
//      if (!this.g)
//        break label494;
//      localTextView.setText(localContext.getString(rf.gesture_seek_speed) + "\n(" + localContext.getString(rf.second_abbr) + "/inch)");
//    }
//    while (true)
//    {
//      this.f = ((TextView)paramViewGroup.findViewById(ra.gesture_seek_speed_text));
//      this.f.setMinimumWidth(3 * mc.a(this.f).width());
//      int n = a(f1);
//      this.e.setMax(-1 + a());
//      this.e.setProgress(n);
//      a(n);
//      this.e.setOnSeekBarChangeListener(new sj(this));
//      return;
//      bool = true;
//      break;
//      label494: localTextView.setText(localContext.getString(rf.gesture_seek_speed) + "\n(" + localContext.getString(rf.second_abbr) + "/cm)");
//    }
  }

  private float a(int paramInt, boolean paramBoolean)
  {
//    float f1;
//    if (this.g)
//      if (paramInt < 6)
//      {
//        f1 = 5 + paramInt * 5;
//        if (paramBoolean)
//          f1 /= 2.54F;
//      }
//    while (true)
//    {
//      return f1;
//      if (paramInt < 13)
//      {
//        f1 = 40 + 10 * (paramInt - 6);
//        break;
//      }
//      if (paramInt < 17)
//      {
//        f1 = 125 + 25 * (-7 + (paramInt - 6));
//        break;
//      }
//      if (paramInt < 21)
//      {
//        f1 = 250 + 50 * (-4 + (-7 + (paramInt - 6)));
//        break;
//      }
//      f1 = 500 + 100 * (-4 + (-4 + (-7 + (paramInt - 6))));
//      break;
//      if (paramInt < 5)
//      {
//        f1 = 2 + paramInt * 2;
//        continue;
//      }
//      if (paramInt < 11)
//      {
//        f1 = 15 + 5 * (paramInt - 5);
//        continue;
//      }
//      if (paramInt < 17)
//      {
//        f1 = 50 + 10 * (-6 + (paramInt - 5));
//        continue;
//      }
//      if (paramInt < 22)
//      {
//        f1 = 120 + 20 * (-6 + (-6 + (paramInt - 5)));
//        continue;
//      }
//      f1 = 250 + 50 * (-5 + (-6 + (-6 + (paramInt - 5))));
//    }
	  return 1.0F;
  }

  private int a()
  {
    return 26;
  }

  private int a(float paramFloat)
  {
//    float f2;
//    float f1;
//    if (this.g)
//    {
//      f2 = 2.54F * paramFloat;
//      if (f2 <= 30.0F)
//        f1 = (f2 - 5.0F) / 5.0F;
//    }
//    while (true)
//    {
//      return Math.round(f1);
//      if (f2 <= 100.0F)
//      {
//        f1 = 6.0F + (f2 - 40.0F) / 10.0F;
//        continue;
//      }
//      if (f2 <= 200.0F)
//      {
//        f1 = 13.0F + (f2 - 125.0F) / 25.0F;
//        continue;
//      }
//      if (f2 <= 400.0F)
//      {
//        f1 = 17.0F + (f2 - 250.0F) / 50.0F;
//        continue;
//      }
//      f1 = 21.0F + (f2 - 500.0F) / 100.0F;
//      continue;
//      if (paramFloat <= 10.0F)
//      {
//        f1 = (paramFloat - 2.0F) / 2.0F;
//        continue;
//      }
//      if (paramFloat <= 40.0F)
//      {
//        f1 = 5.0F + (paramFloat - 15.0F) / 5.0F;
//        continue;
//      }
//      if (paramFloat <= 100.0F)
//      {
//        f1 = 11.0F + (paramFloat - 50.0F) / 10.0F;
//        continue;
//      }
//      if (paramFloat <= 200.0F)
//      {
//        f1 = 17.0F + (paramFloat - 120.0F) / 20.0F;
//        continue;
//      }
//      f1 = 22.0F + (paramFloat - 250.0F) / 50.0F;
//    }
	  return 1;
  }

  private void a(int paramInt)
  {
    this.f.setText(Integer.toString(Math.round(a(paramInt, false))));
  }

  public void a(SharedPreferences.Editor paramEditor)
  {
    paramEditor.putBoolean("navi_show_move_buttons", this.h.isChecked());
    paramEditor.putInt("navi_move_interval", 1 + this.i.getProgress());
    paramEditor.putBoolean("drag_screen", this.d.isChecked());
    paramEditor.putFloat("drag_seek_speed", a(this.e.getProgress(), true));
    paramEditor.putBoolean("show_prev_next", this.k.isChecked());
  }

  public void a(CheckBox paramCheckBox)
  {
    this.l = paramCheckBox;
  }
}