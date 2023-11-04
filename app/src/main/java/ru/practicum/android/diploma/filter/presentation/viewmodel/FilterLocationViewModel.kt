package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.models.FilterLocationScreenState
import ru.practicum.android.diploma.util.SingleEventLiveData

class FilterLocationViewModel(
    country: Area?,
    region: Area?
): ViewModel()  {

    private val stateLiveData = MutableLiveData<FilterLocationScreenState>()
    fun observeState(): LiveData<FilterLocationScreenState> = stateLiveData

    private val showCountryTrigger = SingleEventLiveData<Unit>()
    fun getShowCountryTrigger(): LiveData<Unit> = showCountryTrigger

    private val showRegionTrigger = SingleEventLiveData<Area?>()
    fun getShowRegionTrigger(): LiveData<Area?> = showRegionTrigger

    private val applyFilterTrigger = SingleEventLiveData<List<Area?>>()
    fun getApplyFilterTrigger(): LiveData<List<Area?>> = applyFilterTrigger

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

    fun showCountry() {
        showCountryTrigger.value = Unit
    }

    fun showRegion() {
        if (/*clickDebounce() &&*/ stateLiveData.value is FilterLocationScreenState.Content) {
            showRegionTrigger.value =
                (stateLiveData.value as FilterLocationScreenState.Content).country
        }
    }

    fun applyFilter() {
        if (/*clickDebounce() &&*/ stateLiveData.value is FilterLocationScreenState.Content) {
            applyFilterTrigger.value = listOf(
                (stateLiveData.value as FilterLocationScreenState.Content).country,
                (stateLiveData.value as FilterLocationScreenState.Content).region,
            )
        }
    }
}