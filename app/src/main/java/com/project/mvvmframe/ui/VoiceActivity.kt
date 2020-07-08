package com.project.mvvmframe.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.view.View
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseActivity
import com.project.mvvmframe.ui.service.VoiceService
import kotlinx.android.synthetic.main.activity_voice.*

/**
 * @CreateDate 2020/7/3 11:42
 * @Author jaylm
 */
class VoiceActivity : BaseActivity() {
    override fun bindLayout(): Int = R.layout.activity_voice
    private var mBinder: VoiceService.VoiceBinder? = null
    private lateinit var voiceIntent: Intent

    override fun initView(contentView: View) {
        voiceIntent = Intent(this, VoiceService::class.java)
        bindService(voiceIntent, MyServiceConnection(), Context.BIND_AUTO_CREATE)
    }

    override fun setListener() {
        super.setListener()
        tv_play.setOnClickListener {
            mBinder?.start()
        }

        tv_pause.setOnClickListener {
            mBinder?.pause()
        }

        tv_stop.setOnClickListener {
            mBinder?.stop()
        }
    }

    inner class MyServiceConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder = service as VoiceService.VoiceBinder
        }

    }
}