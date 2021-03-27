package com.nytimes.popular_articles.utils
/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.content.ComponentName
import android.content.Intent
import com.nytimes.popular_articles.R
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.nytimes.popular_articles.ui.EmptyHiltActivity

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.Theme_AppCompat,
    fragmentFactory: FragmentFactory? = null,
    navController: NavController? = null,
    crossinline action: Fragment.() -> Unit = {}
): ActivityScenario<EmptyHiltActivity> {
    val startActivityIntent = startEmptyActivity(themeResId)

    val activityScenario = ActivityScenario.launch<EmptyHiltActivity>(startActivityIntent)
    activityScenario.onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )

        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        fragment.action()
        navController?.let {
            Navigation.setViewNavController(fragment.requireView(), it)
        }
    }
    return activityScenario
}

fun startEmptyActivity(themeResId: Int): Intent {
    val key = "androidx.fragment.app.testing.FragmentScenario" +
            ".EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY"
    return Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            EmptyHiltActivity::class.java
        )
    ).putExtra(key, themeResId)
}