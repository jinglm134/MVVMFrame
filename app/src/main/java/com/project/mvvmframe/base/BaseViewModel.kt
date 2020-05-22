package com.project.mvvmframe.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mvvmframe.app.AppManager
import com.project.mvvmframe.entity.BaseBean
import com.project.mvvmframe.net.RequestException
import com.project.mvvmframe.util.UToast
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

    private var mDialog: ProgressDialog? = null
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> BaseBean<T>): T? {
        var data: T? = null
        try {
            if (mDialog == null) {
                mDialog = ProgressDialog(AppManager.appManager.currentActivity())
            }
            mDialog?.show()
            val result = call()
            if (result.error_code == 0) {
                data = result.result
            }
        } catch (e: Exception) {
            UToast.showShortToast(RequestException.exceptionHandler(e))
        } finally {
            mDialog?.dismiss()
            return data
        }

    }


    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}