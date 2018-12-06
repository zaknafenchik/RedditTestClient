package com.example.kotlinexample.di

import android.content.Context
import com.example.reddittestclient.App
import com.example.reddittestclient.data.api.RedditApi
import com.example.reddittestclient.data.repo.Repo
import com.example.reddittestclient.data.repo.RepoImpl
import com.example.reddittestclient.di.qualifier.ApplicationContext
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(api: RedditApi): Repo = RepoImpl(api)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @ApplicationContext
    @Singleton
    fun provideContext(app: App): Context = app
}