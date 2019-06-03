/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.project.rankers

import android.app.Activity
import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.kakao.auth.KakaoSDK
import com.project.rankers.kakao.KakaoSDKAdapter
import com.project.rankers.utils.AppLogger
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

/**
 * Created by amitshekhar on 07/07/17.
 */

class MvvmApp : Application(), HasActivityInjector {

    var screenHeight: Int = 0

    @Inject
    internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    @Inject
    internal var mCalligraphyConfig: CalligraphyConfig? = null

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        KakaoSDK.init(KakaoSDKAdapter())

        AppLogger.init()

        AndroidNetworking.initialize(applicationContext)
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig)


    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }
    companion object {
        @get:Synchronized
        var instance: MvvmApp? = null
            private set

        private var globalApplication: MvvmApp? = null

        val globalApplicationContext: MvvmApp
            get() {
                if (instance == null) {
                    throw IllegalStateException("This Application does not inherit com.kakao.GlobalApplication")
                }
                return instance as MvvmApp
            }
    }
}
