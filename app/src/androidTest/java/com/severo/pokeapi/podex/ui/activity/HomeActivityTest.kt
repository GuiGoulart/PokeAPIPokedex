package com.severo.pokeapi.podex.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.severo.pokeapi.podex.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun checkActivityVisibility(){
        onView(withId(R.id.homeSearchView)).check(matches(withHint(R.string.search_pokemons)))
    }
}