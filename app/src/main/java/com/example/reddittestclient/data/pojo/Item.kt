package com.example.reddittestclient.data.pojo

import android.support.v7.util.DiffUtil
import com.google.gson.annotations.SerializedName

data class Item(
    val author: String?,
    val title: String?,
    val preview: Preview?,
    @SerializedName("thumbnail") val thumbnail: String?
) {
    companion object {
        const val UNKNOWN_ID = -1

        val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}
