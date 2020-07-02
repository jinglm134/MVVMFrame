package com.project.mvvmframe.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.forEachIndexed

/**
 * @CreateDate 2020/7/1 16:42
 * @Author jaylm
 */
class FlowLayout : ViewGroup {
    private val mLocation = ArrayList<Rect>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)//测量所有子view

        val lPadding = paddingStart
        val tPadding = paddingTop
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = measuredWidth - lPadding - paddingEnd
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = measuredHeight - tPadding - paddingBottom

        var lineWidth = 0//当前子view所占行已使用的宽度
        var lineHeight = 0//当前行的高度,为当前行所有子view的最大高度
        var maxWidth = 0//FlowLayout的宽度
        var maxHeight = 0//FlowLayout的高度

        mLocation.clear()
        forEachIndexed { index, view ->
            view.apply {
                val lp = layoutParams as MarginLayoutParams
                val childWidth = measuredWidth + lp.leftMargin + lp.rightMargin//子view所需的宽度
                val childHeight = measuredHeight + lp.topMargin + lp.bottomMargin////子view所需的高度
                if (childWidth > widthSize - lineWidth) {
                    //换行
                    maxHeight += lineHeight//viewGroup的行高累加
                    lineWidth = childWidth//重置该行行宽
                    lineHeight = childHeight//重置该行行高
                } else {
                    //不换行
                    lineWidth += childWidth//该行行宽累加
                    maxWidth = maxWidth.coerceAtLeast(lineWidth)//求整个viewGroup最大行宽
                    lineHeight = lineHeight.coerceAtLeast(childHeight)//该行最大行高
                }

                //计算子view的位置
                val left = lPadding + lineWidth - measuredWidth - lp.rightMargin
                val top = tPadding + maxHeight + lp.topMargin
                val right = lPadding + lineWidth - lp.rightMargin
                val bottom = tPadding + maxHeight + lp.topMargin + measuredHeight
                mLocation.add(Rect(left, top, right, bottom))

                //如果是最后一个子view
                if (index == childCount - 1) {
                    maxHeight += lineHeight
                    maxWidth = maxWidth.coerceAtLeast(lineWidth)
                }
            }
        }


        val measureWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                widthSize
            }
            MeasureSpec.UNSPECIFIED -> {
                maxWidth
            }
            else -> {
                maxWidth.coerceAtMost(widthSize)
            }
        } + lPadding + paddingEnd

        val measureHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize
            }
            MeasureSpec.UNSPECIFIED -> {
                maxHeight
            }
            else -> {
                maxHeight.coerceAtMost(heightSize)
            }
        } + tPadding + paddingBottom

        setMeasuredDimension(measureWidth, measureHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        forEachIndexed { index, view ->
            val rect = mLocation[index]
            view.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}