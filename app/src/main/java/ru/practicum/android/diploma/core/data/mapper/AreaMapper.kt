package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.data.network.dto.AreaDTO

object AreaMapper {
    fun map(areaDTO: AreaDTO): Area {
        return Area(
            id = areaDTO.id.orEmpty(),
            name = areaDTO.name.orEmpty()
        )
    }
}