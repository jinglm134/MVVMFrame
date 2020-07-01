package com.project.mvvmframe.util

import android.R
import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import com.project.mvvmframe.app.BaseApp

/**
 * Shape工具类
 * @CreateDate 2019/12/9 14:42
 * @Author jaylm
 */
object UShape {
    /**
     * 图片圆形
     *
     * @param bitmap Bitmap
     * @return Bitmap
     */
    private fun getCircleBitmap(bitmap: Bitmap): Bitmap {
        val min = 200
        val outBitmap = Bitmap.createBitmap(
            min,
            min, Bitmap.Config.ARGB_8888
        )
        val paint = Paint()
        paint.isAntiAlias = true
        val rect = Rect(0, 0, min, min)
        val canvas = Canvas(outBitmap)
        canvas.drawCircle((min / 2).toFloat(), (min / 2).toFloat(), (min / 2).toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, Rect(0, 0, bitmap.width, bitmap.height), rect, paint)
        return outBitmap

    }

    /**
     * 将图片圆角,默认4dp圆角
     *
     * @param bitmap Bitmap
     * @return Bitmap
     */
    fun getRoundedCornerBitmap(bitmap: Bitmap): Bitmap {
        return getRoundedCornerBitmap(bitmap, 4f.dp)
    }

    private fun getRoundedCornerBitmap(bitmap: Bitmap, radius: Float): Bitmap {
        val min = 200
        val outBitmap = Bitmap.createBitmap(
            min,
            min, Bitmap.Config.ARGB_8888
        )
        val paint = Paint()
        paint.isAntiAlias = true
        val rect = Rect(0, 0, min, min)
        val rectF = RectF(rect)
        val canvas = Canvas(outBitmap)
        canvas.drawRoundRect(rectF, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, Rect(0, 0, bitmap.width, bitmap.height), rect, paint)
        return outBitmap
    }

    /**
     * 圆角纯色ShapeDrawable
     *
     * @param color  颜色  R.color.c8
     * @param corner 圆角大小,单位dp,没有圆角传0
     * @return RoundRectShape(float[] outerRadii, RectF inset, float[] innerRadii) :指定一个外部（圆角）矩形 和 一个 可选的 内部（圆角）矩形。
     */
    fun getCornerDrawable(color: Int, corner: Int): ShapeDrawable {
        val corners = FloatArray(8)
        for (i in corners.indices) {
            corners[i] = corner.toFloat()
        }
        return getCornerDrawable(color, corners)
    }

    /**
     * 圆角纯色
     *
     * @param color   颜色
     * @param corners 圆角大小,单位px，8个角度不一样时使用，八个角度按顺序依次是左上 右上 右下 左下
     * @return RoundRectShape(float[] outerRadii, RectF inset, float[] innerRadii) :指定一个外部（圆角）矩形 和 一个 可选的 内部（圆角）矩形。
     */
    private fun getCornerDrawable(color: Int, corners: FloatArray): ShapeDrawable {
        val martCorners = FloatArray(corners.size)
        for (i in corners.indices) {
            martCorners[i] = corners[i].dp
        }

        val drawable = ShapeDrawable(RoundRectShape(martCorners, null, null))
        drawable.paint.color = color
        drawable.paint.style = Paint.Style.FILL
        return drawable
    }

    /**
     * 有各种效果的圆角按钮Drawable,padding 默认为0dp，有需求可修改padding
     * setShapeDrawablePadding(getCornerDrawable(normalColor, corner), null)
     *
     * @param normalColor   原本的颜色,该值必须有
     * @param checkedColor  state_check=true选中的颜色
     * @param selectedColor state_selector=true选中的颜色
     * @param pressColor    state_press=true按下的颜色
     * @param corner        圆角大小
     */
    private fun getSelectorDrawable(
        normalColor: Int = 0,
        checkedColor: Int = 0,
        selectedColor: Int = 0,
        pressColor: Int = 0,
        corner: Int = 0
    ): StateListDrawable {
        val listDrawable =
            StateListDrawable()
        if (checkedColor != 0) {
            listDrawable.addState(
                intArrayOf(-R.attr.state_checked),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corner), null)
            )
            listDrawable.addState(
                intArrayOf(R.attr.state_checked),
                setShapeDrawablePadding(getCornerDrawable(checkedColor, corner), null)
            )
        }
        if (selectedColor != 0) {
            listDrawable.addState(
                intArrayOf(-R.attr.state_selected),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corner), null)
            )
            listDrawable.addState(
                intArrayOf(R.attr.state_selected),
                setShapeDrawablePadding(
                    getCornerDrawable(selectedColor, corner),
                    null
                )
            )
        }
        if (pressColor != 0) {
            listDrawable.addState(
                intArrayOf(-R.attr.state_pressed),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corner), null)
            )
            listDrawable.addState(
                intArrayOf(R.attr.state_pressed),
                setShapeDrawablePadding(getCornerDrawable(pressColor, corner), null)
            )
        }
        if (normalColor != 0) {
            listDrawable.addState(
                intArrayOf(),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corner), null)
            )
        }
        return listDrawable
    }


    private fun getSelectorDrawable(
        normalDrawable: Drawable? = null,
        checkedDrawable: Drawable? = null,
        selectedDrawable: Drawable? = null,
        pressDrawable: Drawable? = null
    ): StateListDrawable {
        val listDrawable =
            StateListDrawable()
        if (checkedDrawable != null) {
            listDrawable.addState(intArrayOf(-R.attr.state_checked), normalDrawable)
            listDrawable.addState(intArrayOf(R.attr.state_checked), checkedDrawable)
        }
        if (selectedDrawable != null) {
            listDrawable.addState(intArrayOf(-R.attr.state_selected), normalDrawable)
            listDrawable.addState(intArrayOf(R.attr.state_selected), selectedDrawable)
        }
        if (pressDrawable != null) {
            listDrawable.addState(intArrayOf(-R.attr.state_pressed), normalDrawable)
            listDrawable.addState(intArrayOf(R.attr.state_pressed), pressDrawable)
        }
        if (normalDrawable != null) {
            listDrawable.addState(intArrayOf(), normalDrawable)
        }
        return listDrawable
    }


    /**
     * checkedSelector
     *
     * @param normalColor  原本的颜色
     * @param checkedColor state_check=true选中的颜色
     * @param corner       圆角大小
     */
    fun getCheckedDrawable(
        normalColor: Int,
        checkedColor: Int,
        corner: Int
    ): StateListDrawable {
        return getSelectorDrawable(
            normalColor = normalColor,
            checkedColor = checkedColor,
            corner = corner
        )
    }

    /**
     * checkedSelector
     *
     * @param normalDrawable  原本的Drawable
     * @param checkedDrawable state_check=true选中的Drawable
     */
    fun getCheckedDrawable(
        normalDrawable: Drawable,
        checkedDrawable: Drawable
    ): StateListDrawable {
        return getSelectorDrawable(
            normalDrawable = normalDrawable,
            checkedDrawable = checkedDrawable
        )
    }

    /**
     * SelectedSelector
     *
     * @param normalColor   原本的颜色
     * @param selectedColor state_selector=true选中的颜色
     * @param corner        圆角大小
     */
    fun getSelectedDrawable(
        normalColor: Int,
        selectedColor: Int,
        corner: Int
    ): StateListDrawable {
        return getSelectorDrawable(
            normalColor = normalColor,
            selectedColor = selectedColor,
            corner = corner
        )
    }

    /**
     * SelectedSelector
     *
     * @param normalDrawable   原本的Drawable
     * @param selectedDrawable state_selector=true选中的Drawable
     */
    fun getSelectedDrawable(
        normalDrawable: Drawable,
        selectedDrawable: Drawable
    ): StateListDrawable {
        return getSelectorDrawable(
            normalDrawable = normalDrawable,
            selectedDrawable = selectedDrawable
        )
    }

    /**
     * pressedSelector
     *
     * @param normalColor 原本的颜色
     * @param pressColor  state_press=true按下的颜色
     * @param corner      圆角大小
     */
    fun getPressedDrawable(
        normalColor: Int,
        pressColor: Int,
        corner: Int
    ): StateListDrawable {
        return getSelectorDrawable(
            normalColor = normalColor,
            pressColor = pressColor,
            corner = corner
        )
    }

    /**
     * pressedSelector
     *
     * @param normalDrawable 原本的Drawable
     * @param pressDrawable  state_press=true按下的Drawable
     */
    fun getPressedDrawable(
        normalDrawable: Drawable,
        pressDrawable: Drawable
    ): StateListDrawable {
        return getSelectorDrawable(normalDrawable = normalDrawable, pressDrawable = pressDrawable)
    }


    /*******************************圆角大小不一致时调用********************************/


    /**
     * 有各种效果的圆角按钮Drawable,padding 默认为0dp，有需求可修改padding
     * setShapeDrawablePadding(getCornerDrawable(normalColor, corner), null)
     *
     * @param normalColor   原本的颜色,该值必须有
     * @param checkedColor  state_check=true选中的颜色
     * @param selectedColor state_selector=true选中的颜色
     * @param pressColor    state_press=true按下的颜色
     * @param corners       圆角大小
     */
    private fun getSelectorDrawable(
        normalColor: Int,
        checkedColor: Int,
        selectedColor: Int,
        pressColor: Int,
        corners: FloatArray
    ): StateListDrawable {
        val listDrawable = StateListDrawable()

        if (checkedColor != 0) {
            listDrawable.addState(
                intArrayOf(-R.attr.state_checked),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corners), null)
            )
            listDrawable.addState(
                intArrayOf(R.attr.state_checked),
                setShapeDrawablePadding(getCornerDrawable(checkedColor, corners), null)
            )
        }
        if (selectedColor != 0) {
            listDrawable.addState(
                intArrayOf(-R.attr.state_selected),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corners), null)
            )
            listDrawable.addState(
                intArrayOf(R.attr.state_selected),
                setShapeDrawablePadding(getCornerDrawable(selectedColor, corners), null)
            )
        }
        if (pressColor != 0) {
            listDrawable.addState(
                intArrayOf(-R.attr.state_pressed),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corners), null)
            )
            listDrawable.addState(
                intArrayOf(R.attr.state_pressed),
                setShapeDrawablePadding(getCornerDrawable(pressColor, corners), null)
            )
        }
        if (normalColor != 0) {
            listDrawable.addState(
                intArrayOf(),
                setShapeDrawablePadding(getCornerDrawable(normalColor, corners), null)
            )
        }
        return listDrawable
    }


    /**
     * checkedSelector
     *
     * @param normalColor  原本的颜色
     * @param checkedColor state_check=true选中的颜色
     * @param corners      圆角大小
     */
    fun getCheckedDrawable(
        normalColor: Int,
        checkedColor: Int,
        corners: FloatArray
    ): StateListDrawable {
        return getSelectorDrawable(normalColor, checkedColor, 0, 0, corners)
    }

    /**
     * SelectedSelector
     *
     * @param normalColor   原本的颜色
     * @param selectedColor state_selector=true选中的颜色
     * @param corners       圆角大小
     */
    fun getSelectedDrawable(
        normalColor: Int,
        selectedColor: Int,
        corners: FloatArray
    ): StateListDrawable {
        return getSelectorDrawable(normalColor, 0, selectedColor, 0, corners)
    }

    /**
     * pressedSelector
     *
     * @param normalColor 原本的颜色
     * @param pressColor  state_press=true按下的颜色
     * @param corners     圆角大小
     */
    fun getPressedDrawable(
        normalColor: Int,
        pressColor: Int,
        corners: FloatArray
    ): StateListDrawable {
        return getSelectorDrawable(normalColor, 0, 0, pressColor, corners)
    }


    /**
     * 给ShapeDrawable设置padding
     *
     * @param drawable ShapeDrawable
     * @param padding  间距
     * @return ShapeDrawable
     */
    private fun setShapeDrawablePadding(
        drawable: ShapeDrawable,
        padding: IntArray?
    ): ShapeDrawable {
        if (padding != null && padding.size > 3) {
            drawable.setPadding(padding[0], padding[1], padding[2], padding[3])
        }
        return drawable
    }


    /**
     * 圆角边框1dp
     *
     * @param strokeColor 边框颜色
     * @param solidColor  填充颜色
     * @param corner      圆角大小
     * @return GradientDrawable
     */
    fun getStrokeDrawable(strokeColor: Int, solidColor: Int, corner: Int): GradientDrawable {
        val gd = GradientDrawable()
        gd.setColor(solidColor)
        gd.cornerRadius = corner.toFloat().dp
        gd.setStroke(1f.dp.toInt(), strokeColor)
        return gd
    }

    /**
     * 纯色圆，如果View是长方形则显示为椭圆
     *
     * @param color 颜色
     * @return drawable
     */
    fun getCircleDrawable(color: Int): ShapeDrawable {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = color
        drawable.paint.style = Paint.Style.FILL
        return drawable
    }

    /**
     * 设置显示背景图片
     */
    @SuppressLint("NewApi")
    fun setBackgroundDrawable(drawable: Drawable, vararg views: View) {
        for (view in views) {
            view.background = drawable
        }
    }

    /**
     * 获取颜色
     *
     * @param rId rId
     * @return int color
     */
    @Suppress("deprecation")
    fun getColor(@ColorRes rId: Int): Int {
        val context = BaseApp.context
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(rId)
        } else {
            context.resources.getColor(rId)
        }
    }
}