package com.soaic.jetpackcomponent.paging

import android.arch.paging.DataSource

class CustomPageDataSourceFactory(val repository: DataRepository) : DataSource.Factory<Int, DataBean>() {
    override fun create(): DataSource<Int, DataBean> {
        return CustomPageDataSource(repository)
    }
}