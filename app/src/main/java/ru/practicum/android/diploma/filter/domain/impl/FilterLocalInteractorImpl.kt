package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterLocalRepository
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.domain.models.FilterSaveMode

class FilterLocalInteractorImpl(private val filterLocalRepository: FilterLocalRepository) :
    FilterLocalInteractor {
    override fun getFilterParameters(): FilterParameters? {
        return filterLocalRepository.getFilterParameters()
    }

    override fun saveFilterParameters(
        filterParameters: FilterParameters?,
        saveMode: FilterSaveMode?
    ) {
        val filter = filterLocalRepository.getFilterParameters() ?: FilterParameters()
        val newFilter: FilterParameters? = when (saveMode) {
            FilterSaveMode.ALL -> filterParameters
            FilterSaveMode.LOCATION -> filter.copy(
                country = filterParameters?.country,
                region = filterParameters?.region
            )

            FilterSaveMode.INDUSTRY -> filter.copy(
                industry = filterParameters?.industry,
            )

            else -> {null}
        }
        if ((newFilter == null) || newFilter.isEmpty())
            filterLocalRepository.removeFilterParameters()
        else {
            filterLocalRepository.saveFilterParameters(newFilter)
        }
    }
}