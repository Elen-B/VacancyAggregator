package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.IndustryTreeDto
import ru.practicum.android.diploma.filter.domain.models.Industry

object IndustryTreeMapper {
    fun map(industryTreeDto: IndustryTreeDto?): List<Industry> {
        if (industryTreeDto == null)
            return emptyList()

        val root = listOf(IndustryMapper.map(industryTreeDto))
        val res = industryTreeDto.industries.map { IndustryMapper.map(it) }


        return listOf(root, res).flatten()
    }
}