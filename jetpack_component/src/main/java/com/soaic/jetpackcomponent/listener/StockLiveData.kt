package com.soaic.jetpackcomponent.listener

import android.arch.lifecycle.LiveData
import java.math.BigDecimal

class StockLiveData(symbol: String) : LiveData<BigDecimal>() {

    override fun onActive() {
        //request update
    }

    override fun onInactive() {
        //remove update
    }

}