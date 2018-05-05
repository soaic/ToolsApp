package com.soaic.toolsapp.ui.fragment

import android.os.Bundle
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.fragment.base.BasicFragment

class VideoFragment: BasicFragment() {

    companion object {
        fun newInstance(): VideoFragment{
            return VideoFragment()
        }
    }

    override val contentView: Int
        get() = R.layout.fragment_video

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {

    }

    override fun initEvents() {

    }

    override fun loadData() {

    }

    override fun onUserVisible() {

    }

}