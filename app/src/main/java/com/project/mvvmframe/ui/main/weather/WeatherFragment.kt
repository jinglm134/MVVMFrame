package com.project.mvvmframe.ui.main.weather

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.ui.main.MainVM
import kotlinx.android.synthetic.main.fragment_weather.*

/**
 * @CreateDate 2020/5/27 11:24
 * @Author jaylm
 */
class WeatherFragment : BaseVMFragment<MainVM>() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override fun bindLayout() = R.layout.fragment_weather
    override fun initView() {
        mViewModel.queryWeather("北京")
    }

    //    override fun providerVMClass() = MainVM::class.java
    override fun initVM() = ViewModelProvider(requireActivity()).get(MainVM::class.java)

    override fun startObserve() {
        mViewModel.weather.observe(this, Observer {
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

        tv_pre.setOnClickListener {
            mViewModel.changePosition(1)
        }
        tv_next.setOnClickListener {
            mViewModel.changePosition(3)
        }
    }
}