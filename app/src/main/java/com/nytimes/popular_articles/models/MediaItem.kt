package com.nytimes.popular_articles.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaItem(

	@field:SerializedName("copyright")
	val copyright: String,

	@field:SerializedName("media-metadata")
	val mediaMetadata: List<MediaMetadataItem>,

	@field:SerializedName("subtype")
	val subtype: String,

	@field:SerializedName("caption")
	val caption: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("approved_for_syndication")
	val approvedForSyndication: Int
) : Parcelable