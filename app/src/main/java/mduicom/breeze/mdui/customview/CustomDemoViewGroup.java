package mduicom.breeze.mdui.customview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/2/23.
 */
public class CustomDemoViewGroup extends ViewGroup{

    private int mOffsetX = 0; //用于计算滚动的偏移量

    private static final int DELAY_MILLIS = 1000/60;

    private android.os.Handler mHandler = new Handler();

    private Scroller mScroller = new Scroller(getContext());

    private Runnable mScrollerRunnable = new ScrollerRunnable();

   /* 每次滚动的距离*/
    private int mScrollWidth = 300;


    public CustomDemoViewGroup(Context context) {
        super(context);
    }

    public CustomDemoViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDemoViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight); //制定视图在屏幕上大小
    }

    /*
    EXACTLY:设定具体值 match_parent 和 具体数值
    AT_MOST : 不能超过最大值 wrap_content
    UNSPECIFIED ：没有限制
    */
    private int measureWidth(int pWidthMeasureSpec){
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){
            result = widthSize;
        }else{
            result = getWidth() + getPaddingRight() + getPaddingLeft();//计算自身需要的高度
            if(widthMode == MeasureSpec.AT_MOST){
                result = Math.min(result,widthSize);
            }
        }
        Log.e("customviewGroup", "widthMeasureSpec = " + pWidthMeasureSpec +
                " , measureWidth = " + result +
                " , modeWidth = " + widthMode +
                " , toString = " + MeasureSpec.toString(pWidthMeasureSpec));
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec){
        int result = 0;
        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        if(heightMode == MeasureSpec.EXACTLY){
            result = heightSize;
        }else{
            result = getHeight() + getPaddingBottom() + getPaddingTop();
            if(heightMode == MeasureSpec.AT_MOST){
                result = Math.min(result,heightSize);
            }
        }
        Log.e("customviewGroup", "widthMeasureSpec = " + pHeightMeasureSpec +
                " , measureWidth = " + result +
                " , modeWidth = " + heightMode +
                " , toString = " + MeasureSpec.toString(pHeightMeasureSpec));
        return  result;
    }

    /*left, top, right, bottom是当前ViewGroup整个在屏幕上的位置*/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mTotalHeight = 0;
        //遍历所有子视图
        int childCount = getChildCount();
        for(int i = 0;i < childCount;i++){
            View childView = getChildAt(i);
            //获取在onMeasure中计算的尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measureWidth = childView.getMeasuredWidth();
            childView.layout(l,mTotalHeight,measureWidth,mTotalHeight + measureHeight);
            mTotalHeight += measureHeight;
            Log.e("customViewGroupDemo","changed = " + changed + ", left = " + l + ",top = " + t + ",right = " + r + ",bottom = " + b + ",measureWidth = " +  measureWidth + ",measureHeight = " +measureHeight);
        }

    }

    public void scrollToRight(){

    }

    private void scroll(boolean scrollToRight){
        //向右滚动举例是正值，反之为负值
        int scrollWidth = scrollToRight ? mScrollWidth : - mScrollWidth;

        //此次滚动的X轴终点位置
        int scrollFinalX = mOffsetX + scrollWidth;
        //不能移除边界
        if(scrollFinalX > 300 || scrollFinalX < 0){
            return;
        }
        mHandler.removeCallbacks(mScrollerRunnable);
        //开始触发滚动
        mScroller.startScroll(mOffsetX,0,scrollFinalX,0);
        //子线程中间隔段时间重绘视图
        mHandler.post(mScrollerRunnable);
    }

    private class ScrollerRunnable implements Runnable{
        @Override
        public void run() {
            final Scroller scroller = mScroller;
            if(scroller.computeScrollOffset()){
                setmOffsetX(scroller.getCurrX());

                //位置发生偏移，重绘
                invalidate();
                //每过间隔时间，再次执行当前子线程
                mHandler.postDelayed(this,DELAY_MILLIS);
            }
        }
    }

    /*设置view滚动后的x坐标位置*/
    private void setmOffsetX(int offsetX){
        if (offsetX == mOffsetX){
            return;
        }

        final int leftAndRightOffset = offsetX - mOffsetX;

        int childCount = getChildCount();

        for (int i = 0;i < childCount;i++){
            View childView = getChildAt(i);
            childView.offsetLeftAndRight(leftAndRightOffset);
        }
        mOffsetX = offsetX;
    }


}
