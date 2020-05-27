package com.project.mvvmframe.ui.main

import android.util.SparseArray
import android.view.View
import androidx.fragment.app.Fragment
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMActivity
import com.project.mvvmframe.ui.main.home.HomeFragment
import com.project.mvvmframe.ui.main.news.NewsFragment
import com.project.mvvmframe.ui.main.personal.PersonalFragment
import com.project.mvvmframe.ui.main.weather.WeatherFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVMActivity<MainVM>() {
    private val fragments = SparseArray<Fragment>()

    override fun providerVMClass() = MainVM::class.java
    override fun bindLayout(): Int = R.layout.activity_main

    init {
        fragments.apply {
            put(0, HomeFragment.newInstance())
            put(1, NewsFragment.newInstance())
            put(2, WeatherFragment.newInstance())
            put(3, PersonalFragment.newInstance())
        }
    }

    override fun initView(contentView: View) {
        smartReplaceFragment(R.id.fl_main, fragments[0])
    }

    override fun setListener() {
        super.setListener()
        navView.setOnNavigationItemSelectedListener { menuItem ->
            val position = when (menuItem.itemId) {
                R.id.menu_news -> 1
                R.id.menu_weather -> 2
                R.id.menu_personal -> 3
                else -> 0
            }
            smartReplaceFragment(R.id.fl_main, fragments[position])
            true
        }

    }
}