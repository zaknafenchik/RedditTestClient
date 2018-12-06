package com.example.reddittestclient.data.repo

import com.example.reddittestclient.data.api.RedditApi
import com.example.reddittestclient.data.pojo.Thing
import io.reactivex.Single

class RepoImpl(val api: RedditApi) : Repo {
    override fun loadItems(after: String?): Single<Thing> = api.loadItems(after)
}