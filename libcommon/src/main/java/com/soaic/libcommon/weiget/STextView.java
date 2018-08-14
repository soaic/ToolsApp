package com.soaic.libcommon.weiget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 1、DrawableImage上下居中显示控制
 */
public class STextView extends AppCompatTextView {
    //TextView Drawable Image 上下居中显示, 默认置顶显示
    private boolean isDrawableAlignCenter = false;
    private int mWidth, mHeight;

    public STextView(Context context) {
        super(context);
    }

    public STextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public STextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawableAlignCenter(boolean isAlignCenter){
        this.isDrawableAlignCenter = isAlignCenter;
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        mWidth = w;
        mHeight = h;
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        Drawable drawableTop = drawables[1];
        Drawable drawableRight = drawables[2];
        Drawable drawableBottom = drawables[3];
        if (drawableLeft != null) {
            setDrawable(drawableLeft, 0);
        }
        if (drawableTop != null) {
            setDrawable(drawableTop, 1);
        }
        if (drawableRight != null) {
            setDrawable(drawableRight, 2);
        }
        if (drawableBottom != null) {
            setDrawable(drawableBottom, 3);
        }
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    private void setDrawable(Drawable drawable, int tag) {
        //获取图片实际长宽
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        int left = 0, top = 0, right = 0, bottom = 0;
        switch (tag) {
            case 0:
            case 2:
                left = 0;
                top = isDrawableAlignCenter ? 0 : -getLineCount() * getLineHeight() / 2 + getLineHeight() / 2;
                right = width;
                bottom = top + height;
                break;
            case 1:
                left = isDrawableAlignCenter ? 0 : -mWidth / 2 + width / 2;
                top = 0;
                right = left + width;
                bottom = top + height;
                break;
        }
        drawable.setBounds(left, top, right, bottom);
    }
}
