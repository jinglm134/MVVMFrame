package com.project.mvvmframe.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mvvmframe.app.AppManager
import com.project.mvvmframe.entity.BaseBean
import com.project.mvvmframe.net.RequestException
import com.project.mvvmframe.util.toast
import com.project.mvvmframe.widget.dialog.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @CreateDate 2020/4/21 17:26
 * @Author jaylm
 */
open class BaseViewModel : ViewModel() {

    private var builder: ProgressDialog.Builder? = null
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> BaseBean<T>): T? {
        var data: T? = null
        try {
            if (builder == null) {
                builder = ProgressDialog.Builder(AppManager.appManager.currentActivity())
            }
            builder?.show()
            val result = call()
            if (result.error_code == 0) {
                data = result.result
            }
        } catch (e: Exception) {
            RequestException.exceptionHandler(e).toast()
        } finally {
            builder?.dismiss()
            return data
        }
    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}