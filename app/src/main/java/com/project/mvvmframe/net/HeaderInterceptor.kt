package com.project.mvvmframe.net

import com.project.mvvmframe.constant.ApiDomain
import com.project.mvvmframe.constant.SPConst
import com.project.mvvmframe.util.ULog
import com.project.mvvmframe.util.SPUtils
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @CreateDate 2020/4/22 11:28
 * @Author jaylm
 */
class HeaderInterceptor : Interceptor {

    private var userID by SPUtils(SPConst.SP_USER_ID, "")
    private var token by SPUtils(SPConst.SP_TOKEN, "")

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        var request = chain.request()

        val oldHttpUrl = request.url
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        val headerValues: List<String> = request.headers("url_name")
        if (headerValues.isNotEmpty()) {
            //如果有这个header，先将配置的header删除
            builder.removeHeader("url_name")
            //匹配获得新的BaseUrl
            val headerValue = headerValues[0]
            val newBaseUrl = if ("banner" == headerValue) {
                ApiDomain.BASE_URL_BANNER.toHttpUrlOrNull()
            } else {
                oldHttpUrl
            }
            //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
            val newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl!!.scheme)//更换新url的网络协议,根据实际情况更换成https或者http
                .host(newBaseUrl.host)//更换新url的主机名
                .port(newBaseUrl.port)//更换新url的端口
//                .addEncodedPathSegment(newBaseUrl.encodedPath())
//                .removePathSegment(0)//移除第一个参数v1
                .build()
            ULog.v("HeaderInterceptor", "url_intercept = $newFullUrl")
            request = builder.url(newFullUrl).build()
        }

        val url = request.url
        var requestBuilder: Request.Builder = request.newBuilder()
        if (url.toString().contains("strict")) {
            requestBuilder = requestBuilder.header(
                "userId",//header 为setHeader,即userId唯一
                userID
            ).header(
                "Authorization",//header 为setHeader,即Authorization唯一
                "Bearer${token}"
            )
        }
        val build = requestBuilder.method(request.method, request.body)
            .addHeader("deviceType", "app")
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .build()

        return chain.proceed(build)
    }
}