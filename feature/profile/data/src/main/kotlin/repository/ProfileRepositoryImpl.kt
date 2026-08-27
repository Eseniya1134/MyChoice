package com.mychoice.profile.data.repository

import com.mychoice.network.TokenStorage
import com.mychoice.profile.data.remote.ProfileRemoteDataSource
import com.mychoice.profile.domain.model.MyProfile
import com.mychoice.profile.domain.model.PublicProfile
import com.mychoice.profile.domain.model.UpdateProfileData
import com.mychoice.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val tokenStorage: TokenStorage
) : ProfileRepository {

    override suspend fun getMyProfile(): MyProfile {
        val userId = tokenStorage.userId.first()
            ?: throw IllegalStateException("User not authenticated")
        return remoteDataSource.getMyProfile(userId)
    }

    override suspend fun updateProfile(userId: String, data: UpdateProfileData): MyProfile {
        return remoteDataSource.updateProfile(userId, data)
    }

    override suspend fun getPublicProfile(username: String): PublicProfile {
        return remoteDataSource.getPublicProfile(username)
    }
}