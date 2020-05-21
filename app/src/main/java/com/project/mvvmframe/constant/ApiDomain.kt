package com.project.mvvmframe.constant

import com.project.mvvmframe.BuildConfig

/**
 * 请求域名
 * @CreateDate 2020/4/22 11:14
 * @Author jaylm
 */
object ApiDomain {
    const val BASE_URL: String = BuildConfig.BASEAPI//默认域名
    const val BASE_URL_BANNER: String = BuildConfig.BASEAPI_BANNER//BANNER域名
    const val DATA_URL = "http://api.avatardata.cn/"
}