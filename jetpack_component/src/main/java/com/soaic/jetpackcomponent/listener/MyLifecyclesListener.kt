package com.soaic.jetpackcomponent.listener

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.location.Location

internal class MyLifecyclesListener(private val context: Context, private val callback: (String) -> Unit ) : LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        callback.invoke("start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        callback.invoke("stop")
    }

}
