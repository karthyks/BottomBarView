package com.karthyks.bottombarview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthyks.bottombarview.R;

public class BottomBarButton extends LinearLayout {

  private static final String TAG = BottomBarButton.class.getSimpleName();

  private boolean pressedState;
  private ImageView imageViewButtonBg;
  private TextView textViewButtonText;


  private Drawable buttonNormalDrawable;
  private Drawable buttonPressedDrawable;

  private int textColorNormal;
  private int textColorPressed;

  private String buttonText;

  public BottomBarButton(Context context) {
    super(context);
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

  public void build() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_button, this, false);
    imageViewButtonBg = (ImageView) view.findViewById(R.id.img_button_bg);
    imageViewButtonBg.setImageDrawable(buttonNormalDrawable);
    pressedState = false;
    textViewButtonText = (TextView) view.findViewById(R.id.txt_button_text);
    textViewButtonText.setText(buttonText);
    textViewButtonText.setTextColor(textColorNormal);
    textViewButtonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen
        .bottom_bar_text_normal));
    this.addView(view);
  }

  public void onPressed() {
    imageViewButtonBg.setImageDrawable(buttonPressedDrawable);
    pressedState = true;
    //textViewButtonText.setVisibility(VISIBLE);
    textViewButtonText.setTextColor(textColorPressed);
    textViewButtonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen
        .bottom_bar_text_pressed));
  }

  public void onReleased() {
    imageViewButtonBg.setImageDrawable(buttonNormalDrawable);
    pressedState = false;
    //textViewButtonText.setVisibility(GONE);
    textViewButtonText.setTextColor(textColorNormal);
    textViewButtonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen
        .bottom_bar_text_normal));
  }

  public void setPressedState(boolean state) {
    this.pressedState = state;
    if (pressedState) {
      onPressed();
    } else {
      onReleased();
    }
  }

  public BottomBarButton setButtonDrawables(Drawable normalState, Drawable pressedState) {
    this.buttonNormalDrawable = normalState;
    this.buttonPressedDrawable = pressedState;
    return this;
  }

  public BottomBarButton setButtonText(String text) {
    this.buttonText = text;
    return this;
  }

  public BottomBarButton setTextColors(int normalState, int pressedState) {
    this.textColorNormal = normalState;
    this.textColorPressed = pressedState;
    return this;
  }

  public boolean isPressedState() {
    return this.pressedState;
  }
}
