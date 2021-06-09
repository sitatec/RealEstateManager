package com.berete.realestatemanager.ui.custom;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.berete.realestatemanager.R;

public class PointOfInterestView extends androidx.appcompat.widget.AppCompatTextView {

  private boolean isChecked;

  public PointOfInterestView(@NonNull Context context, boolean isChecked) {
    super(context);
    setChecked(isChecked);
    setPadding(23, 3, 23, 8);
    setMinWidth(100);
    setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
    setTextSize(17);
    setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
  }

  public PointOfInterestView(@NonNull Context context) {
    this(context, false);
  }

  public void setChecked(boolean isChecked) {
    this.isChecked = isChecked;
    if (isChecked) {
      setBackgroundResource(R.drawable.checked_text_bg);
      setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_24, 0);
    } else {
      setBackgroundResource(R.drawable.unchecked_text_bg);
      setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
    }
  }

  public void toggleChecked() {
    setChecked(!isChecked);
  }

  public boolean isChecked() {
    return isChecked;
  }
}
