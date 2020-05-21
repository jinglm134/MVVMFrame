package com.project.mvvmframe.net

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
abstract class AbsRetrofitClient {

    private var mRetrofit: Retrofit? = null

    companion object {
        private const val TIMEOUT = 5000L
    }

    fun <S> getService(serviceClass: Class<S>): S {
        return getRetrofit().create(serviceClass)
    }


    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(HeaderInterceptor()) //Header处理
                .addInterceptor(LogInterceptor()) //打印拦截
                .retryOnConnectionFailure(false)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS) //设置读取超时时间
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS) //设置写入超时时间
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS) //设置请求超时时间//设置出现错误进行重新连接。
            handleBuilder(builder)
            return builder.build()
        }

    private fun getRetrofit(): Retrofit {
        if (mRetrofit == null) {
            synchronized(AbsRetrofitClient::class) {
                if (mRetrofit == null) {
                    mRetrofit = Retrofit.Builder().client(client).baseUrl(ApiDomain.DATA_URL)
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

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)
}