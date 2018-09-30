package com.soaic.toolsapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.soaic.libcommon.utils.*
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.activity.born.BornActivity
import com.soaic.toolsapp.ui.activity.check.CheckActivity
import com.soaic.toolsapp.ui.activity.location.LocationActivity
import com.soaic.toolsapp.ui.fragment.base.BasicFragment
import java.util.*


class MoreFragment: BasicFragment(), CameraUtils.CameraResult {
    lateinit var moreLocation: TextView
    lateinit var moreFm: TextView
    lateinit var moreNovel: TextView
    lateinit var moreExpress: TextView
    lateinit var moreWeather: TextView
    lateinit var moreCheck: TextView
    lateinit var moreBorn: TextView
    lateinit var cameraUtils: CameraUtils
    lateinit var moreContentFragment: FrameLayout
    lateinit var testImage: ImageView

    companion object {
        fun newInstance(): MoreFragment{
            return MoreFragment()
        }
    }

    override val contentView: Int
        get() = R.layout.fragment_more

    override fun initVariables(savedInstanceState: Bundle?) {
        cameraUtils = CameraUtils(this, activity)
    }

    override fun initViews() {
        moreLocation = findViewById(R.id.moreLocation)
        moreFm = findViewById(R.id.moreFm)
        moreNovel = findViewById(R.id.moreNovel)
        moreExpress = findViewById(R.id.moreExpress)
        moreWeather = findViewById(R.id.moreWeather)
        moreCheck = findViewById(R.id.moreCheck)
        moreBorn = findViewById(R.id.moreBorn)
        moreContentFragment = findViewById(R.id.moreContentFragment)
        testImage = findViewById(R.id.testImage)
    }

    override fun initEvents() {
        moreLocation.setOnClickListener {
            startActivity(LocationActivity::class.java)
        }
        moreFm.setOnClickListener {
            cameraUtils.getPhoto2CameraCrop(FileUtils.getTempFilePath(activity))
        }
        moreNovel.setOnClickListener {

        }
        moreExpress.setOnClickListener {

        }
        moreWeather.setOnClickListener {
            scan()
        }
        moreCheck.setOnClickListener{
            startActivity(CheckActivity::class.java)
        }
        moreBorn.setOnClickListener {
            born()
        }
    }

    override fun onCameraSuccess(filePath: String?) {
        try {
            var bitmap = BitmapUtils.getBitmapFromFile(filePath)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCameraFail(message: String?) {

    }

    private fun scan(){
        cameraUtils.getPhoto2AlbumCrop()
    }

    private fun born() {
        val calendarSelect = Calendar.getInstance()
        calendarSelect.time = TimeUtils.string2Date("1990-01-01", "yyyy-MM-dd")
        val calendarEnd  = Calendar.getInstance()
        calendarEnd.time = Date()
        PickerUtils.showTimeYMDPicker(activity, "请选择你的生日", calendarSelect,null, calendarEnd) { date ->
            val bundle = Bundle()
            bundle.putString("date", TimeUtils.date2String(date, "yyyyMMdd"))
            startActivity(BornActivity::class.java, bundle)
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

    fun goNextFragment(fragment: BasicFragment, tag: String){
        moreContentFragment.visibility = View.VISIBLE
        childFragmentManager.beginTransaction().add(R.id.moreContentFragment, fragment, tag).addToBackStack(null).commitAllowingStateLoss()
    }


}