package com.nytimes.popular_articles.data.network

 import com.nytimes.popular_articles.BuildConfig
 import com.nytimes.popular_articles.models.ArticlesResponse
 import retrofit2.Response
 import retrofit2.http.*

interface ApiRequests {
    companion object {
        //complete url example http://api.nytimes.com/svc/mostpopular/v2/viewed/%7Bperiod%7D.json?api-key=sample-key
        //available periods are 1,7 and 30
        const val API_KEY = "api-key"
        const val PERIOD = "period"
        const val DEFAULT_PERIOD = 7
        const val URL_PATH = "viewed/{$PERIOD}.json"

    }

    @GET(URL_PATH)
    suspend fun getArticles(
        @Path(PERIOD) period: Int = DEFAULT_PERIOD, @Query(
            API_KEY
        ) apiKey: String = BuildConfig.API_KEY
    ): Response<ArticlesResponse>

}