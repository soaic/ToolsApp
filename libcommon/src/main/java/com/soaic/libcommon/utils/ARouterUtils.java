package com.soaic.libcommon.utils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.soaic.libcommon.base.BasicActivity;
import com.soaic.libcommon.base.BasicFragment;

/**
 * ARouter帮助类
 */
public class ARouterUtils {

    /**
     * 根据path返回Fragment
     *
     * @param path path
     * @return fragment
     */
    public static BasicFragment getFragment(String path) {
        return (BasicFragment) ARouter.getInstance()
                .build(path)
                .navigation();
    }

    /**
     * 根据path返回Activity
     *
     * @param path path
     * @return Activity
     */
    public static BasicActivity getActivity(String path) {
        return (BasicActivity) ARouter.getInstance()
                .build(path)
                .navigation();
    }
}
