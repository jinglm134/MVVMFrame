package com.project.mvvmframe.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

/**
 * @CreateDate 2020/5/26 16:23
 * @Author jaylm
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {
    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        providerVMClass().apply {
            mViewModel = ViewModelProvider(this@BaseVMFragment).get(this)
        }
        startObserve()
    }

    abstract fun providerVMClass(): Class<VM>
    abstract fun startObserve()
}