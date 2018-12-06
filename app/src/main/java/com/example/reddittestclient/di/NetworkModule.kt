package com.example.reddittestclient.di

import android.content.Context
import com.example.reddittestclient.data.api.HttpInterceptor

import com.example.reddittestclient.data.api.RedditApi
import com.example.reddittestclient.di.qualifier.ApplicationContext
import com.google.gson.Gson

import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    internal fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(context))
            .addInterceptor(HttpInterceptor())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(client: OkHttpClient, @Named("base_url") baseUrl: String, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    internal fun provideApiService(retrofit: Retrofit): RedditApi {
        return retrofit.create(RedditApi::class.java)
    }

    @Singleton
    @Named("base_url")
    @Provides
    internal fun provideBaseUrl(): String {
        return "https://www.reddit.com/"
    }
}
