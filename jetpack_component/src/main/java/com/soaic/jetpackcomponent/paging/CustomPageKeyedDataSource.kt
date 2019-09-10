package com.soaic.jetpackcomponent.paging

import android.arch.paging.PageKeyedDataSource

class CustomPageKeyedDataSource() : PageKeyedDataSource<Long, DataBean>() {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, DataBean>) {

    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, DataBean>) {

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, DataBean>) {

    }

}