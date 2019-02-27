package com.soaic.musiccomponent.ui.frament

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.soaic.libcommon.base.BasicFragment
import com.soaic.libcommon.constant.ARouterConfig
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.recyclerview.XRecycleView
import com.soaic.libcommon.recyclerview.decoration.ListDividerItemDecoration
import com.soaic.libcommon.utils.ListUtil
import com.soaic.musiccomponent.R
import com.soaic.musiccomponent.request.MusicRequest
import com.soaic.musiccomponent.ui.activity.MusicDetailActivity
import com.soaic.musiccomponent.ui.adapter.MusicAdapter
import com.soaic.musiccomponent.model.MusicModel
import com.soaic.musiccomponent.response.MusicResponse

@Route(path = ARouterConfig.MAIN_MUSIC_FRAGMENT)
class MusicFragment: BasicFragment() {

    private lateinit var musicRvl: XRecycleView
    private lateinit var musicSrl: SwipeRefreshLayout
    private var mData: MutableList<MusicModel> = ArrayList()
    private lateinit var musicAdapter: MusicAdapter
    private var offset = 0
    private var size = 10

    companion object {
        fun newInstance(): MusicFragment{
            return MusicFragment()
        }
    }

    override fun getContentView(): Int {
        return R.layout.fragment_music
    }

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        musicRvl = findViewById(R.id.music_rvl)
        musicSrl = findViewById(R.id.music_srl)
        musicSrl.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        musicRvl.layoutManager = LinearLayoutManager(context)
        musicAdapter = MusicAdapter(mData)
        musicRvl.adapter = musicAdapter
        musicRvl.addItemDecoration(ListDividerItemDecoration())
    }

    override fun initEvents() {
        musicAdapter.setOnItemClickListener { _, _, position ->
            val bundle = Bundle()
            bundle.putString("songId", mData[position].song_id)
            startActivity(MusicDetailActivity::class.java, bundle)
        }
        musicRvl.setOnLoadMoreListener {
            requestMusicInfo()
        }
        musicSrl.setOnRefreshListener {
            offset = 0
            musicRvl.setLoadMoreEnabled(true)
            requestMusicInfo()
        }
    }

    override fun loadData() {
        requestMusicInfo()
    }

    private fun requestMusicInfo(){
        MusicRequest.getMusicList(context!!, size, offset, object: OnResultListener<MusicResponse>{
            override fun onSuccess(t: MusicResponse) {
                if(offset == 0) {
                    mData.clear()
                    if(musicSrl.isRefreshing){
                        musicSrl.isRefreshing = false
                    }
                }
                if(ListUtil.isEmpty(t.song_list)){
                    musicRvl.finishLoadMoreWithNoMoreData()
                } else {
                    offset += size
                    mData.addAll(t.song_list)
                    musicRvl.finishLoadMore()
                    musicAdapter.notifyDataSetChanged()

                }
            }
            override fun onFailure(err: Throwable) {
                err.printStackTrace()
                musicRvl.finishLoadMoreError()
                if(musicSrl.isRefreshing)
                    musicSrl.isRefreshing = false
            }
        })

    }



    override fun onUserVisible() {

    }

}