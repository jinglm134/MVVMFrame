package com.project.mvvmframe.ui.main.news

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * @CreateDate 2020/5/27 9:32
 * @Author jaylm
 */
class NewsFragment : BaseVMFragment<NewsVM>() {
    private val titles = mutableListOf("手机", "电脑", "汽车", "奥巴马", "特朗普", "新闻", "笑话")
    private val fragments = mutableListOf<NewsChildFragment>()

    companion object {
        fun newInstance() = NewsFragment()
    }

    override fun providerVMClass() = NewsVM::class.java
    override fun bindLayout() = R.layout.fragment_news

    override fun initView() {
        titles.forEach {
            fragments.add(NewsChildFragment.newInstance(it))
        }

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = titles.size
            override fun createFragment(position: Int) = fragments[position]
        }
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()
    }

}