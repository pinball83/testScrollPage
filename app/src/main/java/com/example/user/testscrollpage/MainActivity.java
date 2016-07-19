package com.example.user.testscrollpage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MainActivity extends AppCompatActivity {


    private static final int SCALE = 1;
    private static final float EDGE = 15;
    private GestureDetectorCompat mDetector;
    private Scroller mScroller;
    private ValueAnimator mScrollAnimator;
    private int scrollX;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final WebView target = new WebView(this);
        container.addView(target);
        mScroller = new Scroller(this, null, true);
        mScrollAnimator = ValueAnimator.ofFloat(0, 1);
        mScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (!mScroller.isFinished()) {
                    mScroller.computeScrollOffset();
//                    System.out.println("x = " + mScroller.getCurrX() + " y = " + mScroller.getCurrY());
                    container.setX(mScroller.getCurrX());
                    container.setY(mScroller.getCurrY());
                    ViewCompat.postInvalidateOnAnimation(container);

                } else {
                    mScrollAnimator.cancel();
                    mScroller.forceFinished(true);
                }
            }
        });

        mDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                scrollX = (int) motionEvent.getX();
                System.out.println("MainActivity.onDown X = " + motionEvent.getX());
                mScroller.forceFinished(true);
                ViewCompat.postInvalidateOnAnimation(container);
                return true;
            }


            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
                System.out.println("MainActivity.onScroll distanceX = " + distanceX + " raw X =" + motionEvent1.getRawX() + " X " + motionEvent1.getX() + " first touch X" + motionEvent.getX());
                mScroller.forceFinished(true);

                int x = (int) motionEvent1.getRawX() - scrollX;
                float dp = convertPixelsToDp(x, MainActivity.this);
                if (Math.abs(dp) >= EDGE && flag) {
                    System.out.println("add new view");
                    final WebView target = new WebView(MainActivity.this);
                    target.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    container.addView(target);
//                    ViewCompat.postInvalidateOnAnimation(container);
                    flag = false;
                }
                mScroller.startScroll(x, 0, (int) distanceX, 0);
                mScrollAnimator.setDuration(mScroller.getDuration());
                mScrollAnimator.start();
                ViewCompat.postInvalidateOnAnimation(container);
                return false;
            }


//            @Override
//            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
//                System.out.println("MainActivity.onFling");
//                mScroller.forceFinished(true);
//                mScroller.fling(0, 0,
//                        (int) velocityX / SCALE, 0,
//                        -container.getWidth(), container.getWidth() + target.getWidth(),
//                        0, 0);
//                mScrollAnimator.setDuration(mScroller.getDuration());
//                mScrollAnimator.start();
//
//                return true;
//            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("MainActivity.onTouchEvent");
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
