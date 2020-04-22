package com.project.mvvmframe.net

import com.project.mvvmframe.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.Charset

/**
 * @CreateDate 2020/4/22 11:19
 * @Author jaylm
 */
class LogInterceptor : Interceptor {

    private val mUTF8 = Charset.forName("UTF-8")
    private val mTAG = "OKHttpRequest"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        LogUtils.v(mTAG, "-----------------------------------------> START_REQUEST")
        LogUtils.v(mTAG, "requestUrl:$request")
        val requestBody = request.body
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            var charset: Charset = mUTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(mUTF8)!!
            }

            LogUtils.v(
                mTAG,
                "requestBody:${buffer.readString(charset)}"
            )
        }

        val t1 = System.nanoTime()
        val response = chain.proceed(chain.request())
        val t2 = System.nanoTime()
        LogUtils.v(
            mTAG,
            "Received response for ${response.request.url} in ${(t2 - t1) / 1e6}ms"
        )

        val mediaType = response.body!!.contentType()
        val content = response.body!!.string()
        LogUtils.long(mTAG, "response body:$content")
        LogUtils.v(mTAG, "-----------------------------------------> END_RESPONSE\n")
        return response.newBuilder()
            .body(content.toResponseBody(mediaType))
            .build()
    }
}