package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.core.network.dto.VacancyDTO
import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.LOG_IMAGE

internal fun VacancyDTO.toSearchVacancy(): SearchVacancy {
    return SearchVacancy(
        id,
        name,
        Salary(
            this.salary?.from,
            this.salary?.to,
            this.salary?.currency
        ),
        Employer(
            this.id,
            this.name
        ),
        this.employer?.logo_urls?.get(LOG_IMAGE)
    )
}
