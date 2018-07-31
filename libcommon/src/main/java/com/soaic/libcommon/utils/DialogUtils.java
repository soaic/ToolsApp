package com.soaic.libcommon.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
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
