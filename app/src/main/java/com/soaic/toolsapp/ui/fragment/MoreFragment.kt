package com.soaic.toolsapp.ui.fragment

import android.Manifest
import android.os.Bundle
import android.widget.TextView
import com.soaic.libcommon.utils.LocationUtil
import com.soaic.libcommon.utils.PermissionsUtils
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.fragment.base.BasicFragment

class MoreFragment: BasicFragment() {

    lateinit var moreLocation: TextView
    lateinit var moreFm: TextView
    lateinit var moreNovel: TextView
    lateinit var moreExpress: TextView
    lateinit var moreWeather: TextView

    companion object {
        fun newInstance(): MoreFragment{
            return MoreFragment()
        }
    }

    override val contentView: Int
        get() = R.layout.fragment_more

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        moreLocation = findViewById(R.id.moreLocation)
        moreFm = findViewById(R.id.moreFm)
        moreNovel = findViewById(R.id.moreNovel)
        moreExpress = findViewById(R.id.moreExpress)
        moreWeather = findViewById(R.id.moreWeather)
    }

    override fun initEvents() {
        moreLocation.setOnClickListener { view ->
            view as TextView
            PermissionsUtils.getInstance().requestPermissions(activity, 1000,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_WIFI_STATE),
                    object: PermissionsUtils.PermissionsResultAction{
                        override fun doExecuteFail(requestCode: Int) {
                            showToast(requestCode.toString())
                        }

                        override fun doExecuteSuccess(requestCode: Int) {
                            LocationUtil.getInstance().startLocation(activity, object: LocationUtil.OnLocationListener{
                                override fun onLocation(location: MutableMap<String, Any>?) {
                                    showToast(location!!["address"].toString())
                                }

                                override fun onError(errStr: String?, errCode: Int) {
                                    showToast(errStr!!)
                                }

                            })
                        }

                    })

        }
        moreFm.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
        moreNovel.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
        moreExpress.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
        moreWeather.setOnClickListener { view ->
            view as TextView
            showToast(view.text as String)
        }
    }

    override fun loadData() {

    }

    override fun onUserVisible() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}