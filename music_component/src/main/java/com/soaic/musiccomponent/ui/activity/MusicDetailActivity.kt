package com.soaic.musiccomponent.ui.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.AppCompatSeekBar
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.glide.GlideUtil
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.libcommon.utils.BlurUtil
import com.soaic.libcommon.utils.MediaPlayerUtil
import com.soaic.libcommon.utils.TimeUtils
import com.soaic.libcommon.utils.Utils
import com.soaic.musiccomponent.R
import com.soaic.musiccomponent.request.MusicRequest
import com.soaic.musiccomponent.response.MusicInfoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MusicDetailActivity : BasicActivity() {

    private lateinit var musicDetailPicIv: ImageView
    private lateinit var musicDetailName: TextView
    private lateinit var musicDetailBgIv: ImageView
    private lateinit var musicDetailTimeSeek: AppCompatSeekBar
    private lateinit var musicDetailTimeNowTv: TextView
    private lateinit var musicDetailTimeEndTv: TextView
    private lateinit var musicDetailPlayBtn: Button
    private var isLoaded: Boolean = false
    private var timer: Timer = Timer()
    private lateinit var timerTask: MusicTimerTask
    private lateinit var animator: ObjectAnimator

    override fun getContentView(): Int {
        return R.layout.activity_music_detail
    }

    override fun initVariables(savedInstanceState: Bundle?) {
        val songId = intent.getStringExtra("songId")
        getMusicInfo(songId)
    }

    override fun initViews() {
        musicDetailPicIv = findViewById(R.id.musicDetailPicIv)
        musicDetailName = findViewById(R.id.musicDetailName)
        musicDetailBgIv = findViewById(R.id.musicDetailBgIv)
        musicDetailTimeSeek = findViewById(R.id.musicDetailTimeSeek)
        musicDetailTimeNowTv = findViewById(R.id.musicDetailTimeNowTv)
        musicDetailTimeEndTv = findViewById(R.id.musicDetailTimeEndTv)
        musicDetailPlayBtn = findViewById(R.id.musicDetailPlayBtn)

        initAnimation()
        timerTask = MusicTimerTask(musicDetailTimeSeek)
    }

    private fun initAnimation(){
        animator = ObjectAnimator.ofFloat(musicDetailPicIv, "rotation", 0f, 360.0f)
        animator.duration = 10000
        animator.interpolator = LinearInterpolator() //匀速
        animator.repeatCount = Animation.INFINITE //设置动画重复次数（-1代表一直转）
        animator.repeatMode = ObjectAnimator.RESTART //动画重复模式
    }

    class MusicTimerTask(private val musicDetailTimeSeek: AppCompatSeekBar): TimerTask(){
        override fun run() {
            if(MediaPlayerUtil.getInstance().isPlaying)
                musicDetailTimeSeek.progress = MediaPlayerUtil.getInstance().currentPosition / 1000
        }
    }

    override fun initEvents() {
        musicDetailTimeSeek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                musicDetailTimeNowTv.text = TimeUtils.secondsToTime(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                MediaPlayerUtil.getInstance().seekTo(seekBar.progress)
            }
        })

        musicDetailPlayBtn.setOnClickListener{
            playMusic()
        }

    }

    private fun playMusic() {
        if(isLoaded) {
            if(MediaPlayerUtil.getInstance().isPlaying){
                MediaPlayerUtil.getInstance().pause()
                musicDetailPlayBtn.text = "播放"
            } else {
                MediaPlayerUtil.getInstance().start()
                musicDetailPlayBtn.text = "暂停"
            }

            if(!animator.isStarted){
                animator.start()
            } else if(animator.isPaused){
                animator.resume()
            } else {
                animator.pause()
            }
        }
    }

    override fun loadData() {

    }

    private fun getMusicInfo(songId: String) {
        MusicRequest.getMusicDetail(applicationContext, songId, object : OnResultListener<MusicInfoResponse> {
            override fun onSuccess(t: MusicInfoResponse) {
                musicDetailName.text = t.songinfo.title
                loadMusicBg(t.songinfo.pic_premium)
                GlideUtil.displayRound(musicDetailPicIv, t.songinfo.pic_premium, Utils.dip2px(applicationContext,90f))
                if(MediaPlayerUtil.getInstance().isCurrentMusic(t.bitrate.song_file_id)){
                    initMusic()
                } else {
                    loadMusic(t.bitrate.file_link, t.bitrate.song_file_id)
                }
            }

            override fun onFailure(err: Throwable) {
                err.printStackTrace()
            }
        })
    }

    private fun loadMusic(url: String, singId: Int){
        MediaPlayerUtil.getInstance().setData(url, singId) {
            initMusic()
        }
    }

    private fun initMusic(){
        timer.schedule(timerTask,0,1000)
        isLoaded = true
        musicDetailTimeEndTv.text = TimeUtils.secondsToTime(MediaPlayerUtil.getInstance().duration / 1000)
        musicDetailTimeSeek.max = MediaPlayerUtil.getInstance().duration / 1000
        playMusic()
    }

    @SuppressLint("CheckResult")
    private fun loadMusicBg(url: String) {
        GlideUtil.getBitmapByUrl(applicationContext, url).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ bitmap ->
                    val blurBitmap = BlurUtil.stack(bitmap, 50, false)
                    musicDetailBgIv.setImageBitmap(blurBitmap)
        },{ err ->
            err.printStackTrace()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        timerTask.cancel()
        timer.cancel()
    }
}