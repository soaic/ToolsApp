package com.soaic.libcommon.recyclerview.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soaic.libcommon.R;

public class LoadMoreView extends LinearLayout {

    public final static int NORMAL = 0;     //禁用
    public final static int LOADING = 1;    //加载中
    public final static int NO_MORE = 2;    //没有更多
    public final static int NO_NETWORK = 3; //没有网络
    public final static int FAILURE = 4;    //加载失败

    private TextView loadMoreStateView;
    private ProgressBar loadMoreRefreshView;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.layout_load_more, this, true);
        init();
    }

    private void init() {
        loadMoreStateView = findViewById(R.id.loadMoreStateView);
        loadMoreRefreshView = findViewById(R.id.loadMoreRefreshView);
    }

    public void switchState(int state){
        switch (state){
            case NORMAL:
                loadMoreRefreshView.setVisibility(GONE);
                loadMoreStateView.setVisibility(GONE);
                break;
            case LOADING:
                loadMoreRefreshView.setVisibility(VISIBLE);
                loadMoreStateView.setVisibility(GONE);
                break;
            case NO_MORE:
                loadMoreRefreshView.setVisibility(GONE);
                loadMoreStateView.setVisibility(VISIBLE);
                loadMoreStateView.setText("没有更多了");
                break;
            case NO_NETWORK:
                loadMoreRefreshView.setVisibility(GONE);
                loadMoreStateView.setVisibility(VISIBLE);
                loadMoreStateView.setText("没有网络");
                break;
            case FAILURE:
                loadMoreRefreshView.setVisibility(GONE);
                loadMoreStateView.setVisibility(VISIBLE);
                loadMoreStateView.setText("加载失败");
                break;
        }
    }
}
