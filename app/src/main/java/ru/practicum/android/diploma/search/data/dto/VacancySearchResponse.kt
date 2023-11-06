package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.core.network.dto.VacancyDTO

data class VacancySearchResponse(val items: List<VacancyDTO>, val found: String) : Response(){
}