package com.project.mvvmframe.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.project.mvvmframe.util.dp
import com.project.mvvmframe.util.sp
import kotlin.math.abs

/**
 * 刮刮卡view
 * @CreateDate 2020/7/1 11:49
 * @Author jaylm
 */
class ScratchView : View {
    companion object {
        const val OFFSET = 3
    }

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val text = "￥ 50000000 元"
    private val mTextBound = Rect()
    private val mPaddingHorizontal = 12f.dp
    private val mPaddingVertical = 5f.dp
    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
    private val mPath = Path()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        mPaint.textSize = 14f.sp
        mPaint.getTextBounds(text, 0, text.length, mTextBound)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            getMeasureSize(widthMeasureSpec, true),
            getMeasureSize(heightMeasureSpec, false)
        )
    }

    private fun getMeasureSize(measureSpec: Int, isWidth: Boolean): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.EXACTLY -> {
                return size
            }
            else -> {
                val padding = if (isWidth) paddingStart + paddingEnd else paddingTop + paddingBottom
                val calSize =
                    (if (isWidth) mPaddingHorizontal + mTextBound.width() + mPaddingHorizontal
                    else mPaddingVertical + mTextBound.height() + mPaddingVertical).toInt() + padding
                if (mode == MeasureSpec.UNSPECIFIED) {
                    return calSize.coerceAtLeast(size)
                }
                return calSize
            }
        }
    }

    private var pointX = 0f
    private var pointY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                pointX = event.x
                pointY = event.y
                mPath.moveTo(pointX, pointY)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val xOffSet = event.x - pointX
                val yOffSet = event.y - pointY
                if (abs(xOffSet) > OFFSET || abs(yOffSet) > OFFSET) {
                    mPath.rLineTo(xOffSet, yOffSet)
                    invalidate()
                }
                pointX = event.x
                pointY = event.y
            }

        }
        return super.onTouchEvent(event)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.color = Color.parseColor("#00ff00")
        mPaint.style = Paint.Style.FILL
        canvas.drawText(
            text,
            (measuredWidth - mTextBound.width()) / 2f,
            (measuredHeight + mTextBound.height()) / 2f,
            mPaint
        )

        val saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)
        mPaint.color = Color.parseColor("#f0f0f0")
        mPaint.style = Paint.Style.STROKE
        mPaint.isDither = true
        mPaint.strokeWidth = 15f
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
        canvas.drawPath(mPath, mPaint)


        mPaint.xfermode = mXfermode
        mPaint.style = Paint.Style.FILL
        canvas.drawRect(
            paddingStart.toFloat(),
            paddingTop.toFloat(),
            measuredWidth - paddingEnd.toFloat(),
            measuredHeight - paddingBottom.toFloat(),
            mPaint
        )

        mPaint.xfermode = null
        canvas.restoreToCount(saved)
    }
}