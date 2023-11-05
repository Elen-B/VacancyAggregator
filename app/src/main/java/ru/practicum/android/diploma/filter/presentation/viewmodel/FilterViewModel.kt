package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.presentation.models.FilterScreenState
import ru.practicum.android.diploma.util.SingleEventLiveData

class FilterViewModel(
    filter: FilterParameters?,
    private val filterLocalInteractor: FilterLocalInteractor
) : ViewModel() {
    private val filterParameters: FilterParameters by lazy { filterLocalInteractor.getFilterParameters() ?: FilterParameters() }

    private val stateLiveData = MutableLiveData<FilterScreenState>()
    fun observeState(): LiveData<FilterScreenState> = stateLiveData

    private val showLocationTrigger = SingleEventLiveData<FilterParameters>()
    fun getShowLocationTrigger(): LiveData<FilterParameters> = showLocationTrigger

    private val saveFilterTrigger = SingleEventLiveData<Unit>()
    fun getSaveFilterTrigger(): LiveData<Unit> = saveFilterTrigger

    init {
        setState(FilterScreenState.Started(filterParameters))
    }

    private fun setState(state: FilterScreenState) {
        stateLiveData.value = state
    }

    private fun getCurrentState(newFilterParameters: FilterParameters, update: Boolean): FilterScreenState {
        return if (filterParameters.equals(newFilterParameters) && stateLiveData.value is FilterScreenState.Initial) {
            FilterScreenState.Initial
        } else {
            FilterScreenState.Modified(newFilterParameters, update)
        }
    }

    private fun setFilterParameters(newFilterParameters: FilterParameters) {
        filterParameters.country = newFilterParameters.country
        filterParameters.region = newFilterParameters.region
        filterParameters.industry = newFilterParameters.industry
        filterParameters.salary = newFilterParameters.salary
        filterParameters.fSalaryRequired = newFilterParameters.fSalaryRequired
    }

    fun onSalaryChanged(value: String) {
        val salary = if (value.isNotEmpty()) value.toInt() else null
        val newFilterParameters = filterParameters.copy(salary = salary)
        setFilterParameters(newFilterParameters)
        setState(getCurrentState(newFilterParameters, false))

    }

    fun onLocationChanged(country: Area?, region: Area?) {
        val newFilterParameters = filterParameters.copy(country = country, region = region)
        setFilterParameters(newFilterParameters)
        setState(getCurrentState(newFilterParameters, true))
    }

    fun onFSalaryRequiredChanged(checked: Boolean) {
        val newFilterParameters = filterParameters.copy(fSalaryRequired = checked)
        setFilterParameters(newFilterParameters)
        setState(getCurrentState(newFilterParameters, false))
    }

    fun onClearFilterClick() {
        val newFilterParameters = FilterParameters()
        setFilterParameters(newFilterParameters)
        setState(getCurrentState(newFilterParameters, true))
    }

    fun showLocation() {
        // добавить clickDebounce
        showLocationTrigger.value = filterParameters
    }

    fun saveFilterParameters() {
        filterLocalInteractor.saveFilterParameters(filterParameters)
    }
}