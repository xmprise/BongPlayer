package com.example.bongplayer2.dialog;

import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bongplayer2.interfaces.rt;

public class sk
{
  public boolean a;
  private TunerDialog b;
  private rt c;
  private SeekBar d;
  private TextView e;
  private CheckBox f;

  public sk(TunerDialog paramrs, ViewGroup paramViewGroup, rt paramrt)
  {
    this.b = paramrs;
    this.c = paramrt;
//    this.d = ((SeekBar)paramViewGroup.findViewById(ra.ssa_font_scale));
//    this.e = ((TextView)paramViewGroup.findViewById(ra.ssa_font_scale_text));
//    this.e.setMinimumWidth(3 * mc.a(this.e).width() + mc.a(this.e, "%").width());
//    float f1 = L.a.getFloat("ssa_font_scale", 1.0F);
//    this.e.setText(Integer.toString(Math.round(100.0F * f1)) + '%');
//    this.d.setMax(Math.round(38.0F));
//    this.d.setProgress(Math.round(10.0F * (f1 - 0.2F)));
//    this.d.setOnSeekBarChangeListener(new sl(this));
//    this.f = ((CheckBox)paramViewGroup.findViewById(ra.ssa_font_ignore));
//    this.f.setChecked(L.a.getBoolean("ssa_font_ignore", false));
//    this.f.setOnCheckedChangeListener(new sm(this));
  }

  public void a(SharedPreferences.Editor paramEditor)
  {
    paramEditor.putFloat("ssa_font_scale", 0.2F + this.d.getProgress() / 10.0F);
    paramEditor.putBoolean("ssa_font_ignore", this.f.isChecked());
  }
}
