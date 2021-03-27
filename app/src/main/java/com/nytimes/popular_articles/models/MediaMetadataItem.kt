package com.nytimes.popular_articles.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaMetadataItem(

	@field:SerializedName("format")
	val format: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("height")
	val height: Int
) : Parcelable