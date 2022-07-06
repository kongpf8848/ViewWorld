package com.github.kongpf8848.viewworld.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 侧滑菜单
 */
public class SlideMenu extends LinearLayout {

    private static final String TAG = "SlideMenu";
    private Scroller scroller;
    private VelocityTracker velocityTracker;

    private int mLastX = 0;
    private int mLastY = 0;
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private int menuWidth;


    public SlideMenu(Context context) {
        this(context,null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        scroller=new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
    }

    public void setMenuWidth(int menuWidth){
        this.menuWidth=menuWidth;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                mLastX = x;
                mLastY = y;
                mLastXIntercept = x;
                mLastYIntercept = y;
                if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(1000);
        int x=(int)event.getX();
        int y=(int)event.getY();
        int scrollX = getScrollX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                 int deltaX = x - mLastX;
                 int deltaY = y - mLastY;
                Log.d(TAG, "onTouchEvent() called with: deltaX = " + deltaX + ",scrollX="+scrollX);
                if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                    int distance=-deltaX;
                    if(scrollX+distance>menuWidth) {
                        distance = menuWidth - scrollX;
                    }else if(scrollX+distance<0){
                        distance=-scrollX;
                    }
                    scrollBy(distance, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int destX = (scrollX > (menuWidth / 2)) ? (menuWidth) : 0;
                int delta = destX - scrollX;
                scroller.startScroll(scrollX, 0, delta, 0, 300);
                invalidate();
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }


}
