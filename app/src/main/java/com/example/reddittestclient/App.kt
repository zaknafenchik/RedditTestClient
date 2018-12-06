package com.example.reddittestclient

import android.app.Activity
import android.app.Application
import com.example.reddittestclient.di.AppInjector
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        Fresco.initialize(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = androidInjector
}