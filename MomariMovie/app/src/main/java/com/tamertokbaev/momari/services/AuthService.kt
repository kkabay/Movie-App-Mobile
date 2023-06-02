package com.tamertokbaev.momari.services

import com.tamertokbaev.momari.models.AuthResponse
import com.tamertokbaev.momari.models.User
import retrofit2.Call
import retrofit2.http.*

interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun signIn(@Body user: User): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun signUp(@Body user: User): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @GET("auth/me")
    fun fetchUserInformation(@Query("token") token: String): Call<User>
}