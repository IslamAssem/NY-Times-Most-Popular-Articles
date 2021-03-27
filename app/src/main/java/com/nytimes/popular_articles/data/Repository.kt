package com.nytimes.popular_articles.data

import com.nytimes.popular_articles.data.network.ApiRequests
import com.nytimes.popular_articles.data.network.getResult
import com.nytimes.popular_articles.models.ArticlesResponse
import com.nytimes.popular_articles.ui.articleslist.ArticlesList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiRequests: ApiRequests) {
    suspend fun getArticles() : Flow<Resource<ArticlesResponse>> = flow<Resource<ArticlesResponse>>
    {
        emit(getResult() { apiRequests.getArticles() })
    }
}