package com.mychoice.search.domain.model

data class University(
    val id: Long,
    val code: String,
    val name: String,
    val city: String,
    val description: String?,
    val rating: Double
)

data class Faculty(
    val id: Long,
    val name: String,
    val description: String?,
    val rating: Double,
    val university: UniversityShort
)

data class UniversityShort(
    val id: Long,
    val name: String,
    val code: String
)

data class FacultyShort(
    val id: Long,
    val name: String,
    val rating: Double
)

data class Program(
    val id: Long,
    val name: String,
    val description: String?,
    val rating: Double,
    val degree: Degree,
    val direction: Direction,
    val faculty: FacultyShort
)

data class ProgramShort(
    val id: Long,
    val name: String,
    val rating: Double,
    val degree: Degree,
    val direction: Direction
)

data class Direction(
    val code: String,
    val name: String
)

enum class Degree { BACHELOR, MASTER, PHD }

data class PagedUniversities(
    val content: List<University>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)