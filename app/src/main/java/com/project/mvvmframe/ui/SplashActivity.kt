package com.project.mvvmframe.ui

/**
 * @CreateDate 2020/5/26 17:28
 * @Author jaylm
 */

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseActivity
import com.project.mvvmframe.constant.SPConst
import com.project.mvvmframe.ui.main.MainActivity
import com.project.mvvmframe.util.DialogUtils
import com.project.mvvmframe.util.SPUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 启动页面
 * @CreateDate 2019/12/2 15:34
 * @Author jaylm
 */
class SplashActivity : BaseActivity() {

    private var mTimer: Timer? = null
    private val isLogin by SPUtils(SPConst.SP_IS_LOGIN, true)

    override fun bindLayout(): Int {
        return R.layout.activity_splash
    }

    override
    fun initView(contentView: View) {
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    private fun requestPermission() {
        RxPermissions(this).requestEachCombined(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).subscribe { permission ->
            when {
                permission.granted -> {
                    if (mTimer == null) {
                        mTimer = Timer()
                    }
                    mTimer?.schedule(object : TimerTask() {
                        override fun run() {
                            if (isLogin) {
                                startActivity(MainActivity::class.java)
                            }
                            finish()
                        }
                    }, 1000)
                }

                permission.shouldShowRequestPermissionRationale -> {
                    DialogUtils.showTwoDialog(context = mActivity,
                        message = "请允许开启权限以正常使用app功能",
                        positiveBtnName = "确定",
                        positiveListener = View.OnClickListener { requestPermission() },
                        cancelListener = View.OnClickListener { finish() })
                }

                else -> {
                    DialogUtils.showTwoDialog(mActivity,
                        message = "请在设置中开启权限,以正常使用app功能",
                        positiveBtnName = "去设置",
                        positiveListener = View.OnClickListener {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:$packageName")
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        },
                        cancelListener = View.OnClickListener { finish() })
                }
            }
        }.addTo(CompositeDisposable())
    }

    private fun Disposable.addTo(c: CompositeDisposable) {
        c.add(this)
    }


    override fun onStop() {
        super.onStop()
        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer = null
        }
    }

}