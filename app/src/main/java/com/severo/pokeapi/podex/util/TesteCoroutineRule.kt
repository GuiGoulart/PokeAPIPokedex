package com.severo.pokeapi.podex.util

import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TesteCoroutineRule : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    override fun apply(base: Statement?, description: Description?): Statement {
        TODO("Not yet implemented")
    }

}