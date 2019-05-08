package com.project.rankers.ui.contest.tournamentResult.customviews

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View



import androidx.viewpager.widget.ViewPager
import com.project.rankers.MvvmApp

/**
 * Created by Emil on 21/10/17.
 */

class WrapContentHeightViewPager : ViewPager {
    private val screenSIze: IntArray
        get() {
            val displaymetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
            val h = displaymetrics.heightPixels
            val w = displaymetrics.widthPixels

            return intArrayOf(w, h)

        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var height = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            val h = child.measuredHeight

            if (h > height) height = h


            val screenHeight = MvvmApp.instance!!.screenHeight
            if (screenHeight > height)
                height = screenHeight

            //overriding wrap content feature
            // int[] screenSize = getScreenSIze();
            // height = 1800;
        }


        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}