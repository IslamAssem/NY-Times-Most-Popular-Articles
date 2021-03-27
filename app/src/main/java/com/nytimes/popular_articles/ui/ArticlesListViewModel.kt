package com.nytimes.popular_articles.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nytimes.popular_articles.data.Repository
import com.nytimes.popular_articles.data.Resource
import com.nytimes.popular_articles.models.Article
import com.nytimes.popular_articles.models.ArticlesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val _articlesLiveData: MutableLiveData<Resource<ArticlesResponse>> by lazy {
        MutableLiveData<Resource<ArticlesResponse>>()
    }
    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getArticles()
                .onStart {
                    _articlesLiveData.postValue(Resource.loading())
                }
                .catch {
                    _articlesLiveData.postValue(Resource.error(it.message.toString()))
                }
                .collect {
                    _articlesLiveData.postValue(it)
                }
        }
    }
}