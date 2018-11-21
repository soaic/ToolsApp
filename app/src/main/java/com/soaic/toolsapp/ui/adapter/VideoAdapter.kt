package com.soaic.toolsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.soaic.libcommon.recyclerview.adapter.BasicAdapter
import com.soaic.libcommon.recyclerview.holder.BasicItemHolder
import com.soaic.toolsapp.R
import com.soaic.toolsapp.model.VideoModel
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.soaic.libcommon.glide.GlideUtil


class VideoAdapter(var context: Context, private val mData: List<VideoModel>): BasicAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BasicItemHolder {
        return BasicItemHolder(inflater.inflate(R.layout.item_video, null, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBasicBindViewHolder(holder: BasicItemHolder, position: Int) {
        val itemVideoUserPhotoIv = holder.findViewById<ImageView>(R.id.itemVideoUserPhotoIv)
        val itemVideoUserNameTv = holder.findViewById<TextView>(R.id.itemVideoUserNameTv)
        val itemVideoUserSendTimeTv = holder.findViewById<TextView>(R.id.itemVideoUserSendTimeTv)
        val videoPlayer = holder.findViewById<JzvdStd>(R.id.itemVideoPlayer)

        itemVideoUserNameTv.text = mData[position].username
        itemVideoUserSendTimeTv.text = mData[position].passtime
        GlideUtil.displayRound(itemVideoUserPhotoIv, mData[position].header, 3)
        videoPlayer.setUp(mData[position].video, mData[position].text, Jzvd.SCREEN_WINDOW_NORMAL)
        GlideUtil.display(videoPlayer.thumbImageView, mData[position].thumbnail)

    }

}