package com.soaic.jetpackcomponent.model

import android.databinding.BaseObservable
import android.databinding.ObservableField

class User: BaseObservable() {
    val firstName = ObservableField<String>()
    val lastName =  ObservableField<String>()

    val age = 0
}