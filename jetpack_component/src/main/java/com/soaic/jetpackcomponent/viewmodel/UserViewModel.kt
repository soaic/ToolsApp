package com.soaic.jetpackcomponent.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class UserViewModel: ViewModel(){
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
}
