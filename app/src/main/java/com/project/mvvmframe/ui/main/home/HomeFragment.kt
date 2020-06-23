package com.project.mvvmframe.ui.main.home

import android.os.CountDownTimer
import androidx.lifecycle.ViewModelProvider
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.ui.main.MainVM
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * @CreateDate 2020/5/27 9:30
 * @Author jaylm
 */
class HomeFragment : BaseVMFragment<MainVM>() {
    private var mTimer: CountDownTimer? = null

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    //    override fun providerVMClass() = MainVM::class.java
    override fun bindLayout(): Int = R.layout.fragment_home
    override fun initVM() = ViewModelProvider(requireActivity()).get(MainVM::class.java)


    override fun initView() {
    }

    override fun setListener() {
        super.setListener()
        tv_pre.setOnClickListener {
            mViewModel.changePosition(3)
        }
        tv_next.setOnClickListener {
            mViewModel.changePosition(1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mTimer != null) {
            mTimer?.cancel()
        }
    }
}