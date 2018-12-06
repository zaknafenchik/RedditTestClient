package com.example.reddittestclient.di

import android.app.Application
import com.example.kotlinexample.di.AppModule
import com.example.reddittestclient.App
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class]
)
interface AppComponent : AndroidInjector<App> {


    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

    fun inject(app: Application)
}