package com.karthyks.bottombarview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.karthyks.bottombarview.R;

public class BottomBarView extends LinearLayout {
  private static final String TAG = BottomBarView.class.getSimpleName();

  private int bottomBarColor;
  private LinearLayout parentFrame;

  public BottomBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public BottomBarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BottomBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    this.setLayoutParams(layoutParams);
    this.setFocusableInTouchMode(true);
    this.addView(getViewContent());
    TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs,
        R.styleable.BottomBarView, 0, 0);
    try {
      bottomBarColor = typedArray.getColor(R.styleable.BottomBarView_bottomBarColor, -1);
      setBgColor(bottomBarColor);
      typedArray.recycle();
    } catch (Exception e) {
      Log.e(TAG, "Initializing in XML failed: Set required fields " + e.toString());
    }
  }

  private void setBgColor(int color) {
    parentFrame.setBackgroundColor(color);
    Log.d(TAG, "getViewContent: " + parentFrame.getHeight());
  }

  private View getViewContent() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_bar, this, false);
    parentFrame = (LinearLayout) view.findViewById(R.id.bottomBarParentFrame);
    return view;
  }

  public void addAsChild(int layoutId) {
    View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
    parentFrame.addView(view);
  }

  public void setWeight(int weight) {
    parentFrame.setWeightSum(3);
  }

  public LinearLayout getParentFrame() {
    return parentFrame;
  }
}
