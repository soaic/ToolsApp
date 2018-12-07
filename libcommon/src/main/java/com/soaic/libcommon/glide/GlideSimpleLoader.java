package com.soaic.libcommon.glide;

import android.content.Context;
import android.net.Uri;

import com.soaic.libcommon.weiget.imagewatcher.ImageWatcher;

public class GlideSimpleLoader implements ImageWatcher.Loader {
    @Override
    public void load(Context context, Uri uri, ImageWatcher.LoadCallback lc) {
        GlideUtil.display(context, uri, lc);
    }
}
