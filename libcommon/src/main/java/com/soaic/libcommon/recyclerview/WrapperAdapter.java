package com.soaic.libcommon.recyclerview;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.soaic.libcommon.recyclerview.adapter.BasicAdapter;
import com.soaic.libcommon.recyclerview.holder.BasicItemHolder;

public class WrapperAdapter extends BasicAdapter {


    @Override
    public void onBasicBindViewHolder(BasicItemHolder holder, int position) {

    }

    @NonNull
    @Override
    public BasicItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
