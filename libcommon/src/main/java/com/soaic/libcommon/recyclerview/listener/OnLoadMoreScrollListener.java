package com.soaic.libcommon.recyclerview.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.soaic.libcommon.utils.RecyclerViewUtil;

public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {}

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if(recyclerView.getLayoutManager() != null){
            int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
            //可见item数量大于0 并且 RecyclerView停止滚动 并且 最后一个item可见
            boolean triggerCondition = visibleItemCount > 0 &&
                    //newState == RecyclerView.SCROLL_STATE_IDLE &&
                    canTriggerLoadMore(recyclerView);
            if(triggerCondition){
                onLoadMore(recyclerView);
            }
        }
    }

    private boolean canTriggerLoadMore(RecyclerView recyclerView) {
        int lastVisibleItem = RecyclerViewUtil.getLastVisibleItemPosition(recyclerView);
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        return totalItemCount - 1 == lastVisibleItem;
    }

    public abstract void onLoadMore(RecyclerView recyclerView);

}
