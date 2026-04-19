package com.mychoice.auth.data.remote

import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApiService
) {

    suspend fun login(email: String, password: String): String {
        val response: Response<Map<String, String>> = api.login(LoginRequest(email, password))
        if (!response.isSuccessful) {
            val message = when (response.code()) {
                401  -> "Неверный email или пароль"
                404  -> "Пользователь не найден"
                else -> "Ошибка сервера (${response.code()})"
            }
            error(message)
        }
        return response.body()?.get("token")
            ?: error("Сервер не вернул токен")
    }

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        city: String,
        role: String?
    ): String {
        val response: Response<Map<String, String>> = api.register(
            RegisterRequest(email, password, firstName, lastName, age, city, role)
        )
        if (!response.isSuccessful) {
            val message = when (response.code()) {
                409  -> "Пользователь с таким email уже существует"
                400  -> "Проверьте правильность данных"
                else -> "Ошибка сервера (${response.code()})"
            }
            error(message)
        }
        return response.body()?.get("token")
            ?: error("Сервер не вернул токен")
    }
}