package com.soaic.libcommon.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.soaic.libcommon.R;

/**
 * glide配置
 *
 * 在AndroidManifest.xml中添加 meta-data
 */
public class SGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //解决设置TAG异常问题
        ViewTarget.setTagId(R.id.glide_tag_id);
        //设置格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
