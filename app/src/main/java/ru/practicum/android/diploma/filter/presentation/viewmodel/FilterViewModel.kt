package ru.practicum.android.diploma.filter.presentation.viewmodel

import android.util.Log
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
        Log.e("filter", state.toString())
        stateLiveData.value = state
    }

    private fun getCurrentState(
        newFilterParameters: FilterParameters,
        update: Boolean
    ): FilterScreenState {
        return when {
            stateLiveData.value is FilterScreenState.Modified -> FilterScreenState.Modified(
                newFilterParameters,
                update
            )

            stateLiveData.value is FilterScreenState.Initial && filterParameters.equals(
                newFilterParameters
            ) -> FilterScreenState.Initial

            stateLiveData.value is FilterScreenState.Started && filterParameters.equals(
                newFilterParameters
            ) -> FilterScreenState.Initial

            else -> FilterScreenState.Modified(newFilterParameters, update)
        }
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
        setState(newState)
    }

    fun onSalaryChanged(value: String) {
        val salary = if (value.isNotEmpty()) value.toInt() else null
        val newFilterParameters = filterParameters.copy(salary = salary)
        setStateEx(newFilterParameters, false)
    }

    fun onLocationChanged(country: Area?, region: Area?) {
        Log.e("filter", country.toString())
        val newFilterParameters = filterParameters.copy(country = country, region = region)
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
        // добавить clickDebounce
        showLocationTrigger.value = filterParameters
    }

    fun saveFilterParameters() {
        filterLocalInteractor.saveFilterParameters(filterParameters)
    }
}