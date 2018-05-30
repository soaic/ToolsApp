package com.soaic.libcommont.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OktHttp统一header设置拦截器
 * Created by DDY-03 on 2017/10/12.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder request = original.newBuilder();
        //request.headers(Headers.of(Configure.getRetrofitHeader()));
        request.method(original.method(), original.body());
        return chain.proceed(request.build());
    }
}
