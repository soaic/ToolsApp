package com.soaic.toolsapp.weight;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.soaic.toolsapp.R;

public class PullToRefreshHeaderView extends FrameLayout implements RefreshHeader {
    private ImageView mLoadingView;
    private ImageView mMovingView;
    private AnimationDrawable mProgressDrawable;
    private final int Count = 32;
    private int mHeaderHeight;
    private boolean mReleased;
    private int mCurImageIndex = 0;

    public PullToRefreshHeaderView(@NonNull Context context) {
        this(context, null, 0);
    }

    public PullToRefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_header_view, this, true);
        mLoadingView = findViewById(R.id.loadingView);
        mMovingView = findViewById(R.id.movingView);
        mMovingView.setImageResource(R.drawable.refresh_header_0);
        setBackgroundColor(0xff000000);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mHeaderHeight = height;
        mReleased = false;
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (!mReleased) {
            mMovingView.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.INVISIBLE);
            int extraHeight = offset - mHeaderHeight;
            if (extraHeight < 0) {
                extraHeight = 0;
            }
            int step = (extraHeight / 20) % Count;
            mCurImageIndex = step;
            int resId = getResources().getIdentifier("refresh_header_" + step, "drawable", getContext().getPackageName());
            mMovingView.setImageResource(resId);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        mReleased = true;
        initAnimation();
        mLoadingView.setBackground(mProgressDrawable);
        mMovingView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        mProgressDrawable.start();
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {}

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if(mProgressDrawable != null && mProgressDrawable.isRunning()) {
            mProgressDrawable.stop();
        }
        mReleased = false;
        return 100;//延迟100毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                break;
            case Refreshing:
                break;
            case ReleaseToRefresh:
                break;
        }
    }

    private void initAnimation(){
        mProgressDrawable = new AnimationDrawable();
        for (int i = mCurImageIndex; i < Count; i++) {
            int id = getResources().getIdentifier("refresh_header_" + i, "drawable", getContext().getPackageName());
            mProgressDrawable.addFrame(getResources().getDrawable(id), 30);
        }
        for (int i = 0; i < mCurImageIndex; i++) {
            int id = getResources().getIdentifier("refresh_header_" + i, "drawable", getContext().getPackageName());
            mProgressDrawable.addFrame(getResources().getDrawable(id), 30);
        }
        mProgressDrawable.setOneShot(false);
    }
}
