package com.project.mvvmframe.util

import android.text.TextUtils
import android.util.Log
import com.project.mvvmframe.BuildConfig

/**
 * @CreateDate 2020/4/21 18:03
 * @Author jaylm
 */
object LogUtils {

    private var logSwitch = BuildConfig.DEBUG
    private var tag = "TAG"


    /**
     * Verbose日志
     *
     * @param msg 消息
     */
    fun v(msg: Any) {
        v(tag, msg)
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    fun v(tag: String, msg: Any) {
        log(tag, msg.toString(), 'v')
    }


    /**
     * Debug日志
     *
     * @param msg 消息
     */
    fun d(msg: Any) {
        d(tag, msg)
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    fun d(tag: String, msg: Any) {// 调试信息
        log(tag, msg.toString(), 'd')
    }


    /**
     * Info日志
     *
     * @param msg 消息
     */
    fun i(msg: Any) {
        i(tag, msg)
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    fun i(tag: String, msg: Any) {
        log(tag, msg.toString(), 'i')
    }


    /**
     * Warn日志
     *
     * @param msg 消息
     */
    fun w(msg: Any) {
        w(tag, msg)
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    fun w(tag: String, msg: Any) {
        log(tag, msg.toString(), 'w')
    }


    /**
     * Error日志
     *
     * @param msg 消息
     */
    fun e(msg: Any) {
        e(tag, msg)
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    fun e(tag: String, msg: Any) {
        log(tag, msg.toString(), 'e')
    }


    fun long(tag: String, msg: Any) {
        log(tag, msg.toString(), 'l')
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param type 日志类型
     */
    private fun log(tag: String, msg: String, type: Char) {

        if (!logSwitch || TextUtils.isEmpty(msg)) {
            return
        }
        when (type) {
            'v' -> Log.v(tag, msg)
            'd' -> Log.d(tag, msg)
            'i' -> Log.i(tag, msg)
            'w' -> Log.w(tag, msg)
            'e' -> Log.e(tag, msg)
            'l' -> logLong(tag, msg)
        }
    }


    // 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制,所以这里使用分节的方式来输出足够长度的message
    private const val MAXLENGTH = 4 * 1024 - 100

    private fun logLong(tag: String, msg: String) {
        if (msg.length > MAXLENGTH) {
            Log.v("$tag --- long ", msg.substring(0, MAXLENGTH))
            logLong(tag, msg.substring(MAXLENGTH))
        } else {
            Log.v("$tag --- long", msg)
        }
    }
}