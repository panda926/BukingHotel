package com.gitrose.bukinghotel.view;

/**
 * Created by GITRose on 1/21/2016.
 */
import android.content.Context;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class FlowLayout extends ViewGroup {
    static final /* synthetic */ boolean $assertionsDisabled;
    private int line_height;

    static {
        $assertionsDisabled = !FlowLayout.class.desiredAssertionStatus();
    }

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if ($assertionsDisabled || MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
            int count = getChildCount();
            int line_height = 0;
            int xpos = getPaddingLeft();
            int ypos = getPaddingTop();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    LayoutParams lp = child.getLayoutParams();
                    child.measure(MeasureSpec.makeMeasureSpec(width, ExploreByTouchHelper.INVALID_ID), MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED));
                    int childw = child.getMeasuredWidth();
                    line_height = Math.max(line_height, child.getMeasuredHeight() + lp.height);
                    if (xpos + childw > width) {
                        xpos = getPaddingLeft();
                        ypos += line_height;
                    }
                    xpos += lp.width + childw;
                }
            }
            this.line_height = line_height;
            if (MeasureSpec.getMode(heightMeasureSpec) == View.VISIBLE) {
                height = ypos + line_height;
            } else if (MeasureSpec.getMode(heightMeasureSpec) == ExploreByTouchHelper.INVALID_ID && ypos + line_height < height) {
                height = ypos + line_height;
            }
            setMeasuredDimension(width, height);
            return;
        }
        throw new AssertionError();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1, 1);
    }

    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof LayoutParams;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int width = r - l;
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int childw = child.getMeasuredWidth();
                int childh = child.getMeasuredHeight();
                LayoutParams lp = child.getLayoutParams();
                if (xpos + childw > width) {
                    xpos = getPaddingLeft();
                    ypos += this.line_height;
                }
                child.layout(xpos, ypos, xpos + childw, ypos + childh);
                xpos += lp.width + childw;
            }
        }
    }
}
