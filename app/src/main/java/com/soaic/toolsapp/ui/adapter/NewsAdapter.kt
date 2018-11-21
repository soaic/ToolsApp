package com.soaic.toolsapp.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.soaic.libcommon.recyclerview.adapter.BasicAdapter
import com.soaic.libcommon.recyclerview.holder.BasicItemHolder
import com.soaic.toolsapp.R
import com.soaic.toolsapp.model.NewsModel
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.soaic.libcommon.glide.GlideUtil


class NewsAdapter(var context: Context, private val mData: List<NewsModel>): BasicAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BasicItemHolder {
        return BasicItemHolder(inflater.inflate(R.layout.item_news, null, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBasicBindViewHolder(holder: BasicItemHolder, position: Int) {
        val itemNewsPictureIv = holder.findViewById<ImageView>(R.id.itemNewsPictureIv)
        val itemNewsTitleTv = holder.findViewById<TextView>(R.id.itemNewsTitleTv)
        val itemNewsSourceTv = holder.findViewById<TextView>(R.id.itemNewsSourceTv)
        val itemNewsRelayTv = holder.findViewById<TextView>(R.id.itemNewsRelayTv)
        val itemNewsTimeTv = holder.findViewById<TextView>(R.id.itemNewsTimeTv)

        if(TextUtils.isEmpty(mData[position].imgsrc)){
            itemNewsPictureIv.visibility = View.GONE
        }else{
            itemNewsPictureIv.visibility = View.VISIBLE
            GlideUtil.display(itemNewsPictureIv, mData[position].imgsrc)
        }

        itemNewsTitleTv.text = mData[position].title
        itemNewsSourceTv.text = mData[position].source
        itemNewsTimeTv.text = mData[position].lmodify
        itemNewsRelayTv.text = "${mData[position].replyCount}评论"

    }

}