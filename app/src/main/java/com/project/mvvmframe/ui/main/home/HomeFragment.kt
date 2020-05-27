package com.project.mvvmframe.ui.main.home

import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.ui.main.MainVM

/**
 * @CreateDate 2020/5/27 9:30
 * @Author jaylm
 */
class HomeFragment : BaseVMFragment<MainVM>() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun providerVMClass(): Class<MainVM> = MainVM::class.java
    override fun bindLayout(): Int = R.layout.fragment_home


    override fun initView() {
    }

    override fun startObserve() {
    }
}