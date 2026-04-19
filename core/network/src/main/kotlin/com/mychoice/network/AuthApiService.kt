package com.mychoice.network

import retrofit2.http.Body
import retrofit2.http.POST
import com.mychoice.network.model.RegisterRequest
import com.mychoice.network.model.LoginRequest

interface AuthApiService {

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Map<String, String>

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Map<String, String>
}

//Unresolved reference 'RegisterRequest'.
//Unresolved reference 'LoginRequest'.