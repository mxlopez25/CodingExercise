package com.maloac.codingexercise

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @Test
    fun test_activityVisible() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.clMainContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.btnMakeRequest)).check(matches(isDisplayed()))
    }

    @Test
    fun test_progressBarInvisible() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btnMakeRequest))
            .check(matches(isDisplayed()))
        onView(withId(R.id.progressBar))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun test_progressBarVisible() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btnMakeRequest))
            .check(matches(isDisplayed()))
        onView(withId(R.id.btnMakeRequest))
            .perform(click())
        onView(withId(R.id.progressBar))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}