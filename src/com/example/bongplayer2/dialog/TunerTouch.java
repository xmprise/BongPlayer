package com.example.bongplayer2.dialog;

import java.text.NumberFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bongplayer2.interfaces.rt;
import com.example.bongplayer2.interfaces.ru;
import com.example.bongplayer2.interfaces.uu;
import com.example.bongplayer2.widget.Spinner2;

public class TunerTouch
{
  public boolean a;
  private  TunerDialog b;
  private  rt c;
  private  Spinner2 d;
  private  Spinner2 e;
  private  int[] f;
  private  CheckBox g;
  private  CheckBox h;
  private  CheckBox i;
  private  CheckBox j;
  private  CheckBox k;
  private  SeekBar l;
  private  TextView m;
  private  String n;
  private CheckBox o;

  public TunerTouch(TunerDialog paramrs, ViewGroup paramViewGroup, ru paramru, rt paramrt, uu paramuu)
  {
//    this.b = paramrs;
//    this.c = paramrt;
//    this.d = ((Spinner2)paramViewGroup.findViewById(ra.touch_action));
//    this.d.setClient(paramuu);
//    this.e = ((Spinner2)paramViewGroup.findViewById(ra.lock_mode));
//    this.e.setClient(paramuu);
//    this.g = ((CheckBox)paramViewGroup.findViewById(ra.video_seeking));
//    this.h = ((CheckBox)paramViewGroup.findViewById(ra.zoom));
//    this.i = ((CheckBox)paramViewGroup.findViewById(ra.volume));
//    this.j = ((CheckBox)paramViewGroup.findViewById(ra.brightness));
//    this.k = ((CheckBox)paramViewGroup.findViewById(ra.lock_show_interface));
//    Context localContext = paramViewGroup.getContext();
//    this.f = localContext.getResources().getIntArray(qv.tune_touch_action_option_values);
//    this.d.setSelection(rs.a(qe.q, this.f, 0));
//    this.d.setOnItemSelectedListener(new rw(this));
//    new sd(this, localContext, paramru, paramuu);
//    this.g.setChecked(L.a.getBoolean("drag_screen", true));
//    this.g.setOnCheckedChangeListener(new rx(this));
//    this.h.setChecked(L.a.getBoolean("drag_zoom", true));
//    this.h.setOnCheckedChangeListener(new ry(this));
//    this.i.setChecked(L.a.getBoolean("drag_volume", true));
//    this.i.setOnCheckedChangeListener(new rz(this));
//    this.j.setChecked(L.a.getBoolean("drag_brigtness", true));
//    this.j.setOnCheckedChangeListener(new sa(this));
//    this.k.setChecked(L.a.getBoolean("lock_show_interface", false));
//    this.k.setOnCheckedChangeListener(new sb(this));
//    L.n.setLength(0);
//    String str = localContext.getString(rf.second_abbr);
//    this.n = (' ' + str);
//    this.l = ((SeekBar)paramViewGroup.findViewById(ra.interface_auto_hide_delay));
//    this.m = ((TextView)paramViewGroup.findViewById(ra.interface_auto_hide_delay_text));
//    int i1 = L.a.getInt("interface_auto_hide_delay", 2000);
//    this.m.setMinimumWidth(4 * mc.a(this.m).width() + mc.a(this.m, str).width());
//    this.m.setText(a(i1));
//    this.l.setMax(18);
//    this.l.setProgress((i1 - 1000) / 500);
//    this.l.setOnSeekBarChangeListener(new sc(this));
  }

  private String a(int paramInt)
  {
//    NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
//    localNumberFormat.setMinimumFractionDigits(1);
//    L.n.setLength(0);
//    L.n.append(localNumberFormat.format(paramInt / 1000.0D)).append(this.n);
//    return L.n.toString();
	  return null;
  }

  public void a(SharedPreferences.Editor paramEditor)
  {
//    qe.q = this.f[this.d.getSelectedItemPosition()];
//    paramEditor.putInt("playback_touch_action", qe.q);
//    paramEditor.putInt("lock_mode", this.e.getSelectedItemPosition());
//    paramEditor.putBoolean("drag_screen", this.g.isChecked());
//    paramEditor.putBoolean("drag_zoom", this.h.isChecked());
//    paramEditor.putBoolean("drag_volume", this.i.isChecked());
//    paramEditor.putBoolean("drag_brigtness", this.j.isChecked());
//    paramEditor.putBoolean("lock_show_interface", this.k.isChecked());
//    paramEditor.putInt("interface_auto_hide_delay", 1000 + 500 * this.l.getProgress());
  }

  public void a(CheckBox paramCheckBox)
  {
    this.o = paramCheckBox;
  }
}