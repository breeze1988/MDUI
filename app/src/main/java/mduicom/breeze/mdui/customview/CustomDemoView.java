package mduicom.breeze.mdui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/2/22.
 */
public class CustomDemoView extends View {

    private Paint mPaint;

    public CustomDemoView(Context context){
        super(context);
        init();
    }

    public CustomDemoView(Context context,AttributeSet attrs){
        super(context,attrs);
        init();
    }

    public CustomDemoView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        init();
    }

    private void init(){
                mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(20);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       int measureWidth = measureWidth(widthMeasureSpec);
       int measureHeight = measureHeight(heightMeasureSpec);

       setMeasuredDimension(measureWidth,measureHeight); //制定视图在屏幕上大小
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL); //设置填充
        canvas.drawRect(10, 10, 200, 200, mPaint); //绘制矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawText("自定义view",10,120,mPaint);
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
        Log.e("customview","widthMeasureSpec = " + pWidthMeasureSpec +
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
        Log.e("customview","widthMeasureSpec = " + pHeightMeasureSpec +
                " , measureWidth = " + result +
                " , modeWidth = " + heightMode +
                " , toString = " + MeasureSpec.toString(pHeightMeasureSpec));
        return  result;
    }
}
