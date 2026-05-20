package com.mychoice.profile.data.remote

import com.mychoice.profile.domain.model.MyProfile
import com.mychoice.profile.domain.model.PublicProfile
import com.mychoice.profile.domain.model.UpdateProfileData
import retrofit2.Response
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val api: ProfileApiService
) {

    suspend fun getMyProfile(userId: String): MyProfile {
        val response: Response<MyProfileResponse> = api.getMyProfile(userId)
        if (!response.isSuccessful) {
            error("Ошибка загрузки профиля (${response.code()})")
        }
        return response.body()!!.toDomain()
    }

    suspend fun getPublicProfile(username: String): PublicProfile {
        val response: Response<PublicProfileResponse> = api.getPublicProfile(username)
        if (!response.isSuccessful) {
            error("Пользователь не найден (${response.code()})")
        }
        return response.body()!!.toDomain()
    }
    suspend fun getMyProfile(): MyProfile {
        val response: Response<MyProfileResponse> = api.getMyProfile()
        if (!response.isSuccessful) {
            error("Ошибка загрузки профиля (${response.code()})")
        }
        return response.body()!!.toDomain()
    }

    suspend fun updateProfile(userId: String, data: UpdateProfileData): MyProfile {
        val response: Response<MyProfileResponse> = api.updateProfile(
            request = UpdateProfileRequest(
                firstName = data.firstName,
                lastName  = data.lastName,
                age       = data.age,
                city      = data.city
            )
        )
        if (!response.isSuccessful) {
            error("Ошибка обновления профиля (${response.code()})")
        }
        return response.body()!!.toDomain()
    }
    private fun MyProfileResponse.toDomain() = MyProfile(
        username  = username,
        email     = email,
        firstName = firstName,
        lastName  = lastName,
        age       = age,
        city      = city
    )

    private fun PublicProfileResponse.toDomain() = PublicProfile(
        username  = username,
        firstName = firstName,
        lastName  = lastName,
        city      = city
    )
}