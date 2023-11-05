package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.AreaTreeDto
import ru.practicum.android.diploma.filter.domain.models.Area

object AreaTreeMapper {
    fun map(areaTreeDto: AreaTreeDto?): List<Area> {
        if (areaTreeDto == null)
            return emptyList()

        val regionList = (areaTreeDto.areas?.map { AreaMapper.map(it) }).orEmpty()
        val cityList =
            ((areaTreeDto.areas?.flatMap { it.areas.orEmpty() })?.map { AreaMapper.map(it) }).orEmpty()
        return listOf(regionList, cityList).flatten()
    }
}