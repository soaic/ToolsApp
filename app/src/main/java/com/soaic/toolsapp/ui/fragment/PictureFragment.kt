package com.soaic.toolsapp.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import com.soaic.libcommon.recyclerview.XRecycleView
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.fragment.base.BasicFragment

class PictureFragment: BasicFragment() {
    private lateinit var pictureListSrl: SwipeRefreshLayout
    private lateinit var pictureListRlv: XRecycleView

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
        pictureListRlv = findViewById(R.id.pictureListRlv)
    }

    override fun initEvents() {
        pictureListSrl.setOnRefreshListener {

        }

        pictureListRlv.setOnLoadMoreListener {

        }
    }

    override fun loadData() {

    }

    override fun onUserVisible() {

    }

}