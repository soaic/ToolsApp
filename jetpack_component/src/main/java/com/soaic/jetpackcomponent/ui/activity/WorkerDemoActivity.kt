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
        //一次的任务
        workA = OneTimeWorkRequest.Builder(CompressWorker::class.java).build()
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


        //循环任务
        periodicWorkRequest = PeriodicWorkRequest.Builder(CompressWorker::class.java,5, TimeUnit.SECONDS).build()

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
                        Logger.d("14workInfo"+workInfo.outputData.keyValueMap)
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
            more()
        }
    }

    override fun loadData() {
    }

    private fun more(){
        WorkManager.getInstance().enqueue(periodicWorkRequest)
    }

    private fun enqueue(){
        WorkManager.getInstance().enqueue(workA)
    }

    private fun chain(){
        WorkManager.getInstance().beginWith(workA).then(workB).then(workC).enqueue()
    }

    private fun chain2(){
        WorkManager.getInstance()
                .beginWith(Arrays.asList(workA,workB))
                .then(workC)
                .then(workD)
                .enqueue()
    }

    private fun chain3(){
        val chain1 = WorkManager.getInstance().beginWith(workA).then(workB)
        val chain2 = WorkManager.getInstance().beginWith(workC).then(workD)
        WorkContinuation.combine(Arrays.asList(chain1,chain2)).enqueue()
    }

    private fun math(){
        WorkManager.getInstance().enqueue(workE)
    }

    private fun cancel(){
        //取消任务
        WorkManager.getInstance().cancelWorkById(workA.id)
    }
}