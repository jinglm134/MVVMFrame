package com.project.mvvmframe.util

import android.content.Context
import android.view.View
import com.project.mvvmframe.widget.dialog.CommonDialog

/**
 * @CreateDate 2020/5/26 16:46
 * @Author jaylm
 */

object DialogUtils {

    /**
     * 显示单个按钮的dialog
     *
     * @param context          Context
     * @param title            title
     * @param message          提示内容
     * @param positiveBtnName  按钮的文本
     * @param positiveListener 按钮的点击事件
     */
    fun showSingleDialog(
        context: Context,
        title: String = "提示",
        message: String = "",
        positiveBtnName: String = "确定",
        positiveListener: View.OnClickListener? = null
    ) {
        val builder = CommonDialog.Builder(context)
        builder.addPositiveButton(positiveBtnName, positiveListener)
            .setMessage(message, CommonDialog.CONTENT_TEXT_TYPE)
            .setTitle(title)
            .show()
    }


    /**
     * 显示两个按钮(积极按钮  和  取消)的dialog
     *
     * @param context          Context
     * @param title            title
     * @param message          提示内容
     * @param positiveBtnName  积极按钮的文本
     * @param positiveListener 积极按钮的点击事件
     * @param cancelListener   取消按钮的点击事件
     */
    fun showTwoDialog(
        context: Context,
        title: String = "提示",
        message: String = "",
        positiveBtnName: String = "确定",
        positiveListener: View.OnClickListener? = null,
        cancelListener: View.OnClickListener? = null
    ) {
        val builder = CommonDialog.Builder(context)
        builder.addPositiveButton(positiveBtnName, positiveListener)
            .setMessage(message, CommonDialog.CONTENT_TEXT_TYPE)
            .addCancelButton("取消", cancelListener)
            .setTitle(title)
            .show()
    }


    /**
     * 显示带输入框的两个按钮(积极按钮和取消)的dialog
     *
     * @param context          Context
     * @param title            title
     * @param message          提示内容
     * @param positiveBtnName  积极按钮的文本
     * @param positiveListener 积极按钮的点击事件
     * @param cancelListener   取消按钮的点击事件
     */
    fun showEditDialog(
        context: Context,
        title: String = "提示",
        message: String = "",
        positiveBtnName: String = "确定",
        positiveListener: CommonDialog.OnEditClickListener,
        cancelListener: View.OnClickListener? = null
    ) {
        val builder = CommonDialog.Builder(context)
        builder.addPositiveButton(positiveBtnName, positiveListener)
            .setMessage(message, CommonDialog.CONTENT_EDIT_TYPE)
            .addCancelButton("取消", cancelListener)
            .setTitle(title)
            .show()
    }
}