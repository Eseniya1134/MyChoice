package com.mychoice.search.domain.repository

import com.mychoice.search.domain.model.*

interface UniversityRepository {
    suspend fun getUniversities(page: Int, size: Int): Result<PagedUniversities>
    suspend fun getUniversity(id: Long): Result<University>
    suspend fun getFaculties(universityId: Long): Result<List<FacultyShort>>
}

interface FacultyRepository {
    suspend fun getFaculty(id: Long): Result<Faculty>
    suspend fun getPrograms(facultyId: Long): Result<List<ProgramShort>>
}

interface ProgramRepository {
    suspend fun getProgram(id: Long): Result<Program>
}