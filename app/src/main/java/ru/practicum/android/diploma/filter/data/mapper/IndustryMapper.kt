package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.IndustryDto
import ru.practicum.android.diploma.filter.data.dto.IndustryTreeDto
import ru.practicum.android.diploma.filter.domain.models.Industry

object IndustryMapper {
    fun map(industryDto: IndustryDto): Industry {
        return Industry(
            id = industryDto.id,
            name = industryDto.name
        )
    }

    fun map(industryTreeDto: IndustryTreeDto): Industry {
        return Industry(
            id = industryTreeDto.id,
            name = industryTreeDto.name
        )
    }
}