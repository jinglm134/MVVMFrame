package com.project.mvvmframe.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.project.mvvmframe.app.BaseApp

/**
 * @CreateDate 2019/12/10 11:44
 * @Author jaylm
 */

//dp转px
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

//sp转px
val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

//px转dp
fun Float.todp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

//px转sp
fun Float.tosp(): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (this / fontScale + 0.5f).toInt()
}

/**
 * 测量视图尺寸
 *
 * @return arr[0]: 视图宽度, arr[1]: 视图高度
 */
fun View.measureView(): IntArray {
    var lp: ViewGroup.LayoutParams? = layoutParams
    if (lp == null) {
        lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
    val lpHeight = lp.height
    val heightSpec = if (lpHeight > 0) {
        View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
    } else {
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    }
    measure(widthSpec, heightSpec)
    return intArrayOf(measuredWidth, measuredHeight)
}


/**
 * 获取屏幕的宽度（单位：px）
 * @return 屏幕宽px
 */
fun getScreenWidth(): Int {
    val windowManager =
        BaseApp.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}


/**
 * 获取屏幕的高度（单位：px）
 *
 * @return 屏幕高px
 */
fun getScreenHeight(): Int {
    val windowManager =
        BaseApp.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}

/**
 * 获取状态栏高度
 *
 * @return 状态栏高度
 */
private fun getStatusBarHeight(): Int {
    var result = -1
    val resourceId =
        BaseApp.context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = BaseApp.context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}