package com.example.reddittestclient.feature.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.reddittestclient.data.pojo.*
import com.example.reddittestclient.data.repo.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(val repo: Repo) : ViewModel() {
    val cd = CompositeDisposable()

    var after: String? = null
    val tag = MainViewModel::class.java.simpleName
    val items: MutableList<Item> = mutableListOf()
    var data: MutableLiveData<Resource<List<Item>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                loadItems()
            }
            return field
        }

    private var loading = false

    private fun loadItems() {
        cd.add(
            repo.loadItems(after)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loading = true
                    data?.value = Resource(State.LOADING)
                }
                .doAfterTerminate { loading = false }
                .subscribe(
                    { handleResponse(it) },
                    { handleError(it) })
        )
    }

    private fun handleError(it: Throwable) {
        Log.d(tag, it.message)
        data?.value = Resource(State.FAILED, null, it)
    }

    private fun handleResponse(it: Thing) {
        after = it.data?.after
        it.data?.children?.let { it1 -> mapData(it1) }?.let { it2 -> items.addAll(it2) }
        data?.value = Resource(State.SUCCESS, items)
    }

    private fun mapData(children: List<Children>): List<Item> {
        val items = arrayListOf<Item>()
        for (child in children) {
            items.add(child.data)
        }
        return items
    }

    override fun onCleared() {
        super.onCleared()
        cd.dispose()
    }

    fun loadNextPage() {
        if (loading) return
        loadItems()
    }
}