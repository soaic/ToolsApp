package com.soaic.toolsapp.ui.activity.location

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.soaic.libcommon.utils.LocationUtil
import com.soaic.libcommon.utils.PermissionsUtils
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.activity.base.BasicActivity

class LocationActivity : BasicActivity(){
    private lateinit var locationInfo: TextView
    private val REQUEST_PERMISSION_SETTING: Int = 1000

    override val contentView: Int
        get() = R.layout.activity_location

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        locationInfo = findViewById(R.id.locationInfo)
        initPermissions()
    }

    override fun initEvents() {

    }

    override fun loadData() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showPermissionsDeniedDialog() {
        val builder = MaterialDialog.Builder(this)
                .content("定位权限获取失败，请到系统设置开启权限！")
                .negativeText("取消")
                .positiveText("设置")
                .cancelable(false)
                .onNegative { _, _ ->  finish() }
                .onPositive { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                }
        val dialog = builder.build()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            initPermissions()
        }
    }

    private fun initPermissions() {

        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE)

        PermissionsUtils.getInstance().requestPermissions(this, 1000, permissions,
                object: PermissionsUtils.PermissionsResultAction{
                    override fun doExecuteSuccess(requestCode: Int) {
                        LocationUtil.getInstance().startLocation(applicationContext, object: LocationUtil.OnLocationListener{
                            override fun onLocation(location: MutableMap<String, Any>?) {
                                locationInfo.text = location!!["address"].toString()
                            }
                            override fun onError(errStr: String?, errCode: Int) {
                                locationInfo.text = errStr
                            }
                        })
                    }

                    override fun doExecuteFail(requestCode: Int) {
                        if(!PermissionsUtils.shouldShowRequestPermissionRationale(getActivity(), *permissions)){
                            finish()
                        }else{
                            showPermissionsDeniedDialog()
                        }
                    }
                })
    }
}