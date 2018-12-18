package com.soaic.jetpackcomponent.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.soaic.jetpackcomponent.R
import com.soaic.jetpackcomponent.viewmodel.NameViewModel

class LiveDataDemoActivity: AppCompatActivity() {
    private lateinit var mModel: NameViewModel
    private lateinit var mNameTextView: TextView
    private lateinit var mTestButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_data_activity)

        mModel = ViewModelProviders.of(this).get(NameViewModel::class.java)
        mNameTextView = findViewById(R.id.mNameTextView)
        mTestButton = findViewById(R.id.testButton)

        val nameObserver = Observer<String> { newName ->
            mNameTextView.text = newName
        }
        mModel.currentName.observe(this, nameObserver)


        mTestButton.setOnClickListener {
            mModel.currentName.value = "test"
        }
    }

}