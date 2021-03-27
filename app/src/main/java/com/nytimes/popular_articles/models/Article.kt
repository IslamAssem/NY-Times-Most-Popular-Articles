package com.nytimes.popular_articles.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(

	@field:SerializedName("section")
	val section: String,

	@field:SerializedName("abstract")
	val jsonMemberAbstract: String,

	@field:SerializedName("media")
	val media: List<MediaItem>,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("id")
	val id: Long,

	@field:SerializedName("published_date")
	val publishedDate: String,

	@field:SerializedName("byline")
	val byline: String
) : Parcelable