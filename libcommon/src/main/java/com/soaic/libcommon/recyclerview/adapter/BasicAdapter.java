package com.soaic.libcommon.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soaic.libcommon.recyclerview.holder.BasicItemHolder;

public abstract class BasicAdapter extends RecyclerView.Adapter<BasicItemHolder>{
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    @Override
    public void onBindViewHolder(final BasicItemHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, holder, position);
                }
            });
        }

        if(onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClick(view, holder, position);
                    return false;
                }
            });
        }

        onBasicBindViewHolder(holder, position);
    }

    public abstract void onBasicBindViewHolder(BasicItemHolder holder, int position);

    public interface OnItemClickListener {
        void onItemClick(View view, BasicItemHolder holder, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view,BasicItemHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


}
