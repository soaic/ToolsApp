package com.soaic.jetpackcomponent.paging

import android.arch.paging.PositionalDataSource

class CustomPositionalDataSource() : PositionalDataSource<DataBean>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<DataBean>) {

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<DataBean>) {

    }

}