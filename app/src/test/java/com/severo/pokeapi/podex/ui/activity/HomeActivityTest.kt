//package com.severo.pokeapi.podex.ui.activity
//
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.action.ViewActions.typeText
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
//import com.severo.pokeapi.podex.R
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.lang.Thread.sleep
//
//@RunWith(AndroidJUnit4ClassRunner::class)
//class HomeActivityTest {
//
//    @get:Rule
//    val activityRule = ActivityScenarioRule(HomeActivity::class.java)
//
//    @Test
//    fun whenSearchViewIsClicked() {
//        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
//        onView(withId(R.id.searchView)).check(matches(withHint(R.string.search_pokemons)))
//        onView(withId(R.id.searchView)).perform(typeText("pikac"))
//        onView(withId(R.id.searchView)).check(matches(withText("pikac")))
//
//        sleep(2000)
//        onView(withText("Pikachu")).perform(click())
//
//        onView(withId(R.id.detailsPokemonBack)).check(matches(isDisplayed()))
//    }
//}