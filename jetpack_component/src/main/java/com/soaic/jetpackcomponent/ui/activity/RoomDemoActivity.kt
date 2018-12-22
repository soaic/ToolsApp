package com.soaic.jetpackcomponent.ui.activity

import android.os.Bundle
import android.widget.Button
import com.soaic.jetpackcomponent.R
import com.soaic.jetpackcomponent.app.AppDatabase
import com.soaic.jetpackcomponent.entity.XUser
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.utils.Logger

class RoomDemoActivity : BasicActivity() {
    private lateinit var add : Button
    private lateinit var find : Button

    override fun getContentView(): Int {
        return R.layout.room_activity
    }

    override fun initVariables(savedInstanceState: Bundle?) {
    }

    override fun initViews() {

        add = findViewById(R.id.add)
        find = findViewById(R.id.find)

    }

    override fun initEvents() {
        find.setOnClickListener{
            var users = AppDatabase.getAppDatabase(applicationContext).userDao().getAll()
            Logger.d("==="+users.toString())
        }

        add.setOnClickListener{
            val user = XUser(System.nanoTime().toInt(),"soaic","xiao")
            AppDatabase.getAppDatabase(applicationContext).userDao().insertAll(user)
        }
    }

    override fun loadData() {
    }

}