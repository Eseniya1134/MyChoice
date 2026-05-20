package com.mychoice.profile.data.remote

import retrofit2.Response
import retrofit2.http.*

// Response модели

data class MyProfileResponse(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val city: String
)

data class PublicProfileResponse(
    val username: String,
    val firstName: String,
    val lastName: String,
    val city: String
)

data class UpdateProfileRequest(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val city: String
)

// Retrofit интерфейс

interface ProfileApiService {

    @GET("/api/users/me")
    suspend fun getMyProfile(): Response<MyProfileResponse>

    @PUT("/api/users/me")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): Response<MyProfileResponse>

    @GET("/api/users/{username}")
    suspend fun getPublicProfile(
        @Path("username") username: String
    ): Response<PublicProfileResponse>
}