package com.project.mvvmframe.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import kotlin.properties.Delegates

/**
 * @CreateDate 2020/5/22 13:53
 * @Author jaylm
 */
abstract class AbsApp : Application() {
    companion object {
        var instance: Application by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                AppManager.appManager.removeActivity(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AppManager.appManager.addActivity(activity)
            }

            override fun onActivityResumed(activity: Activity) {
            }

        })
    }
}