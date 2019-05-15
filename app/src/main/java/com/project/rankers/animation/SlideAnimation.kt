package com.project.rankers.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * Created by Emil on 21/10/17.
 */

class SlideAnimation(internal var mView: View, internal var mFromHeight: Int, internal var mToHeight: Int) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val newHeight: Int

        if (mView.height != mToHeight) {
            newHeight = (mFromHeight + (mToHeight - mFromHeight) * interpolatedTime).toInt()
            mView.layoutParams.height = newHeight
            mView.requestLayout()
        }
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}