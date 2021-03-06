package com.project.mvvmframe.base

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.mvvmframe.R
import kotlinx.android.synthetic.main.activity_base.*

/**
 * @CreateDate 2020/5/26 15:50
 * @Author jaylm
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        /**Tag为类名,用于日志输出的Tag或它用 */
        protected lateinit var TAG: String

        /** 是否沉浸状态栏 */
        var isSetStatusBar = false

        /** 是否允许全屏 */
        var mAllowFullScreen = false

        /** 允许旋转屏幕还是保持竖屏 */
        var isAllowScreenRotate = false
    }

    /** 当前Activity渲染的视图View */
    private lateinit var mRootView: View
    protected lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关闭权限后,app进程被杀死,进入app恢复当前页面时重启app
        /*if (savedInstanceState != null) {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }*/
        mActivity = this
        TAG = mActivity::class.java.simpleName

        if (mAllowFullScreen) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        if (isSetStatusBar) {
            steepStatusBar()
        }

        requestedOrientation = if (isAllowScreenRotate) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        setContentView(R.layout.activity_base)
        initToolbar()
        mRootView = LayoutInflater.from(mActivity).inflate(bindLayout(), base_container, true)
        useVM()
        initView(mRootView)
        setListener()
    }


    @LayoutRes
    abstract fun bindLayout(): Int
    abstract fun initView(contentView: View)
    protected open fun setListener() {}
    protected open fun useVM() {
    }

    /**
     * 沉浸状态栏
     */
    private fun steepStatusBar() {
        // 透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    private fun initToolbar() {
        setSupportActionBar(base_toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)//显示返回图标
            actionBar.setDisplayShowTitleEnabled(false)//不显示应用图标
        }
    }

    //设置toolBar标题
    protected fun setHeader(text: CharSequence?) {
        base_toolbar.visibility = View.VISIBLE
        base_title.text = text
    }

    //设置toolbar右侧按钮
    protected fun setRightButton(
        text: CharSequence,
        @Nullable @DrawableRes drawableRes: Int,
        @Nullable clickListener: View.OnClickListener? = null
    ) {
        base_right_text.text = text
        base_right_text.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
        base_right_text.setOnClickListener(clickListener)
    }

    /**
     * 页面跳转
     *
     * @param clz class
     */
    fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz class
     * @param bundle bundle
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(mActivity, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }


    /**
     * TabLayout+Fragment
     * Fragment替换(隐藏当前的,显示现在的,用过的将不会destrory与create)
     */
    private var currentFragment: Fragment? = null

    fun smartReplaceFragment(
        @IdRes idRes: Int, toFragment: Fragment
    ) {
        smartReplaceFragment(idRes, toFragment, toFragment.javaClass.simpleName)
    }

    fun smartReplaceFragment(
        @IdRes idRes: Int, toFragment: Fragment,
        tag: String
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        // 如有当前在使用的->隐藏当前的
        if (currentFragment != null) {
            transaction.hide(currentFragment!!)
        }
        // toFragment之前添加使用过->显示出来
        if (supportFragmentManager.findFragmentByTag(tag) != null) {
            transaction.show(toFragment)
        } else {// toFragment还没添加使用过->添加上去
            transaction.add(idRes, toFragment, tag)
        }
        transaction.commitNowAllowingStateLoss()
        // toFragment 更新为当前的
        currentFragment = toFragment
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}