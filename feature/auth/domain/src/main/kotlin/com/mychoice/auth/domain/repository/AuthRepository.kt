package com.mychoice.auth.domain.repository

/**
 * Контракт авторизации. Data-слой реализует, domain/presentation только знают об интерфейсе.
 */
interface AuthRepository {

    /** Возвращает JWT-токен при успехе, либо бросает исключение. */
    suspend fun login(email: String, password: String): String

    /** Возвращает токен после регистрации, либо бросает исключение. */
    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        city: String,
        role: String?
    ): String
}