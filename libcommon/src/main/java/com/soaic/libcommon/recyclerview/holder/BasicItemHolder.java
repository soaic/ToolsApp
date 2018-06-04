package com.soaic.libcommon.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BasicItemHolder extends RecyclerView.ViewHolder {

    public BasicItemHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T findViewById(int viewId){
        return itemView.findViewById(viewId);
    }
}
