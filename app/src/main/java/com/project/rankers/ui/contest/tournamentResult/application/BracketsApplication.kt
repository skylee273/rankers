package com.project.rankers.ui.contest.tournamentResult.application

import android.app.Application

/**
 * Created by vyshnav on 21/10/17.
 */

class BracketsApplication : Application() {

    var screenHeight: Int = 0

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        @get:Synchronized
        var instance: BracketsApplication? = null
            private set
    }
}
