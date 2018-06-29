package com.soaic.libcommon.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

public class DialogUtils {

    public static MaterialDialog getMaterialDialog(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("title")
                .content("content")
                .positiveText("确定");
        MaterialDialog dialog = builder.build();
        dialog.show();
        return dialog;
    }

}
