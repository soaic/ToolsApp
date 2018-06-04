package com.soaic.libcommon.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ListDividerItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 分割线颜色
     */
    private int mDividerColor;

    /**
     * 分割线高度
     */
    private int mDividerHeight;

    /**
     * 距离左边
     */
    private int mMarginLeft;

    /**
     * 距离右边
     */
    private int mMarginRight;

    //是否绘制最后一行
    private boolean mIsDrawLastLine = false;

    private Paint mPaint;

    private int startPosition = 0;

    public ListDividerItemDecoration() {
        this(Color.parseColor("#E5E6E6"), 2, 0, 0);
    }

    public ListDividerItemDecoration(int marginLeft) {
        this(Color.parseColor("#E5E6E6"), 2, marginLeft, 0);
    }

    public ListDividerItemDecoration(int marginLeft, int marginRight) {
        this(Color.parseColor("#E5E6E6"), 2, marginLeft, marginRight);
    }

    public ListDividerItemDecoration(int dividerColor, int dividerHeight, int marginLeft, int marginRight) {
        this(dividerColor, dividerHeight, marginLeft, marginRight, 0);
    }

    public ListDividerItemDecoration(int dividerColor, int dividerHeight, int marginLeft, int marginRight, int startPosition) {
        mDividerColor = dividerColor;
        mDividerHeight = dividerHeight;
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mDividerColor);

        this.startPosition = startPosition;
    }

    public void drawLastLine(boolean isDrawLastLine) {
        mIsDrawLastLine = isDrawLastLine;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount() - (mIsDrawLastLine ? 0 : 1);

        for (int i = startPosition; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int left = child.getLeft() + mMarginLeft;
            final int right = child.getRight() - mMarginRight;
            final int top = child.getBottom();
            final int bottom = top + mDividerHeight;

            onDrawDivider(c, left, top, right, bottom, child);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDividerHeight);
    }

    private void onDrawDivider(Canvas c, int left, int top, int right, int bottom, View child) {
        c.drawRect(left, top, right, bottom, mPaint);
    }

}
