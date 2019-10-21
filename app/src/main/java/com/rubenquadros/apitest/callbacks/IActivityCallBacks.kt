package com.rubenquadros.apitest.callbacks

interface IActivityCallBacks {

    fun onSuccess(message: String)

    fun onFailed(message: String)
}