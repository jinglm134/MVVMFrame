package com.project.mvvmframe.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import com.project.mvvmframe.R

/**
 * @CreateDate 2020/6/23 10:54
 * @Author jaylm
 */
class PraiseLayout : LinearLayout {
    private lateinit var imageView: ImageView
    private lateinit var praiseView: PraiseView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        imageView = findViewById(R.id.iv_praise)
        praiseView = findViewById(R.id.praiseView)

        setOnClickListener {
            praiseView.startAnimal(false,1000L)
        }
    }

    fun setText(text: Int) {
        praiseView.setNum(text)
    }

    fun getText(): Int {
        return praiseView.getNum()
    }
}