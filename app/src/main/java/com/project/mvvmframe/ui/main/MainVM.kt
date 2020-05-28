package com.project.mvvmframe.ui.main

import androidx.lifecycle.MutableLiveData
import com.project.mvvmframe.base.BaseViewModel
import com.project.mvvmframe.constant.ApiConfig
import com.project.mvvmframe.entity.WeatherBean
import com.project.mvvmframe.net.RetrofitClient

/**
 * @CreateDate 2020/5/28 9:16
 * @Author jaylm
 */
open class MainVM : BaseViewModel() {
    val mainPosition = MutableLiveData<Int>()
    fun changePosition(position: Int) {
        mainPosition.value = position
    }


    val weather = MutableLiveData<WeatherBean>()
    fun queryWeather(city: String) {
        launchOnUI {
            val result =
                safeApiCall(call = { RetrofitClient.service.queryWeather(ApiConfig.API_KEY, city) })
            if (result != null) {
                weather.value = result
            }
        }
    }
}