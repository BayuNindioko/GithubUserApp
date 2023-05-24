package com.example.githubuser

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Test
    fun testMenuOptionClick() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.setting_menu)).perform(click())
        onView(withId(R.id.theme_activity)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun testMenuFavoriteClick() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.favorite_menu)).perform(click())
        onView(withId(R.id.favorite_activity)).check(matches(isDisplayed()))

        activityScenario.close()
    }
}