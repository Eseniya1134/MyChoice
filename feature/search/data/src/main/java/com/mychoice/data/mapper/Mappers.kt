package com.mychoice.search.data.mapper

import com.mychoice.search.data.dto.*
import com.mychoice.search.domain.model.*

fun UniversityDto.toDomain() = University(
    id = id,
    code = code,
    name = name,
    city = city,
    description = description,
    rating = rating
)

fun PagedUniversityDto.toDomain() = PagedUniversities(
    content = content.map { it.toDomain() },
    page = page,
    size = size,
    totalElements = totalElements,
    totalPages = totalPages
)

fun UniversityShortDto.toDomain() = UniversityShort(
    id = id, name = name, code = code
)

fun FacultyShortDto.toDomain() = FacultyShort(
    id = id, name = name, rating = rating
)

fun FacultyDto.toDomain() = Faculty(
    id = id,
    name = name,
    description = description,
    rating = rating,
    university = university.toDomain()
)

fun DirectionDto.toDomain() = Direction(code = code, name = name)

fun ProgramShortDto.toDomain() = ProgramShort(
    id = id,
    name = name,
    rating = rating,
    degree = Degree.valueOf(degree),
    direction = direction.toDomain()
)

fun ProgramDto.toDomain() = Program(
    id = id,
    name = name,
    description = description,
    rating = rating,
    degree = Degree.valueOf(degree),
    direction = direction.toDomain(),
    faculty = faculty.toDomain()
)