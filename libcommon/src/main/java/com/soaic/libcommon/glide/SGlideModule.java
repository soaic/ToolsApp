package com.soaic.libcommon.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.soaic.libcommon.R;
import com.soaic.libcommon.utils.Logger;

/**
 * glide配置
 *
 * 4.0以下在AndroidManifest.xml中添加 meta-data
 *
 * 4.0以上用@GlideModule添加
 */
@GlideModule
public class SGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //解决设置TAG异常问题
        ViewTarget.setTagId(R.id.glide_tag_id);
        builder.setDefaultRequestOptions(
                new RequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig());
        builder.setLogLevel(Log.DEBUG);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //registry.append(Api.GifResult.class, InputStream.class, new SGlideModule.Factory());
    }
}
