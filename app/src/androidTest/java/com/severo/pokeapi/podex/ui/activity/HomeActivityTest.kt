package com.severo.pokeapi.podex.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.severo.pokeapi.podex.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun checkActivityVisibility(){
        onView(withId(R.id.homeSearchView)).check(matches(isDisplayed()))
        onView(withId(R.id.homeSearchView)).check(matches(withHint(R.string.search_pokemons)))
        onView(withId(R.id.homeSearchView)).perform(typeText(NAME_POKEMON_SEARCH))
        onView(withId(R.id.homeSearchView)).check(matches(withText(NAME_POKEMON_SEARCH)))

        sleep(2000)
        onView(withText(NAME_POKEMON)).perform(click())

        onView(withId(R.id.detailsPokemonBack)).check(matches(isDisplayed()))
    }

    @Test
    fun checkButtonFavorite(){
        onView(withId(R.id.homePokemonFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.homePokemonFavorite)).perform(click())

        onView(withId(R.id.favoritePokemonBack)).check(matches(isDisplayed()))
    }

    private companion object {
        const val NAME_POKEMON_SEARCH = "Pikac"
        const val NAME_POKEMON = "Pikachu"
    }
}