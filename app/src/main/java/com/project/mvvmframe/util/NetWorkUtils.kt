package com.project.mvvmframe.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * 网络Utils
 * @CreateDate 2020/5/21 10:37
 * @Author jaylm
 */
object NetWorkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return !(null == info || !info.isAvailable)
    }
}
