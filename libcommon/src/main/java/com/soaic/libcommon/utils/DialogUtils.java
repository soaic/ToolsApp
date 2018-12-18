package com.soaic.libcommon.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;

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


    public static MaterialDialog getMaterialDialog(Context context, String title, String content) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .content(Html.fromHtml(content.replace("\\n", "<br/>")))
                .positiveText("确定");
        MaterialDialog dialog = builder.build();
        dialog.show();
        return dialog;
    }

    public static MaterialDialog getMaterialDialog(Context context, String title, String content, final OnDialogButtonCallBack onPositive) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(Html.fromHtml(content.replace("\\n", "<br/>")))
                .title(title)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(onPositive != null){
                            onPositive.onClick(dialog);
                        }
                    }
                });
        MaterialDialog dialog = builder.build();
        dialog.show();
        return dialog;
    }

    public static MaterialDialog getMaterialDialog(Context context, View customView, String title, String content, final OnDialogButtonCallBack onPositive) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(customView, true)
                .title(title)
                .content(Html.fromHtml(content.replace("\\n", "<br/>")))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(onPositive != null){
                            onPositive.onClick(dialog);
                        }
                    }
                })
                .positiveText("确定");
        MaterialDialog dialog = builder.build();
        dialog.show();
        return dialog;
    }

    public interface OnDialogButtonCallBack{
        void onClick(MaterialDialog dialog);
    }
}
