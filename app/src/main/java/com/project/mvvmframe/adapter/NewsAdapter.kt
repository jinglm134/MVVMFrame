package com.project.mvvmframe.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.project.mvvmframe.R
import com.project.mvvmframe.entity.NewsBean
import com.project.mvvmframe.util.GlideUtils

/**
 * @CreateDate 2020/5/27 16:23
 * @Author jaylm
 */
class NewsAdapter : BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.item_news) {
    override fun convert(holder: BaseViewHolder, item: NewsBean) {
        holder.setText(R.id.tv_title, item.full_title)
            .setText(R.id.tv_content, item.content)
            .setText(R.id.tv_src, item.src)
            .setText(R.id.tv_time, item.pdate)
        GlideUtils.showImages(holder.getView(R.id.iv_picture), item.img)
    }
}