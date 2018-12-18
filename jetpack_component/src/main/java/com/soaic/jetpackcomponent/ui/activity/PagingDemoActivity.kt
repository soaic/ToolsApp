package com.soaic.jetpackcomponent.ui.activity

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.soaic.jetpackcomponent.R
import com.soaic.jetpackcomponent.paging.CustomAdapter
import com.soaic.jetpackcomponent.paging.CustomPageDataSourceFactory
import com.soaic.jetpackcomponent.paging.DataRepository
import com.soaic.libcommon.base.BasicActivity

class PagingDemoActivity : BasicActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var dataRepository: DataRepository

    override fun getContentView(): Int {
        return R.layout.paging_activity
    }

    override fun initVariables(savedInstanceState: Bundle?) {


    }

    override fun initViews() {
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter()
        recycler.adapter = adapter
        dataRepository = DataRepository()
        val data = LivePagedListBuilder(CustomPageDataSourceFactory(dataRepository), PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build()).build()

        data.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun initEvents() {
    }

    override fun loadData() {
    }

}