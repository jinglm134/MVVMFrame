package com.project.mvvmframe.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.Keep
import com.project.mvvmframe.R
import com.project.mvvmframe.util.sp


/**
 * 点赞view
 * @CreateDate 2020/6/22 14:52
 * @Author jaylm
 */
class PraiseView : View {
    private val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var progress: Float = 1F

    private var mNum: Int
    private var mPreNum: Int
    private var textSize: Float
    private var color: Int
    private var isAdd = true
    private var mTextBound = Rect()

    private lateinit var mCommonText: String
    private lateinit var mOldText: String
    private lateinit var mNowText: String

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PraiseView)
        mNum = attributes.getInt(R.styleable.PraiseView_text, 0)
        mPreNum = mNum
        textSize = attributes.getDimension(R.styleable.PraiseView_textSize, 12f.sp)
        color = attributes.getColor(
            R.styleable.PraiseView_textColor,
            resources.getColor(R.color.black_9)
        )
        attributes.recycle()
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = textSize
        mPaint.textAlign = Paint.Align.LEFT
        mPaint.getTextBounds(mNum.toString(), 0, mNum.toString().length, mTextBound)
        calText(true)
    }

    fun getProgress(): Float {
        return progress
    }

    @Keep
    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun setNum(num: Int) {
        mPreNum = mNum
        this.mNum = num
        calText(true)
        requestLayout()
        invalidate()
    }

    fun getNum(): Int {
        return mNum
    }

    fun startAnimal(num: Int, duration: Long) {
        mPreNum = mNum
        this.mNum = num
        calText()
        requestLayout()
        ObjectAnimator.ofFloat(this, "progress", 0f, 1f).run {
            setDuration(duration).interpolator = LinearInterpolator()
            start()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            getMeasuredLength(widthMeasureSpec, true),
            getMeasuredLength(heightMeasureSpec, false)
        )
    }

    private fun getMeasuredLength(length: Int, isWidth: Boolean): Int {
        val specMode = MeasureSpec.getMode(length)
        val specSize = MeasureSpec.getSize(length)
        var size: Int
        val padding = if (isWidth) paddingLeft + paddingRight else paddingTop + paddingBottom
        if (specMode == MeasureSpec.EXACTLY) {
            size = specSize
        } else {
            size = if (isWidth) {
                mPaint.measureText(mNum.coerceAtLeast(mPreNum).toString()).toInt()
            } else {
                mTextBound.height() * 3
            } + padding

            if (specMode == MeasureSpec.AT_MOST) {
                size = size.coerceAtMost(specSize)
            }
        }
        return size
    }

    private fun calText(isSet: Boolean = false) {
        val nowText = mNum.toString()
        if (isSet) {
            mCommonText = nowText
            mOldText = ""
            mNowText = ""
        } else {
            val preText = mPreNum.toString()
            var len = 0
            for (i in nowText.indices) {
                if (preText.length <= i) {
                    break
                }
                if (preText[i] == nowText[i]) {
                    len++
                }
            }
            mCommonText = nowText.substring(0, len)
            mOldText = preText.substring(len, preText.length)
            mNowText = nowText.substring(len, nowText.length)
        }
        isAdd = mNum > mPreNum
    }

    override fun onDraw(canvas: Canvas) {
        //计算相同部分的宽度
        val commonWidth = mPaint.measureText(mCommonText)

        //画相同部分
        mPaint.alpha = 255
        canvas.drawText(mCommonText, 0F, measuredHeight / 3f * 2, mPaint)

        //画新数据不同部分
        mPaint.alpha = (255 * progress).toInt()
        val nowY = if (isAdd) {
            (3 - progress) / 3
        } else {
            (1 + progress) / 3
        } * measuredHeight
        canvas.drawText(
            mNowText,
            commonWidth,
            nowY,
            mPaint
        )

        //画老数据不同部分
        mPaint.alpha = (255 * (1 - progress)).toInt()
        val oldY = if (isAdd) {
            (2 - progress) / 3
        } else {
            (2 + progress) / 3
        } * measuredHeight
        canvas.drawText(
            mOldText,
            commonWidth,
            oldY,
            mPaint
        )
    }
}