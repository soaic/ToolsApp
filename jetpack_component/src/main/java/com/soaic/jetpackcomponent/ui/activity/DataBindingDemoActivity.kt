package com.soaic.jetpackcomponent.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.soaic.jetpackcomponent.R
import com.soaic.jetpackcomponent.databinding.StartActivityBinding
import com.soaic.jetpackcomponent.ui.fragment.StartFragment

class DataBindingDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<StartActivityBinding>(this, R.layout.start_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, StartFragment.newInstance())
                    .commitNow()
        }
    }


}
