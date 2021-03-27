package com.nytimes.popular_articles.ui.article_details

import android.os.Bundle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nytimes.popular_articles.R
import com.nytimes.popular_articles.models.Article
import com.nytimes.popular_articles.models.MediaItem
import com.nytimes.popular_articles.utils.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ArticleDetailsTest {
    private val article by lazy {
        Article(
            "Health",
            "this is health article",
            ArrayList<MediaItem>(),
            "Health Article",
            1,
            "30/03/2021",
            "by NewYork Times"
        )
    }

    @Before
    fun setup() {
        val bundle = Bundle()
        bundle.putParcelable("article", article)
        bundle.putString("title", article.title)
        launchFragmentInHiltContainer<ArticleDetails>(bundle)
    }

    @Test
    fun testArticleDetailsFragmentVisible() {
        Espresso.onView(withId(R.id.title))
            .check(ViewAssertions.matches(withText(article.title)))
        Espresso.onView(withId(R.id.content))
            .check(ViewAssertions.matches(withText(article.jsonMemberAbstract)))
        Espresso.onView(withId(R.id.date))
            .check(ViewAssertions.matches(withText(article.publishedDate)))
        Espresso.onView(withId(R.id.by))
            .check(ViewAssertions.matches(withText(article.byline)))
    }
}