package com.gitrose.bukinghotel.view;

/**
 * Created by GITRose on 1/19/2016.
 */
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ToughViewPager extends ViewPager {
    public ToughViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToughViewPager(Context context) {
        super(context);
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v == this || !(v instanceof ViewPager)) {
            return super.canScroll(v, checkV, dx, x, y);
        }
        return true;
    }
}
