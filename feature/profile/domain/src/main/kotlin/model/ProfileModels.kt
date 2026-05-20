package com.mychoice.profile.domain.model

data class MyProfile(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val city: String
)

data class PublicProfile(
    val username: String,
    val firstName: String,
    val lastName: String,
    val city: String
)

data class UpdateProfileData(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val city: String
)