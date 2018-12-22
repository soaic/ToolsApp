package com.soaic.jetpackcomponent.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.soaic.libcommon.utils.Logger

class CompressWorker(context : Context, params : WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {
        Logger.d("doWork....")
        // Indicate success or failure with your return value:
        return Result.success()
    }

}