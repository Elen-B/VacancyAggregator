package ru.practicum.android.diploma.favourites.data.mapper

import ru.practicum.android.diploma.core.db.entity.EmploymentEntity
import ru.practicum.android.diploma.core.domain.models.Employment

object EmploymentDbMapper {
    fun map(employment: Employment): EmploymentEntity {
        return EmploymentEntity(
            id = employment.id,
            name = employment.name
        )
    }

    fun map(employmentEntity: EmploymentEntity): Employment {
        return Employment(
            id = employmentEntity.id,
            name = employmentEntity.name
        )
    }
}