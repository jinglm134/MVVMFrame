package com.project.mvvmframe.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

/**
 * @CreateDate 2020/3/7 11:50
 * @Author jaylm
 */
fun ImageView.showImage(url: String) {
    Glide.with(this.context).load(url).into(this)
}

fun ImageView.showImage(url: String, @DrawableRes drawableRes: Int) {
    Glide.with(this.context).load(url).placeholder(drawableRes).error(drawableRes).into(this)
}