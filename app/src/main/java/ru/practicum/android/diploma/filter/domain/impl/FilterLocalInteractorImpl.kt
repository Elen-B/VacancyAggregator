package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterLocalRepository
import ru.practicum.android.diploma.filter.domain.models.FilterParameters

class FilterLocalInteractorImpl(private val filterLocalRepository: FilterLocalRepository) :
    FilterLocalInteractor {
    override fun getFilterParameters(): FilterParameters? {
        return filterLocalRepository.getFilterParameters()
    }

    override fun saveFilterParameters(filterParameters: FilterParameters?) {
        if ((filterParameters == null) || filterParameters.isEmpty())
            filterLocalRepository.removeFilterParameters()
        else
            filterLocalRepository.saveFilterParameters(filterParameters)
    }
}