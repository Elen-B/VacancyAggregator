package ru.practicum.android.diploma.details.data.dto

import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.core.data.network.dto.VacancyDTO

class SimilarVacancyResponse (val items: List<VacancyDTO>) : Response()