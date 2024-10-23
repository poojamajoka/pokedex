package com.pokemon.pokedex.data.network

enum class ApiStatus{
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ApiResHandling <out T> (val status: ApiStatus, val data: T?) {

    data class Success<out R>(val _data: R?): ApiResHandling<R>(status = ApiStatus.SUCCESS, data = _data)

    data class Error(val exception: String): ApiResHandling<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
    )

    data class Loading<out R>(val _data: R?, val isLoading: Boolean): ApiResHandling<R>(
        status = ApiStatus.LOADING,
        data = _data,
    )
}
/**
 * class Success<R> extend ApiReshandling<R>
 *
 *
 *
 *
 */
