package com.mychoice.search.di

import com.mychoice.search.data.api.*
import com.mychoice.search.data.repository.*
import com.mychoice.search.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchBindingsModule {

    @Binds @Singleton
    abstract fun bindUniversityRepository(
        impl: UniversityRepositoryImpl
    ): UniversityRepository

    @Binds @Singleton
    abstract fun bindFacultyRepository(
        impl: FacultyRepositoryImpl        // ← эта реализация должна существовать
    ): FacultyRepository

    @Binds @Singleton
    abstract fun bindProgramRepository(
        impl: ProgramRepositoryImpl        // ← и эта
    ): ProgramRepository
}
@Module
@InstallIn(SingletonComponent::class)
object SearchApiModule {

    @Provides @Singleton
    fun provideUniversityApiService(retrofit: Retrofit): UniversityApiService =
        retrofit.create(UniversityApiService::class.java)

    @Provides @Singleton
    fun provideFacultyApiService(retrofit: Retrofit): FacultyApiService =
        retrofit.create(FacultyApiService::class.java)

    @Provides @Singleton
    fun provideProgramApiService(retrofit: Retrofit): ProgramApiService =
        retrofit.create(ProgramApiService::class.java)
}