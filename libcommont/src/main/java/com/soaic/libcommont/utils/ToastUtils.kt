package com.soaic.libcommont.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast


object ToastUtils {

    private var sToast: Toast? = null
    private val sHandler = Handler(Looper.getMainLooper())
    private var isJumpWhenMore: Boolean = false

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *
     * `true`: 弹出新吐司<br></br>`false`: 只修改文本内容
     *
     * 如果为`false`的话可用来做显示任意时长的吐司
     */
    fun init(isJumpWhenMore: Boolean) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    fun showShortToastSafe(context: Context, text: CharSequence) {
        sHandler.post(Runnable { showToast(context, text, Toast.LENGTH_SHORT) })
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    fun showShortToastSafe(context: Context, resId: Int) {
        sHandler.post(Runnable { showToast(context, resId, Toast.LENGTH_SHORT) })
    }


    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    fun showLongToastSafe(context: Context, text: CharSequence) {
        sHandler.post(Runnable { showToast(context, text, Toast.LENGTH_LONG) })
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    fun showLongToastSafe(context: Context, resId: Int) {
        sHandler.post(Runnable { showToast(context, resId, Toast.LENGTH_LONG) })
    }


    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    fun showShortToast(context: Context, text: CharSequence) {
        showToast(context, text, Toast.LENGTH_SHORT)
    }

    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    fun showShortToast(context: Context, resId: Int) {
        showToast(context, resId, Toast.LENGTH_SHORT)
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    fun showLongToast(context: Context, text: CharSequence) {
        showToast(context, text, Toast.LENGTH_LONG)
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    fun showLongToast(context: Context, resId: Int) {
        showToast(context, resId, Toast.LENGTH_LONG)
    }


    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param text     文本
     * @param duration 显示时长
     */
    @SuppressLint("ShowToast")
    private fun showToast(context: Context, text: CharSequence, duration: Int) {
        if (isJumpWhenMore) cancelToast()
        if (sToast == null) {
            sToast = Toast.makeText(context.applicationContext, text, duration)
        } else {
            sToast!!.setText(text)
            sToast!!.duration = duration
        }
        sToast!!.show()
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private fun showToast(context: Context, resId: Int, duration: Int) {
        showToast(context, context.resources.getText(resId).toString(), duration)
    }


    /**
     * 取消吐司显示
     */
    private fun cancelToast() {
        if (sToast != null) {
            sToast!!.cancel()
            sToast = null
        }
    }

}