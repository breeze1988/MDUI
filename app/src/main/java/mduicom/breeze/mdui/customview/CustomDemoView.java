package mduicom.breeze.mdui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/2/22.
 */
public class CustomDemoView extends View {

    public CustomDemoView(Context context){
        super(context);
    }

    public CustomDemoView(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    public CustomDemoView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       int measureWidth = measureWidth(widthMeasureSpec);
       int measureHeight = measureHeight(heightMeasureSpec);
       setMeasuredDimension(measureWidth,measureHeight);
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
        return  result;
    }
}
