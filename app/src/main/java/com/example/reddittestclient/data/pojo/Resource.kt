package com.example.reddittestclient.data.pojo

class Resource<T>(val state: State, val data: T? = null, val exception: Throwable? = null) {
}