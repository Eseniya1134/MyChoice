package com.mychoice.profile.domain.usecase

import com.mychoice.profile.domain.model.MyProfile
import com.mychoice.profile.domain.model.PublicProfile
import com.mychoice.profile.domain.model.UpdateProfileData
import com.mychoice.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetMyProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<MyProfile> =
        runCatching { repository.getMyProfile() }
}

class GetPublicProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(username: String): Result<PublicProfile> =
        runCatching { repository.getPublicProfile(username) }
}

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, data: UpdateProfileData): Result<MyProfile> =
        runCatching { repository.updateProfile(userId, data) }
}