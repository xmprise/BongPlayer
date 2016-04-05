package com.example.bongplayer2.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bongplayer2.interfaces.rt;
import com.example.bongplayer2.interfaces.uu;
import com.example.bongplayer2.widget.Spinner2;
import com.yixia.zi.widget.colorpicker.ColorPanelView;

public class TunerText
{
  public boolean a;
  private TunerDialog b;
  private rt c;
  private uu d;
  private Spinner2 e;
  private SeekBar f;
  private TextView g;
  private ColorPanelView h;
  private CheckBox i;
  private CheckBox j;
  private CheckBox k;
  private CheckBox l;
  private ColorPanelView m;
  private SeekBar n;
  private TextView o;
  //private tu p;

  public TunerText(TunerDialog paramrs, ViewGroup paramViewGroup, rt paramrt, uu paramuu)
  {
    Context localContext = paramViewGroup.getContext();
//    this.b = paramrs;
//    this.c = paramrt;
//    this.d = paramuu;
//    this.e = ((Spinner2)paramViewGroup.findViewById(ra.subtitleTypeface));
//    this.f = ((SeekBar)paramViewGroup.findViewById(ra.subtitleTextSize));
//    this.g = ((TextView)paramViewGroup.findViewById(ra.subtitleTextSizeText));
//    this.h = ((ColorPanelView)paramViewGroup.findViewById(ra.subtitleTextColor));
//    this.j = ((CheckBox)paramViewGroup.findViewById(ra.subtitleShadow));
//    this.l = ((CheckBox)paramViewGroup.findViewById(ra.subtitleBorder));
//    this.m = ((ColorPanelView)paramViewGroup.findViewById(ra.subtitleBorderColor));
//    this.i = ((CheckBox)paramViewGroup.findViewById(ra.subtitleBold));
//    this.e.setClient(paramuu);
//    this.p = new tu(this, qe.o());
//    this.e.setAdapter(this.p);
//    this.e.setSelection(this.p.a());
//    this.e.setOnItemSelectedListener(this.p);
//    this.g.setMinimumWidth(2 * mc.a(this.g).width());
//    int i1 = qe.b(localContext);
//    this.g.setText(Integer.toString(i1));
//    this.f.setMax(44);
//    this.f.setProgress(i1 - 16);
//    this.f.setOnSeekBarChangeListener(new tj(this));
//    this.h.setBorderWidth(2.0F);
//    this.h.setColor(qe.f);
//    this.h.setOnClickListener(new tk(this));
//    this.j.setChecked(qe.k());
//    this.j.setOnCheckedChangeListener(new tm(this));
//    this.k = ((CheckBox)paramViewGroup.findViewById(ra.subtitle_fadeout));
//    this.k.setChecked(qe.r());
//    this.k.setOnCheckedChangeListener(new tn(this));
//    this.l.setChecked(qe.j());
//    this.l.setOnCheckedChangeListener(new to(this));
//    this.m.setBorderWidth(2.0F);
//    this.m.setColor(qe.g);
//    this.m.setOnClickListener(new tp(this));
//    this.n = ((SeekBar)paramViewGroup.findViewById(ra.border_thickness));
//    this.o = ((TextView)paramViewGroup.findViewById(ra.border_thickness_text));
//    this.o.setMinimumWidth(3 * mc.a(this.o).width() + mc.a(this.o, "%").width());
//    float f1 = L.a.getFloat("subtitle_border_thickness", 0.08F);
//    this.o.setText(Integer.toString(Math.round(100.0F * f1 / 0.1F)) + '%');
//    this.n.setMax(Math.round(25.0F));
//    this.n.setProgress(Math.round((f1 - 0.05F) / 0.01F));
//    this.n.setOnSeekBarChangeListener(new tr(this));
//    CheckBox localCheckBox = this.i;
//    if ((0x1 & qe.e) != 0);
//    for (boolean bool = true; ; bool = false)
//    {
//      localCheckBox.setChecked(bool);
//      this.i.setOnCheckedChangeListener(new ts(this));
//      return;
//    }
  }

  private int a()
  {
//    if (this.i.isChecked());
//    for (int i1 = 0x1 | qe.e; ; i1 = 0xFFFFFFFE & qe.e)
//      return i1;
	  return 1;
  }

  private void b()
  {
    if (this.c != null)
      this.c.a(this.b, this.l.isChecked(), this.m.getColor(), 0.05F + 0.01F * this.n.getProgress());
  }

  public void a(SharedPreferences.Editor paramEditor)
  {
//    qe.d = this.p.b().b;
//    qe.e = a();
//    qe.f = this.h.getColor();
//    qe.g = this.m.getColor();
//    paramEditor.putString("subtitle_typeface_name", qe.d);
//    paramEditor.putInt("subtitle_typeface_style", qe.e);
//    paramEditor.putInt("subtitle_text_size", 16 + this.f.getProgress());
//    paramEditor.putInt("subtitle_text_color", qe.f);
//    paramEditor.putBoolean("subtitle_shadow_enabled", this.j.isChecked());
//    paramEditor.putBoolean("subtitle_border_enabled", this.l.isChecked());
//    paramEditor.putInt("subtitle_border_color", qe.g);
//    paramEditor.putString("typeface_dir", this.p.a.getAbsolutePath());
//    paramEditor.putFloat("subtitle_border_thickness", 0.05F + 0.01F * this.n.getProgress());
//    boolean bool = this.k.isChecked();
//    if (qe.r() != bool)
//      paramEditor.putBoolean("subtitle_fadeout", bool);
  }
}