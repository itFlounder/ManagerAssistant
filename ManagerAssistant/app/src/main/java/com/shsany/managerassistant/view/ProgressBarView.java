package com.shsany.managerassistant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shsany.managerassistant.R;

/**
 * Created by PC on 2017/10/24.
 */

public class ProgressBarView extends View {
    //画笔
    private Paint paint;
    //圆环的颜色
    private int roundColor;
    //圆环进度的颜色
    private int roundProgressColor;
    //中间进度百分比字符串的颜色
    private int textColor;
    //中间进度百分比字符串的字体
    private float textSize;
    //圆环的宽度
    private float roundWidth;
    //最大进度
    private int max;
    //当前进度
    private int progress;
    //是否显示中间的进度
    private boolean textIsDisplayable;
    //进度的风格（实心还是空心）
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public ProgressBarView(Context context) {
        this(context,null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);

    }

    public void init(Context context,@Nullable AttributeSet attrs){
        paint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);

        roundColor =mTypedArray.getColor(R.styleable.ProgressBarView_roundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.ProgressBarView_roundProgressColor,Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.ProgressBarView_textColor,Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.ProgressBarView_textSize,15);
        roundWidth = mTypedArray.getDimension(R.styleable.ProgressBarView_roundWidth,30);
        max = mTypedArray.getInteger(R.styleable.ProgressBarView_max,100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.ProgressBarView_textIsDisplayable,true);
        style = mTypedArray.getInt(R.styleable.ProgressBarView_style,0);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centreX = getWidth()/2;//获取圆心的x坐标
        int centreY = getHeight()/2;//获取圆心的y坐标
        int radius = (int)(centreX - roundWidth/2);//圆环的半径
        /**
         * 画最外层的圆环
         * 当画圆弧时：
         * public void drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         * startAngle-弧线开始绘制时的角度
         *sweepAngle-弧线顺时针旋转的角度
         *useCenter-如果为true，绘制的起点和终点会和圆心相连
         *paint-绘制弧线的画笔
         */
        /*paint.setColor(roundColor);//设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(roundWidth);//设置圆环的宽度
        paint.setAntiAlias(true);//抗锯齿
        canvas.drawCircle(centreX,centreY,radius,paint);//画圆环*/
        paint.setColor(roundColor);//设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(roundWidth);//设置圆环的宽度
        paint.setAntiAlias(true);//抗锯齿
        RectF oval1 = new RectF(centreX - radius,centreY - radius,centreX + radius,centreY + radius);
        canvas.drawArc(oval1,135,270,false,paint);

        /**
         * 画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体
        int percent = (int)(((float)progress / (float)max) * 100);////中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(percent + "&");//测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        if (textIsDisplayable && percent != 0 && style == STROKE){
            canvas.drawText(percent + "%",centreX - textWidth/2 ,centreY + textSize/2,paint);//画出进度百分比
        }

        /**
         * 画圆弧，画圆环的进度
         */
        paint.setStrokeWidth(roundWidth-5);
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(centreX - radius,centreY - radius,centreX + radius,centreY + radius);
        switch (style){
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);
//                canvas.drawArc(oval,0,360*progress/max,false,paint);
                canvas.drawArc(oval,135,225,false,paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0){
//                    canvas.drawArc(oval,0,360*progress/max,true,paint);
                    canvas.drawArc(oval,135,225,true,paint);
                }
                break;
        }
    }
    public synchronized int getMax(){
        return  max;
    }

    public synchronized void setMax(int max){
        if (max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度，需要同步
     * @return
     */
    public synchronized int getProgress(){
        return progress;
    }

    /**
     *  设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param progress

     */
    public synchronized void setProgress(int progress){
        if (progress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }else if (progress > max){
            progress = max;
        }else {
            this.progress = progress;
            postInvalidate();
        }
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getRoundProgressColor() {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }



}
