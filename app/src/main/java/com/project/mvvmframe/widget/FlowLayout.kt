package com.project.mvvmframe.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @CreateDate 2020/7/1 16:42
 * @Author jaylm
 */
class FlowLayout : ViewGroup {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }
}