package com.project.mvvmframe.ui.main.home

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.Color
import android.view.View
import android.view.animation.LinearInterpolator
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseActivity
import com.project.mvvmframe.util.UShape
import kotlinx.android.synthetic.main.activity_animator.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @CreateDate 2020/7/9 16:35
 * @Author jaylm
 */
class AnimatorActivity : BaseActivity() {
    override fun bindLayout(): Int = R.layout.activity_animator

    override fun initView(contentView: View) {
        UShape.setBackgroundDrawable(
            UShape.getCircleDrawable(Color.parseColor("#3AA6FB")),
            animalView
        )
    }

    override fun setListener() {
        super.setListener()

        animalView.setOnClickListener {
            animal()
        }
    }

    private var y0 = 0f
    private var y1 = 1000f
    private var animator: ObjectAnimator? = null
    fun animal() {
        animator = ObjectAnimator.ofFloat(
            animalView,
            "translationY",
            y0,
            y1
        )
        animator?.setEvaluator(object : TypeEvaluator<Float> {
            override fun evaluate(
                fraction: Float,
                startValue: Float?,
                endValue: Float?
            ): Float {
                if (startValue == null) {
                    return 0f
                }
                if (endValue == null) {
                    return 0f
                }
                return startValue.plus(
                    endValue.minus(startValue).times(fraction.pow(if (y1 > y0) 2f else 0.5f))
                )
            }
        })
        animator?.interpolator = LinearInterpolator()
        animator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if (y1 > y0 && y1 - y0 < 10) {
                    y0 = 0f
                    y1 = 1000f
                    return
                }
                val flag = y0
                if (y1 > y0) {
                    y0 = y1
                    y1 = flag + (y1 - flag) * 0.5f
                } else {
                    y0 = y1
                    y1 = flag
                }
                animal()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

        animator?.setDuration((sqrt(abs(y0 - y1)) * 50).toLong())?.start()
    }

    override fun onStop() {
        super.onStop()
        if (animator != null) {
            animator?.cancel()
        }
    }
}