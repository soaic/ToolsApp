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
        if(mediaPlayer != null) mediaPlayer.start();
    }

    public void stop(){
        if(mediaPlayer != null) mediaPlayer.stop();
    }

    public void pause(){
        if(mediaPlayer != null) mediaPlayer.pause();
    }

    public boolean isPlaying(){
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public int getDuration(){
        return mediaPlayer == null ? 0 : mediaPlayer.getDuration();
    }

    public int getCurrentPosition(){
        return mediaPlayer == null ? 0 : mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int process){
        if(mediaPlayer != null) mediaPlayer.seekTo(process * 1000);
    }

    public interface OnPreparedListener{
        void onPrepared(MediaPlayer mp);
    }
}
