package com.mychoice.search.data.repository

import com.mychoice.search.data.api.*
import com.mychoice.search.data.mapper.*
import com.mychoice.search.domain.model.*
import com.mychoice.search.domain.repository.*
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val api: UniversityApiService
) : UniversityRepository {

    override suspend fun getUniversities(page: Int, size: Int): Result<PagedUniversities> =
        runCatching { api.getUniversities(page, size).toDomain() }

    override suspend fun getUniversity(id: Long): Result<University> =
        runCatching { api.getUniversity(id).toDomain() }

    override suspend fun getFaculties(universityId: Long): Result<List<FacultyShort>> =
        runCatching { api.getFaculties(universityId).map { it.toDomain() } }
}

class FacultyRepositoryImpl @Inject constructor(
    private val api: FacultyApiService
) : FacultyRepository {

    override suspend fun getFaculty(id: Long): Result<Faculty> =
        runCatching { api.getFaculty(id).toDomain() }

    override suspend fun getPrograms(facultyId: Long): Result<List<ProgramShort>> =
        runCatching { api.getPrograms(facultyId).map { it.toDomain() } }
}

class ProgramRepositoryImpl @Inject constructor(
    private val api: ProgramApiService
) : ProgramRepository {

    override suspend fun getProgram(id: Long): Result<Program> =
        runCatching { api.getProgram(id).toDomain() }
}