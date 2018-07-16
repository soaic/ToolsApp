package com.soaic.libcommon.recyclerview.itemtouchhelper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //返回可以滑动的方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;      //只允许上下拖动
        int swipeFlags = ItemTouchHelper.LEFT;                          //只允许从右向左侧滑
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //当用户拖动一个Item进行上下移动从旧的位置到新的位置的时候会调用该方法
        //在该方法内可以调用Adapter的notifyItemMoved方法来交换两个ViewHolder的位置
        //最后返回true表示被拖动的ViewHolder已经移动到了目的位置(前提是支持上下拖动)
        return false;
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        //针对swipe状态，swipe滑动的位置超过了百分之多少就消失
        return .5f;
    }

    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        //针对drag状态，滑动超过百分之多少的距离可以可以调用onMove()函数
        //(注意哦，这里指的是onMove()函数的调用，并不是随手指移动的那个view哦)
        return .5f;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        //针对swipe状态，swipe的逃逸速度，换句话说就算没达到getSwipeThreshold设置的距离，达到了这个逃逸速度item也会被swipe消失掉
        return super.getSwipeEscapeVelocity(defaultValue);
    }

    public float getSwipeVelocityThreshold(float defaultValue) {
        //针对swipe状态，swipe滑动的阻尼系数,设置最大滑动速度
        return defaultValue;
    }

    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        //针对drag状态，当前target对应的item是否允许move
        //换句话说我们一般用drag来做一些换位置的操作，就是当前target对应的item是否可以换位置
        return super.canDropOver(recyclerView, current, target);
    }

    public int getBoundingBoxMargin() {
        //针对drag状态，当drag itemView和底下的itemView重叠的时候,可以给drag itemView设置额外的margin，让重叠更加容易发生。相当于增大了drag itemView的区域
        return 0;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //当左右滑动Item达到删除条件时
        //一般手指触摸滑动的距离达到RecyclerView宽度的一半时，
        //再松开手指，此时该Item会继续向原先滑动方向滑过去并且调用onSwiped方法进行删除，否则会反向滑回原来的位置

        //如果在onSwiped方法内我们没有进行任何操作，即不删除已经滑过去的Item，那么就会留下空白的地方，
        //因为实际上该ItemView还占据着该位置，只是移出了我们的可视范围内罢了。
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //返回true时，表示支持长按拖动
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //返回true时，表示如果用户触摸并左右滑动了View，那么可以执行滑动删除操作
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //从静止状态变为拖拽或者滑动的时候会回调该方法，参数actionState表示当前的状态。
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //当用户操作完毕某个item并且其动画也结束后会调用该方法，
        //一般在该方法内恢复ItemView的初始状态，防止由于复用而产生的显示错乱问题。
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //实现自定义的交互规则或者自定义的动画效果
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //针对swipe和drag状态，整个过程中一直会调用这个函数(和ItemDecoration里面的onDrawOver()函数对应)
        //这个函数提供给我们可以在RecyclerView的上面再绘制一层东西，比如绘制一层蒙层啥的
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        //针对swipe和drag状态，当手指离开之后，view回到指定位置动画的持续时间(swipe可能是回到原位，也有可能是swipe掉)
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

}
