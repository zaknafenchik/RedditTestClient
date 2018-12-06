package com.example.reddittestclient.feature.main

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.reddittestclient.R
import com.example.reddittestclient.data.pojo.Item
import com.example.reddittestclient.databinding.RedditItemBinding
import com.squareup.picasso.Picasso

class ItemsAdapter(val imageClickListener: (url: String) -> Unit) :
    ListAdapter<Item, ItemsAdapter.ItemViewHolder>(AsyncDifferConfig.Builder<Item>(Item.diffCallback).build()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<RedditItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.reddit_item,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
        Picasso.get().load(item.thumbnail).into(holder.binding.image)
        holder.binding.executePendingBindings()

        val url: String? = url(item)
        if (!TextUtils.isEmpty(url)) {
            holder.binding.image.setOnClickListener { item.thumbnail?.let { imageClickListener(url!!) } }
        }
    }

    private fun url(item: Item): String? {
        var isEmptyImages = true
        item.preview?.let { isEmptyImages = it.images.isEmpty() }
        var url: String? = null
        if (!isEmptyImages) {
            url = item.preview?.images?.get(0)?.source?.url
        }
        return url
    }

    class ItemViewHolder(val binding: RedditItemBinding) : RecyclerView.ViewHolder(binding.root)
}