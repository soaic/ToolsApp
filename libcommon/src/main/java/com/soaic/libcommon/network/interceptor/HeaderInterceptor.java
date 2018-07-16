package com.soaic.libcommon.network.interceptor;

import android.os.Build;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OktHttp统一header设置拦截器
 * Created by XiaoSai on 2017/10/12.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder request = original.newBuilder();
        //request.headers(Headers.of(Configure.getRetrofitHeader()));
        request.addHeader("User-Agent", Build.BRAND + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE);
        request.method(original.method(), original.body());
        return chain.proceed(request.build());
    }
}
