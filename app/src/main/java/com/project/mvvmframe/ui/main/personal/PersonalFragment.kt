package com.project.mvvmframe.ui.main.personal

import androidx.lifecycle.ViewModelProvider
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.ui.main.MainVM
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * @CreateDate 2020/5/27 9:32
 * @Author jaylm
 */
class PersonalFragment : BaseVMFragment<MainVM>() {
    companion object {
        fun newInstance() = PersonalFragment()
    }

    //    override fun providerVMClass() = MainVM::class.java
    override fun initVM() = ViewModelProvider(requireActivity()).get(MainVM::class.java)
    override fun bindLayout() = R.layout.fragment_personal


    override fun startObserve() {
    }


    override fun initView() {
    }


    override fun setListener() {
        super.setListener()
        tv_pre.setOnClickListener {
            mViewModel.changePosition(2)
        }
        tv_next.setOnClickListener {
            mViewModel.changePosition(0)
        }
    }

}