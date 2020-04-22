package com.project.mvvmframe.net

import android.app.PendingIntent.getService
import com.project.mvvmframe.constant.ApiDomain
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @CreateDate 2020/4/22 10:57
 * @Author jaylm
 */
class RetrofitClient {

    private var mRetrofit: Retrofit? = null

    companion object {
        private const val TIME_OUT = 5000L
    }

    fun <S> getService(serviceClass: Class<S>): S {
        return getRetrofit().create(serviceClass)
    }

    private fun getService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }

    private val client: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS) //设置读取超时时间
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS) //设置请求超时时间
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS) //设置写入超时时间
                .addInterceptor(HeaderInterceptor()) //网络拦截
                .addInterceptor(LogInterceptor()) //打印拦截
                .retryOnConnectionFailure(false) //设置出现错误进行重新连接。
                .build()
        }

    private fun getRetrofit(): Retrofit {
        if (mRetrofit == null) {
            synchronized(RetrofitClient::class) {
                if (mRetrofit == null) {
                    mRetrofit = Retrofit.Builder().client(client).baseUrl(ApiDomain.BASE_URL)
                        .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.createWithScheduler(
                                Schedulers.io()
                            )
                        ).addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }
        return mRetrofit!!
    }
}