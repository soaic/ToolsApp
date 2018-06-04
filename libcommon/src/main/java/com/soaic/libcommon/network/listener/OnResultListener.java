package com.soaic.libcommon.network.listener;

/**
 * 网络请求监听
 * Created by XiaoSai on 2016/11/3.
 */
public interface OnResultListener<T> {

    /** 请求成功*/
    void onSuccess(T t);

    /** 请求失败 根据需求实现*/
    void onFailure(Throwable err);

}
