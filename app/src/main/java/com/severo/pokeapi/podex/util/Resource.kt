package com.severo.pokeapi.podex.util

data class Resource<T> constructor(
    val status: Status,
    val data: T? = null
) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(): Resource<T> {
            return Resource(Status.ERROR)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }
}