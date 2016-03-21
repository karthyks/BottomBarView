package com.karthyks.bottombarview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.karthyks.bottombarview.R;

import java.util.LinkedList;
import java.util.List;

public class BottomBarView extends FrameLayout implements View.OnClickListener {

  public static final int THEME_DARK = 0;
  public static final int THEME_LIGHT = 1;

  private List<BottomBarButton> buttonIDs;

  private static final String TAG = BottomBarView.class.getSimpleName();
  private LinearLayout parentFrame;

  public BottomBarView(Context context) {
    super(context);
  }

  public BottomBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

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
    buttonIDs = new LinkedList<>();
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    this.setLayoutParams(layoutParams);
    this.setFocusableInTouchMode(true);
    this.addView(getViewContent());

    TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs,
        R.styleable.BottomBarView, 0, 0);
    TypedArray drawables = getResources().obtainTypedArray(R.array.drawable_id);
    TypedArray buttonTexts = getResources().obtainTypedArray(R.array.button_text);
    try {
      setTheme(typedArray.getInteger(R.styleable.BottomBarView_bottomBarTheme, -1));
      int noButtons = typedArray.getInteger(R.styleable.BottomBarView_noOfButtons, -1);
      if (noButtons > 0) {
        for (int i = 0; i < noButtons; i++) {
          int id = BottomBarButton.DEFAULT_ID + (BottomBarButton.MULTIPLICATION_FACTOR + i);
          addButton(drawables.getResourceId(i, -1), buttonTexts.getString(i), id);
        }
      }
    } catch (Exception e) {
      Log.e(TAG, "Initializing in XML failed: Set required fields " + e.toString());
    }
    typedArray.recycle();
    drawables.recycle();
    buttonTexts.recycle();
  }

  private void setTheme(int theme) {
    switch (theme) {
      case THEME_DARK:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          parentFrame.setBackgroundColor(getContext().getResources().getColor(R.color.bottomBarDark,
              null));
        } else {
          parentFrame.setBackgroundColor(getContext().getResources().getColor(
              R.color.bottomBarDark));
        }
        break;
      case THEME_LIGHT:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          parentFrame.setBackgroundColor(getContext().getResources().getColor(
              R.color.bottomBarLight, null));
        } else {
          parentFrame.setBackgroundColor(getContext().getResources().getColor(
              R.color.bottomBarLight));
        }
        break;
      default:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          parentFrame.setBackgroundColor(getContext().getResources().getColor(R.color.bottomBarDark,
              null));
        } else {
          parentFrame.setBackgroundColor(getContext().getResources().getColor(
              R.color.bottomBarDark));
        }
    }
  }

  private View getViewContent() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_bar, this, false);
    parentFrame = (LinearLayout) view.findViewById(R.id.bottomBarParentFrame);
    return view;
  }

  private void addButton(int drawable, String text, int id) {
    parentFrame.addView(getButtonAsView(drawable, text, id));
  }

  private View getButtonAsView(final int drawable, String text, int id) {
    BottomBarButton button = new BottomBarButton(getContext(), drawable, text, id, this);
    buttonIDs.add(button);
    return button;
  }

  @Override public void onClick(View v) {
    BottomBarButton buttonClass = (BottomBarButton) v;
    for (int i = 0; i < buttonIDs.size(); i++) {
      if (buttonIDs.get(i).getID() == buttonClass.getID()) {
        if (buttonIDs.get(i).getPressedState()) {
          buttonIDs.get(i).onReleased();
        } else {
          buttonIDs.get(i).onPressed();
        }
      } else {
        buttonIDs.get(i).onReleased();
      }
    }
  }
}
