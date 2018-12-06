package com.example.reddittestclient.data.repo

import com.example.reddittestclient.data.pojo.Thing
import io.reactivex.Single

interface Repo {
    fun loadItems(after: String? = null): Single<Thing>
}