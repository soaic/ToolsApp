package com.soaic.libcommon.utils;

import android.media.MediaPlayer;

import java.io.IOException;

public class MediaPlayerUtil {

    private MediaPlayer mediaPlayer;
    private int mSongId;

    private static class ClassLoader {
        private static MediaPlayerUtil INSTANCE = new MediaPlayerUtil();
    }

    public static MediaPlayerUtil getInstance(){
        return ClassLoader.INSTANCE;
    }

    public void setData(String path, int singId, final OnPreparedListener onPreparedListener){
        try {
            mSongId = singId;
            if(mediaPlayer != null){
                mediaPlayer.stop();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            // 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if(onPreparedListener != null){
                        onPreparedListener.onPrepared(mp);
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public boolean isCurrentMusic(int singId){
        return mSongId == singId;
    }

    public void start(){
        mediaPlayer.start();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public interface OnPreparedListener{
        void onPrepared(MediaPlayer mp);
    }
}
