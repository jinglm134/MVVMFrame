package com.project.mvvmframe.ui.main.news

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.ui.main.MainVM
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * @CreateDate 2020/5/27 9:32
 * @Author jaylm
 */
class NewsFragment : BaseVMFragment<MainVM>() {
    private val titles = mutableListOf("手机", "电脑", "汽车", "奥巴马", "特朗普", "新闻", "笑话")
    private val fragments = mutableListOf<NewsChildFragment>()
    private lateinit var mAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = NewsFragment()
    }

    //    override fun providerVMClass() = MainVM::class.java
    override fun initVM() = ViewModelProvider(requireActivity()).get(MainVM::class.java)
    override fun bindLayout() = R.layout.fragment_news

    override fun initView() {
        titles.forEach {
            fragments.add(NewsChildFragment.newInstance(it))
        }

        mAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = titles.size
            override fun createFragment(position: Int) = fragments[position]
        }
        viewPager.adapter = mAdapter

        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()
    }

    override fun setListener() {
        super.setListener()
        tv_pre.setOnClickListener {
            mViewModel.changePosition(0)
        }
        tv_next.setOnClickListener {
            mViewModel.changePosition(2)
        }
    }

}