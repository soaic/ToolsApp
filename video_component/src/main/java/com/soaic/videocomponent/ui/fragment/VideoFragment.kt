package com.soaic.videocomponent.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import cn.jzvd.Jzvd
import com.alibaba.android.arouter.facade.annotation.Route
import com.soaic.libcommon.base.BasicFragment
import com.soaic.libcommon.constant.ARouterConfig
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.recyclerview.XRecycleView
import com.soaic.libcommon.recyclerview.decoration.ListDividerItemDecoration
import com.soaic.libcommon.utils.ListUtil
import com.soaic.videocomponent.R
import com.soaic.videocomponent.model.VideoModel
import com.soaic.videocomponent.request.VideoRequest
import com.soaic.videocomponent.response.VideoResponse
import com.soaic.videocomponent.ui.adapter.VideoAdapter

@Route(path = ARouterConfig.MAIN_VIDEO_FRAGMENT)
class VideoFragment: BasicFragment() {
    private lateinit var videoListSrl: SwipeRefreshLayout
    private lateinit var videoListRlv: XRecycleView
    private lateinit var videoAdapter: VideoAdapter
    private var mData: MutableList<VideoModel> = mutableListOf()
    private var page: Int = 1

    companion object {
        fun newInstance(): VideoFragment{
            return VideoFragment()
        }
    }

    override fun getContentView(): Int {
        return R.layout.fragment_video
    }
    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        videoListSrl = findViewById(R.id.videoListSrl)
        videoListSrl.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        videoListRlv = findViewById(R.id.videoListRlv)
        videoListRlv.layoutManager = LinearLayoutManager(context)
        videoListRlv.addItemDecoration(ListDividerItemDecoration())
        videoAdapter = VideoAdapter(context!!, mData)
        videoListRlv.adapter = videoAdapter
    }

    override fun initEvents() {
        videoListSrl.setOnRefreshListener {
            page = 1
            requestVideoData()
            videoListRlv.setLoadMoreEnabled(true)
        }

        videoListRlv.setOnLoadMoreListener {
            requestVideoData()
        }

    }

    override fun loadData() {
        requestVideoData()
    }

    override fun onUserVisible() {

    }

    private fun requestVideoData(){
        VideoRequest.getVideoList(context!!, page, object: OnResultListener<VideoResponse> {
            override fun onSuccess(t: VideoResponse) {
                if(page == 1){
                    mData.clear()
                    if(videoListSrl.isRefreshing)
                        videoListSrl.isRefreshing = false
                }

                if(ListUtil.isEmpty(t.data)){
                    videoListRlv.finishLoadMoreWithNoMoreData()
                }else{
                    page++
                    mData.addAll(t.data)
                    videoListRlv.finishLoadMore()
                    videoAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(err: Throwable) {
                err.printStackTrace()
                videoListRlv.finishLoadMoreError()
                if(videoListSrl.isRefreshing)
                    videoListSrl.isRefreshing = false
            }
        })
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun interceptBackPressed(): Boolean {
        if(Jzvd.backPress()){
            return true
        }
        return super.interceptBackPressed()
    }
}