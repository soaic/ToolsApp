package com.soaic.toolsapp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.soaic.libcommont.network.NetClient
import com.soaic.libcommont.network.listener.OnResultListener
import com.soaic.libcommont.recyclerview.adapter.BasicAdapter
import com.soaic.libcommont.recyclerview.holder.BasicItemHolder
import com.soaic.libcommont.utils.LogUtil
import com.soaic.toolsapp.R
import com.soaic.toolsapp.model.Music
import com.soaic.toolsapp.ui.adapter.MusicAdapter
import com.soaic.toolsapp.ui.fragment.base.BasicFragment


class MusicFragment: BasicFragment() {

    private lateinit var musicRvl: RecyclerView
    private var mData: MutableList<Music> = ArrayList()
    private lateinit var musicAdapter: MusicAdapter

    companion object {
        fun newInstance(): MusicFragment{
            return MusicFragment()
        }
    }

    override val contentView: Int
        get() = R.layout.fragment_music

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        musicRvl = findViewById(R.id.music_rvl)
        musicRvl.layoutManager = LinearLayoutManager(activity.applicationContext)
        musicAdapter = MusicAdapter(mData)
        musicRvl.adapter = musicAdapter
        musicRvl.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }

    override fun initEvents() {
        musicAdapter.onItemClickListener = (object: BasicAdapter.OnItemClickListener{
            override fun onItemClick(view: View, holder: BasicItemHolder, position: Int) {
                showToast(mData[position].name)
            }
        })


    }

    override fun loadData() {
        for(i in 1..10){
            mData.add(Music(i.toString(), "name$i", "singer$i", "album$i", "picture$i"))
        }
        musicAdapter.notifyDataSetChanged()



        requestMusicInfo()
    }

    private fun requestMusicInfo(){
        NetClient.Builder(context)
                .url("http://music.163.com/discover/toplist?id=3778678")
                .build().get(String::class.java, object: OnResultListener<String>{
                    override fun onSuccess(t: String?) {
                        LogUtil.d(t)
                    }
                    override fun onFailure(err: Throwable?) {
                        err!!.printStackTrace()
                    }
                })
    }

    override fun onUserVisible() {

    }

}