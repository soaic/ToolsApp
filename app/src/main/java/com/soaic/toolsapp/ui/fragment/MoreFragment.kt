package com.soaic.toolsapp.ui.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.soaic.libcommon.utils.*
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.fragment.base.BasicFragment

class MoreFragment: BasicFragment() {

    lateinit var moreLocation: TextView
    lateinit var moreFm: TextView
    lateinit var moreNovel: TextView
    lateinit var moreExpress: TextView
    lateinit var moreWeather: TextView
    lateinit var cameraUtils: CameraUtils
    lateinit var testImage: ImageView

    companion object {
        fun newInstance(): MoreFragment{
            return MoreFragment()
        }
    }

    override val contentView: Int
        get() = R.layout.fragment_more

    override fun initVariables(savedInstanceState: Bundle?) {
        cameraUtils = CameraUtils(object: CameraUtils.CameraResult{
            override fun onCameraSuccess(filePath: String?) {
                testImage.setImageURI(Uri.parse(filePath))
                LogUtils.d(filePath)
            }

            override fun onCameraFail(message: String?) {
                LogUtils.d(message)
            }

        },activity)
    }

    override fun initViews() {
        moreLocation = findViewById(R.id.moreLocation)
        moreFm = findViewById(R.id.moreFm)
        moreNovel = findViewById(R.id.moreNovel)
        moreExpress = findViewById(R.id.moreExpress)
        moreWeather = findViewById(R.id.moreWeather)
        testImage = findViewById(R.id.testImage)

        testImage.setImageResource(ConvertUtils.resStrToInt(activity,"R.mipmap.ic_launcher"))
    }

    override fun initEvents() {
        moreLocation.setOnClickListener {
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
        moreFm.setOnClickListener {
            cameraUtils.getPhoto2CameraCrop(FileUtils.getTempFilePath(activity))
        }
        moreNovel.setOnClickListener {
            val url = "http://10.3.0.6:10000/sst-2.4.9.259_dev.apk"
            val download = DownloadUtil(activity.applicationContext)
            download.startDownload(url)
            download.queryProcess()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraUtils.onActivityResult(requestCode, resultCode, data)
    }
}