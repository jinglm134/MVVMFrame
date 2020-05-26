package com.project.mvvmframe.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.project.mvvmframe.R
import com.project.mvvmframe.util.UShape
import com.project.mvvmframe.util.USize

/**
 * @CreateDate 2020/5/26 16:37
 * @Author jaylm
 */
class CommonDialog private constructor(context: Context, theme: Int) :
    AppCompatDialog(context, theme) {

    class Builder @SuppressLint("InflateParams") constructor(context: Context) {
        private val mDialog: CommonDialog?
        private val mRootView: View

        init {
            mDialog = CommonDialog(context, R.style.BaseDialogStyle)
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mRootView = inflater.inflate(R.layout.dialog_common, null, false)
            UShape.setBackgroundDrawable(
                UShape.getCornerDrawable(
                    UShape.getColor(R.color.white),
                    8
                ), mRootView
            )
            mDialog.addContentView(
                mRootView, ViewGroup.LayoutParams(
                    (USize.getScreenWidth() * 0.75).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            mDialog.setCancelable(false)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog: DialogInterface, keyCode: Int, event: KeyEvent ->
                if (KeyEvent.KEYCODE_BACK == keyCode && event.action == KeyEvent.ACTION_DOWN) {
                    dialog.dismiss()
                    return@OnKeyListener true
                }
                false
            })
        }


        fun setTitle(title: String): Builder {
            if (TextUtils.isEmpty(title)) {
                return this
            }
            val textView = mRootView.findViewById<TextView>(R.id.tv_dialog_title)
            textView.visibility = View.VISIBLE
            textView.text = title
            return this
        }

        fun setMessage(message: String, type: Int): Builder {
            when (type) {
                CONTENT_TEXT_TYPE -> {
                    val textView =
                        mRootView.findViewById<TextView>(R.id.tv_dialog_content)
                    textView.visibility = View.VISIBLE
                    textView.text = message
                }
                CONTENT_EDIT_TYPE -> {
                    val editText =
                        mRootView.findViewById<EditText>(R.id.et_dialog_content)
                    UShape.setBackgroundDrawable(
                        UShape.getStrokeDrawable(
                            UShape.getColor(R.color.black_f0),
                            UShape.getColor(R.color.white), 4
                        ), editText
                    )
                    editText.visibility = View.VISIBLE
                    editText.hint = message
                }
            }
            return this
        }

        fun addPositiveButton(
            title: String,
            listener: View.OnClickListener?
        ): Builder {
            val layout = mRootView.findViewById<LinearLayout>(R.id.dialog_footer)
            layout.visibility = View.VISIBLE
            val btnOk = mRootView.findViewById<TextView>(R.id.btn_dialog_ok)
            UShape.setBackgroundDrawable(
                UShape.getPressedDrawable(
                    UShape.getCornerDrawable(UShape.getColor(R.color.green), 4),
                    UShape.getCornerDrawable(UShape.getColor(R.color.green), 4)
                ), btnOk
            )
            btnOk.visibility = View.VISIBLE
            btnOk.text = title
            btnOk.setOnClickListener { v: View? ->
                listener?.onClick(v)
                dismiss()
            }
            return this
        }

        fun addPositiveButton(
            title: String,
            listener: OnEditClickListener
        ): Builder {
            val layout = mRootView.findViewById<LinearLayout>(R.id.dialog_footer)
            layout.visibility = View.VISIBLE
            val btnOk = mRootView.findViewById<TextView>(R.id.btn_dialog_ok)
            UShape.setBackgroundDrawable(
                UShape.getPressedDrawable(
                    UShape.getCornerDrawable(UShape.getColor(R.color.green), 4),
                    UShape.getCornerDrawable(UShape.getColor(R.color.green), 4)
                ), btnOk
            )
            btnOk.visibility = View.VISIBLE
            btnOk.text = title
            btnOk.setOnClickListener { v: View ->
                val editText =
                    mRootView.findViewById<EditText>(R.id.et_dialog_content)
                listener.onPositiveClick(v, editText.text.toString().trim { it <= ' ' })
                dismiss()
            }
            return this
        }

        fun addCancelButton(
            title: String,
            listener: View.OnClickListener?
        ): Builder {
            val layout = mRootView.findViewById<LinearLayout>(R.id.dialog_footer)
            layout.visibility = View.VISIBLE
            val btnCancel = mRootView.findViewById<TextView>(R.id.btn_dialog_cancel)
            UShape.setBackgroundDrawable(
                UShape.getStrokeDrawable(
                    UShape.getColor(R.color.black_f0),
                    UShape.getColor(R.color.white), 4
                ), btnCancel
            )
            btnCancel.visibility = View.VISIBLE
            btnCancel.text = title
            btnCancel.setOnClickListener { v: View? ->
                listener?.onClick(v)
                dismiss()
            }
            return this
        }

        fun show() {
            if (mDialog != null && !mDialog.isShowing) {
                mDialog.show()
            }
        }

        private fun dismiss() {
            if (mDialog != null && mDialog.isShowing) {
                mDialog.dismiss()
            }
        }
    }

    interface OnEditClickListener {
        fun onPositiveClick(view: View?, text: String?)
    }

    companion object {
        const val CONTENT_TEXT_TYPE = 0 //default,文本框
        const val CONTENT_EDIT_TYPE = 1 //可选择,输入框
    }
}