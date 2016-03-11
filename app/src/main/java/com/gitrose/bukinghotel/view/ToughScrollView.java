package com.gitrose.bukinghotel.view;

/**
 * Created by GITRose on 1/20/2016.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;

public class ToughScrollView extends ScrollView {
    private GestureDetector mGestureDetector;
    OnTouchListener mGestureListener;

    class YScrollDetector extends SimpleOnGestureListener {
        YScrollDetector() {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    public ToughScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && this.mGestureDetector.onTouchEvent(ev);
    }
}
