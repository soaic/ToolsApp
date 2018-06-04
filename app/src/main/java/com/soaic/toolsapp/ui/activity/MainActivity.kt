package com.soaic.toolsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.soaic.libcommon.utils.BottomNavigationViewHelper
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.activity.base.BasicActivity
import com.soaic.toolsapp.ui.fragment.*

class MainActivity : BasicActivity() {
    lateinit var viewPager:ViewPager
    lateinit var navigation:BottomNavigationView
    lateinit var pages:MutableList<Fragment>

    override val contentView: Int
        get() = R.layout.activity_main

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        initFragments()
        viewPager = findViewById(R.id.viewPager)
        navigation = findViewById(R.id.navigation)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = pages[position]
            override fun getCount() = pages.size
        }
        viewPager.offscreenPageLimit = 5
        BottomNavigationViewHelper.disableShiftMode(navigation)
    }

    override fun initEvents() {
        navigation.setOnNavigationItemSelectedListener{ item ->
            var tab = 0
            when(item.itemId){
                R.id.action_music -> tab = 0
                R.id.action_video -> tab = 1
                R.id.action_picture -> tab = 2
                R.id.action_news -> tab = 3
                R.id.action_more -> tab = 4
            }
            viewPager.currentItem = tab
            false
        }
    }

    override fun loadData() {

    }

    private fun initFragments(){
        pages = ArrayList()
        pages.add(MusicFragment.newInstance())
        pages.add(VideoFragment.newInstance())
        pages.add(PictureFragment.newInstance())
        pages.add(NewsFragment.newInstance())
        pages.add(MoreFragment.newInstance())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(viewPager.currentItem < pages.size)
            pages[viewPager.currentItem].onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(viewPager.currentItem < pages.size)
            pages[viewPager.currentItem].onActivityResult(requestCode, resultCode, data)
    }
}
