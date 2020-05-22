package com.project.mvvmframe.util

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.project.mvvmframe.app.BaseApp
import org.jetbrains.annotations.NotNull

/**
 * SizeUtils
 * @CreateDate 2019/12/10 11:44
 * @Author jaylm
 */
object USize {

    /**
     * dp转px
     * @param dpValue dp值
     * @return px值
     */
    fun dp2px(dpValue: Float): Int {
        val scale = BaseApp.context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    fun px2dp(pxValue: Float): Int {
        val scale = BaseApp.context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = BaseApp.context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    fun px2sp(pxValue: Float): Int {
        val fontScale = BaseApp.context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 各种单位转换
     *
     * 该方法存在于TypedValue
     *
     * @param unit    单位
     * @param value   值
     * @param metrics DisplayMetrics
     * @return 转换结果
     */
    fun applyDimension(unit: Int, value: Float, metrics: DisplayMetrics): Float {
        when (unit) {
            TypedValue.COMPLEX_UNIT_PX -> return value
            TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
            TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
        }
        return 0f
    }

    /**
     * 在onCreate中获取视图的尺寸
     *
     * @param view     视图
     * @param listener 监听器
     */
    fun getViewSize(view: View, @NotNull listener: OnSizeListener) {
        view.post {
            listener.onGetSize(measureView(view))
        }
    }

    /**
     * 获取到View尺寸的监听
     */
    interface OnSizeListener {
        fun onGetSize(s: IntArray)
    }


    /**
     * 测量视图尺寸
     *
     * @param view 视图
     * @return arr[0]: 视图宽度, arr[1]: 视图高度
     */
    private fun measureView(view: View): IntArray {
        var lp: ViewGroup.LayoutParams? = view.layoutParams
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
        view.measure(widthSpec, heightSpec)
        return intArrayOf(view.measuredWidth, view.measuredHeight)
    }

    /**
     * 获取测量视图宽度
     *
     * @param view 视图
     * @return 视图宽度
     */
    private fun getMeasuredWidth(view: View): Int {
        return measureView(view)[0]
    }

    /**
     * 获取测量视图高度
     *
     * @param view 视图
     * @return 视图高度
     */
    private fun getMeasuredHeight(view: View): Int {
        return measureView(view)[1]
    }


    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    fun getScreenWidth(): Int {
        val windowManager =
            BaseApp.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics() // 创建了一张白纸
        windowManager.defaultDisplay.getMetrics(dm) // 给白纸设置宽高
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
     * 获取屏幕旋转角度
     *
     * @param activity DemoActivity
     * @return 屏幕旋转角度
     */
    fun getScreenRotation(activity: Activity): Int {
        return when (activity.windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity DemoActivity
     * @return Bitmap
     */
    fun captureWithStatusBar(activity: Activity, containBars: Boolean): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)

        val ret = if (containBars) {
            Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
        } else {
            val statusBarHeight = getStatusBarHeight()
            Bitmap.createBitmap(
                bmp,
                0,
                statusBarHeight,
                dm.widthPixels,
                dm.heightPixels - statusBarHeight
            )
        }
        view.destroyDrawingCache()
        return ret
    }


    /**
     * 判断是否锁屏
     *
     * @return true:是 false:否
     */
    fun isScreenLock(): Boolean {
        val km = BaseApp.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return km.inKeyguardRestrictedInputMode()
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
}