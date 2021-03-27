package com.nytimes.popular_articles.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.islamassem.utils.BuildConfig
import com.islamassem.utils.LocalHelper
import com.islamassem.utils.getAuthorization
import com.islamassem.utils.logE
import com.nytimes.popular_articles.data.network.ApiRequests
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .apply {
            if (BuildConfig.DEBUG)
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            /*
            uncomment code if needed to add authorization or language header
             addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val originalHttpUrl = original.url
                    val url = originalHttpUrl.newBuilder().build()
                    val lang: String = LocalHelper.getDefaultLanguage()
                    // Request customization: add request headers
                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                        .addHeader("Accept-Language", lang)
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", getAuthorization())
                        .url(url)
                    logE("ok http client parameters-----------------------------")
                    logE(getAuthorization())
                    logE(lang)
                    logE("locale", lang)
                    logE("ok http client parameters-----------------------------")
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }

            }
            */

        }
        .build()

    @Provides
    fun provideApiRequests(retrofit: Retrofit): ApiRequests =
        retrofit.create(ApiRequests::class.java)
}