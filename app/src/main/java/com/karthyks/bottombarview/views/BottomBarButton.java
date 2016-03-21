package com.karthyks.bottombarview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthyks.bottombarview.R;
import com.karthyks.bottombarview.drawable.BBNDrawable;

public class BottomBarButton extends LinearLayout {

  private static final String TAG = BottomBarButton.class.getSimpleName();
  public static final int DEFAULT_ID = 990000002;
  public static final int MULTIPLICATION_FACTOR = 10 * 10;

  private int buttonBg;
  private String buttonText;
  private OnClickListener listener;
  private boolean pressedState;
  private ImageView imageViewButtonBg;
  private TextView textViewButtonText;

  private int maxWidth;
  private int minWidth;
  private int height;

  private int id;

  public BottomBarButton(Context context, int drawable, String text, int id,
                         OnClickListener listener) {
    super(context);
    buttonBg = drawable;
    buttonText = text;
    this.listener = listener;
    this.id = id;
    init();
  }

  public BottomBarButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public BottomBarButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BottomBarButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  private void init() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_button, this, false);
    imageViewButtonBg = (ImageView) view.findViewById(R.id.img_button_bg);
    imageViewButtonBg.setImageDrawable(new BBNDrawable(getContext(), buttonBg,
        BBNDrawable.NORMAL_STATE));
    pressedState = false;
    this.setOnClickListener(listener);
    textViewButtonText = (TextView) view.findViewById(R.id.txt_button_text);
    textViewButtonText.setText(buttonText);
    minWidth = (int) getContext().getResources().getDimension(
        R.dimen.bottom_bar_button_inactive_width);
    maxWidth = (int) getContext().getResources().getDimension(
        R.dimen.bottom_bar_button_active_width);
    height = (int) getContext().getResources().getDimension(
        R.dimen.bottom_bar_default_height);
    this.addView(view);
  }

  private void changeWidth(int width) {
    LayoutParams layoutParams = new LayoutParams(width, height);
    this.setLayoutParams(layoutParams);
  }

  public void onPressed() {
    changeWidth(maxWidth);
    imageViewButtonBg.setImageDrawable(new BBNDrawable(getContext(), buttonBg,
        BBNDrawable.PRESSED_STATE));
    pressedState = true;
    textViewButtonText.setVisibility(VISIBLE);
  }

  public void onReleased() {
    changeWidth(minWidth);
    imageViewButtonBg.setImageDrawable(new BBNDrawable(getContext(), buttonBg,
        BBNDrawable.NORMAL_STATE));
    pressedState = false;
    textViewButtonText.setVisibility(GONE);
  }

  public void setPressedState(boolean state) {
    this.pressedState = state;
    if (pressedState) {
      onReleased();
    } else {
      onPressed();
    }
  }

  public boolean getPressedState() {
    return this.pressedState;
  }

  public int getID() {
    return id;
  }
}
