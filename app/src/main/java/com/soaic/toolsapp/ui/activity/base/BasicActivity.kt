package com.soaic.toolsapp.ui.activity.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import com.soaic.libcommon.utils.ToastUtils
import com.soaic.toolsapp.R


abstract class BasicActivity : AppCompatActivity() {
    private var loadingDialog: Dialog? = null

    /** 获取内容Layout  */
    @get:LayoutRes
    protected abstract val contentView: Int

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (contentView == 0) {
            throw RuntimeException("getContentView() can't return zero")
        }
        super.setContentView(contentView)
        initVariables(savedInstanceState)
        initViews()
        initEvents()
        loadData()
    }

    /**
     * 变量初始化
     * @param savedInstanceState savedInstanceState
     */
    protected abstract fun initVariables(savedInstanceState: Bundle?)

    /** view初始化  */
    protected abstract fun initViews()

    /** 初始化事件  */
    protected abstract fun initEvents()

    /** 加载数据  */
    protected abstract fun loadData()

    /** 显示加载对话框  */
    fun showProgressDialog() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(this, R.style.custom_dialog_style)
            loadingDialog!!.setContentView(R.layout.dialog_custom_loading)
            loadingDialog!!.setCanceledOnTouchOutside(false)
            loadingDialog!!.setCancelable(true)
        }
        if (!loadingDialog!!.isShowing)
            loadingDialog!!.show()
    }

    /** 隐藏加载对话框  */
    fun hideProgressDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    fun startActivity(openClass: Class<*>) {
        val intent = Intent(this, openClass)
        startActivity(intent)
    }

    fun startActivity(openClass: Class<*>, bundle: Bundle) {
        val intent = Intent(this, openClass)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun startActivityForResult(openClass: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent(this, openClass)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    fun showToast(str: String) {
        ToastUtils.showShortToast(this, str)
    }

    fun showToast(@StringRes rid: Int) {
        ToastUtils.showShortToast(this, rid)
    }

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_UP) {
//            //点击空白处隐藏软键盘
//            val v = currentFocus
//            if (KeyBoardUtils.isShouldHideKeyboard(v, ev)) {
//                KeyBoardUtils.hideSoftInput(this)
//            }
//        }
//        return super.dispatchTouchEvent(ev)
//    }

}