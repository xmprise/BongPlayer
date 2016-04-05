package com.example.bongplayer2.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bongplayer2.R;
import com.example.bongplayer2.interfaces.rt;
import com.example.bongplayer2.interfaces.uu;
import com.example.bongplayer2.widget.Spinner2;

public class TunerScreen
{
  public boolean a;
  private TunerDialog b;
  private rt c;
  private Spinner2 d = null;
  private Spinner2 e = null;
  private CheckBox f;
  private SeekBar g;
  private TextView h;
  private CheckBox i = null;
  private CheckBox j = null;
  private CheckBox k = null;
  private CheckBox l = null;
  private CheckBox m = null;
  private CheckBox n = null;
  private CheckBox o = null;
  private CheckBox p = null;
  private CheckBox q = null;
  private int[] r = null;
  private int[] s = null;
  private Toast t = null;
  private int u = 0;

  public TunerScreen(TunerDialog paramrs, ViewGroup paramViewGroup, rt paramrt, uu paramuu)
  {
    this.b = paramrs;
    this.c = paramrt;
//    this.f = ((CheckBox)paramViewGroup.findViewById(R.id.brightnessEnable));
//    this.g = ((SeekBar)paramViewGroup.findViewById(R.id.brightness));
//    this.h = ((TextView)paramViewGroup.findViewById(R.id.brightnessText));
    
    Context cntext = paramViewGroup.getContext();
    
//    this.r = cntext.getResources().getIntArray(qv.tune_orientation_values);
//    this.s = cntext.getResources().getIntArray(qv.fullscreen_values);
//    this.d = ((Spinner2)paramViewGroup.findViewById(ra.orientation));
//    this.d.setClient(paramuu);
//    this.d.setSelection(TunerDialog.a(qe.p, this.r, 0));
//    this.d.setOnItemSelectedListener(new so(this));
//    
//    boolean bool;
//    label205: CheckBox localCheckBox2;
//    if ((L.g) || ((Build.VERSION.SDK_INT >= 16) && (qe.B)))
//    {
//      this.e = null;
//      this.u = 0;
//      paramViewGroup.findViewById(ra.fullscreen_row).setVisibility(8);
//      CheckBox localCheckBox1 = this.f;
//      if (!qe.k)
//        break label795;
//      bool = false;
//      localCheckBox1.setChecked(bool);
//      this.f.setOnCheckedChangeListener(new su(this));
//      int i1 = BrightnessBar.a(cntext);
//      int i2 = BrightnessBar.a(i1, qe.l);
//      this.h.setMinimumWidth(2 * mc.a(this.h).width());
//      this.h.setText(Integer.toString(i2));
//      this.g.setMax(i1);
//      this.g.setProgress(i2);
//      this.g.setOnSeekBarChangeListener(new sv(this));
//      this.i = ((CheckBox)paramViewGroup.findViewById(ra.alwaysShowStatusText));
//      this.i.setChecked(L.a.getBoolean("status_show_always", false));
//      this.i.setOnCheckedChangeListener(new sw(this));
//      this.j = ((CheckBox)paramViewGroup.findViewById(ra.alwaysShowStatusBar));
//      if (!L.g)
//        break label801;
//      this.j.setChecked(L.a.getBoolean("status_bar_show_always", false));
//      this.j.setOnCheckedChangeListener(new sx(this));
//      label409: this.k = ((CheckBox)paramViewGroup.findViewById(ra.alwaysShowElapsedTime));
//      this.k.setChecked(qe.s);
//      this.k.setOnCheckedChangeListener(new sy(this));
//      this.l = ((CheckBox)paramViewGroup.findViewById(ra.osd_bottom));
//      this.l.setChecked(L.a.getBoolean("osd_bottom", false));
//      this.l.setOnCheckedChangeListener(new sz(this));
//      this.m = ((CheckBox)paramViewGroup.findViewById(ra.osd_background));
//      this.m.setChecked(L.a.getBoolean("osd_background", true));
//      this.m.setOnCheckedChangeListener(new ta(this));
//      this.n = ((CheckBox)paramViewGroup.findViewById(ra.softButtons));
//      if (qe.B)
//        break label813;
//      this.n.setVisibility(8);
//      label573: this.p = ((CheckBox)paramViewGroup.findViewById(ra.keepScreenOn));
//      this.p.setChecked(L.a.getBoolean("keep_screen_on", false));
//      this.p.setOnCheckedChangeListener(new sq(this));
//      localCheckBox2 = (CheckBox)paramViewGroup.findViewById(ra.battery_clock_in_title_bar);
//      if (!L.g)
//        break label841;
//      this.q = localCheckBox2;
//      this.q.setChecked(L.a.getBoolean("battery_clock_in_title_bar", false));
//      this.q.setOnCheckedChangeListener(new sr(this));
//    }
//    while (true)
//    {
//      this.o = ((CheckBox)paramViewGroup.findViewById(ra.showLeftTime));
//      this.o.setChecked(L.a.getBoolean("show_left_time", false));
//      this.o.setOnCheckedChangeListener(new ss(this));
//      return;
//      this.u = qe.c();
//      this.e = ((Spinner2)paramViewGroup.findViewById(ra.fullscreen));
//      this.e.setClient(paramuu);
//      this.e.setSelection(rs.a(this.u, this.s, 0));
//      this.e.setOnItemSelectedListener(new st(this));
//      break;
//      label795: bool = true;
//      break label205;
//      label801: this.j.setVisibility(8);
//      break label409;
//      label813: this.n.setChecked(qe.C);
//      this.n.setOnCheckedChangeListener(new sp(this));
//      break label573;
//      label841: localCheckBox2.setVisibility(8);
//      this.q = null;
//    }
  }

  private float a()
  {
    //return (float)BrightnessBar.a(this.g.getMax(), this.g.getProgress());
	  return 1.0F;
  }

  public void a(SharedPreferences.Editor paramEditor)
  {
//    if (this.f.isChecked())
//    for (boolean bool = false; ; bool = true)
//    {
//      qe.k = bool;
//      qe.l = a();
//      qe.s = this.k.isChecked();
//      qe.p = this.r[this.d.getSelectedItemPosition()];
//      if (this.e != null)
//        paramEditor.putInt("fullscreen", this.s[this.e.getSelectedItemPosition()]);
//      paramEditor.putInt("screen_orientation", qe.p);
//      paramEditor.putBoolean("screen_brightness_auto", qe.k);
//      paramEditor.putFloat("screen_brightness", qe.l);
//      paramEditor.putBoolean("status_show_always", this.i.isChecked());
//      if (this.j.getVisibility() == 0)
//        paramEditor.putBoolean("status_bar_show_always", this.j.isChecked());
//      paramEditor.putBoolean("elapsed_time_show_always", qe.s);
//      paramEditor.putBoolean("keep_screen_on", this.p.isChecked());
//      if (this.q != null)
//        paramEditor.putBoolean("battery_clock_in_title_bar", this.q.isChecked());
//      paramEditor.putBoolean("show_left_time", this.o.isChecked());
//      if (this.n.getVisibility() == 0)
//        paramEditor.putBoolean("auto_hide_soft_buttons", this.n.isChecked());
//      paramEditor.putBoolean("osd_bottom", this.l.isChecked());
//      paramEditor.putBoolean("osd_background", this.m.isChecked());
//      return;
//    }
  }
}