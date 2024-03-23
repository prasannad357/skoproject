package com.prasanna.skoproject.data.remote


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(@Query("page") page:Int): Response<UserResponseDto>
}