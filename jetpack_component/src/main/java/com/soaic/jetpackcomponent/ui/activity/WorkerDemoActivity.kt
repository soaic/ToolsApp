package com.soaic.jetpackcomponent.ui.activity

import android.os.Bundle
import android.widget.Button
import androidx.work.*
import com.soaic.jetpackcomponent.R
import com.soaic.jetpackcomponent.worker.CompressWorker
import com.soaic.jetpackcomponent.worker.MathWorker
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.utils.Logger
import java.util.*
import java.util.concurrent.TimeUnit

class WorkerDemoActivity: BasicActivity() {

    private lateinit var workA: OneTimeWorkRequest
    private lateinit var workB: OneTimeWorkRequest
    private lateinit var workC: OneTimeWorkRequest
    private lateinit var workD: OneTimeWorkRequest
    private lateinit var workE: OneTimeWorkRequest
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var testButton:Button

    override fun getContentView(): Int {
        return R.layout.worker_activity
    }

    override fun initVariables(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
        testButton = findViewById(R.id.test)

        //指定条件
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)  // 网络状态
                .setRequiresBatteryNotLow(true)                 // 不在电量不足时执行
                .setRequiresCharging(true)                      // 在充电时执行
                .setRequiresStorageNotLow(true)                 // 不在存储容量不足时执行
                //.setRequiresDeviceIdle(true)                    // 在待机状态下执行，需要 API 23
                .build()

        //一次的任务
        workA = OneTimeWorkRequest.Builder(CompressWorker::class.java).setConstraints(constraints).build()
        workB = OneTimeWorkRequest.Builder(CompressWorker::class.java).build()
        workC = OneTimeWorkRequest.Builder(CompressWorker::class.java).build()
        workD = OneTimeWorkRequest.Builder(CompressWorker::class.java).build()
        workE = OneTimeWorkRequest.Builder(MathWorker::class.java)
                .setInputData(Data.Builder()
                        .putInt(MathWorker.KEY_X_ARG, 1)
                        .putInt(MathWorker.KEY_Y_ARG, 2)
                        .putInt(MathWorker.KEY_Z_ARG, 3)
                        .build())
                .build()


        //循环任务 最小间隔为15分钟
        //Periodic work has a minimum interval of 15 minutes and it cannot have an initial delay.
        periodicWorkRequest = PeriodicWorkRequest.Builder(CompressWorker::class.java,15, TimeUnit.MINUTES).build()

    }

    override fun initEvents() {
        WorkManager.getInstance().getWorkInfoByIdLiveData(workA.id)
                .observe({ lifecycle }, { workInfo ->
                    // Do something with the status
                    if (workInfo != null && workInfo.state.isFinished) {
                        Logger.d("11workInfo")
                    }
                })

        WorkManager.getInstance().getWorkInfoByIdLiveData(workB.id)
                .observe({ lifecycle }, { workInfo ->
                    // Do something with the status
                    if (workInfo != null && workInfo.state.isFinished) {
                        Logger.d("12workInfo")
                    }
                })

        WorkManager.getInstance().getWorkInfoByIdLiveData(workC.id)
                .observe({ lifecycle }, { workInfo ->
                    // Do something with the status
                    if (workInfo != null && workInfo.state.isFinished) {
                        Logger.d("13workInfo")
                    }
                })

        WorkManager.getInstance().getWorkInfoByIdLiveData(workD.id)
                .observe({ lifecycle }, { workInfo ->
                    // Do something with the status
                    if (workInfo != null && workInfo.state.isFinished) {
                        Logger.d("14workInfo")
                    }
                })

        WorkManager.getInstance().getWorkInfoByIdLiveData(workE.id)
                .observe({ lifecycle }, { workInfo ->
                    // Do something with the status
                    if (workInfo != null && workInfo.state.isFinished) {
                        Logger.d("15workInfo="+workInfo.outputData.keyValueMap[MathWorker.KEY_RESULT])
                    }
                })

        WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.id)
                .observe({ lifecycle }, { workInfo ->
                    // Do something with the status
                    Logger.d("workInfo="+workInfo?.state)
                    if (workInfo != null && workInfo.state.isFinished) {
                        Logger.d("workInfo="+workInfo.id)
                    }
                })


        testButton.setOnClickListener {
            math()
        }
    }

    override fun loadData() {
    }

    private fun more() {
        WorkManager.getInstance().enqueue(periodicWorkRequest)
    }

    //执行workA
    private fun enqueue() {
        WorkManager.getInstance().enqueue(workA)
    }

    //先执行workA->workB->workC
    private fun chain() {
        WorkManager.getInstance().beginWith(workA).then(workB).then(workC).enqueue()
    }

    //先同时执行workA和workB，再执行workC，再执行workD
    private fun chain2() {
        WorkManager.getInstance()
                .beginWith(Arrays.asList(workA,workB))
                .then(workC)
                .then(workD)
                .enqueue()
    }

    //同时执行workA->workB 和 workC->workD
    private fun chain3() {
        val chain1 = WorkManager.getInstance().beginWith(workA).then(workB)
        val chain2 = WorkManager.getInstance().beginWith(workC).then(workD)
        WorkContinuation.combine(Arrays.asList(chain1,chain2)).enqueue()
    }

    private fun math() {
        WorkManager.getInstance().enqueue(workE)
    }

    private fun cancel() {
        //取消任务
        WorkManager.getInstance().cancelWorkById(workA.id)
    }
}