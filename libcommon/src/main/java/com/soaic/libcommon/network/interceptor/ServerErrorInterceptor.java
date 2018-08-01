package com.soaic.libcommon.network.interceptor;

public interface ServerErrorInterceptor {
    <T> Boolean isServerError(T t);
    Throwable getServerError();
}
