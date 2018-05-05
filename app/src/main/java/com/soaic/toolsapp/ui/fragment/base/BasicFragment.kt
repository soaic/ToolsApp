package com.soaic.toolsapp.ui.fragment.base

import android.app.Dialog
import android.content.Context
import com.soaic.libcommont.utils.ToastUtils
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.soaic.toolsapp.R


abstract class BasicFragment : Fragment() {
    private var mContentView: View? = null
    private var loadingDialog: Dialog? = null

    /**
     * 获取内容view
     * @return layoutResID
     */
    @get:LayoutRes
    protected abstract val contentView: Int

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            onUserVisible()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            onUserVisible()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            if (contentView == 0) {
                throw RuntimeException("getContentView() can't return zero")
            }
            mContentView = LayoutInflater.from(context).inflate(contentView, null)
            initVariables(savedInstanceState)
            initViews()
            initEvents()
            loadData()
        } else {
            val parent = mContentView!!.parent as ViewGroup
            parent.removeView(mContentView)
        }
        return mContentView
    }

    /** 变量参数初始化  */
    protected abstract fun initVariables(savedInstanceState: Bundle?)

    /** 初始化View控件  */
    protected abstract fun initViews()

    /** 给View控件添加事件监听器  */
    protected abstract fun initEvents()

    /** 处理数据，业务逻辑，接收数据传递  */
    protected abstract fun loadData()

    /** 当fragment对用户可见时，会调用该方法，可在该方法中懒加载网络数据  */
    protected abstract fun onUserVisible()

    /** 显示加载对话框  */
    fun showProgressDialog() {
        if (loadingDialog == null && activity != null) {
            loadingDialog = Dialog(activity, R.style.custom_dialog_style)
            loadingDialog!!.setContentView(R.layout.dialog_custom_loading)
            loadingDialog!!.setCanceledOnTouchOutside(false)
            loadingDialog!!.setCancelable(true)
        }
        if (activity != null && !loadingDialog!!.isShowing)
            loadingDialog!!.show()
    }

    /** 隐藏加载对话框  */
    fun hideProgressDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    fun startActivity(@NonNull openClass: Class<*>) {
        val intent = Intent(context, openClass)
        startActivity(intent)
    }

    fun startActivity(@NonNull openClass: Class<*>, @NonNull bundle: Bundle) {
        val intent = Intent(context, openClass)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun startActivityForResult(@NonNull openClass: Class<*>, @NonNull bundle: Bundle, requestCode: Int) {
        val intent = Intent(context, openClass)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    fun showToast(str: String) {
        ToastUtils.showShortToast(context, str)
    }

    fun showToast(@IdRes rid: Int) {
        ToastUtils.showShortToast(context, rid)
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return View
    </VT> */
    protected fun <VT : View> findViewById(@IdRes id: Int): VT {
        return mContentView!!.findViewById(id)
    }
}