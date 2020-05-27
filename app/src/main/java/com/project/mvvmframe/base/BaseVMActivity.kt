package com.project.mvvmframe.base

import androidx.lifecycle.ViewModelProvider

/**
 * @CreateDate 2020/4/21 18:00
 * @Author jaylm
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM
    override fun useVM() {
        super.useVM()
        providerVMClass().apply {
            mViewModel = ViewModelProvider(this@BaseVMActivity).get(this)
        }
        startObserve()
    }

    abstract fun providerVMClass(): Class<VM>
    open fun startObserve() {}
}