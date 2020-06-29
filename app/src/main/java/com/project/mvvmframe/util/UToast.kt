package com.project.mvvmframe.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.project.mvvmframe.app.BaseApp

/**
 * @CreateDate 2020/4/21 18:04
 * @Author jaylm
 */

fun CharSequence.toast(context: Context = BaseApp.context) {
    UToast.showToast(context, this, Toast.LENGTH_SHORT)
}

fun CharSequence.toastLong(context: Context = BaseApp.context) {
    UToast.showToast(context, this, Toast.LENGTH_LONG)
}

object UToast {
    private var sToast: Toast? = null
    private var isJumpWhenMore: Boolean = true

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     * true: 弹出新吐司  false: 只修改文本内容
     * 如果为false的话可用来做显示任意时长的吐司
     */
    fun init(isJumpWhenMore: Boolean) {
        UToast.isJumpWhenMore = isJumpWhenMore
    }


    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param text     文本
     * @param duration 显示时长
     */
    @SuppressLint("ShowToast")
    fun showToast(context: Context, text: CharSequence, duration: Int) {
        if (isJumpWhenMore) cancelToast()
        if (sToast == null) {
            sToast = Toast.makeText(context.applicationContext, text, duration)
        } else {
            sToast?.run {
                setText(text)
                this.duration = duration
            }
        }
        sToast?.show()
    }


    /**
     * 取消吐司显示
     */
    private fun cancelToast() {
        sToast?.cancel()
        sToast = null
    }
}