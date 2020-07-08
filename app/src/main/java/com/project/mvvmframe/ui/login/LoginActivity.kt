package com.project.mvvmframe.ui.login

import android.view.View
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMActivity

/**
 * @CreateDate 2020/7/3 11:16
 * @Author jaylm
 */
class LoginActivity : BaseVMActivity<LoginMV>() {
    override fun providerVMClass(): Class<LoginMV> = LoginMV::class.java

    override fun bindLayout(): Int = R.layout.activity_login

    override fun initView(contentView: View) {
    }
}