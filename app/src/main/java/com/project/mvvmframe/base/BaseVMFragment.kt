package com.project.mvvmframe.base

import android.os.Bundle

/**
 * @CreateDate 2020/5/26 16:23
 * @Author jaylm
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {
    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val apply = providerVMClass().apply {
//            mViewModel = ViewModelProvider(requireActivity())[this]
//        }
        mViewModel = initVM()
        startObserve()
    }

    abstract fun initVM(): VM

    //    abstract fun providerVMClass(): Class<VM>
    open fun startObserve() {}
}