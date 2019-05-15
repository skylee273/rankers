package com.project.rankers.utils

import android.util.DisplayMetrics
import com.project.rankers.MvvmApp


/**
 * Created by Emil on 21/10/17.
 */

object TournamentUtility {
    fun dpToPx(dp: Int): Int {
        val displayMetrics = MvvmApp.instance!!.baseContext.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

}
