package com.mychoice.auth.domain.usecase

import com.mychoice.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        city: String,
        role: String?
    ): Result<String> = runCatching {
        repository.register(email, password, firstName, lastName, age, city, role)
    }
}