package com.mysdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.mysdk.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by fanjh on 2016/4/29.
 * 流动式布局，可以应用于标签等从左到右排列的布局，会自动向下排列
 * update by fanjh on 2016/8/18
 * 修改部分测量BUG，同时添加显示行数的控制，测量和布局分开写是为了更好理解和修改
 * updaye by fanjh on 2016/8/22
 * 添加左右对齐的排列方式
 * updaye by zhengty on 2016/8/30
 * 增加获取行总数的类，增加setAdapter方法（可以设置单选，多选，单击三种方案）
 * updaye by fanjh on 2018/4/3
 * 添加固定展示某一个view在布局的最后（只要showLines>=1则必然展示在最后）
 */
public class MyFlowLayout extends ViewGroup {
    //代表左边边距相同的对齐方式，右边距不一定相同,左边距通过设置paddingLeft实现
    public static final int LEFT = 0;
    //每一行都进行中央对齐
    public static final int CENTER = 1;
    //按照给定的间距进行排列，右边距不一定相同
    public static final int NORMAL = 2;
    //按照左右对齐的方式进行排列，每一行子视图之间的距离不一定相同
    public static final int HORIZONTALALIGN = 3;
    //从右往左排
    public static final int RIGHT = 4;
    // 当width为wrap时，能正常wrap而不是match_parent
    public static final int WRAP_CENTER = 6;
    /**
     * 必须展示最后一个视图，哪怕行数被限制了
     */
    public static final int SHOW_LAST = 5;
    //记录每一行的行高
    private SparseArray<Integer> heights = new SparseArray<>();
    //记录每行的孩子下标
    private SparseArray<ArrayList<Integer>> indexs = new SparseArray<>();
    //记录每一行的行宽
    private SparseArray<Integer> widths = new SparseArray<>();
    //区分类型<enum name="left" value="0"/><enum name="center" value="1"/><enum name="normal" value="2"/><enum name="horizontalAlign" value="3"/>
    private int type;
    //总高度
    private int totalHeight = 0;
    //总行数
    private int lines = 0;
    //行间距
    private int line_divider = 0;
    //每个元素之间的间距，仅当type=normal时有效
    // v6.1.8 新增 对type=wrap_center有效
    private int padding_divider = 0;
    //每一行的固定左边距，仅当type=left时有效
    private int start_padding = 0;
    //显示的行数
    private int showLines = -1;

    public static final int FLOW_TAG_CHECKED_NONE = 0;
    /**
     * FlowLayout support single-select
     */
    public static final int FLOW_TAG_CHECKED_SINGLE = 1;
    /**
     * FlowLayout support multi-select
     */
    public static final int FLOW_TAG_CHECKED_MULTI = 2;

    /**
     * Should be used by subclasses to listen to changes in the dataSet
     */
    AdapterDataSetObserver mDataSetObserver;

    /**
     * The adapter containing the data to be displayed by this view
     */
    ListAdapter mAdapter;

    /**
     * the tag click event callback
     */
    OnTagClickListener mOnTagClickListener;

    /**
     * the tag select event callback
     */
    OnTagSelectListener mOnTagSelectListener;

    /**
     * 标签流式布局选中模式，默认是不支持选中的
     */
    private int mTagCheckMode = FLOW_TAG_CHECKED_NONE;

    /**
     * 存储选中的tag
     */
    private SparseBooleanArray mCheckedTagArray = new SparseBooleanArray();

    public interface OnInitSelectedPosition {
        boolean isSelectedPosition(int position);
    }

    public interface OnTagClickListener {
        void onItemClick(MyFlowLayout parent, View view, int position);
    }

    public interface OnTagSelectListener {
        void onItemSelect(MyFlowLayout parent, List<Integer> selectedList);
    }

    public MyFlowLayout(Context context) {
        this(context,null);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyFlowLayout,defStyleAttr,0);
        type = typedArray.getInt(R.styleable.MyFlowLayout_type,NORMAL);
        padding_divider = typedArray.getDimensionPixelSize(R.styleable.MyFlowLayout_padding_divider, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics()));
        line_divider = typedArray.getDimensionPixelSize(R.styleable.MyFlowLayout_line_divider,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics()));
        showLines = typedArray.getInt(R.styleable.MyFlowLayout_showLines,-1);
        typedArray.recycle();
    }

    /**
     * 设置ViewGroup支持Layout_Margin相关属性
     *
     * @param attrs
     * @return MarginLayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(), attrs);
    }

    /**
     * 是否继续测量行高，用于“更多”
     * @param lines 当前行数
     * @return
     */
    private boolean isShowLine(int lines){
        if(-1 == showLines){
            return true;
        }
        return showLines >= (lines+1);
    }

    /**
     * 显示所有行数
     */
    public void allShow(){
        showLines = lines;
        invalidate();
        requestLayout();
    }

    public void setShowLine(int lines) {
        showLines = lines;
        invalidate();
        requestLayout();
    }

    public int getCountLine(){
       return lines;
    }

    /**
     * 设置行间距
     * @param lineDividerHeight 行间距
     */
    public void setLineDivider(int lineDividerHeight) {
        this.line_divider = lineDividerHeight;
        requestLayout();
        invalidate();
    }

    /**
     * 设置标签间间距
     * @param paddingDivider 标签间间距
     */
    public void setPaddingDivider(int paddingDivider) {
        this.padding_divider = paddingDivider;
        requestLayout();
        invalidate();
    }

    public int getPaddingDivider() {
        return padding_divider;
    }

    /**
     * 按照正常间距放置的测量过程
     * @param widthMeasureSpec ViewGroup的宽测量标准
     * @param heightMeasureSpec ViewGroup的高测量标准
     */
    private void onNormalMeasure(int widthMeasureSpec,int heightMeasureSpec){
        initMeasure();
        int layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        layoutHeight -= (getPaddingBottom() + getPaddingTop());
        layoutWidth -= (getPaddingLeft() + getPaddingRight());
        int lastLeft = 0;//当前行宽
        int tempLineHeight = 0;//记录当前行高,指的是当前行孩子高度的最大值
        ArrayList<Integer> integers = new ArrayList<>();//用于存放当前行的视图下标
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获得当前View的宽和高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth > layoutWidth){
                return ;
            }else{
                if(lastLeft + childWidth > layoutWidth){//换行时
                    heights.put(lines,tempLineHeight);//记录当前行高度
                    indexs.put(lines,integers);//记录当前所有的孩子下标
                    if (isShowLine(lines)) {
                        totalHeight += tempLineHeight;//所有行孩子的总高度
                    }
                    lines++;//行+1,相当于换行之后
                    if(isShowLine(lines)){
                        totalHeight += line_divider;
                    }
                    tempLineHeight = 0;//重新计算当前行高
                    integers = new ArrayList<>();//重新设置当前行的孩子下标
                    lastLeft = 0;
                    //lastLeft = getPaddingLeft();
                }
                lastLeft += childWidth;//将孩子放上当前行
                lastLeft += padding_divider;//设置孩子之间的行间距
                if(childHeight > tempLineHeight){//若果当前孩子的高度比当前行之前的高度高，更新当前行高
                    tempLineHeight = childHeight;
                }
                integers.add(i);//记录当前行的孩子下标
            }
        }
        handleLastLine(tempLineHeight,lastLeft,integers,layoutWidth);
        if(layoutHeight == 0){//如果设置行高为零。默认使用所有孩子放置后的高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension((layoutWidthMode != MeasureSpec.EXACTLY ? layoutWidth : widthMeasureSpec),
                (layoutHeightMode != MeasureSpec.EXACTLY ? totalHeight: heightMeasureSpec));
    }

    /**
     * 按照正常间距放置的放置过程
     */
    private void onNormalLayout(){
        int lastLeft;
        int lineHeight;
        int tempLineHeight = getPaddingTop();
        ArrayList<Integer> childIndexs = new ArrayList<>();
        for(int i=0;i<lines;++i){//按行的顺序放置孩子
            lastLeft = getPaddingLeft();
            lineHeight = heights.get(i);
            childIndexs.clear();
            childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
            for(int j=0;j<childIndexs.size();++j){//遍历放置每一个孩子
                View child = getChildAt(childIndexs.get(j));//获得孩子
                child.layout(lastLeft,tempLineHeight,lastLeft + child.getMeasuredWidth(),tempLineHeight + child.getMeasuredHeight());
                lastLeft += (child.getMeasuredWidth() + padding_divider);
            }
            tempLineHeight += (lineHeight + line_divider);
        }
    }

    /**
     * 按照中央对齐的方式放置的测量过程，注意此时设置的padding_divider是无效的
     * @param widthMeasureSpec ViewGroup的宽测量标准
     * @param heightMeasureSpec ViewGroup的高测量标准
     */
    private void onCenterMeasure(int widthMeasureSpec,int heightMeasureSpec){
        initMeasure();
        int layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        layoutHeight -= (getPaddingBottom() + getPaddingTop());
        layoutWidth -= (getPaddingLeft() + getPaddingRight());
        int lastLeft = 0;//当前行宽
        int tempLineHeight = 0;//记录当前行高,指的是当前行孩子高度的最大值
        ArrayList<Integer> integers = new ArrayList<>();//用于存放当前行的视图下标
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获得当前View的宽和高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth > layoutWidth){
                return ;
            }else{
                if(lastLeft + childWidth > layoutWidth){//换行时
                    widths.put(lines,lastLeft);
                    heights.put(lines,tempLineHeight);//记录当前行高度
                    indexs.put(lines,integers);//记录当前所有的孩子下标
                    if(isShowLine(lines)) {
                        totalHeight += tempLineHeight;//所有行孩子的总高度
                    }
                    lines++;//行+1,相当于换行之后
                    lastLeft = 0;
                    //lastLeft = getPaddingLeft();//从下一行重新排列
                    if(isShowLine(lines) && lines >= 1){//如果已经是第二行或更多行
                        totalHeight += line_divider;//加上行边距
                    }
                    tempLineHeight = 0;//重新计算当前行高
                    integers = new ArrayList<>();//重新设置当前行的孩子下标
                }
                lastLeft += childWidth;//将孩子放上当前行
                if(childHeight > tempLineHeight){//若果当前孩子的高度比当前行之前的高度高，更新当前行高
                    tempLineHeight = childHeight;
                }
                integers.add(i);//记录当前行的孩子下标
            }
        }
        handleLastLine(tempLineHeight,lastLeft,integers,layoutWidth);
        if(layoutHeight == 0){//如果设置行高为零。默认使用所有孩子放置后的高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension((layoutWidthMode != MeasureSpec.EXACTLY ? layoutWidth : widthMeasureSpec),
                (layoutHeightMode != MeasureSpec.EXACTLY ? totalHeight: heightMeasureSpec));
    }

    /**
     * 按照中央对齐放置的放置过程
     */
    private void onCenterLayout(){
        int lastLeft;
        int lineHeight;
        int lineWidth;
        int childCount;
        int tempLineHeight = getPaddingTop();
        ArrayList<Integer> childIndexs = new ArrayList<>();
        for(int i=0;i<lines;++i){//按行的顺序放置孩子
            lineHeight = heights.get(i);
            lineWidth = widths.get(i);
            childIndexs.clear();
            childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
            childCount = childIndexs.size();
            padding_divider = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - lineWidth)/(childCount+1);
            lastLeft = getPaddingLeft() + padding_divider;
            for(int j=0;j<childCount;++j){//遍历放置每一个孩子
                View child = getChildAt(childIndexs.get(j));//获得孩子
                child.layout(lastLeft,tempLineHeight,lastLeft + child.getMeasuredWidth(),tempLineHeight + child.getMeasuredHeight());
                lastLeft += (child.getMeasuredWidth() + padding_divider);
            }
            tempLineHeight += (lineHeight + line_divider);
        }
    }

    /**
     * 按照左右对齐放置的测量过程，这个过程只要求水平距离可以放得下即可，间距在layout中处理
     * @param widthMeasureSpec ViewGroup的宽测量标准
     * @param heightMeasureSpec ViewGroup的高测量标准
     */
    private void onHorizontalAlignMeasure(int widthMeasureSpec,int heightMeasureSpec){
        initMeasure();
        int layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        layoutHeight -= (getPaddingBottom() + getPaddingTop());
        layoutWidth -= (getPaddingLeft() + getPaddingRight());
        int lastLeft = 0;//当前行宽
        int tempLineHeight = 0;//记录当前行高,指的是当前行孩子高度的最大值
        ArrayList<Integer> integers = new ArrayList<>();//用于存放当前行的视图下标
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获得当前View的宽和高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth > layoutWidth){
                return ;
            }else{
                if(lastLeft + childWidth > layoutWidth){//换行时
                    widths.put(lines,lastLeft);//记录当前行宽
                    heights.put(lines,tempLineHeight);//记录当前行高度
                    indexs.put(lines,integers);//记录当前所有的孩子下标
                    if (isShowLine(lines)) {
                        totalHeight += tempLineHeight;//所有行孩子的总高度
                    }
                    lines++;//行+1,相当于换行之后
                    if(isShowLine(lines)){
                        totalHeight += line_divider;
                    }
                    tempLineHeight = 0;//重新计算当前行高
                    integers = new ArrayList<>();//重新设置当前行的孩子下标
                    lastLeft = 0;
                    //lastLeft = getPaddingLeft();
                }
                lastLeft += childWidth;//将孩子放上当前行
                if(childHeight > tempLineHeight){//若果当前孩子的高度比当前行之前的高度高，更新当前行高
                    tempLineHeight = childHeight;
                }
                integers.add(i);//记录当前行的孩子下标
            }
        }
        handleLastLine(tempLineHeight,lastLeft,integers,layoutWidth);
        if(layoutHeight == 0){//如果设置行高为零。默认使用所有孩子放置后的高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension((layoutWidthMode != MeasureSpec.EXACTLY ? layoutWidth : widthMeasureSpec),
                (layoutHeightMode != MeasureSpec.EXACTLY ? totalHeight: heightMeasureSpec));
    }

    /**
     * 按照左右对齐的方式放置的放置过程，这个过程需要处理每一行孩子视图的间距
     */
    private void onHorizontalAlignLayout(){
        int lastLeft;
        int lineHeight;
        int tempLineHeight = getPaddingTop();
        ArrayList<Integer> childIndexs = new ArrayList<>();
        for(int i=0;i<lines;++i){//按行的顺序放置孩子
            lastLeft = getPaddingLeft();
            lineHeight = heights.get(i);
            childIndexs.clear();
            childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
            int count = childIndexs.size();
            if(1 < count) {
                padding_divider = (getMeasuredWidth() - widths.get(i) - getPaddingRight() - getPaddingLeft()) / (count - 1);
            }
            for(int j=0;j<count;++j){//遍历放置每一个孩子
                View child = getChildAt(childIndexs.get(j));//获得孩子
                child.layout(lastLeft,tempLineHeight,lastLeft + child.getMeasuredWidth(),tempLineHeight + child.getMeasuredHeight());
                lastLeft += (child.getMeasuredWidth() + padding_divider);
            }
            tempLineHeight += (lineHeight + line_divider);
        }
    }

    /**
     * 按照左对齐的方式放置的测量过程，严格说按照第一行居中的排列，后续都采用同等左边距,padding_divider无效
     * @param widthMeasureSpec ViewGroup的宽测量标准
     * @param heightMeasureSpec ViewGroup的高测量标准
     */
    private void onLeftMeasure(int widthMeasureSpec,int heightMeasureSpec){
        initMeasure();
        int layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        layoutHeight -= (getPaddingBottom() + getPaddingTop());
        layoutWidth -= (getPaddingLeft() + getPaddingRight());
        int lastLeft = 0;//当前行宽
        int tempLineHeight = 0;//记录当前行高,指的是当前行孩子高度的最大值
        ArrayList<Integer> integers = new ArrayList<>();//用于存放当前行的视图下标
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获得当前View的宽和高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth > layoutWidth){
                return ;
            }else{
                if(lastLeft + childWidth > layoutWidth){//换行时
                    widths.put(lines,lastLeft);
                    heights.put(lines,tempLineHeight);//记录当前行高度
                    indexs.put(lines,integers);//记录当前所有的孩子下标
                    if(isShowLine(lines)) {
                        totalHeight += tempLineHeight;//所有行孩子的总高度
                    }
                    lines++;//行+1,相当于换行之后
                    if(isShowLine(lines) && lines >= 1){//如果已经是第二行或更多行
                        totalHeight += line_divider;//加上行边距
                    }
                    if(lines == 1){
                        //计算之后都将采用的左边距
                        start_padding = (layoutWidth - getPaddingRight() - getPaddingLeft() - lastLeft)/(integers.size()+1);
                    }
                    //lastLeft = getPaddingLeft();//从下一行重新排列
                    lastLeft = 0;
                    tempLineHeight = 0;//重新计算当前行高
                    integers = new ArrayList<>();//重新设置当前行的孩子下标
                    lastLeft += start_padding;//除第一行外，其他行初始都应该加上固定左边距
                }
                lastLeft += childWidth;//将孩子放上当前行
                if(childHeight > tempLineHeight){//若果当前孩子的高度比当前行之前的高度高，更新当前行高
                    tempLineHeight = childHeight;
                }
                integers.add(i);//记录当前行的孩子下标
            }
        }
        handleLastLine(tempLineHeight,lastLeft,integers,layoutWidth);
        if(lines == 1){
            //计算之后都将采用的左边距
            start_padding = (layoutWidth - getPaddingRight() - getPaddingLeft() - lastLeft)/(integers.size()+1);
        }
        if(layoutHeight == 0){//如果设置行高为零。默认使用所有孩子放置后的高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension((layoutWidthMode != MeasureSpec.EXACTLY ? layoutWidth : widthMeasureSpec),
                (layoutHeightMode != MeasureSpec.EXACTLY ? totalHeight: heightMeasureSpec));
    }

    /**
     * 按照左对齐的方式放置的放置过程，严格说按照第一行居中的排列，后续都采用同等左边
     */
    private void onLeftLayout(){
        int lastLeft;
        int lineHeight;
        int lineWidth;
        int childCount;
        int tempLineHeight = getPaddingTop();
        ArrayList<Integer> childIndexs = new ArrayList<>();
        for(int i=0;i<lines;++i){//按行的顺序放置孩子
            lineHeight = heights.get(i);
            lineWidth = widths.get(i);
            childIndexs.clear();
            childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
            childCount = childIndexs.size();
            if(childCount != 0 ) {
                padding_divider = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - lineWidth) / (childCount);
            }else{
                continue;
            }
            if(i == 0){
                padding_divider = start_padding;
            }
            lastLeft = start_padding + getPaddingLeft();
            for(int j=0;j<childCount;++j){//遍历放置每一个孩子
                View child = getChildAt(childIndexs.get(j));//获得孩子
                child.layout(lastLeft,tempLineHeight,lastLeft + child.getMeasuredWidth(),tempLineHeight + child.getMeasuredHeight());
                lastLeft += child.getMeasuredWidth() + padding_divider;
            }
            tempLineHeight += (lineHeight + line_divider);
        }
    }

    /**
     * 按照右对齐的方式放置的放置过程
     */
    private void onRightLayout(){
        int lastRight;
        int lineHeight;
        int tempLineHeight = getPaddingTop();
        ArrayList<Integer> childIndexs = new ArrayList<>();
        for(int i=0;i<lines;++i){//按行的顺序放置孩子
            lastRight = getMeasuredWidth() - getPaddingRight();
            lineHeight = heights.get(i);
            childIndexs.clear();
            childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
            for(int j=0;j<childIndexs.size();++j){//遍历放置每一个孩子
                View child = getChildAt(childIndexs.get(j));//获得孩子
                child.layout(lastRight - child.getMeasuredWidth(),tempLineHeight,lastRight,tempLineHeight + child.getMeasuredHeight());
                lastRight -= (child.getMeasuredWidth() + padding_divider);
            }
            tempLineHeight += (lineHeight + line_divider);
        }
    }

    /**
     * 正常来说直接按照正常间距放置的测量过程就好
     * 不过如果最后一个被强行替换了，那么要重新考虑高度问题
     * @param widthMeasureSpec ViewGroup的宽测量标准
     * @param heightMeasureSpec ViewGroup的高测量标准
     */
    private void onShowLastMeasure(int widthMeasureSpec,int heightMeasureSpec){
        initMeasure();
        int layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        layoutHeight -= (getPaddingBottom() + getPaddingTop());
        layoutWidth -= (getPaddingLeft() + getPaddingRight());
        int lastLeft = 0;//当前行宽
        int tempLineHeight = 0;//记录当前行高,指的是当前行孩子高度的最大值
        ArrayList<Integer> integers = new ArrayList<>();//用于存放当前行的视图下标
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获得当前View的宽和高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth > layoutWidth){
                return ;
            }else{
                if(lastLeft + childWidth > layoutWidth){//换行时
                    heights.put(lines,tempLineHeight);//记录当前行高度
                    indexs.put(lines,integers);//记录当前所有的孩子下标
                    if(showLines > 0 && lines == showLines - 1){
                        //处理一下被替换后的高度
                        View lastView = getChildAt(getChildCount() - 1);
                        measureChild(lastView, widthMeasureSpec, heightMeasureSpec);
                        if(integers.size() > 0){
                            View replaceView = getChildAt(integers.get(integers.size() - 1));
                            if(replaceView.getMeasuredHeight() == tempLineHeight){
                                //被替换了那么高度就不做数了，重新算一遍
                                int maxHeight = 0;
                                int tempHeight;
                                for(int k = 0;k < integers.size() - 1;++k){
                                    tempHeight = getChildAt(integers.get(k)).getMeasuredHeight();
                                    if(tempHeight > maxHeight){
                                        maxHeight = tempHeight;
                                    }
                                }
                                if(lastView.getMeasuredHeight() > maxHeight){
                                    maxHeight = lastView.getMeasuredHeight();
                                }
                                tempLineHeight = maxHeight;
                                totalHeight += tempLineHeight;
                            }else{
                                if (lastView.getMeasuredHeight() > tempLineHeight) {
                                    tempLineHeight = lastView.getMeasuredHeight();
                                }
                                totalHeight += tempLineHeight;
                            }
                        }else {
                            measureChild(lastView, widthMeasureSpec, heightMeasureSpec);
                            if (lastView.getMeasuredHeight() > tempLineHeight) {
                                tempLineHeight = lastView.getMeasuredHeight();
                            }
                            totalHeight += tempLineHeight;
                        }
                    }else if (isShowLine(lines)) {
                        //否则按照正常方式处理
                        totalHeight += tempLineHeight;//所有行孩子的总高度
                    }
                    lines++;//行+1,相当于换行之后
                    if(isShowLine(lines)){
                        totalHeight += line_divider;
                    }
                    tempLineHeight = 0;//重新计算当前行高
                    integers = new ArrayList<>();//重新设置当前行的孩子下标
                    lastLeft = 0;
                    //lastLeft = getPaddingLeft();
                }
                lastLeft += childWidth;//将孩子放上当前行
                lastLeft += padding_divider;//设置孩子之间的行间距
                if(childHeight > tempLineHeight){//若果当前孩子的高度比当前行之前的高度高，更新当前行高
                    tempLineHeight = childHeight;
                }
                integers.add(i);//记录当前行的孩子下标
            }
        }
        handleLastLine(tempLineHeight,lastLeft,integers,layoutWidth);
        if(layoutHeight == 0){//如果设置行高为零。默认使用所有孩子放置后的高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension((layoutWidthMode != MeasureSpec.EXACTLY ? layoutWidth : widthMeasureSpec),
                (layoutHeightMode != MeasureSpec.EXACTLY ? totalHeight: heightMeasureSpec));
    }

    /**
     * 固定一个视图展示在最后面
     */
    private void onShowLastLayout(){
        if(showLines == -1 || lines <= showLines){
            onNormalLayout();
        }else{
            int lastLeft;
            int lineHeight;
            int tempLineHeight = getPaddingTop();
            ArrayList<Integer> childIndexs = new ArrayList<>();
            for(int i=0;i<showLines;++i){//按行的顺序放置孩子
                if(i == showLines - 1){
                    View lastView = getChildAt(getChildCount() - 1);
                    lastLeft = getPaddingLeft();
                    lineHeight = heights.get(i);
                    childIndexs.clear();
                    childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
                    for (int j = 0; j < childIndexs.size(); ++j) {//遍历放置每一个孩子
                        View child = getChildAt(childIndexs.get(j));//获得孩子
                        if(child != lastView && lastLeft + child.getMeasuredWidth() + padding_divider + lastView.getMeasuredWidth() > getMeasuredWidth() - getPaddingRight()){
                            //因为最后一个视图必须放下，所以如果因为当前元素的原因导致放不下，
                            //那么放弃放置当前元素，直接放置最后一个视图返回
                            lastView.layout(lastLeft, tempLineHeight, lastLeft + lastView.getMeasuredWidth(), tempLineHeight + lastView.getMeasuredHeight());
                            return;
                        }else {
                            child.layout(lastLeft, tempLineHeight, lastLeft + child.getMeasuredWidth(), tempLineHeight + child.getMeasuredHeight());
                            lastLeft += (child.getMeasuredWidth() + padding_divider);
                        }
                        if(j == childIndexs.size() - 1){
                            lastView.layout(lastLeft, tempLineHeight, lastLeft + lastView.getMeasuredWidth(), tempLineHeight + lastView.getMeasuredHeight());
                        }
                    }
                    tempLineHeight += (lineHeight + line_divider);
                }else {
                    lastLeft = getPaddingLeft();
                    lineHeight = heights.get(i);
                    childIndexs.clear();
                    childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
                    for (int j = 0; j < childIndexs.size(); ++j) {//遍历放置每一个孩子
                        View child = getChildAt(childIndexs.get(j));//获得孩子
                        child.layout(lastLeft, tempLineHeight, lastLeft + child.getMeasuredWidth(), tempLineHeight + child.getMeasuredHeight());
                        lastLeft += (child.getMeasuredWidth() + padding_divider);
                    }
                    tempLineHeight += (lineHeight + line_divider);
                }
            }
        }
    }

    /**
     * 按照中央包裹对齐的方式放置的测量过程
     * @param widthMeasureSpec ViewGroup的宽测量标准
     * @param heightMeasureSpec ViewGroup的高测量标准
     */
    private void onWrapCenterMeasure(int widthMeasureSpec,int heightMeasureSpec){
        initMeasure();
        int layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        layoutHeight -= (getPaddingBottom() + getPaddingTop());
        layoutWidth -= (getPaddingLeft() + getPaddingRight());
        int lastLeft = 0;//当前行宽
        int tempLineHeight = 0;//记录当前行高,指的是当前行孩子高度的最大值
        ArrayList<Integer> integers = new ArrayList<>();//用于存放当前行的视图下标
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获得当前View的宽和高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth > layoutWidth){
                return ;
            }else{
                if(lastLeft + childWidth > layoutWidth){//换行时
                    widths.put(lines,lastLeft);
                    heights.put(lines,tempLineHeight);//记录当前行高度
                    indexs.put(lines,integers);//记录当前所有的孩子下标
                    if(isShowLine(lines)) {
                        totalHeight += tempLineHeight;//所有行孩子的总高度
                    }
                    lines++;//行+1,相当于换行之后
                    lastLeft = 0;
                    //lastLeft = getPaddingLeft();//从下一行重新排列
                    if(isShowLine(lines) && lines >= 1){//如果已经是第二行或更多行
                        totalHeight += line_divider;//加上行边距
                    }
                    tempLineHeight = 0;//重新计算当前行高
                    integers = new ArrayList<>();//重新设置当前行的孩子下标
                }
                lastLeft += childWidth;//将孩子放上当前行
                lastLeft += padding_divider;//设置孩子之间的行间距
                if(childHeight > tempLineHeight){//若果当前孩子的高度比当前行之前的高度高，更新当前行高
                    tempLineHeight = childHeight;
                }
                integers.add(i);//记录当前行的孩子下标
            }
        }
        handleLastLine(tempLineHeight,lastLeft,integers,layoutWidth);
        if(layoutHeight == 0){//如果设置行高为零。默认使用所有孩子放置后的高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension((layoutWidthMode != MeasureSpec.EXACTLY ? lastLeft : widthMeasureSpec),
                (layoutHeightMode != MeasureSpec.EXACTLY ? totalHeight : heightMeasureSpec));
    }

    /**
     * 按照中央包裹对齐放置的放置过程
     */
    private void onWrapCenterLayout(){
        int lastLeft;
        int lineHeight;
//        int lineWidth;
        int childCount;
        int tempLineHeight = getPaddingTop();
        ArrayList<Integer> childIndexs = new ArrayList<>();
        for(int i=0;i<lines;++i){//按行的顺序放置孩子
            lineHeight = heights.get(i);
//            lineWidth = widths.get(i);
            childIndexs.clear();
            childIndexs.addAll(indexs.get(i));//获得当前行的所有孩子下标
            childCount = childIndexs.size();
//            padding_divider = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - lineWidth)/(childCount+1);
            lastLeft = getPaddingLeft() + padding_divider;
            for(int j=0;j<childCount;++j){//遍历放置每一个孩子
                View child = getChildAt(childIndexs.get(j));//获得孩子
                child.layout(lastLeft,tempLineHeight,lastLeft + child.getMeasuredWidth(),tempLineHeight + child.getMeasuredHeight());
                lastLeft += (child.getMeasuredWidth() + padding_divider);
            }
            tempLineHeight += (lineHeight + line_divider);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        switch (type){
            case LEFT:
                onLeftMeasure(widthMeasureSpec,heightMeasureSpec);
                break;
            case CENTER:
                onCenterMeasure(widthMeasureSpec,heightMeasureSpec);
                break;
            case NORMAL:
                onNormalMeasure(widthMeasureSpec,heightMeasureSpec);
                break;
            case HORIZONTALALIGN:
                onHorizontalAlignMeasure(widthMeasureSpec,heightMeasureSpec);
                break;
            case RIGHT:
                onNormalMeasure(widthMeasureSpec,heightMeasureSpec);
                break;
            case SHOW_LAST:
                onShowLastMeasure(widthMeasureSpec,heightMeasureSpec);
                break;
            case WRAP_CENTER:
                onWrapCenterMeasure(widthMeasureSpec,heightMeasureSpec);
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        switch (type){
            case LEFT:
                onLeftLayout();
                break;
            case CENTER:
                onCenterLayout();
                break;
            case NORMAL:
                onNormalLayout();
                break;
            case HORIZONTALALIGN:
                onHorizontalAlignLayout();
                break;
            case RIGHT:
                onRightLayout();
                break;
            case SHOW_LAST:
                onShowLastLayout();
                break;
            case WRAP_CENTER:
                onWrapCenterLayout();
                break;
        }
    }

    /**
     * 处理最后一行刚好填充，这种一般需要最后处理
     * @param tempLineHeight 当前行高
     * @param lastLeft 当前行宽
     * @param integers
     * @param layoutWidth
     */
    private void handleLastLine(int tempLineHeight,int lastLeft,ArrayList<Integer> integers,int layoutWidth){
        if(tempLineHeight != 0){//如果当前行还没填满，孩子已经放完，需要记录最后一个孩子
            widths.put(lines,lastLeft);
            heights.put(lines,tempLineHeight);//记录行高
            indexs.put(lines,integers);
            if(isShowLine(lines)) {
                totalHeight += tempLineHeight;
            }
            lines++;
        }
    }

    /**
     * 准备开始测量
     */
    private void initMeasure(){
        widths.clear();
        heights.clear();
        indexs.clear();
        lines = 0;
        totalHeight = getPaddingTop() + getPaddingBottom();
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }


    /**
     * 重新加载刷新数据
     */
    private void reloadData() {
       
        removeAllViews();

        boolean isSetted = false;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int j = i;
            mCheckedTagArray.put(i, false);
            final View childView = mAdapter.getView(i, null, this);
            addView(childView, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            if (mAdapter instanceof OnInitSelectedPosition) {
                boolean isSelected = ((OnInitSelectedPosition) mAdapter).isSelectedPosition(i);
                //判断一下模式
                if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                    //单选只有第一个起作用
                    if (isSelected && !isSetted) {
                        mCheckedTagArray.put(i, true);
                        childView.setSelected(true);
                        isSetted = true;
                    }
                } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                    if (isSelected) {
                        mCheckedTagArray.put(i, true);
                        childView.setSelected(true);
                    }
                }
            }

            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTagCheckMode == FLOW_TAG_CHECKED_NONE) {
                        if (mOnTagClickListener != null) {
                            mOnTagClickListener.onItemClick(MyFlowLayout.this, childView, j);
                        }
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                        //判断状态
                        if (mCheckedTagArray.get(j)) {
                            //点击被选中的标签时，不做任何处理
//                            mCheckedTagArray.put(j, false);
//                            childView.setSelected(false);
//                            if (mOnTagSelectListener != null) {
//                                mOnTagSelectListener.onItemSelect(MyFlowLayout.this, new ArrayList<Integer>());
//                            }
                            return;
                        }

                        for (int k = 0; k < mAdapter.getCount(); k++) {
                            mCheckedTagArray.put(k, false);
                            getChildAt(k).setSelected(false);
                        }
                        mCheckedTagArray.put(j, true);
                        childView.setSelected(true);

                        if (mOnTagSelectListener != null) {
                            mOnTagSelectListener.onItemSelect(MyFlowLayout.this, Arrays.asList(j));
                        }
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                        if (mCheckedTagArray.get(j)) {
                            mCheckedTagArray.put(j, false);
                            childView.setSelected(false);
                        } else {
                            mCheckedTagArray.put(j, true);
                            childView.setSelected(true);
                        }
                        //回调
                        if (mOnTagSelectListener != null) {
                            List<Integer> list = new ArrayList<Integer>();
                            for (int k = 0; k < mAdapter.getCount(); k++) {
                                if (mCheckedTagArray.get(k)) {
                                    list.add(k);
                                }
                            }
                            mOnTagSelectListener.onItemSelect(MyFlowLayout.this, list);
                        }
                    }
                }
            });
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setOnTagSelectListener(OnTagSelectListener onTagSelectListener) {
        this.mOnTagSelectListener = onTagSelectListener;
    }

    /**
     * 像ListView、GridView一样使用FlowLayout
     */
    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        //清除现有的数据
        removeAllViews();
        mAdapter = adapter;

        if (mAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    /**
     * 获取标签模式
     */
    public int getmTagCheckMode() {
        return mTagCheckMode;
    }

    /**
     * 设置标签选中模式
     */
    public void setTagCheckedMode(int tagMode) {
        this.mTagCheckMode = tagMode;
    }
}
