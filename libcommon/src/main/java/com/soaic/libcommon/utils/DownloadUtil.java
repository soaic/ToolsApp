package com.soaic.libcommon.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;

/**
 * val url = "http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_7.15.1.apk"
 * val download = DownloadUtil(activity!!.applicationContext)
 * download.startDownload(url)
 * download.queryProcess()
 */
public class DownloadUtil {

    private long id;
    private DownloadManager mDownloadManager;
    private DownloadReceiver mReceiver;
    private Context mContext;

    public DownloadUtil(Context context){
        this.mContext = context;
        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void startDownload(String url){
        deleteOldUpdates();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, "update.apk");
        request.setTitle("下载更新...");
        id = mDownloadManager.enqueue(request);
        registerDownloadListener(mContext);
    }

    public void queryProcess(){
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(id);
        Cursor cursor = mDownloadManager.query(query);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String bytesDownload = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                String description = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
                String id = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                String localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                String mimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                String status = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                String totalSize = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                Logger.i("MainActivity", "bytesDownload:" + bytesDownload);
                Logger.i("MainActivity", "description:" + description);
                Logger.i("MainActivity", "id:" + id);
                Logger.i("MainActivity", "localUri:" + localUri);
                Logger.i("MainActivity", "mimeType:" + mimeType);
                Logger.i("MainActivity", "title:" + title);
                Logger.i("MainActivity", "status:" + status);
                Logger.i("MainActivity", "totalSize:" + totalSize);
            }
        }
    }

    private void stopDownload(){
        mDownloadManager.remove(id);
        unRegisterDownloadListener();
    }

    public void deleteOldUpdates() {
        File dir = mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        final File[] oldUpdates = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("update[0-9-]*\\.apk");
            }
        });
        if (oldUpdates == null || oldUpdates.length == 0) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    for (File file : oldUpdates) {
                        file.delete();
                    }
                } catch (Throwable ignored) { }
            }
        }.start();
    }

    private void registerDownloadListener(Context context){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        mReceiver = new DownloadReceiver();
        context.registerReceiver(mReceiver, intentFilter);
    }

    private void unRegisterDownloadListener(){
        mContext.unregisterReceiver(mReceiver);
    }

    class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                long did = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if(id == did) {
                    installApk(did);
                    unRegisterDownloadListener();
                }
            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void installApk(long downloadApkId) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        try {
            File apkFile = queryDownloadedApk(mContext, downloadApkId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", apkFile);
                install.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(install);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private File queryDownloadedApk(Context context, long downloadId) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadId != -1 && downloader != null) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }
}
