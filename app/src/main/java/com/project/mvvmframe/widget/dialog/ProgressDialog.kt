package com.project.mvvmframe.widget.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.project.mvvmframe.R
import com.project.mvvmframe.util.UShape

/**
 * @CreateDate 2019/12/9 11:42
 * @Author jaylm
 */
@Suppress("DEPRECATION")
class ProgressDialog(private var mContext: Context, text: CharSequence = "正在加载中...") {
    private var mDialog: Dialog = Dialog(mContext, R.style.BaseDialogStyle)

    init {
        mDialog.setCancelable(true)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setContentView(R.layout.dialog_progress)
        val llRoot: View = mDialog.findViewById(R.id.ll_root)
        UShape.setBackgroundDrawable(
            UShape.getCornerDrawable(Color.parseColor("#645c5b5b"), 8)
            , llRoot
        )
        val tvProgress: TextView = mDialog.findViewById(R.id.tv_progress)
        tvProgress.text = text
//        pbProgress.indeterminateDrawable.setColorFilter(
//            ContextCompat.getColor(
//                mContext,
//                R.color.blue
//            ), PorterDuff.Mode.MULTIPLY
//        )
    }

    fun show() {
        if (mContext is Activity && (mContext as Activity).isFinishing) {
            return
        }
        mDialog.show()
    }

    fun dismiss() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }
}