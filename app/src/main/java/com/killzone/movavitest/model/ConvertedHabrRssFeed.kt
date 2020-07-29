package com.killzone.movavitest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConvertedHabrRssFeed(
    var channel: ConvertedHabrChannel = ConvertedHabrChannel(),
    var posts: MutableList<ConvertedHabrPost> = mutableListOf()
) : Parcelable

@Parcelize
data class ConvertedHabrChannel(
    var title: String = "",
    var link: String = "",
    var imageTitle: String = "",
    var imageUrl: String = "",
    var imageLink: String = ""
) : Parcelable

@Parcelize
data class ConvertedHabrPost(
    var guid: String = "",
    var title: String = "",
    var description: String = "",
    val imageUrl: String? = "",
    var link: String = "",
    var pubDate: String = "",
    var creator: String = "",
    var categories: MutableList<String> = mutableListOf()
) : Parcelable {
    fun convertToMillis(): Long {
        return 1
    }
}