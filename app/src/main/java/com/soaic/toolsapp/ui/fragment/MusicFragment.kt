package com.soaic.toolsapp.ui.fragment

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.soaic.libcommon.network.NetClient
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.recyclerview.decoration.ListDividerItemDecoration
import com.soaic.libcommon.utils.PermissionsUtils
import com.soaic.toolsapp.R
import com.soaic.toolsapp.entity.MusicEntity
import com.soaic.toolsapp.model.Music
import com.soaic.toolsapp.ui.adapter.MusicAdapter
import com.soaic.toolsapp.ui.fragment.base.BasicFragment


class MusicFragment: BasicFragment() {

    private lateinit var musicRvl: RecyclerView
    private var mData: MutableList<Music> = ArrayList()
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var refresh_layout: SmartRefreshLayout
    private var offset = 0
    private var size = 10

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
        musicRvl.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        musicAdapter = MusicAdapter(mData)
        musicRvl.adapter = musicAdapter
        musicRvl.addItemDecoration(ListDividerItemDecoration())
        refresh_layout = findViewById(R.id.refresh_layout)
        refresh_layout.setDragRate(0.5f)
        refresh_layout.setHeaderMaxDragRate(5f)
        refresh_layout.setReboundDuration(800)
        refresh_layout.setOnRefreshListener {
            offset = 0
            requestMusicInfo()
        }
        refresh_layout.setOnLoadMoreListener {
            requestMusicInfo()
        }
    }

    override fun initEvents() {
        musicAdapter.setOnItemClickListener { _, _, position ->
            playMusic(mData[position].song_id)
        }

    }

    override fun loadData() {
        refresh_layout.autoRefresh()
    }

    private fun requestMusicInfo(){
        NetClient.Builder(context)
                .url("http://tingapi.ting.baidu.com/v1/restserver/ting")
                .param("method","baidu.ting.billboard.billList")
                .param("type","1")
                .param("size", size.toString())
                .param("offset",offset.toString())
                .build().get(MusicEntity::class.java, object: OnResultListener<MusicEntity>{
                    override fun onSuccess(t: MusicEntity) {
                        if(offset == 0) {
                            mData.clear()
                            refresh_layout.finishRefresh(2000)
                        } else {
                            refresh_layout.finishLoadMore(2000)
                        }
                        mData.addAll(t.song_list)
                        musicAdapter.notifyDataSetChanged()

                        offset += size
                    }
                    override fun onFailure(err: Throwable) {
                        err.printStackTrace()
                    }
                })
    }

    private fun playMusic(songId: String){
        NetClient.Builder(context)
                .url("https://tingapi.ting.baidu.com/v1/restserver/ting")
                .param("method","baidu.ting.song.play")
                .param("songid",songId)
                .build().get(MusicEntity::class.java, object: OnResultListener<MusicEntity>{
                    override fun onSuccess(t: MusicEntity) {
                        if(offset == 0) {
                            mData.clear()
                            refresh_layout.finishRefresh(2000)
                        } else {
                            refresh_layout.finishLoadMore(2000)
                        }
                        mData.addAll(t.song_list)
                        musicAdapter.notifyDataSetChanged()

                        offset += size
                    }
                    override fun onFailure(err: Throwable) {
                        err.printStackTrace()
                    }
                })
    }

    override fun onUserVisible() {

    }

}