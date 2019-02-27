package com.soaic.jetpackcomponent.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MathWorker(context : Context, params : WorkerParameters) : Worker(context, params) {

    companion object {
        // Define the parameter keys:
        const val KEY_X_ARG = "X"
        const val KEY_Y_ARG = "Y"
        const val KEY_Z_ARG = "Z"
        // ...and the result key:
        const val KEY_RESULT = "result"
    }

    override fun doWork(): Result {
        val x = inputData.getInt(KEY_X_ARG, 0)
        val y = inputData.getInt(KEY_Y_ARG, 0)
        val z = inputData.getInt(KEY_Z_ARG, 0)
        // ...do the math...
        val result = myCrazyMathFunction(x, y, z)
        //...set the output, and we're done!
        val output: Data = Data.Builder().putInt(KEY_RESULT, result).build()

        return Result.success(output)
    }

    private fun myCrazyMathFunction(x: Int, y: Int, z: Int): Int{
        return x + y + z
    }
}