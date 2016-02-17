package mduicom.breeze.mdui.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/17.
 */
public class FreeRockViewPager extends ViewPager {

    public ArrayList<ViewPagerScrollListener> mViewPagerScrollListeners = new ArrayList<ViewPagerScrollListener>();
    private boolean scrollEnable = true;
    private int mDuration = 0;
    private OnPrePageChangeListener mOnPrePageChangeListener;
    private boolean mHandleCanChange = false;// 是否处理过拦截事件
    private boolean mCanChange = true;//是否可以切换页面

    public interface OnPrePageChangeListener {
        /**
         * 该页是否可以切换到其他页面
         *
         * @param currentPosition
         * @return true，也可以正常切换，false：不能切换（该次切换动作无效）
         */
        boolean onCanChange(int currentPosition);
    }

    public void setOnPrePageChangeListener(OnPrePageChangeListener listener) {
        mOnPrePageChangeListener = listener;
    }

    public FreeRockViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FreeRockViewPager(Context context) {
        super(context);
    }

    public void setScrollEnable(boolean scrollEnable) {
        this.scrollEnable = scrollEnable;
    }

    /**
     * 页面滑动拦截
     *
     * @param event
     * @return false表示不可以切换(拦截本次的切换事件)，true表示可以切换
     */
    private boolean interceptEvent(MotionEvent event) {
        int action = event.getAction();

        // 正常情况下在ACTION_DOWN处进行拦截，返回false后就不会再收到event事件
        if (action == MotionEvent.ACTION_DOWN) {
            mHandleCanChange = true;
            if (mOnPrePageChangeListener != null) {
                mCanChange = mOnPrePageChangeListener.onCanChange(getCurrentItem());
            }
        } else if (action == MotionEvent.ACTION_MOVE) {
            // 有的手机不会收到ACTION_DOWN事件，而直接是ACTION_MOVE事件
            // 这个时候在第一次ACTION_MOVE事件时拦截
            if (!mHandleCanChange) {
                mHandleCanChange = true;
                if (mOnPrePageChangeListener != null) {
                    mCanChange = mOnPrePageChangeListener.onCanChange(getCurrentItem());
                }
            }
        } else if (action == MotionEvent.ACTION_UP) {
            // 如果执行了ACTION_DOWN，并且被拦截了就不会执行ACTION_UP
            // 下面两个值就不能被重置，但是在ACTION_DOWN不会判断这两个值，所以没问题

            // 状态还原(这个还原主要是针对第一次接收到的事件是ACTION_MOVE的时候)
            mHandleCanChange = false;
            if (!mCanChange) {
                mCanChange = true;
                return false;
            } else {
                mCanChange = true;
                return true;
            }
        }

        return mCanChange;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!interceptEvent(arg0)) {
            return false;
        }

        if (scrollEnable && isEnabled()) {
            // 可能会抛：IllegalArgumentException: pointerIndex out of range
            try {
                return super.onTouchEvent(arg0);
            } catch (Exception e) {
            }
        }
        return !isEnabled();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (scrollEnable && isEnabled()) {
            try {
                return super.onInterceptTouchEvent(arg0);
            } catch (Exception e) {
            }
        }
        return !isEnabled();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mViewPagerScrollListeners != null) {
            for (ViewPagerScrollListener mViewPagerScrollListener : mViewPagerScrollListeners) {
                mViewPagerScrollListener.scroll(x, y, getWidth(), getAdapter()
                        .getCount());
            }
        }
        super.scrollTo(x, y);
    }

    public void setDuration(int time) {
        if (time <= 0) {
            time = 0;
            try {
                Field mField = ViewPager.class.getDeclaredField("mScroller");
                mField.setAccessible(true);
                mField.set(this, new Scroller(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Field mField = ViewPager.class.getDeclaredField("mScroller");
                mField.setAccessible(true);
                FixedSpeedScroller mScroller = new FixedSpeedScroller(getContext(), new AccelerateInterpolator());
                mField.set(this, mScroller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDuration = time;
    }

    public int getDuration() {
        return mDuration;
    }

    public void addViewPagerScrollListener(
            ViewPagerScrollListener mViewPagerScrollListener) {
        this.mViewPagerScrollListeners.add(mViewPagerScrollListener);
    }

    public void removeViewPagerScrollListener(
            ViewPagerScrollListener mViewPagerScrollListener) {
        this.mViewPagerScrollListeners.remove(mViewPagerScrollListener);
    }

    public class FixedSpeedScroller extends Scroller {
        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                                int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    public interface ViewPagerScrollListener {
        public void scroll(int x, int y, int itemWidth, int count);
    }

}
