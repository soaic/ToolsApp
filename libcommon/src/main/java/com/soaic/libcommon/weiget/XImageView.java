package com.soaic.libcommon.weiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.soaic.libcommon.R;

/**
 * 设置宽高比
 */
public class XImageView extends AppCompatImageView {
    //宽高比，以宽度为基准，高度乘上比率
    private float mAspectRatio = 0;

    public XImageView(Context context) {
        this(context,null);
    }

    public XImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.XImageView);
        if(typedArray != null) {
            if (typedArray.hasValue(R.styleable.XImageView_viewAspectRatio)) {
                mAspectRatio = typedArray.getFloat(R.styleable.XImageView_viewAspectRatio,0);
            }
            typedArray.recycle();
        }
    }

    public void setAspectRatio(float aspectRatio){
        if(mAspectRatio != 0) {
            mAspectRatio = aspectRatio;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mAspectRatio != 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width / mAspectRatio);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
