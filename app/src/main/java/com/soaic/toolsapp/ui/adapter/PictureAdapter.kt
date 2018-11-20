package com.soaic.toolsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.soaic.libcommon.glide.GlideUtil
import com.soaic.libcommon.recyclerview.adapter.BasicAdapter
import com.soaic.libcommon.recyclerview.holder.BasicItemHolder
import com.soaic.libcommon.utils.ScreenUtils
import com.soaic.toolsapp.R
import com.soaic.toolsapp.model.PictureModel

class PictureAdapter(var context: Context, private val mData: List<PictureModel>): BasicAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BasicItemHolder {
        return BasicItemHolder(inflater.inflate(R.layout.item_picture, null, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBasicBindViewHolder(holder: BasicItemHolder, position: Int) {
        val itemPicturePicIv = holder.findViewById<ImageView>(R.id.itemPicturePicIv)
        GlideUtil.display(itemPicturePicIv, mData[position].url)
    }

}