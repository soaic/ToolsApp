package com.soaic.newscomponent.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.soaic.libcommon.base.BasicFragment
import com.soaic.libcommon.base.BasicWebActivity
import com.soaic.libcommon.constant.ARouterConfig
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.recyclerview.XRecycleView
import com.soaic.libcommon.recyclerview.decoration.ListDividerItemDecoration
import com.soaic.libcommon.utils.ListUtil
import com.soaic.newscomponent.R
import com.soaic.newscomponent.model.NewsModel
import com.soaic.newscomponent.request.NewsRequest
import com.soaic.newscomponent.response.NewsResponse
import com.soaic.newscomponent.ui.adapter.NewsAdapter

@Route(path = ARouterConfig.MAIN_NEWS_FRAGMENT)
class NewsFragment: BasicFragment() {
    private lateinit var newsListSrl: SwipeRefreshLayout
    private lateinit var newsListRlv: XRecycleView
    private lateinit var newsAdapter: NewsAdapter
    private var mData: MutableList<NewsModel> = mutableListOf()
    private var page: Int = 1
    
    companion object {
        fun newInstance(): NewsFragment{
            return NewsFragment()
        }
    }

    override fun getContentView(): Int {
        return R.layout.fragment_news
    }

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        newsListSrl = findViewById(R.id.newsListSrl)
        newsListSrl.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        newsListRlv = findViewById(R.id.newsListRlv)
        newsListRlv.layoutManager = LinearLayoutManager(context)
        newsListRlv.addItemDecoration(ListDividerItemDecoration())
        newsAdapter = NewsAdapter(context!!, mData)
        newsListRlv.adapter = newsAdapter
    }

    override fun initEvents() {
        newsListSrl.setOnRefreshListener {
            page = 1
            requestNewsData()
            newsListRlv.setLoadMoreEnabled(true)
        }

        newsListRlv.setOnLoadMoreListener {
            requestNewsData()
        }

        newsAdapter.setOnItemClickListener { view, holder, position ->
            if(!TextUtils.isEmpty(mData[position].url)) {
                val bundle = Bundle()
                bundle.putString(BasicWebActivity.KEY_URL, mData[position].url)
                bundle.putString(BasicWebActivity.KEY_TITLE, mData[position].title)
                startActivity(BasicWebActivity::class.java, bundle)
            }
        }
    }

    override fun loadData() {
        requestNewsData()
    }

    override fun onUserVisible() {

    }

    private fun requestNewsData(){
        NewsRequest.getNewsList(context!!, page, object: OnResultListener<NewsResponse> {
            override fun onSuccess(t: NewsResponse) {
                if(page == 1){
                    mData.clear()
                    if(newsListSrl.isRefreshing)
                        newsListSrl.isRefreshing = false
                }

                if(ListUtil.isEmpty(t.getData())){
                    newsListRlv.finishLoadMoreWithNoMoreData()
                }else{
                    page++
                    mData.addAll(t.getData())
                    newsListRlv.finishLoadMore()
                    newsAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(err: Throwable) {
                err.printStackTrace()
                newsListRlv.finishLoadMoreError()
                if(newsListSrl.isRefreshing)
                    newsListSrl.isRefreshing = false
            }
        })
    }

}