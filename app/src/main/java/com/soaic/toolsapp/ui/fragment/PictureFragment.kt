package com.soaic.toolsapp.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.LinearLayout
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.recyclerview.XRecycleView
import com.soaic.libcommon.recyclerview.decoration.GridSpacingItemDecoration
import com.soaic.libcommon.utils.ListUtil
import com.soaic.toolsapp.R
import com.soaic.toolsapp.model.PictureModel
import com.soaic.toolsapp.request.PictureRequest
import com.soaic.toolsapp.response.PictureResponse
import com.soaic.toolsapp.ui.activity.MainActivity
import com.soaic.toolsapp.ui.adapter.PictureAdapter
import com.soaic.toolsapp.ui.fragment.base.BasicFragment
import android.util.SparseArray
import android.widget.ImageView


class PictureFragment: BasicFragment() {
    private lateinit var pictureListSrl: SwipeRefreshLayout
    private lateinit var pictureListRlv: XRecycleView
    private lateinit var pictureAdapter: PictureAdapter
    private var mData: MutableList<PictureModel> = mutableListOf()
    private var page: Int = 1

    companion object {
        fun newInstance(): PictureFragment{
            return PictureFragment()
        }
    }

    override val contentView: Int
        get() = R.layout.fragment_picture

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        pictureListSrl = findViewById(R.id.pictureListSrl)
        pictureListSrl.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        pictureListRlv = findViewById(R.id.pictureListRlv)
        pictureListRlv.layoutManager = GridLayoutManager(context,2)
        pictureListRlv.addItemDecoration(GridSpacingItemDecoration(2,10,false))
        pictureAdapter = PictureAdapter(context!!, mData)
        pictureListRlv.adapter = pictureAdapter
    }

    override fun initEvents() {
        pictureListSrl.setOnRefreshListener {
            page = 1
            requestPictureData()
            pictureListRlv.setLoadMoreEnabled(true)
        }

        pictureListRlv.setOnLoadMoreListener {
            requestPictureData()
        }

        pictureAdapter.setOnItemClickListener { view, holder, position ->
            showImage(view.findViewById(R.id.itemPicturePicIv), mData[position].url)
        }
    }

    override fun loadData() {
        requestPictureData()
    }

    override fun onUserVisible() {

    }

    private fun requestPictureData(){
        PictureRequest.getPictureList(context!!, page, object: OnResultListener<PictureResponse>{
            override fun onSuccess(t: PictureResponse) {
                if(page == 1){
                    mData.clear()
                    if(pictureListSrl.isRefreshing)
                        pictureListSrl.isRefreshing = false
                }

                if(ListUtil.isEmpty(t.data)){
                    pictureListRlv.finishLoadMoreWithNoMoreData()
                }else{
                    page++
                    mData.addAll(t.data)
                    pictureListRlv.finishLoadMore()
                    pictureAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(err: Throwable) {
                err.printStackTrace()
                pictureListRlv.finishLoadMoreError()
            }
        })
    }

    private fun showImage(clickedImage: ImageView, path: String){
        if(activity is MainActivity){
            val iwHelper = (activity as MainActivity).getImageWatchHelper()
            val mapping = SparseArray<ImageView>()
            mapping.put(0, clickedImage)
            val dataList = mutableListOf<String>()
            dataList.add(path)
            iwHelper.show(clickedImage, mapping, convert(dataList))
        }
    }

    private fun convert(data: List<String>): List<Uri> {
        val list = mutableListOf<Uri>()
        for (d in data) list.add(Uri.parse(d))
        return list
    }
}