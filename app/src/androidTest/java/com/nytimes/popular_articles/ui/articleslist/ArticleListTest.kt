package com.nytimes.popular_articles.ui.articleslist

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.nytimes.popular_articles.R
import com.nytimes.popular_articles.utils.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Matcher
import kotlin.random.Random
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ArticleListTest {
    private lateinit var navController: NavController
    @Before
    fun setup() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        runOnUiThread{navController.setGraph(R.navigation.news_navigation)}
        launchFragmentInHiltContainer<ArticlesList>(navController = navController)
    }

    @Test
    fun testViewsVisibility(){
        onView(withId(R.id.empty)).check(ViewAssertions.matches(not(isDisplayed())))
        onView(withId(R.id.error)).check(ViewAssertions.matches(not(isDisplayed())))
    }
    @Test
    fun testNavigationToArticleDetailsScreen() {
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    Random.nextInt(20), click()
                )
            )
        assertEquals(navController.currentDestination?.id,R.id.nav_details)
    }
}