package com.material.components.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    private float initialXValue;
    private boolean canSwipeToLeft = false; // 默认为false，根据你的需要进行设置

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.swipeAllowed(event) && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.swipeAllowed(event) && super.onInterceptTouchEvent(event);
    }

    private boolean swipeAllowed(MotionEvent event) {
        if (this.canSwipeToLeft) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX < 0) {
                    // swipe from left to right detected
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public void setCanSwipeToLeft(boolean canSwipeToLeft) {
        this.canSwipeToLeft = canSwipeToLeft;
    }
}
