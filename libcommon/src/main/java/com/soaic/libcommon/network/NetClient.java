package com.soaic.libcommon.network;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.soaic.libcommon.network.cookie.CookiesManager;
import com.soaic.libcommon.network.https.OkHttpSSLSocketFactory;
import com.soaic.libcommon.network.interceptor.HeaderInterceptor;
import com.soaic.libcommon.network.interceptor.HttpLoggingInterceptor;
import com.soaic.libcommon.network.listener.OnResultListener;
import com.soaic.libcommon.network.util.AppUtil;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * net request client
 *
 * @author Soaic
 */
public class NetClient {
    private String mBaseUrl = "https://soaic.com/";
    private Builder mBuilder;
    private Retrofit mRetrofit;
    private Call<ResponseBody> mCall;
    private Gson mGson;
    private OkHttpClient mClient;
    private CookiesManager cookiesManager;
    private HttpLoggingInterceptor interceptor;
    private OkHttpClient.Builder okHttpBuilder;
    private NetworkErrorException networkErrorException;

    private NetClient() {
        mGson = new Gson();
        networkErrorException = new NetworkErrorException("can't connect network!");
        //日志拦截配置
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)       //设置连接超时10s
                .readTimeout(10, TimeUnit.SECONDS)          //设置读取超时10s
                .writeTimeout(10, TimeUnit.SECONDS)         //设置写入超时10s
                .sslSocketFactory(OkHttpSSLSocketFactory.getSocketFactory(), OkHttpSSLSocketFactory.getTrustManager()) //设置SSL
                .hostnameVerifier(OkHttpSSLSocketFactory.getHostnameVerifier())
                .addInterceptor(interceptor);    //添加日志拦截器（该方法也可以设置公共参数，头信息）
    }

    /**
     * 设置日志级别
     */
    public void setLoggingLevel(HttpLoggingInterceptor.Level level) {
        interceptor.setLevel(level);
    }

    /**
     * 获取Cookies管理
     */
    public CookiesManager getCookiesManager() {
        return cookiesManager;
    }

    private static NetClient getInstance() {
        return SingleLoader.INSTANCE;
    }

    private static class SingleLoader {
        private final static NetClient INSTANCE = new NetClient();
    }

    private void initData(@NonNull Builder builder) {
        if (builder.context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        if (TextUtils.isEmpty(builder.url)) {
            throw new IllegalArgumentException("url can't be null");
        }
        this.mBuilder = builder;
        if (mRetrofit == null || mClient == null) {
            try {
                //cookies管理
                cookiesManager = new CookiesManager(builder.context);
                mClient = okHttpBuilder
                        .cache(new Cache(builder.context.getCacheDir(), 10 * 1024 * 1024))
                        .addInterceptor(new HeaderInterceptor())
                        .cookieJar(cookiesManager)
                        .build();
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(mBaseUrl)
                        .client(mClient)
                        .build();
            } catch (Exception ignored) {
            }
        }
    }

    public <T> NetClient get(@NonNull Class<T> clazz, OnResultListener<T> onResultListener) {
        if (!AppUtil.isNetworkAvailable(mBuilder.context)) {
            handlerError(networkErrorException, onResultListener);
            return this;
        }
        if (mRetrofit != null) {
            mCall = mRetrofit.create(Params.class).paramsGet(mBuilder.url, mBuilder.params);
            request(clazz, onResultListener);
        }
        return this;
    }

    public <T> NetClient post(@NonNull Class<T> clazz, OnResultListener<T> onResultListener) {
        if (!AppUtil.isNetworkAvailable(mBuilder.context)) {
            handlerError(networkErrorException, onResultListener);
            return this;
        }
        if (mRetrofit != null) {
            if (mBuilder.bodyParams.size() > 0) {
                for (String key : mBuilder.params.keySet()) {
                    RequestBody value = RequestBody.create(MediaType.parse("text/plain"), mBuilder.params.get(key));
                    mBuilder.bodyParams.put(key, value);
                }
                mCall = mRetrofit.create(Params.class).paramsPostUpload(mBuilder.url, mBuilder.bodyParams);
            } else if (!TextUtils.isEmpty(mBuilder.bodyJson)) {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), mBuilder.bodyJson);
                mCall = mRetrofit.create(Params.class).paramsPostJSON(mBuilder.url, body);
            } else {
                mCall = mRetrofit.create(Params.class).paramsPost(mBuilder.url, mBuilder.params);
            }
            request(clazz, onResultListener);
        }
        return this;
    }

    public <T> NetClient put(@NonNull Class<T> clazz, OnResultListener<T> onResultListener) {
        if (!AppUtil.isNetworkAvailable(mBuilder.context)) {
            handlerError(networkErrorException, onResultListener);
            return this;
        }
        if (mRetrofit != null) {
            if (mBuilder.bodyParams.size() > 0) {
                for (String key : mBuilder.params.keySet()) {
                    RequestBody value = RequestBody.create(MediaType.parse("text/plain"), mBuilder.params.get(key));
                    mBuilder.bodyParams.put(key, value);
                }
                mCall = mRetrofit.create(Params.class).paramsPutUpload(mBuilder.url, mBuilder.bodyParams);
            } else if (!TextUtils.isEmpty(mBuilder.bodyJson)) {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), mBuilder.bodyJson);
                mCall = mRetrofit.create(Params.class).paramsPutJSON(mBuilder.url, body);
            } else {
                mCall = mRetrofit.create(Params.class).paramsPut(mBuilder.url, mBuilder.params);
            }
            request(clazz, onResultListener);
        }
        return this;
    }

    public <T> NetClient delete(@NonNull Class<T> clazz, OnResultListener<T> onResultListener) {
        if (!AppUtil.isNetworkAvailable(mBuilder.context)) {
            handlerError(networkErrorException, onResultListener);
            return this;
        }
        if (mRetrofit != null) {
            mCall = mRetrofit.create(Params.class).paramsDelete(mBuilder.url, mBuilder.params);
            request(clazz, onResultListener);
        }
        return this;
    }

    public void cancelRequest() {
        if (mCall != null && mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private <T> void request(final @NonNull Class<T> clazz, final OnResultListener<T> onResultListener) {
        if (mCall == null) {
            return;
        }
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (clazz.getName().equals("java.lang.String")) {
                        onResultListener.onSuccess((T) response.body().string());
                    } else {
                        onResultListener.onSuccess(mGson.fromJson(response.body().string(), clazz));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handlerError(e, onResultListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable e) {
                e.printStackTrace();
                handlerError(e, onResultListener);
            }
        });
    }

    private <T> void handlerError(Throwable err, OnResultListener<T> onResultListener) {
        onResultListener.onFailure(err);
    }

    public static final class Builder {
        private String url = "";
        private LinkedHashMap<String, String> params = new LinkedHashMap<>(); //LinkedHashMap顺序排列
        private LinkedHashMap<String, RequestBody> bodyParams = new LinkedHashMap<>();
        private String bodyJson = "";
        private Context context;

        public Builder(Context bdContext) {
            this.context = bdContext;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder param(String key, String value) {
            if (key != null && value != null) {
                params.put(key, value);
            }
            return this;
        }

        public Builder params(Map<String, String> maps) {
            if (maps != null) {
                for (String key : maps.keySet()) {
                    if (key != null && maps.get(key) != null) {
                        params.put(key, maps.get(key));
                    }
                }
            }
            return this;
        }

        public Builder paramsJSON(String json) {
            this.bodyJson = json;
            return this;
        }

        public Builder files(Map<String, File> mapFiles) {
            if (mapFiles != null) {
                for (String key : mapFiles.keySet()) {
                    if (key != null && mapFiles.get(key) != null) {
                        bodyParams.put(key + "\"; filename=\"" + mapFiles.get(key).getName(), RequestBody.create(MediaType.parse("application/octet-stream"), mapFiles.get(key)));
                    }
                }
            }
            return this;
        }

        public Builder file(String key, File file) {
            if (key != null && file != null) {
                bodyParams.put(key + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }
            return this;
        }

        public NetClient build() {
            NetClient.getInstance().initData(this);
            return NetClient.getInstance();
        }
    }
}
