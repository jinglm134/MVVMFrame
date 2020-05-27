package com.project.mvvmframe.ui.main.news

import androidx.lifecycle.MutableLiveData
import com.project.mvvmframe.base.BaseViewModel
import com.project.mvvmframe.entity.NewsBean
import com.project.mvvmframe.net.RetrofitClient

/**
 * @CreateDate 2020/5/27 16:12
 * @Author jaylm
 */
class NewsVM : BaseViewModel() {

    val uiState = MutableLiveData<List<NewsBean>>()
    fun queryNews(keyWord: String) {
        launchOnUI {
            val result =
                safeApiCall(call = {
                    RetrofitClient.service.queryNews(
                        "80a301ed2fe9418ea02330bbf4ceb776",
                        keyWord
                    )
                })
            if (result != null) {
                uiState.value = result
            }
        }

    }
}