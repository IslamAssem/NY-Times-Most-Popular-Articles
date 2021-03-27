package com.nytimes.popular_articles.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nytimes.popular_articles.data.Repository
import com.nytimes.popular_articles.data.Resource
import com.nytimes.popular_articles.data.network.ApiRequests
import com.nytimes.popular_articles.models.Article
import com.nytimes.popular_articles.models.ArticlesResponse
import com.nytimes.popular_articles.models.MediaItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ArticlesViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var apiRequests: ApiRequests

    private lateinit var viewModel: ArticlesListViewModel
    @Mock
    private lateinit var articlesObserver: Observer<Resource<ArticlesResponse>>
    @Captor
    private lateinit var captor: ArgumentCaptor<ArticlesResponse>
    private val articlesResponse by lazy {

        val article = Article(
            "Health",
            "this is health article",
            ArrayList<MediaItem>(),
            "Health Article",
            1,
            "30/03/2021",
            "by NewYork Times"
        )
        ArticlesResponse("NY Times", listOf(article),1,"true")
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {

        runBlockingTest {
            apiRequests = object : ApiRequests {
                override suspend fun getArticles(
                    period: Int,
                    apiKey: String
                ): Response<ArticlesResponse> {
                    Thread.sleep(1000)
                    return Response.success(articlesResponse)
                }
            }
            val repository = Repository(apiRequests)

//            `when`(repository.getArticles()).thenReturn(articlesFlow)
            viewModel = ArticlesListViewModel(repository)
            viewModel._articlesLiveData.observeForever(articlesObserver)
            viewModel.getArticles()
            Thread.sleep(100)
            Assert.assertEquals(Resource.loading(null),viewModel._articlesLiveData.value)
            Thread.sleep(2000)
            Assert.assertEquals(Resource.success(articlesResponse),viewModel._articlesLiveData.value)
            viewModel._articlesLiveData.removeObserver(articlesObserver)

        }
    }
}