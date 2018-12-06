package com.soaic.libcommon.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soaic.libcommon.R;
import com.soaic.libcommon.fragment.BackHandledFragment;
import com.soaic.libcommon.utils.ToastUtils;

public abstract class BasicFragment extends BackHandledFragment {

    private View mContentView;
    private Dialog loadingDialog;

    @LayoutRes
    protected abstract int getContentView();

    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.onUserVisible();
        }
    }

    @Override public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            this.onUserVisible();
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            if (getContentView() == 0) {
                throw new RuntimeException("getContentView() can't return zero");
            }
            mContentView = LayoutInflater.from(getContext()).inflate(getContentView(), null);
            initVariables(savedInstanceState);
            initViews();
            initEvents();
            loadData();
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            parent.removeView(mContentView);
        }
        return mContentView;
    }

    /** 变量参数初始化  */
    protected abstract void initVariables(Bundle bundle);

    /** 初始化View控件  */
    protected abstract void initViews();

    /** 给View控件添加事件监听器  */
    protected abstract void initEvents();

    /** 处理数据，业务逻辑，接收数据传递  */
    protected abstract void loadData();

    /** 当fragment对用户可见时，会调用该方法，可在该方法中懒加载网络数据  */
    protected abstract void onUserVisible();

    /** 显示加载对话框  */
    protected void showProgressDialog() {
        if (loadingDialog == null && getActivity() != null) {
            loadingDialog = new Dialog(getActivity(), R.style.custom_dialog_style);
            loadingDialog.setContentView(R.layout.dialog_custom_loading);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setCancelable(true);
        }
        if (getActivity() != null && !loadingDialog.isShowing())
            loadingDialog.show();
    }

    /** 隐藏加载对话框  */
    protected void hideProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected void startActivity(@NonNull Class<?> openClass) {
        Intent intent = new Intent(getContext(), openClass);
        startActivity(intent);
    }

    protected void startActivity(@NonNull Class<?> openClass, @NonNull Bundle bundle) {
        Intent intent = new Intent(getContext(), openClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivityForResult(@NonNull Class<?> openClass, @NonNull Bundle bundle, int requestCode) {
        Intent intent = new Intent(getContext(), openClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected void showToast(String str) {
        ToastUtils.showShortToast(getContext(), str);
    }

    protected void showToast(@IdRes int rid) {
        ToastUtils.showShortToast(getContext(), rid);
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return View
     */
    protected <VT extends View> VT findViewById(@IdRes int id){
        return mContentView.findViewById(id);
    }
}
