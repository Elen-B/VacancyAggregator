package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.models.FilterLocationScreenState

class FilterLocationViewModel(
    country: Area?,
    region: Area?
): ViewModel()  {

    private val stateLiveData = MutableLiveData<FilterLocationScreenState>()
    fun observeState(): LiveData<FilterLocationScreenState> = stateLiveData

    init {
        setState(FilterLocationScreenState.Content(country, region))
    }

    private fun setState(state: FilterLocationScreenState) {
        stateLiveData.value = state
    }

    fun onCountryChanged(country: Area?) {
        val newState =
            (stateLiveData.value as FilterLocationScreenState.Content).copy(country = country)
        setState(newState)
    }

    fun onRegionChanged(region: Area?) {
        val newState =
            (stateLiveData.value as FilterLocationScreenState.Content).copy(region = region)
        setState(newState)
    }
}