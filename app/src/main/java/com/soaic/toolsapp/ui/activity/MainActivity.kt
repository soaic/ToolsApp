package com.soaic.toolsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.constant.ARouterConfig
import com.soaic.libcommon.fragment.BackHandlerHelper
import com.soaic.libcommon.fragment.EmptyFragment
import com.soaic.libcommon.glide.GlideSimpleLoader
import com.soaic.libcommon.utils.ARouterUtils
import com.soaic.libcommon.weiget.NoScrollViewPager
import com.soaic.libcommon.weiget.imagewatcher.ImageWatcherHelper
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.fragment.MoreFragment

class  MainActivity : BasicActivity() {
    lateinit var viewPager: NoScrollViewPager
    lateinit var navigation:BottomNavigationView
    lateinit var pages:MutableList<Fragment>
    private lateinit var iwHelper: ImageWatcherHelper
    private var musicFragment = ARouterUtils.getFragment(ARouterConfig.MAIN_MUSIC_FRAGMENT)
    private var videoFragment = ARouterUtils.getFragment(ARouterConfig.MAIN_VIDEO_FRAGMENT)
    private var newsFragment = ARouterUtils.getFragment(ARouterConfig.MAIN_NEWS_FRAGMENT)
    private var pictureFragment = ARouterUtils.getFragment(ARouterConfig.MAIN_PICTURE_FRAGMENT)

    override fun getImageWatchHelper(): ImageWatcherHelper{
        return iwHelper
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initVariables(savedInstanceState: Bundle?) {
        iwHelper = ImageWatcherHelper.with(this, GlideSimpleLoader())
    }

    override fun initViews() {
        initFragments()
        viewPager = findViewById(R.id.viewPager)
        viewPager.setNoScroll(true)
        navigation = findViewById(R.id.navigation)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = pages[position]
            override fun getCount() = pages.size
        }
        viewPager.offscreenPageLimit = pages.size
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
            viewPager.setCurrentItem(tab, false)
            true
            //这里返回true，表示事件已经被处理。如果返回false，为了达到条目选中效果，还需要设置item.setChecked(true);
            //不论点击了哪一个，都手动设置为选中状态true（该控件并没有默认实现), 如果不设置，只有第一个menu展示的时候是选中状态，其他的即便被点击选中了，图标和文字也不会做任何更改
        }
    }

    override fun loadData() {

    }

    private fun initFragments(){
        pages = ArrayList()
        if(musicFragment == null){
            musicFragment = EmptyFragment()
        }
        if(videoFragment == null){
            videoFragment = EmptyFragment()
        }
        if(newsFragment == null){
            newsFragment = EmptyFragment()
        }
        if(pictureFragment == null){
            pictureFragment = EmptyFragment()
        }
        pages.add(musicFragment)
        pages.add(videoFragment)
        pages.add(pictureFragment)
        pages.add(newsFragment)
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

    override fun onBackPressed() {
        if (BackHandlerHelper.handleBackPress(this) ||     //处理Fragment返回
                iwHelper.handleBackPressed()) {                         //处理ImageWatch返回)

            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        videoFragment.onPause()
    }
}
