package com.soaic.toolsapp.ui.activity.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.soaic.libcommon.utils.AppUtils
import com.soaic.libcommon.utils.ToastUtils
import com.soaic.toolsapp.R
import com.soaic.libcommon.fragment.BackHandlerHelper




abstract class BasicActivity : AppCompatActivity(), BGASwipeBackHelper.Delegate {
    private var loadingDialog: Dialog? = null
    private lateinit var mSwipeBackHelper: BGASwipeBackHelper

    /** 获取内容Layout  */
    @get:LayoutRes
    protected abstract val contentView: Int

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        initSwipeBackFinish()
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

    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true)
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false)
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


    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    override fun isSupportSwipeBack(): Boolean {
        return true
    }
    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {}
    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    override fun onSwipeBackLayoutCancel() {}
    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }
    override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }

}