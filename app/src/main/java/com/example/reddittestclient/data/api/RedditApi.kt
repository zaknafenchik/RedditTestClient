package com.example.reddittestclient.data.api

import com.example.reddittestclient.data.pojo.Thing
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("top.json")
    fun loadItems(
        @Query("after") after: String?,
        @Query("limit") limit: Int = 10,
        @Query("raw_json") rawJson: Int = 1
    ): Single<Thing>
}