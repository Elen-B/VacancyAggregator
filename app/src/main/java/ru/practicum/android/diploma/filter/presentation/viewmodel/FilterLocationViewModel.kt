package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.models.FilterLocationScreenState
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.SingleEventLiveData
import ru.practicum.android.diploma.util.debounce

class FilterLocationViewModel(
    country: Area?,
    region: Area?,
    private val filterInteractor: FilterInteractor
): ViewModel()  {

    private val stateLiveData = MutableLiveData<FilterLocationScreenState>()
    fun observeState(): LiveData<FilterLocationScreenState> = stateLiveData

    private val showCountryTrigger = SingleEventLiveData<Unit>()
    fun getShowCountryTrigger(): LiveData<Unit> = showCountryTrigger

    private val showRegionTrigger = SingleEventLiveData<Area?>()
    fun getShowRegionTrigger(): LiveData<Area?> = showRegionTrigger

    private val applyFilterTrigger = SingleEventLiveData<List<Area?>>()
    fun getApplyFilterTrigger(): LiveData<List<Area?>> = applyFilterTrigger

    private var isClickAllowed = true
    private val onTrackClickDebounce =
        debounce<Boolean>(CLICK_DEBOUNCE_DELAY, viewModelScope, false) {
            isClickAllowed = it
        }

    init {
        setState(FilterLocationScreenState.Content(country, region))
    }

    private fun setState(state: FilterLocationScreenState) {
        stateLiveData.value = state
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            onTrackClickDebounce(true)
        }
        return current
    }

    fun onCountryChanged(country: Area?) {
        val newState =
            if (country == null || !country.equals((stateLiveData.value as FilterLocationScreenState.Content).country))
                (stateLiveData.value as FilterLocationScreenState.Content).copy(
                    country = country,
                    region = null
                )
        else
            (stateLiveData.value as FilterLocationScreenState.Content).copy(country = country)
        setState(newState)
    }

    fun onRegionChanged(region: Area?) {
        viewModelScope.launch {
            val newState =
                if (region != null) {
                    val country = filterInteractor.getCuntryByRegion(region.id)
                    (stateLiveData.value as FilterLocationScreenState.Content).copy(
                        country = country,
                        region = region
                    )
                } else {
                    (stateLiveData.value as FilterLocationScreenState.Content).copy(region = null)
                }
            setState(newState)
        }
    }

    fun showCountry() {
        if (clickDebounce()) {
            showCountryTrigger.value = Unit
        }
    }

    fun showRegion() {
        if (clickDebounce() && stateLiveData.value is FilterLocationScreenState.Content) {
            showRegionTrigger.value =
                (stateLiveData.value as FilterLocationScreenState.Content).country
        }
    }

    fun applyFilter() {
        if (clickDebounce() && stateLiveData.value is FilterLocationScreenState.Content) {
            applyFilterTrigger.value = listOf(
                (stateLiveData.value as FilterLocationScreenState.Content).country,
                (stateLiveData.value as FilterLocationScreenState.Content).region,
            )
        }
    }
}