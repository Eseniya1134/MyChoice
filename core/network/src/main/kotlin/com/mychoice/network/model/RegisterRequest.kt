package com.mychoice.network.model

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val city: String,
    val role: String? = null
)