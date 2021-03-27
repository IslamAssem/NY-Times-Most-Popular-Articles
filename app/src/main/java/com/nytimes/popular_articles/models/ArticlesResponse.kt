package com.nytimes.popular_articles.models

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("copyright")
	val copyright: String,

	@field:SerializedName("results")
	val results: List<Article>,

	@field:SerializedName("num_results")
	val numResults: Int,

	@field:SerializedName("status")
	val status: String
)