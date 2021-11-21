package com.sihabudin.itask.core.utils

sealed class DatabaseResponse<out R> {
    data class Success<out T>(val data: T): DatabaseResponse<T>()
    data class Error(val errorMessage: String) : DatabaseResponse<Nothing>()
    object Empty : DatabaseResponse<Nothing>()
}