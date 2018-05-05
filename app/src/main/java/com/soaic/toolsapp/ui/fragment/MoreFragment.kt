package com.soaic.toolsapp.ui.fragment

import android.os.Bundle
import android.widget.TextView
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.fragment.base.BasicFragment

class MoreFragment: BasicFragment() {

    lateinit var moreLocation: TextView
    lateinit var moreFm: TextView
    lateinit var moreNovel: TextView
    lateinit var moreExpress: TextView
    lateinit var moreWeather: TextView

    companion object {
        fun newInstance(): MoreFragment{
            return MoreFragment()
        }
    }

    override val contentView: Int
        get() = R.layout.fragment_more

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        moreLocation = findViewById(R.id.moreLocation)
        moreFm = findViewById(R.id.moreFm)
        moreNovel = findViewById(R.id.moreNovel)
        moreExpress = findViewById(R.id.moreExpress)
        moreWeather = findViewById(R.id.moreWeather)
    }

    override fun initEvents() {
        moreLocation.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
        moreFm.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
        moreNovel.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
        moreExpress.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
        moreWeather.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
    }

    override fun loadData() {

    }

    override fun onUserVisible() {

    }

}