package com.mychoice.profile.data.local

import com.mychoice.profile.domain.model.MyProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileLocalDataSource @Inject constructor() {

    private val _profile = MutableStateFlow<MyProfile?>(null)
    val profileFlow: StateFlow<MyProfile?> = _profile.asStateFlow()

    suspend fun saveProfile(profile: MyProfile) {
        _profile.value = profile
    }

    suspend fun getProfile(): MyProfile? {
        return _profile.value
    }

    suspend fun clear() {
        _profile.value = null
    }
}