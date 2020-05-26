package com.project.mvvmframe.ui.main

import android.view.View
import androidx.lifecycle.Observer
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVMActivity<MainVM>() {

    override fun bindLayout(): Int = R.layout.activity_main
    override fun providerVMClass(): Class<MainVM> = MainVM::class.java

    override fun initView(contentView: View) {
        setHeader("测试")
        mViewModel.queryWeather("北京")
    }

    override fun startObserve() {
        mViewModel.uiState.observe(this, Observer {
            tv1.text = it.life.info.chuanyi[1]
            tv2.text = it.life.info.kongtiao[1]
            tv3.text = it.life.info.ganmao[1]
            tv4.text = it.life.info.ziwaixian[1]
        })
    }

    override fun setListener() {
        super.setListener()
        btn1.setOnClickListener {
            mViewModel.queryWeather("广州")
        }
    }
}