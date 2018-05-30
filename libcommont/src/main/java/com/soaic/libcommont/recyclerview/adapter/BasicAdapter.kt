package com.soaic.libcommont.recyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.soaic.libcommont.recyclerview.holder.BasicItemHolder

abstract class BasicAdapter: RecyclerView.Adapter<BasicItemHolder>(){
    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null

    override fun onBindViewHolder(holder: BasicItemHolder?, position: Int) {
        if(onItemClickListener != null) {
            holder!!.itemView.setOnClickListener{
                onItemClickListener!!.onItemClick(it, holder, position)
            }
        }

        if(onItemLongClickListener != null) {
            holder!!.itemView.setOnLongClickListener{
                if(onItemLongClickListener != null) {
                    onItemLongClickListener!!.onItemLongClick(it, holder, position)
                }else{
                    false
                }
            }
        }

        onBasicBindViewHolder(holder!!, position)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, holder: BasicItemHolder, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, holder: BasicItemHolder, position: Int): Boolean
    }

    abstract fun onBasicBindViewHolder(holder: BasicItemHolder, position: Int)
}