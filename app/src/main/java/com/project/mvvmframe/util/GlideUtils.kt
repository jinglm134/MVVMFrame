package com.project.mvvmframe.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

/**
 * @CreateDate 2020/3/7 11:50
 * @Author jaylm
 */
object GlideUtils {
    fun showImages(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url).into(imageView)
    }

    fun showImages(imageView: ImageView, url: String, @DrawableRes drawableRes: Int) {
        Glide.with(imageView.context).load(url).placeholder(drawableRes).error(drawableRes)
            .into(imageView)
    }
}