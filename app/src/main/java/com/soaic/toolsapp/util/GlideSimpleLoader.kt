package com.soaic.toolsapp.util

import android.content.Context
import android.net.Uri
import com.soaic.libcommon.glide.GlideUtil
import com.soaic.libcommon.weiget.imagewatcher.ImageWatcher

class GlideSimpleLoader: ImageWatcher.Loader {

    override fun load(context: Context?, uri: Uri?, lc: ImageWatcher.LoadCallback?) {
        GlideUtil.display(context, uri, lc)
    }

}