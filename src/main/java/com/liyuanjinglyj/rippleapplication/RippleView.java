package com.liyuanjinglyj.rippleapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/***
 * 水波纹动画自定义控件
 */
public class RippleView extends View {
    private Paint paint;//画笔
    private Path path;//路径
    private int mRippleLength=1200;//波长
    private int dx;//动画的移动值
    public RippleView(Context context) {
        super(context);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.paint=new Paint();
        this.paint.setColor(Color.GREEN);//画笔颜色
        this.paint.setStyle(Paint.Style.FILL);//填充模式
        this.path=new Path();
        this.startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.path.reset();//清除之前绘制的path
        int halfLength=this.mRippleLength/2;//波长的一半
        this.path.moveTo(-this.mRippleLength+dx,300);//起始点P₀
        for(int i=-this.mRippleLength;i<=getWidth()+this.mRippleLength;i+=this.mRippleLength){
            //两句代码组成一个波长，波谷到中间是第一个rQuadTo,中间到波峰是第二rQuadTo
            this.path.rQuadTo(halfLength/2,-100,halfLength,0);
            this.path.rQuadTo(halfLength/2,100,halfLength,0);
        }
        //闭合区域
        this.path.lineTo(getWidth(),getHeight());
        this.path.lineTo(0,getHeight());
        this.path.close();
        //绘制
        canvas.drawPath(this.path,this.paint);
    }

    private void startAnim(){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,this.mRippleLength);//设置动画为一个波长
        valueAnimator.setDuration(2000);//设置一次动画时间
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//设置动画为无线循环
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx=(Integer) animation.getAnimatedValue();//获取动画进度
                postInvalidate();//重回界面
            }
        });
        valueAnimator.start();//执行动画
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
