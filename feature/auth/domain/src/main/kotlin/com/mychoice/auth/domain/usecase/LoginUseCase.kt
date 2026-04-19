package com.mychoice.auth.domain.usecase

import com.mychoice.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<String> =
        runCatching { repository.login(email, password) }
}