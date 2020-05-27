package com.project.mvvmframe.ui

import android.view.View
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseActivity

/**
 * @CreateDate 2020/5/27 11:30
 * @Author jaylm
 */
class NavigationActivity : BaseActivity() {
    override fun bindLayout(): Int = R.layout.activity_navigation

    override fun initView(contentView: View) {
    }

//    NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_newsFragment)
}