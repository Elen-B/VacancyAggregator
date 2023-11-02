package ru.practicum.android.diploma.details.domain.usecase

import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.Resource

interface DetailsInterActor {
    suspend fun getDetails (
        id: String
    ) : Resource<ProfessionDetail>
}