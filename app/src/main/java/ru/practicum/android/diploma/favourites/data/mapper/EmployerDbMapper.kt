package ru.practicum.android.diploma.favourites.data.mapper

import ru.practicum.android.diploma.core.data.db.entity.EmployerEntity
import ru.practicum.android.diploma.core.domain.models.Employer

object EmployerDbMapper {
    fun map(employer: Employer): EmployerEntity {
        return EmployerEntity(
            id = employer.id,
            name = employer.name,
            logo = employer.logo
        )
    }

    fun map(employerEntity: EmployerEntity): Employer {
        return Employer(
            id = employerEntity.id,
            name = employerEntity.name,
            logo = employerEntity.logo
        )
    }
}