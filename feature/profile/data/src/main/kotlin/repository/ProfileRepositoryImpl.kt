package com.mychoice.profile.data.repository

import com.mychoice.profile.data.remote.ProfileRemoteDataSource
import com.mychoice.profile.domain.model.MyProfile
import com.mychoice.profile.domain.model.PublicProfile
import com.mychoice.profile.domain.model.UpdateProfileData
import com.mychoice.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getMyProfile(): MyProfile =
        remoteDataSource.getMyProfile()

    override suspend fun updateProfile(userId: String, data: UpdateProfileData): MyProfile =
        remoteDataSource.updateProfile(userId, data)

    override suspend fun getPublicProfile(username: String): PublicProfile =
        remoteDataSource.getPublicProfile(username)
}