package com.project.mvvmframe.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.project.mvvmframe.R
import com.project.mvvmframe.util.UShape

/**
 * @CreateDate 2020/6/1 11:08
 * @Author jaylm
 */

class LineChartMarkView(context: Context) :
    MarkerView(context, R.layout.layout_chart_mark_view) {
    private lateinit var mValueFormatter: IAxisValueFormatter
    private lateinit var tvDate: TextView
    private lateinit var tvValue: TextView

    constructor(context: Context, iAxisValueFormatter: IAxisValueFormatter) : this(context) {
        this.mValueFormatter = iAxisValueFormatter
        tvDate = findViewById(R.id.tv_date)
        tvValue = findViewById(R.id.tv_value)
        val rootView = findViewById<View>(R.id.ll_root)
        UShape.setBackgroundDrawable(
            UShape.getCornerDrawable(Color.parseColor("#66000000"), 4),
            rootView
        )
    }

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry, highlight: Highlight) {
        tvDate.text = mValueFormatter.getFormattedValue(e.x, null)
        tvValue.text = "价格:${e.y}"
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(width / 2F * -1, height * 1f)
    }
}