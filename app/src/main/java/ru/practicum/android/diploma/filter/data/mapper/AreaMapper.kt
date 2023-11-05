package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.AreaDto
import ru.practicum.android.diploma.filter.data.dto.AreaTreeDto
import ru.practicum.android.diploma.filter.domain.models.Area

object AreaMapper {
    fun map(areaDto: AreaDto): Area {
        return Area(
            id = areaDto.id,
            name = areaDto.name,
            parentId = areaDto.parentId
        )
    }

    fun map(areaTreeDto: AreaTreeDto): Area {
        return Area(
            id = areaTreeDto.id,
            name = areaTreeDto.name,
            parentId = areaTreeDto.parentId
        )
    }
}