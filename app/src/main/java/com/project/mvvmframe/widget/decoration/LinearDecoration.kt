package com.project.mvvmframe.widget.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.project.mvvmframe.R
import com.project.mvvmframe.util.UShape
import com.project.mvvmframe.util.dp

/**
 * recyclerView decoration
 * @CreateDate 2019/12/18 11:02
 * @Author jaylm
 * @param color         线的颜色        默认 R.color.black_f0
 * @param height        线的高度        默认 1dp
 * @param padding       线的边距        默认 0dp
 * @param hideLastLine  隐藏最后一根线   默认 true
 */
class LinearDecoration(
    @ColorRes color: Int = R.color.black_f0,
    height: Float = 1F,
    padding: Float = 0f,
    private val hideLastLine: Boolean = true
) : RecyclerView.ItemDecoration() {
    private var mDividerHeight: Int
    private var mPadding: Int
    private var dividerPaint = Paint()

    init {
        dividerPaint.color = UShape.getColor(color)
        mDividerHeight = height.dp.toInt()
        mPadding = padding.dp.toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mDividerHeight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var childCount = parent.childCount
        if (hideLastLine) {
            childCount--
        }
        val left = if (mPadding == 0) parent.paddingLeft else mPadding
        val right = parent.width - if (mPadding == 0) parent.paddingRight else mPadding

        var firstPosition = 0
        while (firstPosition < childCount) {
            val view = parent.getChildAt(firstPosition)
            val bottom = (view.bottom + mDividerHeight).toFloat()
            c.drawRect(left.toFloat(), view.bottom.toFloat(), right.toFloat(), bottom, dividerPaint)
            firstPosition++
        }
    }
}