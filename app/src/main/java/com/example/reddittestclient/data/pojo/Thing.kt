package com.example.reddittestclient.data.pojo

import com.google.gson.annotations.SerializedName

data class Thing(
    @SerializedName("kind") val kind: String?,
    @SerializedName("data") val data: RedditListening?
)
