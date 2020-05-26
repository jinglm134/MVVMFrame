package com.project.mvvmframe.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.project.mvvmframe.R
import com.project.mvvmframe.util.UShape

/**
 * @CreateDate 2019/12/9 11:42
 * @Author jaylm
 */
@Suppress("DEPRECATION")

class ProgressDialog(context: Context, theme: Int) :
    AppCompatDialog(context, theme) {

    class Builder @SuppressLint("InflateParams") constructor(
        context: Context,
        text: CharSequence = "正在加载中..."
    ) {
        private val mDialog: ProgressDialog?
        private val mRootView: View

        init {
            mDialog = ProgressDialog(context, R.style.BaseDialogStyle)
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mRootView = inflater.inflate(R.layout.dialog_progress, null, false)
            mDialog.setCancelable(true)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.setContentView(mRootView)
            val llRoot: View = mRootView.findViewById(R.id.ll_root)
            UShape.setBackgroundDrawable(
                UShape.getCornerDrawable(Color.parseColor("#645c5b5b"), 8)
                , llRoot
            )
            val tvProgress: TextView = mRootView.findViewById(R.id.tv_progress)
            tvProgress.text = text
//        pbProgress.indeterminateDrawable.setColorFilter(
//            ContextCompat.getColor(
//                mContext,
//                R.color.blue
//            ), PorterDuff.Mode.MULTIPLY
//        )
        }

        fun show() {
            if (mDialog != null && !mDialog.isShowing) {
                mDialog.show()
            }
        }

        fun dismiss() {
            if (mDialog != null && mDialog.isShowing) {
                mDialog.dismiss()
            }
        }
    }
}