package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.IndustryTreeDto
import ru.practicum.android.diploma.filter.domain.models.Industry

object IndustryTreeMapper {
    fun map(industryTreeDto: IndustryTreeDto?): List<Industry> {
        if (industryTreeDto == null)
            return emptyList()
        return  industryTreeDto.industries.map { IndustryMapper.map(it) }
    }
}