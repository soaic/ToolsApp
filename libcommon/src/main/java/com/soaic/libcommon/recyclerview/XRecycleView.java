package com.soaic.libcommon.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.soaic.libcommon.recyclerview.listener.OnRefreshListener;

public class XRecycleView extends RecyclerView {

    private int lastVisibleItemPosition;
    private boolean isBottom;
    private OnRefreshListener mOnRefreshListener;
    private boolean loadingMoreEnabled;
    private boolean mRefreshing;
    private boolean isLoading;
    private boolean isNoMore;
    private boolean isShowLoadMore;

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

    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if(getAdapter() == null) return;
        int mAdapterCount = getAdapter().getItemCount();

        lastVisibleItemPosition = getLastVisibleItemPosition();
        isBottom = mAdapterCount == lastVisibleItemPosition;
        if (mOnRefreshListener != null && loadingMoreEnabled && !mRefreshing && isBottom && !isLoading && !isNoMore) {
            isShowLoadMore = true;
        }

    }

    private int getLastVisibleItemPosition(){
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            //通过LayoutManager找到当前显示的最后的item的position
            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition() + 1;
        }else if(layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            //因为StaggeredGridLayoutManager的特殊性可能导致最先显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最先显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            return findMax(lastPositions);
        }
        return 0;
    }

    private int getFirstVisibleItemPosition(){
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            //通过LayoutManager找到当前显示的最后的item的position
            return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }else if(layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            //因为StaggeredGridLayoutManager的特殊性可能导致最先显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最小的那个就是最先显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(lastPositions);
            return findMin(lastPositions);
        }
        return 0;
    }

    //找到数组中的最小值
    private int findMin(int[] lastPositions) {
        int mix = lastPositions[0];
        for (int value : lastPositions) {
            if (value < mix) {
                mix = value;
            }
        }
        return mix;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    public void setLoadingMoreEnabled(boolean loadingMoreEnabled) {
        this.loadingMoreEnabled = loadingMoreEnabled;
    }
}
