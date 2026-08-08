package com.mychoice.search.domain.usecase

import com.mychoice.search.domain.model.*
import com.mychoice.search.domain.repository.*
import javax.inject.Inject

class SearchUniversitiesUseCase @Inject constructor(
    private val repository: UniversityRepository
) {
    suspend operator fun invoke(query: String, page: Int = 0, size: Int = 20): Result<PagedUniversities> {
        return repository.getUniversities(page, size).map { paged ->
            if (query.isBlank()) paged
            else paged.copy(
                content = paged.content.filter { uni ->
                    uni.name.contains(query, ignoreCase = true) ||
                            uni.city.contains(query, ignoreCase = true)
                }
            )
        }
    }
}

class GetUniversityUseCase @Inject constructor(
    private val repository: UniversityRepository
) {
    suspend operator fun invoke(id: Long): Result<University> =
        repository.getUniversity(id)
}

class GetUniversityFacultiesUseCase @Inject constructor(
    private val repository: UniversityRepository
) {
    suspend operator fun invoke(universityId: Long): Result<List<FacultyShort>> =
        repository.getFaculties(universityId)
}

class GetFacultyUseCase @Inject constructor(
    private val repository: FacultyRepository
) {
    suspend operator fun invoke(id: Long): Result<Faculty> =
        repository.getFaculty(id)
}

class GetFacultyProgramsUseCase @Inject constructor(
    private val repository: FacultyRepository
) {
    suspend operator fun invoke(facultyId: Long): Result<List<ProgramShort>> =
        repository.getPrograms(facultyId)
}

class GetProgramUseCase @Inject constructor(
    private val repository: ProgramRepository
) {
    suspend operator fun invoke(id: Long): Result<Program> =
        repository.getProgram(id)
}