package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.presentation.models.FilterScreenState
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.SingleEventLiveData
import ru.practicum.android.diploma.util.debounce

class FilterViewModel(
    filter: FilterParameters?,
    private val filterLocalInteractor: FilterLocalInteractor
) : ViewModel() {
    private val filterParameters: FilterParameters by lazy { filter ?: FilterParameters() }
    private val originalFilter: FilterParameters by lazy { filter?.copy() ?: FilterParameters() }

    private val stateLiveData = MutableLiveData<FilterScreenState>()
    fun observeState(): LiveData<FilterScreenState> = stateLiveData

    private val showLocationTrigger = SingleEventLiveData<FilterParameters>()
    fun getShowLocationTrigger(): LiveData<FilterParameters> = showLocationTrigger

    private val showIndustryTrigger = SingleEventLiveData<Industry?>()
    fun getShowIndustryTrigger(): LiveData<Industry?> = showIndustryTrigger

    private val saveFilterTrigger = SingleEventLiveData<Unit>()
    fun getSaveFilterTrigger(): LiveData<Unit> = saveFilterTrigger

    private var isClickAllowed = true
    private val onTrackClickDebounce =
        debounce<Boolean>(CLICK_DEBOUNCE_DELAY, viewModelScope, false) {
            isClickAllowed = it
        }

    init {
        setState(FilterScreenState.Started(filterParameters, true))
    }

    private fun setState(state: FilterScreenState) {
        stateLiveData.value = state
    }

    private fun getCurrentState(
        newFilterParameters: FilterParameters,
        update: Boolean
    ): FilterScreenState {
        return if (originalFilter == newFilterParameters)
            FilterScreenState.Started(newFilterParameters, update)
        else
            FilterScreenState.Modified(newFilterParameters, update)
    }

    private fun setFilterParameters(newFilterParameters: FilterParameters) {
        filterParameters.country = newFilterParameters.country
        filterParameters.region = newFilterParameters.region
        filterParameters.industry = newFilterParameters.industry
        filterParameters.salary = newFilterParameters.salary
        filterParameters.fSalaryRequired = newFilterParameters.fSalaryRequired
    }

    private fun setStateEx(filterParameters: FilterParameters, update: Boolean) {
        val newState = getCurrentState(filterParameters, update)
        setFilterParameters(filterParameters)
        if (newState != stateLiveData.value)
            setState(newState)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            onTrackClickDebounce(true)
        }
        return current
    }

    fun onSalaryChanged(value: String) {
        val salary = if (value.isNotEmpty()) value.toInt() else null
        val newFilterParameters = filterParameters.copy(salary = salary)
        setStateEx(newFilterParameters, false)
    }

    fun onLocationChanged(country: Area?, region: Area?) {
        val newFilterParameters = filterParameters.copy(country = country, region = region)
        setStateEx(newFilterParameters, true)
    }

    fun onIndustryChanged(industry: Industry?) {
        val newFilterParameters = filterParameters.copy(industry = industry)
        setStateEx(newFilterParameters, true)
    }

    fun onFSalaryRequiredChanged(checked: Boolean) {
        val newFilterParameters = filterParameters.copy(fSalaryRequired = checked)
        setStateEx(newFilterParameters, false)
    }

    fun onClearFilterClick() {
        val newFilterParameters = FilterParameters()
        setStateEx(newFilterParameters, true)
    }

    fun showLocation() {
        if (clickDebounce()) {
            showLocationTrigger.value = filterParameters
        }
    }

    fun showIndustry() {
        if (clickDebounce()) {
            showIndustryTrigger.value = filterParameters.industry
        }
    }

    fun saveFilterParameters() {
        if (clickDebounce()) {
            filterLocalInteractor.saveFilterParameters(filterParameters)
        }
    }
}