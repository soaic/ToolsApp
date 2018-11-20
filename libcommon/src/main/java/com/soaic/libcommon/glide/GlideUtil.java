package com.soaic.libcommon.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.EmptySignature;
import com.soaic.libcommon.weiget.imagewatcher.ImageWatcher;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class GlideUtil {

    public static void display(ImageView imageView, String path) {
        if (imageView == null) return;
        Glide.with(imageView.getContext())
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void display(ImageView imageView, @DrawableRes int rid) {
        if (imageView == null) return;
        String path = "android.resource://" + imageView.getContext().getPackageName() + "/drawable/" + rid;
        Glide.with(imageView.getContext()).load(path).into(imageView);
    }

    public static void display(ImageView imageView, File file) {
        if (file == null || imageView == null) return;
        String path = "file://" + file.getAbsolutePath();
        Glide.with(imageView.getContext()).load(path).into(imageView);
    }

    public static void display(Context context, Uri uri, final ImageWatcher.LoadCallback lc){
        Glide.with(context).load(uri)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        lc.onResourceReady(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        lc.onLoadFailed(errorDrawable);
                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        lc.onLoadStarted(placeholder);
                    }
                });
    }

    /**
     * 设置圆角 90dp宽高一致时为圆形
     *
     * @param imageView
     * @param path
     * @param dp        圆角度数
     */
    public static void displayRound(ImageView imageView, String path, int dp) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CenterCrop(), new GlideRoundTransform(dp));

        Glide.with(imageView.getContext()).load(path)
                .listener(new SGlideRequestListener(imageView, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.CENTER_CROP))
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 通过Url获取Bitmap(需在异步线程中调用)
     *
     * @param context
     * @param path
     * @return
     */
    public static Observable<Bitmap> getBitmapByUrl(final Context context, final String path) {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) {
                try {
                    Bitmap bitmap = Glide.with(context)
                            .asBitmap()
                            .load(path)
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    emitter.onNext(bitmap);
                    emitter.onComplete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    public static void preload(Context context, String url) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).preload();
    }

    private static File getCachedFile(Context context, String url) {
        if (TextUtils.isEmpty(url) || context == null) {
            return null;
        }
        return DiskLruCacheWrapper.get(Glide.getPhotoCacheDir(context),
                DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE).get(new OriginalKey(url, EmptySignature.obtain()));
    }

    /**
     * 清除单个图片缓存
     *
     * @param context
     * @param url
     * @return
     */
    public static boolean clearCache(Context context, final String url) {
        if (TextUtils.isEmpty(url) || context == null) {
            return false;
        }
        File file = getCachedFile(context, url);
        return file != null && file.delete();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache(final Context context) {
        try {
            if (context == null) return;
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public long getCacheSize(Context context) {
        try {
            return getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     */
    private long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        boolean isDeleted = false;
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        isDeleted = deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        isDeleted = file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            isDeleted = file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                isDeleted = false;
            }
        }
        return isDeleted;
    }

    static class OriginalKey implements Key {
        private final String id;
        private final Key signature;

        private OriginalKey(String id, Key signature) {
            this.id = id;
            this.signature = signature;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            OriginalKey that = (OriginalKey) o;
            return id.equals(that.id) && signature.equals(that.signature);
        }

        @Override
        public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + signature.hashCode();
            return result;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            try {
                messageDigest.update(id.getBytes(STRING_CHARSET_NAME));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            signature.updateDiskCacheKey(messageDigest);
        }
    }

    static class SGlideRequestListener implements RequestListener<Drawable> {
        private ImageView.ScaleType mPlaceScaleType;
        private ImageView.ScaleType mActualScaleType;
        private ImageView mImageView;

        SGlideRequestListener(ImageView mImageView, ImageView.ScaleType placeScaleType, ImageView.ScaleType actualScaleType) {
            this.mPlaceScaleType = placeScaleType;
            this.mActualScaleType = actualScaleType;
            this.mImageView = mImageView;
            mImageView.setScaleType(placeScaleType);
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            mImageView.setScaleType(mPlaceScaleType);
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            mImageView.setScaleType(mActualScaleType);
            return false;
        }
    }
}
