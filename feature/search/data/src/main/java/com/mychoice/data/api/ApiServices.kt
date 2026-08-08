package com.mychoice.search.data.api

import com.mychoice.search.data.dto.FacultyDto
import com.mychoice.search.data.dto.FacultyShortDto
import com.mychoice.search.data.dto.PagedUniversityDto
import com.mychoice.search.data.dto.ProgramDto
import com.mychoice.search.data.dto.ProgramShortDto
import com.mychoice.search.data.dto.UniversityDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UniversityApiService {
    @GET("api/universities")
    suspend fun getUniversities(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): PagedUniversityDto

    @GET("api/universities/{id}")
    suspend fun getUniversity(@Path("id") id: Long): UniversityDto

    @GET("api/universities/{id}/faculties")
    suspend fun getFaculties(@Path("id") universityId: Long): List<FacultyShortDto>
}

interface FacultyApiService {
    @GET("api/faculties/{id}")
    suspend fun getFaculty(@Path("id") id: Long): FacultyDto

    @GET("api/faculties/{id}/programs")
    suspend fun getPrograms(@Path("id") facultyId: Long): List<ProgramShortDto>
}

interface ProgramApiService {
    @GET("api/programs/{id}")
    suspend fun getProgram(@Path("id") id: Long): ProgramDto
}