package com.project.mvvmframe.ui.main.news

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mvvmframe.R
import com.project.mvvmframe.adapter.NewsAdapter
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.entity.NewsBean
import com.project.mvvmframe.util.UToast
import com.project.mvvmframe.widget.decoration.LinearDecoration
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * @CreateDate 2020/5/27 16:19
 * @Author jaylm
 */
class NewsChildFragment : BaseVMFragment<NewsVM>() {
    private val mAdapter = NewsAdapter()

    companion object {
        fun newInstance(keyWord: String): NewsChildFragment {
            val fragment = NewsChildFragment()
            val bundle = Bundle()
            bundle.putString("key", keyWord)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun providerVMClass(): Class<NewsVM> = NewsVM::class.java
    override fun bindLayout(): Int = R.layout.layout_recyclerview

    override fun initView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(LinearDecoration())
        mAdapter.setDiffCallback(DiffCallBack())
        recyclerView.adapter = mAdapter

        val keyWord = requireArguments().getString("key", "")
        mViewModel.queryNews(keyWord)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.uiState.observeForever {
            mAdapter.setDiffNewData(it.toMutableList())
        }
    }

    override fun setListener() {
        super.setListener()
        mAdapter.setOnItemClickListener { _, _, position -> UToast.showShortToast(position.toString()) }
    }


    class DiffCallBack : DiffUtil.ItemCallback<NewsBean>() {
        override fun areItemsTheSame(oldItem: NewsBean, newItem: NewsBean) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: NewsBean, newItem: NewsBean) =
            oldItem.content == newItem.content
    }
}