package com.mychoice.profile.domain.repository

import com.mychoice.profile.domain.model.MyProfile
import com.mychoice.profile.domain.model.PublicProfile
import com.mychoice.profile.domain.model.UpdateProfileData

interface ProfileRepository {
    suspend fun getMyProfile(): MyProfile
    suspend fun updateProfile(userId: String, data: UpdateProfileData): MyProfile
    suspend fun getPublicProfile(username: String): PublicProfile
}