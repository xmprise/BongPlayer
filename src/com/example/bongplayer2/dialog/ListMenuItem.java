package com.example.bongplayer2.dialog;

public class ListMenuItem
{
  public static final int TYPE_ITEM = 0;
  public static final int TYPE_LINE = 1;
  public int icon;
  public int title;
  public int type = 0;

  public ListMenuItem(int paramInt)
  {
    this.type = paramInt;
  }

  public ListMenuItem(int paramInt1, int paramInt2)
  {
    this.title = paramInt1;
    this.icon = paramInt2;
  }
}