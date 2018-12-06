package com.example.reddittestclient.di


import com.example.reddittestclient.feature.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity
}