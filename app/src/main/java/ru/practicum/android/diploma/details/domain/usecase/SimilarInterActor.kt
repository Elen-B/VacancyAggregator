package ru.practicum.android.diploma.details.domain.usecase

import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar
import ru.practicum.android.diploma.util.Resource

interface SimilarInterActor {
    suspend fun getSimilar (
        id: String
    ) : Resource<List<ProfessionSimillar>>
}