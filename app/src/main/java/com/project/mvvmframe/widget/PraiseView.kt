package com.project.mvvmframe.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.Keep
import com.project.mvvmframe.R


/**
 * @CreateDate 2020/6/22 14:52
 * @Author jaylm
 */
class PraiseView : View {
    private val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var progress: Float = 1F

    private var mNum: Int
    private var textSize: Float
    private var color: Int

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
        textSize = attributes.getDimension(R.styleable.PraiseView_textSize, 12F)
        color = attributes.getColor(
            R.styleable.PraiseView_textColor,
            resources.getColor(R.color.black_9)
        )
        attributes.recycle()
        initView()
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
        this.mNum = num
        calText()
        requestLayout()
        invalidate()
    }

    fun getNum(): Int {
        return mNum
    }

    private fun initView() {
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = textSize
        mPaint.textAlign = Paint.Align.LEFT
        calText()
    }

    fun startAnimal(isAdd: Boolean, duration: Long) {
        if (isAdd) {
            mNum++
        } else {
            mNum--
        }
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
            size =
                (if (isWidth) padding + mPaint.measureText(mNum.toString()) else padding + textSize * 3f).toInt()
            if (specMode == MeasureSpec.AT_MOST) {
                size = size.coerceAtMost(specSize)
            }
        }
        return size
    }

    private fun calText() {
        val preText = (mNum - 1).toString()
        val nowText = mNum.toString()

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val commonWidth = mPaint.measureText(mCommonText)
        mPaint.alpha = 255
        canvas.drawText(mCommonText, 0F, textSize * 2, mPaint)
        mPaint.alpha = (255 * progress).toInt()
        canvas.drawText(
            mNowText,
            commonWidth,
            textSize * (3 - progress),
            mPaint
        )
        mPaint.alpha = (255 * (1 - progress)).toInt()
        canvas.drawText(
            mOldText,
            commonWidth,
            textSize * (2 - progress),
            mPaint
        )
    }

}