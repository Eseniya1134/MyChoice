package com.mychoice.auth.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val city: String,
    val role: String?
)

interface AuthApiService {

    @POST("/api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<Map<String, String>>

    @POST("/api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<Map<String, String>>
}