package com.soaic.picturecomponent.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.util.SparseArray
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.base.BasicFragment
import com.soaic.libcommon.constant.ARouterConfig
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.recyclerview.XRecycleView
import com.soaic.libcommon.recyclerview.decoration.GridSpacingItemDecoration
import com.soaic.libcommon.utils.ListUtil
import com.soaic.libcommon.utils.Utils
import com.soaic.picturecomponent.R
import com.soaic.picturecomponent.model.PictureModel
import com.soaic.picturecomponent.request.PictureRequest
import com.soaic.picturecomponent.response.PictureResponse
import com.soaic.picturecomponent.ui.adapter.PictureAdapter

@Route(path = ARouterConfig.MAIN_PICTURE_FRAGMENT)
class PictureFragment: BasicFragment() {
    private lateinit var pictureListSrl: SwipeRefreshLayout
    private lateinit var pictureListRlv: XRecycleView
    private lateinit var pictureAdapter: PictureAdapter
    private var mData: MutableList<PictureModel> = mutableListOf()
    private var mDataUri: MutableList<Uri> = mutableListOf()
    private var page: Int = 0

    companion object {
        fun newInstance(): PictureFragment {
            return PictureFragment()
        }
    }

    override fun getContentView(): Int {
        return R.layout.fragment_picture
    }
    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        pictureListSrl = findViewById(R.id.pictureListSrl)
        pictureListSrl.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        pictureListRlv = findViewById(R.id.pictureListRlv)
        pictureListRlv.layoutManager = GridLayoutManager(context,2)
        pictureListRlv.addItemDecoration(GridSpacingItemDecoration(2,
                Utils.dip2px(context,3f),false))
        pictureAdapter = PictureAdapter(context!!, mData)
        pictureListRlv.adapter = pictureAdapter
    }

    override fun initEvents() {
        pictureListSrl.setOnRefreshListener {
            page = 0
            requestPictureData()
            pictureListRlv.setLoadMoreEnabled(true)
        }

        pictureListRlv.setOnLoadMoreListener {
            requestPictureData()
        }

        pictureAdapter.setOnItemClickListener { view, holder, position ->
            showImage(position, view.findViewById(R.id.itemPicturePicIv))
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
                if(page == 0){
                    mData.clear()
                    mDataUri.clear()
                    if(pictureListSrl.isRefreshing)
                        pictureListSrl.isRefreshing = false
                }

                if(ListUtil.isEmpty(t.data)){
                    pictureListRlv.finishLoadMoreWithNoMoreData()
                }else{
                    page++
                    mData.addAll(t.data)
                    for (d in t.data) mDataUri.add(Uri.parse(d.url))
                    pictureListRlv.finishLoadMore()
                    pictureAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(err: Throwable) {
                err.printStackTrace()
                pictureListRlv.finishLoadMoreError()
                if(pictureListSrl.isRefreshing)
                    pictureListSrl.isRefreshing = false
            }
        })
    }

    private fun showImage(position: Int, imageView: ImageView){
        if(activity is BasicActivity){
            val iwHelper = (activity as BasicActivity).imageWatchHelper
            val mapping = SparseArray<ImageView>()
            mapping.put(position, imageView)
            iwHelper?.show(imageView, mapping, mDataUri)
        }
    }

}