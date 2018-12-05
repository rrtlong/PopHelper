package com.moli.module.widget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import com.moli.module.widget.R;

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/11/3 10:53 AM
 * 修改人：yuliyan
 * 修改时间：2018/11/3 10:53 AM
 * 修改备注：
 */
public class MarqueeTextView extends AppCompatTextView {
    private static final int ROLLING_INTERVAL_DEFAULT = 10000;
    private static final int FIRST_SCROLL_DELAY_DEFAULT = 1000;
    public static final int SCROLL_FOREVER = 100;
    public static final int SCROLL_ONCE = 101;
    private Scroller mScroller;
    private int mRollingInterval;
    private int mXPaused;
    private boolean mPaused;
    private boolean mFirst;
    private int mScrollMode;
    private int mFirstScrollDelay;

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mXPaused = 0;
        this.mPaused = true;
        this.mFirst = true;
        this.initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        this.mRollingInterval = typedArray.getInt(R.styleable.MarqueeTextView_scroll_interval, 10000);
        this.mScrollMode = typedArray.getInt(R.styleable.MarqueeTextView_scroll_mode, 100);
        this.mFirstScrollDelay = typedArray.getInt(R.styleable.MarqueeTextView_scroll_first_delay, 1000);
        typedArray.recycle();
        this.setSingleLine();
        this.setEllipsize((TextUtils.TruncateAt) null);
    }

    public void startScroll() {
        this.mXPaused = 0;
        this.mPaused = true;
        this.mFirst = true;
        this.resumeScroll();
    }

    public void resumeScroll() {
        if (this.mPaused) {
            this.setHorizontallyScrolling(true);
            if (this.mScroller == null) {
                this.mScroller = new Scroller(this.getContext(), new LinearInterpolator());
                this.setScroller(this.mScroller);
            }

            int scrollingLen = this.calculateScrollingLen();
            final int distance = scrollingLen - (this.getWidth() + this.mXPaused);
            final int duration = Double.valueOf((double) (this.mRollingInterval * distance) * 1.0D / (double) scrollingLen).intValue();
            if (this.mFirst) {
                (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                    public void run() {
                        MarqueeTextView.this.mScroller.startScroll(MarqueeTextView.this.mXPaused, 0, distance, 0, duration);
                        MarqueeTextView.this.invalidate();
                        MarqueeTextView.this.mPaused = false;
                    }
                }, (long) this.mFirstScrollDelay);
            } else {
                this.mScroller.startScroll(this.mXPaused, 0, distance, 0, duration);
                this.invalidate();
                this.mPaused = false;
            }

        }
    }

    public void pauseScroll() {
        if (null != this.mScroller) {
            if (!this.mPaused) {
                this.mPaused = true;
                this.mXPaused = this.mScroller.getCurrX();
                this.mScroller.abortAnimation();
            }
        }
    }

    public void stopScroll() {
        if (null != this.mScroller) {
            this.mPaused = true;
            this.mScroller.startScroll(0, 0, 0, 0, 0);
        }
    }

    private int calculateScrollingLen() {
        TextPaint tp = this.getPaint();
        Rect rect = new Rect();
        String strTxt = this.getText().toString();
        tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
        int scrollingLen = rect.width() + this.getWidth();
        rect = null;
        return scrollingLen;
    }

    public void computeScroll() {
        super.computeScroll();
        if (null != this.mScroller) {
            if (this.mScroller.isFinished() && !this.mPaused) {
                if (this.mScrollMode == 101) {
                    this.stopScroll();
                    return;
                }

                this.mPaused = true;
                this.mXPaused = -1 * this.getWidth();
                this.mFirst = false;
                this.resumeScroll();
            }

        }
    }

    public int getRndDuration() {
        return this.mRollingInterval;
    }

    public void setRndDuration(int duration) {
        this.mRollingInterval = duration;
    }

    public void setScrollMode(int mode) {
        this.mScrollMode = mode;
    }

    public int getScrollMode() {
        return this.mScrollMode;
    }

    public void setScrollFirstDelay(int delay) {
        this.mFirstScrollDelay = delay;
    }

    public int getScrollFirstDelay() {
        return this.mFirstScrollDelay;
    }

    public boolean isPaused() {
        return this.mPaused;
    }
}
