package com.soaic.toolsapp.ui.activity.born

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.utils.TimeUtils
import com.soaic.toolsapp.R
import java.util.*

class BornActivity : BasicActivity() {

    private var bornDate = TimeUtils.string2Date("19930318","yyyyMMdd")
    private lateinit var bornAge: TextView
    private lateinit var bornYear: TextView
    private lateinit var bornMonth: TextView
    private lateinit var bornWeek: TextView
    private lateinit var bornDay: TextView
    private lateinit var bornHour: TextView
    private lateinit var bornMinute: TextView
    private lateinit var bornYearTime: TextView
    private val handler = Handler()
    private val timer = Timer()
    private val timerTask = object : TimerTask(){
        override fun run() {
            handler.post {
                initTimeData()
            }
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_born
    }

    override fun initVariables(savedInstanceState: Bundle?) {
        bornDate = TimeUtils.string2Date(intent.getStringExtra("date"),"yyyyMMdd")
    }

    override fun initViews() {
        bornAge = findViewById(R.id.bornAge)
        bornYear = findViewById(R.id.bornYear)
        bornMonth = findViewById(R.id.bornMonth)
        bornWeek = findViewById(R.id.bornWeek)
        bornDay = findViewById(R.id.bornDay)
        bornHour = findViewById(R.id.bornHour)
        bornMinute = findViewById(R.id.bornMinute)
    }

    override fun initEvents() {
        timer.schedule(timerTask, 1000, 1000)
    }

    override fun loadData() {
        initTimeData()
    }

    private fun initTimeData() {
        val nowDate = Date()
        bornAge.text = String.format(getString(R.string.born_age), TimeUtils.getDateBetweenYearDecimal(bornDate, nowDate).toString())
        bornYear.text = String.format(getString(R.string.born_year), TimeUtils.getDateBetweenYear(bornDate, nowDate).toString())
        bornMonth.text = String.format(getString(R.string.born_month), TimeUtils.getDateBetweenMonth(bornDate, nowDate).toString())
        bornWeek.text = String.format(getString(R.string.born_week), TimeUtils.getDateBetweenWeek(bornDate, nowDate).toString())
        bornDay.text = String.format(getString(R.string.born_day), TimeUtils.getDateBetweenDay(bornDate, nowDate).toString())
        bornHour.text = String.format(getString(R.string.born_hour), TimeUtils.getDateBetweenHour(bornDate, nowDate).toString())
        bornMinute.text = String.format(getString(R.string.born_minute), TimeUtils.getDateBetweenMinute(bornDate, nowDate).toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}