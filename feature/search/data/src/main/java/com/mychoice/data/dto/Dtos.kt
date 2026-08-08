package com.mychoice.search.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagedUniversityDto(
    val content: List<UniversityDto>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)

@JsonClass(generateAdapter = true)
data class UniversityDto(
    val id: Long,
    val code: String,
    val name: String,
    val city: String,
    val description: String?,
    val rating: Double
)

@JsonClass(generateAdapter = true)
data class UniversityShortDto(
    val id: Long,
    val name: String,
    val code: String
)

@JsonClass(generateAdapter = true)
data class FacultyDto(
    val id: Long,
    val name: String,
    val description: String?,
    val rating: Double,
    val university: UniversityShortDto
)

@JsonClass(generateAdapter = true)
data class FacultyShortDto(
    val id: Long,
    val name: String,
    val rating: Double
)

@JsonClass(generateAdapter = true)
data class ProgramDto(
    val id: Long,
    val name: String,
    val description: String?,
    val rating: Double,
    val degree: String,
    val direction: DirectionDto,
    val faculty: FacultyShortDto
)

@JsonClass(generateAdapter = true)
data class ProgramShortDto(
    val id: Long,
    val name: String,
    val rating: Double,
    val degree: String,
    val direction: DirectionDto
)

@JsonClass(generateAdapter = true)
data class DirectionDto(
    val code: String,
    val name: String
)