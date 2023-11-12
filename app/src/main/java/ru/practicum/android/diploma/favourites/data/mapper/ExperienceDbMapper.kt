package ru.practicum.android.diploma.favourites.data.mapper

import ru.practicum.android.diploma.core.db.entity.ExperienceEntity
import ru.practicum.android.diploma.core.domain.models.Experience

object ExperienceDbMapper {
    fun map(experience: Experience): ExperienceEntity {
        return ExperienceEntity(
            id = experience.id,
            name = experience.name
        )
    }

    fun map(experienceEntity: ExperienceEntity): Experience {
        return Experience(
            id = experienceEntity.id,
            name = experienceEntity.name
        )
    }
}