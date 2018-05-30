package com.soaic.toolsapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.soaic.libcommont.recyclerview.adapter.BasicAdapter
import com.soaic.libcommont.recyclerview.holder.BasicItemHolder
import com.soaic.toolsapp.R
import com.soaic.toolsapp.model.Music

class MusicAdapter(var data: MutableList<Music>) : BasicAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BasicItemHolder {
        return BasicItemHolder(View.inflate(parent?.context, R.layout.item_music, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBasicBindViewHolder(holder: BasicItemHolder, position: Int) {
        val nameView: TextView = holder.findViewById(R.id.music_name_tv)
        val signerView: TextView = holder.findViewById(R.id.music_singer_tv)
        val albumView: TextView = holder.findViewById(R.id.music_album_tv)

        nameView.text = data[position].name
        signerView.text = data[position].singer
        albumView.text = data[position].album

    }

}