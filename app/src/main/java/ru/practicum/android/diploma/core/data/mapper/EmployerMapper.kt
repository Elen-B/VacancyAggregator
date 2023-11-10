package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.domain.models.Employer
import ru.practicum.android.diploma.core.network.dto.EmployerDTO
import ru.practicum.android.diploma.util.LOG_IMAGE

object EmployerMapper {
    fun map(employerDto: EmployerDTO): Employer {
        return Employer(
            id = employerDto.id.toString(),
            name = employerDto.name,
            logo = employerDto.logo_urls?.get(LOG_IMAGE)
        )
    }
}