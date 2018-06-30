package com.soaic.libcommon.network.cookie;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * TODO Cookies管理
 *
 * @author XiaoSai
 * @version V1.0
 * @since 2017/3/2.
 */

public class CookiesManager implements CookieJar{
    
    private final PersistentCookieStore cookieStore;
    
    public CookiesManager(Context context){
        cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }
    
    public void cleanCookies(){
        cookieStore.removeAll();
    }
    
    public List<Cookie> getCookies(){
        return cookieStore.getCookies();
    }
}
