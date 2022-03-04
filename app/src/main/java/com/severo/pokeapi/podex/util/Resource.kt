package com.severo.pokeapi.podex.util

class Resource<T> private constructor(
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

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data)
        }
    }
}