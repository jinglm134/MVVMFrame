package com.project.mvvmframe.ui.main.personal

import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.ui.main.MainVM

/**
 * @CreateDate 2020/5/27 9:32
 * @Author jaylm
 */
class PersonalFragment : BaseVMFragment<MainVM>() {
    companion object {
        fun newInstance() = PersonalFragment()
    }

    override fun providerVMClass() = MainVM::class.java
    override fun bindLayout() = R.layout.fragment_news


    override fun startObserve() {
    }


    override fun initView() {
    }
}