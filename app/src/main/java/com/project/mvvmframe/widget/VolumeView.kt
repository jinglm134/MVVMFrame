package com.project.mvvmframe.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.project.mvvmframe.R
import com.project.mvvmframe.util.dp
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * 音量view
 * @CreateDate 2020/6/28 9:32
 * @Author jaylm
 */
class VolumeView : View {
    companion object {
        private const val OFFSET = 5
    }

    private val colorBg: Int//圆环背景色
    private val colorProgress: Int//圆环进度色
    private val r: Float//圆环外环半径
    private val arcWidth: Float//每个扇形宽度
    private val splitWidth: Float//间隔宽度

    private val strokeWidth: Float//内外环宽度
    private val bitmap: Bitmap//图片
    private var progress: Int//当前进度
    private val mPath: Path
    private val mPaint: Paint
    private val rectF: RectF


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.VolumeView)
        colorBg = attributes.getColor(
            R.styleable.VolumeView_backgroundColor,
            resources.getColor(R.color.black_c)
        )
        colorProgress = attributes.getColor(
            R.styleable.VolumeView_progressColor,
            resources.getColor(R.color.black_6)
        )

        arcWidth = attributes.getDimension(R.styleable.VolumeView_arcWidth, 10f.dp)
        splitWidth = attributes.getDimension(R.styleable.VolumeView_splitWidth, 4f.dp)
        strokeWidth = attributes.getDimension(R.styleable.VolumeView_strokeWidth, 2f.dp)
        r = attributes.getDimension(R.styleable.VolumeView_circleWidth, 30f.dp)
        progress = attributes.getInt(R.styleable.VolumeView_progress, 0)
        bitmap = BitmapFactory.decodeResource(
            resources,
            attributes.getResourceId(R.styleable.VolumeView_src, 0)
        )
        attributes.recycle()

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = strokeWidth
        val pathEffect =
            DashPathEffect(floatArrayOf(arcWidth + strokeWidth, splitWidth + strokeWidth), 1f)
        mPaint.pathEffect = pathEffect
        mPaint.strokeCap = Paint.Cap.ROUND

        mPath = Path()
        rectF = RectF()
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
            size = (r * 2).toInt() + padding
            if (specMode == MeasureSpec.AT_MOST) {
                size = size.coerceAtMost(specSize)
            }
        }
        return size
    }


    override fun onDraw(canvas: Canvas) {
        //内环外接矩形
        rectF.left = strokeWidth + paddingLeft
        rectF.top = strokeWidth + paddingTop
        rectF.right = 2 * (r - strokeWidth) + paddingLeft
        rectF.bottom = 2 * (r - strokeWidth) + paddingTop

        //画背景扇形
        mPaint.color = colorBg
        mPath.addArc(rectF, -225f, 270f)
        canvas.drawPath(mPath, mPaint)

        //画进度扇形
        mPath.reset()
        if (progress > 100) {
            progress = 100
        }
        if (progress < 0) {
            progress = 0
        }
        mPaint.color = colorProgress
        mPath.addArc(rectF, -225f, 270 * progress / 100f)
        canvas.drawPath(mPath, mPaint)

        //画图片
        if (bitmap.width.coerceAtLeast(bitmap.height) > sqrt(2.0) * (r - strokeWidth)) {
            val offset = (sqrt(2.0) * 0.5 * (r - strokeWidth)).toFloat()
            rectF.left = r - offset + paddingLeft
            rectF.right = r + offset + paddingLeft
            rectF.top = r - offset + paddingTop
            rectF.bottom = r + offset + paddingBottom
            canvas.drawBitmap(bitmap, null, rectF, mPaint)
        } else {
            canvas.drawBitmap(
                bitmap,
                r - bitmap.width / 2 + paddingLeft,
                r - bitmap.height / 2 + paddingTop,
                mPaint
            )
        }
    }

    private var xDown = 0f
    private var xUp = 0f
    private var yDown = 0f
    private var yUp = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                xDown = event.x
                yDown = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                xUp = event.x
                yUp = event.y

                val offsetX = xUp - xDown
                val offsetY = yUp - yDown
                if (abs(offsetX) > abs(offsetY)) {
                    if (offsetX > OFFSET) {
                        progress++
                        invalidate()
                    } else if (offsetX < -1 * OFFSET) {
                        progress--
                        invalidate()
                    }
                } else {
                    if (offsetY > OFFSET) {
                        progress--
                        invalidate()
                    } else if (offsetY < -1 * OFFSET) {
                        progress++
                        invalidate()
                    }
                }
            }
        }

        return super.onTouchEvent(event)
    }

    fun getProgress(): Int {
        return progress
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }
}