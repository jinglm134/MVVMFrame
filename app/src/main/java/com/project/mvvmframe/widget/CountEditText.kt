package com.project.mvvmframe.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatEditText
import com.project.mvvmframe.R
import com.project.mvvmframe.util.sp

/**
 * @CreateDate 2020/6/28 14:51
 * @Author jaylm
 */
class CountEditText : AppCompatEditText {

    private var totalChar: Int
    private var currentChar: Int = 0
    private var countTextColor: Int
    private var countTextSize: Float
    private var lineColor: Int
    private val mPaint: Paint

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CountEditText)
        totalChar = typedArray.getInteger(R.styleable.CountEditText_totalChar, 20)
        countTextSize =
            typedArray.getDimension(R.styleable.CountEditText_countTextSize, 12f.sp)
        countTextColor = typedArray.getColor(
            R.styleable.CountEditText_countTextColor,
            getColor(R.color.black_9)
        )

        lineColor = typedArray.getColor(
            R.styleable.CountEditText_lineColor,
            getColor(R.color.blue)
        )
        typedArray.recycle()
        currentChar = if (text == null) 0 else text!!.length
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.textSize = countTextSize
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s == null) {
                    currentChar = 0
                    invalidate()
                    return
                }
                if (s.length > totalChar) {
                    setText(s.substring(0, totalChar))
                    setSelection(totalChar)
                    return
                }
                currentChar = s.length
                invalidate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = lineColor
        canvas.drawLine(
            paddingLeft.toFloat(),
            height.toFloat(), width - paddingRight.toFloat(), height.toFloat(), mPaint
        )

        mPaint.color = countTextColor
        val text = "$currentChar/$totalChar"
        canvas.drawText(
            text,
            width - paddingRight.toFloat() - paint.measureText(text),
            height - paddingBottom.toFloat(),
            mPaint
        )
    }


    private fun getColor(@ColorRes rId: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(rId)
        } else {
            context.resources.getColor(rId)
        }
    }
}