package com.project.mvvmframe.ui.service

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.project.mvvmframe.util.toast


/**
 * @CreateDate 2020/7/3 11:49
 * @Author jaylm
 */
class VoiceService : Service() {
    private var player: MediaPlayer? = null
    private lateinit var fd: AssetFileDescriptor
    override fun onCreate() {
        super.onCreate()
        fd = assets.openFd("music.wav")
    }


    override fun onBind(intent: Intent?): IBinder? {
        return VoiceBinder()
    }

    override fun onDestroy() {
        player = player?.run {
            if (isPlaying) {
                stop()
                release()
            }
            null
        }
        super.onDestroy()
    }

    inner class VoiceBinder : Binder() {
        fun start() {
            if (player == null) {
                player = MediaPlayer()
                player?.apply {
                    setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
                    isLooping = true
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    prepareAsync()
                    setOnPreparedListener { start() }
                }
            } else {
                player!!.start()
            }
            "start".toast()
        }

        fun pause() {
            player?.pause()
            "pause".toast()
        }

        fun stop() {
            player = player?.run {
                stop()
                release()
                "stop".toast()
                null
            }
        }

        fun getTime(): Int {
            return if (player == null) 0 else player!!.currentPosition
        }
    }
}