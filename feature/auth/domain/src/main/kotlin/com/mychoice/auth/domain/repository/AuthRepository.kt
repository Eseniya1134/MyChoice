package com.mychoice.auth.domain.repository

interface AuthRepository {

    /** Возвращает Pair(token, userId) при успехе */
    suspend fun login(email: String, password: String): Pair<String, String>

    /** Просто регистрирует пользователя, токен не возвращает */
    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        city: String,
        role: String?
    )
}