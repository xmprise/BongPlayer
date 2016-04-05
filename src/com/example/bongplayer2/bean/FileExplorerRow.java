package com.example.bongplayer2.bean;

import com.example.bongplayer2.R;
import com.example.bongplayer2.R.id;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public final class FileExplorerRow
{
  public CheckBox checked;
  public TextView duration;
  public ImageView icon;
  public View iconWrapper;
  public TextView name;
  public ProgressBar progress;
  public TextView size;
  public TextView speed;

  public FileExplorerRow(View paramView)
  {
    name = ((TextView)paramView.findViewById(R.id.file_name));
    size = ((TextView)paramView.findViewById(R.id.file_size));
    duration = ((TextView)paramView.findViewById(R.id.file_duration));
    icon = ((ImageView)paramView.findViewById(R.id.file_icon));
    iconWrapper = paramView.findViewById(R.id.file_icon_wrapper);
    checked = ((CheckBox)paramView.findViewById(R.id.file_check));
    progress = ((ProgressBar)paramView.findViewById(android.R.id.progress));
    speed = ((TextView)paramView.findViewById(R.id.file_speed));
  }
}
