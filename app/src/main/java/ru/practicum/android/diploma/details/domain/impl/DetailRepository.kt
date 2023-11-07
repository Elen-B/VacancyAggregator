package ru.practicum.android.diploma.details.domain.impl

import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar
import ru.practicum.android.diploma.util.Resource

interface DetailRepository {
    suspend fun getVacancyDetail (id:String): Resource<ProfessionDetail>

    suspend fun getVacanciesSimilar (id:String): Resource<List<ProfessionSimillar>>
}

