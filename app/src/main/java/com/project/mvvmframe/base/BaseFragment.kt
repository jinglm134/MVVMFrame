package com.project.mvvmframe.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * @CreateDate 2020/5/26 16:17
 * @Author jaylm
 */
abstract class BaseFragment : Fragment() {

    companion object {
        /**Tag为类名,用于日志输出的Tag或它用 */
        protected val TAG by lazy { this::class.java.simpleName }
    }

    private var mRootView: View? = null
//    private var hasConfig =
//        false//是否已调用过initView/setListeners,因为fragment恢复时,onCreateView等生命周期方法会重新调用
//    /*默认可见,用于懒加载*/
//    private var hasVisible = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(bindLayout(), container, false)
        }

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        val parent: ViewParent? = mRootView!!.parent
        if (parent != null) {
            (parent as ViewGroup).removeView(mRootView)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()//初始化view
        setListener()//设置监听
    }

//    /*使用viewpager的FragmentPagerAdapter时，会调用setUserVisibleHint,并且在onCreateView之前调用,实现懒加载*/
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (isVisibleToUser) {
//            hasVisible = true
//            onVisible()
//        } else {
//            hasVisible = false
//        }
//    }

//    private fun onVisible() {
//        if (hasVisible && mRootView != null && !hasConfig) {
//            /*onViewCreated 和 setUserVisibleHint 均可能调用该方法,以下代码只被执行一次.
//              onViewCreated:未使用FragmentPagerAdapter加载的fragment ，或者FragmentPagerAdapter中的第一个fragment,因为hasVisible=true会执行以下代码
//              setUserVisibleHint：使用FragmentPagerAdapter加载的fragment,且非FragmentPagerAdapter中的第一个fragment。执行onViewCreated,由于它们的hasVisible=false,所以不会执行以下代码,当fragment可见时才会执行以下代码*/
//            initView()//初始化view
//            setListener()//设置监听
//            hasConfig = true
//        }
//    }


    protected abstract fun bindLayout(): Int
    protected abstract fun initView()
    open fun setListener() {}


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
        val transaction = childFragmentManager.beginTransaction()
        // 如有当前在使用的->隐藏当前的
        if (currentFragment != null) {
            transaction.hide(currentFragment!!)
        }
        // toFragment之前添加使用过->显示出来
        if (childFragmentManager.findFragmentByTag(tag) != null) {
            transaction.show(toFragment)
        } else {// toFragment还没添加使用过->添加上去
            transaction.add(idRes, toFragment, tag)
        }
        transaction.commitNowAllowingStateLoss()
        // toFragment 更新为当前的
        currentFragment = toFragment
    }

    override fun onDestroy() {
        super.onDestroy()
        mRootView = null
    }
}