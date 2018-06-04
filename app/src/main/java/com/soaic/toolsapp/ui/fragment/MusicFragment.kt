package com.soaic.toolsapp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.soaic.libcommon.network.NetClient
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.recyclerview.decoration.ListDividerItemDecoration
import com.soaic.libcommon.utils.LogUtils
import com.soaic.toolsapp.R
import com.soaic.toolsapp.model.Music
import com.soaic.toolsapp.ui.adapter.MusicAdapter
import com.soaic.toolsapp.ui.fragment.base.BasicFragment


class MusicFragment: BasicFragment() {

    private lateinit var musicRvl: RecyclerView
    private var mData: MutableList<Music> = ArrayList()
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var refresh_layout: SmartRefreshLayout

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
        musicRvl.addItemDecoration(ListDividerItemDecoration())
        refresh_layout = findViewById(R.id.refresh_layout)
        refresh_layout.setDragRate(0.5f)
        refresh_layout.setHeaderMaxDragRate(5f)
        refresh_layout.setReboundDuration(800)
        refresh_layout.setOnRefreshListener {
            it.finishRefresh(2000)
        }
        refresh_layout.setOnLoadMoreListener {
            it.finishLoadMore(2000)
        }
    }

    override fun initEvents() {
        musicAdapter.setOnItemClickListener { _, _, position -> showToast(mData[position].name) }


    }

    override fun loadData() {
        for(i in 1..10){
            mData.add(Music(i.toString(), "name$i", "singer$i", "album$i", "picture$i"))
        }
        musicAdapter.notifyDataSetChanged()

        //requestMusicInfo()
    }

    private fun requestMusicInfo(){
        NetClient.Builder(context)
                .url("http://music.163.com/discover/toplist?id=3778678")
                .build().get(String::class.java, object: OnResultListener<String>{
                    override fun onSuccess(t: String?) {
                        LogUtils.d(t)
                    }
                    override fun onFailure(err: Throwable?) {
                        err!!.printStackTrace()
                    }
                })
    }

    override fun onUserVisible() {

    }

}