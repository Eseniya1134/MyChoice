package com.mychoice.profile.di

import com.mychoice.network.TokenStorage
import com.mychoice.profile.data.remote.ProfileApiService
import com.mychoice.profile.data.repository.ProfileRepositoryImpl
import com.mychoice.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    companion object {

        private const val BASE_URL = "https://unhygienically-fluxional-sharolyn.ngrok-free.dev"

        @Provides
        @Singleton
        @Named("profile")
        fun provideProfileOkHttpClient(
            tokenStorage: TokenStorage
        ): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    val token = runBlocking { tokenStorage.token.first() }
                    val request = chain.request().newBuilder()
                        .apply {
                            if (token != null) {
                                addHeader("Authorization", "Bearer $token")
                            }
                        }
                        .build()
                    chain.proceed(request)
                }
                .build()

        @Provides
        @Singleton
        fun provideProfileApiService(
            @Named("profile") okHttpClient: OkHttpClient
        ): ProfileApiService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProfileApiService::class.java)
    }
}