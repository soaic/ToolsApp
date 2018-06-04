package com.soaic.libcommon.recyclerview.decoration.sticky;

import android.view.View;

/**
 * Created by chenpengfei88 on 2018/4/12.
 */

public interface StickyView {

    /**
     * 是否是吸附view
     * @param view
     * @return
     */
    boolean isStickyView(View view);

    /**
     * 得到吸附view的itemType
     * @return
     */
    int getStickViewType();
}
