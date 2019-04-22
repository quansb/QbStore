package com.example.quansb.qbstore.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.mysdk.util.ViewCommonUtils;

public class MyPassWordView extends View {
    private Paint linePaint;   //线笔
    private Paint circlePaint;     //圆笔
    private int linePaintColor;
    private int circlePaintColor;
    private int leftMargin;
    private int rightMargin;
    private int viewHeight;  //控件高度
    private int lineWidth;   //控件边的宽度
    private Context mContext;
    private int itemWidth;          //每个格子的宽度
    private int passWordNum;
    private int topMargin;          //距离顶部的margin
    private int circleSum;    //圆的数量
    private float circleRadius;     //圆的半径

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public void setPassWordNum(int passWordNum) {
        this.passWordNum = passWordNum;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public MyPassWordView(Context context) {
        this(context,null);
    }

    public MyPassWordView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public MyPassWordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (mContext==null){
            return;
        }
        initPaint();
    }
    public void setContext(Context context){
        mContext=context;
        initPaint();
        invalidate();
    }

    private void initPaint() {
        leftMargin= ViewCommonUtils.dipToPx(mContext,10);
        rightMargin=ViewCommonUtils.dipToPx(mContext,10);
        lineWidth =ViewCommonUtils.dipToPx(mContext,1);
        viewHeight =ViewCommonUtils.dipToPx(mContext,50);
        itemWidth =(ViewCommonUtils.getScreenWidth(mContext)-leftMargin-rightMargin)/passWordNum;
        topMargin=ViewCommonUtils.dipToPx(mContext,20);
        circleRadius =ViewCommonUtils.dipToPx(mContext,10);
        linePaint =new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setColor(linePaintColor);
        circlePaint =new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(circlePaintColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
         canvas.drawRect(leftMargin,topMargin, ViewCommonUtils.getScreenWidth(mContext)-rightMargin, viewHeight+topMargin, linePaint);
            drawLine(canvas);
            drawCircle(circleSum,canvas);
    }

    private void drawCircle(int num,Canvas canvas) {
        for(int i=0;i<num;i++){
        canvas.drawCircle(leftMargin+(itemWidth*i+itemWidth/2),viewHeight/2+topMargin, circleRadius, circlePaint);
        }
    }

    private void drawLine(Canvas canvas) {
        for(int i=0;i<passWordNum-1;i++){
            canvas.drawLine(leftMargin+ itemWidth *(i+1),topMargin,leftMargin+ itemWidth *(i+1),topMargin+ viewHeight, linePaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize=getHeightSize(heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),heightSize);

    }

    private int getHeightSize(int heightMeasureSpec) {
        int mode=MeasureSpec.getMode(heightMeasureSpec);
        int size=MeasureSpec.getSize(heightMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                case MeasureSpec.AT_MOST:
            break;
            case MeasureSpec.UNSPECIFIED:
                size=topMargin+viewHeight;
                break;
        }
        return size;
    }

    /**
     *
     * @param i 设置 笔的颜色
     */
    public void setCircleColor(int i){
       circlePaintColor=i;
    }

    public void setLineColor(int i){
        linePaintColor =i;
    }


    /**
     *
     * @param i  添加圆的数量
     */
    public void addCircle(int i){
        circleSum =i;
        invalidate();
    }


}
