package com.soaic.libcommon.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

public class CameraUtils {

    //调用系统相机的Code
    private static final int REQUEST_TAKE_PHOTO_CODE = 1001;

    //拍照裁剪的Code
    private static final int REQUEST_TAKE_PHOTO_CROP_CODE = 1003;

    //调用系统图库的Code
    private static final int REQUEST_TAKE_PICTURE_CODE = 1002;

    //调用系统图库裁剪Code
    private static final int REQUEST_TAKE_PICTURE_CROP_CODE = 1004;

    //裁剪的Code
    private static final int REQUEST_TAKE_CROP_CODE = 1005;

    //截取图片的高度
    private static final int REQUEST_HEIGHT = 400;

    //截取图片的宽度
    private static final int REQUEST_WIDTH = 400;

    //用来存储照片的URi
    private Uri photoURI;

    //调用照片的Activity
    private Activity activity;

    //回调函数
    private CameraResult cameraResult;

    public CameraUtils(CameraResult cameraResult, Activity activity) {
        this.cameraResult = cameraResult;
        this.activity = activity;
    }

    /**
     * 拍照
     */
    public void getPhoto2Camera(final String path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsUtils.getInstance().requestPermissions(activity, 100,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    new PermissionsUtils.PermissionsResultAction() {
                        @Override
                        public void doExecuteSuccess(int requestCode) {
                            Uri uri = FileUtils.getFileUri(activity, new File(path));
                            activity.startActivityForResult(startTakePhoto(uri), REQUEST_TAKE_PHOTO_CODE);
                        }

                        @Override
                        public void doExecuteFail(int requestCode, boolean isShowPermissionsDialog) {
                            cameraResult.onCameraFail("获取权限失败");
                        }
                    });
        } else {
            Uri uri = FileUtils.getFileUri(activity, new File(path));
            activity.startActivityForResult(startTakePhoto(uri), REQUEST_TAKE_PHOTO_CODE);
        }

    }

    /**
     * 拍照后截屏
     */
    public void getPhoto2CameraCrop(final String path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsUtils.getInstance().requestPermissions(activity, 101,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    new PermissionsUtils.PermissionsResultAction() {
                        @Override
                        public void doExecuteSuccess(int requestCode) {
                            Uri uri = FileUtils.getFileUri(activity, new File(path));
                            Intent intent = startTakePhoto(uri);
                            activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO_CROP_CODE);
                        }

                        @Override
                        public void doExecuteFail(int requestCode, boolean isShowPermissionsDialog) {
                            cameraResult.onCameraFail("获取权限失败");
                        }
                    });
        } else {
            Uri uri = FileUtils.getFileUri(activity, new File(path));
            Intent intent = startTakePhoto(uri);
            activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO_CROP_CODE);
        }

    }

    /**
     * 获取系统相册
     */
    public void getPhoto2Album() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsUtils.getInstance().requestPermissions(activity, 102,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    new PermissionsUtils.PermissionsResultAction() {
                        @Override
                        public void doExecuteSuccess(int requestCode) {
                            activity.startActivityForResult(startTakePicture(), REQUEST_TAKE_PICTURE_CODE);
                        }

                        @Override
                        public void doExecuteFail(int requestCode, boolean isShowPermissionsDialog) {
                            cameraResult.onCameraFail("获取权限失败");
                        }
                    });
        } else {
            activity.startActivityForResult(startTakePicture(), REQUEST_TAKE_PICTURE_CODE);
        }
    }

    /**
     * 获取系统相册后裁剪
     */
    public void getPhoto2AlbumCrop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsUtils.getInstance().requestPermissions(activity, 103,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    new PermissionsUtils.PermissionsResultAction() {
                        @Override
                        public void doExecuteSuccess(int requestCode) {
                            activity.startActivityForResult(startTakePicture(), REQUEST_TAKE_PICTURE_CROP_CODE);
                        }

                        @Override
                        public void doExecuteFail(int requestCode, boolean isShowPermissionsDialog) {
                            cameraResult.onCameraFail("获取权限失败");
                        }
                    });
        } else {
            activity.startActivityForResult(startTakePicture(), REQUEST_TAKE_PICTURE_CROP_CODE);
        }
    }

    //调用系统照相机，对Intent参数进行封装
    private Intent startTakePhoto(Uri uri) {
        this.photoURI = uri;
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);//将拍取的照片保存到指定URI
        //7.0
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return intent;
    }

    //调用系统图库,对Intent参数进行封装
    private Intent startTakePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return intent;
    }

    //调用系统裁剪图片，对Intent参数进行封装
    private Intent takeCropPicture(Uri uri, int with, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //7.0
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", with);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        photoURI = Uri.fromFile(new File(FileUtils.getTempFilePath(activity)));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    //处理onActivityResult
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                //选择系统图库
                case REQUEST_TAKE_PICTURE_CODE:
                    //获取系统返回的照片的Uri
                    photoURI = intent.getData();
                    if (photoURI != null && "file".equals(photoURI.getScheme())) {
                        //解决小米手机获取URL失败
                        cameraResult.onCameraSuccess(photoURI.getEncodedPath());
                        return;
                    }
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    //从系统表中查询指定Uri对应的照片
                    Cursor cursor = activity.getContentResolver().query(photoURI, filePathColumn, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        if (!TextUtils.isEmpty(picturePath)) {
                            cameraResult.onCameraSuccess(picturePath);
                        } else {
                            cameraResult.onCameraFail("文件没找到");
                        }
                    } else {
                        cameraResult.onCameraFail("文件没找到");
                    }
                    break;
                //选择系统图库.裁剪
                case REQUEST_TAKE_PICTURE_CROP_CODE:
                    photoURI = intent.getData();
                    activity.startActivityForResult(takeCropPicture(photoURI, REQUEST_WIDTH, REQUEST_HEIGHT), REQUEST_TAKE_CROP_CODE);
                    break;
                //调用相机
                case REQUEST_TAKE_PHOTO_CODE:
                    String pathCamera = parseOwnUri(activity, photoURI);
                    cameraResult.onCameraSuccess(pathCamera);
                    break;
                //调用相机,裁剪
                case REQUEST_TAKE_PHOTO_CROP_CODE:
                    activity.startActivityForResult(takeCropPicture(photoURI, REQUEST_WIDTH, REQUEST_HEIGHT), REQUEST_TAKE_CROP_CODE);
                    break;
                //裁剪之后的回调
                case REQUEST_TAKE_CROP_CODE:
                    String path = parseOwnUri(activity, photoURI);
                    cameraResult.onCameraSuccess(path);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 将TakePhoto 提供的Uri 解析出文件绝对路径
     *
     * @param uri
     * @return
     */
    private static String parseOwnUri(Context context, Uri uri) {
        if (uri == null) return null;
        String path;
        if (TextUtils.equals(uri.getAuthority(), FileUtils.getFileProviderName(context))) {
            path = new File(uri.getPath().replace("external_cache_path/", "")).getAbsolutePath();
            if (context.getExternalCacheDir() != null && !path.contains(context.getExternalCacheDir().getAbsolutePath())) {
                path = context.getExternalCacheDir().getAbsolutePath() + path;
            }
        } else {
            String[] filePathColumn = {MediaStore.MediaColumns.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            //也可用下面的方法拿到cursor
            //Cursor cursor = this.context.managedQuery(uri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                cursor.moveToFirst();
                path = cursor.getString(columnIndex);
                cursor.close();
                return TextUtils.isEmpty(path) ? uri.getPath() : path;
            } else {
                path = uri.getPath();
            }
        }
        return path;
    }


    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public Uri getUri(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri != null && uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = activity.getContentResolver();
                StringBuilder buff = new StringBuilder();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value    
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing    
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        photoURI = savedInstanceState.getParcelable("photoURI");
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("photoURI", photoURI);
    }

    public interface CameraResult {
        void onCameraSuccess(String filePath);

        void onCameraFail(String message);
    }
}