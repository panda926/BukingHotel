package com.gitrose.bukinghotel.view;

/**
 * Created by GITRose on 1/27/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.gitrose.bukinghotel.R;

public class CustomPopupWindow  extends PopupWindow {
    private Activity activity;
    private View contentView;
    private int width;
    private int height;

    public CustomPopupWindow(Activity activity) {
        super();
        this.activity = activity;
        this.initPopupWindow();
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contentView = inflater.inflate(R.layout.popupwindow_custom, null);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
        ColorDrawable background = new ColorDrawable(0x4f000000);
        this.setBackgroundDrawable(background);
        this.mandatorDraw();
    }

    private void mandatorDraw() {
        this.contentView.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        this.width = this.contentView.getMeasuredWidth();
        this.height = this.contentView.getMeasuredHeight();
    }


    public void showAtDropDownRight(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] + parent.getWidth()
                    - this.getWidth(), location[1] + parent.getHeight());
        }
    }


    public void showAtDropDownLeft(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];

            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0], location[1] + parent.getHeight());
        }
    }


    public void showAtDropDownCenter(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];

            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] / 2 + parent.getWidth() / 2 - this.width / 6, location[1] + parent.getHeight());
        }
    }

    public static class PopupWindowBuilder{
        private static String activityHashCode;
        private static CustomPopupWindow popupWindow;
        public static PopupWindowBuilder ourInstance;

        public static PopupWindowBuilder getInstance(Activity activity) {
            if (ourInstance == null) ourInstance = new PopupWindowBuilder();
            String hashCode = String.valueOf(activity.hashCode());

            if (!hashCode.equals(String.valueOf(activityHashCode))) {
                activityHashCode = hashCode;
                popupWindow = new CustomPopupWindow(activity);
            }
            return ourInstance;
        }

        public PopupWindowBuilder setTouchable(boolean touchable) {
            popupWindow.setTouchable(touchable);
            return this;
        }

        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
            popupWindow.setAnimationStyle(animationStyle);
            return this;
        }

        public PopupWindowBuilder setBackgroundDrawable(Drawable background) {
            popupWindow.setBackgroundDrawable(background);
            return this;
        }

        public CustomPopupWindow getPopupWindow() {
            popupWindow.update();
            return popupWindow;
        }

    }
}
