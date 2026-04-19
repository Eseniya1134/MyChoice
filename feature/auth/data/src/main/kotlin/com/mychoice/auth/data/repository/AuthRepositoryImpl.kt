package com.mychoice.auth.data.repository

import com.mychoice.auth.data.remote.AuthRemoteDataSource
import com.mychoice.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): String =
        remoteDataSource.login(email, password)

    override suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        city: String,
        role: String?
    ): String = remoteDataSource.register(email, password, firstName, lastName, age, city, role)
}