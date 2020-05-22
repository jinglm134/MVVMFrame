package com.project.mvvmframe.ui.main

import androidx.lifecycle.MutableLiveData
import com.project.mvvmframe.base.BaseViewModel
import com.project.mvvmframe.constant.ApiConfig
import com.project.mvvmframe.entity.WeatherBean
import com.project.mvvmframe.net.RetrofitClient

/**
 * @CreateDate 2020/5/21 17:12
 * @Author jaylm
 */
class MainVM : BaseViewModel() {

    val uiState = MutableLiveData<WeatherBean>()
    fun queryWeather(city: String) {
        launchOnUI {
            val result =
                safeApiCall(call = { RetrofitClient.service.queryWeather(ApiConfig.API_KEY, city) })
            if (result != null) {
                uiState.value = result
            }
        }

//        GlobalScope.launch {
//            val result = withContext(Dispatchers.Default) {
//                RetrofitClient.service.queryWeather(ApiConfig.API_KEY, city)
//            }
//            if (result.error_code == 0) {
//                uiState.value = result.result
//            }
//        }
    }
}

