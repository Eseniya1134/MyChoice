package com.mychoice.profile.data.remote

import retrofit2.Response
import retrofit2.http.*

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

interface ProfileApiService {

    @GET("/api/users/me")
    suspend fun getMyProfile(
        @Header("X-Auth-User-Id") userId: String
    ): Response<MyProfileResponse>

    @PUT("/api/users/me")
    suspend fun updateProfile(
        @Header("X-Auth-User-Id") userId: String,
        @Body request: UpdateProfileRequest
    ): Response<MyProfileResponse>

    @GET("/api/users/{username}")
    suspend fun getPublicProfile(
        @Path("username") username: String
    ): Response<PublicProfileResponse>
}