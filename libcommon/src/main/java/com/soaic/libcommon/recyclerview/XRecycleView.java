package com.soaic.libcommon.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.soaic.libcommon.recyclerview.adapter.WrapperAdapter;
import com.soaic.libcommon.recyclerview.listener.OnLoadMoreScrollListener;
import com.soaic.libcommon.recyclerview.listener.OnLoadMoreListener;
import com.soaic.libcommon.recyclerview.weight.LoadMoreView;

public class XRecycleView extends RecyclerView {

    private OnLoadMoreScrollListener mOnLoadMoreScrollListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean mLoadMoreEnabled;
    private FrameLayout mLoadMoreContainer;
    private LoadMoreView mLoadMoreView;
    private boolean isLoading = false;

    public XRecycleView(@NonNull Context context) {
        this(context, null);
    }

    public XRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLoadMoreEnabled(true);
        setLoadMoreView();
    }

    public void setLoadMoreView(){
        if(mLoadMoreView != null){
            removeLoadMoreView(mLoadMoreView);
        }
        this.mLoadMoreView = new LoadMoreView(getContext());
        enableLoadMoreContainer();
        mLoadMoreContainer.addView(mLoadMoreView);
    }

    private void removeLoadMoreView(View loadMoreView) {
        if(mLoadMoreContainer != null){
            mLoadMoreContainer.removeView(loadMoreView);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        enableLoadMoreContainer();
        super.setAdapter(new WrapperAdapter(adapter, mLoadMoreContainer));
    }

    private void enableLoadMoreContainer() {
        if(mLoadMoreContainer == null){
            mLoadMoreContainer = new FrameLayout(getContext());
            mLoadMoreContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public Adapter getRealAdapter() {
        WrapperAdapter adapter = (WrapperAdapter) super.getAdapter();
        return adapter == null ? null : adapter.getAdapter();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        if(mLoadMoreEnabled == loadMoreEnabled) return;
        this.mLoadMoreEnabled = loadMoreEnabled;
        if (loadMoreEnabled) {
            if (mOnLoadMoreScrollListener == null) {
                mOnLoadMoreScrollListener = new OnLoadMoreScrollListener() {
                    @Override
                    public void onLoadMore(RecyclerView recyclerView) {
                        if(!isLoading) {
                            isLoading = true;
                            mLoadMoreView.switchState(LoadMoreView.LOADING);
                            if (mOnLoadMoreListener != null) {
                                mOnLoadMoreListener.onLoadMore();
                            }
                        }
                    }
                };
            }
            addOnScrollListener(mOnLoadMoreScrollListener);
        } else {
            if (mOnLoadMoreScrollListener != null) {
                removeOnScrollListener(mOnLoadMoreScrollListener);
            }
        }
    }

    public void finishLoadMore(){
        isLoading = false;
        mLoadMoreView.switchState(LoadMoreView.NORMAL);
    }
    public void finishLoadMoreWithNoMoreData(){
        mLoadMoreView.switchState(LoadMoreView.NO_MORE);
        setLoadMoreEnabled(false);
    }
    public void finishLoadMoreError(){
        mLoadMoreView.switchState(LoadMoreView.FAILURE);
    }
}
