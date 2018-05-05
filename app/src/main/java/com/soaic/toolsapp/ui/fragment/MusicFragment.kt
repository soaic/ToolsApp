package com.soaic.toolsapp.ui.fragment

import android.os.Bundle
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.fragment.base.BasicFragment

class MusicFragment: BasicFragment() {

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

    }

    override fun initEvents() {

    }

    override fun loadData() {

    }

    override fun onUserVisible() {

    }

}