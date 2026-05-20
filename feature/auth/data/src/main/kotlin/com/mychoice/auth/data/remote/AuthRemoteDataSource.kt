package com.mychoice.auth.data.remote

import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApiService
) {

    suspend fun login(email: String, password: String): Pair<String, String> {
        val response: Response<AuthResponse> = api.login(LoginRequest(email, password))
        android.util.Log.d("AUTH", "login body: ${response.body()}")

        if (!response.isSuccessful) {
            val message = when (response.code()) {
                401  -> "Неверный email или пароль"
                404  -> "Пользователь не найден"
                else -> "Ошибка сервера (${response.code()})"
            }
            error(message)
        }
        val body = response.body() ?: error("Пустой ответ сервера")
        return Pair(body.token, body.userId.toString())
    }

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        city: String,
        role: String?
    ) {
        val response: Response<AuthResponse> = api.register(
            RegisterRequest(email, password, firstName, lastName, age, city, role)
        )
        android.util.Log.d("AUTH", "register code: ${response.code()}, body: ${response.body()}")

        if (!response.isSuccessful) {
            val message = when (response.code()) {
                409  -> "Пользователь с таким email уже существует"
                400  -> "Проверьте правильность данных"
                else -> "Ошибка сервера (${response.code()})"
            }
            error(message)
        }
        // регистрация не возвращает токен — просто успех
    }
}