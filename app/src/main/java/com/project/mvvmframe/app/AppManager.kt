package com.project.mvvmframe.app

import android.app.Activity
import java.util.*

/**
 * @CreateDate 2020/5/22 14:09
 * @Author jaylm
 */
class AppManager private constructor() {
    companion object {
        private var activityStack = Stack<Activity>()
        private var instance: AppManager? = null

        val appManager: AppManager
            get() {
                if (instance == null) {
                    synchronized(AppManager::class.java) {
                        if (instance == null) {
                            instance = AppManager()
                        }
                    }
                }
                return instance!!
            }
    }

    /**
     * 是否有activity
     */
    val isEmpty: Boolean
        get() = !activityStack.isEmpty()


    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        activityStack.push(activity)
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        finishActivity(currentActivity())
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity) {
        if (!activity.isFinishing) {
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            finishActivity(activity)
        }
        activityStack.clear()
    }

    /**
     * 获取指定的Activity
     */
    fun getActivity(cls: Class<*>): Activity? {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                return activity
            }
        }
        return null
    }


    /**
     * 退出应用程序
     */
    fun AppExit() {
        try {
            finishAllActivity()
        } catch (e: Exception) {
            activityStack.clear()
            e.printStackTrace()
        }
    }
}