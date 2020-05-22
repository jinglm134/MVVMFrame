package com.project.mvvmframe.app

import android.content.Context
import com.project.mvvmframe.R
import com.project.mvvmframe.util.UToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlin.properties.Delegates

/**
 * @CreateDate 2020/4/21 17:07
 * @Author jaylm
 */
class BaseApp : AbsApp() {

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        UToast.init(true)
    }

    init {
        //        设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            ClassicsHeader(context).setTextSizeTime(10f).setTextSizeTitle(12f).setDrawableSize(22f)
            //                .setTimeFormat(DynamicTimeFormat("上次更新 %s"))
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            ClassicsFooter(context).setTextSizeTitle(12f).setDrawableSize(22f)
        }
    }
}