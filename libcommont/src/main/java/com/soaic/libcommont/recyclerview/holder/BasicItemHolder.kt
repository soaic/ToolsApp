package com.soaic.libcommont.recyclerview.holder

import android.support.v7.widget.RecyclerView
import android.view.View

class BasicItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun <T: View> findViewById(viewId: Int):T{
        return itemView.findViewById(viewId)
    }

}