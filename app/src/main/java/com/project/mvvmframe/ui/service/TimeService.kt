package com.project.mvvmframe.ui.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.project.mvvmframe.R
import com.project.mvvmframe.ui.login.LoginActivity

/**
 * @CreateDate 2020/7/3 10:51
 * @Author jaylm
 */
class TimeService : Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val builder = Notification.Builder(this)
        builder.setSmallIcon(R.mipmap.icon_news)
            .setContentText("????")
            .setAutoCancel(true)
        val _intent = Intent(this, LoginActivity::class.java)
        val taskStackBuilder = TaskStackBuilder.create(this)
        taskStackBuilder.addParentStack(LoginActivity::class.java)
        taskStackBuilder.addNextIntent(_intent)
        val pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//        val pendingIntent =
//            PendingIntent.getActivity(this, 0, _intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = builder.build()
        notificationManager.notify(0, notification)
        startForeground(0, notification)
        return super.onStartCommand(_intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

    }


}