package com.soaic.libcommon.recyclerview.adapter;

import com.soaic.libcommon.recyclerview.weight.SlidingMenu;

public abstract class BasicSlidingAdapter extends BasicAdapter {

    private SlidingMenu mOpenMenu;
    private SlidingMenu mScrollingMenu;

    public SlidingMenu getScrollingMenu() {
        return mScrollingMenu;
    }

    public void setScrollingMenu(SlidingMenu scrollingMenu) {
        mScrollingMenu = scrollingMenu;
    }

    public void holdOpenMenu(SlidingMenu slidingMenu) {
        mOpenMenu = slidingMenu;
    }

    public void closeOpenMenu() {
        if (mOpenMenu != null && mOpenMenu.isOpen()) {
            mOpenMenu.closeMenu();
            mOpenMenu = null;
        }
    }

    // 修改为内容&菜单监听器:
    public interface OnClickListener {
        void onMenuClick(int position, boolean top);

        void onContentClick(int position);
    }

    public OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

}
